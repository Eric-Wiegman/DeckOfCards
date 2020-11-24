/**
 * 
 */
package com.compugain.nextsteps.wiegman;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;


/**
  <OL>
 	<LI>Implement automation to test the following APIs in Java</LI>
	<LI>Create a new deck of cards</LI>
	<LI>GET https://deckofcardsapi.com/api/deck/new/</LI>
	<LI>Support adding Jokers with a POST</LI>
	<LI>Draw one or more cards from a deck</LI>
	<LI>GET https://deckofcardsapi.com/api/deck/<<deck_id>>/draw/</LI>
	<LI>Consider Object Oriented principles and code organization.
	<LI>Assume the code you are creating will be the foundation for testing all of the http://deckofcardsapi.com/ APIs.</LI>
	<LI>Automate at-least one test case, per API above, to demonstrate the design you have created</LI>
	<LI>Code should be executable, please provide a README that indicates how to run it.</LI>
  </OL>
  <BR>
  For this exercise, assuming a default one (1) deck of cards
 *
 */
public class CardsAPI extends Fetch {

	private static String deckId;
	private static boolean shuffled;
	private static int remaining;

	public class CardItem {
		private String image;
		private String value;
		private String suit;
		private String code;
		/**
		 * @return the image
		 */
		public String getImage() {
			return image;
		}
		/**
		 * @param image the image to set
		 */
		public void setImage(String image) {
			this.image = image;
		}
		/**
		 * @return the value
		 */
		public String getValue() {
			return value;
		}
		/**
		 * @param value the value to set
		 */
		public void setValue(String value) {
			this.value = value;
		}
		/**
		 * @return the suit
		 */
		public String getSuit() {
			return suit;
		}
		/**
		 * @param suit the suit to set
		 */
		public void setSuit(String suit) {
			this.suit = suit;
		}
		/**
		 * @return the code
		 */
		public String getCode() {
			return code;
		}
		/**
		 * @param code the code to set
		 */
		public void setCode(String code) {
			this.code = code;
		}
	}


	public static boolean createNewDeckOfCards () {
		//default is not to add Jokers
		return createNewDeckOfCards(false);
	}
	

	public static boolean createNewDeckOfCards (boolean addJokers) {
		boolean success = false;
		String apiUrl = CardsTester.BASE_URL + "/new?jokers_enabled=" + addJokers;

		JSONObject json = new JSONObject();
		try {
			Fetch.POSTRequest("POST", 
					apiUrl,
					"");
			json = JsonHandler.convertResponseToJsonObject (
					HTTPRequest.getResponseBody());
		} catch (IOException e) {
			//Ignore this exception. We handle this next.
		}

		success = json.getBoolean("success");
		setDeckId(json.getString("deck_id"));
		setRemaining(json.getInt("remaining"));
		setShuffled(json.getBoolean("shuffled"));

		return success;
	}


	public static List<CardItem> drawCards (int numberToDraw) {
		//https://deckofcardsapi.com/api/deck/<<deck_id>>/draw/?count=2
		String apiUrl = CardsTester.BASE_URL + SLASH
				+ deckId + "/draw/?count=" + numberToDraw;
		boolean success = false;

		JSONObject json = new JSONObject();
		try {
			Fetch.GETRequest("GET", 
					apiUrl,
					"");
			json = JsonHandler.convertResponseToJsonObject (
					HTTPRequest.getResponseBody());
		} catch (IOException e) {
			//Ignore this exception. We handle this next.
		}

		ArrayList<CardItem> cardList = new ArrayList<>();
		cardList.clear();

		success = json.getBoolean("success");
		if (success) {

			JSONArray array = json.getJSONArray("cards");
			Iterator<Object> iterator = array.iterator();

			while (iterator.hasNext()) {
				JSONObject jsonObject = (JSONObject) iterator.next();

				CardsAPI.CardItem ci = new CardsAPI().new CardItem();

				for (String key : jsonObject.keySet()) {
					switch (key) {
					case "image":
						ci.setImage(jsonObject.getString(key));
						break;        
					case "images":
						//no op ... was not in the API's documentation!
						break;
					case "code":
						ci.setCode(jsonObject.getString(key));
						break;        
					case "suit":
						ci.setSuit(jsonObject.getString(key));
						break;
					case "value":
						ci.setValue(jsonObject.getString(key));
						break;	
					default:
						//throw exception - tell user to contact support		
					}   
				}
				//here we add the items to the Java List<class> instance
				cardList.add(ci);
			}

			setDeckId(json.getString("deck_id"));
			setRemaining(json.getInt("remaining"));		
		}
		return cardList;
	}
	
	public static boolean shuffleDeck(String deckId) {
		boolean success = false;
		String apiUrl = 
				CardsTester.BASE_URL + SLASH
				+ deckId + "/shuffle";

		JSONObject json = new JSONObject();
		try {
			Fetch.POSTRequest("POST", 
					apiUrl,
					"");
			json = JsonHandler.convertResponseToJsonObject (
					HTTPRequest.getResponseBody());
		} catch (IOException e) {
			//Ignore this exception. We handle this next.
		}

		success = json.getBoolean("success");
		setDeckId(json.getString("deck_id"));
		setRemaining(json.getInt("remaining"));
		setShuffled(json.getBoolean("shuffled"));

		return success;
	}

	/**
	 * @return the deckId
	 */
	public static String getDeckId() {
		return deckId;
	}

	/**
	 * @param deckId the deckId to set
	 */
	public static void setDeckId(String deckId) {
		CardsAPI.deckId = deckId;
	}

	/**
	 * @return the shuffled
	 */
	public static boolean isShuffled() {
		return shuffled;
	}

	/**
	 * @param shuffled the shuffled to set
	 */
	public static void setShuffled(boolean shuffled) {
		CardsAPI.shuffled = shuffled;
	}

	/**
	 * @return the remaining
	 */
	public static int getRemaining() {
		return remaining;
	}

	/**
	 * @param remaining the remaining to set
	 */
	public static void setRemaining(int remaining) {
		CardsAPI.remaining = remaining;
	}

}

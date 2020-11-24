package com.compugain.nextsteps.wiegman;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.compugain.nextsteps.wiegman.CardsAPI.CardItem;


public class ApiTests
{
	
    /**
     * DataDriven HttpGet Tests for number of cards remaining
     * @throws IOException 
     */
    @Test (dataProvider = "data-provider-get-cards-remaining", 
    		dataProviderClass = DataProviderClass.class )
    public void countCardsRemainingTests(
    		int picked,
    		int expected) throws IOException
    {
    	CardsAPI.createNewDeckOfCards(true);
    	CardsAPI.shuffleDeck(CardsAPI.getDeckId());
    	CardsAPI.drawCards(picked);
    	int remaining = CardsAPI.getRemaining();
    	 
    	 Assert.assertEquals(remaining, expected, 
    			 "The remaining # of cards is not what was expected.");
    }
    
    
    /**
     * DataDriven HttpGet Tests for exact cards picked -- unshuffled
     * new deck without Jokers
     * @throws IOException 
     */
    @Test (dataProvider = "data-provider-get-cards", 
    		dataProviderClass = DataProviderClass.class )
    public void getCardsPickedTests(
    		int picked,
    		String expected) throws IOException
    {
    	CardsAPI.createNewDeckOfCards(false);
    	List<CardItem> list = CardsAPI.drawCards(picked);
    	String actual = "";
    	for (CardsAPI.CardItem cards : list) {
			actual += cards.getValue() + " " + cards.getSuit() + " ";
		}
    	
    	actual = actual.trim();
    	 
    	 Assert.assertEquals(actual, expected, 
    			 "The actual cards are not what were expected.");
    }
    
    /**
     * DataDriven HttpGet Tests for counting total cards after shuffling
     * <<deck>> number of new decks, without Jokers
     * @throws IOException 
     */
    @Test (dataProvider = "data-provider-get-cards-muti-decks", 
    		dataProviderClass = DataProviderClass.class )
    public void getCardsInMultipleDecksTests(
    		int decks,
    		int expected) throws IOException
    {
    	
    	String apiUrl = 
    			CardsTester.BASE_URL +
    			"/new/shuffle/?deck_count=" +
    			decks;
    	
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
		
		int totalCards = json.getInt("remaining");
    	
    	 
    	 Assert.assertEquals(totalCards, expected, 
    			 "The actual cards are not what were expected.");
    }
    
    /**
     * DataDriven HttpGet Tests that verify the file name of the image
     * is equal to the code associated with the Card.
     * @throws IOException 
     */
    @Test ( )
    public void imageHasCodeNameTests() throws IOException
    {
    	SoftAssert softAssert = new SoftAssert();
    	
    	int counter = 0;
    	while (counter < 5) {
    		CardsAPI.createNewDeckOfCards(true);
    		CardsAPI.shuffleDeck(CardsAPI.getDeckId());

    		List<CardItem> list = CardsAPI.drawCards(1);
    		String filePath = "";
    		String code = "";
    		for (CardsAPI.CardItem cards : list) {
    			filePath = cards.getImage();
    			code = cards.getCode();
    		}

    		File f = new File(filePath);
    		String fileName = f.getName().replace(".png", "");

    		System.out.println("file name of " + fileName + " should equal " 
    				+ "code of " + code);
    		
    		softAssert.assertEquals(fileName, code, 
    				"The file name of the image is not equal to the code for the Card.");
    		
    		counter++;

    }
    	softAssert.assertAll();
    	
    	}
    
    //reshuffle of same deck should not change the deckId
    /**
     * DataDriven HttpGet Tests for verifying that the deckId does
     * not change when you reshuffle the same deck of cards.
     * @throws IOException 
     */
    @Test ()
    public void reshuffleDoesNotChangeDeckIdTests() throws IOException
    {
    	
    	SoftAssert softAssert = new SoftAssert();
    	
    	int counter = 0;
    	while (counter < 5) {
    	
    	CardsAPI.createNewDeckOfCards(true);
    	String deckId = CardsAPI.getDeckId();
    	CardsAPI.shuffleDeck(deckId);
    	String shuffledDeckId = CardsAPI.getDeckId();
    	
    	System.out.println("Original deck ID of " + deckId + 
    			" should remain the same as " + shuffledDeckId +
    			" when the same Deck is reshuffled.");
    	 
    	 softAssert.assertEquals(deckId, shuffledDeckId,
    			 "Reshuffle of same deck of cards changed the Deck ID.");
    	 
    	 counter++;
    	}
    	
    	softAssert.assertAll();
    }
    
    
    /**
     * DataDriven HttpGet Tests for verifying the shuffle state.
     * @throws IOException 
     */
    @Test (dataProvider = "data-provider-shuffling-test", 
    		dataProviderClass = DataProviderClass.class )
    public void cardShufflingTests(
    		boolean doShuffle,
    		boolean expected) throws IOException
    {
    	boolean isShuffled = false;
    	
    	String apiUrl = CardsTester.BASE_URL + "/new/";
    	
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
		
		isShuffled = json.getBoolean("shuffled");
		CardsAPI.setDeckId(json.getString("deck_id"));
    	
    	if (doShuffle) {
    		isShuffled = CardsAPI.shuffleDeck(CardsAPI.getDeckId());
    	}
    	 
    	 Assert.assertEquals(doShuffle, isShuffled, 
    			 "The shuffle state of the deck of cards is not what was expected.");
    }
    
}

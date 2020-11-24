package com.compugain.nextsteps.wiegman;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONObject;

/**
 * 
 */
public class CardsTester extends Fetch {
	public static final String BASE_URL = "https://deckofcardsapi.com/api/deck";
	static Scanner scanner = new Scanner(System.in);
	

	/**
	 * The main entry point into the Java code.
	 * 
	 * @param args used for CLI use.
	 * @throws IOException Anomalous or exceptional conditions requiring special
	 *                     processing during the execution of this program. Signals
	 *                     that an I/O exception of some sort has occurred.
	 */
	public static void main(String[] args) {
		int numAvailable = 0;

		if (CardsAPI.createNewDeckOfCards(true)) {
			numAvailable = CardsAPI.getRemaining();

			// shuffle the deck of cards
			if (CardsAPI.shuffleDeck(CardsAPI.getDeckId())) {

				while (numAvailable > 0) {
					numAvailable = askForChoice(numAvailable);
				}
			} else {
				logger.info("Unable to shuffle the deck of cards.");
				numAvailable = -1;
			}
		} else {
			logger.info("Unable to create a new deck of cards.");
			numAvailable = -1;
		}

		if (numAvailable == -1) {
			logger.info(NEWLINE + "You requested to quit. Please try again, later.");
			scanner.close();
		} else {
			// only thing left is we ran out of cards!
			logger.info(NEWLINE + "You ran out of cards. Please try again, later.");
			scanner.close();
		}

	}
	

	/**
	 * Uses the Java Scanner class to allow user to choose to identify the country
	 * by either Name or Code.
	 * 
	 * @param numAvailable
	 */
	public static int askForChoice(int numAvailable) {
		int newNumAvailable = numAvailable;

		logger.info("How many cards do you want out of the {} available? " + NEWLINE
				+ "(enter 0, or a negative integer, to quit)", newNumAvailable);
		String choose = scanner.nextLine();

		int numCards = 1;
		try {
			numCards = Integer.parseInt(choose);
		} catch (NumberFormatException e) {
			logger.warn(
					"{} is not a valid number of cards. " + NEWLINE + "We will assume you meant to choose one card.",
					SINGLE_QUOTE + choose + SINGLE_QUOTE);
		}

		if (numCards <= newNumAvailable) {
			if (numCards > 0) {
				// draw N cards from a deck
				List<CardsAPI.CardItem> cardList = CardsAPI.drawCards(numCards);
				
				logger.info("Here are the card(s) picked...");
				for (CardsAPI.CardItem cards : cardList) {
					logger.info("{} of {}", cards.getValue(), cards.getSuit());
				}
				newNumAvailable = CardsAPI.getRemaining();
			} else {
				newNumAvailable = -1;
			}
		} else {
			// number of cards requested is out of range
			// newNumAvailable remains the same
			logger.info("Requested number of cards is out of range. " + "Please try again!");
		}
		return newNumAvailable;
	}
	

	public static JSONObject convertResponseToJsonObject(String response) {
		if (response.substring(0, 1).equals("[")) {
			response = response.substring(1);
		}
		return new JSONObject(response);
	}
}

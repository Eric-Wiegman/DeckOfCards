package com.compugain.nextsteps.wiegman;

import java.io.IOException;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

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
}

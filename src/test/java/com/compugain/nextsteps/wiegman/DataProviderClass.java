package com.compugain.nextsteps.wiegman;

import org.testng.annotations.DataProvider;

/**
 * The class containing data used to drive the tests where we have a
 * shuffled deck of cards (one deck, with 2 jokers, for 54 total)
 */
public class DataProviderClass 
{
    /**
     * We pick X cards and expect a certain number to remain.
     * @return ObjectArray that contains the data used to drive the tests
     */
    @DataProvider(name = "data-provider-get-cards-remaining")
    public static Object[][] dataProviderGetCardsRemaining() 
    {
        return new Object[][] { 
        	//{picked,expected}
			{2,52}, 
			{52,2}, 
			{26,28},
			// API cannot pick more cards than are available, so we will
			// expect the remaining to be the original 54 cards (as none
			// are picked.
        	{55,54},
        	{0,54},
        	{54,0}
        };
    }
    
    
    /**
     * We pick X cards and expect exact picks (value and suit) as in this 
     * case we do not add Jokers and do not shuffle the deck
     * @return ObjectArray that contains the data used to drive the tests
     */
    @DataProvider(name = "data-provider-get-cards")
    public static Object[][] dataProviderGetCards()
    {
        return new Object[][] { 
        	//{picked,expected}
			{1,"ACE SPADES"},
			{3, "ACE SPADES 2 SPADES 3 SPADES"},
			{13, "ACE SPADES 2 SPADES 3 SPADES 4 SPADES 5 SPADES 6 SPADES "
					+ "7 SPADES 8 SPADES 9 SPADES 10 SPADES JACK SPADES "
					+ "QUEEN SPADES KING SPADES"}
        };
    }
    
    
    /**
     * We pick X cards and expect exact picks (value and suit) as in this 
     * case we do not add Jokers
     * @return ObjectArray that contains the data used to drive the tests
     */
    @DataProvider(name = "data-provider-get-cards-muti-decks")
    public static Object[][] dataProviderGetCardCountForMultiDecks()
    {
        return new Object[][] { 
        	//{decks,expected}
			{1,52},
			{3, 156},
			{13, 676},
			{0,0},
			//A negative # of decks is rejected, and we have zero cards
			{-1,0}
			
        };
    }
    
    
    /**
     * We are checking if we shuffle the cards that the shuffle state is true
     * otherwise it is false
     * @return ObjectArray that contains the data used to drive the tests
     */
    @DataProvider(name = "data-provider-shuffling-test")
    public static Object[][] dataProviderGetShuffleStateForDec()
    {
        return new Object[][] { 
        	//{doShuffle,expected}
			{true,true},
			{false,false}
			
        };
    }
    
}

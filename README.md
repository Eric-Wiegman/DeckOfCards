# DeckOfCards

The DeckOfCards app is a game that tests the "Deck of Cards" API by creating a new deck of cards; adding jokers; shuffling it; and then iteratively asking the user to draw a number of cards.

## Features
- Implements automation to test the following APIs in Java
  - Create a new deck of cards
    - GET https://deckofcardsapi.com/api/deck/new/
  - Support adding Jokers with a POST
  - Draw one or more cards from a deck
     - GET https://deckofcardsapi.com/api/deck/<>/draw/
- Takes advantage of Object Oriented principles and code organization. 

## Getting started
Prerequisites:
- Your computer should be running the latest Windows 10, version 1809 or newer. It is not required, but the code was developed and tested on that environment.
- Install the latest version of your favorite IDE -- such as Eclipse or IntelliJ.
- Install the needed JDK code in able to run the Java code.
- Get the code:
    ```
    git clone https://github.com/Eric-Wiegman/DeckOfCards.git
    ```

- Open the CardsTester.java file and run it as a Java Application to play the game that indirectly tests the API.
- Open the APITests.java file and run it as a TestNG Test to run QA Test Cases to more formally test the API.

## Instructions
 - The following actions occur before you even start the executable CardsTester game:
   - A new, unshuffled deck of cards is created.
   - A POST is done to add the 2 Jokers to the deck.
   - The deck is shuffled.
   - The initial count of 54 cards is stored in a variable (and will be updated from time-to-time.)
   
 - While the count of cards is greater than zero, the code then goes into a loop, where it asks a prompt such as below in the Console
 ```
 2020-11-24 13:20:42,412 [main] How many cards do you want out of the 54 available? 
(enter 0, or a negative integer, to quit)
```
- As indicated, if you enter a zero (0) or a negative integer, then the game politely exits.
```
2020-11-24 13:38:24,683 [main] How many cards do you want out of the 54 available? 
(enter 0, or a negative integer, to quit)
0
2020-11-24 13:38:30,596 [main] 
You requested to quit. Please try again, later.
```
- If you enter a positive number of cards greater than the current count of cards in the deck, then you are asked to try again.
```
2020-11-24 13:20:42,412 [main] How many cards do you want out of the 54 available? 
(enter 0, or a negative integer, to quit)
1000
2020-11-24 13:33:44,533 [main] Requested number of cards is out of range. Please try again!
2020-11-24 13:33:44,534 [main] How many cards do you want out of the 54 available? 
(enter 0, or a negative integer, to quit)
```
- If you enter a non-numeric value at the prompt, the code assumes you meant to draw one (1) card and then does so.
```
2020-11-24 13:33:44,534 [main] How many cards do you want out of the 54 available? 
(enter 0, or a negative integer, to quit)
APIs are fun
2020-11-24 13:34:49,903 [main] 'APIs are fun' is not a valid number of cards. 
We will assume you meant to choose one card.
2020-11-24 13:34:50,279 [main] Here are the card(s) picked...
2020-11-24 13:34:50,279 [main] 9 of SPADES
```
- You will eventually have no more available cards to draw, as all cards have been drawn from the deck. At that time, the game politely exits.
```
You ran out of cards. Please try again, later.
```
- In all other cases, the cards are drawn from the deck and listed (similar to the output seen above when a non-numeric value was given at the prompt.)

## Issues

- You will notice that one of the TestNG tests may fail, but I have not changed the code to "work around" the potential issue that it has recovered.
Specifically, the assumption was that the main image file (such as "3S.png") had the same name as the code ("3S") for this example of 3 of Spades.
However, when testing the testcase runs, it was discovered that for the Ace of Diamonds, the code was the expected "AD" yet the file name was "aceDiamonds.png" --
While this may not finding a software bug, it is certainly pointing out a glaring inconsistency.

## License

This is not licensed.

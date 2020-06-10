package com.blackjack;

import com.blackjack.Deck;
import com.blackjack.Values;

import java.util.Scanner;

public class Blackjack {

    public static int playerMoney = 200;
    public static boolean endRound = false;
    public static int playerBet;
    public static Deck playingDeck = new Deck();

    // create dealer hand, player hand and split hand if it's needed
    public static Deck playerHand = new Deck();
    public static Deck dealerHand = new Deck();
    public static Deck split = new Deck();

    // initialize variables for player's split choice, double down choice, hit or
    // stand choice
    public static String answerSplit = "";
    public static String answerdd = "";
    public static String hitOrStand = "";
    public static String splitHitOrStand = "";
    public static boolean doubleDown = false;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";

    public static void main(String[] args) throws InterruptedException {

        System.out.println("\nWelcome to Blackjack!");

        playingDeck.createFullDeck();
        // Don't shuffle the deck if you need to test splitting cards
        playingDeck.shuffleDeck();

        // Set up scanner
        Scanner userInput = new Scanner(System.in);

        // Game loops while player still has money
        do {
            // Take the player's bet
            Thread.sleep(800);
            System.out.print("\nYou have $" + playerMoney + ", how much would you like to bet? $");
            playerBet = userInput.nextInt();
            // if statement that will not allow player to bet unless they are betting in
            // increments of $5
            try {
                if (playerBet % 5 != 0) {
                    throw new ArithmeticException("Sorry - you are only allowed to bet in $5 increments.");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }

            try {
                if (playerBet > playerMoney) {
                    throw new ArithmeticException("You cannot bet more than you have. Please leave.");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                break;
            }

            // Player gets two cards
            playerHand.draw(playingDeck);
            playerHand.draw(playingDeck);

            // Set the values and suits of the cards to test split functionality
            // playerHand.getCard(0).setValue(Values.ACE);
            // playerHand.getCard(0).setSuit(Suits.CLUB);
            //
            // playerHand.getCard(1).setValue(Values.ACE);
            // playerHand.getCard(1).setSuit(Suits.DIAMOND);

            // Dealer gets two cards
            dealerHand.draw(playingDeck);
            dealerHand.draw(playingDeck);

            // reset endRound and doubleDown to false before each round
            endRound = false;
            doubleDown = false;
            answerdd = "";
            answerSplit = "";

            // ------------------------------------------------------------------------------------------------

            Thread.sleep(800);
            System.out.println("\nYour hand: " + playerHand.toString());
            Thread.sleep(800);
            System.out.println("Your hand is valued at: " + playerHand.cardsValue());

            // Display dealer hand - one card is hidden until the player busts or stands
            Thread.sleep(800);
            System.out.println("\nDealer Hand: " + dealerHand.getCard(0).toString() + " and [Hidden]");

            // if statement that will create a new hand if player wants to split cards with
            // same values
            // NEVER split Tens (Jacks, Queens, Kings), Fours or Fives
            if (playerHand.getCard(0).getValue() == playerHand.getCard(1).getValue()
                    && playerHand.dontSplitThese(playerHand)) {
                Thread.sleep(800);
                System.out.print("\nDo you want to split your cards?");
                answerSplit = userInput.next();

                // Tell player they should split if they have Eights or Aces
                if (!answerSplit.equalsIgnoreCase("yes") && playerHand.getCard(0).getValue() == Values.ACE
                        || playerHand.getCard(0).getValue() == Values.EIGHT) {
                    Thread.sleep(800);
                    System.out.println("You should always split Eights or Aces. Would you like to split your cards?");
                    answerSplit = userInput.next();
                }

                if (answerSplit.equalsIgnoreCase("yes")) {
                    playerHand.moveCard(split);
                }

            }

            while (answerSplit.equalsIgnoreCase("yes")) {
                // Ask player if they want to hit or stand on the first hand - it will only ask
                // them to hit or stand
                // if they haven't busted or decided to stand
                if (!splitHitOrStand.equalsIgnoreCase("stand") && !(split.cardsValue() >= 21)) {
                    Thread.sleep(800);
                    System.out.print("Your first hand is now: " + split.toString());
                    Thread.sleep(800);
                    System.out.print("Would you like to hit or stand? ");
                    splitHitOrStand = userInput.next();

                    // Player hits
                    if (splitHitOrStand.equalsIgnoreCase("hit") && !(split.cardsValue() >= 21)) {
                        split.draw(playingDeck);
                        Thread.sleep(800);
                        System.out.println("\nYou draw a: " + split.getCard(split.deckSize() - 1).toString());
                        Thread.sleep(800);
                        System.out.println("Your hand is now valued at: " + split.cardsValue());

                        playerHas21(split);
                        // Bust if > 21
                        Thread.sleep(800);
                        if (split.cardsValue() > 21) {
                            System.out.println(ANSI_RED + "Your hand went over 21. You busted!" + ANSI_RESET);
                        }
                    }
                }

                // Player hits second hand or stood on the first hand
                if (splitHitOrStand.equalsIgnoreCase("stand") || split.cardsValue() >= 21) {
                    // Play the second hand now that the first one went bust
                    // Ask player if they want to hit or stand on the second hand
                    Thread.sleep(800);
                    System.out.println("Your second hand is now: " + playerHand.toString());
                    Thread.sleep(800);
                    System.out.print("Would you like to hit or stand? ");
                    hitOrStand = userInput.next();

                    if (hitOrStand.equalsIgnoreCase("hit")) {
                        playerHand.draw(playingDeck);
                        Thread.sleep(800);
                        System.out.println("\nYou draw a: " + playerHand.getCard(playerHand.deckSize() - 1).toString());
                        Thread.sleep(800);
                        System.out.println("Your hand is now valued at: " + playerHand.cardsValue());

                        playerHas21(playerHand);
                        // Bust if > 21
                        Thread.sleep(800);
                        if (playerHand.cardsValue() > 21) {
                            System.out.println(ANSI_RED + "Your hand went over 21. You busted!" + ANSI_RESET);
                            break;
                        }
                    }

                    // Player stands
                    if (hitOrStand.equalsIgnoreCase("stand")) {
                        break;
                    }
                }

            }

            do {
                // don't check any of these conditions if player decided to split
                if (answerSplit.equalsIgnoreCase("yes")) {
                    break;
                }

                // player can only double down if their hand total is 9, 10 or 11
                if (playerHand.cardsValue() == 9 || playerHand.cardsValue() == 10
                        || playerHand.cardsValue() == 11 && !answerSplit.equalsIgnoreCase("yes")) {
                    Thread.sleep(800);
                    System.out.print("Do you want to Double Down? ");
                    answerdd = userInput.next();
                }

                // player doesn't want to double down so they get the option to hit or stand
                if (!answerdd.equalsIgnoreCase("yes")) {
                    // Ask player if they want to hit or stand
                    Thread.sleep(800);
                    System.out.print("\nWould you like to hit or stand? ");
                    hitOrStand = userInput.next();

                    // Player hits
                    if (hitOrStand.equalsIgnoreCase("hit")) {
                        playerHand.draw(playingDeck);
                        Thread.sleep(800);
                        System.out.println("\nYou draw a: " + playerHand.getCard(playerHand.deckSize() - 1).toString());
                        Thread.sleep(800);
                        System.out.println("Your hand is now valued at: " + playerHand.cardsValue());

                        playerHas21(playerHand);
                        // Bust if > 21
                        Thread.sleep(800);
                        if (playerHand.cardsValue() > 21) {
                            System.out.println(ANSI_RED + "Your hand went over 21. You busted!" + ANSI_RESET);
                            break;
                        }
                    }

                    // Player stands
                    if (hitOrStand.equalsIgnoreCase("stand")) {
                        break;
                    }
                }

                // player wants to double down
                if (answerdd.equalsIgnoreCase("yes")) {
                    if (playerBet * 2 > playerMoney) {
                        Thread.sleep(800);
                        System.out.println("You do not have enough money to double down. However, you can continue "
                                + "to play your hand.");
                        // Ask player if they want to hit or stand

                        // player can't double down but they still have the option to hit or stand
                        if (hitOrStand.charAt(0) == 'h' || hitOrStand.charAt(0) == 'H') {
                            playerHand.draw(playingDeck);
                            Thread.sleep(800);
                            System.out.println(
                                    "\nYou draw a: " + playerHand.getCard(playerHand.deckSize() - 1).toString());
                            Thread.sleep(800);
                            System.out.println("Your hand is now valued at: " + playerHand.cardsValue());

                            playerHas21(playerHand);
                            // Bust if > 21
                            Thread.sleep(800);
                            if (playerHand.cardsValue() > 21) {
                                System.out.println(ANSI_RED + "Your hand went over 21. You busted!" + ANSI_RESET);
                                break;
                            }
                        }

                        // Player stands
                        if (hitOrStand.charAt(0) == 's' || hitOrStand.charAt(0) == 'S') {
                            break;
                        }

                    } else { // player has enough money to double down
                        playerBet = playerBet * 2;
                        doubleDown = true;
                        System.out.println("Your bet is now $" + playerBet);
                        playerHand.draw(playingDeck);
                        Thread.sleep(800);
                        System.out.println("\nYou draw a: " + playerHand.getCard(playerHand.deckSize() - 1).toString());
                        Thread.sleep(800);
                        System.out.println("Your hand is now valued at: " + playerHand.cardsValue());

                        // Bust if > 21
                        Thread.sleep(800);
                        if (playerHand.cardsValue() > 21) {
                            System.out.println(ANSI_RED + "Your hand went over 21. You busted!" + ANSI_RESET);
                            break;
                        }
                    }
                }

            } while (!doubleDown);

            if (answerSplit.equalsIgnoreCase("yes")) {
                Thread.sleep(800);
                System.out.println("\nDealer reveals second card. It's a " + dealerHand.getCard(1).toString());
                Thread.sleep(800);
                System.out.println("Dealer Hand: " + dealerHand.toString());
                Thread.sleep(800);
                determineWinner(dealerHand, split);
                Thread.sleep(800);
                endRound = false;
                System.out.println("-------------------------------------------------------");
                determineWinner(dealerHand, playerHand);
            }

            if (!answerSplit.equalsIgnoreCase("yes")) {
                Thread.sleep(800);
                System.out.println("\nDealer reveals second card. It's a " + dealerHand.getCard(1).toString());
                Thread.sleep(800);
                System.out.println("Dealer Hand: " + dealerHand.toString());
                Thread.sleep(800);
                determineWinner(dealerHand, playerHand);
            }

            // Move the player's cards and the dealer's cards back into the deck
            playerHand.moveAllToDeck(playingDeck);
            dealerHand.moveAllToDeck(playingDeck);

            Thread.sleep(800);
            System.out.println("\nEnd of hand");

        } while (playerMoney > 0);

        System.out.println(ANSI_RED + "Game Over... You are out of money." + ANSI_RESET);
        userInput.close();
    }

    // method that determines winner of the hand
    public static void determineWinner(Deck dealerHand, Deck hand) throws InterruptedException {

        // Adjust player balance if they busted
        if (hand.cardsValue() > 21) {
            Thread.sleep(800);
            System.out.println(ANSI_RED + "\nYou busted! You lose $" + playerBet + "." + ANSI_RESET);
            playerMoney -= playerBet;
            endRound = true;
        }

        // Dealer draws at 16 and below - stands at 17
        while ((dealerHand.cardsValue() < 17) && !endRound) {
            dealerHand.draw(playingDeck);
            Thread.sleep(800);
            System.out.println("Dealer Draws: " + dealerHand.getCard(dealerHand.deckSize() - 1).toString());
        }

        Thread.sleep(800);
        // Display Total Value for Dealer and Player
        System.out.println("\nDealer's Hand value: " + dealerHand.cardsValue() + ". Player's Hand value: "
                + hand.cardsValue() + ".");

        // Dealer busts if their cards are more than 21
        if ((dealerHand.cardsValue() > 21) && !endRound) {
            Thread.sleep(800);
            System.out.println(ANSI_GREEN + "Dealer busts! You win $" + playerBet + "." + ANSI_RESET);
            playerMoney += playerBet;
            endRound = true;
        }

        // Check if dealer beat the player
        if ((dealerHand.cardsValue() >= 17) && (dealerHand.cardsValue() > hand.cardsValue()) && !endRound) {
            Thread.sleep(800);
            System.out.println(ANSI_RED + "Dealer beats you! You lose $" + playerBet + "." + ANSI_RESET);
            playerMoney -= playerBet;
            endRound = true;
        }

        // Determine if it's a push
        if ((hand.cardsValue() == dealerHand.cardsValue()) && !endRound) {
            Thread.sleep(800);
            System.out.println("Push");
            endRound = true;
        }

        // Determine the winner of the hand and recalculate the player's betting balance
        if ((hand.cardsValue() > dealerHand.cardsValue()) && !endRound) {
            Thread.sleep(800);
            System.out.println(ANSI_GREEN + "You win the hand! You win $" + playerBet + "." + ANSI_RESET);
            playerMoney += playerBet;
        } else if (!endRound) {
            Thread.sleep(800);
            System.out.println(ANSI_RED + "You lose the hand! You lose $" + playerBet + "." + ANSI_RESET);
            playerMoney -= playerBet;
        }
    }

    // Player has 21. Method will tell them they have 21 and they will not be able to hit
    public static void playerHas21(Deck hand) throws InterruptedException {
        if (hand.cardsValue() == 21) {
            hitOrStand = "stand";
            splitHitOrStand = "stand";
            Thread.sleep(800);
            System.out.println(ANSI_GREEN + "You have 21!" + ANSI_RESET);
        }
    }

}
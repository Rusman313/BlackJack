package com.blackjack;

import java.awt.*;
import java.util.Scanner;

public class Blackjack {

    public static void main(String[] args) {

        System.out.println("Welcome to Blackjack!");

        // Create the playing deck
        Deck playingDeck = new Deck();
        playingDeck.createFullDeck();
        playingDeck.shuffleDeck();

        // Create hands for the player and the dealer - hands are created from methods that are made in the deck class
        Deck playerHand = new Deck();
        Deck dealerHand = new Deck();

        // Set amount player is playing with
        int playerMoney = 200;

        Scanner userInput = new Scanner(System.in);

        // Game loops while player still has money
        while(playerMoney > 0) {
            // Take the player's bet
            System.out.print("You have $" + playerMoney + ", how much would you like to bet? ");
            int playerBet = userInput.nextInt();
            // if statement that will not allow player to bet unless they are betting in increments of $5
            try {
                if (playerBet % 5 != 0) {
                    throw new ArithmeticException("Sorry - you are only allowed to bet in $5 increments.");
                }
            } catch(Exception e) {
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

            // boolean set to false when round starts - it will be true and round will end when certain
            // conditions are met
            boolean endRound = false;

            // Player gets two cards
            playerHand.draw(playingDeck);
            playerHand.draw(playingDeck);

            // Dealer gets two cards
            dealerHand.draw(playingDeck);
            dealerHand.draw(playingDeck);

            // if statement that will create a new hand if player wants to split cards with same values
            // not working as of now
//            if (playerHand.getCard(0).getValue() == playerHand.getCard(1).getValue()) {
//                Deck split = new Deck();
//                split.addCard(new Card());
//                split.addCard(new Card());
//            }

            while (true) {
                System.out.println("Your hand: ");
                System.out.println(playerHand.toString());
                System.out.println("Your hand is valued at: " + playerHand.cardsValue());

                // Display dealer hand - one card is hidden until the player busts or stands
                System.out.println("Dealer Hand: " + dealerHand.getCard(0).toString() + " and [Hidden]");

                System.out.println("Do you want to Double Down?");
                String answerdd = userInput.next();
                boolean doubledown = false;
                String hitOrStand = "";

                do {
                    // player doesn't want to double down so they get the option to hit or stand
                    if (!answerdd.equals("yes")) {
                        // Ask player if they want to hit or stand
                        System.out.print("Would you like to hit or stand? ");
                        hitOrStand = userInput.next();
                        try {
                            throw new ArithmeticException("Please enter 'hit' or 'stand' to continue playing");
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }

                        // Player hits
                        if (hitOrStand.equalsIgnoreCase("hit")) {
                            playerHand.draw(playingDeck);
                            System.out.println("You draw a: " + playerHand.getCard(playerHand.deckSize() - 1).toString());
                            System.out.println("Your hand is now valued at: " + playerHand.cardsValue());
                            // Bust if > 21
                            if (playerHand.cardsValue() > 21) {
                                System.out.println("Bust. Currently valued at: " + playerHand.cardsValue());
                                playerMoney -= playerBet;
                                endRound = true;
                                break;
                            }
                        }

                        // Player stands
                        if (hitOrStand.equalsIgnoreCase("stand")) {
                            break;
                        }
                    }

                    // player wants to double down
                    if (answerdd.equalsIgnoreCase("yes")|| answerdd.charAt(0) == 'y'
                            || answerdd.charAt(0) == 'Y') {
                        if (playerBet * 2 > playerMoney) {
                            System.out.println("You do not have enough money to double down. However, you can continue " +
                                    "to play your hand.");
                            // Ask player if they want to hit or stand
                            try {
                                throw new ArithmeticException("Please enter 'hit' or 'stand' to continue playing");
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }

                            // player can't double down but they still have the option to hit or stand
                            if (hitOrStand.charAt(0) == 'h' || hitOrStand.charAt(0) == 'H') {
                                playerHand.draw(playingDeck);
                                System.out.println("You draw a: " + playerHand.getCard(playerHand.deckSize() - 1).toString());
                                System.out.println("Your hand is now valued at: " + playerHand.cardsValue());
                                // Bust if > 21
                                if (playerHand.cardsValue() > 21) {
                                    System.out.println("Bust. Currently valued at: " + playerHand.cardsValue());
                                    playerMoney -= playerBet;
                                    endRound = true;
                                    break;
                                }
                            }

                            // Player stands
                            if (hitOrStand.charAt(0) == 's' || hitOrStand.charAt(0) == 'S') {
                                break;
                            }

                        } else { // player has enough money to double down
                            playerBet = playerBet * 2;
                            doubledown = true;
                            System.out.println("Your bet is now $" + playerBet);
                            playerHand.draw(playingDeck);
                            System.out.println("You draw a: " + playerHand.getCard(playerHand.deckSize() - 1).toString());
                            System.out.println("Your hand is now valued at: " + playerHand.cardsValue());
                            // Bust if > 21
                            if (playerHand.cardsValue() > 21) {
                                System.out.println("Bust. Currently valued at: " + playerHand.cardsValue());
                                playerMoney -= playerBet;
                                endRound = true;
                                break;
                            }
                        }
                    }

                    } while (!doubledown);

                break;
            }

            // Reveal Dealer Cards
            System.out.println("Dealer Cards: " + dealerHand.toString());
            // See if dealer has more points than the player. Dealer has to have more than a 17 and a higher total than
            // the player in order to win
            if ((dealerHand.cardsValue() >= 17) && (dealerHand.cardsValue() > playerHand.cardsValue()) && !endRound) {
                System.out.println("Dealer beats you!");
                playerMoney -= playerBet;
                endRound = true;
            }
            // Dealer draws at 16 and below - stands at 17
            while ((dealerHand.cardsValue() < 17) && !endRound) {
                dealerHand.draw(playingDeck);
                System.out.println("Dealer Draws: " + dealerHand.getCard(dealerHand.deckSize()-1).toString());
            }

            // Display Total Value for Dealer and Player
            System.out.println("Dealer's Hand value: " + dealerHand.cardsValue() + "\nPlayer's Hand value: "
                    + playerHand.cardsValue());
            // Dealer busts if their cards are more than 21
            if ((dealerHand.cardsValue() > 21) && !endRound) {
                System.out.println("Dealer busts! You win.");
                playerMoney += playerBet;
                endRound = true;
            }

            // Determine if it's a push
            if ((playerHand.cardsValue() == dealerHand.cardsValue()) && !endRound) {
                System.out.println("Push");
                endRound = true;
            }

            // Determine the winner of the hand and recalculate the player's betting balance
            if ((playerHand.cardsValue() > dealerHand.cardsValue()) && !endRound) {
                System.out.println("You win the hand!");
                playerMoney += playerBet;
            } else if (!endRound) {
                System.out.println("You lose the hand!");
                playerMoney -= playerBet;
            }

            // Move the player's cards and the dealer's cards back into the deck
            playerHand.moveAllToDeck(playingDeck);
            dealerHand.moveAllToDeck(playingDeck);
            System.out.println("End of hand");

        }

        System.out.println("Game Over... You are out of money.");
    }
}

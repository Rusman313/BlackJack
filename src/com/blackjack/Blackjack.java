package com.blackjack;

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

    // initialize variables for player's split choice, double down choice, hit or stand choice
    public static String answerSplit = "";
    public static String answerdd = "";
    public static String hitOrStand = "";
    public static String splitHitOrStand = "";
    public static boolean doubledown = false;

    public static void main(String[] args) {

        System.out.println("Welcome to Blackjack!");

        playingDeck.createFullDeck();
        // Don't shuffle the deck if you need to test splitting cards
        playingDeck.shuffleDeck();

        // Set up scanner
        Scanner userInput = new Scanner(System.in);

        // Game loops while player still has money
        do {
            // Take the player's bet
            System.out.print("You have $" + playerMoney + ", how much would you like to bet? ");
            playerBet = userInput.nextInt();
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

            // Player gets two cards
            playerHand.draw(playingDeck);
            playerHand.draw(playingDeck);

            // Set the values and suits of the cards to test split functionality
//            playerHand.getCard(0).setValue(Values.ACE);
//            playerHand.getCard(0).setSuit(Suits.CLUB);
//
//            playerHand.getCard(1).setValue(Values.ACE);
//            playerHand.getCard(1).setSuit(Suits.DIAMOND);

            // Dealer gets two cards
            dealerHand.draw(playingDeck);
            dealerHand.draw(playingDeck);

            // reset endRound to false before each round
            endRound = false;

//          ------------------------------------------------------------------------------------------------

            System.out.println("Your hand: ");
            System.out.println(playerHand.toString());
            System.out.println("Your hand is valued at: " + playerHand.cardsValue());

            // Display dealer hand - one card is hidden until the player busts or stands
            System.out.println("Dealer Hand: " + dealerHand.getCard(0).toString() + " and [Hidden]");

            // if statement that will create a new hand if player wants to split cards with same values
            // NEVER split Tens (Jacks, Queens, Kings), Fours or Fives
            if (playerHand.getCard(0).getValue() == playerHand.getCard(1).getValue()
                    && playerHand.dontSplitThese(playerHand)) {
                System.out.println("Do you want to split your cards?");
                answerSplit = userInput.next();

                // Tell player they should split if they have Eights or Aces
                if (!answerSplit.equalsIgnoreCase("yes") && playerHand.getCard(0).getValue()
                        == Values.ACE || playerHand.getCard(0).getValue() == Values.EIGHT) {
                    System.out.println("You should always split Eights or Aces. Would you like to split your cards?");
                    answerSplit = userInput.next();
                }

                if (answerSplit.equalsIgnoreCase("yes")) {
                    playerHand.moveCard(split);
                }

            }

            while (answerSplit.equalsIgnoreCase("yes")) {
                // Ask player if they want to hit or stand on the first hand - it will only ask them to hit or stand
                // if they haven't busted or decided to stand
                if (!splitHitOrStand.equalsIgnoreCase("stand") && !(split.cardsValue() > 21)) {
                    System.out.println("Your first hand is now: ");
                    System.out.println(split.toString());
                    System.out.print("Hit or stand? ");
                    splitHitOrStand = userInput.next();
                }

                // Player hits
                if (splitHitOrStand.equalsIgnoreCase("hit")) {
                    split.draw(playingDeck);
                    System.out.println("You draw a: " + split.getCard(split.deckSize() - 1).toString());
                    System.out.println("Your hand is now valued at: " + split.cardsValue());
                    // Bust if > 21
                    if (split.cardsValue() > 21) {
                        System.out.println("Your hand went over 21. You busted!");
                        playerMoney -= playerBet;

                        // Play the second hand now that the first one went bust
                        // Ask player if they want to hit or stand on the second hand
                        System.out.println("Your second hand is now: ");
                        System.out.println(playerHand.toString());
                        System.out.print("Would you like to hit or stand? ");
                        hitOrStand = userInput.next();

                        // Player hits
                        if (hitOrStand.equalsIgnoreCase("hit")) {
                            playerHand.draw(playingDeck);
                            System.out.println("You draw a: " + playerHand.getCard(playerHand.deckSize() - 1).toString());
                            System.out.println("Your hand is now valued at: " + playerHand.cardsValue());
                            // Bust if > 21
                            if (playerHand.cardsValue() > 21) {
                                System.out.println("Your hand went over 21. You busted!");
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
                }

                // Player stands
                if (splitHitOrStand.equalsIgnoreCase("stand")) {
                    // Play the second hand now that the first hand is a stand
                    // Ask player if they want to hit or stand on the second hand
                    System.out.println("Your second hand is now: ");
                    System.out.println(playerHand.toString());
                    System.out.print("Would you like to hit or stand? ");
                    hitOrStand = userInput.next();

                    // Player hits
                    if (hitOrStand.equalsIgnoreCase("hit")) {
                        playerHand.draw(playingDeck);
                        System.out.println("You draw a: " + playerHand.getCard(playerHand.deckSize() - 1).toString());
                        System.out.println("Your hand is now valued at: " + playerHand.cardsValue());
                        // Bust if > 21
                        if (playerHand.cardsValue() > 21) {
                            System.out.println("Your hand went over 21. You busted!");
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
            }


            do {
                // don't check any of these conditions if player decided to split
                if (answerSplit.equalsIgnoreCase("yes")) {
                    break;
                }

                // player can only double down if their hand total is 9, 10 or 11
                if (playerHand.cardsValue() == 9 || playerHand.cardsValue() == 10 || playerHand.cardsValue() == 11
                        && !answerSplit.equalsIgnoreCase("yes")) {
                    System.out.println("Do you want to Double Down?");
                    answerdd = userInput.next();
                }

                // player doesn't want to double down so they get the option to hit or stand
                if (!answerdd.equalsIgnoreCase("yes")) {
                    // Ask player if they want to hit or stand
                    System.out.print("Would you like to hit or stand? ");
                    hitOrStand = userInput.next();

                    // Player hits
                    if (hitOrStand.equalsIgnoreCase("hit")) {
                        playerHand.draw(playingDeck);
                        System.out.println("You draw a: " + playerHand.getCard(playerHand.deckSize() - 1).toString());
                        System.out.println("Your hand is now valued at: " + playerHand.cardsValue());
                        // Bust if > 21
                        if (playerHand.cardsValue() > 21) {
                            System.out.println("Your hand went over 21. You busted!");
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
                if (answerdd.equalsIgnoreCase("yes")) {
                    if (playerBet * 2 > playerMoney) {
                        System.out.println("You do not have enough money to double down. However, you can continue " +
                                "to play your hand.");
                        // Ask player if they want to hit or stand

                        // player can't double down but they still have the option to hit or stand
                        if (hitOrStand.charAt(0) == 'h' || hitOrStand.charAt(0) == 'H') {
                            playerHand.draw(playingDeck);
                            System.out.println("You draw a: " + playerHand.getCard(playerHand.deckSize() - 1).toString());
                            System.out.println("Your hand is now valued at: " + playerHand.cardsValue());
                            // Bust if > 21
                            if (playerHand.cardsValue() > 21) {
                                System.out.println("Your hand went over 21. You busted!");
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
                            System.out.println("Your hand went over 21. You busted!");
                            playerMoney -= playerBet;
                            endRound = true;
                            break;
                        }
                    }
                }

                } while (!doubledown);

            if (answerSplit.equalsIgnoreCase("yes")) {
                System.out.println("Results from the first hand...");
                determineWinner(dealerHand, split);
                endRound = false;
                System.out.println("Results from the second hand...");
                determineWinner(dealerHand, playerHand);
            }

            if (!answerSplit.equalsIgnoreCase("yes")) {
                determineWinner(dealerHand, playerHand);
            }

            // Move the player's cards and the dealer's cards back into the deck
            playerHand.moveAllToDeck(playingDeck);
            dealerHand.moveAllToDeck(playingDeck);

            System.out.println("End of hand");

        } while (playerMoney > 0);

        System.out.println("Game Over... You are out of money.");
    }

    // method that determines winner of the hand
    public static void determineWinner(Deck dealerHand, Deck hand) {

        // Dealer draws at 16 and below - stands at 17
        while ((dealerHand.cardsValue() < 17) && !endRound) {
            dealerHand.draw(playingDeck);
            System.out.println("Dealer Draws: " + dealerHand.getCard(dealerHand.deckSize()-1).toString());
        }

        System.out.println("Dealer reveals second card. It's a " + dealerHand.getCard(1).toString());
        System.out.println("Dealer Hand: " + dealerHand.toString());

        // Display Total Value for Dealer and Player
        System.out.println("Dealer's Hand value: " + dealerHand.cardsValue() + "\nPlayer's Hand value: "
                + hand.cardsValue());

        // Dealer busts if their cards are more than 21
        if ((dealerHand.cardsValue() > 21) && !endRound) {
            System.out.println("Dealer busts! You win $" + playerBet + ".");
            playerMoney += playerBet;
            endRound = true;
        }

        // Check if dealer beat the player
        if ((dealerHand.cardsValue() >= 17) && (dealerHand.cardsValue() > hand.cardsValue()) && !endRound) {
            System.out.println("Dealer beats you! You lose $" + playerBet + ".");
            playerMoney -= playerBet;
            endRound = true;
        }

        // Determine if it's a push
        if ((hand.cardsValue() == dealerHand.cardsValue()) && !endRound) {
            System.out.println("Push");
            endRound = true;
        }

        // Determine the winner of the hand and recalculate the player's betting balance
        if ((hand.cardsValue() > dealerHand.cardsValue()) && !endRound) {
            System.out.println("You win the hand! You win $" + playerBet + ".");
            playerMoney += playerBet;
        } else if (!endRound) {
            System.out.println("You lose the hand! You lose $" + playerBet + ".");
            playerMoney -= playerBet;
        }
    }

}
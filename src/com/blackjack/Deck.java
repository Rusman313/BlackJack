package com.blackjack;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    private ArrayList<Card> deck;

    public Deck() {
        this.deck = new ArrayList<Card>();
    }

    public void createFullDeck() {
        // generate cards
        for (Suits cardSuit : Suits.values()) {
            for (Values cardValue : Values.values()) {
                // Add a new card
                this.deck.add(new Card(cardSuit, cardValue));
            }
        }
    }

    public void shuffleDeck(){
        Collections.shuffle(deck);
    }

    public String toString() {
        String cardListOutput = "";
        for (Card card : this.deck) {
            cardListOutput += " " + card.toString();
        }
        return cardListOutput;
    }

    public Card getCard(int i){
        return this.deck.get(i);
    }

    public void removeCard(int i){
        this.deck.remove(i);
    }

    public Boolean dontSplitThese(Deck playerHand) {
        return playerHand.getCard(0).getValue() != Values.FOUR
                && playerHand.getCard(0).getValue() != Values.FIVE
                && playerHand.getCard(0).getValue() != Values.TEN
                && playerHand.getCard(0).getValue() != Values.JACK
                && playerHand.getCard(0).getValue() != Values.QUEEN
                && playerHand.getCard(0).getValue() != Values.KING;
    }

    public void addCard(Card addCard) {
        this.deck.add(addCard);
    }

    // Get the size of the deck
    public int deckSize() {
        return this.deck.size();
    }

    // Draws from the deck
    public void draw(Deck comingFrom) {
        this.deck.add(comingFrom.getCard(0));
        comingFrom.removeCard(0);
    }

    // This will move cards back into the deck to continue playing
    public void moveAllToDeck(Deck moveTo) {
        int thisDeckSize = this.deck.size();

        // Cards will be added to the deck until the deck size matches its original size
        for (int i = 0; i < thisDeckSize; i++) {
            moveTo.addCard(this.getCard(i));
        }

        for (int i = 0; i < thisDeckSize; i++) {
            this.removeCard(0);
        }
    }

    // Move card to new hand if the player splits their cards
    public void moveCard(Deck split) {
        split.addCard(this.getCard(1));
        this.removeCard(1);
    }

    // Return total value of cards in hand
    public int cardsValue() {
        int total = 0;
        int aces = 0;

        // Get the value of every card in the deck
        for (Card card : this.deck) {
            switch (card.getValue()) {
                case TWO:
                    total += 2;
                    break;
                case THREE:
                    total += 3;
                    break;
                case FOUR:
                    total += 4;
                    break;
                case FIVE:
                    total += 5;
                    break;
                case SIX:
                    total += 6;
                    break;
                case SEVEN:
                    total += 7;
                    break;
                case EIGHT:
                    total += 8;
                    break;
                case NINE:
                    total += 9;
                    break;
                case TEN:
                case JACK:
                case QUEEN:
                case KING:
                    total += 10;
                    break;
                case ACE:
                    aces += 1;
                    break;
            }
        }

        // Ace will be worth 1 if the player total is above 10. Ace will be worth 11 if the player total is 10 or less
        for (int i = 0; i < aces; i++) {
            if (total > 10) {
                total += 1;
            } else {
                total += 11;
            }
        }

        return total;
    }
}
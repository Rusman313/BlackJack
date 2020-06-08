package com.blackjack;

public class Card {

    private Suits suit;
    private Values value;

    public Card(Suits suit, Values value) {
        this.value = value;
        this.suit = suit;
    }

    // no arg constructor
    public Card() {

    }

    public String toString() {
        return this.suit.toString() + "-" + this.value.toString();
    }
    public Values getValue() {
        return this.value;
    }

    public void setValue(Values value) {
        this.value = value;
    }

    public Suits getSuit() {
        return suit;
    }

    public void setSuit(Suits suit) {
        this.suit = suit;
    }

}
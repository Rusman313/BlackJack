package com.blackjack;

public class Card {

    private Suits suit;
    private Values value;

    public Card(Suits suit, Values value) {
        this.value = value;
        this.suit = suit;
    }

    public String toString() {
        String symbol = "Err";
        String cardValue = "Err";
        switch (value.toString()) {
            case "TWO":
                cardValue = "2";
                break;
            case "THREE":
                cardValue = "3";
                break;
            case "FOUR":
                cardValue = "4";
                break;
            case "FIVE":
                cardValue = "5";
                break;
            case "SIX":
                cardValue = "6";
                break;
            case "SEVEN":
                cardValue = "7";
                break;
            case "EIGHT":
                cardValue = "8";
                break;
            case "NINE":
                cardValue = "9";
                break;
            case "TEN":
                cardValue = "10";
                break;
            case "JACK":
                cardValue = "J";
                break;
            case "QUEEN":
                cardValue = "Q";
                break;
            case "KING":
                cardValue = "K";
                break;
            case "ACE":
                cardValue = "A";
                break;
        }
        switch (suit.toString()) {
            case "HEART":
                symbol = "\u2665";
                break;
            case "SPADE":
                symbol = "\u2660";
                break;
            case "CLUB":
                symbol = "\u2663";
                break;
            case "DIAMOND":
                symbol = "\u2666";
                break;
        }
        return cardValue + symbol;
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
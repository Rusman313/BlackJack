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
        String ANSI_RESET = "\u001B[0m";
        String ANSI_BLACK = "\u001B[30m";
        String ANSI_RED = "\u001B[31m";
        String ANSI_WHITE_BACKGROUND = "\u001B[47m";
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
        if (suit.toString().equals("HEART") || suit.toString().equals("DIAMOND")) {
            return ANSI_WHITE_BACKGROUND + ANSI_RED + cardValue + symbol + ANSI_RESET;
        }
        else {
            return ANSI_WHITE_BACKGROUND + ANSI_BLACK + cardValue + symbol + ANSI_RESET;
        }
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
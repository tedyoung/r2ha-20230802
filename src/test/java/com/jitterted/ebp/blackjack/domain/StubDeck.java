package com.jitterted.ebp.blackjack.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class StubDeck implements Deck {
    private static final Suit DUMMY_SUIT = Suit.HEARTS;
    private final ListIterator<Card> iterator;

    public StubDeck(Rank... ranks) {
        List<Card> cards = new ArrayList<>();
        for (Rank rank : ranks) {
            cards.add(new Card(DUMMY_SUIT, rank));
        }
        this.iterator = cards.listIterator();
    }

    public StubDeck(List<Card> cards) {
        this.iterator = cards.listIterator();
    }

    public static Deck playerHitsAndGoesBust() {
        return new StubDeck(Rank.TEN, Rank.EIGHT,
                            Rank.QUEEN, Rank.JACK,
                            Rank.THREE);
    }

    public static Deck playerStandsAndBeatsDealer() {
        return new StubDeck(Rank.TEN, Rank.EIGHT,
                            Rank.QUEEN, Rank.JACK);
    }

    public static Deck playerPushesDealer() {
        return new StubDeck(Rank.TEN, Rank.QUEEN,
                            Rank.NINE, Rank.NINE);
    }

    public static Deck singleCardDeck() {
        return new StubDeck(Rank.TEN);
    }

    public static Deck playerHitsDoesNotBust() {
        return new StubDeck(Rank.TEN, Rank.EIGHT,
                            Rank.SEVEN, Rank.JACK,
                            Rank.THREE);
    }

    public static Deck playerPushesWithDealer() {
        return new StubDeck(Rank.TEN, Rank.QUEEN,
                            Rank.NINE, Rank.NINE);
    }

    public static Deck dealerDrawsOneCardUponDealerTurn() {
        return new StubDeck(Rank.TEN, Rank.QUEEN,
                            Rank.NINE, Rank.FIVE,
                            /*      */ Rank.SIX);
    }

    public static Deck playerDealtBlackjackDealerNotDealtBlackjack() {
        return new StubDeck(Rank.ACE, Rank.NINE,
                            Rank.JACK, Rank.QUEEN);
    }

    @Override
    public Card draw() {
        return iterator.next();
    }

}

package com.jitterted.ebp.blackjack.domain;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

class ShuffledDeckTest {

    @Test
    public void fullDeckHas52Cards() throws Exception {
        ShuffledDeck deck = new ShuffledDeck();

        assertThat(deck.size())
                .isEqualTo(52);
    }

    @Test
    public void drawCardFromDeckReducesDeckSizeByOne() throws Exception {
        ShuffledDeck deck = new ShuffledDeck();

        deck.draw();

        assertThat(deck.size())
                .isEqualTo(51);
    }

    @Test
    public void drawCardFromDeckReturnsValidCard() throws Exception {
        Deck deck = new ShuffledDeck();

        Card card = deck.draw();

        assertThat(card)
                .isNotNull();

        assertThat(card.rankValue())
                .isGreaterThan(0);
    }

    @Test
    public void drawAllCardsResultsInSetOf52UniqueCards() throws Exception {
        Deck deck = new ShuffledDeck();

        Set<Card> drawnCards = new HashSet<>();
        for (int i = 1; i <= 52; i++) {
            drawnCards.add(deck.draw());
        }

        assertThat(drawnCards)
                .hasSize(52);
    }

}
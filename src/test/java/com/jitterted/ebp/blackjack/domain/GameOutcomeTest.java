package com.jitterted.ebp.blackjack.domain;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class GameOutcomeTest {

    @Test
    @Disabled // flaky
    void playerHitsAndGoesBustThenOutcomeIsPlayerLoses() {
        Game game = new Game(new ShuffledDeck());
        game.initialDeal();

        game.playerHits();

        assertThat(game.determineOutcome())
                .isEqualTo("You Busted, so you lose.  💸");
    }
}
package com.jitterted.ebp.blackjack.domain;

import com.jitterted.ebp.blackjack.application.port.GameMonitor;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

class GameMonitorTest {

    @Test
    void playerStandsThenGameIsOverAndResultsSentToMonitor() {
        Fixture fixture = createGameWithMonitorSpy(StubDeck.playerStandsAndBeatsDealer());

        fixture.game.playerStands();

        // verify that the roundCompleted method was called with the specific Game we're using
        verify(fixture.gameMonitorSpy).roundCompleted(fixture.game);
    }

    @Test
    void playerHitsAndGoesBustThenGameResultsSentToMonitor() {
        Fixture fixture = createGameWithMonitorSpy(StubDeck.playerHitsAndGoesBust());

        fixture.game.playerHits();

        verify(fixture.gameMonitorSpy).roundCompleted(fixture.game);
    }

    @Test
    void playerHitsAndDoesNotBustThenNoGameResultsSentToMonitor() {
        Fixture fixture = createGameWithMonitorSpy(StubDeck.playerHitsDoesNotBust());

        fixture.game.playerHits();

        verify(fixture.gameMonitorSpy, never()).roundCompleted(fixture.game);
    }

    @Test
    void playerDealtBlackjackThenGameResultsSentToMonitor() {
        GameMonitor gameMonitorSpy = spy(GameMonitor.class);
        Game game = new Game(StubDeck.playerDealtBlackjackDealerNotDealtBlackjack(), gameMonitorSpy);

        game.initialDeal();

        verify(gameMonitorSpy).roundCompleted(game);
    }


    private static Fixture createGameWithMonitorSpy(Deck deck) {
        GameMonitor gameMonitorSpy = spy(GameMonitor.class);
        Game game = new Game(deck, gameMonitorSpy);
        game.initialDeal();
        return new Fixture(gameMonitorSpy, game);
    }

    private static class Fixture {
        public final GameMonitor gameMonitorSpy;
        public final Game game;

        public Fixture(GameMonitor gameMonitorSpy, Game game) {
            this.gameMonitorSpy = gameMonitorSpy;
            this.game = game;
        }
    }
}
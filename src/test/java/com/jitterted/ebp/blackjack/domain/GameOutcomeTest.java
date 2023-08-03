package com.jitterted.ebp.blackjack.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class GameOutcomeTest {

    @Test
    void playerHitsAndGoesBustThenOutcomeIsPlayerLoses() {
        Game game = createGameAndDoInitialDeal(StubDeck.playerHitsAndGoesBust());

        game.playerHits();

        assertThat(game.determineOutcome())
                .isEqualByComparingTo(GameOutcome.PLAYER_BUSTED);
    }

    @Test
    void playerDealtBetterHandThanDealerAndStandsThenPlayerBeatsDealer() {
        Game game = createGameAndDoInitialDeal(StubDeck.playerStandsAndBeatsDealer());

        game.playerStands();

        assertThat(game.determineOutcome())
                .isEqualByComparingTo(GameOutcome.PLAYER_BEATS_DEALER);
        assertThat(game.isPlayerDone())
                .isTrue();
    }

    @Test
    void playerDealtHandWithSameValueAsDealerThenPlayerPushesDealer() {
        Game game = createGameAndDoInitialDeal(StubDeck.playerPushesDealer());

        game.playerStands();

        assertThat(game.determineOutcome())
                .isEqualByComparingTo(GameOutcome.PLAYER_PUSHES_DEALER);
    }

    @Test
    void playerDoneIsFalseWhenGameIsFirstCreated() {
        Game game = new Game(StubDeck.singleCardDeck());

        assertThat(game.isPlayerDone())
                .isFalse();
    }

    @Test
    void playerDealtBlackjackUponInitialDealAndDealerNotDealtBlackjackThenPlayerWinsBlackjack() {
        Game game = new Game(new StubDeck(Rank.JACK, Rank.NINE,
                                           Rank.ACE, Rank.TEN));

        game.initialDeal();

        assertThat(game.determineOutcome())
                .isEqualByComparingTo(GameOutcome.PLAYER_WINS_BLACKJACK);
        assertThat(game.isPlayerDone())
                .isTrue();
    }

    @Test
    void playerWithHandValueOf21And3CardsIsNotBlackjack() {
        Game game = createGameAndDoInitialDeal(new StubDeck(Rank.JACK, Rank.NINE,
                                                            Rank.EIGHT, Rank.TEN,
                                                            Rank.THREE));

        game.playerHits();
        game.playerStands();

        assertThat(game.determineOutcome())
                .isEqualByComparingTo(GameOutcome.PLAYER_BEATS_DEALER);
    }

    @Test
    void noBlackjackDealtPlayerIsNotDone() {
        Deck notDealtBlackjack = new StubDeck(Rank.EIGHT, Rank.NINE,
                                              Rank.THREE, Rank.EIGHT);
        Game game = new Game(notDealtBlackjack);

        game.initialDeal();

        assertThat(game.isPlayerDone())
                .isFalse();
    }

    @Test
    void playerStandsThenDealerAutomaticallyTakesItsTurn() {
        Game game = new Game(StubDeck.dealerDrawsOneCardUponDealerTurn());
        game.initialDeal();

        game.playerStands();

        assertThat(game.dealerHand().cards())
                .hasSize(3);
    }


    private static Game createGameAndDoInitialDeal(Deck deck) {
        Game game = new Game(deck);
        game.initialDeal();
        return game;
    }

}
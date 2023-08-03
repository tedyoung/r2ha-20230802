package com.jitterted.ebp.blackjack.adapter.in.web;

import com.jitterted.ebp.blackjack.domain.Card;
import com.jitterted.ebp.blackjack.domain.Deck;
import com.jitterted.ebp.blackjack.domain.Game;
import com.jitterted.ebp.blackjack.domain.Rank;
import com.jitterted.ebp.blackjack.domain.ShuffledDeck;
import com.jitterted.ebp.blackjack.domain.StubDeck;
import com.jitterted.ebp.blackjack.domain.Suit;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class BlackjackControllerTest {

    @Test
    void startGameResultsInTwoCardsDealtToPlayer() {
        Game game = new Game(new ShuffledDeck());
        BlackjackController blackjackController = new BlackjackController(game);

        String redirect = blackjackController.startGame();

        assertThat(redirect)
                .isEqualTo("redirect:/game");
        assertThat(game.playerHand().cards())
                .hasSize(2);
    }

    @Test
    void gameViewPopulatesViewModelWithAllCards() {
        Deck stubDeck = new StubDeck(List.of(new Card(Suit.DIAMONDS, Rank.TEN),
                                             new Card(Suit.HEARTS, Rank.TWO),
                                             new Card(Suit.DIAMONDS, Rank.KING),
                                             new Card(Suit.CLUBS, Rank.THREE)));
        Game game = new Game(stubDeck);
        BlackjackController blackjackController = new BlackjackController(game);
        blackjackController.startGame();

        Model model = new ConcurrentModel();
        String viewName = blackjackController.gameView(model);

        assertThat(viewName)
                .isEqualTo("blackjack");

        GameView gameView = (GameView) model.getAttribute("gameView");

        assertThat(gameView.getDealerCards())
                .containsExactly("2♥", "3♣");

        assertThat(gameView.getPlayerCards())
                .containsExactly("10♦", "K♦");
    }

    @Test
    public void hitCommandResultsInThirdCardDealtToPlayer() throws Exception {
        Fixture fixture = createControllerWithGameStarted(StubDeck.playerHitsDoesNotBust());

        String redirectPage = fixture.blackjackController.hitCommand();

        assertThat(redirectPage)
                .isEqualTo("redirect:/game");
        assertThat(fixture.game.playerHand().cards())
                .hasSize(3);
    }

    @Test
    public void hitCommandAndPlayerBustsThenRedirectToDonePage() throws Exception {
        Fixture fixture = createControllerWithGameStarted(StubDeck.playerHitsAndGoesBust());

        String redirectPage = fixture.blackjackController.hitCommand();

        assertThat(redirectPage)
                .isEqualTo("redirect:/done");
    }

    @Test
    public void donePageShowsFinalGameStateWithOutcome() throws Exception {
        Fixture fixture = createControllerWithGameStarted(StubDeck.playerPushesWithDealer());

        Model model = new ConcurrentModel();
        fixture.blackjackController.doneView(model);

        assertThat(model.containsAttribute("gameView"))
                .isTrue();

        String outcome = (String) model.getAttribute("outcome");
        assertThat(outcome)
                .isNotBlank();
    }

    @Test
    void playerStandsResultsInRedirectToDonePageAndPlayerIsDone() {
        Fixture fixture = createControllerWithGameStarted(StubDeck.playerStandsAndBeatsDealer());

        String redirectPage = fixture.blackjackController.standCommand();

        assertThat(redirectPage)
                .isEqualTo("redirect:/done");
        assertThat(fixture.game.isPlayerDone())
                .isTrue();
    }

    private static Fixture createControllerWithGameStarted(Deck deck) {
        Game game = new Game(deck);
        BlackjackController blackjackController = new BlackjackController(game);
        blackjackController.startGame();
        return new Fixture(game, blackjackController);
    }

    private static class Fixture {
        public final Game game;
        public final BlackjackController blackjackController;

        public Fixture(Game game, BlackjackController blackjackController) {
            this.game = game;
            this.blackjackController = blackjackController;
        }
    }

}
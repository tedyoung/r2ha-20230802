package com.jitterted.ebp.blackjack.adapter.in.console;

import com.jitterted.ebp.blackjack.domain.Game;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class ConsoleGame {

    private final Game game;

    public ConsoleGame(Game game) {
        this.game = game;
    }

    public void start() {
        displayWelcomeScreen();
        waitForEnterFromUser();

        game.initialDeal();

        playerPlays();

        displayFinalGameState();

        System.out.println(game.determineOutcome().message());

        resetScreen();
    }

    private void playerPlays() {
        while (!game.isPlayerDone()) {
            displayGameState();
            String command = inputFromPlayer();
            handle(command);
        }
    }

    // convert/transform the input "command" into a COMMAND invocation against Game
    private void handle(String command) {
        if (command.toLowerCase().startsWith("h")) {
            game.playerHits();
        } else if (command.toLowerCase().startsWith("s")) {
            game.playerStands();
        }
    }


    private void resetScreen() {
        System.out.println(ansi().reset());
    }

    private void waitForEnterFromUser() {
        System.out.println(ansi()
                                   .cursor(3, 1)
                                   .fgBrightBlack().a("Hit [ENTER] to start..."));

        System.console().readLine();
    }

    private void displayWelcomeScreen() {
        AnsiConsole.systemInstall();
        System.out.println(ansi()
                                   .bgBright(Ansi.Color.WHITE)
                                   .eraseScreen()
                                   .cursor(1, 1)
                                   .fgGreen().a("Welcome to")
                                   .fgRed().a(" JitterTed's")
                                   .fgBlack().a(" BlackJack game"));
    }

    private String inputFromPlayer() {
        System.out.println("[H]it or [S]tand?");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private void displayGameState() {
        System.out.print(ansi().eraseScreen().cursor(1, 1));
        System.out.println("Dealer has: ");
        System.out.println(ConsoleHand.displayFaceUpCard(game.dealerHand()));

        // second card is the hole card, which is hidden, or "face down"
        ConsoleCard.displayBackOfCard();

        System.out.println();
        System.out.println("Player has: ");
        System.out.println(ConsoleHand.cardsAsString(game.playerHand()));
        System.out.println(" (" + game.playerHand().value() + ")");
    }

    private void displayFinalGameState() {
        System.out.print(ansi().eraseScreen().cursor(1, 1));
        System.out.println("Dealer has: ");
        System.out.println(ConsoleHand.cardsAsString(game.dealerHand()));
        System.out.println(" (" + game.dealerHand().value() + ")");

        System.out.println();
        System.out.println("Player has: ");
        System.out.println(ConsoleHand.cardsAsString(game.playerHand()));
        System.out.println(" (" + game.playerHand().value() + ")");
    }

}

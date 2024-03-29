package com.jitterted.ebp.blackjack.adapter.in.web;

import com.jitterted.ebp.blackjack.domain.Game;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BlackjackController {

    private final Game game; // NOT RECOMMENDED: direct reference to specific DOMAIN OBJECT (Entity)

    public BlackjackController(Game game) {
        this.game = game;
    }

    @PostMapping("/start-game")
    public String startGame() {
        game.initialDeal();
        return redirectBasedOnPlayerState();
    }

    @PostMapping("/stand")
    public String standCommand() {
        game.playerStands();
        return redirectBasedOnPlayerState();
    }

    @PostMapping("/hit")
    public String hitCommand() {
        game.playerHits();
        return redirectBasedOnPlayerState();
    }

    @GetMapping("/game")
    public String gameView(Model model) {
        model.addAttribute("gameView", GameView.from(game));
        return "blackjack";
    }

    @GetMapping("/done")
    public String doneView(Model model) {
        model.addAttribute("gameView", GameView.from(game));
        model.addAttribute("outcome", game.determineOutcome().message());
        return "done";
    }

    private String redirectBasedOnPlayerState() {
        if (game.isPlayerDone()) {
            return "redirect:/done";
        }
        return "redirect:/game";
    }

}

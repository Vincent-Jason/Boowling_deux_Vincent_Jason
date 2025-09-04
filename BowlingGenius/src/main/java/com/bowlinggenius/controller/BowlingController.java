package com.bowlinggenius.controller;

import com.bowlinggenius.model.BowlingGame;
import com.bowlinggenius.service.BowlingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Controller
@RequestMapping("/bowling")
public class BowlingController {
    
    private static final Logger logger = LoggerFactory.getLogger(BowlingController.class);
    private final Map<Long, BowlingGame> games = new HashMap<>();
    private final AtomicLong gameIdCounter = new AtomicLong(1);
    
    @Autowired
    private BowlingService bowlingService;

    @GetMapping("/")
    public String home(Model model) {
        logger.info("GET /bowling/ - Affichage de la page d'accueil du bowling");
        try {
            // Créer un jeu vide pour le formulaire initial
            BowlingGame game = new BowlingGame();
            game.setPlayerName("");
            game.setFrames(new ArrayList<>());
            logger.info("Nouvelle partie créée pour le formulaire: {}", game);
            model.addAttribute("game", game);
            return "bowling";
        } catch (Exception e) {
            logger.error("Erreur lors de l'affichage de la page d'accueil", e);
            throw e;
        }
    }

    @PostMapping("/new")
    public String newGame(@RequestParam String playerName, Model model) {
        logger.info("POST /bowling/new - Création d'une nouvelle partie pour le joueur: {}", playerName);
        try {
            BowlingGame game = bowlingService.createNewGame(playerName);
            long gameId = gameIdCounter.getAndIncrement();
            game.setId(gameId);
            games.put(gameId, game);
            logger.info("Nouvelle partie créée avec l'ID: {}", gameId);
            logger.info("Détails du jeu: {}", game);
            model.addAttribute("game", game);
            return "redirect:/bowling/game/" + gameId;
        } catch (Exception e) {
            logger.error("Erreur lors de la création d'une nouvelle partie", e);
            throw e;
        }
    }

    @GetMapping("/game/{gameId}")
    public String game(@PathVariable Long gameId, Model model) {
        logger.info("GET /bowling/game/{}", gameId);
        try {
            BowlingGame game = games.get(gameId);
            if (game == null) {
                logger.warn("Jeu non trouvé avec l'ID: {}", gameId);
                return "redirect:/";
            }
            logger.info("Affichage du jeu: {}", game);
            model.addAttribute("game", game);
            return "bowling";
        } catch (Exception e) {
            logger.error("Erreur lors de l'affichage du jeu avec l'ID: " + gameId, e);
            throw e;
        }
    }

    @PostMapping("/game/{gameId}/roll")
    public String roll(@PathVariable Long gameId, @RequestParam int pins, Model model) {
        BowlingGame game = games.get(gameId);
        if (game != null) {
            bowlingService.roll(game, pins);
            model.addAttribute("game", game);
        }
        return "bowling :: #game-board";
    }

    @PostMapping("/game/{gameId}/reset")
    public String resetGame(@PathVariable Long gameId) {
        BowlingGame game = games.get(gameId);
        if (game != null) {
            game = bowlingService.resetGame(game);
            games.put(gameId, game);
        }
        return "redirect:/bowling/game/" + gameId;
    }
}

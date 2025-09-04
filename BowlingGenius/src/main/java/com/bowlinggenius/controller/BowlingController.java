package com.bowlinggenius.controller;

import com.bowlinggenius.model.BowlingGame;
import com.bowlinggenius.model.BowlingFrame;
import com.bowlinggenius.service.BowlingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import java.util.ArrayList;

@Controller
@RequestMapping("/bowling")
public class BowlingController {
    
    private static final Logger logger = LoggerFactory.getLogger(BowlingController.class);
    private BowlingGame currentGame;
    
    @Autowired
    private BowlingService bowlingService;

    @GetMapping("")
    public String home() {
        logger.info("Redirection vers /bowling/");
        return "redirect:/bowling/";
    }

    @GetMapping("/")
    public String showHome(Model model) {
        logger.info("=== GET /bowling/ - Début");
        // Crée une nouvelle partie si elle n'existe pas
        if (currentGame == null) {
            logger.info("Création d'une nouvelle partie");
            currentGame = bowlingService.createNewGame("Joueur");
        } else {
            logger.info("Partie existante trouvée - Score: {}", currentGame.getScore());
        }
        model.addAttribute("game", currentGame);
        logger.info("=== GET /bowling/ - Fin");
        return "bowling";
    }

    @PostMapping("/roll")
    @ResponseBody
    public Map<String, Object> roll(@RequestParam("pins") int pins) {
        logger.info("=== REQUÊTE REÇUE == POST /bowling/roll?pins={}", pins);
        Map<String, Object> response = new HashMap<>();
        
        if (currentGame == null) {
            currentGame = bowlingService.createNewGame("Joueur");
            response.put("message", "Nouvelle partie créée");
        }
        
        try {
            bowlingService.roll(currentGame, pins);
            response.put("success", true);
            response.put("score", currentGame.getScore());
            response.put("currentFrame", currentGame.getCurrentFrame());
            response.put("gameOver", currentGame.isGameOver());
        } catch (Exception e) {
            logger.error("Erreur lors du lancer", e);
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        
        return response;
    }

    @PostMapping("/reset")
    public String resetGame() {
        if (currentGame != null) {
            bowlingService.resetGame(currentGame);
        } else {
            currentGame = bowlingService.createNewGame("Joueur");
        }
        return "redirect:/";
    }
}

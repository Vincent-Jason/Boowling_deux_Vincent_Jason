package com.bowlinggenius.service;

import com.bowlinggenius.model.BowlingFrame;
import com.bowlinggenius.model.BowlingGame;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BowlingService {
    
    public BowlingGame createNewGame(String playerName) {
        BowlingGame game = new BowlingGame();
        game.setPlayerName(playerName);
        game.setScore(0);
        game.setComplete(false);
        
        // Initialiser les frames
        List<BowlingFrame> frames = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            BowlingFrame frame = new BowlingFrame();
            frame.setFrameNumber(i + 1);
            frame.setComplete(false);
            frame.setStrike(false);
            frame.setSpare(false);
            frames.add(frame);
        }
        game.setFrames(frames);
        
        return game;
    }
    
    public void roll(BowlingGame game, int pins) {
        if (pins < 0 || pins > 10) {
            throw new IllegalArgumentException("Number of pins must be between 0 and 10");
        }
        
        if (game.isComplete()) {
            throw new IllegalStateException("Game is already complete");
        }
        
        BowlingFrame currentFrame = getCurrentFrame(game);
        if (currentFrame == null) {
            throw new IllegalStateException("Game is already complete");
        }
        
        // Enregistrer le lancer
        if (currentFrame.getFirstRoll() == null) {
            currentFrame.setFirstRoll(pins);
            if (pins == 10) { // Strike
                currentFrame.setStrike(true);
                currentFrame.setComplete(true);
            }
        } else if (currentFrame.getSecondRoll() == null) {
            currentFrame.setSecondRoll(pins);
            if (currentFrame.getFirstRoll() + pins == 10) { // Spare
                currentFrame.setSpare(true);
            }
            currentFrame.setComplete(true);
        } else if (currentFrame.getFrameNumber() == 10) { // 10ème frame spéciale
            currentFrame.setThirdRoll(pins);
            currentFrame.setComplete(true);
        }
        
        // Mettre à jour le score
        updateScores(game);
        
        // Vérifier si la partie est terminée
        if (game.getFrames().stream().allMatch(BowlingFrame::isComplete)) {
            game.setComplete(true);
        }
    }
    
    public BowlingGame resetGame(BowlingGame game) {
        game.setScore(0);
        game.setComplete(false);
        
        // Réinitialiser les frames
        List<BowlingFrame> frames = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            BowlingFrame frame = new BowlingFrame();
            frame.setFrameNumber(i + 1);
            frame.setComplete(false);
            frame.setStrike(false);
            frame.setSpare(false);
            frame.setFirstRoll(null);
            frame.setSecondRoll(null);
            frame.setThirdRoll(null);
            frames.add(frame);
        }
        game.setFrames(frames);
        
        return game;
    }
    
    private BowlingFrame getCurrentFrame(BowlingGame game) {
        return game.getFrames().stream()
                .filter(f -> !f.isComplete())
                .findFirst()
                .orElse(null);
    }
    
    private void updateScores(BowlingGame game) {
        List<BowlingFrame> frames = game.getFrames();
        int score = 0;
        
        for (int i = 0; i < frames.size(); i++) {
            BowlingFrame frame = frames.get(i);
            
            // Score de base pour cette frame
            int frameScore = getFramePins(frame);
            
            // Bonus pour les strikes et spares
            if (frame.isStrike() && i < 9) {
                // Bonus pour un strike: ajoute les 2 prochains lancers
                frameScore += getStrikeBonus(frames, i);
            } else if (frame.isSpare() && i < 9) {
                // Bonus pour un spare: ajoute le prochain lancer
                frameScore += getSpareBonus(frames, i);
            }
            
            // Mettre à jour le score cumulé
            score += frameScore;
            frame.setFrameScore(score);
        }
        
        game.setScore(score);
    }
    
    private int getFramePins(BowlingFrame frame) {
        int pins = 0;
        if (frame.getFirstRoll() != null) pins += frame.getFirstRoll();
        if (frame.getSecondRoll() != null) pins += frame.getSecondRoll();
        if (frame.getThirdRoll() != null) pins += frame.getThirdRoll();
        return pins;
    }
    
    private int getStrikeBonus(List<BowlingFrame> frames, int currentIndex) {
        int bonus = 0;
        int nextIndex = currentIndex + 1;
        
        if (nextIndex < frames.size()) {
            BowlingFrame nextFrame = frames.get(nextIndex);
            // Premier lancer de la frame suivante
            if (nextFrame.getFirstRoll() != null) {
                bonus += nextFrame.getFirstRoll();
                // Deuxième lancer (peut être dans la même frame ou la suivante)
                if (nextFrame.getSecondRoll() != null) {
                    bonus += nextFrame.getSecondRoll();
                } else if (nextIndex + 1 < frames.size() && nextFrame.isStrike()) {
                    // Si c'était un strike, on prend le premier lancer de la frame d'après
                    BowlingFrame nextNextFrame = frames.get(nextIndex + 1);
                    if (nextNextFrame.getFirstRoll() != null) {
                        bonus += nextNextFrame.getFirstRoll();
                    }
                }
            }
        }
        
        return bonus;
    }
    
    private int getSpareBonus(List<BowlingFrame> frames, int currentIndex) {
        int bonus = 0;
        int nextIndex = currentIndex + 1;
        
        if (nextIndex < frames.size()) {
            BowlingFrame nextFrame = frames.get(nextIndex);
            if (nextFrame.getFirstRoll() != null) {
                bonus += nextFrame.getFirstRoll();
            }
        }
        
        return bonus;
    }
}

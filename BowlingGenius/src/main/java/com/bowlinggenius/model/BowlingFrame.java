package com.bowlinggenius.model;

import lombok.Data;

@Data
public class BowlingFrame {
    private int frameNumber;
    private Integer firstRoll;
    private Integer secondRoll;
    private Integer thirdRoll; // Pour le 10ème frame en cas de strike ou spare
    private int frameScore;
    private boolean strike = false;
    private boolean spare = false;
    private boolean complete = false;
    
    public boolean isComplete() {
        if (frameNumber < 10) {
            return strike || (firstRoll != null && (secondRoll != null || firstRoll == 10));
        } else {
            // Pour le 10ème frame
            if (firstRoll != null && firstRoll == 10) { // Strike au premier lancer
                return secondRoll != null && thirdRoll != null;
            } else if (firstRoll != null && secondRoll != null && (firstRoll + secondRoll == 10)) { // Spare
                return thirdRoll != null;
            } else {
                return firstRoll != null && secondRoll != null;
            }
        }
    }
    
    public int getPinsKnockedDown() {
        int pins = 0;
        if (firstRoll != null) pins += firstRoll;
        if (secondRoll != null) pins += secondRoll;
        if (thirdRoll != null) pins += thirdRoll;
        return pins;
    }
    
    @Override
    public String toString() {
        return String.format("Frame{number=%d, rolls=[%s, %s, %s], score=%d, strike=%b, spare=%b, complete=%b}",
                frameNumber,
                firstRoll != null ? firstRoll : "-",
                secondRoll != null ? secondRoll : "-",
                thirdRoll != null ? thirdRoll : "-",
                frameScore,
                strike,
                spare,
                isComplete());
    }
}

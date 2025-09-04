package com.bowlinggenius.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;

@Data
public class BowlingGame {
    private Long id;
    private String playerName;
    private LocalDateTime gameDate;
    private int score;
    private boolean complete;
    private List<BowlingFrame> frames;
    
    public BowlingGame() {
        this.gameDate = LocalDateTime.now();
        this.frames = new ArrayList<>();
    }
    
    public BowlingFrame getCurrentFrame() {
        if (frames == null) {
            return null;
        }
        return frames.stream()
                .filter(frame -> !frame.isComplete())
                .findFirst()
                .orElse(null);
    }
    
    @Override
    public String toString() {
        return "BowlingGame{" +
                "id=" + id +
                ", playerName='" + playerName + '\'' +
                ", gameDate=" + (gameDate != null ? gameDate.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) : "null") +
                ", score=" + score +
                ", complete=" + complete +
                ", frames=" + (frames != null ? frames.stream().map(BowlingFrame::toString).collect(Collectors.joining(", ")) : "null") +
                '}';
    }
}

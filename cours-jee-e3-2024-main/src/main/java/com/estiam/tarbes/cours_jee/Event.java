package com.estiam.tarbes.cours_jee;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity @Data
public class Event {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotEmpty
    private String name;

    @NotNull
    private LocalDate date;

    @NotEmpty
    private String description;

    @ManyToMany(mappedBy = "games")
    private List<User> participants = new ArrayList<>();
}

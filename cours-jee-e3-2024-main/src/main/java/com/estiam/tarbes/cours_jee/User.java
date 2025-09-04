package com.estiam.tarbes.cours_jee;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data @Entity(name = "users")
public class User {
    
    @Id
    private Long id;

    @Column @NotEmpty
    private String name;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @Column
    private String telephon;

    @OneToOne
    private Event birthday;
    
    @OneToMany
    private List<Event> appointments = new ArrayList<>();

    @ManyToMany
    private List<Event> games = new ArrayList<>();

    public User() {
        this.id = (long) (Math.random() * 1000);
    }

    public User(String name, String email, String password) {
        this();
        this.name = name;
        this.email = email;
        this.password = password;
    }
}

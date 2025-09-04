package com.Estiam.Cours.J2E;

public class User {
    private static int nextId = 1;
    private int id;
    private String nom;
    private String email;
    private String telephone;
    private String adresse;

    public User() {
        this.id = nextId++;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
}

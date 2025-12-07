package com.esport.model;

import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "Player")
@XmlAccessorType(XmlAccessType.FIELD)
public class Player {
    
    @XmlElement(required = true)
    private int id;
    
    @XmlElement(required = true)
    private String pseudo;
    
    @XmlElement(required = true)
    private String email;
    
    @XmlElement(required = true)
    private String rank;
    
    @XmlElement
    private int score;
    
    // Constructeurs
    public Player() {
        this.score = 0;
    }
    
    public Player(int id, String pseudo, String email, String rank) {
        this.id = id;
        this.pseudo = pseudo;
        this.email = email;
        this.rank = rank;
        this.score = 0;
    }
    
    // Getters et Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getPseudo() {
        return pseudo;
    }
    
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getRank() {
        return rank;
    }
    
    public void setRank(String rank) {
        this.rank = rank;
    }
    
    public int getScore() {
        return score;
    }
    
    public void setScore(int score) {
        this.score = score;
    }
    
    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", pseudo='" + pseudo + '\'' +
                ", rank='" + rank + '\'' +
                ", score=" + score +
                '}';
    }
}
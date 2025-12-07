package com.esport.model;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teams")
@XmlRootElement(name = "Team")
@XmlAccessorType(XmlAccessType.FIELD)
public class Team {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlElement(required = true)
    private int id;
    
    @Column(nullable = false)
    @XmlElement(required = true)
    private String name;
    
    @Transient
    @XmlElement
    private List<Player> players;
    
    @Column(nullable = false)
    @XmlElement
    private int wins;
    
    @Column(nullable = false)
    @XmlElement
    private int losses;
    
    // Constructeurs
    public Team() {
        this.players = new ArrayList<>();
        this.wins = 0;
        this.losses = 0;
    }
    
    public Team(int id, String name) {
        this();
        this.id = id;
        this.name = name;
    }
    
    // Getters et Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public List<Player> getPlayers() {
        return players;
    }
    
    public void setPlayers(List<Player> players) {
        this.players = players;
    }
    
    public void addPlayer(Player player) {
        this.players.add(player);
    }
    
    public int getWins() {
        return wins;
    }
    
    public void setWins(int wins) {
        this.wins = wins;
    }
    
    public int getLosses() {
        return losses;
    }
    
    public void setLosses(int losses) {
        this.losses = losses;
    }
    
    public void incrementWins() {
        this.wins++;
    }
    
    public void incrementLosses() {
        this.losses++;
    }
    
    public int getTotalMatches() {
        return wins + losses;
    }
    
    public double getWinRate() {
        int total = getTotalMatches();
        return total > 0 ? (double) wins / total * 100 : 0.0;
    }
    
    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", wins=" + wins +
                ", losses=" + losses +
                ", winRate=" + String.format("%.1f%%", getWinRate()) +
                '}';
    }
}
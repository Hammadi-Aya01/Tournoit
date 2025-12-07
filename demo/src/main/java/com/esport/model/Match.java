package com.esport.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "matches")
public class Match {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(nullable = false)
    private int team1Id;
    
    @Column(nullable = false)
    private int team2Id;
    
    @Column(nullable = false)
    private int scoreTeam1;
    
    @Column(nullable = false)
    private int scoreTeam2;
    
    @Column(nullable = false)
    private LocalDateTime matchDate;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MatchStatus status;
    
    // Constructeurs
    public Match() {
        this.scoreTeam1 = 0;
        this.scoreTeam2 = 0;
        this.status = MatchStatus.SCHEDULED;
        this.matchDate = LocalDateTime.now();
    }
    
    public Match(int team1Id, int team2Id, LocalDateTime matchDate) {
        this();
        this.team1Id = team1Id;
        this.team2Id = team2Id;
        this.matchDate = matchDate;
    }
    
    // Getters et Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getTeam1Id() {
        return team1Id;
    }
    
    public void setTeam1Id(int team1Id) {
        this.team1Id = team1Id;
    }
    
    public int getTeam2Id() {
        return team2Id;
    }
    
    public void setTeam2Id(int team2Id) {
        this.team2Id = team2Id;
    }
    
    public int getScoreTeam1() {
        return scoreTeam1;
    }
    
    public void setScoreTeam1(int scoreTeam1) {
        this.scoreTeam1 = scoreTeam1;
    }
    
    public int getScoreTeam2() {
        return scoreTeam2;
    }
    
    public void setScoreTeam2(int scoreTeam2) {
        this.scoreTeam2 = scoreTeam2;
    }
    
    public LocalDateTime getMatchDate() {
        return matchDate;
    }
    
    public void setMatchDate(LocalDateTime matchDate) {
        this.matchDate = matchDate;
    }
    
    public MatchStatus getStatus() {
        return status;
    }
    
    public void setStatus(MatchStatus status) {
        this.status = status;
    }
    
    public int getWinnerId() {
        if (status != MatchStatus.COMPLETED) return -1;
        if (scoreTeam1 > scoreTeam2) return team1Id;
        if (scoreTeam2 > scoreTeam1) return team2Id;
        return 0; // Draw
    }
    
    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", team1=" + team1Id +
                " vs team2=" + team2Id +
                ", score=" + scoreTeam1 + "-" + scoreTeam2 +
                ", status=" + status +
                '}';
    }
}
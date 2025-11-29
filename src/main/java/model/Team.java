package model;

import org.bson.types.ObjectId;
import java.util.ArrayList;
import java.util.List;

public class Team {
    private ObjectId id;
    private String name;
    private String coach;
    private List<Player> players;
    private int wins;
    private int losses;
    private int draws;
    private int goalsScored;
    private int goalsConceded;
    
    public Team() {
        this.players = new ArrayList<>();
        this.wins = 0;
        this.losses = 0;
        this.draws = 0;
        this.goalsScored = 0;
        this.goalsConceded = 0;
    }
    
    public Team(String name, String coach) {
        this();
        this.name = name;
        this.coach = coach;
    }
    
    public int getPoints() {
        return (wins * 3) + draws;
    }
    
    public int getGoalDifference() {
        return goalsScored - goalsConceded;
    }
    
    // Getters and Setters
    public ObjectId getId() { return id; }
    public void setId(ObjectId id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getCoach() { return coach; }
    public void setCoach(String coach) { this.coach = coach; }
    
    public List<Player> getPlayers() { return players; }
    public void setPlayers(List<Player> players) { this.players = players; }
    
    public int getWins() { return wins; }
    public void setWins(int wins) { this.wins = wins; }
    
    public int getLosses() { return losses; }
    public void setLosses(int losses) { this.losses = losses; }
    
    public int getDraws() { return draws; }
    public void setDraws(int draws) { this.draws = draws; }
    
    public int getGoalsScored() { return goalsScored; }
    public void setGoalsScored(int goalsScored) { this.goalsScored = goalsScored; }
    
    public int getGoalsConceded() { return goalsConceded; }
    public void setGoalsConceded(int goalsConceded) { this.goalsConceded = goalsConceded; }
}

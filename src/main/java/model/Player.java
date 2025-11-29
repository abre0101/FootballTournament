package model;

import org.bson.types.ObjectId;

public class Player {
    private ObjectId id;
    private String name;
    private int jerseyNumber;
    private String position;
    private int goals;
    private int assists;
    private int yellowCards;
    private int redCards;
    private ObjectId teamId;
    
    public Player() {
        this.goals = 0;
        this.assists = 0;
        this.yellowCards = 0;
        this.redCards = 0;
    }
    
    public Player(String name, int jerseyNumber, String position) {
        this();
        this.name = name;
        this.jerseyNumber = jerseyNumber;
        this.position = position;
    }
    
    // Getters and Setters
    public ObjectId getId() { return id; }
    public void setId(ObjectId id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public int getJerseyNumber() { return jerseyNumber; }
    public void setJerseyNumber(int jerseyNumber) { this.jerseyNumber = jerseyNumber; }
    
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
    
    public int getGoals() { return goals; }
    public void setGoals(int goals) { this.goals = goals; }
    
    public int getAssists() { return assists; }
    public void setAssists(int assists) { this.assists = assists; }
    
    public int getYellowCards() { return yellowCards; }
    public void setYellowCards(int yellowCards) { this.yellowCards = yellowCards; }
    
    public int getRedCards() { return redCards; }
    public void setRedCards(int redCards) { this.redCards = redCards; }
    
    public ObjectId getTeamId() { return teamId; }
    public void setTeamId(ObjectId teamId) { this.teamId = teamId; }
}

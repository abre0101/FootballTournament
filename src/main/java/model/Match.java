package model;

import org.bson.types.ObjectId;
import java.util.Date;

public class Match {
    private ObjectId id;
    private ObjectId tournamentId;
    private ObjectId homeTeamId;
    private ObjectId awayTeamId;
    private String homeTeamName;
    private String awayTeamName;
    private int homeScore;
    private int awayScore;
    private Date matchDate;
    private String venue;
    private String status; // SCHEDULED, ONGOING, COMPLETED, CANCELLED
    
    public Match() {
        this.homeScore = 0;
        this.awayScore = 0;
        this.status = "SCHEDULED";
    }
    
    public Match(ObjectId tournamentId, ObjectId homeTeamId, ObjectId awayTeamId, Date matchDate, String venue) {
        this();
        this.tournamentId = tournamentId;
        this.homeTeamId = homeTeamId;
        this.awayTeamId = awayTeamId;
        this.matchDate = matchDate;
        this.venue = venue;
    }
    
    // Getters and Setters
    public ObjectId getId() { return id; }
    public void setId(ObjectId id) { this.id = id; }
    
    public ObjectId getTournamentId() { return tournamentId; }
    public void setTournamentId(ObjectId tournamentId) { this.tournamentId = tournamentId; }
    
    public ObjectId getHomeTeamId() { return homeTeamId; }
    public void setHomeTeamId(ObjectId homeTeamId) { this.homeTeamId = homeTeamId; }
    
    public ObjectId getAwayTeamId() { return awayTeamId; }
    public void setAwayTeamId(ObjectId awayTeamId) { this.awayTeamId = awayTeamId; }
    
    public String getHomeTeamName() { return homeTeamName; }
    public void setHomeTeamName(String homeTeamName) { this.homeTeamName = homeTeamName; }
    
    public String getAwayTeamName() { return awayTeamName; }
    public void setAwayTeamName(String awayTeamName) { this.awayTeamName = awayTeamName; }
    
    public int getHomeScore() { return homeScore; }
    public void setHomeScore(int homeScore) { this.homeScore = homeScore; }
    
    public int getAwayScore() { return awayScore; }
    public void setAwayScore(int awayScore) { this.awayScore = awayScore; }
    
    public Date getMatchDate() { return matchDate; }
    public void setMatchDate(Date matchDate) { this.matchDate = matchDate; }
    
    public String getVenue() { return venue; }
    public void setVenue(String venue) { this.venue = venue; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

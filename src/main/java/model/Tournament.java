package model;

import org.bson.types.ObjectId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Tournament {
    private ObjectId id;
    private String name;
    private Date startDate;
    private Date endDate;
    private String format; // LEAGUE, KNOCKOUT, GROUP_STAGE
    private String status; // UPCOMING, ONGOING, COMPLETED
    private List<ObjectId> teamIds;
    
    public Tournament() {
        this.teamIds = new ArrayList<>();
        this.status = "UPCOMING";
    }
    
    public Tournament(String name, Date startDate, Date endDate, String format) {
        this();
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.format = format;
    }
    
    // Getters and Setters
    public ObjectId getId() { return id; }
    public void setId(ObjectId id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }
    
    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }
    
    public String getFormat() { return format; }
    public void setFormat(String format) { this.format = format; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public List<ObjectId> getTeamIds() { return teamIds; }
    public void setTeamIds(List<ObjectId> teamIds) { this.teamIds = teamIds; }
}

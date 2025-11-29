package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import config.MongoDBConnection;
import model.Team;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.ArrayList;
import java.util.List;

public class TeamDAO {
    private MongoCollection<Document> collection;
    
    public TeamDAO() {
        MongoDatabase database = MongoDBConnection.getDatabase();
        this.collection = database.getCollection("teams");
    }
    
    public void createTeam(Team team) {
        Document doc = new Document("name", team.getName())
            .append("coach", team.getCoach())
            .append("wins", team.getWins())
            .append("losses", team.getLosses())
            .append("draws", team.getDraws())
            .append("goalsScored", team.getGoalsScored())
            .append("goalsConceded", team.getGoalsConceded());
        collection.insertOne(doc);
        team.setId(doc.getObjectId("_id"));
    }
    
    public Team getTeamById(ObjectId id) {
        Document doc = collection.find(new Document("_id", id)).first();
        return documentToTeam(doc);
    }
    
    public List<Team> getAllTeams() {
        List<Team> teams = new ArrayList<>();
        for (Document doc : collection.find()) {
            teams.add(documentToTeam(doc));
        }
        return teams;
    }
    
    public void updateTeam(Team team) {
        Document doc = new Document("name", team.getName())
            .append("coach", team.getCoach())
            .append("wins", team.getWins())
            .append("losses", team.getLosses())
            .append("draws", team.getDraws())
            .append("goalsScored", team.getGoalsScored())
            .append("goalsConceded", team.getGoalsConceded());
        collection.updateOne(new Document("_id", team.getId()), new Document("$set", doc));
    }
    
    public void deleteTeam(ObjectId id) {
        collection.deleteOne(new Document("_id", id));
    }
    
    private Team documentToTeam(Document doc) {
        if (doc == null) return null;
        Team team = new Team();
        team.setId(doc.getObjectId("_id"));
        team.setName(doc.getString("name"));
        team.setCoach(doc.getString("coach"));
        team.setWins(doc.getInteger("wins", 0));
        team.setLosses(doc.getInteger("losses", 0));
        team.setDraws(doc.getInteger("draws", 0));
        team.setGoalsScored(doc.getInteger("goalsScored", 0));
        team.setGoalsConceded(doc.getInteger("goalsConceded", 0));
        return team;
    }
}

package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import config.MongoDBConnection;
import model.Match;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.ArrayList;
import java.util.List;

public class MatchDAO {
    private MongoCollection<Document> collection;
    
    public MatchDAO() {
        MongoDatabase database = MongoDBConnection.getDatabase();
        this.collection = database.getCollection("matches");
    }
    
    public void createMatch(Match match) {
        Document doc = new Document("tournamentId", match.getTournamentId())
            .append("homeTeamId", match.getHomeTeamId())
            .append("awayTeamId", match.getAwayTeamId())
            .append("homeTeamName", match.getHomeTeamName())
            .append("awayTeamName", match.getAwayTeamName())
            .append("homeScore", match.getHomeScore())
            .append("awayScore", match.getAwayScore())
            .append("matchDate", match.getMatchDate())
            .append("venue", match.getVenue())
            .append("status", match.getStatus());
        collection.insertOne(doc);
        match.setId(doc.getObjectId("_id"));
    }
    
    public Match getMatchById(ObjectId id) {
        Document doc = collection.find(new Document("_id", id)).first();
        return documentToMatch(doc);
    }
    
    public List<Match> getMatchesByTournament(ObjectId tournamentId) {
        List<Match> matches = new ArrayList<>();
        for (Document doc : collection.find(new Document("tournamentId", tournamentId))) {
            matches.add(documentToMatch(doc));
        }
        return matches;
    }
    
    public List<Match> getAllMatches() {
        List<Match> matches = new ArrayList<>();
        for (Document doc : collection.find()) {
            matches.add(documentToMatch(doc));
        }
        return matches;
    }
    
    public void updateMatch(Match match) {
        Document doc = new Document("tournamentId", match.getTournamentId())
            .append("homeTeamId", match.getHomeTeamId())
            .append("awayTeamId", match.getAwayTeamId())
            .append("homeTeamName", match.getHomeTeamName())
            .append("awayTeamName", match.getAwayTeamName())
            .append("homeScore", match.getHomeScore())
            .append("awayScore", match.getAwayScore())
            .append("matchDate", match.getMatchDate())
            .append("venue", match.getVenue())
            .append("status", match.getStatus());
        collection.updateOne(new Document("_id", match.getId()), new Document("$set", doc));
    }
    
    public void deleteMatch(ObjectId id) {
        collection.deleteOne(new Document("_id", id));
    }
    
    private Match documentToMatch(Document doc) {
        if (doc == null) return null;
        Match match = new Match();
        match.setId(doc.getObjectId("_id"));
        match.setTournamentId(doc.getObjectId("tournamentId"));
        match.setHomeTeamId(doc.getObjectId("homeTeamId"));
        match.setAwayTeamId(doc.getObjectId("awayTeamId"));
        match.setHomeTeamName(doc.getString("homeTeamName"));
        match.setAwayTeamName(doc.getString("awayTeamName"));
        match.setHomeScore(doc.getInteger("homeScore", 0));
        match.setAwayScore(doc.getInteger("awayScore", 0));
        match.setMatchDate(doc.getDate("matchDate"));
        match.setVenue(doc.getString("venue"));
        match.setStatus(doc.getString("status"));
        return match;
    }
}

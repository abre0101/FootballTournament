package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import config.MongoDBConnection;
import model.Tournament;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.ArrayList;
import java.util.List;

public class TournamentDAO {
    private MongoCollection<Document> collection;
    
    public TournamentDAO() {
        MongoDatabase database = MongoDBConnection.getDatabase();
        this.collection = database.getCollection("tournaments");
    }
    
    public void createTournament(Tournament tournament) {
        Document doc = new Document("name", tournament.getName())
            .append("startDate", tournament.getStartDate())
            .append("endDate", tournament.getEndDate())
            .append("format", tournament.getFormat())
            .append("status", tournament.getStatus())
            .append("teamIds", tournament.getTeamIds());
        collection.insertOne(doc);
        tournament.setId(doc.getObjectId("_id"));
    }
    
    public Tournament getTournamentById(ObjectId id) {
        Document doc = collection.find(new Document("_id", id)).first();
        return documentToTournament(doc);
    }
    
    public List<Tournament> getAllTournaments() {
        List<Tournament> tournaments = new ArrayList<>();
        for (Document doc : collection.find()) {
            tournaments.add(documentToTournament(doc));
        }
        return tournaments;
    }
    
    public void updateTournament(Tournament tournament) {
        Document doc = new Document("name", tournament.getName())
            .append("startDate", tournament.getStartDate())
            .append("endDate", tournament.getEndDate())
            .append("format", tournament.getFormat())
            .append("status", tournament.getStatus())
            .append("teamIds", tournament.getTeamIds());
        collection.updateOne(new Document("_id", tournament.getId()), new Document("$set", doc));
    }
    
    public void deleteTournament(ObjectId id) {
        collection.deleteOne(new Document("_id", id));
    }
    
    @SuppressWarnings("unchecked")
    private Tournament documentToTournament(Document doc) {
        if (doc == null) return null;
        Tournament tournament = new Tournament();
        tournament.setId(doc.getObjectId("_id"));
        tournament.setName(doc.getString("name"));
        tournament.setStartDate(doc.getDate("startDate"));
        tournament.setEndDate(doc.getDate("endDate"));
        tournament.setFormat(doc.getString("format"));
        tournament.setStatus(doc.getString("status"));
        tournament.setTeamIds((List<ObjectId>) doc.get("teamIds"));
        return tournament;
    }
}

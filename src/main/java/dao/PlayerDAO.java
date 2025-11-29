package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import config.MongoDBConnection;
import model.Player;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.ArrayList;
import java.util.List;

public class PlayerDAO {
    private MongoCollection<Document> collection;
    
    public PlayerDAO() {
        MongoDatabase database = MongoDBConnection.getDatabase();
        this.collection = database.getCollection("players");
    }
    
    public void createPlayer(Player player) {
        Document doc = new Document("name", player.getName())
            .append("jerseyNumber", player.getJerseyNumber())
            .append("position", player.getPosition())
            .append("goals", player.getGoals())
            .append("assists", player.getAssists())
            .append("yellowCards", player.getYellowCards())
            .append("redCards", player.getRedCards())
            .append("teamId", player.getTeamId());
        collection.insertOne(doc);
        player.setId(doc.getObjectId("_id"));
    }
    
    public Player getPlayerById(ObjectId id) {
        Document doc = collection.find(new Document("_id", id)).first();
        return documentToPlayer(doc);
    }
    
    public List<Player> getPlayersByTeam(ObjectId teamId) {
        List<Player> players = new ArrayList<>();
        for (Document doc : collection.find(new Document("teamId", teamId))) {
            players.add(documentToPlayer(doc));
        }
        return players;
    }
    
    public List<Player> getAllPlayers() {
        List<Player> players = new ArrayList<>();
        for (Document doc : collection.find()) {
            players.add(documentToPlayer(doc));
        }
        return players;
    }
    
    public void updatePlayer(Player player) {
        Document doc = new Document("name", player.getName())
            .append("jerseyNumber", player.getJerseyNumber())
            .append("position", player.getPosition())
            .append("goals", player.getGoals())
            .append("assists", player.getAssists())
            .append("yellowCards", player.getYellowCards())
            .append("redCards", player.getRedCards())
            .append("teamId", player.getTeamId());
        collection.updateOne(new Document("_id", player.getId()), new Document("$set", doc));
    }
    
    public void deletePlayer(ObjectId id) {
        collection.deleteOne(new Document("_id", id));
    }
    
    public List<Player> getTopScorers(int limit) {
        List<Player> players = getAllPlayers();
        players.sort((a, b) -> Integer.compare(b.getGoals(), a.getGoals()));
        return players.subList(0, Math.min(limit, players.size()));
    }
    
    private Player documentToPlayer(Document doc) {
        if (doc == null) return null;
        Player player = new Player();
        player.setId(doc.getObjectId("_id"));
        player.setName(doc.getString("name"));
        player.setJerseyNumber(doc.getInteger("jerseyNumber", 0));
        player.setPosition(doc.getString("position"));
        player.setGoals(doc.getInteger("goals", 0));
        player.setAssists(doc.getInteger("assists", 0));
        player.setYellowCards(doc.getInteger("yellowCards", 0));
        player.setRedCards(doc.getInteger("redCards", 0));
        player.setTeamId(doc.getObjectId("teamId"));
        return player;
    }
}

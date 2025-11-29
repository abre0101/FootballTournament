package service;

import dao.MatchDAO;
import dao.TeamDAO;
import dao.TournamentDAO;
import model.Match;
import model.Team;
import model.Tournament;
import org.bson.types.ObjectId;
import java.util.Comparator;
import java.util.List;

public class TournamentService {
    private TournamentDAO tournamentDAO;
    private TeamDAO teamDAO;
    private MatchDAO matchDAO;
    
    public TournamentService() {
        this.tournamentDAO = new TournamentDAO();
        this.teamDAO = new TeamDAO();
        this.matchDAO = new MatchDAO();
    }
    
    public void createTournament(Tournament tournament) {
        tournamentDAO.createTournament(tournament);
    }
    
    public void addTeamToTournament(ObjectId tournamentId, ObjectId teamId) {
        Tournament tournament = tournamentDAO.getTournamentById(tournamentId);
        if (tournament != null && !tournament.getTeamIds().contains(teamId)) {
            tournament.getTeamIds().add(teamId);
            tournamentDAO.updateTournament(tournament);
        }
    }
    
    public void scheduleMatch(Match match) {
        matchDAO.createMatch(match);
    }
    
    public void updateMatchResult(ObjectId matchId, int homeScore, int awayScore) {
        Match match = matchDAO.getMatchById(matchId);
        if (match != null) {
            match.setHomeScore(homeScore);
            match.setAwayScore(awayScore);
            match.setStatus("COMPLETED");
            matchDAO.updateMatch(match);
            
            // Update team statistics
            Team homeTeam = teamDAO.getTeamById(match.getHomeTeamId());
            Team awayTeam = teamDAO.getTeamById(match.getAwayTeamId());
            
            if (homeTeam != null && awayTeam != null) {
                homeTeam.setGoalsScored(homeTeam.getGoalsScored() + homeScore);
                homeTeam.setGoalsConceded(homeTeam.getGoalsConceded() + awayScore);
                awayTeam.setGoalsScored(awayTeam.getGoalsScored() + awayScore);
                awayTeam.setGoalsConceded(awayTeam.getGoalsConceded() + homeScore);
                
                if (homeScore > awayScore) {
                    homeTeam.setWins(homeTeam.getWins() + 1);
                    awayTeam.setLosses(awayTeam.getLosses() + 1);
                } else if (awayScore > homeScore) {
                    awayTeam.setWins(awayTeam.getWins() + 1);
                    homeTeam.setLosses(homeTeam.getLosses() + 1);
                } else {
                    homeTeam.setDraws(homeTeam.getDraws() + 1);
                    awayTeam.setDraws(awayTeam.getDraws() + 1);
                }
                
                teamDAO.updateTeam(homeTeam);
                teamDAO.updateTeam(awayTeam);
            }
        }
    }
    
    public List<Team> getStandings(ObjectId tournamentId) {
        Tournament tournament = tournamentDAO.getTournamentById(tournamentId);
        List<Team> teams = teamDAO.getAllTeams();
        
        teams.sort(Comparator
            .comparingInt(Team::getPoints).reversed()
            .thenComparingInt(Team::getGoalDifference).reversed()
            .thenComparingInt(Team::getGoalsScored).reversed());
        
        return teams;
    }
    
    public List<Match> getTournamentMatches(ObjectId tournamentId) {
        return matchDAO.getMatchesByTournament(tournamentId);
    }
    
    public Tournament getTournamentById(ObjectId id) {
        return tournamentDAO.getTournamentById(id);
    }
    
    public List<Tournament> getAllTournaments() {
        return tournamentDAO.getAllTournaments();
    }
}

import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.bson.types.ObjectId;
import com.google.gson.Gson;
import model.*;
import service.TournamentService;
import dao.*;

@WebServlet("/api/*")
public class FootballTournamentManager extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TournamentService tournamentService;
    private TeamDAO teamDAO;
    private PlayerDAO playerDAO;
    private Gson gson;
    
    public FootballTournamentManager() {
        super();
        this.tournamentService = new TournamentService();
        this.teamDAO = new TeamDAO();
        this.playerDAO = new PlayerDAO();
        this.gson = new Gson();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        String pathInfo = request.getPathInfo();
        
        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                response.getWriter().write("{\"message\":\"Football Tournament Management API\"}");
            } else if (pathInfo.equals("/tournaments")) {
                List<Tournament> tournaments = tournamentService.getAllTournaments();
                response.getWriter().write(gson.toJson(tournaments));
            } else if (pathInfo.startsWith("/tournament/")) {
                String id = pathInfo.substring(12);
                Tournament tournament = tournamentService.getTournamentById(new ObjectId(id));
                response.getWriter().write(gson.toJson(tournament));
            } else if (pathInfo.equals("/teams")) {
                List<Team> teams = teamDAO.getAllTeams();
                response.getWriter().write(gson.toJson(teams));
            } else if (pathInfo.startsWith("/team/")) {
                String id = pathInfo.substring(6);
                Team team = teamDAO.getTeamById(new ObjectId(id));
                response.getWriter().write(gson.toJson(team));
            } else if (pathInfo.equals("/players")) {
                List<Player> players = playerDAO.getAllPlayers();
                response.getWriter().write(gson.toJson(players));
            } else if (pathInfo.startsWith("/players/team/")) {
                String teamId = pathInfo.substring(14);
                List<Player> players = playerDAO.getPlayersByTeam(new ObjectId(teamId));
                response.getWriter().write(gson.toJson(players));
            } else if (pathInfo.equals("/players/topscorers")) {
                List<Player> topScorers = playerDAO.getTopScorers(10);
                response.getWriter().write(gson.toJson(topScorers));
            } else if (pathInfo.startsWith("/standings/")) {
                String tournamentId = pathInfo.substring(11);
                List<Team> standings = tournamentService.getStandings(new ObjectId(tournamentId));
                response.getWriter().write(gson.toJson(standings));
            } else if (pathInfo.equals("/matches")) {
                MatchDAO matchDAO = new MatchDAO();
                List<Match> matches = matchDAO.getAllMatches();
                response.getWriter().write(gson.toJson(matches));
            } else if (pathInfo.startsWith("/matches/")) {
                String tournamentId = pathInfo.substring(9);
                List<Match> matches = tournamentService.getTournamentMatches(new ObjectId(tournamentId));
                response.getWriter().write(gson.toJson(matches));
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\":\"Endpoint not found\"}");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        String pathInfo = request.getPathInfo();
        
        try {
            if (pathInfo.equals("/tournament")) {
                String name = request.getParameter("name");
                String format = request.getParameter("format");
                Tournament tournament = new Tournament(name, new Date(), null, format);
                tournamentService.createTournament(tournament);
                response.getWriter().write(gson.toJson(tournament));
            } else if (pathInfo.equals("/team")) {
                String name = request.getParameter("name");
                String coach = request.getParameter("coach");
                Team team = new Team(name, coach);
                teamDAO.createTeam(team);
                response.getWriter().write(gson.toJson(team));
            } else if (pathInfo.equals("/match")) {
                String tournamentIdStr = request.getParameter("tournamentId");
                String homeTeamIdStr = request.getParameter("homeTeamId");
                String awayTeamIdStr = request.getParameter("awayTeamId");
                String homeTeamName = request.getParameter("homeTeamName");
                String awayTeamName = request.getParameter("awayTeamName");
                String venue = request.getParameter("venue");
                String matchDateStr = request.getParameter("matchDate");
                
                // Find the actual ObjectIds from the teams
                Team homeTeam = null;
                Team awayTeam = null;
                Tournament tournament = null;
                
                for (Team t : teamDAO.getAllTeams()) {
                    if (t.getId().getTimestamp() == Integer.parseInt(homeTeamIdStr)) {
                        homeTeam = t;
                    }
                    if (t.getId().getTimestamp() == Integer.parseInt(awayTeamIdStr)) {
                        awayTeam = t;
                    }
                }
                
                for (Tournament tr : tournamentService.getAllTournaments()) {
                    if (tr.getId().getTimestamp() == Integer.parseInt(tournamentIdStr)) {
                        tournament = tr;
                    }
                }
                
                if (homeTeam == null || awayTeam == null || tournament == null) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("{\"error\":\"Invalid team or tournament ID\"}");
                    return;
                }
                
                Match match = new Match();
                match.setTournamentId(tournament.getId());
                match.setHomeTeamId(homeTeam.getId());
                match.setAwayTeamId(awayTeam.getId());
                match.setHomeTeamName(homeTeamName);
                match.setAwayTeamName(awayTeamName);
                match.setVenue(venue);
                
                // Parse date
                try {
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                    match.setMatchDate(sdf.parse(matchDateStr));
                } catch (Exception e) {
                    match.setMatchDate(new Date());
                }
                
                match.setStatus("SCHEDULED");
                
                tournamentService.scheduleMatch(match);
                response.getWriter().write(gson.toJson(match));
            } else if (pathInfo.equals("/player")) {
                String name = request.getParameter("name");
                int jerseyNumber = Integer.parseInt(request.getParameter("jerseyNumber"));
                String position = request.getParameter("position");
                String teamId = request.getParameter("teamId");
                
                Player player = new Player(name, jerseyNumber, position);
                player.setTeamId(new ObjectId(teamId));
                playerDAO.createPlayer(player);
                response.getWriter().write(gson.toJson(player));
            } else if (pathInfo.equals("/match/result")) {
                String matchId = request.getParameter("matchId");
                int homeScore = Integer.parseInt(request.getParameter("homeScore"));
                int awayScore = Integer.parseInt(request.getParameter("awayScore"));
                tournamentService.updateMatchResult(new ObjectId(matchId), homeScore, awayScore);
                response.getWriter().write("{\"message\":\"Match result updated\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\":\"Endpoint not found\"}");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}

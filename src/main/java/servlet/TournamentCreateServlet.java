package servlet;

import com.google.gson.Gson;
import model.Tournament;
import service.TournamentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/tournament/create")
public class TournamentCreateServlet extends HttpServlet {
    private TournamentService tournamentService;
    private SimpleDateFormat dateFormat;
    
    @Override
    public void init() throws ServletException {
        tournamentService = new TournamentService();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/tournament-create.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            String name = request.getParameter("tournamentname");
            String startDateStr = request.getParameter("tournamentstart");
            String endDateStr = request.getParameter("tournamentend");
            String format = request.getParameter("format");
            
            // Validation
            if (name == null || name.trim().isEmpty()) {
                result.put("success", false);
                result.put("error", "Tournament name is required");
                response.getWriter().write(new Gson().toJson(result));
                return;
            }
            
            if (startDateStr == null || startDateStr.trim().isEmpty()) {
                result.put("success", false);
                result.put("error", "Start date is required");
                response.getWriter().write(new Gson().toJson(result));
                return;
            }
            
            if (endDateStr == null || endDateStr.trim().isEmpty()) {
                result.put("success", false);
                result.put("error", "End date is required");
                response.getWriter().write(new Gson().toJson(result));
                return;
            }
            
            Date startDate = dateFormat.parse(startDateStr);
            Date endDate = dateFormat.parse(endDateStr);
            
            if (endDate.before(startDate) || endDate.equals(startDate)) {
                result.put("success", false);
                result.put("error", "End date must be after the start date");
                response.getWriter().write(new Gson().toJson(result));
                return;
            }
            
            // Create tournament
            Tournament tournament = new Tournament(name, startDate, endDate, format != null ? format : "LEAGUE");
            tournamentService.createTournament(tournament);
            
            result.put("success", true);
            result.put("message", "Tournament created successfully");
            result.put("tournamentId", tournament.getId().toString());
            
        } catch (ParseException e) {
            result.put("success", false);
            result.put("error", "Invalid date format");
        } catch (Exception e) {
            result.put("success", false);
            result.put("error", "Error creating tournament: " + e.getMessage());
        }
        
        response.getWriter().write(new Gson().toJson(result));
    }
}

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
import java.util.List;

@WebServlet("/tournament/list")
public class TournamentListServlet extends HttpServlet {
    private TournamentService tournamentService;
    
    @Override
    public void init() throws ServletException {
        tournamentService = new TournamentService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String format = request.getParameter("format");
        
        if ("json".equals(format)) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            List<Tournament> tournaments = tournamentService.getAllTournaments();
            response.getWriter().write(new Gson().toJson(tournaments));
        } else {
            List<Tournament> tournaments = tournamentService.getAllTournaments();
            request.setAttribute("tournaments", tournaments);
            request.getRequestDispatcher("/WEB-INF/views/tournament-list.jsp").forward(request, response);
        }
    }
}

package main.Controllers;

import main.Models.BL.TurnOutcomeModel;
import main.Models.BL.TurnStatsModel;
import main.Services.Impl.FightService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "FightServlet", urlPatterns = {"/fight"})
public class FightServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        String att1 = request.getParameter("att1");
        String att2 = request.getParameter("att2");
        String def1 = request.getParameter("def1");
        String def2 = request.getParameter("def2");
        String hp = request.getParameter("userHp");
        String round = request.getParameter("round");

        String endTurn = request.getParameter("endTurn");
        if (endTurn != null) {
            FightService fs = new FightService();
            TurnOutcomeModel model = fs.calculateTurnOutcome(new TurnStatsModel());
            request.setAttribute("round", model.round);
            request.setAttribute("userHp", model.userHp);
            request.setAttribute("oppHp", model.oppHp);
            request.setAttribute("log", model.log);
        }
        request.getRequestDispatcher("fight.jsp").forward(request, response);
    }
}

package main.Controllers;

import main.Models.BL.TurnOutcomeModel;
import main.Models.BL.TurnStatsModel;
import main.Models.CONS.BodyParts;
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
        // fightId -> GetPlayerStats (Hp and so on).
        // in the future we need to get stats from USER EXTENDED
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

            TurnStatsModel turnStatsModel = new TurnStatsModel();
            turnStatsModel.att1 = BodyParts.valueOf(att1);
            turnStatsModel.att2 = BodyParts.valueOf(att2);
            turnStatsModel.def1 = BodyParts.valueOf(def1);
            turnStatsModel.def2 = BodyParts.valueOf(def2);
            turnStatsModel.hp = Integer.parseInt(hp);
            turnStatsModel.userId = Integer.parseInt(userId);
            turnStatsModel.round = Integer.parseInt(round);
            TurnOutcomeModel model = fs.calculateTurnOutcome(turnStatsModel);

            request.setAttribute("round", model.round);
            request.setAttribute("userHp", model.userHp);
            request.setAttribute("oppHp", model.oppHp);
            request.setAttribute("log", model.log);
        }
        request.getRequestDispatcher("fight.jsp").forward(request, response);
    }
}

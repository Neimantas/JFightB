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
        // TODO create a parameter checker --- VERY IMPORTANTEN!!@11
        if (request.getParameterMap().size() > 0 && request.getParameter("userId") != null && request.getParameter("fightId") != null) {
            FightService fs = new FightService();
            long userId = Long.parseLong(request.getParameter("userId"));
            String fightId = request.getParameter("fightId");
            String round = request.getParameter("round");
            TurnOutcomeModel turnOutcomeModel;

            if (round != null && round.equals("0")) {
                // THIS IS ROUND 0 we need to get the stats for the first time
                TurnStatsModel turnStatsModel = new TurnStatsModel();
                turnStatsModel.fightId = fightId;
                turnStatsModel.userId = userId;
                turnStatsModel.round = 0;

                turnOutcomeModel = fs.getStatsForRound(turnStatsModel);
                turnOutcomeModel.round = 1;
            } else {
                String userName = request.getParameter("userName");
                String oppName = request.getParameter("oppName");
                String att1 = request.getParameter("att1");
                String att2 = request.getParameter("att2");
                String def1 = request.getParameter("def1");
                String def2 = request.getParameter("def2");
                String hp = request.getParameter("userHp");

                TurnStatsModel turnStatsModel = new TurnStatsModel();
                turnStatsModel.att1 = BodyParts.valueOf(att1);
                turnStatsModel.att2 = BodyParts.valueOf(att2);
                turnStatsModel.def1 = BodyParts.valueOf(def1);
                turnStatsModel.def2 = BodyParts.valueOf(def2);
                turnStatsModel.hp = Integer.parseInt(hp);
                turnStatsModel.userId = userId;
                turnStatsModel.userName = userName;
                turnStatsModel.oppName = oppName;
                turnStatsModel.fightId = fightId;
                turnStatsModel.round = Integer.parseInt(round);

                turnOutcomeModel = fs.getTurnOutcome(turnStatsModel);
            }
            request.setAttribute("fightId", turnOutcomeModel.fightId);
            request.setAttribute("round", turnOutcomeModel.round);
            request.setAttribute("userName", turnOutcomeModel.userName);
            System.out.println("USER ID --> " + turnOutcomeModel.userId);
            request.setAttribute("userId", turnOutcomeModel.userId);
            request.setAttribute("userHp", turnOutcomeModel.userHp);
            request.setAttribute("oppName", turnOutcomeModel.oppName);
            request.setAttribute("oppHp", turnOutcomeModel.oppHp);
            // TODO not yet implemented
//                request.setAttribute("log", turnOutcomeModel.log);
            request.setAttribute("fightStatus", turnOutcomeModel.fightStatus.toString());
            request.getRequestDispatcher("fight.jsp").forward(request, response);
        }
        response.sendRedirect("/login");
    }
}

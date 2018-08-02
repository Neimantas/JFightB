package main.Controllers;

import main.Models.BL.TurnOutcomeModel;
import main.Models.BL.TurnStatsModel;
import main.Models.CONS.BodyParts;
import main.Models.CONS.Settings;
import main.Services.Impl.FightService;
import main.Services.Impl.LoginService;
import main.Services.ParameterChecker;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
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
        LoginService loginService = new LoginService();
        Cookie token = loginService.findTokenCookie(request.getCookies());

        if (loginService.validate(token)) {
            FightService fs = new FightService();
            TurnOutcomeModel turnOutcomeModel = null;

            String fightId;
            long userId;

            if (ParameterChecker.checkParameters(request,Settings.INITIAL_FIGHT_PARAMETERS)) {
                // THIS IS 0 ROUND we need to get the stats for the first time
                userId = Long.parseLong(request.getParameter("userId"));
                fightId = request.getParameter("fightId");
                TurnStatsModel turnStatsModel = new TurnStatsModel();
                turnStatsModel.fightId = fightId;
                turnStatsModel.userId = userId;
                turnStatsModel.round = 0;
                turnOutcomeModel = fs.getStatsForRound(turnStatsModel);
                turnOutcomeModel.round = 1;
            } else if (ParameterChecker.checkParameters(request,Settings.FIGHT_PARAMETERS)) {
                userId = Long.parseLong(request.getParameter("userId"));
                String round = request.getParameter("round");
                String userName = request.getParameter("userName");
                String oppName = request.getParameter("oppName");
                String att1 = request.getParameter("att1");
                String att2 = request.getParameter("att2");
                String def1 = request.getParameter("def1");
                String def2 = request.getParameter("def2");
                String hp = request.getParameter("userHp");
                fightId = request.getParameter("fightId");

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

            if (turnOutcomeModel != null) {

                request.setAttribute("fightId", turnOutcomeModel.fightId);
                request.setAttribute("round", turnOutcomeModel.round);
                request.setAttribute("userName", turnOutcomeModel.userName);
                request.setAttribute("userId", turnOutcomeModel.userId);
                request.setAttribute("userHp", turnOutcomeModel.userHp);
                request.setAttribute("oppName", turnOutcomeModel.oppName);
                request.setAttribute("oppHp", turnOutcomeModel.oppHp);
                // TODO implement fight log
                request.setAttribute("fightStatus", turnOutcomeModel.fightStatus.toString());
                request.getRequestDispatcher("fight.jsp").forward(request, response);

            } else {
                response.sendRedirect("/news");
            }

        } else {
            // UserModel is not logged in
            response.sendRedirect("/login");
        }
    }
}

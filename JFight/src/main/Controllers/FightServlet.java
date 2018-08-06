package main.Controllers;

import main.Models.BL.TurnOutcomeModel;
import main.Models.BL.TurnStatsModel;
import main.Models.BL.UserModel;
import main.Models.CONS.BodyParts;
import main.Models.CONS.Settings;
import main.Services.Helpers.ObjectConverterToString;
import main.Services.Helpers.ParameterChecker;
import main.Services.ICache;
import main.Services.Impl.Cache;
import main.Services.Impl.FightService;
import main.Services.Impl.LoginService;

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
            ICache cache = Cache.getInstance();
            UserModel user = (UserModel) cache.get(token.getValue());
            FightService fs = new FightService();
            TurnOutcomeModel turnOutcomeModel = null;

            if (ParameterChecker.checkParameters(request,Settings.INITIAL_FIGHT_PARAMETERS)) {
                // THIS IS ROUND 0 we need to get the stats for the first time
                TurnStatsModel turnStatsModel = createTurnStatsModelFromRequest(request, true, user);
                turnOutcomeModel = fs.getStatsForRoundZero(turnStatsModel);
                turnOutcomeModel.round = 1;

            } else if (ParameterChecker.checkParameters(request,Settings.FIGHT_PARAMETERS)) {

                TurnStatsModel turnStatsModel = createTurnStatsModelFromRequest(request, false, user);
                turnOutcomeModel = fs.getTurnOutcome(turnStatsModel);

            } else {
                response.sendRedirect("/news");
            }

            if (turnOutcomeModel != null) {

                request.setAttribute("turnOutcome", ObjectConverterToString.convertObject(turnOutcomeModel));
                request.getRequestDispatcher("fight.jsp").forward(request, response);

            }

        } else {
            // User is not logged in
            response.sendRedirect("/login");
        }
    }

    private TurnStatsModel createTurnStatsModelFromRequest(HttpServletRequest request, boolean firstRound, UserModel user) {
        TurnStatsModel turnStatsModel = new TurnStatsModel();

        turnStatsModel.fightId = request.getParameter("fightId");
        turnStatsModel.userId = user.id;
        turnStatsModel.userName = user.name;

        if (firstRound) {
            turnStatsModel.round = 0;
        } else {
            turnStatsModel.round = Integer.parseInt(request.getParameter("round"));
            turnStatsModel.hp = Integer.parseInt(request.getParameter("userHp"));
            turnStatsModel.oppName = request.getParameter("oppName");
            turnStatsModel.att1 = BodyParts.valueOf(request.getParameter("att1"));
            turnStatsModel.att2 = BodyParts.valueOf(request.getParameter("att2"));
            turnStatsModel.def1 = BodyParts.valueOf(request.getParameter("def1"));
            turnStatsModel.def2 = BodyParts.valueOf(request.getParameter("def2"));
        }

        return turnStatsModel;
    }
}

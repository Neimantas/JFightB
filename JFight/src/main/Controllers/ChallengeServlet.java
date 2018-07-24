package main.Controllers;

import main.Models.DAL.ChallengeDAL;
import main.Models.DTO.*;
import main.Services.Impl.ChallengeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ChallengeServlet", urlPatterns = {"/challenge"})
public class ChallengeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getParameterMap().size() > 0 && request.getParameter("userId") != null) {
            long userId = Long.parseLong(request.getParameter("userId"));
            List<ChallengeDAL> dalList = new ArrayList<>();
            ChallengeService cs = new ChallengeService();

            if (request.getParameter("challengedPlayers") != null) {
                String[] split = request.getParameter("challengedPlayers").split("#");
                for (String s : split) {
                    dalList.add(new ChallengeDAL(0, userId, Long.parseLong(s)));
                }

                DBqueryDTO dbDTO = cs.submitChallenges(dalList);

                if (dbDTO.isSuccess()) {
                    ChallengeDTO challengeDTO = cs.checkForMatches(userId);

                    if (challengeDTO.isSuccess()) {

                        FightDTO fightDTO = cs.createFightForMatchedPlayers(challengeDTO.getList().get(0));

                        if (fightDTO.isSuccess()) {
                            response.sendRedirect("/fight?fightId=" + fightDTO.getDal().getFightId() +
                                                    "&userId=" + userId + "&round=0");
                        }

                    } else {
                        IssuedChallengesDTO issuedChallengesDTO = cs.getIssuedChallenges(userId);

                        if (issuedChallengesDTO.success) {
                            request.setAttribute("userChallenges", issuedChallengesDTO.issuedChallenge.userChallenges);
                            request.setAttribute("oppChallenges", issuedChallengesDTO.issuedChallenge.oppChallenges);
                        }

                    }
                }
            }
            // User has entered the challenge page for the first time or no matches found, return him all players Ready to Fight
            ReadyToFightDTO readyDTO = cs.getAllReadyToFightUsersId(userId);
            request.setAttribute("readyToFightList", readyDTO.getList());
        } else {
            response.sendRedirect("/login");
        }

        request.getRequestDispatcher("/challenge.jsp").forward(request, response);
    }
}

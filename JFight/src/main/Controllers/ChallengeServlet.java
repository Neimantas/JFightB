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
            request.setAttribute("userId", userId);
            List<ChallengeDAL> dalList = new ArrayList<>();
            ChallengeService cs = new ChallengeService();
            cs.addPlayerToReadyToFight(userId);

            if (request.getParameter("challengedPlayers") != null) {
                FightDTO userGotMatched = cs.checkIfUserGotMatched(userId);

                if (userGotMatched.isSuccess()) {
                    response.sendRedirect("/fight?fightId=" + userGotMatched.getDal().getFightId() +
                            "&userId=" + userId + "&round=0");
                    return;
                }

                String[] split = request.getParameter("challengedPlayers").split("#");
                for (String s : split) {
                    dalList.add(new ChallengeDAL(userId, Long.parseLong(s)));
                }

                DBqueryDTO dbDTO = cs.submitChallenges(dalList);

                if (dbDTO.isSuccess()) {
                    ChallengeDTO challengeDTO = cs.checkForMatches(userId);

                    if (challengeDTO.isSuccess()) {

                        FightDTO fightDTO = cs.createFightForMatchedPlayers(challengeDTO.getList().get(0));

                        if (fightDTO.isSuccess()) {
                            response.sendRedirect("/fight?fightId=" + fightDTO.getDal().getFightId() +
                                                    "&userId=" + userId + "&round=0");
                            return;
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
            if (readyDTO.getList().size() > 0) {
                request.setAttribute("readyToFightList", readyDTO.getList());
                readyDTO.getList().forEach(el -> System.out.println(el.getUserName()));
            }
        } else {
            response.sendRedirect("/login");
            return;
        }
        request.getRequestDispatcher("/challenge.jsp").forward(request, response);
    }
}

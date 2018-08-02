package main.Controllers;

import main.Models.DAL.ChallengeDAL;
import main.Models.DTO.*;
import main.Services.IChallengeService;
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
            IChallengeService cs = new ChallengeService();
            String username = null;
            if (!cs.addPlayerToReadyToFight(userId, username)) {
                // TODO should give a message to Front that an error has occurred.
            }

            if (request.getParameter("challengedPlayers") != null) {

                String[] challengedPlayers = request.getParameter("challengedPlayers").split("#");

                // TODO show the user that he is waiting for a match
                // What should we do when a user challenges someone and they challenge him after 15seconds.
                // Find out any possibilities of semi refresh of page

                if (cs.submitChallenges(userId, challengedPlayers)) {

                    if (cs.checkIfUserGotMatched(userId)) {

                        FightDTO fightDTO = cs.createFightForMatchedPlayers(userId);

                        if (fightDTO.success) {
                            response.sendRedirect("/fight?fightId=" + fightDTO.dal.fightId +
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

                // OPTION 2
//                FightDTO fDTO = cs.mainFightProcedure(userId, challengedPlayers);
//
//                if (fDTO.success) {
//                    response.sendRedirect("/fight");
//                } else if (fDTO.message.equals("User has no matches")) {
//                    IssuedChallengesDTO issuedChallengesDTO = cs.getIssuedChallenges(userId);
//
//                    if (issuedChallengesDTO.success) {
//                        request.setAttribute("userChallenges", issuedChallengesDTO.issuedChallenge.userChallenges);
//                        request.setAttribute("oppChallenges", issuedChallengesDTO.issuedChallenge.oppChallenges);
//                    }
//                } else if (fDTO.message.equals(........)){
//
//                }

            }
            // User has entered the challenge page for the first time or no matches found, return him all players Ready to Fight
            ReadyToFightDTO readyDTO = cs.getReadyToFightUsersExceptPrimaryUser(userId);

            if (readyDTO.list.size() > 0) {
                request.setAttribute("readyToFightList", readyDTO.list);
                readyDTO.list.forEach(el -> System.out.println("Users in ReadyToFight -> " + el.userName));
                request.getRequestDispatcher("/challenge.jsp").forward(request, response);
            }

        } else {
            response.sendRedirect("/login");
        }
    }
}

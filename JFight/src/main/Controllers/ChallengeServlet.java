package main.Controllers;

import main.Models.DAL.ChallengeDAL;
import main.Models.DTO.ChallengeDTO;
import main.Models.DTO.DBqueryDTO;
import main.Models.DTO.FightDTO;
import main.Models.DTO.IssuedChallengesDTO;
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
                            long oppId = fightDTO.getDal().getUserId1() != userId ? fightDTO.getDal().getUserId1() : fightDTO.getDal().getUserId2();
                            response.sendRedirect("/fight?fightId=" + fightDTO.getDal().getFightId() +
                                                    "&userId=" + userId + "&oppId=" + oppId);
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
            // User has entered the challenge page for the first time, return him all players Ready to Fight

        }

        request.getRequestDispatcher("/challenge.jsp").forward(request, response);
    }
}

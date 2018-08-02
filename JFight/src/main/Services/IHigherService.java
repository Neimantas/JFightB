package main.Services;

import main.Models.BL.TurnStatsModel;
import main.Models.DAL.ChallengeDAL;
import main.Models.DTO.*;

public interface IHigherService {
    ReadyToFightDTO getAllReadyToFightUsersId(long UserName);

    UserDTO getUserByEmail(String email);

    DBqueryDTO insertTurnStats(TurnStatsModel model);

    DBqueryDTO checkForFightLogByIdAndRound(TurnStatsModel model);

    FightDTO getFightByUserId(long userId);

    DBqueryDTO insertIntoChallenge(ChallengeDAL dal);

    DBqueryDTO checkIfTwoUsersChallengedEachOther(long userId);

    DBqueryDTO getAllIssuedChallengesByUserId(long userId);

    DBqueryDTO addUserToReadyToFightTable(long id);

    DBqueryDTO moveUsersToFight(ChallengeDAL dal);

    DBqueryDTO checkIfFightIsAlreadyCreated(long userId);

    UserDTO getUserNameByUserId(long userId);

    DBqueryDTO checkIfUserIsAlreadyInReadyToFightTable(long userId);

    DBqueryDTO deleteMatchedPlayersFromChallenge(long userId, long opponentId);

    UserDTO getUserByUserNameAndEmail(String userName, String email);

    UserDTO registerUser(String userName, String password, String email);

    UserExtendedDTO getUserExtendByUserId(long userId);
}

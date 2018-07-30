package main.Services;

import main.Models.DAL.ChallengeDAL;
import main.Models.DAL.FightLogDAL;
import main.Models.DAL.ReadyToFightDAL;
import main.Models.DAL.UserDAL;
import main.Models.DTO.*;

public interface IHigherService {
    ReadyToFightDTO getAllReadyToFightUsers();
    DBqueryDTO insertUserToReadyToFightTable(ReadyToFightDAL readyUserDal);
    UserDTO getUserByEmailAndPass(UserDAL user);
    DBqueryDTO insertTurnStats(FightLogDAL fightLog);
    FightLogDTO getFightLogByIdAndRound(FightLogDAL fightLog);
    FightDTO getFightByUserId(long userId);
    DBqueryDTO insertIntoChallenge(ChallengeDAL dal);
    DBqueryDTO checkIfTwoUsersChallengedEachOther(long userId);
    ChallengeDTO getAllIssuedChallengesByUserId(long userId);
    FightDTO moveUsersToFight(ChallengeDAL dal);
    FightDTO checkIfFightIsAlreadyCreated(long userId);
    UserDTO getUserNameByUserId(long userId);
    DBqueryDTO checkIfUserIsAlreadyInReadyToFightTable(long userId);
    DBqueryDTO deleteMatchedPlayersFromChallenge(long userId, long opponentId);
}

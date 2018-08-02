package main.Services;

import main.Models.DAL.ChallengeDAL;
import main.Models.DAL.FightLogDAL;
import main.Models.DAL.ReadyToFightDAL;
import main.Models.DAL.UserDAL;
import main.Models.DTO.*;

public interface IHigherService {
    ChallengeDTO checkIfTwoUsersChallengedEachOther(long userId);

    UserDTO getUserByEmail(String email);

    DBqueryDTO deleteMatchedPlayersFromChallenge(long userId, long opponentId);

    DBqueryDTO deleteUserAndOpponentFromReadyToFight(long userId, long opponentId);

    ChallengeDTO getAllIssuedChallengesByUserId(long userId);

    ReadyToFightDTO getAllReadyToFightUsers();

    FightDTO getFightByUserId(long userId);

    FightLogDTO getFightLogByIdAndRound(FightLogDAL fightLog);

    UserDTO getUserByEmailAndPass(UserDAL user);

    UserDTO getUserByUserId(long userId);

    ReadyToFightDTO getUserFromReadyToFightByUserId(long userId);

    DBqueryDTO insertChallengedPlayers(ChallengeDAL dal);

    DBqueryDTO insertNewFight(ChallengeDAL dal);

    DBqueryDTO insertTurnStats(FightLogDAL fightLog);

    DBqueryDTO insertUserToReadyToFightTable(ReadyToFightDAL readyUserDal);

    DBqueryDTO registerUser(UserDAL user);

    DBqueryDTO deleteFightLogByUserId(long userId);

    UserDTO getUserByUserNameAndEmail(String userName, String email);

    UserExtendedDTO getUserExtendByUserId(long userId);
}

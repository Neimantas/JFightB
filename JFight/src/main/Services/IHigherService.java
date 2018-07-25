package main.Services;

import main.Models.BL.TurnStatsModel;
import main.Models.DAL.ChallengeDAL;
import main.Models.DTO.DBqueryDTO;
import main.Models.DTO.FightDTO;
import main.Models.DTO.ReadyToFightDTO;
import main.Models.DTO.UserDTO;

public interface IHigherService {
    ReadyToFightDTO getAllReadyToFightUsersId(long UserName);
    UserDTO getUserByEmailAndPass(String email, String password);
    DBqueryDTO insertTurnStats(TurnStatsModel model);
    DBqueryDTO checkForFightRecordByIdAndRound(TurnStatsModel model);
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
}

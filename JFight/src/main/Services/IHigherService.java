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
    //    ReadyToFightDTO getReadyToFightUserById(String query);
    DBqueryDTO addUserToReadyToFightTable(long id);

    DBqueryDTO addUsersToChallenge(long UserName, long  UserName2); // prisides i chalanga 2

    DBqueryDTO moveUsersToFight(long UserName, long  UserName2);  // user 1 ir use 2 i fight ir trinam is ready to fight ir trinam is chalange

    ReadyToFightDTO checkIfChallenged(long id); //chalange 1 if exist

    ReadyToFightDTO addWinner (long id);




}

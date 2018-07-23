package main.Services;

import main.Models.DTO.DBqueryDTO;
import main.Models.DTO.ReadyToFightDTO;

public interface IHigherService {
    ReadyToFightDTO getAllReadyToFightUsersId(long UserName);

    //    ReadyToFightDTO getReadyToFightUserById(String query);
    DBqueryDTO addUserToReadyToFightTable(long id);

    DBqueryDTO addUsersToChallenge(long UserName, long  UserName2); // prisides i chalanga 2

    DBqueryDTO moveUsersToFight(long UserName, long  UserName2);  // user 1 ir use 2 i fight ir trinam is ready to fight ir trinam is chalange

    ReadyToFightDTO checkIfChallenged(long id); //chalange 1 if exist

    ReadyToFightDTO addWinner (long id);




}

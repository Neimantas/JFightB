package main.Services;

import main.Models.DTO.DBqueryDTO;
import main.Models.DTO.ReadyToFightDTO;

public interface IFighterService {
    ReadyToFightDTO getAllReadyToFightUsersId();

    DBqueryDTO addUserToReadyTable(long id);

    DBqueryDTO addUserSToChallenge(long id, long id2);

    DBqueryDTO moveUsersToFight(long id, long id2);

    DBqueryDTO checkIfCalanged(long id);

}

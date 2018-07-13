package main.Services;

import main.Models.DTO.DBqueryDTO;
import main.Models.DTO.ReadyToFightDTO;

public interface IFighterService {
    ReadyToFightDTO getAllReadyToFightUsersId();
    ReadyToFightDTO getReadyToFightUserById(String id);
    DBqueryDTO moveUserToReadyTable(String id);
    ReadyToFightDTO userToChallenge(String id);
}

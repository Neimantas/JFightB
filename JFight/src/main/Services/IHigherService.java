package main.Services;

import main.Models.DTO.DBqueryDTO;
import main.Models.DTO.ReadyToFightDTO;

public interface IHigherService {
    ReadyToFightDTO getAllReadyToFightUsersId();
    ReadyToFightDTO getReadyToFightUserById(String query);
    DBqueryDTO moveUserToReadyTable(String id);
    DBqueryDTO moveUserToChallenge(String id);
}

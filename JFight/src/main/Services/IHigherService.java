package main.Services;

import main.Models.BL.TurnStatsModel;
import main.Models.BL.User;
import main.Models.DTO.DBqueryDTO;
import main.Models.DTO.FightDTO;
import main.Models.DTO.ReadyToFightDTO;
import main.Models.DTO.UserDTO;

public interface IHigherService {
    ReadyToFightDTO getAllReadyToFightUsersId();
    ReadyToFightDTO getReadyToFightUserById(String query);
    UserDTO getUserByEmailAndPass(String email, String password);
    DBqueryDTO insertTurnStats(TurnStatsModel model);
    DBqueryDTO checkForFightRecordByIdAndRound(TurnStatsModel model);
    FightDTO getFightByUserId(long userId);
}

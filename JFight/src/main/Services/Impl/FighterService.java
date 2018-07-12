package Services.Impl;

import Models.DTO.ReadyToFightDTO;
import Services.IFighterService;

public class FighterService implements IFighterService {

    @Override
    public ReadyToFightDTO getAllReadyToFightUsersId() {
        ReadyToFightDTO readyToFightDTO = new HigherService().getAllReadyToFightUsersId();
        return readyToFightDTO;
    }
}

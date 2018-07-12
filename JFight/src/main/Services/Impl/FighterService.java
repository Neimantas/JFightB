package main.Services.Impl;

import main.Models.DTO.ReadyToFightDTO;
import main.Services.IFighterService;

public class FighterService implements IFighterService {

    @Override
    public ReadyToFightDTO getAllReadyToFightUsersId() {
        ReadyToFightDTO readyToFightDTO = new HigherService().getAllReadyToFightUsersId();
        return readyToFightDTO;
    }
}

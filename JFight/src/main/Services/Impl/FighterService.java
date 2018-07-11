package Services.Impl;

import Models.DTO.DBqueryDTO;
import Models.DTO.ReadyToFightDTO;
import Services.ICrud;
import Services.IFighterService;

import java.sql.SQLException;

public class FighterService implements IFighterService {

    ICrud crud = new Crud();

    @Override
    public  getAllReadyToFightUsersId() {

        try {
            ReadyToFightDTO dto = HigherService.GetReadyToFight()//crud.read("select * from ReadyToFight;");
            return dBqueryDTO;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ReadyToFightDTO getReadyToFightUserById(String query) {
        return null;
    }
}

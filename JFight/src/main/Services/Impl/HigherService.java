package main.Services.Impl;

import main.Models.CONS.FighterStatus;
import main.Models.DAL.ReadyToFightDAL;
import main.Models.DTO.DBqueryDTO;
import main.Models.DTO.ReadyToFightDTO;
import main.Services.ICrud;
import main.Services.IHigherService;
import java.util.ArrayList;
import java.util.List;

public class HigherService implements IHigherService {

    ICrud crud = new Crud();

    private ReadyToFightDTO createDals(String query) {
        DBqueryDTO dBqueryDTO = crud.read(query);
        if (dBqueryDTO.isSuccess()){
            List<List<Object>> lists = dBqueryDTO.getList();
            List<ReadyToFightDAL> listReadyToFightDal = new ArrayList<>();
            for (int i = 0; i < lists.size(); i++) {
                ReadyToFightDAL dal = new ReadyToFightDAL();
                for (int j = 0; j < lists.get(i).size(); j++) {
                    dal.setUserId(Long.parseLong(lists.get(i).get(j).toString()));
                }
                listReadyToFightDal.add(dal);
            }
            return new ReadyToFightDTO(true, null, listReadyToFightDal);
        }else return new ReadyToFightDTO(false,null,null);

    }

    @Override
    public ReadyToFightDTO getAllReadyToFightUsersId() {

        String query = "select *\n" + "from ReadyToFight";
        return createDals(query);
    }

    @Override
    public ReadyToFightDTO getReadyToFightUserById(String id) {
        String query = "select *\n" + "from ReadyToFight WHERE id = " + id;
        return createDals(query);
    }

    @Override
    public DBqueryDTO moveUserToReadyTable(String id) {
        String query = "INSERT INTO ReadyToFight select UserId from [User] where userId = " + id;
        DBqueryDTO dto = crud.create(query);
        if (dto.isSuccess()){
            dto.setMessage(FighterStatus.SUCCESS.Status());
            return dto;
        }else {
            dto.setMessage(FighterStatus.FAILURE.Status());
            return dto;
        }
    }

    @Override
    public DBqueryDTO moveUserToChallenge(String id) {
        return null;
    }

}

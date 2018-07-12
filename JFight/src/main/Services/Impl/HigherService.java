package Services.Impl;

import Models.DAL.ReadyToFightDAL;
import Models.DTO.DBqueryDTO;
import Models.DTO.ReadyToFightDTO;
import Services.ICrud;
import Services.IHigherService;

import java.util.ArrayList;
import java.util.List;

public class HigherService implements IHigherService {

    ICrud crud = new Crud();

    private ReadyToFightDTO createDals(String query) {
        DBqueryDTO dBqueryDTO = crud.read(query);
        List<List<Object>> lists = dBqueryDTO.getList();
        List<ReadyToFightDAL> listReadyToFightDal = new ArrayList<>();
        for (int i = 0; i < lists.size(); i++) {
            ReadyToFightDAL dal = new ReadyToFightDAL();
            for (int j = 0; j < lists.get(i).size(); j++) {
                dal.setUserId((long) lists.get(i).get(i));
            }
            listReadyToFightDal.add(dal);
        }
        return new ReadyToFightDTO(true, null, listReadyToFightDal);
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


}

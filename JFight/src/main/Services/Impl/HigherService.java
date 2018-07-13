package main.Services.Impl;

import main.Models.DAL.ReadyToFightDAL;
import main.Models.DAL.UserDAL;
import main.Models.DTO.DBqueryDTO;
import main.Models.DTO.ReadyToFightDTO;
import main.Models.DTO.UserDTO;
import main.Services.ICrud;
import main.Services.IHigherService;
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

    @Override
    public UserDTO getUserByEmailAndPass(String email, String password) {
        DBqueryDTO dBqueryDTO = crud.read("SELECT * FROM User WHERE Email = " + email + " AND Password = " + password);
        if(!dBqueryDTO.isSuccess()) {
            return new UserDTO(false, dBqueryDTO.getMessage(), null);
        }
        List<Object> list = dBqueryDTO.getList().get(0);
        UserDAL userDAL = new UserDAL();
        for (int i = 0; i < list.size(); i++) {
            userDAL.setUserId(Long.parseLong(list.get(0).toString()));
            userDAL.setUserName(list.get(1).toString());
            userDAL.setPassword(list.get(2).toString());
            userDAL.setEmail(list.get(3).toString());
            userDAL.setAccessLevel(Integer.parseInt(list.get(2).toString()));
        }
        return new UserDTO(true, null, userDAL);
    }
}

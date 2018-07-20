package main.Services.Impl;

import main.Models.BL.TurnStatsModel;
import main.Models.BL.User;
import main.Models.DAL.FightDAL;
import main.Models.DAL.ReadyToFightDAL;
import main.Models.DAL.UserDAL;
import main.Models.DTO.DBqueryDTO;
import main.Models.DTO.FightDTO;
import main.Models.DTO.ReadyToFightDTO;
import main.Models.DTO.UserDTO;
import main.Services.ICrud;
import main.Services.IHigherService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HigherService implements IHigherService {

    private ICrud crud = new Crud();

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

    @Override
    public DBqueryDTO insertTurnStats(TurnStatsModel model) {
        String query = "INSERT INTO FightLog VALUES(" +
                    model.fightId + ", " + model.userId + ", '" +
                    model.att1 + "', '" + model.att2 + "', '" +
                    model.def1 + "', '" + model.def2 + "', " +
                    model.hp + ", " + model.round + ")";
        return crud.create(query);
    }

    @Override
    public DBqueryDTO checkForFightRecordByIdAndRound(TurnStatsModel model) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM FightLog WHERE FightId = ")
                .append(model.fightId).append(" AND Round = ")
                .append(model.round);
        DBqueryDTO dto = crud.read(query.toString());
        if (!dto.isSuccess()) {
            return dto;
        }
        if (dto.getList().size() != 2) {
            return new DBqueryDTO(false, "Only one record found.", null);
        }
        return dto;
    }

    @Override
    public FightDTO getFightByUserId(long userId) {
        String query = "SELECT * FROM Fight WHERE UserId1 = " +
                userId + " or UserId2 = " + userId;
        DBqueryDTO dto = crud.read(query);
        if (dto.isSuccess()) {
            FightDAL fightDAL = new FightDAL();
            List<Object> list = dto.getList().get(0);
            fightDAL.setFightId(Long.parseLong(list.get(0).toString()));
            fightDAL.setUserId1(Long.parseLong(list.get(1).toString()));
            fightDAL.setUserId2(Long.parseLong(list.get(2).toString()));
            fightDAL.setWinnerId(Long.parseLong(list.get(3).toString()));
            return new FightDTO(true, "", Arrays.asList(fightDAL));
        }
        return new FightDTO(false, dto.getMessage(), null);
    }
}

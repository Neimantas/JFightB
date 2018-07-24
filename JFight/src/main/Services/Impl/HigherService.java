package main.Services.Impl;

import main.Models.CONS.FighterStatus;
import main.Models.DAL.ReadyToFightDAL;
import main.Models.DTO.DBqueryDTO;
import main.Models.DTO.ReadyToFightDTO;
import main.Services.ICrud;
import main.Services.IHigherService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HigherService implements IHigherService {

    ICrud crud = new Crud();

    private ReadyToFightDTO createDals(String query) {
        DBqueryDTO dBqueryDTO = crud.read(query);
        if (dBqueryDTO.isSuccess()) {
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
        } else return new ReadyToFightDTO(false, null, null);
    }

    @Override
    public ReadyToFightDTO getAllReadyToFightUsersId(long UserId) {

        String query = "select * from ReadyToFight WHERE UserId <> " + UserId;

        return createDals(query);
    }

//    @Override
//    public ReadyToFightDTO checkIfChallenged(long UserId) {
//        String query = "SELECT * from Challenge where Challenger1 = " + UserId;
//        return createDals(query);
//    }

    @Override
    public ReadyToFightDTO addWinner(long WinnerId) {
        String query = "insert into Fight (WinnerId) select" + WinnerId;
        return null;
    }

    @Override
    public DBqueryDTO addUserToReadyToFightTable(long UserId) {
        String query = "insert into ReadyToFight select UserID, UserName from [User] where UserId = " + UserId;
        DBqueryDTO dto = crud.create(query);
        if (dto.isSuccess()) {
            dto.setMessage(FighterStatus.SUCCESSREADYTOFIGHT);
            return dto;
        } else {
            dto.setMessage(FighterStatus.FAILURETOINSERTREADYTOFIGHT);
            return dto;
        }
    }

    @Override
    public DBqueryDTO addUsersToChallenge(long UserId, long OpponentId) {
        String query = "insert into Challenge\n" +
                "select " + UserId + " , " + OpponentId;
        DBqueryDTO dto = crud.create(query);
        if (dto.isSuccess()) {
            dto.setMessage(FighterStatus.SUCCESSFULLYADDTOCHALANGE);
            return dto;
        } else dto.setMessage(FighterStatus.FAILURETOINSERTCHALLENGE);
        return dto;
    }

    @Override
    public DBqueryDTO moveUsersToFight(long Fighter1, long Fighter2) {

        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        String query = "insert into Fight (FightId, UserId1, UserId2) select" + randomUUIDString + " , " + Fighter1 + " , " + Fighter2;
        String queryDeleteFromChallenge = "delete from Challenge where (UserId=" + Fighter1 + " or UserId=" + Fighter2 + ")";
        String QueryDeleteFromReadyToFigth = "delete from ReadyToFight where UserId = " + Fighter1 + " and UserId = " + Fighter2 + ";";
        DBqueryDTO dto = crud.delete(queryDeleteFromChallenge);
        if (dto.isSuccess()) {
            dto = crud.delete(QueryDeleteFromReadyToFigth);
            if (dto.isSuccess()) {
                dto = crud.create(query);
                return dto;
            } else {
                dto.setMessage(FighterStatus.FAILURE);
                return dto;
            }
        } else {
            dto.setMessage(FighterStatus.FAILURE);
            return dto;
        }
    }


//    public ReadyToFightDTO getReadyToFightUserById(String id) {
//        String query = "select *\n" + "from ReadyToFight WHERE id = " + id;
//        return createDals(query);
//    }


}

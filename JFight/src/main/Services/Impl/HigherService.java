package main.Services.Impl;

import main.Models.BL.TurnStatsModel;
import main.Models.CONS.FighterStatus;
import main.Models.DAL.*;
import main.Models.DTO.DBqueryDTO;
import main.Models.DTO.FightDTO;
import main.Models.DTO.ReadyToFightDTO;
import main.Models.DTO.UserDTO;
import main.Services.ICrud;
import main.Services.IHigherService;

import java.util.ArrayList;
import java.util.List;

public class HigherService implements IHigherService {

    private ICrud crud = new Crud();

    private ReadyToFightDTO createDals(String query) {
        DBqueryDTO dBqueryDTO = crud.read(query);
        if (dBqueryDTO.isSuccess()) {
            List<List<Object>> lists = dBqueryDTO.getList();
            List<ReadyToFightDAL> listReadyToFightDal = new ArrayList<>();
            for (int i = 0; i < lists.size(); i++) {
                ReadyToFightDAL dal = new ReadyToFightDAL();
                dal.setUserId(Long.parseLong(lists.get(i).get(0).toString()));
                dal.setUserName(lists.get(i).get(1).toString());
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
    public DBqueryDTO moveUsersToFight(ChallengeDAL dal) {
        String query = "INSERT INTO Fight (UserId1, UserId2) VALUES(" + dal.userId + " , " + dal.opponentId + ")";
        String queryDeleteFromChallenge = "DELETE FROM Challenge WHERE (UserId = " + dal.userId + " OR UserId = " + dal.opponentId + ")";
        String QueryDeleteFromReadyToFigth = "DELETE FROM ReadyToFight WHERE UserId = " + dal.userId + " OR UserId = " + dal.opponentId;
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
            fightDAL.setFightId(dto.getList().get(0).get(0).toString());
            fightDAL.setUserId1(Long.parseLong(dto.getList().get(0).get(1).toString()));
            fightDAL.setUserId2(Long.parseLong(dto.getList().get(0).get(2).toString()));
            return new FightDTO(true, "", fightDAL);
        }
        return new FightDTO(false, dto.getMessage(), null);
    }

    public DBqueryDTO insertIntoChallenge(ChallengeDAL dal) {
        String query = "INSERT INTO Challenge ('UserId', 'opponentId') Values("
                    + dal.userId + ", " + dal.opponentId + ")";
        return crud.create(query);
    }

    public DBqueryDTO checkIfTwoUsersChallengedEachOther(long userId) {
        String query = "DECLARE @tempTable table(id int, userId int, oppId int) " +
                        "INSERT INTO @tempTable SELECT * FROM Challenge WHERE opponentId = " + userId +
                        " SELECT ch.userId, ch.oppId " + "FROM Challenge ch " +
                        "INNER JOIN @tempTable tt on tt.userId = ch.oppId WHERE ch.userId = " + userId;
        return crud.read(query);
    }

    public DBqueryDTO getAllIssuedChallengesByUserId(long userId) {
        String query = "SELECT * FROM Challenge WHERE userId = " + userId + " OR opponentId = " + userId;
        return crud.read(query);
    }

    public DBqueryDTO checkIfFightIsAlreadyCreated(long userId) {
        String query = "SELECT * FROM Fight WHERE UserId1 = " + userId + " OR UserId2 = " + userId;
        return crud.read(query);
    }

    public UserDTO getUserNameByUserId(long userId) {
        String query = "SELECT UserName From User WHERE UserId = " + userId;
        DBqueryDTO dto = crud.read(query);
        if (!dto.isSuccess()) {
            return new UserDTO(false, dto.getMessage(), null);
        }
        if (dto.getList().size() > 0) {
            UserDAL dal = new UserDAL();
            dal.setUserName(dto.getList().get(0).get(0).toString());
            return new UserDTO(true, "", dal);
        }
        return new UserDTO(false, "User not found.", null);
    }
}
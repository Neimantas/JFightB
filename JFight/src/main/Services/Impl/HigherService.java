package main.Services.Impl;

import main.Models.BL.TurnStatsModel;
import main.Models.CONS.FighterStatus;
import main.Models.DAL.*;
import main.Models.DTO.*;
import main.Services.ICrud;
import main.Services.IHigherService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HigherService implements IHigherService {

    private ICrud crud = new Crud();

    private ReadyToFightDTO createDals(String query) {
        DBqueryDTO dBqueryDTO = crud.read(query);
        if (dBqueryDTO.success) {
            List<List<Object>> lists = dBqueryDTO.list;
            List<ReadyToFightDAL> listReadyToFightDal = new ArrayList<>();
            for (int i = 0; i < lists.size(); i++) {
                ReadyToFightDAL dal = new ReadyToFightDAL();
                dal.userId = (Long.parseLong(lists.get(i).get(0).toString()));
                dal.userName = (lists.get(i).get(1).toString());
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
        String query = "insert into ReadyToFight select UserID, UserName from [UserModel] where UserId = " + UserId;
        DBqueryDTO dto = crud.create(query);
        if (dto.success) {
            dto.message = (FighterStatus.SUCCESSREADYTOFIGHT);
            return dto;
        } else {
            dto.message = (FighterStatus.FAILURETOINSERTREADYTOFIGHT);
            return dto;
        }
    }

    @Override
    public DBqueryDTO moveUsersToFight(ChallengeDAL dal) {

        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        String queryDeleteFromReadyToFight = "DELETE FROM ReadyToFight WHERE UserId = " + dal.userId + " OR UserId = " + dal.opponentId;
        String queryInsertToFight = "insert into Fight (FightId, UserId1, UserId2) select '" + randomUUIDString + "', " + dal.userId + ", " + dal.opponentId;

        DBqueryDTO dto = crud.delete(queryDeleteFromReadyToFight);
        if (dto.success) {
            dto = crud.create(queryInsertToFight);
            return dto;
        } else {
            dto.message = (FighterStatus.FAILURE);
            return dto;
        }
    }

    @Override
    public DBqueryDTO insertTurnStats(TurnStatsModel model) {
        String query = "INSERT INTO FightLog VALUES('" +
                model.fightId + "', " + model.userId + ", '" +
                model.att1 + "', '" + model.att2 + "', '" +
                model.def1 + "', '" + model.def2 + "', " +
                model.hp + ", " + model.round + ")";
        return crud.create(query);
    }

    @Override
    public DBqueryDTO checkForFightLogByIdAndRound(TurnStatsModel model) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM FightLog WHERE FightId = '")
                .append(model.fightId).append("' AND Round = ")
                .append(model.round);
        DBqueryDTO dto = crud.read(query.toString());
        if (!dto.success) {
            return dto;
        }
        if (dto.list.size() != 2) {
            return new DBqueryDTO(false, "Only one record found.", null);
        }
        return dto;
    }

    @Override
    public FightDTO getFightByUserId(long userId) {
        String query = "SELECT * FROM Fight WHERE UserId1 = " +
                userId + " or UserId2 = " + userId;
        DBqueryDTO dto = crud.read(query);
        if (dto.success && dto.list.size() > 0) {
            FightDAL fightDAL = new FightDAL();
            fightDAL.fightId = (dto.list.get(0).get(0).toString());
            fightDAL.userId1 = (Long.parseLong(dto.list.get(0).get(1).toString()));
            fightDAL.userId2 = (Long.parseLong(dto.list.get(0).get(2).toString()));
            return new FightDTO(true, "", fightDAL);
        }
        return new FightDTO(false, dto.message, null);
    }

    public DBqueryDTO insertIntoChallenge(ChallengeDAL dal) {
        String query = "INSERT INTO Challenge Values("
                + dal.userId + ", " + dal.opponentId + ")";
        return crud.create(query);
    }

    public DBqueryDTO checkIfTwoUsersChallengedEachOther(long userId) {
        String query = "DECLARE @tempTable table(userId int, oppId int) " +
                "INSERT INTO @tempTable SELECT * FROM Challenge WHERE OpponentId = " + userId +
                " SELECT ch.UserId, ch.OpponentId " + "FROM Challenge ch " +
                "INNER JOIN @tempTable tt on tt.userId = ch.OpponentId WHERE ch.UserId = " + userId;
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
        String query = "SELECT UserName From [UserModel] WHERE UserId = " + userId;
        DBqueryDTO dto = crud.read(query);
        if (!dto.success) {
            return new UserDTO(false, dto.message, null);
        }
        if (dto.list.size() > 0) {
            UserDAL dal = new UserDAL();
            dal.userName = (dto.list.get(0).get(0).toString());
            return new UserDTO(true, "", dal);
        }
        return new UserDTO(false, "UserModel not found.", null);
    }

    public DBqueryDTO checkIfUserIsAlreadyInReadyToFightTable(long userId) {
        String query = "SELECT * FROM ReadyToFight WHERE UserId = " + userId;
        return crud.read(query);
    }

    public DBqueryDTO deleteMatchedPlayersFromChallenge(long userId, long opponentId) {
        String queryDeleteFromChallenge = "DELETE FROM Challenge WHERE (UserId = " + userId + " OR UserId = " + opponentId + ")";
        return crud.delete(queryDeleteFromChallenge);
    }

    @Override
    public UserDTO getUserByUserNameAndEmail(String userName, String email) {
        String query = "SELECT * from [UserModel] where UserName= '" + userName + "' or Email='" + email;
        DBqueryDTO dBqueryDTO = crud.read(query);
        if (!dBqueryDTO.success) {
            return new UserDTO(false, dBqueryDTO.message, null);
        } else {
            UserDAL userDAL = new UserDAL();
            if (dBqueryDTO.list.isEmpty()) {
                return new UserDTO(true, null, userDAL);
            } else {
                List<Object> list = dBqueryDTO.list.get(0);
                for (int i = 0; i < list.size(); i++) {
                    userDAL.userName = (list.get(1).toString());
                    userDAL.email = (list.get(3).toString());
                }
                return new UserDTO(false, null, userDAL);
            }
        }
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        DBqueryDTO dBqueryDTO = crud.read("SELECT * from [UserModel] where Email= '" + email + "'");
        if (!dBqueryDTO.success) {
            return new UserDTO(false, dBqueryDTO.message, null);
        } else {
            UserDAL userDAL = new UserDAL();
            if (dBqueryDTO.list.isEmpty()) {
                return new UserDTO(false, null, userDAL);
            } else {
                List<Object> list = dBqueryDTO.list.get(0);
                for (int i = 0; i < list.size(); i++) {
                    userDAL.userId = (Long.parseLong(list.get(0).toString()));
                    userDAL.userName = (list.get(1).toString());
                    userDAL.password = (list.get(2).toString());
                    userDAL.email = (list.get(3).toString());
                    userDAL.accessLevel = (Integer.parseInt(list.get(4).toString()));
                }
                return new UserDTO(true, null, userDAL);
            }
        }
    }

    @Override
    public UserDTO registerUser(String userName, String password, String email) {
        String query = "insert into [UserModel] (UserName, Password, Email, AccessLevel)\n" +
                "    values ('" + userName + "','" + password + "','" + email + "','0');";
        DBqueryDTO dto = crud.read(query);
        if (!dto.success) {
            return new UserDTO(false, dto.message, null);
        } else {
            return new UserDTO(true, null, null);
        }
    }

    @Override
    public UserExtendedDTO getUserExtendByUserId(long userId) {
        String query = "Select * from UserExtended where userId =" + userId;
        DBqueryDTO dto = crud.read(query);
        if (!dto.success) {
            return new UserExtendedDTO(false, dto.message, null);
        } else if (!dto.list.isEmpty()) {
            List<List<Object>> rows = dto.list;
            List<Object> columns = rows.get(0);
            UserExtendedDAL dal = new UserExtendedDAL();

            dal.userId = (Long) columns.get(0);
            dal.userName = columns.get(1).toString();
            dal.image = (byte[]) columns.get(2);
            dal.win = (Integer) columns.get(3);
            dal.lose = (Integer) columns.get(4);
            dal.draw = (Integer) columns.get(5);
            dal.totalFights = (Integer) columns.get(6);

            return new UserExtendedDTO(true, "", dal);
        }
        return new UserExtendedDTO(false, "Such user not found in UserExtended table.", null);
    }
}

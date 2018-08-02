package main.Services.Impl;

import main.Models.BL.DBQueryModel;
import main.Models.BL.ProcedureModel;
import main.Models.DAL.ChallengeDAL;
import main.Models.DAL.FightDAL;
import main.Models.DAL.ReadyToFightDAL;
import main.Models.DAL.UserDAL;
import main.Models.DTO.DBqueryDTO;
import main.Models.DTO.FightDTO;
import main.Models.DTO.ReadyToFightDTO;
import main.Models.DTO.UserDTO;
import main.Services.ICrud;
import main.Services.IHigherService;

import java.util.UUID;

public class HigherService implements IHigherService {

    private ICrud crud = new Crud();

    /**
     * Gets all users that are inserted into the ReadyToFight table.
     * This info is used to show current user all available players to challenge.
     * @return returns a DTO filed with all users that are available to be challenged.
     */
    @Override
    public ReadyToFightDTO getAllReadyToFightUsers() {
        DBqueryDTO<ReadyToFightDAL> dto = crud.read(new DBQueryModel(), ReadyToFightDAL.class);
        if (dto.success) {
            return new ReadyToFightDTO(true, "", dto.list);
        }
        return new ReadyToFightDTO(false, dto.message, null);
    }

    /**
     * Inserts given user into the ReadyToFight table.
     * @param readyUserDal should not be null and must have its fields filled (username and id)
     * @return returns DTO which will have either success as (1) true - insert was successful
     * or (2) false - insert encountered an error.
     */
    @Override
    public DBqueryDTO insertUserToReadyToFightTable(ReadyToFightDAL readyUserDal) { return crud.create(readyUserDal); }

    @Override
    public DBqueryDTO deleteUserAndOpponentFromReadyToFight(long userId, long opponentId) {
        DBQueryModel dbQueryModel = new DBQueryModel();
        dbQueryModel.where = new String[]{"UserId"};
        dbQueryModel.whereValue = new String[][]{new String[]{String.valueOf(userId),
                                                        String.valueOf(opponentId)}};
        return crud.delete(dbQueryModel, ReadyToFightDAL.class);
    }


    @Override
    public DBqueryDTO insertNewFight(ChallengeDAL dal) {
        String uuid = UUID.randomUUID().toString();
        FightDAL fightDAL = new FightDAL(uuid, dal.userId, dal.opponentId);
        return crud.create(fightDAL);
    }

    // TODO check what happens if there is no match
    /**
     * Gets user with specified Email and Password if there is such a match in DataBase.
     * @param user UserDAL with filled Email and Password fields.
     * @return UserDTO with the matched user if both the Email and Password, other it will return ????
     */
    @Override
    public UserDTO getUserByEmailAndPass(UserDAL user) {
        DBQueryModel dbQueryModel = new DBQueryModel();
        dbQueryModel.where = new String[]{"Email", "Password"};
        dbQueryModel.whereValue = new String[][]{new String[]{user.email},
                new String[]{user.password}};
        DBqueryDTO<UserDAL> dbqueryDTO = crud.read(dbQueryModel, UserDAL.class);

        if (dbqueryDTO.success && !dbqueryDTO.list.isEmpty()) {
            return new UserDTO(true, "", dbqueryDTO.list.get(0));
        } else if (dbqueryDTO.success) {
            return new UserDTO(false, "User with such Email and Password not found.", null);
        }

        return new UserDTO(false, dbqueryDTO.message, null);
    }
    public DBqueryDTO insertTurnStats(FightLogDAL fightLog) { return crud.create(fightLog); }

    @Override
    public FightLogDTO getFightLogByIdAndRound(FightLogDAL fightLog) {
        DBQueryModel dbQueryModel = new DBQueryModel();
        dbQueryModel.where = new String[]{"FightId", "Round"};
        dbQueryModel.whereValue = new String[][] {new String[]{fightLog.fightId},
                                            new String[]{String.valueOf(fightLog.round)}};
        DBqueryDTO<FightLogDAL> dto = crud.read(dbQueryModel, FightLogDAL.class);

        if (dto.success && dto.list.size() == Settings.NUMBER_OF_PLAYERS) {
            return new FightLogDTO(true, "", dto.list);
        } else if (dto.success) {
            // TODO change to ENUM
            return new FightLogDTO(false, "Only one record found.", dto.list);
        }

        return new FightLogDTO(false, dto.message, null);
    }

    @Override
    public FightDTO getFightByUserId(long userId) {
        DBQueryModel dbQueryModel = new DBQueryModel();
        dbQueryModel.where = new String[]{"UserId1", "UserId2"};
        dbQueryModel.whereValue = new String[][]{new String[]{String.valueOf(userId)},
                                            new String[]{String.valueOf(userId)}};
        DBqueryDTO<FightDAL> dto = crud.read(dbQueryModel, FightDAL.class);

        if (dto.success && dto.list.size() > 0) {
            return new FightDTO(true, "", dto.list.get(0));
        } else if (dto.success) {
            // TODO use ENUM here
            return new FightDTO(false, "No Fights with such UserId found.", null);
        }

        return new FightDTO(false, dto.message, null);
    }

    @Override
    public DBqueryDTO insertChallengedPlayers(ChallengeDAL challengeDAL) {
        return crud.create(challengeDAL);
    }

    // TODO THIS IS NOT FIXED!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    @Override
    public ChallengeDTO checkIfTwoUsersChallengedEachOther(long userId) {
        /*String query = "DECLARE @tempTable table(userId int, oppId int) " +
                "INSERT INTO @tempTable SELECT * FROM Challenge WHERE OpponentId = " + userId +
                " SELECT ch.UserId, ch.OpponentId " + "FROM Challenge ch " +
                        "INNER JOIN @tempTable tt on tt.userId = ch.OpponentId WHERE ch.UserId = " + userId;*/
        DBQueryModel dbQueryModel = new DBQueryModel();
        dbQueryModel = new DBQueryModel();
        ProcedureModel procedure = new ProcedureModel();
//        procedure.name = "CheckChallenges";
//        procedure.params = userId.toString();
        DBqueryDTO<ChallengeDAL> dto = crud.read(dbQueryModel, ChallengeDAL.class);

        if (dto.success) {
            return new ChallengeDTO(true, "", dto.list);
        }

        return new ChallengeDTO(false, dto.message, null);
    }

    @Override
    public ChallengeDTO getAllIssuedChallengesByUserId(long userId) {
        DBQueryModel dbQueryModel = new DBQueryModel();
        dbQueryModel.where = new String[]{"UserId", "OpponentId"};
        dbQueryModel.whereValue = new String[][]{new String[]{String.valueOf(userId)},
                                                new String[]{String.valueOf(userId)}};
        DBqueryDTO<ChallengeDAL> dto = crud.read(dbQueryModel, ChallengeDAL.class);

        if (dto.success) {
            return new ChallengeDTO(true, "", dto.list);
        }

        return new ChallengeDTO(false, dto.message, null);
    }

    @Override
    public UserDTO getUserByUserId(long userId) {
        DBQueryModel dbQueryModel = new DBQueryModel();
        dbQueryModel.where = new String[]{"UserId"};
        dbQueryModel.whereValue = new String[][]{new String[]{String.valueOf(userId)}};
        DBqueryDTO<UserDAL> dto = crud.read(dbQueryModel, UserDAL.class);

        if (dto.success && dto.list.size() > 0) {
            return new UserDTO(true, "", dto.list.get(0));
        } else if (dto.success) {
            return  new UserDTO(false, "No such user found.", null);
        }

        return new UserDTO(false, dto.message, null);
    }

    @Override
    public ReadyToFightDTO getUserFromReadyToFightByUserId(long userId) {
        DBQueryModel dbQueryModel = new DBQueryModel();
        dbQueryModel.where = new String[]{"UserId"};
        dbQueryModel.whereValue = new String[][]{new String[]{String.valueOf(userId)}};

        DBqueryDTO<ReadyToFightDAL> dto = crud.read(dbQueryModel, ReadyToFightDAL.class);

        if (dto.success && !dto.list.isEmpty()) {
            return new ReadyToFightDTO(true, "", dto.list);
        } else if (dto.success) {
            return new ReadyToFightDTO(false, "Such user not found in ReadyToFight", null);
        }

        return new ReadyToFightDTO(false, dto.message, null);
    }

    @Override
    public DBqueryDTO deleteMatchedPlayersFromChallenge(long userId, long opponentId) {
        DBQueryModel dbQueryModel = new DBQueryModel();
        dbQueryModel.where = new String[]{"UserId"};
        dbQueryModel.whereValue = new String[][]{new String[]{String.valueOf(userId), String.valueOf(opponentId)}};
        return crud.delete(dbQueryModel, ChallengeDAL.class);
    }

    @Override
    public UserDTO getUserByEmail(String email) {

        DBQueryModel dbQueryModel = new DBQueryModel();
        dbQueryModel.where = new String[]{"Email"};
        dbQueryModel.whereValue = new String[][]{ new String[]{email}};

        DBqueryDTO<UserDAL> dBqueryDTO = crud.read(dbQueryModel, UserDAL.class);

        if (dBqueryDTO.success && !dBqueryDTO.list.isEmpty()) {
            return new UserDTO(true, "", dBqueryDTO.list.get(0));
        } else if (dBqueryDTO.success) {
            return new UserDTO(false, "User not found.", null);
        }

        return new UserDTO(false, dBqueryDTO.message, null);
    }

    @Override
    public DBqueryDTO registerUser(UserDAL user) {
        user.accessLevel = 0;
        return crud.create(user);
    }

    @Override
    public DBqueryDTO deleteFightLogByUserId(long userId) {
        DBQueryModel model = new DBQueryModel();
        model.where = new String[] {"userId"};
        model.whereValue = new String[][] {new String[]{String.valueOf(userId)}};
        DBqueryDTO dto = crud.delete(model, FightLogDAL.class);
        return dto;
    }

    @Override
    public UserDTO getUserByUserNameAndEmail(String userName, String email) {
        DBQueryModel dbQueryModel = new DBQueryModel();
        dbQueryModel.where = new String[]{"UserName", "Email"};
        dbQueryModel.whereValue = new String[][] {new String[]{userName},
                                                  new String[]{email}};
        DBqueryDTO<UserDAL> dto = crud.read(dbQueryModel, UserDAL.class);

        if (dto.success && !dto.list.isEmpty()) {
            return new UserDTO(true, "", dto.list.get(0));
        } else if (dto.success){
            return new UserDTO(false, "User not found.", null);
        }

    @Override
    public UserExtendedDTO getUserExtendByUserId(long userId) {
        return new UserDTO(false, dto.message, null);
    }
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

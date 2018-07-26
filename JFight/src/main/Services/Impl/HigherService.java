package main.Services.Impl;

import com.sun.istack.internal.NotNull;
import main.Models.BL.DBQueryModel;
import main.Models.BL.ProcedureModel;
import main.Models.BL.TurnStatsModel;
import main.Models.BL.User;
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
    public DBqueryDTO insertUserToReadyToFightTable(ReadyToFightDAL readyUserDal) {
        DBqueryDTO dto = crud.create(readyUserDal);
        if (dto.success) {
            dto.message = FighterStatus.SUCCESSREADYTOFIGHT;
            return dto;
        } else {
            dto.message = FighterStatus.FAILURETOINSERTREADYTOFIGHT;
            return dto;
        }
    }


    /**
     * Deletes given users inside ChallengeDAL from ReadyToFight table and creates a new fight
     * using a unique randomUUID as fightId.
     * @param dal ChallengeDAL, it cannot be null and should have its both fields filled.
     * @return returns DBqueryDTO with success as (1) true if delete and insert were successful
     * or (2) false if either of them failed.
     */
    @Override
    public DBqueryDTO moveUsersToFight(ChallengeDAL dal) {
        UUID uuid = UUID.randomUUID();
        DBQueryModel dbModel = new DBQueryModel();
        dbModel.where = new String[]{"UserId"};
        dbModel.whereValue = new String[][]{new String[]{String.valueOf(dal.userId),
                                                        String.valueOf(dal.opponentId)}};
        DBqueryDTO dto = crud.delete(dbModel, ReadyToFightDAL.class);

        if (dto.success) {
            String randomUUIDString = uuid.toString();
            FightDAL fightDAL = new FightDAL(randomUUIDString, dal.userId, dal.opponentId);
            dto = crud.create(fightDAL);
            return dto;
        } else {
            dto.message = FighterStatus.FAILURE;
            return dto;
        }
    }

    // TODO check what happens if there is no match
    /**
     * Gets user with specified Email and Password if there is such a match in DataBase.
     * @param user UserDAL with filled Email and Password fields.
     * @return UserDTO with the matched user if both the Email and Password, other it will return ????
     */
    @Override
    public UserDTO getUserByEmailAndPass(UserDAL user) {
        DBQueryModel dbModel = new DBQueryModel();
        dbModel.where = new String[]{"Email", "Password"};
        dbModel.whereValue = new String[][]{new String[]{user.email},
                                            new String[]{user.password}};
        DBqueryDTO<UserDAL> dBqueryDTO = crud.read(dbModel, UserDAL.class);

        if(dBqueryDTO.success) return new UserDTO(true, "", dBqueryDTO.list.get(0));

        return new UserDTO(false, dBqueryDTO.message, null);
    }

    @Override
    public DBqueryDTO insertTurnStats(FightLogDAL fightLog) {
        return crud.create(fightLog);
    }

    @Override
    public FightLogDTO getFightLogByIdAndRound(FightLogDAL fightLog) {
        DBQueryModel dbModel = new DBQueryModel();
        dbModel.where = new String[]{"FightId", "Round"};
        dbModel.whereValue = new String[][] {new String[]{fightLog.fightId},
                                            new String[]{String.valueOf(fightLog.round)}};
        DBqueryDTO<FightLogDAL> dto = crud.read(dbModel, FightLogDAL.class);

        // TODO magic numer 2 ??? Make it into a constant
        if (dto.success && dto.list.size() == 2) {
            return new FightLogDTO(true, "", dto.list);
        } else if (dto.success) {
            // TODO change to ENUM
            return new FightLogDTO(false, "Only one record found.", dto.list);
        }

        return new FightLogDTO(false, dto.message, null);
    }

    @Override
    public FightDTO getFightByUserId(long userId) {
        DBQueryModel dbModel = new DBQueryModel();
        dbModel.where = new String[]{"UserId1", "UserId2"};
        dbModel.whereValue = new String[][]{new String[]{String.valueOf(userId)},
                                            new String[]{String.valueOf(userId)}};
        DBqueryDTO<FightDAL> dto = crud.read(dbModel, FightDAL.class);

        if (dto.success && dto.list.size() > 0) {
            return new FightDTO(true, "", dto.list.get(0));
        } else if (dto.success) {
            // TODO use ENUM here
            return new FightDTO(false, "No Fights with such UserId found.", null);
        }

        return new FightDTO(false, dto.message, null);
    }

    @Override
    public DBqueryDTO insertIntoChallenge(ChallengeDAL challengeDAL) {
        return crud.create(challengeDAL);
    }

    // TODO THIS IS NOT FIXED!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    @Override
    public DBqueryDTO checkIfTwoUsersChallengedEachOther(long userId) {
        /*String query = "DECLARE @tempTable table(userId int, oppId int) " +
                        "INSERT INTO @tempTable SELECT * FROM Challenge WHERE OpponentId = " + userId +
                        " SELECT ch.UserId, ch.OpponentId " + "FROM Challenge ch " +
                        "INNER JOIN @tempTable tt on tt.userId = ch.OpponentId WHERE ch.UserId = " + userId;*/
        DBQueryModel query = new DBQueryModel();
        ProcedureModel procedure = new ProcedureModel();
//        procedure.name = "CheckChallenges";
//        procedure.params = userId.toString();
        return crud.read(query, ChallengeDAL.class);
    }

    @Override
    public DBqueryDTO getAllIssuedChallengesByUserId(long userId) {
        String query = "SELECT * FROM Challenge WHERE userId = " + userId + " OR opponentId = " + userId;
        return crud.read(query);
    }

    @Override
    public DBqueryDTO checkIfFightIsAlreadyCreated(long userId) {
        String query = "SELECT * FROM Fight WHERE UserId1 = " + userId + " OR UserId2 = " + userId;
        return crud.read(query);
    }

    @Override
    public UserDTO getUserNameByUserId(long userId) {
        String query = "SELECT UserName From [User] WHERE UserId = " + userId;
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

    @Override
    public DBqueryDTO checkIfUserIsAlreadyInReadyToFightTable(long userId) {
        String query = "SELECT * FROM ReadyToFight WHERE UserId = " + userId;
        return crud.read(query);
    }

    @Override
    public DBqueryDTO deleteMatchedPlayersFromChallenge(long userId, long opponentId) {
        String queryDeleteFromChallenge = "DELETE FROM Challenge WHERE (UserId = " + userId + " OR UserId = " + opponentId + ")";
        return crud.delete(queryDeleteFromChallenge);
    }
}
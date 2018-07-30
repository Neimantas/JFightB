package main.Services.Impl;

import main.Models.BL.DBQueryModel;
import main.Models.BL.ProcedureModel;
import main.Models.CONS.FighterStatus;
import main.Models.DAL.*;
import main.Models.DTO.*;
import main.Services.ICrud;
import main.Services.IHigherService;

import java.util.UUID;

public class HigherService implements IHigherService {

    private ICrud crud = new Crud();
    private DBQueryModel dbQueryModel = new DBQueryModel();

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
    public FightDTO moveUsersToFight(ChallengeDAL dal) {
        UUID uuid = UUID.randomUUID();
        dbQueryModel.where = new String[]{"UserId"};
        dbQueryModel.whereValue = new String[][]{new String[]{String.valueOf(dal.userId),
                                                        String.valueOf(dal.opponentId)}};
        DBqueryDTO dto = crud.delete(dbQueryModel, ReadyToFightDAL.class);

        if (dto.success) {
            String randomUUIDString = uuid.toString();
            FightDAL fightDAL = new FightDAL(randomUUIDString, dal.userId, dal.opponentId);
            // TODO paklausti ar cia visgi reiktu tikrinti ar success or not ? Ty ka tokiais atvejais daryti jeigu false ?
            crud.create(fightDAL);
            return new FightDTO(true, "", fightDAL);
        } else {
            dto.message = FighterStatus.FAILURE;
            return new FightDTO(false, dto.message, null);
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
        dbQueryModel.where = new String[]{"Email", "Password"};
        dbQueryModel.whereValue = new String[][]{new String[]{user.email},
                                            new String[]{user.password}};
        DBqueryDTO<UserDAL> dbqueryDTO = crud.read(dbQueryModel, UserDAL.class);

        if(dbqueryDTO.success) {
            return new UserDTO(true, "", dbqueryDTO.list.get(0));
        }

        return new UserDTO(false, dbqueryDTO.message, null);
    }

    @Override
    public DBqueryDTO insertTurnStats(FightLogDAL fightLog) {
        return crud.create(fightLog);
    }

    @Override
    public FightLogDTO getFightLogByIdAndRound(FightLogDAL fightLog) {
        dbQueryModel.where = new String[]{"FightId", "Round"};
        dbQueryModel.whereValue = new String[][] {new String[]{fightLog.fightId},
                                            new String[]{String.valueOf(fightLog.round)}};
        DBqueryDTO<FightLogDAL> dto = crud.read(dbQueryModel, FightLogDAL.class);

        // TODO magic number 2 ??? Make it into a constant
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
        dbQueryModel = new DBQueryModel();
        ProcedureModel procedure = new ProcedureModel();
//        procedure.name = "CheckChallenges";
//        procedure.params = userId.toString();
        return crud.read(dbQueryModel, ChallengeDAL.class);
    }

    @Override
    public ChallengeDTO getAllIssuedChallengesByUserId(long userId) {
        dbQueryModel.where = new String[]{"UserId", "OponnentId"};
        dbQueryModel.whereValue = new String[][]{new String[]{String.valueOf(userId)},
                                                new String[]{String.valueOf(userId)}};
        DBqueryDTO<ChallengeDAL> dto = crud.read(dbQueryModel, ChallengeDAL.class);

        if (dto.success) {
            return new ChallengeDTO(true, "", dto.list);
        }

        return new ChallengeDTO(false, dto.message, null);
    }

    @Override
    public FightDTO checkIfFightIsAlreadyCreated(long userId) {
        dbQueryModel.where = new String[]{"UserId1", "UserId2"};
        dbQueryModel.whereValue = new String[][]{new String[]{String.valueOf(userId)},
                                                new String[]{String.valueOf(userId)}};
        DBqueryDTO<FightDAL> dto = crud.read(dbQueryModel, FightDAL.class);

        if (dto.success && dto.list.size() > 0) {
            return new FightDTO(true, "", dto.list.get(0));
        }

        return new FightDTO(false, dto.message, null);
    }

    //TODO IMPORTANT now this method will return the whole user not just its username
    @Override
    public UserDTO getUserNameByUserId(long userId) {
        dbQueryModel.where = new String[]{"UserId"};
        dbQueryModel.whereValue = new String[][]{new String[]{String.valueOf(userId)}};
        DBqueryDTO<UserDAL> dto = crud.read(dbQueryModel, UserDAL.class);

        if (dto.success && dto.list.size() > 0) {
            return new UserDTO(true, "", dto.list.get(0));
        }

        return new UserDTO(false, dto.message, null);
    }

    @Override
    public DBqueryDTO checkIfUserIsAlreadyInReadyToFightTable(long userId) {
        dbQueryModel.where = new String[]{"UserId"};
        dbQueryModel.whereValue = new String[][]{new String[]{String.valueOf(userId)}};
        return crud.read(dbQueryModel, ReadyToFightDAL.class);
    }

    @Override
    public DBqueryDTO deleteMatchedPlayersFromChallenge(long userId, long opponentId) {
        dbQueryModel.where = new String[]{"UserId"};
        dbQueryModel.whereValue = new String[][]{new String[]{String.valueOf(userId), String.valueOf(opponentId)}};
        return crud.delete(dbQueryModel, ChallengeDAL.class);
    }
}
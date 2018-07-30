package main.Services.Impl;

import main.Models.BL.IssuedChallenges;
import main.Models.BL.TurnStatsModel;
import main.Models.DAL.ChallengeDAL;
import main.Models.DAL.FightDAL;
import main.Models.DAL.FightLogDAL;
import main.Models.DAL.ReadyToFightDAL;
import main.Models.DTO.*;
import main.Services.IChallenge;
import main.Services.IHigherService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChallengeService implements IChallenge {

    private IHigherService hs = new HigherService();
    private DBqueryDTO dto;

    public DBqueryDTO submitChallenges(List<ChallengeDAL> dalList) {
        for (ChallengeDAL dal : dalList) {
            dto = hs.insertIntoChallenge(dal);
        }
        return dto;
    }

    public ChallengeDTO checkForMatches(long userId){
        dto = hs.checkIfTwoUsersChallengedEachOther(userId);
        if (dto.success) {
            if (dto.list.size() > 0) {
                List<Object> list = dto.list.get(0);
                List<ChallengeDAL> dalList = Arrays.asList(new ChallengeDAL(
                        Long.parseLong(list.get(0).toString()),
                        Long.parseLong(list.get(1).toString())));
                return new ChallengeDTO(true, "", dalList);
            }
            return new ChallengeDTO(false, "No matches found.", null);
        }
        return new ChallengeDTO(false, dto.message, null);
    }

    private FightDTO wasNewFightAlreadyCreated(long userId) {
        FightDTO fightDTO = hs.checkIfFightIsAlreadyCreated(userId);

        if (fightDTO.success && fightDTO.dal != null) {
            return new FightDTO(true, "Fight has been already created.", fightDTO.dal);
        } else if (fightDTO.success){
            return new FightDTO(false, "No fight has been found.", null);
        }

        // DB has crashed
        return new FightDTO(false, dto.message, null);
    }

    public FightDTO createFightForMatchedPlayers(ChallengeDAL dal) {
        FightDTO fightDTO = wasNewFightAlreadyCreated(dal.userId);
        // TODO use enums here
        if (!fightDTO.success && fightDTO.message.equals("No fight has been found.")) {

            // Fight has not been created so we move create new Fight and remove users from Challenge, ReadyTOFight tables
            fightDTO = hs.moveUsersToFight(dal);

            if (fightDTO.success) {
                insertZeroRoundStatsBeforeFight(fightDTO);
            }

        }
        return fightDTO;
    }

    private void insertZeroRoundStatsBeforeFight(FightDTO fightDTO) {
        // TODO we need to get stats from user Character table (not implemented)
        int hp = 10;
        int round = 0; //stats before fight
//        TurnStatsModel turnStatsModel1 = new TurnStatsModel();
//        turnStatsModel1.userName = hs.getUserNameByUserId(fightDTO.dal.userId1).user.userName;
//        turnStatsModel1.userId = fightDTO.dal.userId1;
//        turnStatsModel1.fightId = fightDTO.dal.fightId;
//        turnStatsModel1.round = round;
//        turnStatsModel1.hp = hp;

//        TurnStatsModel turnStatsModel2 = new TurnStatsModel();
//        turnStatsModel2.userName = hs.getUserNameByUserId(fightDTO.dal.userId2).user.userName;
//        turnStatsModel2.userId = fightDTO.dal.userId2;
//        turnStatsModel2.fightId = fightDTO.dal.fightId;
//        turnStatsModel2.round = round;
//        turnStatsModel2.hp = hp;

        FightLogDAL fightLog = new FightLogDAL();
        fightLog.userId = fightDTO.dal.userId1;
        fightLog.fightId = fightDTO.dal.fightId;
        fightLog.round = round;
        fightLog.hp = hp;

        // TODO must be check for success
        hs.insertTurnStats(fightLog);

        fightLog.userId = fightDTO.dal.userId2;

        // TODO must be check for success
        hs.insertTurnStats(fightLog);
    }

    // TODO FIX THIS
    public IssuedChallengesDTO getIssuedChallenges(long userId) {
        ChallengeDTO challengeDTO = hs.getAllIssuedChallengesByUserId(userId);
        if (!dto.success) {
            return null;
        }
        List<List<Object>> rows = dto.list;
        List<Long> userChallenges = new ArrayList<>();
        List<Long> oppChallenges = new ArrayList<>();
        for (int i = 0; i < rows.size(); i++) {
            List<Object> columns = rows.get(i);
            if (Long.parseLong(columns.get(1).toString()) == userId) {
                userChallenges.add(Long.parseLong(columns.get(2).toString()));
            } else {
                oppChallenges.add(Long.parseLong(columns.get(1).toString()));
            }
        }
        return new IssuedChallengesDTO(true, "", new IssuedChallenges(userId, null, userChallenges, oppChallenges));
    }

    public ReadyToFightDTO getAllReadyToFightUsersId(long userId) {
        ReadyToFightDTO readyToFightDTO = hs.getAllReadyToFightUsers();
        for (int i = 0; i < readyToFightDTO.list.size(); i++) {
            if (readyToFightDTO.list.get(i).userId == userId) {
                readyToFightDTO.list.remove(i);
                break;
            }
        }
        return readyToFightDTO;
    }

    public DBqueryDTO addPlayerToReadyToFight(long userId) {
        // Check if user is already in ReadyToFight table
        dto = hs.checkIfUserIsAlreadyInReadyToFightTable(userId);
        if (dto.success && dto.list.size() > 0) {
            return dto;
        }
        // TODO as usual....
        return hs.insertUserToReadyToFightTable(userId);
    }

    public FightDTO checkIfUserGotMatched(long userId) {
        FightDTO fightDTO = hs.getFightByUserId(userId);
        if (fightDTO.success) {
            long oppId = fightDTO.dal.userId1 != userId ? fightDTO.dal.userId1 : fightDTO.dal.userId2;
            hs.deleteMatchedPlayersFromChallenge(userId, oppId);
        }
        return fightDTO
    }
}

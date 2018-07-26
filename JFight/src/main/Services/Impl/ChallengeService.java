package main.Services.Impl;

import main.Models.BL.IssuedChallenges;
import main.Models.BL.TurnStatsModel;
import main.Models.DAL.ChallengeDAL;
import main.Models.DAL.FightDAL;
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
        dto = new DBqueryDTO(false, null, null);
        for (ChallengeDAL dal : dalList) {
            dto = hs.insertIntoChallenge(dal);
        }
        return dto;
    }

    public ChallengeDTO checkForMatches(long userId){
        dto = hs.checkIfTwoUsersChallengedEachOther(userId);
        if (dto.isSuccess()) {
            if (dto.getList().size() > 0) {
                List<Object> list = dto.getList().get(0);
                List<ChallengeDAL> dalList = Arrays.asList(new ChallengeDAL(
                        Long.parseLong(list.get(0).toString()),
                        Long.parseLong(list.get(1).toString())));
                return new ChallengeDTO(true, "", dalList);
            }
            return new ChallengeDTO(false, "No matches found.", null);
        }
        return new ChallengeDTO(false, dto.getMessage(), null);
    }

    private FightDTO wasNewFightAlreadyCreated(long userId) {
        dto = hs.checkIfFightIsAlreadyCreated(userId);
        if (dto.isSuccess()) {
            if (dto.getList().size() > 0) {
                FightDAL dal = new FightDAL();
                dal.setFightId(dto.getList().get(0).get(0).toString());
                dal.setUserId1(Long.parseLong(dto.getList().get(0).get(1).toString()));
                dal.setUserId2(Long.parseLong(dto.getList().get(0).get(2).toString()));
                return new FightDTO(true, "Fight has been already created.", dal);
            } else {
                return new FightDTO(true, "No fight found", null);
            }
        }
        // DB has crashed
        return new FightDTO(false, dto.getMessage(), null);
    }

    public FightDTO createFightForMatchedPlayers(ChallengeDAL dal) {
        FightDTO fightDTO = wasNewFightAlreadyCreated(dal.userId);

        if (fightDTO.isSuccess()) {

            if (fightDTO.getMessage().equals("No fight found")) {
                dto = hs.moveUsersToFight(dal);

                if (dto.isSuccess()) {
                    fightDTO = hs.getFightByUserId(dal.userId);
                    insertZeroRoundStatsBeforeFight(fightDTO);
                    if (fightDTO.isSuccess()) {
                        return fightDTO;
                    }

                }

            } else {
                return fightDTO;
            }
        }
        // DB crash
        return new FightDTO(false, dto.getMessage(), null);
    }

    private void insertZeroRoundStatsBeforeFight(FightDTO fightDTO) {
        // TODO we need to get stats from user Character table (not implemented)
        int hp = 10;
        int round = 0; //stats before fight
        TurnStatsModel turnStatsModel1 = new TurnStatsModel();
        turnStatsModel1.userName = hs.getUserNameByUserId(fightDTO.getDal().getUserId1()).getUser().getUserName();
        turnStatsModel1.userId = fightDTO.getDal().getUserId1();
        turnStatsModel1.fightId = fightDTO.getDal().getFightId();
        turnStatsModel1.round = round;
        turnStatsModel1.hp = hp;

        TurnStatsModel turnStatsModel2 = new TurnStatsModel();
        turnStatsModel2.userName = hs.getUserNameByUserId(fightDTO.getDal().getUserId2()).getUser().getUserName();
        turnStatsModel2.userId = fightDTO.getDal().getUserId2();
        turnStatsModel2.fightId = fightDTO.getDal().getFightId();
        turnStatsModel2.round = round;
        turnStatsModel2.hp = hp;

        // TODO must be check for success
        hs.insertTurnStats(turnStatsModel1);
        hs.insertTurnStats(turnStatsModel2);
    }

    public IssuedChallengesDTO getIssuedChallenges(long userId) {
        dto = hs.getAllIssuedChallengesByUserId(userId);
        if (!dto.isSuccess()) {
            return null;
        }
        List<List<Object>> rows = dto.getList();
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
        return hs.getAllReadyToFightUsersId(userId);
    }

    public DBqueryDTO addPlayerToReadyToFight(long userId) {
        // Check if user is already in ReadyToFight table
        dto = hs.checkIfUserIsAlreadyInReadyToFightTable(userId);
        if (dto.isSuccess() && dto.getList().size() > 0) {
            return dto;
        }
        // TODO as usual....
        return hs.addUserToReadyToFightTable(userId);
    }

    public FightDTO checkIfUserGotMatched(long userId) {
        FightDTO fightDTO = hs.getFightByUserId(userId);
        if (fightDTO.isSuccess()) {
            long oppId = fightDTO.getDal().getUserId1() != userId ? fightDTO.getDal().getUserId1() : fightDTO.getDal().getUserId2();
            hs.deleteMatchedPlayersFromChallenge(userId, oppId);
            return fightDTO;
        }
        return new FightDTO(false, "No fight found for userId - " + userId, null);
    }
}

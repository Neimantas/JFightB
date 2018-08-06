package main.Services.Impl;

import main.Models.BL.TurnOutcomeModel;
import main.Models.BL.TurnStatsModel;
import main.Models.CONS.BodyParts;
import main.Models.CONS.FightStatus;
import main.Models.DAL.FightLogDAL;
import main.Models.DAL.FightResultDAL;
import main.Models.DTO.DBqueryDTO;
import main.Models.DTO.FightLogDTO;
import main.Services.IFightService;
import main.Services.IHigherService;

public class FightService implements IFightService {

    private IHigherService hs = new HigherService();

    private DBqueryDTO insertTurnFightLog(FightLogDAL model) {
        return hs.insertTurnStats(model);
    }

    private FightLogDAL createFightLogFromTurnStatsModel(TurnStatsModel model) {
        FightLogDAL fightLog = new FightLogDAL();
        fightLog.userId = model.userId;
        fightLog.fightId = model.fightId;
        fightLog.attack1 = model.att1 != null ? model.att1.toString() : "NONE";
        fightLog.attack2 = model.att2 != null ? model.att2.toString() : "NONE";
        fightLog.defence1 = model.def1 != null ? model.def1.toString() : "NONE";
        fightLog.defence2 = model.def2 != null ? model.def2.toString() : "NONE";
        fightLog.round = model.round;
        fightLog.hp = model.hp;
        return fightLog;
    }

    private TurnStatsModel createTurnStatsModelFromFightLog(FightLogDAL fightLog) {
        TurnStatsModel turnStatsModel = new TurnStatsModel();
        turnStatsModel.userId = fightLog.userId;
        turnStatsModel.fightId = fightLog.fightId;
        turnStatsModel.round = fightLog.round;
        turnStatsModel.att1 = BodyParts.valueOf(fightLog.attack1);
        turnStatsModel.att2 = BodyParts.valueOf(fightLog.attack2);
        turnStatsModel.def1 = BodyParts.valueOf(fightLog.defence1);
        turnStatsModel.def2 = BodyParts.valueOf(fightLog.defence2);
        turnStatsModel.hp = fightLog.hp;
        return turnStatsModel;
    }

    private FightLogDTO checkForOpponent(String fightId, int round) {
        FightLogDTO dto = hs.getFightLogByIdAndRound(fightId, round);
        // TODO we will need a timeout counter if we cannot get result or opponent leaves
        int count = 0;
        while ((!dto.success && dto.message.equals("Only one record found.")) && count < 30) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            dto = hs.getFightLogByIdAndRound(fightId, round);
            count++;
        }
        return dto;
    }

    public TurnOutcomeModel getStatsForRoundZero(TurnStatsModel userModel) {
        FightLogDTO dto = hs.getFightLogByIdAndRound(userModel.fightId, userModel.round);

        if (dto.success) {
            TurnOutcomeModel outcome = new TurnOutcomeModel();
            outcome.fightId = dto.list.get(0).fightId;
            outcome.round = dto.list.get(0).round;
            outcome.userId = userModel.userId;
            outcome.userName = userModel.userName;

            for (FightLogDAL dal : dto.list) {

                if (dal.userId == userModel.userId) {
                    outcome.userHp = dal.hp;
                } else {
                    outcome.oppId = dal.userId;
                    outcome.oppHp = dal.hp;
                }

            }

            // TODO handle errors
            outcome.oppName = hs.getUserByUserId(outcome.oppId).user.userName;
            outcome.fightStatus = FightStatus.FIGHTING;
            return outcome;
        }

        return new TurnOutcomeModel();
    }

    public TurnOutcomeModel getTurnOutcome(TurnStatsModel userModel) {

        DBqueryDTO dto = insertTurnFightLog(createFightLogFromTurnStatsModel(userModel));
        // TODO fix the null part once we can
        if (!dto.success) {
            return null;
        }

        // TODO handle timeout
        FightLogDTO fightLogDTO = checkForOpponent(userModel.fightId, userModel.round);

        if (!fightLogDTO.success) {
            return null;
        }

        TurnStatsModel opponent = null;
        for (FightLogDAL dal : fightLogDTO.list) {
            if (dal.userId != userModel.userId) {
                opponent = createTurnStatsModelFromFightLog(dal);
                opponent.userName = userModel.oppName;
                break;
            }
        }

        TurnOutcomeModel turnOutcome = calculateOutcome(userModel, opponent);
        turnOutcome.userName = userModel.userName;
        turnOutcome.userId = userModel.userId;
        turnOutcome.oppName = userModel.oppName;
        turnOutcome.oppId = userModel.oppId;

        fightCleanup(turnOutcome);

        return turnOutcome;
    }

    private TurnOutcomeModel calculateOutcome(TurnStatsModel user, TurnStatsModel opponent) {
        TurnOutcomeModel model = new TurnOutcomeModel();
        // TODO in the future damage variable will change depending on items/skills
        int damage = 1;

        // User outcome
        int attacksReceivedUser = 0;
        // Check if user defends against first Opponent attack
        if (user.def1 != opponent.att1 && user.def2 != opponent.att1) attacksReceivedUser++;
        // Check if user defends against second Opponent attack
        if (user.def1 != opponent.att2 && user.def2 != opponent.att2) attacksReceivedUser++;

        model.userHp = user.hp - (attacksReceivedUser * damage);

        // Opponent outcome
        int attacksReceivedOpp = 0;
        if (opponent.def1 != user.att1 && opponent.def2 != user.att1) attacksReceivedOpp++;
        if (opponent.def1 != user.att2 && opponent.def2 != user.att2) attacksReceivedOpp++;

        model.oppHp = opponent.hp - (attacksReceivedOpp * damage);
        model.round = user.round + 1;

        // TODO create FIGHT LOG

        if (model.userHp <= 0 && model.oppHp <= 0){
            model.fightStatus = FightStatus.DRAW;
        } else if (model.userHp <= 0) {
            model.fightStatus = FightStatus.LOSER;
        } else if (model.oppHp <= 0) {
            model.fightStatus = FightStatus.WINNER;
        } else {
            model.fightStatus = FightStatus.FIGHTING;
        }
        return model;
    }

    private void fightCleanup(TurnOutcomeModel outcome){
        // First check if Winner has already been inserted!
        // If he was then you can safely delete Fight, FightLog records with that FightId;
        if (outcome.fightStatus != FightStatus.FIGHTING) {
            FightResultDAL fightResult = new FightResultDAL();
            fightResult.fightId = outcome.fightId;

            if (outcome.fightStatus == FightStatus.WINNER) {
                fightResult.winnerId = outcome.userId;
                fightResult.loserId = outcome.oppId;
            } else if (outcome.fightStatus == FightStatus.LOSER) {
                fightResult.winnerId = outcome.oppId;
                fightResult.loserId = outcome.userId;
            } else if (outcome.fightStatus == FightStatus.DRAW){
                fightResult.draw = 1;
            }

            hs.insertFightResults(fightResult);
            hs.deleteAllFightLogsByFightId(outcome.fightId);
        }

    }

}

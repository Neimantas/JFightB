package main.Services.Impl;

import main.Models.BL.TurnOutcomeModel;
import main.Models.BL.TurnStatsModel;
import main.Models.CONS.BodyParts;
import main.Models.CONS.FightStatus;
import main.Models.DTO.DBqueryDTO;
import main.Services.IFightService;
import main.Services.IHigherService;

import java.util.List;

public class FightService implements IFightService {

    private IHigherService hs = new HigherService();

    private DBqueryDTO sendFightStats(TurnStatsModel model) {
        return hs.insertTurnStats(model);
    }

    private DBqueryDTO checkForOpponent(TurnStatsModel model) {
        DBqueryDTO dto = hs.getFightLogByIdAndRound(model);
        // TODO we will need a timeout counter if we cannot get result or opponent leaves
        while (!dto.success) {
            dto = hs.getFightLogByIdAndRound(model);
            if (!dto.success) {
                try {
                    Thread.sleep(1000);
                    System.out.println("Woke up after 1s sleep.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return dto;
    }

    public TurnOutcomeModel getTurnOutcome(TurnStatsModel userModel) {
        DBqueryDTO dto = sendFightStats(userModel);
        // TODO fix the null part once we can
        if (!dto.success) {
            return null;
        }
        // TODO handle timeout
        dto = checkForOpponent(userModel);
        TurnStatsModel opponent = new TurnStatsModel();
        List<List<Object>> list = dto.list;
        for (int i = 0; i < list.size(); i++) {
            List<Object> columns = list.get(i);
            if (Integer.parseInt(columns.get(1).toString()) != userModel.userId) {
                opponent.fightId = columns.get(0).toString();
                opponent.userId = Integer.parseInt(columns.get(1).toString());
                opponent.userName = userModel.oppName;
                opponent.att1 = BodyParts.valueOf(columns.get(2).toString());
                opponent.att2 = BodyParts.valueOf(columns.get(3).toString());
                opponent.def1 = BodyParts.valueOf(columns.get(4).toString());
                opponent.def2 = BodyParts.valueOf(columns.get(5).toString());
                opponent.hp = Integer.parseInt(columns.get(6).toString());
                opponent.round = Integer.parseInt(columns.get(7).toString());
                break;
            }
        }
        TurnOutcomeModel model = calculateOutcome(userModel, opponent);
        model.userName = userModel.userName;
        model.userId = userModel.userId;
        model.oppName = userModel.oppName;
        model.oppId = userModel.oppId;
        // TODO if model.fightStatus != FIGHTING -> hs.insertWinner()...
        return model;
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

    public TurnOutcomeModel getStatsForRound(TurnStatsModel userModel) {
        DBqueryDTO dto = hs.getFightLogByIdAndRound(userModel);
        if (dto.success) {
            TurnOutcomeModel outcome = new TurnOutcomeModel();
            List<Object> columns;
            for (int i = 0; i < dto.list.size(); i++) {
                columns = dto.list.get(i);
                if (Integer.parseInt(columns.get(1).toString()) == userModel.userId) {
                    outcome.fightId = columns.get(0).toString();
                    outcome.userHp = Integer.parseInt(columns.get(6).toString());
                    outcome.round = 0;
                    outcome.userId = userModel.userId;
                } else {
                    outcome.oppId = Integer.parseInt(columns.get(1).toString());
                    outcome.oppHp = Integer.parseInt(columns.get(6).toString());
                }
            }
            // TODO you know the drill....
            outcome.userName = hs.getUserNameByUserId(outcome.userId).user.userName;
            outcome.oppName = hs.getUserNameByUserId(outcome.oppId).user.userName;
            outcome.fightStatus = FightStatus.FIGHTING;
            return outcome;
        }
        // TODO return not null...
        return null;
    }
}

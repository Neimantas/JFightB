package main.Services.Impl;

import main.Models.BL.TurnStatsModel;
import org.junit.Test;

import static org.junit.Assert.*;

public class FightServiceTest {

    FightService fightService = new FightService();

    @Test
    public void getTurnOutcome() {
        assertNotNull(fightService.getTurnOutcome(new TurnStatsModel()));
    }

    @Test
    public void getStatsForRound() {
        assertNotNull(fightService.getStatsForRound(new TurnStatsModel()));
    }
}
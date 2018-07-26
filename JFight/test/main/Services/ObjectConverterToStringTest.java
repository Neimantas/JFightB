package main.Services;

import main.Models.BL.TurnOutcomeModel;
import main.Models.CONS.FightStatus;
import org.junit.Test;

import java.util.Map;

public class ObjectConverterToStringTest {

    @Test
    public void listOfObjectsToStringList() {
        TurnOutcomeModel model = new TurnOutcomeModel();
        model.userId = 1;
        model.round = 2;
        model.fightStatus = FightStatus.FIGHTING;
        Map map = ObjectConverterToString.objectToStringMap(model);
    }

    @Test
    public void objectToStringMap() {
    }
}
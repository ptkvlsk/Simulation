package actions;

import model.Creature;
import model.GameMap;

import java.util.List;

public class MoveCreaturesActions implements Action{
    @Override
    public void execute(GameMap map) {
        List<Creature> creatures = map.getEntitiesBy(Creature.class);
        for (Creature creature: creatures){
            creature.makeMove(map);
        }

    }
}

package actions;

import model.GameMap;
import model.Predator;

import java.util.List;

public class MovePredatorAction implements Action {

    @Override
    public void execute(GameMap map) {
        List<Predator> predators = map.getEntitiesBy(Predator.class);
        for (Predator p : predators) {
            p.makeMove(map);
        }
    }
}

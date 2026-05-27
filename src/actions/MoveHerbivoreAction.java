package actions;

import model.GameMap;
import model.Herbivore;

import java.util.List;

public class MoveHerbivoreAction implements Action {

    @Override
    public void execute(GameMap map) {
        List<Herbivore> herbivores = map.getEntitiesBy(Herbivore.class);
        for (Herbivore h : herbivores) {
            h.makeMove(map);
        }
    }
}

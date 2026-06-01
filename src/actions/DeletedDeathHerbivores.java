package actions;

import model.GameMap;
import model.Herbivore;

import java.util.List;

public class DeletedDeathHerbivores implements Action {

    @Override
    public void execute(GameMap map) {
        List<Herbivore> herbivores = map.getEntitiesBy(Herbivore.class);
        for (Herbivore h : herbivores) {
            if (h.getHp() <= 0) {
                map.getEntityAt(h.getPosition()).ifPresent(entity -> map.removeEntity(h.getPosition()));
            }
        }
    }
}

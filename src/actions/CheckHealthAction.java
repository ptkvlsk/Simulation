package actions;

import model.GameMap;
import model.Herbivore;

import java.util.List;

public class CheckHealthAction implements Action {
    @Override
    public void execute(GameMap map) {
        List<Herbivore> herbivores = map.getAllHerbivores();
        for (Herbivore h : herbivores) {
            if (h.getHp() <= 0) {
                map.removeEntity(h.getPosition());
            }
        }
    }
}

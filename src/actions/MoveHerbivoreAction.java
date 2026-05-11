package actions;

import model.GameMap;
import model.Herbivore;

import java.util.List;

public class MoveHerbivoreAction implements Action{

    @Override
    public void execute(GameMap map) {
        List<Herbivore> herbivores = map.getAllHerbivores();
        for (Herbivore h: herbivores){
            h.makeMove(map);
            System.out.println("Herbivore at " + h.position + " HP: " + h.hp);
        }
    }
}

package actions;

import model.GameMap;
import model.Predator;

import java.util.List;

public class MovePredotorAction implements Action{
    @Override
    public void execute(GameMap map) {
        List<Predator> predators = map.getAllPredators();
        for (Predator p:predators){
            p.makeMove(map);
        }
    }
}

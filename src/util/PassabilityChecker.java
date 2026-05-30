package util;

import model.*;

import java.awt.*;
import java.util.Optional;

public class PassabilityChecker {
    public boolean isPassible(GameMap map, Point position, Class<?> targetType) {
        Optional<Entity> optEntity = map.getEntityAt(position);
        if (optEntity.isEmpty()) return true;

        Entity entity = optEntity.get();
        if (targetType.isInstance(entity)) return true;
        if (entity instanceof Predator) return false;
        if (entity instanceof Grass) return false;
        if (entity instanceof Herbivore) return false;
        return !(entity instanceof Rock) && !(entity instanceof Tree);
    }
}

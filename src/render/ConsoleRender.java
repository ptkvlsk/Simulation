package render;

import model.*;

import java.awt.*;
import java.util.Optional;

public class ConsoleRender implements Render {
    public void render(GameMap map) {
        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                Point point = new Point(x, y);
                Optional<Entity> optEntity = map.getEntityAt(point);
                if (optEntity.isEmpty()) {
                    System.out.print("\uD83D\uDFEB ");
                    continue;
                }
                Entity entity = optEntity.get();
                if (entity instanceof Grass) {
                    System.out.print("\uD83C\uDF3F ");
                } else if (entity instanceof Herbivore) {
                    System.out.print("\uD83D\uDC11 ");
                } else if (entity instanceof Predator) {
                    System.out.print("\uD83D\uDC3A ");
                } else if (entity instanceof Rock) {
                    System.out.print("\uD83E\uDEA8 ");
                } else if (entity instanceof Tree) {
                    System.out.print("\uD83C\uDF32 ");
                }
            }
            System.out.println();
        }
        System.out.println("Herbivores count: " + map.getEntitiesBy(Herbivore.class).size());
    }
}

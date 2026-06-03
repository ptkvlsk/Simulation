package util;

import java.awt.*;
import java.util.List;

public final class Direction {
    public static final List<Point> NEIGHBOR_OFFSETS = List.of(new Point(-1, 0),
            new Point(1, 0),
            new Point(0, -1),
            new Point(0, 1));

    private Direction() {
    }
}

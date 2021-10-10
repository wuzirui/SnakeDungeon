package org.tztyun.SnakeDungeon;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;

public class SnakeDungeonUtils {
    public static Pair<Integer, Integer> Coordinate2Pixel(Integer x, Integer y) {
        return new Pair<Integer, Integer>(
                x * MapVisualize.blockWidth + MapVisualize.mapMargin,
                y * MapVisualize.blockHeight + MapVisualize.mapMargin);
    }

    private static final Map<Direction, Direction> opposite = new HashMap<Direction,Direction>() {{
        put(Direction.UP, Direction.DOWN);
        put(Direction.DOWN, Direction.UP);
        put(Direction.LEFT, Direction.RIGHT);
        put(Direction.RIGHT, Direction.LEFT);
    }};

    private static final Map<Direction, Pair<Integer, Integer>> delta = new HashMap<Direction, Pair<Integer,Integer>>() {{
        put(Direction.UP, new Pair<Integer, Integer>(0, -1));
        put(Direction.DOWN, new Pair<Integer, Integer>(0, 1));
        put(Direction.LEFT, new Pair<Integer, Integer>(-1, 0));
        put(Direction.RIGHT, new Pair<Integer, Integer>(1, 0));
    }};

    public static Direction getOppositeDirection(Direction current) {
        return opposite.get(current);
    }

    public static Pair<Integer, Integer> getDelta(Direction direction) {
        return delta.get(direction);
    }
}

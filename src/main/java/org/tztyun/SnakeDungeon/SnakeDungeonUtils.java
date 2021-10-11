package org.tztyun.SnakeDungeon;

import com.almasb.fxgl.entity.Entity;
import javafx.util.Pair;
import org.tztyun.SnakeDungeon.Components.UnifiedBlockInfoComponent;

import java.util.*;

public class SnakeDungeonUtils {
    private static Random random;
    static {
        Date date = new Date();
        random = new Random(date.getTime());
    }
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

   public static boolean checkIfAvailable(int x, int y, LinkedList<Entity> Snake) {
        if (x < 0 || x >= MapInfo.mapWidth || y < 0 || y >= MapInfo.mapHeight) {
            return false;
        }
        if (MapInfo.getBlockType(x, y) == BlockType.Wall) return false;
        for (Entity e : Snake) {
            var BlockInfo = e.getComponent(UnifiedBlockInfoComponent.class);
            if (x == BlockInfo.getPosX() && y == BlockInfo.getPosY()) return false;
        }
        return true;
    }

    public static Pair<Integer, Integer> randomSpaceInMap(LinkedList<Entity> Snake) {
        int x = 0, y = 0;
        do {
            x = random.nextInt(MapInfo.mapWidth);
            y = random.nextInt(MapInfo.mapHeight);
        } while (!checkIfAvailable(x, y, Snake));
        return new Pair<Integer, Integer>(x, y);
    }
}

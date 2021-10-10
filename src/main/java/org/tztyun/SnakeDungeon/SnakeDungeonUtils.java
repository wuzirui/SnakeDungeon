package org.tztyun.SnakeDungeon;

import javafx.util.Pair;

public class SnakeDungeonUtils {
    public static Pair<Integer, Integer> Coordinate2Piexel(Integer x, Integer y) {
        return new Pair<Integer, Integer>(
                x * MapInfo.blockWidth + MapInfo.mapMargin,
                y * MapInfo.blockHeight + MapInfo.mapMargin);
    }
}

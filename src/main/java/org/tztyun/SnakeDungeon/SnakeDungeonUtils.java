package org.tztyun.SnakeDungeon;

import javafx.util.Pair;

public class SnakeDungeonUtils {
    public static Pair<Integer, Integer> Coordinate2Piexel(Integer x, Integer y) {
        return new Pair<Integer, Integer>(
                x * Configures.blockWidth + Configures.mapMargin,
                y * Configures.blockHeight + Configures.mapMargin);
    }
}

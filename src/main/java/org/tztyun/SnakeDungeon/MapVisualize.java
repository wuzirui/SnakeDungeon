package org.tztyun.SnakeDungeon;

import javafx.scene.paint.Color;
import java.util.HashMap;
import java.util.Map;

public class MapVisualize {
    public static final int blockWidth = 25;
    public static final int blockHeight = 25;
    public static final int mapMargin = 10;

    public static final Map<BlockType, Color> blockColor = new HashMap<BlockType, Color>() {{
        put(BlockType.Floor, Color.gray(0.9));
        put(BlockType.Wall, Color.GRAY);
        put(BlockType.SnakeBody, Color.CADETBLUE);
        put(BlockType.Food, Color.DARKRED);
    }};
}

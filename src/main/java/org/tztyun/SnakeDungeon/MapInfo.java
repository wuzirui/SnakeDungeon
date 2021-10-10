package org.tztyun.SnakeDungeon;

import javafx.scene.control.Alert;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import static javafx.application.Platform.exit;

public class MapInfo {
    public static int mapWidth = 2;
    public static int mapHeight = 3;

    public static int snakeStartXPos;
    public static int snakeStartYPos;
    public static Direction snakeStartDirection;

    public static int blockPadding = 1;
    public static JSONObject mapJson;

    private static String[][] blockTypes;

    public static void initMapSettings(String filename) throws IOException {
        File jsonFile = new File(filename);
        String content = new String("{}");
        try{
            content = FileUtils.readFileToString(jsonFile, "UTF-8");
        }
        catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("地图读入失败: " + e.getMessage());
            alert.setTitle("IOException");
            alert.show();
            exit();
        }
        mapJson = new JSONObject(content);
        var basicInfo = mapJson.getJSONObject("BasicInfo");
        mapWidth = basicInfo.getInt("MapWidth");
        mapHeight = basicInfo.getInt("MapHeight");

        initMapBlocks();

        var snakeInfo = mapJson.getJSONObject("SnakeInfo");
        snakeStartXPos = snakeInfo.getInt("StartXPos");
        snakeStartYPos = snakeInfo.getInt("StartYPos");
        snakeStartDirection = Direction.valueOf(snakeInfo.getString("StartDirection"));
    }

    private static void initMapBlocks() {
        assert(!mapJson.isEmpty());
        var rows = mapJson.getJSONArray("Blocks");
        assert(rows.length() == mapWidth);

        blockTypes = new String[mapWidth][mapHeight];

        for (int i = 0; i != mapWidth; i++) {
            assert(rows.getJSONArray(i).length() == mapHeight);
            for (int j = 0; j != mapHeight; j++) {
                blockTypes[i][j] = loadBlockTypeFromFile(i, j);
            }
        }
    }

    public static String loadBlockTypeFromFile(int x, int y) {
        assert(!mapJson.isEmpty());
        var rows = mapJson.getJSONArray("Blocks");
        return rows.getJSONArray(x).getString(y);
    }

    public static BlockType getBlockType(int x, int y) {
        assert(!mapJson.isEmpty());
        return BlockType.valueOf(blockTypes[x][y]);
    }
}

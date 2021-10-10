package org.tztyun.SnakeDungeon;

import javafx.scene.control.Alert;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import static javafx.application.Platform.exit;

public class MapInfo{
    public static int blockWidth = 25;
    public static int blockHeight = 25;
    public static int mapWidth = 2;
    public static int mapHeight = 3;

    public static int mapMargin = 10;
    public static int blockPadding = 1;
    public static JSONObject mapJson;

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
    }
}

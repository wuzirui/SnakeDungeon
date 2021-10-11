package org.tztyun.SnakeDungeon;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.input.Input;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.tztyun.SnakeDungeon.Components.UnifiedBlockInfoComponent;

import java.io.IOException;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameTimer;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

public class SnakeDungeonGameApp extends GameApplication {
    public SnakeDungeonGameApp() {
        try {
            MapInfo.initMapSettings("system/map.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initSettings(GameSettings settings) {
        var pos = SnakeDungeonUtils.Coordinate2Pixel(MapInfo.mapWidth, MapInfo.mapHeight);
        settings.setWidth(pos.getKey() + MapVisualize.mapMargin);
        settings.setHeight(pos.getValue() + MapVisualize.mapMargin);
        settings.setTitle("Snake Dungeon");
        settings.setVersion("0.1.0");
    }

    private Entity player;
    private Deque<Entity> snakeBody;
    private Entity[][] gameMap;
    private Direction curDirection;

    private void initMap() {
        gameMap = new Entity[MapInfo.mapWidth][MapInfo.mapHeight];
        for (int i = 0; i != MapInfo.mapWidth; i++) {
            for (int j = 0; j != MapInfo.mapHeight; j++) {
                var pos = SnakeDungeonUtils.Coordinate2Pixel(i, j);
                gameMap[i][j] = FXGL.spawn("Block", new SpawnData(pos.getKey(), pos.getValue())
                        .put("Type", MapInfo.getBlockType(i, j))
                        .put("xPos", i)
                        .put("yPos", j));
            }
        }
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new SnakeDungeonFactory());
        initMap();
        initSnake();
        System.out.println("Start timer");
        getGameTimer().runAtInterval(()->{
            snakeForward(curDirection);
        }, Duration.millis(300));
    }

    private void initSnake() {
        int xpos = MapInfo.snakeStartXPos;
        int ypos = MapInfo.snakeStartYPos;
        snakeBody = new LinkedList<Entity>();
        snakeBody.addFirst(placeSnakeBody(xpos, ypos));
        var delta = SnakeDungeonUtils.getDelta(MapInfo.snakeStartDirection);
        for (int i = 0; i != 2; i++) {
            var newBlock = placeSnakeBody(xpos += delta.getKey(), ypos += delta.getValue());
            snakeBody.addFirst(newBlock);
        }
        curDirection = MapInfo.snakeStartDirection;
    }

    private Entity placeSnakeBody(int xpos, int ypos) {
        var pos = SnakeDungeonUtils.Coordinate2Pixel(xpos, ypos);

        return FXGL.spawn("SnakeBlock", new SpawnData(pos.getKey(), pos.getValue())
                .put("xPos", xpos)
                .put("yPos", ypos) );
    }

    private void snakeForward(Direction direction) {
        var head = snakeBody.getFirst();
        var last = snakeBody.removeLast();
        getGameWorld().removeEntity(last);
        var delta = SnakeDungeonUtils.getDelta(direction);
        int xpos = head.getComponent(UnifiedBlockInfoComponent.class).getPosX() + delta.getKey();
        int ypos = head.getComponent(UnifiedBlockInfoComponent.class).getPosY() + delta.getValue();

        var newBlock = placeSnakeBody(xpos, ypos);
        snakeBody.addFirst(newBlock);
    }

    @Override
    protected void initInput() {
        Input input = FXGL.getInput();

    }

    @Override
    protected void initUI() {
        Text textPixels = new Text();
        textPixels.setTranslateX(50);
        textPixels.setTranslateY(100);
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
    }

    public static void main(String[] args) {
        launch(args);
    }
}

package org.tztyun.SnakeDungeon;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.input.Input;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.tztyun.SnakeDungeon.Components.UnifiedBlockInfoComponent;

import java.io.IOException;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.getDialogService;
import static com.almasb.fxgl.dsl.FXGL.getGameController;
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

    private int mapBoundaryRight;
    private int mapBoundaryDown;
    @Override
    protected void initSettings(GameSettings settings) {
        var pos = SnakeDungeonUtils.Coordinate2Pixel(MapInfo.mapWidth, MapInfo.mapHeight);
        mapBoundaryRight = pos.getKey();
        mapBoundaryDown = pos.getValue();
        settings.setWidth(mapBoundaryRight + MapVisualize.mapMargin);
        settings.setHeight(mapBoundaryDown + 50 + MapVisualize.mapMargin);
        settings.setTitle("Snake Dungeon");
        settings.setVersion("0.1.0");
    }

    private Entity player;
    private Deque<Entity> snakeBody;
    private Entity[][] gameMap;
    private Direction curDirection, nextDirection;

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

    private Entity findFoodAt(int x, int y) {
        var foodList = FXGL.getGameWorld().getEntitiesByType(BlockType.Food);
        for (var food : foodList) {
            var info = food.getComponent(UnifiedBlockInfoComponent.class);
            if (x == info.getPosX() && y == info.getPosY()) return food;
        }
        return null;
    }

    private void updateSnakeStatus() {
        var head = snakeBody.getFirst();
        var last = snakeBody.getLast();
        var delta = SnakeDungeonUtils.getDelta(nextDirection);
        curDirection = nextDirection;
        int xpos = head.getComponent(UnifiedBlockInfoComponent.class).getPosX() + delta.getKey();
        int ypos = head.getComponent(UnifiedBlockInfoComponent.class).getPosY() + delta.getValue();

        if (SnakeDungeonUtils.checkIfAvailable(xpos, ypos, (LinkedList<Entity>) snakeBody)) {
            var newBlock = placeSnakeBody(xpos, ypos);
            snakeBody.addFirst(newBlock);
        }
        else {
            FXGL.inc("score", -1);
            if (snakeBody.size() == 1) {
                getDialogService().showMessageBox("You Died", getGameController()::exit);
            }
        }

        var food = findFoodAt(xpos, ypos);
        if (food != null) {
            FXGL.inc("score", 1);
            FXGL.getGameWorld().removeEntity(food);
            generateRandomFood();
        }
        else {
            getGameWorld().removeEntity(snakeBody.removeLast());
        }

    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new SnakeDungeonFactory());
        initMap();
        initSnake();
        System.out.println("Start timer");
        getGameTimer().runAtInterval(() -> {
            updateSnakeStatus();

        }, Duration.millis(150));
        generateRandomFood();
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
        nextDirection = curDirection;
    }

    private Entity placeSnakeBody(int xpos, int ypos) {
        var pos = SnakeDungeonUtils.Coordinate2Pixel(xpos, ypos);

        return FXGL.spawn("SnakeBlock", new SpawnData(pos.getKey(), pos.getValue())
                .put("xPos", xpos)
                .put("yPos", ypos));
    }

    private void tryTurning(Direction to) {
        if (to != SnakeDungeonUtils.getOppositeDirection(curDirection)) {
            nextDirection = to;
        }
    }

    private void generateRandomFood() {
        var pos = SnakeDungeonUtils.randomSpaceInMap((LinkedList<Entity>) snakeBody);
        var pixelPos = SnakeDungeonUtils.Coordinate2Pixel(pos.getKey(), pos.getValue());
        FXGL.spawn("FoodBlock", new SpawnData(pixelPos.getKey(), pixelPos.getValue())
                .put("xPos", pos.getKey())
                .put("yPos", pos.getValue()));
    }

    @Override
    protected void initInput() {
        Input input = FXGL.getInput();
        FXGL.onKey(KeyCode.A, () -> {
            tryTurning(Direction.LEFT);
        });
        FXGL.onKey(KeyCode.D, () -> {
            tryTurning(Direction.RIGHT);
        });
        FXGL.onKey(KeyCode.W, () -> {
            tryTurning(Direction.UP);
        });
        FXGL.onKey(KeyCode.S, () -> {
            tryTurning(Direction.DOWN);
        });
    }

    @Override
    protected void initUI() {
        Text textScore = new Text();
        textScore.setTranslateX(50);
        textScore.setTranslateY(mapBoundaryDown + 15 + MapVisualize.mapMargin);

        FXGL.getGameScene().addUINode(textScore);
        textScore.textProperty().bind(FXGL.getWorldProperties().intProperty("score").asString());
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("score", 0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

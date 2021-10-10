package org.tztyun.SnakeDungeon;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.view.TextViewComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.input.UserAction;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Deque;
import java.util.Map;
import java.util.Vector;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

public class SnakeDungeonGameApp extends GameApplication {

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(800);
        settings.setHeight(600);
        settings.setTitle("Snake Dungeon");
        settings.setVersion("0.1.0");
    }

    private Entity player;
    private Deque<Entity> snakeBody;
    private Entity[][] gameMap;

    private void initMap() {
        gameMap = new Entity[Configures.mapWidth][Configures.mapHeight];
        for (int i = 0; i != Configures.mapWidth; i++) {
            for (int j = 0; j != Configures.mapHeight; j++) {
                var pos = SnakeDungeonUtils.Coordinate2Piexel(i, j);
                gameMap[i][j] = FXGL.spawn("Block", new SpawnData(pos.getKey(), pos.getValue()));
            }
        }
    }
    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new SnakeDungeonFactory());
        player = FXGL.entityBuilder()
                .at(300, 300)
                .view(new Rectangle(25, 25, Color.BLUE))
                .buildAndAttach();
        initMap();
    }

    @Override
    protected void initInput() {
        Input input = FXGL.getInput();

        FXGL.onKey(KeyCode.W, () -> {
            player.translateY(-5);
            FXGL.inc("pixelsMoved", +5);
        });
        FXGL.onKey(KeyCode.A, () -> {
            player.translateX(-5);
            FXGL.inc("pixelsMoved", +5);
        });
        FXGL.onKey(KeyCode.S, () -> {
            player.translateY(5);
            FXGL.inc("pixelsMoved", +5);
        });
        FXGL.onKey(KeyCode.D, () -> {
            player.translateX(5);
            FXGL.inc("pixelsMoved", +5);
        });
    }

    @Override
    protected void initUI() {
        Text textPixels = new Text();
        textPixels.setTranslateX(50);
        textPixels.setTranslateY(100);

        FXGL.getGameScene().addUINode(textPixels);

        textPixels.setFont(Font.font("Consolas"));
        textPixels.textProperty().bind(FXGL.getWorldProperties().intProperty("pixelsMoved").asString());
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("pixelsMoved", 0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

package org.tztyun.SnakeDungeon;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;

public class SnakeDungeonFactory implements EntityFactory {
//    @Spawns("Snake")
//    public Entity newSnake(SpawnData data) {
//
//    }

    @Spawns("Block")
    public Entity newPlainBlock(SpawnData data) {
        return entityBuilder(data)
                .at(data.getX() + Configures.blockPadding, data.getY() + Configures.blockPadding)
                .type(BlockType.Floor)
                .view(new Rectangle(Configures.blockWidth - 2 * Configures.blockPadding
                        , Configures.blockHeight - 2 * Configures.blockPadding, Color.gray(0.9)))
                .buildAndAttach();
    }
}

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
                .at(data.getX() + MapInfo.blockPadding, data.getY() + MapInfo.blockPadding)
                .type(BlockType.Floor)
                .view(new Rectangle(MapInfo.blockWidth - 2 * MapInfo.blockPadding ,
                        MapInfo.blockHeight - 2 * MapInfo.blockPadding, Color.gray(0.9)))
                .buildAndAttach();
    }
}

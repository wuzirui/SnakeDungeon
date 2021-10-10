package org.tztyun.SnakeDungeon;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import javafx.scene.shape.Rectangle;
import org.jetbrains.annotations.NotNull;
import org.tztyun.SnakeDungeon.Components.UnifiedBlockInfoComponent;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;

public class SnakeDungeonFactory implements EntityFactory {

    @Spawns("Block")
    public Entity newPlainBlock(SpawnData data) {
        int xpos = data.get("xPos");
        int ypos = data.get("yPos");
        var type = MapInfo.getBlockType(xpos, ypos);
        return getBlockEntity(data, xpos, ypos, type);
    }

    @Spawns("SnakeBlock")
    public Entity newSnakeBlock(SpawnData data) {
        int xpos = data.get("xPos");
        int ypos = data.get("yPos");
        var type = BlockType.SnakeBody;
        return getBlockEntity(data, xpos, ypos, type);
    }

    @NotNull
    private Entity getBlockEntity(SpawnData data, int xpos, int ypos, BlockType type) {
        var color = MapVisualize.blockColor.get(type);

        return entityBuilder(data)
                .at(data.getX() + MapInfo.blockPadding, data.getY() + MapInfo.blockPadding)
                .type(type)
                .view(new Rectangle(MapVisualize.blockWidth - 2 * MapInfo.blockPadding ,
                        MapVisualize.blockHeight - 2 * MapInfo.blockPadding, color))
                .with(new UnifiedBlockInfoComponent(xpos, ypos, type))
                .build();
    }

}

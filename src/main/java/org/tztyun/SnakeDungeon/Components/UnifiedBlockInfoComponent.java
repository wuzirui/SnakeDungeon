package org.tztyun.SnakeDungeon.Components;

import com.almasb.fxgl.entity.component.Component;
import org.tztyun.SnakeDungeon.BlockType;

public class UnifiedBlockInfoComponent extends Component {
    private int posX;
    private int posY;
    private BlockType type;

    public UnifiedBlockInfoComponent(int posX, int posY, BlockType type) {
        this.posX = posX;
        this.posY = posY;
        this.type = type;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public BlockType getType() {
        return type;
    }
}

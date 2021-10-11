package org.tztyun.SnakeDungeon;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


class MapInfoTest {
    @BeforeEach
    void setUp() {
        try{
            MapInfo.initMapSettings("system/map.json");
        }
        catch (IOException e) {
            fail("Load Failed");
        }
    }

    @Test
    void initMapSettings() {
        assertEquals(MapInfo.mapWidth, 25);
        assertEquals(MapInfo.mapHeight, 25);
        assertEquals(MapInfo.snakeStartXPos, 12);
        assertEquals(MapInfo.snakeStartYPos, 13);
    }

    @Test
    void loadBlockTypeFromFile() {
        assertEquals(MapInfo.loadBlockTypeFromFile(0, 0), "Wall");
        assertEquals(MapInfo.loadBlockTypeFromFile(24, 24), "Wall");
        assertEquals(MapInfo.loadBlockTypeFromFile(1, 1), "Floor");
    }
}
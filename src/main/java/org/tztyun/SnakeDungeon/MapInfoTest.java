package org.tztyun.SnakeDungeon;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class MapInfoTest {
    @Test
    void initMapSettings() {
        try{
            MapInfo.initMapSettings("system/map.json");
        }
        catch (IOException e) {
            fail("Load Failed");
        }
        assertEquals(MapInfo.mapWidth, 25);
        assertEquals(MapInfo.mapHeight, 25);
    }
}
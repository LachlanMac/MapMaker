package com.lmac.mapmaker.main.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import com.lmac.mapmaker.main.biomes.Biome;
import com.lmac.mapmaker.main.biomes.BiomeEngine;
import com.lmac.mapmaker.main.components.Map;
import com.lmac.mapmaker.main.engines.RiverEngine;

public class DataMap {

	Map terrainMap, weatherMap, temperatureMap, forestMap;
	TileData[][] tileDataMap;
	int mapHeight, mapWidth;

	public DataMap(Map terrainMap, Map weatherMap, Map temperatureMap, Map forestMap) {
		this.mapHeight = terrainMap.getHeight();
		this.mapWidth = terrainMap.getWidth();
		tileDataMap = new TileData[mapWidth][mapHeight];
		this.forestMap = forestMap;
	}

	public TileData getTile(int x, int y) {
		return tileDataMap[x][y];
	}

	public void setTile(TileData tile) {

		tileDataMap[tile.getX()][tile.getY()] = tile;
	}
	public int getHeight() {
		return mapHeight;
	}

	public int getWidth() {
		return mapWidth;
	}

	public void adjustTemperatureForHeight() {

		float tempHeightFactor = 0.08f;

		for (int y = 0; y < mapHeight; y++) {
			for (int x = 0; x < mapWidth; x++) {

				TileData d = tileDataMap[x][y];

				int height = d.getHeight();
				int temperature = d.getTemperature();
				int humidity = d.getHumidity();
				float newTemperature = temperature - ((float) height * tempHeightFactor);

				int tempInt = (int) Math.max(newTemperature, 0);

				tileDataMap[x][y].setTemperature(tempInt);
				tileDataMap[x][y].setBiome(BiomeEngine.getBiome((int) height, humidity, tempInt));

			}
		}

	}

	public TileData[][] getTileArray() {
		return tileDataMap;
	}

	public void exportDataMap(String path) {

		try {
			BufferedWriter br = new BufferedWriter(new FileWriter(new File(path)));
			br.write("w=" + mapWidth + "<" + "h=" + mapHeight);
			for (int y = 0; y < mapHeight; y++) {
				for (int x = 0; x < mapWidth; x++) {
					br.write(getTile(x, y).encodeData());
				}
			}

			br.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

}

package com.lmac.mapmaker.main.data;

import com.lmac.mapmaker.main.biomes.BiomeEngine;
import com.lmac.mapmaker.main.components.Map;

public class DataMap {

	Map terrainMap, weatherMap, temperatureMap;
	TileData[][] tileDataMap;
	int mapHeight, mapWidth;
	BiomeEngine be;

	public DataMap(Map terrainMap, Map weatherMap, Map temperatureMap) {
		this.mapHeight = terrainMap.getHeight();
		this.mapWidth = terrainMap.getWidth();
		tileDataMap = new TileData[mapWidth][mapHeight];
		be = new BiomeEngine();
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
				// System.out.println("HEIGHT : " + height + " OLD TEMP : " + temperature + "
				// NEW TEMP : " + (int)f);

				int tempInt = (int) Math.max(newTemperature, 0);

				tileDataMap[x][y].setTemperature(tempInt);
				tileDataMap[x][y].setBiome(be.getBiome((int) height, humidity, tempInt));

			}
		}

	}

	public void checkForLakes(int lakeSize) {

		for (int mapY = 0; mapY < mapHeight - lakeSize; mapY++) {
			for (int mapX = 0; mapX < mapWidth - lakeSize; mapX++) {

				if (tileDataMap[mapX][mapY].getBiome().getName().equals("Ocean")
						|| tileDataMap[mapX][mapY].getBiome().getName().equals("Deep Ocean")) {
					tileDataMap[mapX][mapY].setIsPotentialLake(true);
				}

			}
		}

		for (int mapY = 0; mapY < mapHeight - lakeSize; mapY += 5) {
			for (int mapX = 0; mapX < mapWidth - lakeSize; mapX += 5) {

				if (!tileDataMap[mapX][mapY].isPotentialLake()) {
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
				}

			}
		}

	}

}

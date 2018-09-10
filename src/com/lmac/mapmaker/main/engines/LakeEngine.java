package com.lmac.mapmaker.main.engines;

import java.util.ArrayList;

import com.lmac.mapmaker.main.biomes.Biome;
import com.lmac.mapmaker.main.data.DataMap;
import com.lmac.mapmaker.main.data.Lake;

public class LakeEngine {

	DataMap map;
	private int mapHeight, mapWidth;
	private int smallLakeSize = 100;
	private int largeLakeSize = 500;
	private boolean thickenRivers = false;
	ArrayList<Lake> lakes;

	public LakeEngine() {
		this.lakes = new ArrayList<Lake>();
	}

	public void checkForLakes(DataMap map, int lakeSize, Biome setToBiome) {
		this.mapHeight = map.getHeight();
		this.mapWidth = map.getWidth();
		for (int mapY = 0; mapY < mapHeight - lakeSize; mapY += 20) {
			for (int mapX = 0; mapX < mapWidth - lakeSize; mapX += 20) {

				if (!map.getTileArray()[mapX][mapY].getBiome().isUncheckedWaterTile()) {
					boolean isLandSquare = true;

					int startX = mapX;
					int startY = mapY;
					int finishX = mapX + lakeSize;
					int finishY = mapY + lakeSize;

					for (int y = startY; y < finishY; y++) {
						if (map.getTileArray()[startX][y].getBiome().isUncheckedWaterTile()
								|| map.getTileArray()[finishX][y].getBiome().isUncheckedWaterTile()) {
							isLandSquare = false;
							break;
						}
						if (isLandSquare == false) {
							break;
						}
					}
					if (isLandSquare) {
						for (int x = startX; x < finishX; x++) {
							if (map.getTileArray()[x][startY].getBiome().isUncheckedWaterTile()
									|| map.getTileArray()[x][finishY].getBiome().isUncheckedWaterTile()) {
								isLandSquare = false;
								break;
							}
							if (isLandSquare == false) {
								break;
							}
						}
					}

					if (isLandSquare) {
						boolean hasSomeWater = false;

						Lake lake = new Lake();

						for (int y = startY; y < finishY; y++) {
							for (int x = startX; x < finishX; x++) {

								if (map.getTileArray()[x][y].getBiome().isUncheckedWaterTile()) {
									hasSomeWater = true;
									lake.addLakePoint(map.getTileArray()[x][y]);
									map.getTileArray()[x][y].setBiome(setToBiome);

								}
							}
						}

						if (hasSomeWater) {
							lake.setLimits();
							lakes.add(lake);

						}

					}
				}

			}
		}

	}

	public void setSmallLakeSize(int val) {
		this.smallLakeSize = val;
	}

	public void setLargeLakeSize(int val) {
		this.largeLakeSize = val;
	}

	public int getSmallLakeSize() {
		return smallLakeSize;
	}

	public int getLargeLakeSize() {
		return largeLakeSize;
	}

	

	public ArrayList<Lake> getLakes() {
		return lakes;
	}
}

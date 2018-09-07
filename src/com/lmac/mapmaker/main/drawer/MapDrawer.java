package com.lmac.mapmaker.main.drawer;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import com.lmac.mapmaker.main.biomes.BiomeEngine;
import com.lmac.mapmaker.main.components.Map;
import com.lmac.mapmaker.main.data.DataMap;
import com.lmac.mapmaker.main.data.TileData;

public class MapDrawer {

	BiomeEngine be;

	public MapDrawer() {
		be = new BiomeEngine();
	}

	public void drawBiomeMap(String name, Map terrainMap, Map humidityMap, Map temperatureMap) {
		int mapHeight = terrainMap.getHeight();
		int mapWidth = terrainMap.getWidth();
		String path = name + ".png";

		BiomeEngine be = new BiomeEngine();

		DataMap dm = new DataMap(terrainMap, humidityMap, temperatureMap);

		BufferedImage image = new BufferedImage(terrainMap.getWidth(), terrainMap.getHeight(),
				BufferedImage.TYPE_INT_RGB);

		for (int y = 0; y < mapHeight; y++) {
			for (int x = 0; x < mapWidth; x++) {
				int height = terrainMap.getGlobalValue(x, y);
				int humidity = humidityMap.getGlobalValue(x, y);
				int temperature = temperatureMap.getGlobalValue(x, y);

				TileData t = new TileData(x, y, height, humidity, temperature);
				dm.setTile(t);
			}
		}

		dm.adjustTemperatureForHeight();

		for (int y = 0; y < mapHeight; y++) {
			for (int x = 0; x < mapWidth; x++) {

				image.setRGB(x, y, dm.getTile(x, y).getBiome().getColor().getRGB());

			}
		}

		smoothBiomeMap(dm, image, 1);
		smoothBiomeMap(dm, image, 2);
		smoothBiomeMap(dm, image, 3);
		smoothBiomeMap(dm, image, 4);
		smoothBiomeMap(dm, image, 1);
		smoothBiomeMap(dm, image, 2);
		smoothBiomeMap(dm, image, 3);

		dm.checkForLakes(100);

		File HeightMapFile = new File(path);
		try {
			ImageIO.write(image, "PNG", HeightMapFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void drawMap(Map map, String name) {

		String path = name + ".png";

		BufferedImage image = new BufferedImage(map.getWidth(), map.getHeight(), BufferedImage.TYPE_INT_RGB);

		for (int y = 0; y < map.getHeight(); y++) {
			for (int x = 0; x < map.getWidth(); x++) {

				int rbg = map.getGlobalValue(x, y);
				Color c = new Color(rbg, rbg, rbg);
				// Color pixel = biomer.assignBiome(map.getGlobalValue(x,y));
				if (y % map.getChunkSize() == 0) {
					// c = new Color(255,255,255);
				}

				image.setRGB(x, y, c.getRGB());

			}
		}

		File HeightMapFile = new File(path);
		try {
			ImageIO.write(image, "PNG", HeightMapFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void drawHeightMap(Map map, String name) {

		String path = "maps/" + name + ".png";
		BufferedImage image = new BufferedImage(map.getWidth(), map.getHeight(), BufferedImage.TYPE_INT_RGB);

		for (int y = 0; y < map.getHeight(); y++) {
			for (int x = 0; x < map.getWidth(); x++) {

				int rbg = map.getGlobalValue(x, y);

				if (rbg < 40) {
					rbg = 40;
				}

				float squishFactor = 0.20f;

				if (rbg >= 71 && rbg <= 151) {

					int rbgBase = rbg - 71;
					// so, 81 = 11;
					float percent = rbgBase * squishFactor;

					rbg = 71 + (int) percent;

				}
				squishFactor = 0.40f;
				if (rbg >= 152 && rbg <= 191) {
					// 87
					int rbgBase = rbg - 152;
					// so, 81 = 11;
					float percent = rbgBase * squishFactor;

					rbg = 87 + (int) percent;

				}

				/*
				 * if (rbg >= 71 && rbg < 125) {
				 * 
				 * if (rbg >= 71 && rbg <= 74) { rbg = 71; } else if (rbg >= 75 && rbg <= 79) {
				 * rbg = 72; } else if (rbg >= 80 && rbg <= 85) { rbg = 73; } else if (rbg >= 86
				 * && rbg <= 92) { rbg = 74; }else if (rbg >= 93 && rbg <= 100) { rbg = 75;
				 * }else if (rbg >= 101 && rbg <= 110) { rbg = 76; }else { rbg = 77; } }
				 */

				Color c = new Color(rbg, rbg, rbg);
				image.setRGB(x, y, c.getRGB());

			}
		}

		File HeightMapFile = new File(path);
		try {
			ImageIO.write(image, "PNG", HeightMapFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void smoothBiomeMap(DataMap dataMap, BufferedImage image, int degree) {
		for (int y = 1; y < dataMap.getHeight() - 1; y++) {
			for (int x = 1; x < dataMap.getWidth() - 1; x++) {

				int majorityRGB = 0;

				int counter = 0;

				int rbgCode = image.getRGB(x, y);

				if (image.getRGB(x + 1, y) == rbgCode) {
					counter++;
				} else {
					majorityRGB = image.getRGB(x + 1, y);
				}

				if (image.getRGB(x + 1, y + 1) == rbgCode) {
					counter++;
				} else {
					majorityRGB = image.getRGB(x + 1, y + 1);
				}

				if (image.getRGB(x + 1, y - 1) == rbgCode) {
					counter++;
				} else {
					majorityRGB = image.getRGB(x + 1, y - 1);
				}

				if (image.getRGB(x - 1, y) == rbgCode) {
					counter++;
				} else {
					majorityRGB = image.getRGB(x - 1, y);
				}

				if (image.getRGB(x - 1, y + 1) == rbgCode) {
					counter++;
				} else {
					majorityRGB = image.getRGB(x - 1, y + 1);
				}

				if (image.getRGB(x - 1, y - 1) == rbgCode) {
					counter++;
				} else {
					majorityRGB = image.getRGB(x - 1, y - 1);
				}

				if (image.getRGB(x, y + 1) == rbgCode) {
					counter++;
				} else {
					majorityRGB = image.getRGB(x, y + 1);
				}

				if (image.getRGB(x, y - 1) == rbgCode) {
					counter++;
				} else {
					majorityRGB = image.getRGB(x, y - 1);
				}

				if (counter <= degree) {

					image.setRGB(x, y, majorityRGB);
					dataMap.getTile(x, y).setBiome(be.getBiomeByColor(new Color(majorityRGB)));
				}

			}
		}
	}

	/*
	 * public void drawMap2(WeatherMap weatherMap, String name) { Biome grass = new
	 * Biome("Grassland", 71, 150, 25, 59, 0, 0, new Color(42, 162, 42)); Biome
	 * rainForest = new Biome("RainForest", 71, 150, 60, 100, 0, 0, new Color(26,
	 * 101, 26)); Biome beach = new Biome("Beach", 67, 70, 0, 100, 0, 0, new
	 * Color(194, 178, 128)); Biome desert = new Biome("Desert", 71, 150, 0, 24, 0,
	 * 0, new Color(194, 178, 128)); Biome dunes = new Biome("Dune", 151, 191, 0,
	 * 24, 0, 0, new Color(150, 113, 23)); Biome pineForest = new
	 * Biome("PineForest", 151, 191, 25, 100, 0, 0, new Color(0, 51, 0)); Biome
	 * mountain = new Biome("Mountain", 192, 255, 0, 100, 0, 0, new Color(79, 51,
	 * 23)); Biome snowTopMountain = new Biome("SnowTop", 221, 255, 24, 100, 0, 0,
	 * new Color(200, 200, 200)); Biome water = new Biome("Water", 0, 66, 0, 100, 0,
	 * 0, new Color(0, 0, 100));
	 * 
	 * Biomer b = new Biomer(grass);
	 * 
	 * b.addBiome(rainForest); b.addBiome(desert); b.addBiome(pineForest);
	 * b.addBiome(snowTopMountain); b.addBiome(mountain);
	 * 
	 * b.addBiome(water); b.addBiome(dunes); b.addBiome(beach);
	 * 
	 * int[][] vals = weatherMap.getMapVals();
	 * 
	 * String path = name + ".png";
	 * 
	 * BufferedImage image = new BufferedImage(weatherMap.getWidth(),
	 * weatherMap.getHeight(), BufferedImage.TYPE_INT_RGB);
	 * 
	 * for (int y = 0; y < weatherMap.getHeight(); y++) { for (int x = 0; x <
	 * weatherMap.getWidth(); x++) {
	 * 
	 * int rbg = vals[x][y]; // Color c = new Color(rbg, rbg, rbg); Color pixel =
	 * b.assignBiome(weatherMap.getLandMap().getGlobalValue(x, y), vals[x][y], 0);
	 * 
	 * image.setRGB(x, y, pixel.getRGB());
	 * 
	 * } }
	 * 
	 * smoothMap(weatherMap, image, 1); smoothMap(weatherMap, image, 2);
	 * smoothMap(weatherMap, image, 1); smoothMap(weatherMap, image, 3);
	 * smoothMap(weatherMap, image, 1); smoothMap(weatherMap, image, 2);
	 * smoothMap(weatherMap, image, 1); smoothMap(weatherMap, image, 3);
	 * smoothMap(weatherMap, image, 1); smoothMap(weatherMap, image, 4);
	 * 
	 * File HeightMapFile = new File(path); try { ImageIO.write(image, "PNG",
	 * HeightMapFile); } catch (IOException e) { e.printStackTrace(); }
	 * 
	 * }
	 */
	public void smoothMap(Map map, BufferedImage image, int degree) {
		for (int y = 1; y < map.getHeight() - 1; y++) {
			for (int x = 1; x < map.getWidth() - 1; x++) {

				int majorityRGB = 0;

				int counter = 0;

				int rbgCode = image.getRGB(x, y);

				if (image.getRGB(x + 1, y) == rbgCode) {
					counter++;
				} else {
					majorityRGB = image.getRGB(x + 1, y);
				}

				if (image.getRGB(x + 1, y + 1) == rbgCode) {
					counter++;
				} else {
					majorityRGB = image.getRGB(x + 1, y + 1);
				}

				if (image.getRGB(x + 1, y - 1) == rbgCode) {
					counter++;
				} else {
					majorityRGB = image.getRGB(x + 1, y - 1);
				}

				if (image.getRGB(x - 1, y) == rbgCode) {
					counter++;
				} else {
					majorityRGB = image.getRGB(x - 1, y);
				}

				if (image.getRGB(x - 1, y + 1) == rbgCode) {
					counter++;
				} else {
					majorityRGB = image.getRGB(x - 1, y + 1);
				}

				if (image.getRGB(x - 1, y - 1) == rbgCode) {
					counter++;
				} else {
					majorityRGB = image.getRGB(x - 1, y - 1);
				}

				if (image.getRGB(x, y + 1) == rbgCode) {
					counter++;
				} else {
					majorityRGB = image.getRGB(x, y + 1);
				}

				if (image.getRGB(x, y - 1) == rbgCode) {
					counter++;
				} else {
					majorityRGB = image.getRGB(x, y - 1);
				}

				if (counter <= degree) {

					image.setRGB(x, y, majorityRGB);
				}

			}
		}
	}

}

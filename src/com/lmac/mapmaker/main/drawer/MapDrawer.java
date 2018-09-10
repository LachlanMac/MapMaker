package com.lmac.mapmaker.main.drawer;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import com.lmac.mapmaker.main.biomes.Biome;
import com.lmac.mapmaker.main.biomes.BiomeEngine;
import com.lmac.mapmaker.main.components.Map;
import com.lmac.mapmaker.main.data.DataMap;
import com.lmac.mapmaker.main.data.TileData;
import com.lmac.mapmaker.main.engines.ForestEngine;
import com.lmac.mapmaker.main.engines.LakeEngine;
import com.lmac.mapmaker.main.engines.RiverEngine;

public class MapDrawer {

	public MapDrawer() {

	}

	public void processMap(String name, Map terrainMap, Map humidityMap, Map temperatureMap, ForestEngine forestEngine,
			LakeEngine lakeEngine, RiverEngine riverEngine) {
		int mapHeight = terrainMap.getHeight();
		int mapWidth = terrainMap.getWidth();
		String path = "maps/" + name + ".png";

		DataMap dm = new DataMap(terrainMap, humidityMap, temperatureMap, forestEngine.getMap());

		BufferedImage image = new BufferedImage(terrainMap.getWidth(), terrainMap.getHeight(),
				BufferedImage.TYPE_INT_RGB);
		System.out.println("...Setting Tile Data Map");
		for (int y = 0; y < mapHeight; y++) {
			for (int x = 0; x < mapWidth; x++) {
				int height = terrainMap.getGlobalValue(x, y);
				int humidity = humidityMap.getGlobalValue(x, y);
				int temperature = temperatureMap.getGlobalValue(x, y);

				TileData t = new TileData(x, y, height, humidity, temperature);
				dm.setTile(t);
			}
		}

		System.out.println("...Adjusting Temperatures for Height");
		dm.adjustTemperatureForHeight();

		System.out.println("...Setting Initial Image");
		for (int y = 0; y < mapHeight; y++) {
			for (int x = 0; x < mapWidth; x++) {

				image.setRGB(x, y, dm.getTile(x, y).getBiome().getColor().getRGB());

			}
		}
		System.out.println("...Smoothing first iteration");
		smoothBiomeMap(dm, image, 1);
		smoothBiomeMap(dm, image, 2);
		smoothBiomeMap(dm, image, 3);
		smoothBiomeMap(dm, image, 4);
		smoothBiomeMap(dm, image, 1);
		smoothBiomeMap(dm, image, 2);
		smoothBiomeMap(dm, image, 3);

		System.out.println("...Checking for Small Lakes");
		lakeEngine.checkForLakes(dm, lakeEngine.getSmallLakeSize(), BiomeEngine.getSmallLake());
		System.out.println("...Checking for Large Lakes");
		lakeEngine.checkForLakes(dm, lakeEngine.getLargeLakeSize(), BiomeEngine.getLake());
		System.out.println("...Generating Rivers");
		riverEngine.generateRiver(lakeEngine.getLakes(), dm);
		System.out.println("...Thickening Rivers");
		riverEngine.thickenRivers(dm);
		System.out.println("...Setting Forests");
		forestEngine.setForestedBiomes(dm);
		System.out.println("...Resetting image pixels");
		for (int y = 0; y < mapHeight; y++) {
			for (int x = 0; x < mapWidth; x++) {

				image.setRGB(x, y, dm.getTile(x, y).getBiome().getColor().getRGB());

			}
		}
		System.out.println("...Smoothing second iteration");
		smoothBiomeMap(dm, image, 1);
		smoothBiomeMap(dm, image, 2);
		smoothBiomeMap(dm, image, 3);

		File HeightMapFile = new File(path);
		try {
			System.out.println("...Writing HeightMap File");
			ImageIO.write(image, "PNG", HeightMapFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("...Finished");

	}

	public void drawHeightMap(Map map, String name) {

		String path = "maps/" + name + ".png";
		BufferedImage image = new BufferedImage(map.getWidth(), map.getHeight(), BufferedImage.TYPE_INT_RGB);

		for (int y = 0; y < map.getHeight(); y++) {
			for (int x = 0; x < map.getWidth(); x++) {

				int rbg = map.getGlobalValue(x, y);
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
					dataMap.getTile(x, y).setBiome(BiomeEngine.getBiomeByColor(new Color(majorityRGB)));
					image.setRGB(x, y, majorityRGB);

				}

			}
		}
	}

}

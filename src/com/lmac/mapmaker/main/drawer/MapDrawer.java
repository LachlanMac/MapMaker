package com.lmac.mapmaker.main.drawer;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.lmac.mapmaker.main.biomes.BiomeEngine;
import com.lmac.mapmaker.main.civs.Civilization;
import com.lmac.mapmaker.main.components.Map;
import com.lmac.mapmaker.main.data.DataMap;
import com.lmac.mapmaker.main.data.TileData;
import com.lmac.mapmaker.main.engines.CivilizationEngine;
import com.lmac.mapmaker.main.engines.ForestEngine;
import com.lmac.mapmaker.main.engines.LakeEngine;
import com.lmac.mapmaker.main.engines.RaceEngine;
import com.lmac.mapmaker.main.engines.RiverEngine;
import com.lmac.mapmaker.main.engines.TileStatEngine;
import com.lmac.mapmaker.races.Race;

public class MapDrawer {

	public MapDrawer() {

	}

	public void processMap(String name, Map terrainMap, Map humidityMap, Map temperatureMap, ForestEngine forestEngine,
			LakeEngine lakeEngine, RiverEngine riverEngine, TileStatEngine statEngine, CivilizationEngine civEngine,
			RaceEngine raceEngine) {
		int mapHeight = terrainMap.getHeight();
		int mapWidth = terrainMap.getWidth();
		String path = "/Users/lachlanmccallum/Desktop/maps/" + name + ".png";

		DataMap dm = new DataMap(terrainMap, humidityMap, temperatureMap, forestEngine.getMap());

		BufferedImage image = new BufferedImage(terrainMap.getWidth(), terrainMap.getHeight(),
				BufferedImage.TYPE_INT_RGB);

		BufferedImage livibilityMap = new BufferedImage(terrainMap.getWidth(), terrainMap.getHeight(),
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
		smoothBiomeMap(dm, image, 4);
		smoothBiomeMap(dm, image, 1);
		smoothBiomeMap(dm, image, 2);
		smoothBiomeMap(dm, image, 3);

		for (Race r : raceEngine.getRaces()) {
			System.out.println("...Setting Tile Stats for " + r.getName());
			statEngine.setStats(dm, r);
			System.out.println("...Drawing Livibility Map for " + r.getName());

			for (int y = 0; y < mapHeight; y++) {
				for (int x = 0; x < mapWidth; x++) {

					Color c = null;

					float livibility = dm.getTile(x, y).getLivibilityValue();

					if (livibility < 1) {
						c = Color.BLACK;
					} else if (livibility >= 1 && livibility < 20) {
						c = new Color(255, 0, 0);
					} else if (livibility >= 20 && livibility < 30) {
						c = new Color(240, 100, 0);
					} else if (livibility >= 30 && livibility < 40) {
						c = new Color(220, 120, 0);
					} else if (livibility >= 40 && livibility < 50) {
						c = new Color(200, 140, 0);
					} else if (livibility >= 50 && livibility < 60) {
						c = new Color(170, 170, 0);
					} else if (livibility >= 60 && livibility < 70) {
						c = new Color(120, 200, 0);
					} else if (livibility >= 70 && livibility < 80) {
						c = new Color(80, 220, 0);
					} else if (livibility >= 80 && livibility < 90) {
						c = new Color(40, 255, 0);
					} else if (livibility >= 90) {
						c = new Color(0, 255, 0);
					}

					livibilityMap.setRGB(x, y, c.getRGB());

				}
			}

			// add civiliztions for races

			System.out.println("Setting starting spots for civilizations");

			civEngine.setPoints(dm, r);

			for (TileData d : civEngine.getStartingPoints()) {

				

				Civilization c = new Civilization(d, r, dm);
				civEngine.addCivilization(c);

				for (TileData civTile : c.getTiles()) {

					image.setRGB(civTile.getX(), civTile.getY(), r.getColor().getRGB());

				}

			}

			File LivMap = new File("/Users/lachlanmccallum/Desktop/maps/liveMap_" + r.getName() + ".png");

			try {
				System.out.println("...Writing Livbility File");
				ImageIO.write(livibilityMap, "PNG", LivMap);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		File HeightMapFile = new File(path);
		try {
			System.out.println("...Writing HeightMap File");
			ImageIO.write(image, "PNG", HeightMapFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("...Writing DataMap to file");

		dm.exportDataMap("res/datamap.txt");

		System.out.println("...Finished");

	}

	public void drawHeightMap(Map map, String name) {

		String path = "/Users/lachlanmccallum/Desktop/maps/" + name + ".png";
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

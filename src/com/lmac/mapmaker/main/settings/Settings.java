package com.lmac.mapmaker.main.settings;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.lmac.mapmaker.main.biomes.Biome;
import com.lmac.mapmaker.main.biomes.BiomeEngine;

import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.awt.Color;
import java.io.File;

public class Settings {
	// world params
	public static int WORLD_HEIGHT, WORLD_WIDTH, GLOBAL_SEED, CHUNK_MULTIPLE;
	public static String MAP_NAME;

	// terrain
	public static int TERRAIN_SEED, TERRAIN_MAGNITUDE, TERRAIN_OFFSET, TERRAIN_MIN_CLAMP, TERRAIN_MAX_CLAMP,
			SAVE_TERRAIN_MAP;
	public static float TERRAIN_DECAY;

	// humidity
	public static int HUMIDITY_SEED, HUMIDITY_MAGNITUDE, HUMIDITY_OFFSET, HUMIDITY_MIN_CLAMP, HUMIDITY_MAX_CLAMP,
			SAVE_HUMIDITY_MAP, DEAD_BAND_EXTREME, DEAD_BAND_WIDTH;
	public static float HUMIDITY_DECAY;

	// temperature
	public static int TEMPERATURE_SEED, TEMPERATURE_MAGNITUDE, TEMPERATURE_OFFSET, TEMPERATURE_MIN_CLAMP,
			TEMPERATURE_MAX_CLAMP, SAVE_TEMPERATURE_MAP, MAX_TEMPERATURE, MIN_TEMPERATURE;
	public static float TEMPERATURE_DECAY;

	// forest settings
	public static int FOREST_SEED, FOREST_MAGNITUDE, FOREST_PERCENT, SAVE_FOREST_MAP;
	public static float FOREST_DECAY;

	// forest settings
	public static int RIVER_SEED, SMALL_LAKE_SIZE, LARGE_LAKE_SIZE, MIN_RIVER_LENGTH, MAX_RIVER_LENGTH, THICKEN_RIVERS;

	public static void loadSettings() {

		try {
			File fXmlFile = new File("res/settings.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

			String rootElement = doc.getDocumentElement().getNodeName();

			if (!rootElement.equals("settings")) {
				System.out.println("Invalid XML file");
				return;
			}

			loadWorldSettings(doc);
			loadTemperatureSettings(doc);
			loadHumiditySettings(doc);
			loadTerrainSettings(doc);
			loadForestSettings(doc);
			loadLakeAndRiverSettings(doc);
			loadLandBiomes(doc);
			loadForestBiomes(doc);
			loadWaterBiomes(doc);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void loadWorldSettings(Document doc) {

		NodeList nList = doc.getElementsByTagName("world");

		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;

				WORLD_WIDTH = Integer.parseInt(eElement.getElementsByTagName("width").item(0).getTextContent());
				WORLD_HEIGHT = Integer.parseInt(eElement.getElementsByTagName("height").item(0).getTextContent());
				GLOBAL_SEED = Integer.parseInt(eElement.getElementsByTagName("seed").item(0).getTextContent());
				CHUNK_MULTIPLE = Integer.parseInt(eElement.getElementsByTagName("multiple").item(0).getTextContent());
				MAP_NAME = eElement.getElementsByTagName("name").item(0).getTextContent();
			}
		}

	}

	public static void loadTerrainSettings(Document doc) {

		NodeList nList = doc.getElementsByTagName("terrain");

		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;

				TERRAIN_SEED = Integer.parseInt(eElement.getElementsByTagName("seed").item(0).getTextContent());
				TERRAIN_MAGNITUDE = Integer
						.parseInt(eElement.getElementsByTagName("magnitude").item(0).getTextContent());
				TERRAIN_DECAY = Float.parseFloat(eElement.getElementsByTagName("decay").item(0).getTextContent());
				TERRAIN_MIN_CLAMP = Integer
						.parseInt(eElement.getElementsByTagName("minClamp").item(0).getTextContent());
				TERRAIN_MAX_CLAMP = Integer
						.parseInt(eElement.getElementsByTagName("maxClamp").item(0).getTextContent());
				TERRAIN_OFFSET = Integer.parseInt(eElement.getElementsByTagName("offset").item(0).getTextContent());
				SAVE_TERRAIN_MAP = Integer.parseInt(eElement.getElementsByTagName("saveMap").item(0).getTextContent());

			}
		}

	}

	public static void loadTemperatureSettings(Document doc) {

		NodeList nList = doc.getElementsByTagName("temperature");

		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;

				TEMPERATURE_SEED = Integer.parseInt(eElement.getElementsByTagName("seed").item(0).getTextContent());
				TEMPERATURE_MAGNITUDE = Integer
						.parseInt(eElement.getElementsByTagName("magnitude").item(0).getTextContent());
				TEMPERATURE_DECAY = Float.parseFloat(eElement.getElementsByTagName("decay").item(0).getTextContent());
				TEMPERATURE_MIN_CLAMP = Integer
						.parseInt(eElement.getElementsByTagName("minClamp").item(0).getTextContent());
				TEMPERATURE_MAX_CLAMP = Integer
						.parseInt(eElement.getElementsByTagName("maxClamp").item(0).getTextContent());
				MIN_TEMPERATURE = Integer
						.parseInt(eElement.getElementsByTagName("minTemperature").item(0).getTextContent());
				MAX_TEMPERATURE = Integer
						.parseInt(eElement.getElementsByTagName("maxTemperature").item(0).getTextContent());
				TEMPERATURE_OFFSET = Integer.parseInt(eElement.getElementsByTagName("offset").item(0).getTextContent());
				SAVE_TEMPERATURE_MAP = Integer
						.parseInt(eElement.getElementsByTagName("saveMap").item(0).getTextContent());

			}
		}

	}

	public static void loadForestSettings(Document doc) {

		NodeList nList = doc.getElementsByTagName("forests");

		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;

				FOREST_SEED = Integer.parseInt(eElement.getElementsByTagName("seed").item(0).getTextContent());
				FOREST_MAGNITUDE = Integer
						.parseInt(eElement.getElementsByTagName("magnitude").item(0).getTextContent());
				FOREST_DECAY = Float.parseFloat(eElement.getElementsByTagName("decay").item(0).getTextContent());

				FOREST_PERCENT = Integer
						.parseInt(eElement.getElementsByTagName("forestPercent").item(0).getTextContent());
				SAVE_FOREST_MAP = Integer.parseInt(eElement.getElementsByTagName("saveMap").item(0).getTextContent());

			}
		}

	}

	public static void loadHumiditySettings(Document doc) {

		NodeList nList = doc.getElementsByTagName("humidity");

		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;

				HUMIDITY_SEED = Integer.parseInt(eElement.getElementsByTagName("seed").item(0).getTextContent());
				HUMIDITY_MAGNITUDE = Integer
						.parseInt(eElement.getElementsByTagName("magnitude").item(0).getTextContent());
				HUMIDITY_DECAY = Float.parseFloat(eElement.getElementsByTagName("decay").item(0).getTextContent());
				HUMIDITY_MIN_CLAMP = Integer
						.parseInt(eElement.getElementsByTagName("minClamp").item(0).getTextContent());
				HUMIDITY_MAX_CLAMP = Integer
						.parseInt(eElement.getElementsByTagName("maxClamp").item(0).getTextContent());
				DEAD_BAND_EXTREME = Integer
						.parseInt(eElement.getElementsByTagName("deadbandExtreme").item(0).getTextContent());
				DEAD_BAND_WIDTH = Integer
						.parseInt(eElement.getElementsByTagName("deadbandWidth").item(0).getTextContent());
				HUMIDITY_OFFSET = Integer.parseInt(eElement.getElementsByTagName("offset").item(0).getTextContent());
				SAVE_HUMIDITY_MAP = Integer.parseInt(eElement.getElementsByTagName("saveMap").item(0).getTextContent());

			}
		}

	}

	public static void loadLakeAndRiverSettings(Document doc) {

		NodeList nList = doc.getElementsByTagName("lakeandriver");

		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;

				RIVER_SEED = Integer.parseInt(eElement.getElementsByTagName("riverSeed").item(0).getTextContent());
				SMALL_LAKE_SIZE = Integer
						.parseInt(eElement.getElementsByTagName("smallLakeSize").item(0).getTextContent());

				LARGE_LAKE_SIZE = Integer
						.parseInt(eElement.getElementsByTagName("largeLakeSize").item(0).getTextContent());
				MIN_RIVER_LENGTH = Integer
						.parseInt(eElement.getElementsByTagName("minRiverLength").item(0).getTextContent());
				MAX_RIVER_LENGTH = Integer
						.parseInt(eElement.getElementsByTagName("maxRiverLength").item(0).getTextContent());
				THICKEN_RIVERS = Integer
						.parseInt(eElement.getElementsByTagName("thickenRivers").item(0).getTextContent());

			}
		}

	}

	public static void loadLandBiomes(Document doc) {

		NodeList nList = doc.getElementsByTagName("landbiomes");

		for (int temp = 0; temp < nList.getLength(); temp++) {

			Node nNode = nList.item(temp);
			NodeList biomeNodes = nNode.getChildNodes();

			for (int j = 0; j < biomeNodes.getLength(); j++) {
				Node biomeNode = biomeNodes.item(j);

				if (biomeNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) biomeNode;
					String biomeName = eElement.getElementsByTagName("name").item(0).getTextContent();
					int minHeight = Integer
							.parseInt(eElement.getElementsByTagName("minHeight").item(0).getTextContent());
					int maxHeight = Integer
							.parseInt(eElement.getElementsByTagName("maxHeight").item(0).getTextContent());
					int minHumidity = Integer
							.parseInt(eElement.getElementsByTagName("minHumidity").item(0).getTextContent());
					int maxHumidity = Integer
							.parseInt(eElement.getElementsByTagName("maxHumidity").item(0).getTextContent());
					int minTemperature = Integer
							.parseInt(eElement.getElementsByTagName("minTemperature").item(0).getTextContent());
					int maxTemperature = Integer
							.parseInt(eElement.getElementsByTagName("maxTemperature").item(0).getTextContent());

					Node n = (eElement.getElementsByTagName("color").item(0));
					int r = Integer.parseInt(n.getAttributes().getNamedItem("r").getTextContent());
					int g = Integer.parseInt(n.getAttributes().getNamedItem("g").getTextContent());
					int b = Integer.parseInt(n.getAttributes().getNamedItem("b").getTextContent());
					Color c = new Color(r, g, b);

					Biome biome = new Biome(biomeName, minHeight, maxHeight, minHumidity, maxHumidity, minTemperature,
							maxTemperature, c, false);
					BiomeEngine.addBiome(biome);
				}

			}
		}

	}

	public static void loadWaterBiomes(Document doc) {

		NodeList nList = doc.getElementsByTagName("waterbiomes");

		for (int temp = 0; temp < nList.getLength(); temp++) {

			Node nNode = nList.item(temp);
			NodeList biomeNodes = nNode.getChildNodes();

			for (int j = 0; j < biomeNodes.getLength(); j++) {
				Node biomeNode = biomeNodes.item(j);

				if (biomeNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) biomeNode;
					String biomeName = eElement.getElementsByTagName("name").item(0).getTextContent();
					int minHeight = Integer
							.parseInt(eElement.getElementsByTagName("minHeight").item(0).getTextContent());
					int maxHeight = Integer
							.parseInt(eElement.getElementsByTagName("maxHeight").item(0).getTextContent());
					int minHumidity = Integer
							.parseInt(eElement.getElementsByTagName("minHumidity").item(0).getTextContent());
					int maxHumidity = Integer
							.parseInt(eElement.getElementsByTagName("maxHumidity").item(0).getTextContent());
					int minTemperature = Integer
							.parseInt(eElement.getElementsByTagName("minTemperature").item(0).getTextContent());
					int maxTemperature = Integer
							.parseInt(eElement.getElementsByTagName("maxTemperature").item(0).getTextContent());

					Node n = (eElement.getElementsByTagName("color").item(0));
					int r = Integer.parseInt(n.getAttributes().getNamedItem("r").getTextContent());
					int g = Integer.parseInt(n.getAttributes().getNamedItem("g").getTextContent());
					int b = Integer.parseInt(n.getAttributes().getNamedItem("b").getTextContent());
					Color c = new Color(r, g, b);

					Biome biome = new Biome(biomeName, minHeight, maxHeight, minHumidity, maxHumidity, minTemperature,
							maxTemperature, c, true);
					BiomeEngine.addBiome(biome);
				}

			}
		}

	}

	public static void loadForestBiomes(Document doc) {

		NodeList nList = doc.getElementsByTagName("forestbiomes");

		for (int temp = 0; temp < nList.getLength(); temp++) {

			Node nNode = nList.item(temp);
			NodeList biomeNodes = nNode.getChildNodes();

			for (int j = 0; j < biomeNodes.getLength(); j++) {
				Node biomeNode = biomeNodes.item(j);

				if (biomeNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) biomeNode;
					String biomeName = eElement.getElementsByTagName("name").item(0).getTextContent();
					int minHeight = Integer
							.parseInt(eElement.getElementsByTagName("minHeight").item(0).getTextContent());
					int maxHeight = Integer
							.parseInt(eElement.getElementsByTagName("maxHeight").item(0).getTextContent());
					int minHumidity = Integer
							.parseInt(eElement.getElementsByTagName("minHumidity").item(0).getTextContent());
					int maxHumidity = Integer
							.parseInt(eElement.getElementsByTagName("maxHumidity").item(0).getTextContent());
					int minTemperature = Integer
							.parseInt(eElement.getElementsByTagName("minTemperature").item(0).getTextContent());
					int maxTemperature = Integer
							.parseInt(eElement.getElementsByTagName("maxTemperature").item(0).getTextContent());

					Node n = (eElement.getElementsByTagName("color").item(0));
					int r = Integer.parseInt(n.getAttributes().getNamedItem("r").getTextContent());
					int g = Integer.parseInt(n.getAttributes().getNamedItem("g").getTextContent());
					int b = Integer.parseInt(n.getAttributes().getNamedItem("b").getTextContent());
					Color c = new Color(r, g, b);

					Biome biome = new Biome(biomeName, minHeight, maxHeight, minHumidity, maxHumidity, minTemperature,
							maxTemperature, c, false);
					BiomeEngine.addForestBiome(biome);
				}

			}
		}

	}

}

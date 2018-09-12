package com.lmac.mapmaker.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import javax.xml.bind.DatatypeConverter;

import com.lmac.mapmaker.main.biomes.BiomeEngine;
import com.lmac.mapmaker.main.drawer.MapDrawer;
import com.lmac.mapmaker.main.engines.ForestEngine;
import com.lmac.mapmaker.main.engines.LakeEngine;
import com.lmac.mapmaker.main.engines.RiverEngine;
import com.lmac.mapmaker.main.engines.TemperatureEngine;
import com.lmac.mapmaker.main.engines.TerrainEngine;
import com.lmac.mapmaker.main.engines.WeatherEngine;
import com.lmac.mapmaker.main.math.Formatter;
import com.lmac.mapmaker.main.settings.Settings;

public class Main {

	// 15712189

	public static void main(String[] args) {

		BiomeEngine.initBiomes();
		Settings.loadSettings();
		generateWorld();

	}

	public static void generateWorld() {
		String mapName = Settings.MAP_NAME;
		int width = Settings.WORLD_WIDTH;
		int height = Settings.WORLD_HEIGHT;
		int multiple = Settings.CHUNK_MULTIPLE;
		int seed = Settings.GLOBAL_SEED;

		System.out.println("...Loading Biomes");
		// BiomeEngine.loadBiomes();
		System.out.println("...Creating Terrain Engine");
		TerrainEngine terrainEngine = new TerrainEngine(width, height, multiple, Settings.TERRAIN_MIN_CLAMP,
				Settings.TERRAIN_MAX_CLAMP, Settings.TERRAIN_OFFSET);
		WeatherEngine weatherEngine = new WeatherEngine(width, height, multiple, Settings.HUMIDITY_MIN_CLAMP,
				Settings.HUMIDITY_MAX_CLAMP, Settings.HUMIDITY_OFFSET);
		ForestEngine forestEngine = new ForestEngine(width, height, multiple, 0, 100, 0);
		TemperatureEngine tempEngine = new TemperatureEngine(width, height, multiple, Settings.TEMPERATURE_MIN_CLAMP,
				Settings.TEMPERATURE_MAX_CLAMP, Settings.TEMPERATURE_OFFSET);
		if (seed == 0) {
			terrainEngine.setSeed(Settings.TERRAIN_SEED);
			weatherEngine.setSeed(Settings.HUMIDITY_SEED);
			tempEngine.setSeed(Settings.TEMPERATURE_SEED);
			forestEngine.setSeed(Settings.FOREST_SEED);
		} else {
			terrainEngine.setSeed(seed);
			weatherEngine.setSeed(seed);
			tempEngine.setSeed(seed);
			forestEngine.setSeed(seed);
		}

		terrainEngine.setMagnitude(Settings.TERRAIN_MAGNITUDE);
		terrainEngine.setDecay(Settings.TERRAIN_DECAY);
		terrainEngine.setup();
		System.out.println("...Generating Terrain");
		terrainEngine.generate();

		weatherEngine.setDeadBandExtremes(Settings.DEAD_BAND_EXTREME);
		weatherEngine.setMagnitude(Settings.HUMIDITY_MAGNITUDE);
		weatherEngine.setDecay(Settings.HUMIDITY_DECAY);

		weatherEngine.setup();
		System.out.println("...Generating Global Weather");
		weatherEngine.generate();
		weatherEngine.climatize();

		forestEngine.setMagnitude(Settings.FOREST_MAGNITUDE);
		forestEngine.setDecay(Settings.FOREST_DECAY);

		forestEngine.setup();
		System.out.println("...Generating Forests");
		forestEngine.generate();
		forestEngine.setForestChance(Settings.FOREST_PERCENT);
		forestEngine.setAbsolute();

		tempEngine.setup();
		System.out.println("...Generating Global Temperatures");
		tempEngine.generate();
		tempEngine.setTemperatures(Settings.MIN_TEMPERATURE, Settings.MAX_TEMPERATURE);

		MapDrawer drawer = new MapDrawer();
		if (Settings.SAVE_TERRAIN_MAP == 1) {
			System.out.println("...Drawing Terrain Height Map");
			drawer.drawHeightMap(terrainEngine.getMap(), "TerrainMap_" + mapName);
		}
		if (Settings.SAVE_HUMIDITY_MAP == 1) {
			System.out.println("...Drawing Weather Map");
			drawer.drawHeightMap(weatherEngine.getMap(), "WeatherMap_" + mapName);
		}
		if (Settings.SAVE_TEMPERATURE_MAP == 1) {
			System.out.println("...Drawing Temperature Map");
			drawer.drawHeightMap(tempEngine.getMap(), "TemperatureMap_" + mapName);
		}
		if (Settings.SAVE_FOREST_MAP == 1) {
			System.out.println("...Drawing Forest Map");
			drawer.drawHeightMap(forestEngine.getMap(), "ForestMap_" + mapName);
		}

		LakeEngine lakeEngine = new LakeEngine();
		lakeEngine.setLargeLakeSize(Settings.LARGE_LAKE_SIZE);
		lakeEngine.setSmallLakeSize(Settings.SMALL_LAKE_SIZE);

		RiverEngine riverEngine = new RiverEngine();
		riverEngine.setSeed(Settings.RIVER_SEED);
		riverEngine.setMaxRiverLength(Settings.MAX_RIVER_LENGTH);
		riverEngine.setMinRiverLength(Settings.MIN_RIVER_LENGTH);

		if (Settings.THICKEN_RIVERS == 1) {
			riverEngine.setThickenRivers(true);
		} else {
			riverEngine.setThickenRivers(false);
		}

		System.out.println("...Drawing Map");
		drawer.processMap(mapName + "_Map", terrainEngine.getMap(), weatherEngine.getMap(), tempEngine.getMap(),
				forestEngine, lakeEngine, riverEngine);
	}

}

package com.lmac.mapmaker.main;

import com.lmac.mapmaker.main.biomes.BiomeEngine;
import com.lmac.mapmaker.main.drawer.MapDrawer;
import com.lmac.mapmaker.main.engines.ForestEngine;
import com.lmac.mapmaker.main.engines.LakeEngine;
import com.lmac.mapmaker.main.engines.RiverEngine;
import com.lmac.mapmaker.main.engines.TemperatureEngine;
import com.lmac.mapmaker.main.engines.TerrainEngine;
import com.lmac.mapmaker.main.engines.WeatherEngine;

public class Main {

	public static void main(String[] args) {

		int width = 18;
		int height = 10;
		int multiple = 8;
		int seed = 234235;
		System.out.println("...Loading Biomes");
		BiomeEngine.loadBiomes();
		System.out.println("...Creating Terrain Engine");
		TerrainEngine terrainEngine = new TerrainEngine(width, height, multiple, 0, 255, -10);
		terrainEngine.setSeed(seed);
		terrainEngine.setMagnitude(40);
		terrainEngine.setDecay(0.85f);

		terrainEngine.setup();
		System.out.println("...Generating Terrain");
		terrainEngine.generate();

		WeatherEngine weatherEngine = new WeatherEngine(width, height, multiple, 30, 100);
		weatherEngine.setSeed(seed);
		weatherEngine.setDeadBandExtremes(-17);
		weatherEngine.setMagnitude(30);
		weatherEngine.setDecay(0.80f);

		weatherEngine.setup();
		System.out.println("...Generating Global Weather");
		weatherEngine.generate();
		weatherEngine.climatize();

		ForestEngine forestEngine = new ForestEngine(width, height, multiple, 0, 100);
		forestEngine.setSeed(seed);
		forestEngine.setMagnitude(20);
		forestEngine.setDecay(0.9f);

		forestEngine.setup();
		System.out.println("...Generating Forests");
		forestEngine.generate();
		forestEngine.setForestChance(50);
		forestEngine.setAbsolute();

		TemperatureEngine tempEngine = new TemperatureEngine(width, height, multiple, 0, 10);
		tempEngine.setSeed(seed);
		tempEngine.setup();
		System.out.println("...Generating Global Temperatures");
		tempEngine.generate();
		tempEngine.setTemperatures(0, 100);

		MapDrawer drawer = new MapDrawer();

		System.out.println("...Drawing Terrain Height Map");
		drawer.drawHeightMap(terrainEngine.getMap(), "TerrainMap");
		System.out.println("...Drawing Weather Map");
		drawer.drawHeightMap(weatherEngine.getMap(), "WeatherMap");
		System.out.println("...Drawing Temperature Map");
		drawer.drawHeightMap(tempEngine.getMap(), "TemperatureMap");
		System.out.println("...Drawing Forest Map");
		drawer.drawHeightMap(forestEngine.getMap(), "ForestMap");
		
		LakeEngine lakeEngine = new LakeEngine();
		
		RiverEngine riverEngine = new RiverEngine();
		System.out.println("...Drawing Map");
		drawer.processMap("BiomeMap", terrainEngine.getMap(), weatherEngine.getMap(), tempEngine.getMap(), forestEngine,
				lakeEngine, riverEngine);

	}

}

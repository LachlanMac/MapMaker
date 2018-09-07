package com.lmac.mapmaker.main;

import com.lmac.mapmaker.main.drawer.MapDrawer;
import com.lmac.mapmaker.main.engines.TemperatureEngine;
import com.lmac.mapmaker.main.engines.TerrainEngine;
import com.lmac.mapmaker.main.engines.WeatherEngine;

public class Main {

	public static void main(String[] args) {

		int width = 4;
		int height = 4;
		int multiple = 8;
		int seed = 1;

		TerrainEngine terrainEngine = new TerrainEngine(width, height, multiple, 0, 255);
		terrainEngine.setSeed(seed);
		terrainEngine.setMagnitude(32);
		terrainEngine.setDecay(0.80f);

		terrainEngine.setup();
		terrainEngine.generate();

		WeatherEngine weatherEngine = new WeatherEngine(width, height, multiple, 30, 100);
		weatherEngine.setSeed(seed);
		weatherEngine.setMagnitude(30);
		weatherEngine.setDecay(0.8f);

		weatherEngine.setup();
		weatherEngine.generate();
		weatherEngine.climatize();

		TemperatureEngine tempEngine = new TemperatureEngine(width, height, multiple, 0, 10);
		tempEngine.setSeed(seed);
		tempEngine.setup();
		tempEngine.generate();
		tempEngine.setTemperatures(0, 100);

		MapDrawer drawer = new MapDrawer();
		drawer.drawHeightMap(terrainEngine.getMap(), "TerrainMap");
		drawer.drawHeightMap(weatherEngine.getMap(), "WeatherMap");
		drawer.drawHeightMap(tempEngine.getMap(), "TemperatureMap");
		drawer.drawBiomeMap("BiomeMap", terrainEngine.getMap(), weatherEngine.getMap(), tempEngine.getMap());

	}

}

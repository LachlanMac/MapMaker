package com.lmac.mapmaker.main.biomes;

import java.awt.Color;
import java.util.ArrayList;

public class BiomeEngine {
	Biome defaultBiome;
	ArrayList<Biome> biomes;

	public BiomeEngine() {

		loadBiomes();

	}

	public void loadBiomes() {

		defaultBiome = new Biome("Default Biome", 0, 255, 0, 100, 0, 100, Color.black);
		biomes = new ArrayList<Biome>();

		biomes.add(new Biome("Ice Cap", 0, 70, 0, 100, 0, 8, new Color(200, 243, 246)));
		biomes.add(new Biome("Deep Ocean", 0, 50, 0, 100, 9, 100, new Color(0, 0, 204)));
		biomes.add(new Biome("Ocean", 51, 70, 0, 100, 9, 100, new Color(0, 128, 255)));
		biomes.add(new Biome("Grass", 71, 150, 31, 100, 21, 100, new Color(0, 150, 0)));
		biomes.add(new Biome("Tundra", 71, 150, 0, 100, 0, 20, new Color(224, 224, 224)));
		biomes.add(new Biome("Desert", 71, 150, 0, 30, 21, 100, new Color(254, 255, 204)));
		biomes.add(new Biome("Lower Mountain", 151, 180, 0, 100, 0, 100, new Color(47, 30, 9)));
		biomes.add(new Biome("Mountain", 181, 200, 0, 100, 0, 100, new Color(89, 79, 58)));
		biomes.add(new Biome("Mountain Top", 201, 255, 0, 100, 32, 100, new Color(89, 79, 58)));
		biomes.add(new Biome("Mountain Top (snow)", 201, 255, 0, 100, 0, 31, new Color(230, 230, 230)));
		biomes.add(new Biome("Lake", 0, 0, 0, 0, 0, 0, Color.PINK));
		biomes.add(new Biome("Dumb", 0, 0, 0, 0, 0, 0, Color.ORANGE));
	}

	public Biome getBiome(int height, int humidity, int temperature) {

		Biome b = defaultBiome;

		for (Biome biome : biomes) {
			if (biome.inRange(height, humidity, temperature)) {

				b = biome;
			} else {

			}
		}

		if (b == defaultBiome) {
			System.out.println("height " + height + " humidity " + humidity + " temperature " + temperature);
		}

		return b;
	}

	public Biome getBiomeByName(String name) {
		Biome b = defaultBiome;

		for (Biome biome : biomes) {
			if (biome.getName().equals(name)) {
				b = biome;
			}
		}

		return b;
	}

	public Biome getBiomeByColor(Color c) {

		Biome b = defaultBiome;

		for (Biome biome : biomes) {
			if (biome.getColor() == c) {
				b = biome;
			}
		}

		return b;

	}
}

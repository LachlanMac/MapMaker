package com.lmac.mapmaker.main.biomes;

import java.awt.Color;
import java.util.ArrayList;

public class BiomeEngine {
	static Biome defaultBiome, lake, smallLake, river, riverBank;
	static ArrayList<Biome> biomes;
	static ArrayList<Biome> forestBiomes;

	public static void loadBiomes() {

		defaultBiome = new Biome("Default Biome", 0, 255, 0, 100, 0, 100, Color.black, false);
		lake = new Biome("Lake", 0, 0, 0, 0, 0, 0, new Color(0, 128, 255), false);
		river = new Biome("River", 0, 0, 0, 0, 0, 0, new Color(0, 128, 255), false);
		riverBank = new Biome("River Bank", 0, 0, 0, 0, 0, 0, new Color(0, 128, 255), false);
		smallLake = new Biome("Small Lake", 0, 0, 0, 0, 0, 0, new Color(0, 128, 255), false);

		lake.setToWaterTile();
		river.setToWaterTile();
		riverBank.setToWaterTile();
		smallLake.setToWaterTile();

		biomes = new ArrayList<Biome>();
		forestBiomes = new ArrayList<Biome>();

		forestBiomes.add(new Biome("Temperature Forest", 80, 140, 35, 50, 40, 80, new Color(112, 130, 56), false));
		forestBiomes.add(new Biome("Rain Forest", 72, 130, 65, 100, 50, 100, new Color(41, 171, 135), false));
		forestBiomes.add(new Biome("Pine Forest", 80, 130, 40, 100, 10, 25, new Color(63, 122, 77), false));

		biomes.add(new Biome("Ice Cap", 0, 70, 0, 100, 0, 8, new Color(200, 243, 246), true));
		biomes.add(new Biome("Deep Ocean", 0, 50, 0, 100, 9, 100, new Color(0, 0, 204), true));
		biomes.add(new Biome("Ocean", 51, 70, 0, 100, 9, 100, new Color(0, 128, 255), true));

		biomes.add(new Biome("Tundra", 71, 150, 0, 100, 0, 20, new Color(224, 224, 224), false));
		biomes.add(new Biome("Desert", 71, 150, 0, 24, 21, 100, new Color(254, 255, 204), false));
		biomes.add(new Biome("Grass", 71, 150, 25, 100, 21, 100, new Color(0, 150, 0), false));

		biomes.add(new Biome("Lower Mountain", 151, 180, 0, 100, 0, 100, new Color(47, 30, 9), false));
		biomes.add(new Biome("Mountain", 181, 200, 0, 100, 0, 100, new Color(89, 79, 58), false));
		biomes.add(new Biome("Mountain Top", 201, 255, 0, 100, 32, 100, new Color(89, 79, 58), false));
		biomes.add(new Biome("Mountain Top (snow)", 201, 255, 0, 100, 0, 31, new Color(230, 230, 230), false));

	}

	public static Biome getBiome(int height, int humidity, int temperature) {

		Biome b = defaultBiome;

		for (Biome biome : biomes) {
			if (biome.inRange(height, humidity, temperature)) {

				b = biome;
			}
		}

		return b;
	}

	public static Biome getForestBiome(int height, int humidity, int temperature) {

		Biome b = null;

		for (Biome biome : forestBiomes) {
			if (biome.inRange(height, humidity, temperature)) {

				b = biome;
			}
		}
		return b;
	}

	public static Biome getBiomeByName(String name) {
		Biome b = defaultBiome;

		for (Biome biome : biomes) {
			if (biome.getName().equals(name)) {
				b = biome;
			}
		}

		return b;
	}

	public static Biome getBiomeByColor(Color c) {

		Biome b = defaultBiome;

		for (Biome biome : biomes) {
			if (biome.getColor().equals(c)) {
				b = biome;
			}
		}

		return b;

	}

	public static Biome getLake() {
		return lake;
	}

	public static Biome getSmallLake() {
		return smallLake;
	}

	public static Biome getRiver() {
		return river;
	}

	public static Biome getRiverBank() {
		return riverBank;
	}
}

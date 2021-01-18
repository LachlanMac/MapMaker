package com.lmac.mapmaker.main.biomes;

import java.awt.Color;
import java.util.ArrayList;

public class BiomeEngine {
	static Biome defaultBiome, lake, smallLake, river, riverBank;
	static ArrayList<Biome> biomes;
	static ArrayList<Biome> forestBiomes;

	public static void addBiome(Biome b) {

		biomes.add(b);

	}

	public static void addForestBiome(Biome b) {
		forestBiomes.add(b);
	}

	public static void initBiomes() {
	
		biomes = new ArrayList<Biome>();
		forestBiomes = new ArrayList<Biome>();
		loadDefaultBiomes();
	}

	public static void loadDefaultBiomes() {
		
		defaultBiome = new Biome(0, "Default Biome", 0, 255, 0, 100, 0, 100, Color.black, false, 0f, 0f, 0, 0, 0, 0);
		lake = new Biome(1, "Lake", 0, 0, 0, 0, 0, 0, new Color(0, 128, 255), false, 0f, 30f, 0, 50, 0, 0);
		river = new Biome(2, "River", 0, 0, 0, 0, 0, 0, new Color(0, 128, 255), false, 0f, 40f, 0, 40, 0, 0);
		riverBank = new Biome(3, "River Bank", 0, 0, 0, 0, 0, 0, new Color(0, 128, 255), false, 0f, 40f, 0, 25,0,0);
		smallLake = new Biome(4, "Small Lake", 0, 0, 0, 0, 0, 0, new Color(0, 128, 255), false, 0f, 40f, 0, 30,0,0);

		lake.setToWaterTile();
		river.setToWaterTile();
		riverBank.setToWaterTile();
		smallLake.setToWaterTile();
		
		biomes.add(defaultBiome);
		biomes.add(lake);
		biomes.add(river);
		biomes.add(riverBank);
		biomes.add(smallLake);
			
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

package com.lmac.mapmaker.main.biomes;

import java.awt.Color;

public class Biome {

	private String name;
	private int minHeight, maxHeight, minHumidity, maxHumidity, minTemperature, maxTemperature;
	private Color color;
	private boolean isUncheckedWaterTile = false;
	private boolean isWaterTile = false;
	private int id;
	private int plantCover, animalCover, treeCover, mineralCover;

	private float livibilityIndex, adjacencyIndex;

	public Biome(int id, String name, int minHeight, int maxHeight, int minHumidity, int maxHumidity,
			int minTemperature, int maxTemperature, Color color, boolean isUncheckedWaterTile, float livibilityIndex,
			float adjacencyIndex, int plantCover, int animalCover, int treeCover, int mineralCover) {
		this.name = name;
		this.minHeight = minHeight;
		this.maxHeight = maxHeight;
		this.minHumidity = minHumidity;
		this.maxHumidity = maxHumidity;
		this.minTemperature = minTemperature;
		this.maxTemperature = maxTemperature;
		this.color = color;
		this.isUncheckedWaterTile = isUncheckedWaterTile;
		this.id = id;
		this.livibilityIndex = livibilityIndex;
		this.adjacencyIndex = adjacencyIndex;
		this.plantCover = plantCover;
		this.animalCover = animalCover;
		this.treeCover = treeCover;
		this.mineralCover = mineralCover;
	}

	public void setToWaterTile() {
		isWaterTile = true;
	}

	public boolean isWaterTile() {
		return isWaterTile;
	}

	public Color getColor() {
		return color;
	}

	public boolean inRange(int height, int humidity, int temperature) {
		boolean inRange = true;
		if (height < minHeight || height > maxHeight) {
			inRange = false;
		}
		if (humidity < minHumidity || humidity > maxHumidity) {
			inRange = false;
		}
		if (temperature < minTemperature || temperature > maxTemperature) {
			inRange = false;
		}

		return inRange;
	}

	public String getName() {
		return name;
	}

	public int getID() {
		return id;
	}

	public boolean isUncheckedWaterTile() {
		return isUncheckedWaterTile;
	}

	public float getAdjacencyIndex() {
		return adjacencyIndex;
	}

	public float getLivibilityIndex() {
		return livibilityIndex;
	}

	public int getPlantCover() {
		return plantCover;
	}

	public int getAnimalCover() {
		return animalCover;
	}

	public int getTreeCover() {
		return treeCover;
	}

	public int getMineralCover() {
		return mineralCover;
	}

}

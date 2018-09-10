package com.lmac.mapmaker.main.biomes;

import java.awt.Color;

public class Biome {

	private String name;
	private int minHeight, maxHeight, minHumidity, maxHumidity, minTemperature, maxTemperature;
	private Color color;
	private boolean isUncheckedWaterTile = false;
	private boolean isWaterTile = false;

	public Biome(String name, int minHeight, int maxHeight, int minHumidity, int maxHumidity, int minTemperature,
			int maxTemperature, Color color, boolean isUncheckedWaterTile) {
		this.name = name;
		this.minHeight = minHeight;
		this.maxHeight = maxHeight;
		this.minHumidity = minHumidity;
		this.maxHumidity = maxHumidity;
		this.minTemperature = minTemperature;
		this.maxTemperature = maxTemperature;
		this.color = color;
		this.isUncheckedWaterTile = isUncheckedWaterTile;
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

	public boolean isUncheckedWaterTile() {
		return isUncheckedWaterTile;
	}
}

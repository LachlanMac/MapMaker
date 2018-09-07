package com.lmac.mapmaker.main.biomes;

import java.awt.Color;

public class Biome {

	private String name;
	private int minHeight, maxHeight, minHumidity, maxHumidity, minTemperature, maxTemperature;
	private Color color;

	public Biome(String name, int minHeight, int maxHeight, int minHumidity, int maxHumidity, int minTemperature,
			int maxTemperature, Color color) {
		this.name = name;
		this.minHeight = minHeight;
		this.maxHeight = maxHeight;
		this.minHumidity = minHumidity;
		this.maxHumidity = maxHumidity;
		this.minTemperature = minTemperature;
		this.maxTemperature = maxTemperature;
		this.color = color;
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
}

package com.lmac.mapmaker.races;

import java.awt.Color;

import com.lmac.mapmaker.main.biomes.Biome;
import com.lmac.mapmaker.main.data.DataMap;

public class Race {

	private String name;
	private int idealTemperature, idealHumidity, maxCivs;
	private float mineralEfficiency, treeEfficiency, foodEfficiency;
	Biome preferredBiome;
	Color color;

	DataMap dm;

	public Race(String name, int maxCivs, Color color) {
		this.name = name;
		this.maxCivs = maxCivs;
		this.color = color;
	}

	public void setIdealTemperature(int idealTemperature) {
		this.idealTemperature = idealTemperature;
	}

	public int getIdealTemperature() {
		return idealTemperature;
	}

	public void setIdealHumidity(int idealHumidity) {
		this.idealHumidity = idealHumidity;
	}

	public int getIdealHumidity() {
		return idealHumidity;
	}

	public void setPreferredBiome(Biome b) {
		this.preferredBiome = b;
	}

	public Biome getPreferredBiome() {
		return preferredBiome;
	}

	public void setMineralEfficiency(float mineralEfficiency) {
		this.mineralEfficiency = mineralEfficiency;
	}

	public float getMineralEffiency() {
		return mineralEfficiency;
	}

	public void setTreeEfficiency(float treeEfficiency) {
		this.treeEfficiency = treeEfficiency;
	}

	public float getTreeEffiency() {
		return treeEfficiency;
	}

	public void setFoodEfficiency(float foodEfficiency) {
		this.foodEfficiency = foodEfficiency;
	}

	public float getFoodeffiency() {
		return foodEfficiency;
	}

	public String getName() {
		return name;
	}

	public int getMaxCivs() {
		return maxCivs;
	}

	public void setDataMap(DataMap dm) {
		this.dm = dm;
	}

	public DataMap getDataMap() {
		return dm;
	}

	public Color getColor() {
		return color;
	}

}

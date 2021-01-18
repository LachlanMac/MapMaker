package com.lmac.mapmaker.main.data;

import com.lmac.mapmaker.main.biomes.Biome;
import com.lmac.mapmaker.main.math.Formatter;
import com.lmac.mapmaker.main.math.Vector2;

public class TileData {

	private int temperature, humidity, height, x, y;
	Biome b;
	boolean potentialLake = false;
	boolean isLake = false;
	String region = "default";

	private int food = 0, minerals = 0, trees = 0;

	private float livibilityValue;

	public TileData(int x, int y, int height, int humidity, int temperature) {
		this.temperature = temperature;
		this.humidity = humidity;
		this.height = height;
		this.x = x;
		this.y = y;

	}

	public int getTemperature() {
		return temperature;
	}

	public int getHumidity() {
		return humidity;
	}

	public int getHeight() {
		return height;
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}

	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}

	public void setBiome(Biome b) {
		this.b = b;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Biome getBiome() {
		return b;
	}

	public void setToLake() {
		this.isLake = true;

	}

	public Vector2 getLoc() {
		return new Vector2(x, y);
	}

	public void setIsPotentialLake(boolean val) {
		this.potentialLake = val;
	}

	public boolean isPotentialLake() {
		return this.potentialLake;
	}

	public String toString() {
		return "<" + x + "-" + y + "-" + height + "-" + humidity + "-" + temperature + "-" + b.getID();
	}

	public String encodeData() {

		String data = "<" + Formatter.getHexString(height, humidity, temperature, b.getID());
		return data;
	}

	public void setLivibilityValue(float f) {
		this.livibilityValue = f;
	}

	public float getLivibilityValue() {
		return this.livibilityValue;
	}

	public void setFood(int food) {
		this.food = food;
	}

	public int getFood() {
		return food;
	}

	public void setMinerals(int minerals) {
		this.minerals = minerals;
	}

	public int getMinerals() {
		return minerals;
	}

	public void setTrees(int trees) {
		this.trees = trees;
	}

	public int getTrees() {
		return trees;
	}

}

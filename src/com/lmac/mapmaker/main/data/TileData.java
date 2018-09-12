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

}

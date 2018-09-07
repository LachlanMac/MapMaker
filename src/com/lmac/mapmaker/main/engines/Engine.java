package com.lmac.mapmaker.main.engines;

import java.util.Random;

import com.lmac.mapmaker.main.components.Chunk;
import com.lmac.mapmaker.main.components.Map;
import com.lmac.mapmaker.main.diamondsquare.DiamondSquare;
import com.lmac.mapmaker.main.math.Vector2;

public abstract class Engine {

	Map map;
	DiamondSquare ds;
	Chunk currentChunk;
	Random random;

	protected final int DEFAULT_MAGNITUDE = 25;
	protected final int DEFAULT_OFFSET = 0;
	protected final int DEFAULT_SEED = 0;
	protected final float DEFAULT_DECAY = 0.85f;
	protected int magnitude, offset, seed, multiple, mapWidth, mapHeight, minClamp, maxClamp;
	protected float decay;

	public Engine(int mapWidth, int mapHeight, int multiple, int minClamp, int maxClamp) {
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
		this.minClamp = minClamp;
		this.maxClamp = maxClamp;
		this.multiple = multiple;
		this.magnitude = DEFAULT_MAGNITUDE;
		this.offset = DEFAULT_OFFSET;
		this.seed = DEFAULT_SEED;
		this.decay = DEFAULT_DECAY;

	}

	public abstract void setup();

	public abstract void setSurroundingTiles(Chunk chunk);

	public void generate() {

		ds.runDiamondSqure(currentChunk);
		currentChunk.setDiamondSquared(true);
		map.getChunkList().remove(currentChunk);

		while (!map.getChunkList().isEmpty()) {
			for (int n = 0; n < map.getChunkList().size(); n++) {
				setSurroundingTiles(map.getChunkList().get(n));

			}
		}
	}

	public Map getMap() {
		return map;
	}

	public DiamondSquare getDS() {
		return ds;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public void setSeed(int seed) {
		this.seed = seed;
	}

	public void setMagnitude(int magnitude) {
		this.magnitude = magnitude;
	}

	public void setDecay(float decay) {
		this.decay = decay;
	}

}

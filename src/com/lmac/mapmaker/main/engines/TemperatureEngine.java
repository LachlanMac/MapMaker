package com.lmac.mapmaker.main.engines;

import java.util.Random;

import com.lmac.mapmaker.main.components.Chunk;
import com.lmac.mapmaker.main.components.Map;
import com.lmac.mapmaker.main.diamondsquare.DiamondSquare;
import com.lmac.mapmaker.main.math.Vector2;

public class TemperatureEngine extends Engine {

	public TemperatureEngine(int mapWidth, int mapHeight, int multiple, int minClamp, int maxClamp, int offset) {
		super(mapWidth, mapHeight, multiple, minClamp, maxClamp, offset);

	}

	@Override
	public void setup() {

		random = new Random(seed);
		this.map = new Map(seed, mapWidth, mapHeight, multiple, minClamp, maxClamp, true);
		this.currentChunk = map.getCurrentChunk();
		ds = new DiamondSquare(seed, magnitude, decay);
		currentChunk.setCorners(random.nextInt(maxClamp) + offset, random.nextInt(maxClamp) + offset,
				random.nextInt(maxClamp) + offset, random.nextInt(maxClamp) + offset);
	}

	@Override
	public void setSurroundingTiles(Chunk chunk) {
		if (chunk.getDiamondSquared()) {
			return;
		}

		Chunk rightN = null;
		Chunk leftN = null;
		Chunk upN = null;
		Chunk downN = null;

		boolean dsThisChunk = false;

		if (map.getNeighbourChunk(chunk, new Vector2(1, 0)) != null) {
			rightN = map.getNeighbourChunk(chunk, new Vector2(1, 0));
			if (rightN.getDiamondSquared()) {
				chunk.setWall("east", rightN.getWall("west"));
				dsThisChunk = true;
			}

		}
		if (map.getNeighbourChunk(chunk, new Vector2(-1, 0)) != null) {
			leftN = map.getNeighbourChunk(chunk, new Vector2(-1, 0));
			if (leftN.getDiamondSquared()) {
				chunk.setWall("west", leftN.getWall("east"));
				dsThisChunk = true;
			}
		}
		if (map.getNeighbourChunk(chunk, new Vector2(0, -1)) != null) {
			upN = map.getNeighbourChunk(chunk, new Vector2(0, -1));
			if (upN.getDiamondSquared()) {
				chunk.setWall("north", upN.getWall("south"));
				dsThisChunk = true;
			}
		}
		if (map.getNeighbourChunk(chunk, new Vector2(0, 1)) != null) {
			downN = map.getNeighbourChunk(chunk, new Vector2(0, 1));
			if (downN.getDiamondSquared()) {
				chunk.setWall("south", downN.getWall("north"));
				dsThisChunk = true;
			}
		}

		if (dsThisChunk) {

			chunk.setDiamondSquared(true);

			int randNE = random.nextInt(maxClamp);
			int randSE = random.nextInt(maxClamp);
			int randNW = random.nextInt(maxClamp);
			int randSW = random.nextInt(maxClamp);

			chunk.setCorners(randNW + offset, randNE + offset, randSW + offset, randSE + offset);

			ds.runDiamondSqure(chunk);
			map.getChunkList().remove(chunk);

		}
	}

	public void setTemperatures(int minTemp, int maxTemp) {
		map.setTemperatures(minTemp, maxTemp);
	}

}

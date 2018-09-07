package com.lmac.mapmaker.main.engines;

import java.util.Random;

import com.lmac.mapmaker.main.components.Chunk;
import com.lmac.mapmaker.main.components.Map;
import com.lmac.mapmaker.main.diamondsquare.DiamondSquare;
import com.lmac.mapmaker.main.math.Vector2;

public class TerrainEngine extends Engine {

	private final int DEFAULT_SEA_LEVEL = 73;
	private int seaLevel = DEFAULT_SEA_LEVEL;

	public TerrainEngine(int mapWidth, int mapHeight, int multiple, int minClamp, int maxClamp) {
		super(mapWidth, mapHeight, multiple, minClamp, maxClamp);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setup() {
		random = new Random(seed);
		this.map = new Map(seed, mapWidth, mapHeight, multiple, minClamp, maxClamp, true);
		this.currentChunk = map.getCurrentChunk();
		ds = new DiamondSquare(seed, magnitude, decay, offset);
		currentChunk.setCorners(random.nextInt(10) + seaLevel, random.nextInt(10) + seaLevel,
				random.nextInt(10) + seaLevel, random.nextInt(10) + seaLevel);
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

			int ne = 0;
			int se = 0;
			int sw = 0;
			int nw = 0;

			int randNE = random.nextInt(maxClamp);
			int randSE = random.nextInt(maxClamp);
			int randNW = random.nextInt(maxClamp);
			int randSW = random.nextInt(maxClamp);

			ne = chunk.getPoint(chunk.getSize() - 1, 0);
			nw = chunk.getPoint(0, 0);
			se = chunk.getPoint(chunk.getSize() - 1, chunk.getSize() - 1);
			sw = chunk.getPoint(0, chunk.getSize() - 1);
			int chance = random.nextInt(100);
			int[] points = { ne, nw, se, sw };
			int sum = 0;
			int mountainCounter = 0;
			int oceanCounter = 0;

			for (int i = 0; i < points.length; i++) {
				if (points[i] != 0) {
					sum = sum + points[i];
				}
				if (points[i] > 170) {
					mountainCounter++;
				}
				if (points[i] < seaLevel && points[i] >= 1) {
					oceanCounter++;
				}
			}

			if (mountainCounter >= 2) {
				if (chance < 60) {
					chunk.forceMiddle(random.nextInt(50) + 170);
				}
			}

			if (oceanCounter >= 2) {
				if (chance < 90) {
					chunk.setCorners(random.nextInt(seaLevel) + 20, random.nextInt(seaLevel) + 20,
							random.nextInt(seaLevel) + 20, random.nextInt(seaLevel) + 20);
					chunk.forceMiddle(random.nextInt(seaLevel) + 20);
				}
			}

			chunk.setCorners(randNW, randNE, randSW, randSE);

			ds.runDiamondSqure(chunk);
			map.getChunkList().remove(chunk);

		}

	}

	public void setSeaLevel(int seaLevel) {
		this.seaLevel = seaLevel;
	}

}

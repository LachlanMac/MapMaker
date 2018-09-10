package com.lmac.mapmaker.main.components;

import java.util.ArrayList;
import java.util.Random;

import com.lmac.mapmaker.main.math.Vector2;

public class Map {

	private Random rn;
	public Chunk[][] chunks;
	private int chunkSize;
	private Chunk currentChunk;
	private int chunkMultiple;
	private int minClamp, maxClamp;
	protected final static int DEFAULT_MIN_CLAMP = 0;
	protected final static int DEFAULT_MAX_CLAMP = 255;
	private int totalWidth;
	private int totalHeight;
	private int widthInChunks, heightInChunks;
	private boolean oceanEdges = false;
	private int[][] mapVals;
	int seed;

	private ArrayList<Chunk> chunkList;

	public Map(int seed, int widthInChunks, int heightInChunks, int chunkMultiple, int minClamp, int maxClamp,
			boolean oceanEdges) {
		this.seed = seed;
		this.oceanEdges = oceanEdges;
		this.chunkMultiple = chunkMultiple;
		this.widthInChunks = widthInChunks;
		this.heightInChunks = heightInChunks;
		chunks = new Chunk[widthInChunks][heightInChunks];
		this.minClamp = minClamp;
		this.maxClamp = maxClamp;
		chunkList = new ArrayList<Chunk>();
		chunkSize = (int) Math.pow(2, chunkMultiple) + 1;
		rn = new Random(seed);

		initEmptyChunks();

		currentChunk = chunks[widthInChunks / 2][heightInChunks / 2];

		totalWidth = chunkSize * widthInChunks;
		totalHeight = chunkSize * heightInChunks;

	}

	public void initEmptyChunks() {

		for (int y = 0; y < heightInChunks; y++) {
			for (int x = 0; x < widthInChunks; x++) {

				chunks[x][y] = new Chunk(seed, this, new Vector2(x, y), minClamp, maxClamp, oceanEdges);
				chunkList.add(chunks[x][y]);

			}
		}

	}

	public void setChunkSize(int chunkSize) {
		this.chunkSize = chunkSize;
	}

	public int getChunkSize() {
		return this.chunkSize;
	}

	public Chunk getChunkAt(int x, int y) {

		if (x < 0 || x > chunks[0].length || y < 0 || y > chunks.length) {
			System.out.println("Invalid Chunk");
			return null;
		}
		return chunks[x][y];

	}

	public Chunk getCurrentChunk() {
		return currentChunk;
	}

	public void generateChunk(Vector2 v) {

	}

	public int getWidth() {
		return totalWidth;
	}

	public int getHeight() {
		return totalHeight;
	}

	public int getGlobalValue(int x, int y) {

		int mapX = x / chunkSize;
		int modX = x % chunkSize;
		int mapY = y / chunkSize;
		int modY = y % chunkSize;

		// System.out.println("VALUE OF MAP[" + mapX + "-" + mapY + "] point: (" + modX
		// + "," + modY + ")");

		return chunks[mapX][mapY].getPoint(modX, modY);

	}

	public void setGlobalValue(int x, int y, int value) {

		int mapX = x / chunkSize;
		int modX = x % chunkSize;
		int mapY = y / chunkSize;
		int modY = y % chunkSize;

		chunks[mapX][mapY].forcePoint(modX, modY, value);

	}

	public Chunk getNeighbourChunk(Chunk chunk, Vector2 dir) {
		Chunk neighbour = null;
		int xChunk = chunk.getMapX() + dir.getX();
		int yChunk = chunk.getMapY() + dir.getY();

		if (yChunk < 0 || xChunk < 0 || xChunk >= widthInChunks || yChunk >= heightInChunks) {
			return neighbour;
		} else {

			neighbour = chunks[xChunk][yChunk];

		}
		return neighbour;

	}

	public ArrayList<Chunk> getChunkList() {
		return chunkList;
	}

	public int getChunkLength() {
		return chunks.length;
	}

	public int[][] getAll() {
		int[][] all = new int[this.getWidth()][this.getHeight()];

		for (int y = 0; y < this.getHeight(); y++) {
			for (int x = 0; x < this.getWidth(); x++) {

				all[x][y] = this.getGlobalValue(x, y);

			}
		}

		return all;

	}

	public int[][] getMapVals() {
		System.out.println("Getting vals");
		return mapVals;
	}

	public void setMap(int[][] newMap) {
		mapVals = newMap;
	}

	public void climatize(float f) {

		int[][] vals = this.getAll();

		for (int y = 0; y < this.getHeight(); y++) {
			for (int x = 0; x < this.getWidth(); x++) {

				int below100 = Math.min(100, this.getGlobalValue(x, y) + (int) afterDeadZone(y, f));

				setGlobalValue(x, y, Math.max(below100, 0));

				vals[x][y] = Math.max(below100, 0);

			}
		}

		this.setMap(vals);

	}

	public float afterDeadZone(int height, float deadBandExtreme) {
		float deadZone = deadBandExtreme;
		float subtractor = 0;
		float percentHeight = (float) height / (float) this.getHeight();

		if (percentHeight >= 0.42 && percentHeight <= 0.58) {

			return 0;

		} else if (percentHeight <= 0.42 && percentHeight >= 0.3) {
			// between 30 and 42%
			float middle = (0.42f + 0.3f) / 2;

			float distanceToMid = Math.abs(middle - percentHeight);
			float maxDistanceToMid = .42f - middle;

			float percentFromMid = distanceToMid / maxDistanceToMid;

			subtractor = deadZone + Math.abs(deadZone * percentFromMid);

		} else if (percentHeight >= 0.58 && percentHeight <= 0.7) {
			// between 58 and 70%
			float middle = (0.7f + 0.58f) / 2;
			float distanceToMid = Math.abs(middle - percentHeight);
			float maxDistanceToMid = .7f - middle;

			float percentFromMid = distanceToMid / maxDistanceToMid;

			subtractor = deadZone + Math.abs(deadZone * percentFromMid);
		}
		if (subtractor != 0) {

		}
		return subtractor;

	}

	public void setTemperatures(int minTemp, int maxTemp) {

		int[][] vals = this.getAll();

		for (int y = 0; y < this.getHeight(); y++) {
			for (int x = 0; x < this.getWidth(); x++) {

				int below100 = Math.min(maxTemp, this.getGlobalValue(x, y) + getTempModifier(y, minTemp, maxTemp));

				setGlobalValue(x, y, Math.max(below100, 0));

				vals[x][y] = Math.max(below100, 0);

			}
		}

		this.setMap(vals);

	}

	public int getTempModifier(int height, int minTemp, int maxTemp) {

		float percentHeight = (float) height / (float) this.getHeight();
		float distanceFromEquator = Math.abs(percentHeight - .50f);
		float multiplier = distanceFromEquator * 2;

		float newTemp = maxTemp + (-multiplier * 100);
		return (int) newTemp;

	}

	public float inDeadZone(int height) {

		float subtractor = -25;

		float percentHeight = (float) height / (float) this.getHeight();

		if (percentHeight >= 0.40 && percentHeight <= 0.60) {

			float diff = Math.abs(.5f - percentHeight);

			diff = diff * 100;

			float offset = -(diff * 2.5f + (rn.nextInt(6) - 3) + subtractor);

			return offset;

		}

		if (percentHeight >= 0.24f && percentHeight <= 0.40f) {

			float diff = Math.abs(.32f - percentHeight);

			diff = diff * 100;

			float offset = diff * 2.5f + (rn.nextInt(6) - 3) + subtractor;

			return offset;

		} else if (percentHeight >= 0.60f && percentHeight <= 0.76f) {

			float diff = Math.abs(.68f - percentHeight);
			diff = diff * 100;
			float offset = diff * 2.5f + (rn.nextInt(6) - 3) + subtractor;

			return offset;

		} else {

			subtractor = 0;
		}

		return subtractor;

	}

}

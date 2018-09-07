package com.lmac.mapmaker.main.components;

import java.util.ArrayList;
import java.util.Random;

import com.lmac.mapmaker.main.math.Vector2;

public class Chunk {

	protected Map map;
	protected int chunkSize;
	protected int[][] mapVals;
	protected Vector2 mapLocation;
	protected boolean diamondSquared = false;
	protected boolean oceanEdges = false;
	private Random noise;
	private final int DEFAULT_MIN_CLAMP = 0;
	private final int DEFAULT_MAX_CLAMP = 255;
	private int minClamp, maxClamp, seed;

	public Chunk(int seed, Map map, Vector2 mapLocation, boolean oceanEdges) {
		this.mapLocation = mapLocation;
		this.map = map;
		this.seed = seed;
		chunkSize = map.getChunkSize();
		mapVals = new int[chunkSize][chunkSize];
		if (oceanEdges)
			mapEdge();

		minClamp = DEFAULT_MIN_CLAMP;
		maxClamp = DEFAULT_MAX_CLAMP;

	}

	public Chunk(int seed, Map map, Vector2 mapLocation, int minClamp, int maxClamp, boolean oceanEdges) {
		this.mapLocation = mapLocation;
		this.map = map;
		this.seed = seed;
		chunkSize = map.getChunkSize();
		mapVals = new int[chunkSize][chunkSize];
		if (oceanEdges)
			mapEdge();
		noise = new Random(seed);
		this.minClamp = minClamp;
		this.maxClamp = maxClamp;

	}

	public void mapEdge() {

		if (mapLocation.getX() == 0) {
			mapVals[0][0] = 5;
			mapVals[0][chunkSize - 1] = 5;
		}
		if (mapLocation.getX() == map.getChunkLength() - 1) {
			mapVals[chunkSize - 1][0] = 5;
			mapVals[chunkSize - 1][chunkSize - 1] = 5;
		}

		if (mapLocation.getY() == map.getChunkLength() - 1) {
			mapVals[chunkSize - 1][chunkSize - 1] = 5;
			mapVals[0][chunkSize - 1] = 5;
		}
		if (mapLocation.getY() == 0) {
			mapVals[chunkSize - 1][0] = 5;
			mapVals[0][0] = 5;
		}

	}

	public void forcePoint(int x, int y, int val) {
		int data = val;

		if (val > 255) {
			data = 255;
		}
		if (val < 0) {
			data = 0;
		}
		mapVals[x][y] = data;
	}

	public void setPoint(int x, int y, int val) {
		int data = val;

		if (val > maxClamp) {
			data = maxClamp;
		}
		if (val < minClamp) {
			data = minClamp;
		}

		if (mapVals[x][y] == 0) {
			mapVals[x][y] = data;
		}

	}

	// Function that ensures the point in question is within the bounds of the array
	public boolean isValid(int x, int y) {
		if (x < 0 || y < 0 || x >= chunkSize || y >= chunkSize)
			return false;
		else
			return true;

	}

	public int getDiamondAverage(int x, int y, int halfStep) {
		ArrayList<Integer> points = new ArrayList<Integer>();
		// n
		if (isValid(x, y - halfStep)) {
			points.add(mapVals[x][y - halfStep]);
		}
		// s
		if (isValid(x, y + halfStep)) {
			points.add(mapVals[x][y + halfStep]);
		}
		// w
		if (isValid(x - halfStep, y)) {
			points.add(mapVals[x - halfStep][y]);
		}
		// e
		if (isValid(x + halfStep, y)) {
			points.add(mapVals[x + halfStep][y]);
		}
		int sum = 0;
		for (int i = 0; i < points.size(); i++) {
			sum = sum + points.get(i);
		}
		return sum / points.size();

	}

	// Prints the map to the console
	public void printChunk() {
		System.out.println("\n :: MAP :: \n");

		for (int y = 0; y < chunkSize; y++) {
			System.out.println();
			for (int x = 0; x < chunkSize; x++) {
				System.out.print(mapVals[x][y] + " ");
			}
		}

	}

	// sets the initial corners of the map, required for start of the DS algorithm
	public void setCorners(int nw, int ne, int sw, int se) {
		setPoint(0, 0, nw);
		setPoint(chunkSize - 1, chunkSize - 1, se);
		setPoint(0, chunkSize - 1, sw);
		setPoint(chunkSize - 1, 0, ne);

	}

	// Skip the first averaging step of the DS algorithm
	public void forceMiddle(int mid) {
		setPoint(chunkSize / 2, chunkSize / 2, mid);
	}

	public int[][] getWall(String dir) {
		int[][] wall;
		if (dir.equals("north") || dir.equals("south")) {
			wall = new int[chunkSize][2];
		} else if (dir.equals("east") || dir.equals("west")) {
			wall = new int[2][chunkSize];
		} else {
			System.out.println("Error");
			return null;
		}
		if (dir == "north") {
			for (int y = 0; y < 2; y++) {
				for (int x = 0; x < chunkSize; x++) {
					wall[x][y] = getPoint(x, y);
				}
			}
		}

		if (dir == "south") {
			for (int y = 0; y < 2; y++) {
				for (int x = 0; x < chunkSize; x++) {
					wall[x][y] = getPoint(x, chunkSize - 1 - y);
				}
			}
		}

		if (dir == "west") {
			for (int y = 0; y < chunkSize; y++) {
				for (int x = 0; x < 2; x++) {
					wall[x][y] = getPoint(x, y);
				}
			}
		}

		if (dir == "east") {
			for (int y = 0; y < chunkSize; y++) {
				for (int x = 0; x < 2; x++) {
					wall[x][y] = getPoint((chunkSize - 1 - x), y);
				}
			}
		}

		// returns the correct array
		return wall;
	}

	public int applyNoise() {

		int r = noise.nextInt(6) - 3;

		return r;
	}

	// Function that sets the border of a new map with set height parameters
	public void setWall(String dir, int[][] wall) {
		if (dir.equals("west")) {
			for (int y = 0; y < chunkSize; y++) {
				for (int x = 0; x < 2; x++) {
					setPoint(x, y, wall[x][y] + applyNoise());
				}
			}
		}
		if (dir.equals("east")) {
			for (int y = 0; y < chunkSize; y++) {
				for (int x = 0; x < 2; x++) {
					setPoint(chunkSize - 1 - x, y, wall[x][y] + applyNoise());
				}
			}
		}
		if (dir.equals("north")) {
			for (int y = 0; y < 2; y++) {
				for (int x = 0; x < chunkSize; x++) {
					setPoint(x, y, wall[x][y] + applyNoise());
				}
			}
		}
		if (dir.equals("south")) {
			for (int y = 0; y < 2; y++) {
				for (int x = 0; x < chunkSize; x++) {
					setPoint(x, chunkSize - 1 - y, wall[x][y] + applyNoise());
				}
			}
		}

	}

	// gets a point within the map
	public int getPoint(int x, int y) {
		return mapVals[x][y];
	}

	public int getSize() {
		return mapVals.length;
	}

	public Vector2 getMapLocation() {
		return mapLocation;
	}

	public int getMapX() {
		return mapLocation.getX();
	}

	public int getMapY() {
		return mapLocation.getY();
	}

	public Map getMap() {

		return map;
	}

	public boolean getDiamondSquared() {
		return diamondSquared;
	}

	public void setDiamondSquared(boolean status) {
		diamondSquared = status;
	}
}

package com.lmac.mapmaker.main.data;

import java.util.ArrayList;

import com.lmac.mapmaker.main.math.Vector2;

public class Lake {

	static int lakeIDCounter = 0;

	private TileData mostShallow;
	private Vector2 mostWest, mostEast, mostNorth, mostSouth;

	private int lakeID;
	ArrayList<TileData> lakePoints;

	public Lake() {
		lakePoints = new ArrayList<TileData>();
	}

	public void addLakePoint(TileData data) {

		if (lakePoints.isEmpty()) {
			this.lakeID = lakeIDCounter;
			lakeIDCounter++;
		}

		lakePoints.add(data);
	}

	public String toString() {

		return "Lake " + lakeID + " + has " + lakePoints.size() + " many points  " + mostWest + " = " + mostEast + " = "
				+ mostSouth + " = " + mostNorth + "   MOST SHALLOW TILE " + mostShallow.getHeight();

	}

	public void setLimits() {

		mostWest = new Vector2(lakePoints.get(0).getX(), lakePoints.get(0).getY());
		mostEast = new Vector2(lakePoints.get(0).getX(), lakePoints.get(0).getY());
		mostNorth = new Vector2(lakePoints.get(0).getX(), lakePoints.get(0).getY());
		mostSouth = new Vector2(lakePoints.get(0).getX(), lakePoints.get(0).getY());
		mostShallow = lakePoints.get(0);
		for (TileData t : lakePoints) {

			if (t.getHeight() > mostShallow.getHeight()) {
				mostShallow = t;
			}

			if (t.getX() < mostWest.getX()) {
				mostWest = t.getLoc();
			}
			if (t.getX() > mostEast.getX()) {
				mostEast = t.getLoc();
			}
			if (t.getY() < mostNorth.getY()) {
				mostNorth = t.getLoc();
			}
			if (t.getY() > mostSouth.getY()) {
				mostSouth = t.getLoc();
			}

		}

	}

	public int getPointCount() {
		return lakePoints.size();
	}

	public TileData getHighestPoint() {
		return mostShallow;
	}

	public Vector2 getMostWest() {
		return mostWest;
	}

	public Vector2 getMostEast() {
		return mostEast;
	}

	public Vector2 getMostNorth() {
		return mostNorth;
	}

	public Vector2 getMostSouth() {
		return mostSouth;
	}

}

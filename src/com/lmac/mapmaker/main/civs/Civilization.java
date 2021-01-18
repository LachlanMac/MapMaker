package com.lmac.mapmaker.main.civs;

import java.util.ArrayList;

import com.lmac.mapmaker.main.data.DataMap;
import com.lmac.mapmaker.main.data.TileData;
import com.lmac.mapmaker.races.Race;

public class Civilization {

	int population;
	TileData startingTile;
	ArrayList<TileData> tiles;
	DataMap dm;
	Race race;

	int maxSize = 3;

	public Civilization(TileData startingTile, Race race, DataMap dm) {
		this.dm = dm;
		this.race = race;
		this.startingTile = startingTile;
		tiles = new ArrayList<TileData>();
		tiles.add(startingTile);
		firstExpand(4);

	}

	public void firstExpand(int size) {

		for (int maxY = startingTile.getY() - size; maxY < startingTile.getY() + size; maxY++) {
			for (int maxX = startingTile.getX() - size; maxX < startingTile.getX() + size; maxX++) {

				tiles.add(dm.getTile(maxX, maxY));

			}
		}

	}

	public void expand() {

	}

	public ArrayList<TileData> getTiles() {
		return tiles;
	}

	public double getDistanceFromCenter(TileData otherTile) {

		return Math.sqrt(Math.pow((otherTile.getX() - startingTile.getX()), 2)
				+ Math.pow(otherTile.getY() - startingTile.getY(), 2));

	}

}

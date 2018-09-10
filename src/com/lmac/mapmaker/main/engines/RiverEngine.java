package com.lmac.mapmaker.main.engines;

import java.util.ArrayList;
import java.util.Random;

import com.lmac.mapmaker.main.biomes.BiomeEngine;
import com.lmac.mapmaker.main.data.DataMap;
import com.lmac.mapmaker.main.data.Lake;
import com.lmac.mapmaker.main.data.TileData;

public class RiverEngine {

	DataMap dm;

	private int minRiverLength = 500;
	private int maxRiverLength = 5000;
	private int seed = 0;

	public RiverEngine() {

	}

	public void createRiver(DataMap dm, TileData startingTile, Lake originLake) {
		this.dm = dm;
		TileData startTile = startingTile;
		startTile.setBiome(BiomeEngine.getRiver());

		Area startingArea = new Area(dm, startingTile);

		Random rn = new Random(seed);
		int length = rn.nextInt(maxRiverLength - minRiverLength) + minRiverLength;

		for (int i = 0; i < length; i++) {

			TileData nextTile = startingArea.getHighestTile();

			if (nextTile.getBiome().getName().equals(BiomeEngine.getBiomeByName("Mountain").getName())) {
				return;
			}

			if (nextTile.getBiome().getName().equals(BiomeEngine.getBiomeByName("Ice Cap").getName())) {
				return;
			}

			nextTile.setBiome(BiomeEngine.getRiver());

			startingArea = startingArea.getNextArea(nextTile);
		}

	}

	public void thickenRivers(DataMap dm) {

		for (int y = 5; y < dm.getHeight() - 5; y++) {
			for (int x = 5; x < dm.getWidth() - 5; x++) {

				if (dm.getTile(x, y).getBiome().getName().equals("River")) {

					dm.getTile(x, y - 1).setBiome(BiomeEngine.getRiverBank());
					dm.getTile(x + 1, y - 1).setBiome(BiomeEngine.getRiverBank());
					dm.getTile(x + 1, y).setBiome(BiomeEngine.getRiverBank());
					dm.getTile(x + 1, y + 1).setBiome(BiomeEngine.getRiverBank());
					dm.getTile(x, y + 1).setBiome(BiomeEngine.getRiverBank());
					dm.getTile(x - 1, y + 1).setBiome(BiomeEngine.getRiverBank());
					dm.getTile(x - 1, y).setBiome(BiomeEngine.getRiverBank());
					dm.getTile(x - 1, y - 1).setBiome(BiomeEngine.getRiverBank());

				}

			}

		}

		boolean thickerRivers = true;
		if (thickerRivers) {

			for (int y = 5; y < dm.getHeight() - 5; y++) {
				for (int x = 5; x < dm.getWidth() - 5; x++) {

					if (dm.getTile(x, y).getBiome().getName().equals("River Bank")) {

						dm.getTile(x, y - 1).setBiome(BiomeEngine.getRiver());
						dm.getTile(x + 1, y - 1).setBiome(BiomeEngine.getRiver());
						dm.getTile(x + 1, y).setBiome(BiomeEngine.getRiver());
						dm.getTile(x + 1, y + 1).setBiome(BiomeEngine.getRiver());
						dm.getTile(x, y + 1).setBiome(BiomeEngine.getRiver());
						dm.getTile(x - 1, y + 1).setBiome(BiomeEngine.getRiver());
						dm.getTile(x - 1, y).setBiome(BiomeEngine.getRiver());
						dm.getTile(x - 1, y - 1).setBiome(BiomeEngine.getRiver());

					}

				}

			}

		}

	}

	public void generateRiver(ArrayList<Lake> lakes, DataMap dm) {

		for (Lake l : lakes) {

			int waterContent = l.getPointCount();
			if (waterContent > 1000) {

				createRiver(dm, l.getHighestPoint(), l);

			}

		}

	}

	public void setMinRiverLength(int val) {
		this.minRiverLength = val;
	}

	public void setMaxRiverLength(int val) {
		this.maxRiverLength = val;
	}

	public void setSeed(int val) {
		this.seed = val;
	}
}

class Area {

	TileData nTile, neTile, eTile, seTile, sTile, swTile, wTile, nwTile, startingTile;
	DataMap dm;
	ArrayList<TileData> areaTiles;
	BiomeEngine be;

	public Area(DataMap dm, TileData startingTile) {
		areaTiles = new ArrayList<TileData>();
		this.dm = dm;
		this.be = new BiomeEngine();
		nTile = dm.getTile(startingTile.getX(), startingTile.getY() - 1);
		neTile = dm.getTile(startingTile.getX() + 1, startingTile.getY() - 1);
		eTile = dm.getTile(startingTile.getX() + 1, startingTile.getY());
		seTile = dm.getTile(startingTile.getX() + 1, startingTile.getY() + 1);
		sTile = dm.getTile(startingTile.getX(), startingTile.getY() + 1);
		swTile = dm.getTile(startingTile.getX() - 1, startingTile.getY() + 1);
		wTile = dm.getTile(startingTile.getX() - 1, startingTile.getY());
		nwTile = dm.getTile(startingTile.getX() - 1, startingTile.getY() - 1);
		areaTiles.add(nTile);
		areaTiles.add(neTile);
		areaTiles.add(nwTile);
		areaTiles.add(sTile);
		areaTiles.add(seTile);
		areaTiles.add(swTile);
		areaTiles.add(wTile);
		areaTiles.add(eTile);

	}

	public TileData getHighestTile() {

		TileData highestTile = nTile;

		for (TileData d : areaTiles) {

			boolean comethefuckon = (d.getBiome().getName().equals(BiomeEngine.getRiver().getName()));

			if (d.getHeight() > highestTile.getHeight() && !comethefuckon) {

				d.setBiome(BiomeEngine.getRiver());
				highestTile = d;
			}
		}

		return highestTile;

	}

	public Area getNextArea(TileData nextTile) {

		Area nextArea = new Area(dm, nextTile);
		return nextArea;
	}

}

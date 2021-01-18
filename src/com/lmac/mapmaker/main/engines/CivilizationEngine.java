package com.lmac.mapmaker.main.engines;

import java.util.ArrayList;
import java.util.Random;

import com.lmac.mapmaker.main.civs.Civilization;
import com.lmac.mapmaker.main.data.DataMap;
import com.lmac.mapmaker.main.data.TileData;
import com.lmac.mapmaker.races.Race;

public class CivilizationEngine {

	final int MIN_LIVABILITY_INDEX = 70;

	ArrayList<TileData> points;
	ArrayList<Civilization> civList;

	public CivilizationEngine() {
		civList = new ArrayList<Civilization>();
	}

	public void addCivilization(Civilization civ) {
		civList.add(civ);
	}

	public ArrayList<TileData> getStartingPoints() {
		return points;
	}

	public void setPoints(DataMap dm, Race race) {

		points = new ArrayList<TileData>();

		for (int y = 0; y < dm.getHeight(); y += 60) {
			for (int x = 0; x < dm.getWidth(); x += 60) {

				if (dm.getTile(x, y).getLivibilityValue() > MIN_LIVABILITY_INDEX) {

					if (dm.getTile(x, y).getBiome().isWaterTile()) {

					} else {

						TileData tileData = dm.getTile(x, y);
						points.add(tileData);

					}

				}

			}

		}

		if (points.size() < race.getMaxCivs()) {
			System.out.println("Not enough desireable spots for race " + race.getName());
		} else {

			int startingPoints = points.size();

			for (int i = 0; i < startingPoints - race.getMaxCivs(); i++) {

				Random rn = new Random(System.currentTimeMillis());
				int index = rn.nextInt(points.size());
				points.remove(index);

			}

		}

		System.out.println("Points left : " + points.size() + " compared to max points " + race.getMaxCivs());

	}

	public ArrayList<Civilization> getCivList() {
		return civList;
	}

}

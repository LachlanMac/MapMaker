package com.lmac.mapmaker.main.engines;

import com.lmac.mapmaker.main.data.DataMap;
import com.lmac.mapmaker.main.data.TileData;
import com.lmac.mapmaker.races.Race;

public class TileStatEngine {

	Race race;

	public TileStatEngine() {

	}

	public void setStats(DataMap dm, Race race) {

		for (int y = 5; y < dm.getHeight() - 5; y++) {
			for (int x = 5; x < dm.getWidth() - 5; x++) {

				TileData d = dm.getTile(x, y);

				float livibilityIndex = d.getBiome().getLivibilityIndex();

				if (livibilityIndex == 0) {
					d.setLivibilityValue(0);

				} else {

					float p1 = dm.getTile(x - 1, y - 1).getBiome().getAdjacencyIndex();
					float p2 = dm.getTile(x + 1, y - 1).getBiome().getAdjacencyIndex();
					float p3 = dm.getTile(x, y - 1).getBiome().getAdjacencyIndex();
					float p4 = dm.getTile(x - 1, y + 1).getBiome().getAdjacencyIndex();
					float p5 = dm.getTile(x + 1, y + 1).getBiome().getAdjacencyIndex();
					float p6 = dm.getTile(x, y + 1).getBiome().getAdjacencyIndex();
					float p7 = dm.getTile(x - 1, y).getBiome().getAdjacencyIndex();
					float p8 = dm.getTile(x + 1, y).getBiome().getAdjacencyIndex();

					float adjacencyTotal = (p1 + p2 + p3 + p4 + p5 + p6 + p7 + p8) / 8;

					float totalLivibility = ((livibilityIndex * 2) + adjacencyTotal);

					int animalCover = d.getBiome().getAnimalCover();
					int plantCover = d.getBiome().getPlantCover();

					float foodChance = (animalCover + plantCover) / 2;

					d.setFood((int) foodChance);
					d.setMinerals(d.getBiome().getMineralCover());
					d.setTrees(d.getBiome().getTreeCover());

					float diffHumidity = Math.abs(d.getHumidity() - race.getIdealHumidity());
					float diffTemperature = Math.abs(d.getTemperature() - race.getIdealTemperature());

					float fromZeroHumidity = -(diffHumidity / 2)  ;
					float fromZeroTemp = -(diffTemperature / 2);
					int bonus = 0;

					if (fromZeroTemp < 3 && fromZeroTemp > -3 && fromZeroHumidity > -3 && fromZeroHumidity < 3) {
						bonus += 20;
					}

					if (d.getBiome().equals(race.getPreferredBiome())) {
						bonus += 20;
					}

					// System.out.println("Total : " + totalLivibility + " FoodChance " + foodChance
					// + " humidBonus " + fromZeroHumidity + " Temp Bonus " + fromZeroTemp);

					float value = (foodChance / 2) + totalLivibility + fromZeroHumidity + fromZeroTemp + bonus + 30;
					d.setLivibilityValue(Math.max(2, Math.min(value, 100)));

				}

			}
		}

	}

}

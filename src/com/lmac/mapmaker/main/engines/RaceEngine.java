package com.lmac.mapmaker.main.engines;

import java.util.ArrayList;

import com.lmac.mapmaker.races.Race;

public class RaceEngine {

	ArrayList<Race> races;

	public RaceEngine() {
		races = new ArrayList<Race>();
	}

	public void addRace(Race race) {
		races.add(race);
	}

	public ArrayList<Race> getRaces() {
		return races;
	}

}

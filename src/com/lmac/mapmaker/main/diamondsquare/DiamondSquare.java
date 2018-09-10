package com.lmac.mapmaker.main.diamondsquare;

import java.util.ArrayList;
import java.util.Random;

import com.lmac.mapmaker.main.components.Chunk;

public class DiamondSquare {

	private ArrayList<SquareStep> squareSteps;
	private ArrayList<DiamondStep> diamondSteps;
	private int magnitude, seed, initialMagnitude;
	private float decay;
	private Random r;
	private Chunk currentChunk;

	public DiamondSquare(int seed, int magnitude, float decay) {
		this.seed = seed;
		this.magnitude = magnitude;
		this.initialMagnitude = magnitude;
		
		this.decay = decay;
		r = new Random(seed);
	}

	public void runDiamondSqure(Chunk chunk) {

		this.currentChunk = chunk;
		int stepSize = currentChunk.getSize() - 1;
		// Lists to track the steps
		squareSteps = new ArrayList<SquareStep>();
		diamondSteps = new ArrayList<DiamondStep>();
		// add initial steps
		squareSteps.add(new SquareStep(0, 0, stepSize));
		diamondSteps.add(new DiamondStep(0, 0, stepSize));
		// resets magnitude
		magnitude = initialMagnitude;
		// resets Stepsize
		int currentStep = stepSize;

		// loop that runs the DS algorithm iterations
		while (currentStep >= 2) {

			for (int ss = 0; ss < squareSteps.size(); ss++) {
				if (squareSteps.get(ss).getStepSize() == currentStep)
					squareStep(squareSteps.get(ss).getX(), squareSteps.get(ss).getY(),
							squareSteps.get(ss).getStepSize());
			}
			reduceMagnitude();
			for (int ds = 0; ds < diamondSteps.size(); ds++) {
				if (diamondSteps.get(ds).getStepSize() == currentStep)
					diamondStep(diamondSteps.get(ds).getX(), diamondSteps.get(ds).getY(),
							diamondSteps.get(ds).getStepSize());
			}
			currentStep = currentStep / 2;
			// currentChunk.printChunk();

		}

	}

	// Square step of DiamondSquare Algorithm
	public void squareStep(int x, int y, int currentStep) {

		int nw = currentChunk.getPoint(x, y);
		int ne = currentChunk.getPoint(x + currentStep, y);
		int se = currentChunk.getPoint(x + currentStep, y + currentStep);
		int sw = currentChunk.getPoint(x, y + currentStep);

		int average = ((nw + ne + se + sw) / 4) + getRandomInt();

		currentChunk.setPoint(x + getHalfStep(currentStep), y + getHalfStep(currentStep), average);

		if (getHalfStep(currentStep) < 2) {
			return;
		}
		squareSteps.add(new SquareStep(x, y, getHalfStep(currentStep)));
		squareSteps.add(new SquareStep(x + getHalfStep(currentStep), y, getHalfStep(currentStep)));
		squareSteps.add(new SquareStep(x, y + getHalfStep(currentStep), getHalfStep(currentStep)));
		squareSteps.add(
				new SquareStep(x + getHalfStep(currentStep), y + getHalfStep(currentStep), getHalfStep(currentStep)));

	}

	// Diamond Step of DiamondSquare Algorithm
	public void diamondStep(int x, int y, int currentStep) {
		int hs = getHalfStep(currentStep);
		int cX = x + hs;
		int cY = y + hs;

		// e
		currentChunk.setPoint(cX + hs, cY, currentChunk.getDiamondAverage(cX + hs, cY, hs) + getRandomInt());
		// n
		currentChunk.setPoint(cX, cY - hs, currentChunk.getDiamondAverage(cX, cY - hs, hs) + getRandomInt());
		// w
		currentChunk.setPoint(cX - hs, cY, currentChunk.getDiamondAverage(cX - hs, cY, hs) + getRandomInt());
		// s
		currentChunk.setPoint(cX, cY + hs, currentChunk.getDiamondAverage(cX, cY + hs, hs) + getRandomInt());

		if (getHalfStep(currentStep) < 2) {
			return;
		}
		diamondSteps.add(new DiamondStep(x, y, getHalfStep(currentStep)));
		diamondSteps.add(new DiamondStep(x + getHalfStep(currentStep), y, getHalfStep(currentStep)));
		diamondSteps.add(new DiamondStep(x, y + getHalfStep(currentStep), getHalfStep(currentStep)));
		diamondSteps.add(
				new DiamondStep(x + getHalfStep(currentStep), y + getHalfStep(currentStep), getHalfStep(currentStep)));
	}

	// helper function get get half of the current step
	public int getHalfStep(int currentStep) {

		return (int) Math.floor(currentStep / 2);

	}

	// helper function get get average
	public int getAverage(int[] nums) {
		int sum = 0;
		for (int x = 0; x < nums.length; x++) {
			sum = sum + nums[x];
		}
		return (int) sum / nums.length;
	}

	// Reduces the magnitude by decay factor
	public void reduceMagnitude() {

		magnitude = (int) ((float) magnitude * decay);

		if (magnitude < 1) {
			magnitude = 1;
		}

	}

	// helper function that gets random integer based on magnitude
	public int getRandomInt() {
		int randomInt = (r.nextInt(magnitude) * 2) - magnitude;
		return randomInt;
	}

}

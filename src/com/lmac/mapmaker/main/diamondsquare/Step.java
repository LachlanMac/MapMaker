package com.lmac.mapmaker.main.diamondsquare;

public abstract class Step {

	private int x, y, stepSize, halfstep;

	public Step(int x, int y, int stepSize) {

		this.x = x;
		this.y = y;
		this.stepSize = stepSize;
		this.halfstep = (int) Math.floor(stepSize / 2);
	}

	public int getStepSize() {
		return stepSize;
	}

	public int getX() {
		return x;
	}

	public int getY() {

		return y;
	}

	public String toString() {

		return "Step [" + x + ", " + y + "]   size:  " + stepSize;
	}

}
package com.lmac.mapmaker.main.math;

public final class Vector2 {

	public float x;
	public float y;

	final static Vector2 UP = new Vector2(0, -1);
	final static Vector2 DOWN = new Vector2(0, 1);
	final static Vector2 LEFT = new Vector2(-1, 0);
	final static Vector2 RIGHT = new Vector2(1, 0);
	final static Vector2 CENTER = new Vector2(0, 0);

	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Vector2 add(final Vector2 v) {
		return new Vector2(x + v.x, y + v.y);
	}

	public float getDistance(Vector2 d) {

		float distance = 0;

		distance = (float) Math.sqrt((Math.pow((d.x - this.x), 2)) + (Math.pow(d.y - this.y, 2)));
		return Math.abs(distance);
	}

	public int getX() {
		return (int) x;
	}

	public int getY() {
		return (int) y;
	}

	public String toString() {
		return "V:[" + x + ", " + y + "]";
	}
}
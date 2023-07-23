package nl.saxion.mazes.compass;

import nl.saxion.mazes.Direction;

import java.util.Random;

public enum CompassDirection implements Direction<CompassDirection> {
	NORTH,
	NORTHEAST,
	EAST,
	SOUTHEAST,
	SOUTH,
	SOUTHWEST,
	WEST,
	NORTHWEST;

	private static final Random RNG = new Random();
	public CompassDirection getLeft() {
		int dirValue = ordinal();
		int newValue = dirValue+7;
		newValue = newValue%8;
		CompassDirection[] possibleValues = CompassDirection.values();
		return possibleValues[newValue];
	}

	public CompassDirection getRight() {
		return CompassDirection.values()[(ordinal()+1)%8];
	}

	public CompassDirection getOpposite() {
		return CompassDirection.values()[(ordinal()+4)%8];
	}

	public CompassDirection getRandomDirection() {
		return CompassDirection.values()[RNG.nextInt(CompassDirection.values().length)];
	}
}

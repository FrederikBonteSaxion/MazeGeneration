package nl.saxion.mazes.nesw;

import nl.saxion.mazes.Direction;

import java.util.Random;

public enum NESWDirection implements Direction<NESWDirection> {
	NORTH,
	EAST,
	SOUTH,
	WEST;

	private static final Random RNG = new Random();
	public NESWDirection getLeft() {
		int dirValue = ordinal();
		int newValue = dirValue+3;
		newValue = newValue%4;
		NESWDirection[] possibleValues = NESWDirection.values();
		return possibleValues[newValue];
	}

	public NESWDirection getRight() {
		return NESWDirection.values()[(ordinal()+1)%4];
	}

	public NESWDirection getOpposite() {
		return NESWDirection.values()[(ordinal()+2)%4];
	}

	public NESWDirection getRandomDirection() {
		return NESWDirection.values()[RNG.nextInt(NESWDirection.values().length)];
	}
}

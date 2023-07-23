package nl.saxion.mazes.nesw;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestNESWDirection {
	@Test
	public void testThereAreFourDirections() {
		assertEquals(4, NESWDirection.values().length);
		assertEquals(0, NESWDirection.NORTH.ordinal());
		assertEquals(1, NESWDirection.EAST.ordinal());
		assertEquals(2, NESWDirection.SOUTH.ordinal());
		assertEquals(3, NESWDirection.WEST.ordinal());
	}

	@Test
	public void testLeftDirectionsAreCorrect() {
		assertEquals(NESWDirection.WEST, NESWDirection.NORTH.getLeft());
		assertEquals(NESWDirection.EAST, NESWDirection.SOUTH.getLeft());
		assertEquals(NESWDirection.SOUTH, NESWDirection.WEST.getLeft());
		assertEquals(NESWDirection.NORTH, NESWDirection.EAST.getLeft());
	}

	@Test
	public void testRightDirectionsAreCorrect() {
		assertEquals(NESWDirection.EAST, NESWDirection.NORTH.getRight());
		assertEquals(NESWDirection.WEST, NESWDirection.SOUTH.getRight());
		assertEquals(NESWDirection.NORTH, NESWDirection.WEST.getRight());
		assertEquals(NESWDirection.SOUTH, NESWDirection.EAST.getRight());
	}

	@Test
	public void testOppositeDirectionsAreCorrect() {
		assertEquals(NESWDirection.SOUTH, NESWDirection.NORTH.getOpposite());
		assertEquals(NESWDirection.NORTH, NESWDirection.SOUTH.getOpposite());
		assertEquals(NESWDirection.EAST, NESWDirection.WEST.getOpposite());
		assertEquals(NESWDirection.WEST, NESWDirection.EAST.getOpposite());
	}

	@RepeatedTest(5)
	public void testRandomDirections() {
		assertTrue(NESWDirection.NORTH.getRandomDirection().ordinal()>=0);
		assertTrue(NESWDirection.SOUTH.getRandomDirection().ordinal()<=3);
		assertTrue(NESWDirection.EAST.getRandomDirection().ordinal()>=0);
		assertTrue(NESWDirection.WEST.getRandomDirection().ordinal()<=3);
	}
}

package nl.saxion.mazes.compass;

import nl.saxion.mazes.nesw.NESWDirection;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestCompassDirection {
	@Test
	public void testThereAreFourDirections() {
		assertEquals(8, CompassDirection.values().length);
		assertEquals(0, CompassDirection.NORTH.ordinal());
		assertEquals(2, CompassDirection.EAST.ordinal());
		assertEquals(4, CompassDirection.SOUTH.ordinal());
		assertEquals(1, CompassDirection.NORTHEAST.ordinal());
		assertEquals(3, CompassDirection.SOUTHEAST.ordinal());
		assertEquals(5, CompassDirection.SOUTHWEST.ordinal());
		assertEquals(7, CompassDirection.NORTHWEST.ordinal());
	}

	@Test
	public void testLeftDirectionsAreCorrect() {
		assertEquals(CompassDirection.NORTHWEST, CompassDirection.NORTH.getLeft());
		assertEquals(CompassDirection.SOUTHEAST, CompassDirection.SOUTH.getLeft());
		assertEquals(CompassDirection.SOUTHWEST, CompassDirection.WEST.getLeft());
		assertEquals(CompassDirection.NORTHEAST, CompassDirection.EAST.getLeft());
	}

	@Test
	public void testRightDirectionsAreCorrect() {
		assertEquals(CompassDirection.NORTHEAST, CompassDirection.NORTH.getRight());
		assertEquals(CompassDirection.SOUTHWEST, CompassDirection.SOUTH.getRight());
		assertEquals(CompassDirection.NORTHWEST, CompassDirection.WEST.getRight());
		assertEquals(CompassDirection.SOUTHEAST, CompassDirection.EAST.getRight());
	}

	@Test
	public void testOppositeDirectionsAreCorrect() {
		assertEquals(CompassDirection.SOUTH, CompassDirection.NORTH.getOpposite());
		assertEquals(CompassDirection.NORTH, CompassDirection.SOUTH.getOpposite());
		assertEquals(CompassDirection.EAST, CompassDirection.WEST.getOpposite());
		assertEquals(CompassDirection.WEST, CompassDirection.EAST.getOpposite());
		assertEquals(CompassDirection.SOUTHWEST, CompassDirection.NORTHEAST.getOpposite());
		assertEquals(CompassDirection.NORTHWEST, CompassDirection.SOUTHEAST.getOpposite());
		assertEquals(CompassDirection.NORTHEAST, CompassDirection.SOUTHWEST.getOpposite());
		assertEquals(CompassDirection.SOUTHEAST, CompassDirection.NORTHWEST.getOpposite());
	}

	@RepeatedTest(50)
	public void testRandomDirections() {
		assertTrue(CompassDirection.NORTH.getRandomDirection().ordinal()>=0);
		assertTrue(CompassDirection.SOUTH.getRandomDirection().ordinal()<=7);
		assertTrue(CompassDirection.EAST.getRandomDirection().ordinal()>=0);
		assertTrue(CompassDirection.WEST.getRandomDirection().ordinal()<=7);
	}
}

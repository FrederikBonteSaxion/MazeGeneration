package nl.saxion.mazes.compass;

import nl.saxion.mazes.Character;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestTinyMaze {
	private TestMaze maze;
	private Character<CompassDirection> pc;

	@BeforeEach
	public void createTwoRoomMaze() {
		maze = new TestMaze(1,2);
		maze.printMaze();
		assertNotNull(maze);
		pc = new Character<>();
		assertNotNull(pc);
		maze.enter(pc);
		// Ensure character is in the left room.
		pc.setDirection(CompassDirection.NORTH);
		pc.moveForward();
		pc.turnBack();
		// Check that PC is facing east again.
		assertEquals(CompassDirection.SOUTH, pc.getDirection());
		// And can move forward.
		assertTrue(pc.canMoveForward());
	}

	@Test
	public void testMazeStructureIsCorrect() {
		assertNotNull(maze.get(0,0));
		assertNotNull(maze.get(0,1));
		assertEquals(0b00010000, (byte)maze.get(0,0));
		assertEquals(0b00000001, (byte)maze.get(0,1));
	}

	@Test
	public void testCharacterCanSeeWallsAndTurn() {
		for (int i = 0; i < 7; i++) {
			pc.turnRight();
			assertFalse(pc.canMoveForward());
			assertFalse(pc.isAtFinish());
		}
		pc.turnRight();
		// Check that PC is facing south again.
		assertEquals(CompassDirection.SOUTH, pc.getDirection());
		// And can move forward.
		assertTrue(pc.canMoveForward());
		assertFalse(pc.isAtFinish());
		for (int i = 0; i < 7; i++) {
			pc.turnLeft();
			assertFalse(pc.canMoveForward());
			assertFalse(pc.isAtFinish());
		}
		pc.turnLeft();
		// Check that PC is facing south again.
		assertEquals(CompassDirection.SOUTH, pc.getDirection());
		// And can move forward.
		assertTrue(pc.canMoveForward());
		assertFalse(pc.isAtFinish());
	}

	@Test
	public void testWhenMovedForwardCanOnlyMoveBackNorth() {
		pc.moveForward();
		assertFalse(pc.canMoveForward());
		pc.turnBack();
		assertEquals(CompassDirection.NORTH, pc.getDirection());
		for (int i = 0; i < 7; i++) {
			pc.turnLeft();
			assertFalse(pc.canMoveForward());
			assertTrue(pc.isAtFinish());
		}
		pc.turnLeft();
		// Check that PC is facing north again.
		assertEquals(CompassDirection.NORTH, pc.getDirection());
		// And can move forward.
		assertTrue(pc.canMoveForward());
		assertTrue(pc.isAtFinish());
		for (int i = 0; i < 7; i++) {
			pc.turnRight();
			assertFalse(pc.canMoveForward());
			assertTrue(pc.isAtFinish());
		}
		pc.turnRight();
		// Check that PC is facing north again.
		assertEquals(CompassDirection.NORTH, pc.getDirection());
		// And can move forward.
		assertTrue(pc.canMoveForward());
		assertTrue(pc.isAtFinish());
	}


	@Test
	public void testWhenCharacterMovesForwardEndOfMazeIsReached() {
		pc.moveForward();
		assertFalse(pc.canMoveForward());
		assertTrue(pc.isAtFinish());
		pc.turnBack();
		// Check that PC is facing north again.
		assertEquals(CompassDirection.NORTH, pc.getDirection());
		// And can move forward.
		assertTrue(pc.canMoveForward());
	}

	public class TestMaze extends CompassMaze {
		public TestMaze(int sizeX, int sizeY) {
			super(sizeX, sizeY);
		}


		@Override
		public Byte get(int x, int y) {
			return super.get(x, y);
		}
	}
}

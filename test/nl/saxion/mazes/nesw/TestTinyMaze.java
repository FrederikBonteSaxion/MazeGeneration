package nl.saxion.mazes.nesw;

import nl.saxion.mazes.Character;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestTinyMaze {
	private TestMaze maze;
	private Character<NESWDirection> pc;

	@BeforeEach
	public void createTwoRoomMaze() {
		maze = new TestMaze(2,1);
		assertNotNull(maze);
		maze.printMaze();
		pc = new Character<>();
		assertNotNull(pc);
		maze.enter(pc);
		// Ensure character is in the left room.
		pc.setDirection(NESWDirection.WEST);
		pc.moveForward();
		pc.turnBack();
		// Check that PC is facing east again.
		assertEquals(NESWDirection.EAST, pc.getDirection());
		// And can move forward.
		assertTrue(pc.canMoveForward());
	}

	@Test
	public void testMazeStructureIsCorrect() {
		assertNotNull(maze.get(0,0));
		assertNotNull(maze.get(1,0));
		assertEquals(0b0010, (byte)maze.get(0,0));
		assertEquals(0b1000, (byte)maze.get(1,0));
	}

	@Test
	public void testCharacterCanSeeWallsAndTurn() {
		for (int i = 0; i < 3; i++) {
			pc.turnRight();
			assertFalse(pc.canMoveForward());
			assertFalse(pc.isAtFinish());
		}
		pc.turnRight();
		// Check that PC is facing east again.
		assertEquals(NESWDirection.EAST, pc.getDirection());
		// And can move forward.
		assertTrue(pc.canMoveForward());
		assertFalse(pc.isAtFinish());
		for (int i = 0; i < 3; i++) {
			pc.turnLeft();
			assertFalse(pc.canMoveForward());
			assertFalse(pc.isAtFinish());
		}
		pc.turnLeft();
		// Check that PC is facing east again.
		assertEquals(NESWDirection.EAST, pc.getDirection());
		// And can move forward.
		assertTrue(pc.canMoveForward());
		assertFalse(pc.isAtFinish());
	}

	@Test
	public void testWhenMovedForwardCanOnlyMoveBackWest() {
		pc.moveForward();
		assertFalse(pc.canMoveForward());
		pc.turnBack();
		assertEquals(NESWDirection.WEST, pc.getDirection());
		for (int i = 0; i < 3; i++) {
			pc.turnLeft();
			assertFalse(pc.canMoveForward());
			assertTrue(pc.isAtFinish());
		}
		pc.turnLeft();
		// Check that PC is facing west again.
		assertEquals(NESWDirection.WEST, pc.getDirection());
		// And can move forward.
		assertTrue(pc.canMoveForward());
		assertTrue(pc.isAtFinish());
		for (int i = 0; i < 3; i++) {
			pc.turnRight();
			assertFalse(pc.canMoveForward());
			assertTrue(pc.isAtFinish());
		}
		pc.turnRight();
		// Check that PC is facing west again.
		assertEquals(NESWDirection.WEST, pc.getDirection());
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
		// Check that PC is facing west again.
		assertEquals(NESWDirection.WEST, pc.getDirection());
		// And can move forward.
		assertTrue(pc.canMoveForward());
	}

	public class TestMaze extends NESWMaze {
		public TestMaze(int sizeX, int sizeY) {
			super(sizeX, sizeY);
		}


		@Override
		public Byte get(int x, int y) {
			return super.get(x, y);
		}
	}
}

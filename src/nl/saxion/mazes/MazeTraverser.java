package nl.saxion.mazes;

/**
 * This interface defines the functionality of traversing a {@link Maze}.
 * @param <D> the type of {@link Direction}s allowed by the {@link Maze}.
 */
public interface MazeTraverser<D extends Direction<D>> {
	/**
	 * Returns <i>true</i> when there is a path in the current (forward) direction.
	 * @return <i>false</i> when you are facing a wall.
	 */
	boolean canMoveForward();

	/**
	 * Move the player to the adjacent room. When the {@link #canMoveForward()} function
	 * would return <i>false</i> this silently fails.
	 */
	void moveForward();

	/**
	 * Turn to the players left by one "step", for instance from east to north.
	 */
	void turnLeft();

	/**
	 * Turn to the players right by one "step", for instance from north to east.
	 */
	void turnRight();

	/**
	 * Turn the player around, facing the opposite {@link Direction}.
	 */
	void turnBack();

	/**
	 * Turn the player to the provided {@link Direction}.
	 * @param direction
	 */
	void setDirection(D direction);

	/**
	 * Returns the {@link Direction} the player is facing.
	 * @return the {@link Direction} the player is facing.
	 */
	D getDirection();

	/**
	 * Returns <i>true</i> when the player has reached the "exit" of the {@link Maze}.
	 * @return <i>false</i> anywhere else in the {@link Maze}
	 */
	boolean isAtFinish();
}

package nl.saxion.mazes;

/**
 * This interface defines the ways in which a player may interact with their orientation.
 * @param <T> the actual {@link Direction} implementation used.
 */
public interface Direction<T extends Direction> {
	/**
	 * Returns the {@link Direction} to the left of this one.
	 * @return the {@link Direction} to the left of this one.
	 */
	T getLeft();
	/**
	 * Returns the {@link Direction} to the right of this one.
	 * @return the {@link Direction} to the right of this one.
	 */
	T getRight();
	/**
	 * Returns the {@link Direction} that is the opposite of thisone.
	 * @return the {@link Direction} that is the opposite of this one.
	 */
	T getOpposite();

	/**
	 * Returns a random {@link Direction}.
 	 * @return a random {@link Direction}.
	 */
	T getRandomDirection();
}

package nl.saxion.mazes;

/**
 * This class store the posible locations and the connections between them.
 * The defining factor is the type of {@link Direction} that is used.
 * @param <D> the type of {@link Direction} that defines the connections between locations.
 */
public interface Maze<D extends Direction<D>> {
	/**
	 * Links the {@link java.lang.Character} and the {@link Maze} together. Afterward you can navigate using the
	 * provided {@link java.lang.Character}.
	 * @param character the {@link java.lang.Character} that enters the {@link Maze}.
	 */
	void enter(Character<D> character);
}

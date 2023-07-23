package nl.saxion.mazes;

/**
 * {@link Character}s are the users access to navigating a maze, however the {@link Character} class is
 * completely separated from the {@link Maze} implementation.
 * @param <D> the {@link Direction} type used to navigate the maze. For instance "North, East, South, West".
 */
public class Character<D extends Direction<D>> implements MazeTraverser<D> {
	private MazeTraverser<D> traverser;
	public void setMazeTraverser(MazeTraverser<D> traverser) {
		this.traverser = traverser;
	}

	@Override
	public boolean canMoveForward() {
		return traverser.canMoveForward();
	}

	@Override
	public void moveForward() {
		traverser.moveForward();
	}

	@Override
	public void turnLeft() {
		traverser.turnLeft();
	}

	@Override
	public void turnRight() {
		traverser.turnRight();
	}

	@Override
	public void turnBack() {
		traverser.turnBack();
	}

	@Override
	public void setDirection(D direction) {
		traverser.setDirection(direction);
	}

	@Override
	public D getDirection() {
		return traverser.getDirection();
	}

	@Override
	public boolean isAtFinish() {
		return traverser.isAtFinish();
	}
}

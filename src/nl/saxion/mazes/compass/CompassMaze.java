package nl.saxion.mazes.compass;

import nl.saxion.mazes.Character;
import nl.saxion.mazes.Maze;
import nl.saxion.mazes.MazeTraverser;
import nl.saxion.mazes.TraverserList;
import nl.saxion.mazes.gui.Renderer;
import nl.saxion.mazes.gui.RendererFactory;
import nl.saxion.mazes.utils.Array2D;
import nl.saxion.mazes.utils.Tuple;

import java.awt.*;
import java.io.PrintStream;
import java.util.Random;
import java.util.Stack;

import static nl.saxion.mazes.utils.Round.round;

public class CompassMaze extends Array2D<Byte> implements Maze<CompassDirection>, RendererFactory {
	private final byte[] MASKS = {
			0b00000001,
			0b00000010,
			0b00000100,
			0b00001000,
			0b00010000,
			0b00100000,
			0b01000000,
			(byte) 0b10000000
	};
	private static final Random RNG = new Random();
	private final TraverserList<CompassDirection, CompassTraverser> traversers;

	public CompassMaze(int sizeX, int sizeY) {
		super(Byte.class, sizeX, sizeY);
		this.traversers = new TraverserList<CompassDirection, CompassTraverser>(CompassTraverser::new);
		clear();
		generate();
	}

	private void clear() {
		for (int y = 0; y < getSizeY(); y++) {
			for (int x = 0; x < getSizeX(); x++) {
				set(x, y, (byte) 0);
			}
		}
	}

	private void generate() {
		Stack<Tuple<Integer, Integer>> path = new Stack<>();
		Tuple<Integer, Integer> start = new Tuple<>(RNG.nextInt(getSizeX()), RNG.nextInt(getSizeY()));
		path.push(start);
		while (!path.isEmpty()) {
			Tuple<Integer, Integer> current = path.peek();
			if (isLockedIn(current.getLeft(), current.getRight())) {
//				System.out.println(current+" is locked in...");
				path.pop();
			}
			else {
				CompassDirection direction = CompassDirection.NORTH.getRandomDirection();
				while (isVisited(current.getLeft(), current.getRight(), direction) || isDiagonallyBlocked(current.getLeft(), current.getRight(), direction)) {
					direction = direction.getLeft();
				}
				path.push(connect(current.getLeft(), current.getRight(), direction));
//				System.out.println("Moved "+direction+" to "+current);
			}
		}
	}

	private boolean isDiagonallyBlocked(int x, int y, CompassDirection direction) {
		switch (direction) {
			case NORTH : return false;
			case NORTHEAST: return canMove(x+1,y,CompassDirection.NORTHWEST);
			case EAST : return false;
			case SOUTHEAST: return canMove(x+1,y,CompassDirection.SOUTHWEST);
			case SOUTH : return false;
			case SOUTHWEST: return canMove(x-1,y,CompassDirection.SOUTHEAST);
			case WEST : return false;
			case NORTHWEST: return canMove(x-1,y,CompassDirection.NORTHEAST);
			default : throw new UnsupportedOperationException("Cannot handle unknown direction "+direction);
		}
	}

	private Tuple<Integer, Integer> connect(int x, int y, CompassDirection direction) {
		Tuple<Integer, Integer> next = getNext(x, y, direction);
		set(x, y, (byte) (get(x, y) | MASKS[direction.ordinal()]));
		byte visited = MASKS[direction.getOpposite().ordinal()];
		set(next.getLeft(), next.getRight(), visited);
		return next;
	}

	private Tuple<Integer, Integer> getNext(int x, int y, CompassDirection direction) {
		switch (direction) {
			case NORTH: {
				return new Tuple<>(x, y - 1);
			}
			case EAST: {
				return new Tuple<>(x + 1, y);
			}
			case SOUTH: {
				return new Tuple<>(x, y + 1);
			}
			case WEST: {
				return new Tuple<>(x - 1, y);
			}
			case NORTHEAST: {
				return new Tuple<>(x + 1, y - 1);
			}
			case SOUTHEAST: {
				return new Tuple<>(x + 1, y + 1);
			}
			case SOUTHWEST: {
				return new Tuple<>(x - 1, y + 1);
			}
			case NORTHWEST: {
				return new Tuple<>(x - 1, y - 1);
			}
			default: {
				throw new UnsupportedOperationException("Unknown direction : " + direction);
			}
		}
	}

	public boolean canMove(int x, int y, CompassDirection direction) {
		byte b = get(x,y);
		return (b & MASKS[direction.ordinal()]) != 0;
	}

	private boolean isVisited(int x, int y, CompassDirection direction) {
		Byte b = get(x, y, direction);
		if (b == null) {
			return true; // Because you don't want to connect beyond the range of the maze.
		}
		else {
			return isVisited(b);
		}
	}

	private boolean isVisited(byte value) {
		return value != 0;
	}

	private Byte get(int x, int y, CompassDirection direction) {
		Tuple<Integer, Integer> next = getNext(x, y, direction);
		return get(next.getLeft(), next.getRight());
	}

	private boolean isLockedIn(int x, int y) {
		for (CompassDirection d : CompassDirection.values()) {
			if (!isVisited(x, y, d) && !isDiagonallyBlocked(x,y,d)) {
				return false;
			}
		}
		return true;
	}

	public void printMaze() {
		printMaze(System.out);
	}

	public void printMaze(PrintStream out) {
		out.println(" Maze " + getSizeX() + " x " + getSizeY());
		out.print("+");
		for (int i = 0; i < getSizeX(); i++) {
			out.print("--+");
		}
		out.println();
		for (int y = 0; y < getSizeY(); y++) {
			out.print("|");
			for (int x = 0; x < getSizeX(); x++) {
				if (canMove(x, y, CompassDirection.EAST)) {
					out.print("   ");
				}
				else {
					out.print("  |");
				}
			}
			out.println();
			out.print("+");
			for (int x = 0; x < getSizeX(); x++) {
				if (canMove(x, y, CompassDirection.SOUTH)) {
					out.print("  +");
				}
				else {
					out.print("--+");
				}
			}
			out.println();
		}
	}

	public Renderer getRenderer() {
		return new MazeRenderer();
	}

	@Override
	public void enter(Character<CompassDirection> character) {
		this.traversers.register(character);
	}

	public class MazeRenderer implements Renderer {

		@Override
		public void render(Graphics2D graphics) {
			Rectangle size = graphics.getClipBounds();
			double width = size.getWidth() / getSizeX();
			double height = size.getHeight() / getSizeY();
			double margin = Math.min(width, height) * 0.05;
			double pathWidth = Math.min(width, height) * 0.15;
			double pathWidthD = Math.min(width, height) * 0.25;

			graphics.clearRect(size.x, size.y, size.width, size.height);

			// Draw all rooms and erase the inside corners.
			for (int y = 0; y < getSizeY(); y++) {
				for (int x = 0; x < getSizeX(); x++) {
					double offsetX = x * width;
					double offsetY = y * height;
					graphics.drawRect(round(offsetX + margin), round(offsetY + margin), round(width - 2 * margin), round(height - 2 * margin));
					if (y > 0) {
						if (x > 0) {
							graphics.clearRect(round(offsetX - pathWidthD) - 2, round(offsetY - pathWidthD) - 2, round(pathWidthD * 2) + 4, round(pathWidthD * 2) + 4);
						}
//						if (x < getSizeX() - 1) {
//							graphics.clearRect(round(offsetX + width - pathWidthD) - 2, round(offsetY - pathWidthD) - 2, round(pathWidthD * 2) + 4, round(pathWidthD * 2) + 4);
//						}
					}
				}
			}

			// Draw the connecting paths.
			for (int y = 0; y < getSizeY(); y++) {
				for (int x = 0; x < getSizeX(); x++) {
					double offsetX = x * width;
					double offsetY = y * height;
					if (canMove(x, y, CompassDirection.NORTH)) {
						graphics.clearRect(round(offsetX + width / 2 - pathWidth), round(offsetY - margin) - 2, round(pathWidth * 2), round(margin * 2 + 4));
						graphics.drawLine(round(offsetX + width / 2 - pathWidth), round(offsetY + margin) + 2, round(offsetX + width / 2 - pathWidth), round(offsetY - margin) - 2);
						graphics.drawLine(round(offsetX + width / 2 + pathWidth), round(offsetY + margin) + 2, round(offsetX + width / 2 + pathWidth), round(offsetY - margin) - 2);
					}
					if (canMove(x, y, CompassDirection.NORTHWEST)) {
						graphics.drawLine(round(offsetX - pathWidthD) - 4, round(offsetY - margin - 2), round(offsetX + margin) + 2, round(offsetY + pathWidthD + 4));
						graphics.drawLine(round(offsetX - margin) - 2, round(offsetY - pathWidthD - 4), round(offsetX + pathWidthD) + 4, round(offsetY + margin + 2));
					}
					if (canMove(x, y, CompassDirection.WEST)) {
						graphics.clearRect(round(offsetX - margin) - 2, round(offsetY + height / 2 - pathWidth), round(margin * 2 + 4), round(pathWidth * 2));
						graphics.drawLine(round(offsetX - margin) - 2, round(offsetY + height / 2 - pathWidth), round(offsetX + margin) + 2, round(offsetY + height / 2 - pathWidth));
						graphics.drawLine(round(offsetX - margin) - 2, round(offsetY + height / 2 + pathWidth), round(offsetX + margin) + 2, round(offsetY + height / 2 + pathWidth));
					}
					if (canMove(x, y, CompassDirection.NORTHEAST)) {
						graphics.drawLine(round(offsetX + width - pathWidthD) - 4, round(offsetY + margin + 2), round(offsetX + width + margin) + 2, round(offsetY - pathWidthD - 4));
						graphics.drawLine(round(offsetX + width - margin) - 2, round(offsetY + pathWidthD + 4), round(offsetX + width + pathWidthD) + 4, round(offsetY - margin - 2));
					}
				}
			}

			// Reconstruct remaining missing corners.
			for (int y = 0; y < getSizeY(); y++) {
				for (int x = 0; x < getSizeX(); x++) {
					double offsetX = x * width;
					double offsetY = y * height;
					if (x<getSizeX()-1 && y<getSizeY()-1 && !canMove(x,y,CompassDirection.SOUTHEAST)) {
						graphics.drawLine(round(offsetX+width-pathWidthD)-2, round(offsetY+height-margin), round(offsetX+width-margin), round(offsetY+height-pathWidthD)-2);
					}
					if (x>0 && y<getSizeY()-1 && !canMove(x,y,CompassDirection.SOUTHWEST)) {
						graphics.drawLine(round(offsetX+margin), round(offsetY+height-pathWidthD)-2, round(offsetX+pathWidthD)+2, round(offsetY+height-margin));
					}
					if (x<getSizeX()-1 && y>0 && !canMove(x,y,CompassDirection.NORTHEAST)) {
						graphics.drawLine(round(offsetX+width-pathWidthD)-2, round(offsetY+margin), round(offsetX+width-margin), round(offsetY+pathWidthD)+2);
					}
					if (x>0 && y>0 && !canMove(x,y,CompassDirection.NORTHWEST)) {
						graphics.drawLine(round(offsetX+margin), round(offsetY+pathWidthD)+2, round(offsetX+pathWidthD)+2, round(offsetY+margin));
					}
				}
			}

			drawAllCharacters(graphics, width, height);
		}

		private void drawAllCharacters(Graphics2D graphics, double width, double height) {
			for (CompassTraverser traverser:traversers) {
				drawCharacter(graphics, width, height, traverser);
			}
		}

		private void drawCharacter(Graphics2D graphics, double width, double height, CompassTraverser traverser) {
			graphics.setColor(Color.BLACK);
			double x = width*(traverser.x+0.5);
			double y = height*(traverser.y+0.5);
			int size = round(Math.min(width,height)*0.5)/2;
			graphics.fillOval(round(x-size+1),round(y-size+1),size*2,size*2);
			graphics.setColor(Color.RED);
			graphics.fillOval(round(x-size),round(y-size),size*2,size*2);
			int angle = traverser.direction.ordinal()*45;
			drawAngle(graphics, x, y, angle, size*2);
		}

		private void drawAngle(Graphics2D graphics, double x, double y, int angle, int length) {
			int dx = round(x+length *Math.sin(angle*Math.PI/180));
			int dy = round(y-length * Math.cos(angle*Math.PI/180));
			graphics.drawLine(round(x), round(y),dx,dy);
		}
	}

	private class CompassTraverser implements MazeTraverser<CompassDirection> {
		private int x, y;
		private CompassDirection direction;

		private CompassTraverser() {
			this.x = RNG.nextInt(getSizeX());
			this.y = RNG.nextInt(getSizeY());
			this.direction = CompassDirection.SOUTHEAST.getRandomDirection();
		}

		@Override
		public boolean canMoveForward() {
			return canMove(x,y,direction);
		}

		@Override
		public void moveForward() {
			if (canMoveForward()) {
				Tuple<Integer, Integer> targetPosition = getNext(x, y, direction);
				this.x = targetPosition.getLeft();
				this.y = targetPosition.getRight();
			}
		}

		@Override
		public void turnLeft() {
			setDirection(direction.getLeft());
		}

		@Override
		public void turnRight() {
			setDirection(direction.getRight());
		}

		@Override
		public void turnBack() {
			setDirection(direction.getOpposite());
		}

		@Override
		public void setDirection(CompassDirection direction) {
			this.direction = direction;
		}

		@Override
		public CompassDirection getDirection() {
			return direction;
		}

		@Override
		public boolean isAtFinish() {
			return this.x+1==getSizeX() && this.y+1==getSizeY();
		}
	}
}

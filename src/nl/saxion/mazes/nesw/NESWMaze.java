package nl.saxion.mazes.nesw;

import nl.saxion.mazes.*;
import nl.saxion.mazes.Character;
import nl.saxion.mazes.gui.Renderer;
import nl.saxion.mazes.gui.RendererFactory;
import nl.saxion.mazes.utils.Array2D;
import nl.saxion.mazes.utils.Tuple;

import java.awt.*;
import java.io.PrintStream;
import java.util.Random;
import java.util.Stack;

import static nl.saxion.mazes.utils.Round.round;

public class NESWMaze extends Array2D<Byte> implements Maze<NESWDirection>, RendererFactory {
	private final byte[] MASKS = {
			0b0001,
			0b0010,
			0b0100,
			0b1000
	};
	private static final Random RNG = new Random();

	private final TraverserList<NESWDirection, NESWTraverser> traversers;

	public NESWMaze(int sizeX, int sizeY) {
		super(Byte.class, sizeX, sizeY);
		this.traversers = new TraverserList<>(NESWTraverser::new);
		clear();
		generate();
	}

	private void clear() {
		for (int y = 0; y < getSizeY(); y++) {
			for (int x = 0; x < getSizeX(); x++) {
				set(x,y,(byte)0);
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
				System.out.println(current+" is locked in...");
				path.pop();
			}
			else {
				NESWDirection direction = NESWDirection.NORTH.getRandomDirection();
				while (isVisited(current.getLeft(), current.getRight(), direction)) {
					direction = direction.getLeft();
				}
				path.push(connect(current.getLeft(), current.getRight(), direction));
				System.out.println("Moved "+direction+" from "+current+" to "+path.peek());
			}
		}
	}

	private Tuple<Integer, Integer> connect(int x, int y, NESWDirection direction) {
		Tuple<Integer, Integer> next = getNext(x,y,direction);
		set(x,y, (byte)(get(x,y) | MASKS[direction.ordinal()]));
		byte visited = MASKS[direction.getOpposite().ordinal()];
		set(next.getLeft(), next.getRight(), visited);
		return next;
	}

	private Tuple<Integer, Integer> getNext(int x, int y, NESWDirection direction) {
		switch (direction) {
			case NORTH : { return new Tuple<>(x,y-1); }
			case EAST : { return new Tuple<>(x+1,y); }
			case SOUTH : { return new Tuple<>(x,y+1); }
			case WEST : { return new Tuple<>(x-1,y); }
			default: {
				throw new UnsupportedOperationException("Unknown direction : "+direction);
			}
		}
	}

	private boolean isVisited(int x, int y, NESWDirection direction) {
		Byte b = get(x, y, direction);
		if (b==null) {
			return true; // Because you don't want to connect beyond the range of the maze.
		} else {
			return isVisited(b);
		}
	}

	private boolean isVisited(byte value) {
		return value!=0;
	}

	private Byte get(int x, int y, NESWDirection direction) {
		Tuple<Integer, Integer> next = getNext(x,y,direction);
		return get(next.getLeft(), next.getRight());
	}

	private boolean canMove(int x, int y, NESWDirection direction) {
		byte b = get(x, y);
		return canMove(b, direction);
	}

	private boolean canMove(byte value, NESWDirection direction) {
		return (value&MASKS[direction.ordinal()])!=0;
	}

	private boolean isLockedIn(int x, int y) {
		for (NESWDirection d:NESWDirection.values()) {
			if (!isVisited(x,y,d)) {
				return false;
			}
		}
		return true;
	}

	public void printMaze() {
		printMaze(System.out);
	}

	public void printMaze(PrintStream out) {
		out.println(" Maze "+getSizeX()+" x "+getSizeY());
		out.print("+");
		for (int i = 0; i < getSizeX(); i++) {
			out.print("--+");
		}
		out.println();
		for (int y = 0; y < getSizeY(); y++) {
			out.print("|");
			for (int x = 0; x < getSizeX(); x++) {
				if (canMove(x,y,NESWDirection.EAST)) {
					out.print("   ");
				} else {
					out.print("  |");
				}
			}
			out.println();
			out.print("+");
			for (int x = 0; x < getSizeX(); x++) {
				if (canMove(x,y, NESWDirection.SOUTH)) {
					out.print("  +");
				} else {
					out.print("--+");
				}
			}
			out.println();
		}
	}

	@Override
	public void enter(Character<NESWDirection> character) {
		traversers.register(character);
	}

	@Override
	public Renderer getRenderer() {
		return new MazeRenderer();
	}

	public class MazeRenderer implements Renderer {

		@Override
		public void render(Graphics2D graphics) {
			Rectangle size = graphics.getClipBounds();
			double width = size.getWidth() / getSizeX();
			double height = size.getHeight() / getSizeY();
			double margin = Math.min(width, height) * 0.03;
			double pathWidth = Math.min(width, height) * 0.15;

			graphics.clearRect(size.x, size.y, size.width, size.height);

			// Draw all rooms and erase the inside corners.
			for (int y = 0; y < getSizeY(); y++) {
				for (int x = 0; x < getSizeX(); x++) {
					double offsetX = x * width;
					double offsetY = y * height;
					graphics.drawRect(round(offsetX + margin), round(offsetY + margin), round(width - 2 * margin), round(height - 2 * margin));
				}
			}

			// Draw the connecting paths.
			for (int y = 0; y < getSizeY(); y++) {
				for (int x = 0; x < getSizeX(); x++) {
					double offsetX = x * width;
					double offsetY = y * height;
					if (canMove(x, y, NESWDirection.NORTH)) {
						graphics.clearRect(round(offsetX + width / 2 - pathWidth), round(offsetY - margin) - 2, round(pathWidth * 2), round(margin * 2 + 4));
						graphics.drawLine(round(offsetX + width / 2 - pathWidth), round(offsetY + margin) + 2, round(offsetX + width / 2 - pathWidth), round(offsetY - margin) - 2);
						graphics.drawLine(round(offsetX + width / 2 + pathWidth), round(offsetY + margin) + 2, round(offsetX + width / 2 + pathWidth), round(offsetY - margin) - 2);
					}
					if (canMove(x, y, NESWDirection.WEST)) {
						graphics.clearRect(round(offsetX - margin) - 2, round(offsetY + height / 2 - pathWidth), round(margin * 2 + 4), round(pathWidth * 2));
						graphics.drawLine(round(offsetX - margin) - 2, round(offsetY + height / 2 - pathWidth), round(offsetX + margin) + 2, round(offsetY + height / 2 - pathWidth));
						graphics.drawLine(round(offsetX - margin) - 2, round(offsetY + height / 2 + pathWidth), round(offsetX + margin) + 2, round(offsetY + height / 2 + pathWidth));
					}
				}
			}

			drawAllCharacters(graphics, width, height);
		}

		private void drawAllCharacters(Graphics2D graphics, double width, double height) {
			for (NESWMaze.NESWTraverser traverser:traversers) {
				drawCharacter(graphics, width, height, traverser);
			}
		}

		private void drawCharacter(Graphics2D graphics, double width, double height, NESWTraverser traverser) {
			graphics.setColor(Color.BLACK);
			double x = width*(traverser.x+0.5);
			double y = height*(traverser.y+0.5);
			int size = round(Math.min(width,height)*0.5)/2;
			graphics.fillOval(round(x-size+1),round(y-size+1),size*2,size*2);
			graphics.setColor(Color.RED);
			graphics.fillOval(round(x-size),round(y-size),size*2,size*2);
			int angle = traverser.direction.ordinal()*90;
			drawAngle(graphics, x, y, angle, size*2);
		}

		private void drawAngle(Graphics2D graphics, double x, double y, int angle, int length) {
			int dx = round(x+length *Math.sin(angle*Math.PI/180));
			int dy = round(y-length * Math.cos(angle*Math.PI/180));
			graphics.drawLine(round(x), round(y),dx,dy);
		}
	}

	private class NESWTraverser implements MazeTraverser<NESWDirection> {
		private int x, y;
		private NESWDirection direction;

		private NESWTraverser() {
			this.x = RNG.nextInt(getSizeX());
			this.y = RNG.nextInt(getSizeY());
			this.direction = NESWDirection.EAST.getRandomDirection();
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
		public void setDirection(NESWDirection direction) {
			this.direction = direction;
		}

		@Override
		public NESWDirection getDirection() {
			return direction;
		}

		@Override
		public boolean isAtFinish() {
			return this.x+1==getSizeX() && this.y+1==getSizeY();
		}
	}

}

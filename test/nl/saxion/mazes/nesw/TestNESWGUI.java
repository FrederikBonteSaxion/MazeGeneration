package nl.saxion.mazes.nesw;

import nl.saxion.mazes.Character;
import nl.saxion.mazes.gui.JRendererPanel;
import org.junit.jupiter.api.*;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestNESWGUI {
	private static JRendererPanel panel;

	private static JFrame frame;

	private static NESWMaze maze;
	private static Character<NESWDirection> character;

	@BeforeAll
	public static void createWindow() {
		frame = new JFrame("Maze");
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		maze = new NESWMaze(4,3);
		character = new Character<>();
		maze.enter(character);
		maze.printMaze();
		panel = new JRendererPanel(maze);
		frame.add(panel);
		frame.setVisible(true);
	}

	@RepeatedTest(30)
	public void testCharacterMove() {
		assertNotNull(character);
		if (character.canMoveForward()) {
			character.moveForward();
			character.turnRight();
		} else {
			character.turnLeft();
		}
	}

	@AfterAll
	public static void confirmAllLocationsReached() {
		frame.setVisible(false);
		frame.dispose();
	}
}

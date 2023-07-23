package nl.saxion.mazes.compass;

import nl.saxion.mazes.Character;
import nl.saxion.mazes.gui.JRendererPanel;
import nl.saxion.mazes.nesw.NESWDirection;
import nl.saxion.mazes.nesw.NESWMaze;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestCompassGUI {
	private static JRendererPanel panel;

	private static JFrame frame;

	private static CompassMaze maze;
	private static Character<CompassDirection> character;

	@BeforeAll
	public static void createWindow() {
		frame = new JFrame("Maze");
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		maze = new CompassMaze(8,6);
		character = new Character<>();
		maze.enter(character);
		maze.printMaze();
		panel = new JRendererPanel(maze);
		frame.add(panel);
		frame.setVisible(true);
	}

	@RepeatedTest(300)
	public void testCharacterMove() {
		assertNotNull(character);
		if (character.canMoveForward()) {
			character.moveForward();
			character.turnRight();
			character.turnRight();
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

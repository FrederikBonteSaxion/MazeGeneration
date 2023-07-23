package nl.saxion.test;

import nl.saxion.mazes.Character;
import nl.saxion.mazes.compass.CompassDirection;
import nl.saxion.mazes.compass.CompassMaze;
import nl.saxion.mazes.gui.JRendererPanel;
import nl.saxion.mazes.nesw.NESWDirection;
import nl.saxion.mazes.nesw.NESWMaze;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class Main {
	private static final ArrayList<Character<?>> list = new ArrayList<>();
	private static JRendererPanel panel;

	private static Timer timer;
	private static JFrame frame;

	public static void main(String[] args) {
		frame = new JFrame("Maze");
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//frame.addWindowStateListener(Main::stateListener);
		CompassMaze maze = new CompassMaze(60,45);
		//maze.printMaze();
		for (int i = 0; i < 10; i++) {
			Character<CompassDirection> c = new Character<>();
			maze.enter(c);
			list.add(c);
		}
		panel = new JRendererPanel(maze);
		frame.add(panel);
		timer = new Timer(100,
				Main::update
		);
		timer.start();
		frame.setVisible(true);
	}

	private static void update(ActionEvent actionEvent) {
		moveCharacters();
	}

	private static void moveCharacters() {
		for (Character<?> c:list) {
			moveCompassCharacter(c);
		}
	}

	private static void moveCompassCharacter(Character<?> c) {
		if (!frame.isVisible()) {
			timer.stop();
		}
		if (c.canMoveForward()) {
			c.moveForward();
			c.turnLeft();
			c.turnLeft();
			c.turnLeft();
		} else {
			c.turnRight();
		}
		panel.repaint();
	}

	private static void moveNESWCharacter(Character<?> c) {
		if (!frame.isVisible()) {
			timer.stop();
		}
		if (c.canMoveForward()) {
			c.moveForward();
			c.turnLeft();
		} else {
			c.turnRight();
		}
		panel.repaint();
	}
}

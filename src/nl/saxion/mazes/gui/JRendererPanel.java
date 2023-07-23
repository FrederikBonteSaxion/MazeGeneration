package nl.saxion.mazes.gui;

import javax.swing.*;
import java.awt.*;

public class JRendererPanel extends JPanel {
	private final Renderer renderer;

	public JRendererPanel(Renderer renderer) {
		this.renderer = renderer;
		this.setDoubleBuffered(true);
	}

	public JRendererPanel(RendererFactory factory) {
		this(factory.getRenderer());
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g.create();
		renderer.render(g2d);
		g2d.dispose();
	}

}

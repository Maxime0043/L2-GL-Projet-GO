package gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import donnees.ParametrePartie;

public class GoPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	public GoPanel() {
		
		
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.white);

		drawDebugGrid(g);
	}

	private void drawDebugGrid(Graphics g) {
		int width = getWidth();
		int height = getHeight();
		g.setColor(Color.GRAY);

		for (int i = ParametrePartie.LARGEUR; i <= width; i += ParametrePartie.LARGEUR) {
			g.drawLine(i, 1, i, height);
		}

		for (int i = ParametrePartie.LARGEUR; i <= height; i += ParametrePartie.LARGEUR) {
			g.drawLine(1, i, width, i);
		}
	}

}

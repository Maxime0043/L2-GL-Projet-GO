package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import traitement.Moteur;

public class GoPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	Moteur moteur;
	
	public GoPanel() {
		moteur = new Moteur();
		
		this.addMouseListener(new Souris());
		
		this.setBackground(Color.decode("#F2B352"));
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		moteur.paint(g);
	}
	
	
	private class Souris implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			moteur.clicEvent(e);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			
		}

	}

}

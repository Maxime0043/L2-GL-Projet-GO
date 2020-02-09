package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import donnees.ParametrePartie;

public class GoPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	int choix = 0;
	
	int cellule = ParametrePartie.LARGEUR_CASE;
	int ecart_window = ParametrePartie.ECART;
	int taille_goban = ParametrePartie.TAILLE_GOBAN[choix];
	
	
	
	public GoPanel() {
		this.addMouseListener(new Souris());
	}
	
	public int getChoix() {
		return choix;
	}
	
	public void setChoix(int valeur) {
		choix = valeur;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.white);
		
		drawGrid(g);
	}
	
	private void drawGrid(Graphics g){
		g.setColor(Color.BLACK);
		
		for(int i = 0 ; i < taille_goban ; i++){
			g.drawLine(ecart_window, ecart_window + i * cellule, ecart_window + (taille_goban - 1) * cellule, ecart_window + i * cellule);
			g.drawLine(ecart_window + i * cellule, ecart_window, ecart_window +i * cellule, ecart_window + (taille_goban - 1) * cellule);
		}
	}
	
	class Souris implements MouseListener{

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
			//On écrira le code ici
			System.out.println("X : " + e.getX());
			System.out.println("Y : " + e.getY());
			
			int x = (e.getX() - ecart_window / 2) / cellule;
			int y = (e.getY() - ecart_window / 2) / cellule;
			
			System.out.println("\nTabX : " + x);
			System.out.println("\nTabY : " + y + "\n");
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			
		}

	}

}

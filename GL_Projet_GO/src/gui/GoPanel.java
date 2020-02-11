package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import donnees.Cercle;
import donnees.Couleur;
import donnees.ParametrePartie;
import traitement.Moteur;

public class GoPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	Moteur moteur;
	
	int cellule;
	int ecart_window;
	int taille_goban;
	
	public GoPanel() {
		moteur = new Moteur();
		
		cellule = ParametrePartie.LARGEUR_CASE;
		ecart_window = ParametrePartie.ECART;
		taille_goban = ParametrePartie.TAILLE_GOBAN[moteur.getChoix()];
		
		this.addMouseListener(new Souris());
		
		this.setBackground(Color.decode("#F2B352"));
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		drawGrid(g);
		drawCercle(g);
		drawCouleurJoueur(g);
	}
	
	private void drawGrid(Graphics g){
		g.setColor(Color.BLACK);
		
		for(int i = 0 ; i < taille_goban ; i++){
			g.drawLine(ecart_window, ecart_window + i * cellule, ecart_window + (taille_goban - 1) * cellule, ecart_window + i * cellule);
			g.drawLine(ecart_window + i * cellule, ecart_window, ecart_window +i * cellule, ecart_window + (taille_goban - 1) * cellule);
		}
	}
	
	private void drawCercle(Graphics g) {
		int x, y;
		
		for(Cercle c : moteur.getCercleList()) {
			x = c.getY() * cellule + ecart_window / 2 - ParametrePartie.ERREUR_NON_CONTROLEE;
			y = c.getX() * cellule + ecart_window / 2 - ParametrePartie.ERREUR_NON_CONTROLEE;
			
			setColor(g, c.getCouleur());
			
			g.fillOval(x, y, ParametrePartie.TAILLE_CERCLE, ParametrePartie.TAILLE_CERCLE);
		}
	}
	
	private void drawCouleurJoueur(Graphics g) {
		if(moteur.getNoir()) {
			g.setColor(Color.BLACK);
		}
		else if(moteur.getBlanc()) {
			g.setColor(Color.WHITE);
		}
		else if(moteur.getRouge()){
			g.setColor(Color.RED);
		}
		
		int x = ecart_window;
		int y = taille_goban * cellule + 15;
		
		g.fillOval(x, y, 30, 30);
	}
	
	private void setColor(Graphics g, Couleur couleur) {
		if(couleur.equals(Couleur.NOIR)) {
			g.setColor(Color.BLACK);
		}
		
		else if(couleur.equals(Couleur.BLANC)) {
			g.setColor(Color.WHITE);
		}
		
		else if(couleur.equals(Couleur.ROUGE)) {
			g.setColor(Color.RED);
		}
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

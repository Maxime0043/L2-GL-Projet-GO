package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import donnees.Coordonnee;
import donnees.ParametrePartie;

public class GoPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	int choix = 0;
	
	int cellule = ParametrePartie.LARGEUR_CASE;
	int ecart_window = ParametrePartie.ECART;
	int taille_goban = ParametrePartie.TAILLE_GOBAN[choix];
	
	ArrayList<Coordonnee> cercle;
	
	public GoPanel() {
		this.addMouseListener(new Souris());
		
		cercle = new ArrayList<Coordonnee>();
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
		
		drawGrid(g);
		drawCercle(g);
	}
	
	private void drawGrid(Graphics g){
		g.setColor(Color.BLACK);
		
		for(int i = 0 ; i < taille_goban ; i++){
			g.drawLine(ecart_window, ecart_window + i * cellule, ecart_window + (taille_goban - 1) * cellule, ecart_window + i * cellule);
			g.drawLine(ecart_window + i * cellule, ecart_window, ecart_window +i * cellule, ecart_window + (taille_goban - 1) * cellule);
		}
	}
	
	private void drawCercle(Graphics g) {
		g.setColor(Color.black);
		
		int x, y;
		
		for(Coordonnee coordCercle : cercle) {
			x = coordCercle.getX() * cellule + ecart_window / 2 - 5;
			y = coordCercle.getY() * cellule + ecart_window / 2 - 5;
			
			g.fillOval(x, y, ParametrePartie.TAILLE_CERCLE, ParametrePartie.TAILLE_CERCLE);
		}
	}
	
	public void addCercle(Coordonnee c) {
		boolean result = true;
		
		for(Coordonnee coordCercle : cercle) {
			if((c.getX() == coordCercle.getX()) && (c.getY() == coordCercle.getY())) {
				result = false;
			}
		}
		
		if(result) {
			cercle.add(c);
			System.out.println("Ajout (" + c.getX() + ", " + c.getY() + ")");
		}
	}
	
	public void removeCercle(Coordonnee c) {
		boolean result = false;
		Coordonnee coord = null;
		
		for(Coordonnee coordCercle : cercle) {
			if((c.getX() == coordCercle.getX()) && (c.getY() == coordCercle.getY())) {
				coord = coordCercle;
				result = true;
			}
		}
		
		if(result) {
			cercle.remove(coord);
		}
	}
	
	public ArrayList<Coordonnee> cercleListe(){
		return cercle;
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
			int x = (e.getX() - ecart_window / 2) / cellule;
			int y = (e.getY() - ecart_window / 2) / cellule;
			
			addCercle(new Coordonnee(x, y));
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			
		}

	}

}

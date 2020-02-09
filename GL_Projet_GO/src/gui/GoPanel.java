package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import donnees.Cercle;
import donnees.Coordonnee;
import donnees.Couleur;
import donnees.ParametrePartie;

public class GoPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	int choix = 0;
	
	int cellule = ParametrePartie.LARGEUR_CASE;
	int ecart_window = ParametrePartie.ECART;
	int taille_goban = ParametrePartie.TAILLE_GOBAN[choix];
	
	private ArrayList<Cercle> cercle;
	
	private boolean noir = true;
	private boolean blanc = false;
	private boolean rouge = false;
	
	public GoPanel() {
		this.addMouseListener(new Souris());
		
		cercle = new ArrayList<Cercle>();
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
		
		for(Cercle c : cercle) {
			x = c.getX() * cellule + ecart_window / 2 - 5;
			y = c.getY() * cellule + ecart_window / 2 - 5;
			
			setColor(g, c.getCouleur());
			
			g.fillOval(x, y, ParametrePartie.TAILLE_CERCLE, ParametrePartie.TAILLE_CERCLE);
		}
	}
	
	private void setColor(Graphics g, String couleur) {
		if(couleur.equals(Couleur.NOIR.getCouleur())) {
			g.setColor(Color.BLACK);
		}
		
		else if(couleur.equals(Couleur.BLANC.getCouleur())) {
			g.setColor(Color.WHITE);
		}
		
		else if(couleur.equals(Couleur.ROUGE.getCouleur())) {
			g.setColor(Color.RED);
		}
	}

	public void addCercle(Cercle c) {
		boolean result = true;
		
		for(Cercle coordCercle : cercle) {
			if((c.getX() == coordCercle.getX()) && (c.getY() == coordCercle.getY())) {
				result = false;
			}
		}
		
		if(result) {
			cercle.add(c);
			System.out.println("Ajout (" + c.getX() + ", " + c.getY() + ")");
		}
	}
	
	public void removeCercle(Cercle c) {
		boolean result = false;
		Cercle coord = null;
		
		for(Cercle coordCercle : cercle) {
			if((c.getX() == coordCercle.getX()) && (c.getY() == coordCercle.getY())) {
				coord = coordCercle;
				result = true;
			}
		}
		
		if(result) {
			cercle.remove(coord);
		}
	}
	
	public ArrayList<Cercle> cercleListe(){
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
			
			if((x >= 0) && (x < taille_goban) && (y >= 0) && (y < taille_goban)) {
				Coordonnee c = new Coordonnee(x, y);
				
				if(noir) {
					addCercle(new Cercle(c, Couleur.NOIR));
					noir = false;
					blanc = true;
				}
				else if(blanc) {
					addCercle(new Cercle(c, Couleur.BLANC));
					blanc = false;
					rouge = true;
				}
				else if(rouge) {
					addCercle(new Cercle(c, Couleur.ROUGE));
					noir = true;
					rouge = false;
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			
		}

	}

}

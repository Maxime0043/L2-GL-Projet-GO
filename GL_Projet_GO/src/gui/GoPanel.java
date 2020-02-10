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
import donnees.Goban;
import donnees.ParametrePartie;
import donnees.Pierre;

public class GoPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	int choix = 0;
	
	int cellule = ParametrePartie.LARGEUR_CASE;
	int ecart_window = ParametrePartie.ECART;
	int taille_goban = ParametrePartie.TAILLE_GOBAN[choix];
	
	private ArrayList<Cercle> cercle;
	
	Goban goban;
	
	private boolean noir = true;
	private boolean blanc = false;
	private boolean rouge = false;
	
	public GoPanel() {
		this.addMouseListener(new Souris());
		
		cercle = new ArrayList<Cercle>();
		goban = new Goban(choix);
		
		this.setBackground(Color.decode("#F2B352"));
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
			x = c.getY() * cellule + ecart_window / 2 - 5;
			y = c.getX() * cellule + ecart_window / 2 - 5;
			
			setColor(g, c.getCouleur());
			
			g.fillOval(x, y, ParametrePartie.TAILLE_CERCLE, ParametrePartie.TAILLE_CERCLE);
		}
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
	
	public Cercle getCercle(int x, int y) {
		Cercle result = null;
		
		for(Cercle c : cercle) {
			if((c.getX() == x) && (c.getY() == y)) {
				result = c;
			}
		}
		
		if(result != null) {
			return result;
		}
		else {
			return null;
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
			
			Coordonnee coordCercle = new Coordonnee(c.getX(), c.getY());
			int numero;
			
			if(c.getCouleur().equals(Couleur.NOIR)) {
				numero = goban.getNbNoir();
			}
			else if(c.getCouleur().equals(Couleur.BLANC)) {
				numero = goban.getNbBlanc();
			}
			else {
				numero = goban.getNbRouge();
			}
			
			goban.addPierre(new Pierre(c.getCouleur(), null, coordCercle, numero));
			System.out.println("Ajout (" + c.getX() + ", " + c.getY() + ")");
			System.out.println(goban.getPierre(c.getX(), c.getY()).getCouleur());
			
			for(int i = 0 ; i < taille_goban ; i++) {
				for(int j = 0 ; j < taille_goban ; j++) {
					if(goban.existPierre(i, j)) {		
						System.out.println("(" + i + ", " + j + ") " + goban.getPierre(i, j).getLiberte());
						
						if(goban.isPierreCapture(goban.getPierre(i, j), choix)) {
							removeCercle(getCercle(i, j));
						}
					}
				}
			}
			System.out.print("\n");
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
			
			goban.removePierre(goban.getPierre(c.getX(), c.getY()));
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
			int x = (e.getY() - ecart_window / 2) / cellule;
			int y = (e.getX() - ecart_window / 2) / cellule;
			
			System.out.println(x + "   " + y);
			
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

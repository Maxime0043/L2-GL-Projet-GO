package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import donnees.Cercle;
import donnees.Coordonnee;
import donnees.Couleur;
import donnees.ParametrePartie;
import traitement.CalculFactory;
import traitement.moteurs.Moteur;

/**
 * Cette classe gère l'affichage graphique lié au jeu de go.
 * 
 * Cette classe est seulement responsable de l'affichage des résultats graphique.
 * 
 * Il n'y a pas de traitement algorithmique dans cette classe.
 * 
 * @author Maxime, Micael et Houssam
 *
 */
public class GoPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private Moteur moteur;
	
	private int cellule;
	private int ecart_window_horizontal;
	private int ecart_window_vertical;
	private int taille_goban;
	private int nb_joueurs;
	
	private boolean isDidacticiel;
	
	private int taille_cercle;
	private int taille_mega_cercle;
	private int petit_decalage;
	private int taille_hoshi;
	private int petit_decalage_hoshi;
	
	public GoPanel(Moteur moteur, int choix, int nb_joueurs, boolean isDidacticiel) {
		initGoPanel(moteur, choix, nb_joueurs, isDidacticiel);
		
		this.addMouseListener(new Souris());
		this.addMouseMotionListener(new DeplacementSouris());
		
		this.setBackground(ParametrePartie.BACKGROUND_COLOR);
	}
	
	public void initGoPanel(Moteur moteur, int choix, int nb_joueurs, boolean isDidacticiel) {
		if(choix == 0) {
			cellule = ParametrePartie.LARGEUR_CASE_9;
			taille_cercle = ParametrePartie.TAILLE_CERCLE_9;
			taille_mega_cercle = ParametrePartie.TAILLE_MEGA_CERCLE_9;
			petit_decalage = ParametrePartie.PETITE_DECALAGE_9;
			taille_hoshi = ParametrePartie.TAILLE_HOSHI_9;
			petit_decalage_hoshi = ParametrePartie.PETIT_DECALAGE_HOSHI_9;
		}
		
		else {
			cellule = ParametrePartie.LARGEUR_CASE_19;
			taille_cercle = ParametrePartie.TAILLE_CERCLE_19;
			taille_mega_cercle = ParametrePartie.TAILLE_MEGA_CERCLE_19;
			petit_decalage = ParametrePartie.PETITE_DECALAGE_19;
			taille_hoshi = ParametrePartie.TAILLE_HOSHI_19;
			petit_decalage_hoshi = ParametrePartie.PETIT_DECALAGE_HOSHI_19;
		}
		
		this.moteur = moteur;
		this.nb_joueurs = nb_joueurs;
		this.isDidacticiel = isDidacticiel;

		taille_goban = ParametrePartie.TAILLE_GOBAN[choix];
		ecart_window_horizontal = CalculFactory.getCoordEcartHorizontal(taille_goban, cellule, isDidacticiel);
		ecart_window_vertical = ParametrePartie.ECART_VERTICAL;
	}

	/**
	 * Définit ce que l'on fait lorsque la méthode repaint() est appelée.
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		drawGrille(g);
		drawCercle(g);
		drawScore(g);
		drawCouleurJoueur(g);
	}
	
	/**
	 * Dessine la grille du goban sur la sous-fenêtre GoPanel.
	 */
	private void drawGrille(Graphics g){
		g.setColor(Color.BLACK);
		
		for(int i = 0 ; i < taille_goban ; i++){
			g.drawLine(ecart_window_horizontal, ecart_window_vertical + i * cellule, ecart_window_horizontal + (taille_goban - 1) * cellule, ecart_window_vertical + i * cellule);
			g.drawLine(ecart_window_horizontal + i * cellule, ecart_window_vertical, ecart_window_horizontal + i * cellule, ecart_window_vertical + (taille_goban - 1) * cellule);
		}
	}
	
	/**
	 * Dessine les cercles des pierres / méga-pierres du goban sur la sous-fenêtre GoPanel.
	 */	
	private void drawCercle(Graphics g) {
		int x, y;
		
		for(Coordonnee c : moteur.getHoshis()) {
			x = CalculFactory.getCoordWindow(c.getY(), ecart_window_horizontal, cellule, petit_decalage_hoshi);
			y = CalculFactory.getCoordWindow(c.getX(), ecart_window_vertical, cellule, petit_decalage_hoshi);
			
			setColor(g, Couleur.NOIR, false);
			
			g.fillOval(x, y, taille_hoshi, taille_hoshi);

			Go.logger.trace("Le hoshi de coordonnées (" + x + ", " + y + ") vient d'être dessinée");
		}
		
		for(Cercle c : moteur.getCercleList()) {
			x = CalculFactory.getCoordWindow(c.getY(), ecart_window_horizontal, cellule, petit_decalage);
			y = CalculFactory.getCoordWindow(c.getX(), ecart_window_vertical, cellule, petit_decalage);
			
			setColor(g, c.getCouleur(), false);
			
			if(!c.getIsMegaPierre()) {
				g.fillOval(x, y, taille_cercle, taille_cercle);
			}
			
			else {
				g.fillOval(x, y, taille_mega_cercle, taille_mega_cercle);
			}
			
			Go.logger.trace("Le cercle de coordonnées (" + x + ", " + y + ") vient d'être dessinée");
		}
		
		Cercle survole = moteur.getSurvoleCercle();
		
		if(survole != null) {
			x = CalculFactory.getCoordWindow(survole.getY(), ecart_window_horizontal, cellule, petit_decalage);
			y = CalculFactory.getCoordWindow(survole.getX(), ecart_window_vertical, cellule, petit_decalage);
			
			setColor(g, survole.getCouleur(), true);
			
			if(!moteur.isMegaPierre() || !moteur.currentJoueur().hasMegaPierre()) {
				g.fillOval(x, y, taille_cercle, taille_cercle);
			}
			
			else if(moteur.currentJoueur().hasMegaPierre()){
				if(survole.getY() == taille_goban - 1) {
					x = CalculFactory.getCoordWindow(survole.getY() - 1, ecart_window_horizontal, cellule, petit_decalage);
				}
				if(survole.getX() == taille_goban - 1) {
					y = CalculFactory.getCoordWindow(survole.getX() - 1, ecart_window_vertical, cellule, petit_decalage);
				}
				
				g.fillOval(x, y, taille_mega_cercle, taille_mega_cercle);
			}
			
			Go.logger.trace("Le cercle de coordonnées (" + x + ", " + y + ") vient d'être dessinée au survole");
		}
		
		if(moteur.getPositionJouable() != null) {
			for(Cercle position_jouable : moteur.getPositionJouable()) {
				x = CalculFactory.getCoordWindow(position_jouable.getY(), ecart_window_horizontal, cellule, petit_decalage);
				y = CalculFactory.getCoordWindow(position_jouable.getX(), ecart_window_vertical, cellule, petit_decalage);
				
				setColor(g, position_jouable.getCouleur(), true);
				
				if(!position_jouable.getIsMegaPierre()) {
					g.drawOval(x, y, taille_cercle, taille_cercle);
				}
				
				else {
					g.drawOval(x, y, taille_mega_cercle, taille_mega_cercle);
				}
				
				Go.logger.trace("Le cercle de coordonnées (" + x + ", " + y + ") vient d'être dessinée au survole");
			}
		}
	}
	
	/**
	 * Dessine le score des joueurs sur la sous-fenêtre GoPanel.
	 */
	private void drawScore(Graphics g) {
		g.setColor(Color.BLACK);		
		g.setFont(new Font("Impact", Font.PLAIN, 18));
		
		int x, y;
		
		if(nb_joueurs < 3) {
			x = ParametrePartie.WINDOW_WIDTH / 2 - 75;
			y = 20;
			
			if(taille_goban == ParametrePartie.TAILLE_GOBAN[1]) {
				x += 6;
			}
			
			if(isDidacticiel) {
				x -= ParametrePartie.LARGEUR_DESCRIPTION / 2 + 10;
			}
		}
		
		else {
			x = ParametrePartie.WINDOW_WIDTH / 3;
			y = 20;			
		}
		
		String score = "Noir: " + moteur.getScores()[0];
		g.drawString(score, x, y);
		
		x += 85;

		score = "Blanc: " + moteur.getScores()[1];
		g.drawString(score, x, y);

		if(nb_joueurs == 3) {
			x += 85;
			
			score = "Rouge: " + moteur.getScores()[2];
			g.drawString(score, x, y);
		}
	}
	
	/**
	 * Dessine une pastille avec la couleur du joueur courant sur la sous-fenêtre GoPanel.
	 */
	private void drawCouleurJoueur(Graphics g) {		
		if(moteur.currentCouleur() == Couleur.getCouleurs()[0]) {
			g.setColor(Color.BLACK);
		}
		else if(moteur.currentCouleur() == Couleur.getCouleurs()[1]) {
			g.setColor(Color.WHITE);
		}
		else if(moteur.currentCouleur() == Couleur.getCouleurs()[2]){
			g.setColor(Color.RED);
		}
		
		int x = 5;
		int y = 5;
		
		g.fillOval(x, y, 20, 20);
		
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, 30, 30);
	}

	private void setColor(Graphics g, Couleur couleur, boolean isTransparant) {
		if(couleur.equals(Couleur.NOIR)) {
			if(isTransparant) {
				g.setColor(new Color(0, 0, 0, 100));
			}
			
			else {
				g.setColor(Color.BLACK);
			}
		}
		
		else if(couleur.equals(Couleur.BLANC)) {
			if(isTransparant) {
				g.setColor(new Color(255, 255, 255, 180));
			}
			
			else {
				g.setColor(Color.WHITE);
			}
		}
		
		else if(couleur.equals(Couleur.ROUGE)) {
			if(isTransparant) {
				g.setColor(new Color(255, 0, 0, 100));
			}
			
			else {
				g.setColor(Color.RED);
			}
		}
		
		else if(couleur.equals(Couleur.VERT)) {
			g.setColor(new Color(0, 160, 0));
		}
	}
	
	/**
	 * Cette classe permet de récupérer des informations 
	 * lors d'un clique de la souris
	 * 
	 * @author Maxime, Micael et Houssam
	 *
	 */
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
			moteur.setCoord(e.getX(), e.getY());
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			
		}

	}
	
	/**
	 * Cette classe permet de récupérer des informations 
	 * lorsque la souris bouge dans la sous-fenêtre GoPanel.
	 * 
	 * @author Maxime, Micael et Houssam
	 *
	 */
	private class DeplacementSouris implements MouseMotionListener{

		@Override
		public void mouseDragged(MouseEvent e) {
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			moteur.survoleZone(e.getX(), e.getY());
		}
		
	}
}

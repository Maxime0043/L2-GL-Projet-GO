package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import donnees.Cercle;
import donnees.Couleur;
import donnees.Joueur;
import donnees.ParametrePartie;
import traitement.CalculFactory;
import traitement.moteurs.Moteur;

/**
 * 
 * @author Maxime
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
	
	private int taille_cerle;
	private int taille_mega_cercle;
	private int petit_decalage;
	
	public GoPanel(Moteur moteur, int choix, int nb_joueurs, boolean isDidacticiel) {
		initGoPanel(moteur, choix, nb_joueurs, isDidacticiel);
		
		this.addMouseListener(new Souris());
		this.addMouseMotionListener(new DeplacementSouris());
		this.addKeyListener(new Touche());
		
		this.setBackground(ParametrePartie.BACKGROUND_COLOR);
	}
	
	public void initGoPanel(Moteur moteur, int choix, int nb_joueurs, boolean isDidacticiel) {
		if(choix == 0) {
			cellule = ParametrePartie.LARGEUR_CASE_9;
			taille_cerle = ParametrePartie.TAILLE_CERCLE_9;
			taille_mega_cercle = ParametrePartie.TAILLE_MEGA_CERCLE_9;
			petit_decalage = ParametrePartie.PETITE_DECALAGE_9;
		}
		
		else {
			cellule = ParametrePartie.LARGEUR_CASE_19;
			taille_cerle = ParametrePartie.TAILLE_CERCLE_19;
			taille_mega_cercle = ParametrePartie.TAILLE_MEGA_CERCLE_19;
			petit_decalage = ParametrePartie.PETITE_DECALAGE_19;		
		}
		
		this.moteur = moteur;
		this.nb_joueurs = nb_joueurs;
		this.isDidacticiel = isDidacticiel;

		taille_goban = ParametrePartie.TAILLE_GOBAN[choix];
		ecart_window_horizontal = CalculFactory.getCoordEcartHorizontal(taille_goban, cellule, isDidacticiel);
		ecart_window_vertical = ParametrePartie.ECART_VERTICAL;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		drawGrid(g);
		drawCercle(g);
		drawScore(g);
		drawCouleurJoueur(g);
	}
	
	private void drawGrid(Graphics g){
		g.setColor(Color.BLACK);
		
		for(int i = 0 ; i < taille_goban ; i++){
			g.drawLine(ecart_window_horizontal, ecart_window_vertical + i * cellule, ecart_window_horizontal + (taille_goban - 1) * cellule, ecart_window_vertical + i * cellule);
			g.drawLine(ecart_window_horizontal + i * cellule, ecart_window_vertical, ecart_window_horizontal + i * cellule, ecart_window_vertical + (taille_goban - 1) * cellule);
		}
	}
	
	private void drawCercle(Graphics g) {
		int x, y;
		
		for(Cercle c : moteur.getCercleList()) {
			x = CalculFactory.getCoordWindow(c.getY(), ecart_window_horizontal, cellule, petit_decalage);
			y = CalculFactory.getCoordWindow(c.getX(), ecart_window_vertical, cellule, petit_decalage);
			
			setColor(g, c.getCouleur(), false);
			
			if(!c.getIsMegaPierre()) {
				g.fillOval(x, y, taille_cerle, taille_cerle);
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
				g.fillOval(x, y, taille_cerle, taille_cerle);
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
					g.drawOval(x, y, taille_cerle, taille_cerle);
				}
				
				else {
					g.drawOval(x, y, taille_mega_cercle, taille_mega_cercle);
				}
				
				Go.logger.trace("Le cercle de coordonnées (" + x + ", " + y + ") vient d'être dessinée au survole");
			}
		}
	}
	
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
		
		String score = "Noir: " + getScores()[0];
		g.drawString(score, x, y);
		
		x += 85;

		score = "Blanc: " + getScores()[1];
		g.drawString(score, x, y);

		if(nb_joueurs == 3) {
			x += 85;
			
			score = "Rouge: " + getScores()[2];
			g.drawString(score, x, y);
		}
	}
	
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
	
	public boolean canPlayMegaPierre() {
		return moteur.canPlayMegaPierre();
	}
	
	public int[] getScores() {
		int[] scores = new int[nb_joueurs];
		Joueur[] joueurs = moteur.getJoueurs();
		
		for(int i = 0 ; i < nb_joueurs ; i++) {
			scores[i] = joueurs[i].getScore();
		}
		
		return scores;
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
			moteur.clicEvent(e.getX(), e.getY());
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			
		}

	}
	
	private class DeplacementSouris implements MouseMotionListener{

		@Override
		public void mouseDragged(MouseEvent e) {
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			moteur.survoleZone(e.getX(), e.getY());
		}
		
	}
	
	private class Touche implements KeyListener{

		@Override
		public void keyPressed(KeyEvent e) {
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			
		}
		
	}

}

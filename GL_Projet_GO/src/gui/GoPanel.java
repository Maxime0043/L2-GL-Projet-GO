package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import donnees.Cercle;
import donnees.Couleur;
import donnees.ParametrePartie;
import traitement.Joueur;
import traitement.Moteur;

/**
 * 
 * @author Maxime
 *
 */
public class GoPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private Moteur moteur;
	
	private int cellule;
	private int ecart_window;
	private int taille_goban;
	private int nb_joueurs;
	
	private int taille_cerle;
	private int taille_mega_cercle;
	private int petit_decalage;
	
	public GoPanel(int choix, int nb_joueur, int nb_ordi, boolean didacticiel) {
		initGoPanel(choix, nb_joueur, nb_ordi, didacticiel);
		
		this.addMouseListener(new Souris());
		this.addMouseMotionListener(new DeplacementSouris());
		this.addKeyListener(new Touche());
		
		this.setBackground(Color.decode("#F2B352"));
	}
	
	public void initGoPanel(int choix, int nb_joueur, int nb_ordi, boolean didacticiel) {
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
		
		ecart_window = ParametrePartie.ECART;
		taille_goban = ParametrePartie.TAILLE_GOBAN[choix];
		nb_joueurs = nb_joueur + nb_ordi;
		
		moteur = new Moteur(cellule, ecart_window, taille_goban, nb_joueur, nb_ordi, didacticiel);
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
			x = c.getY() * cellule + ecart_window + petit_decalage - (cellule / 2);
			y = c.getX() * cellule + ecart_window + petit_decalage - (cellule / 2);
			
			setColor(g, c.getCouleur());
			
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
			x = survole.getY() * cellule + ecart_window + petit_decalage - (cellule / 2);
			y = survole.getX() * cellule + ecart_window + petit_decalage - (cellule / 2);
			
			setColor(g, survole.getCouleur());
			
			if(!moteur.getIsMegaPierre() || !moteur.currentJoueur().hasMegaPierre()) {
				g.fillOval(x, y, taille_cerle, taille_cerle);
			}
			
			else if(moteur.currentJoueur().hasMegaPierre()){
				if(survole.getY() == taille_goban - 1) {
					x = (survole.getY() - 1) * cellule + ecart_window + petit_decalage - (cellule / 2);
				}
				if(survole.getX() == taille_goban - 1) {
					y = (survole.getX() - 1) * cellule + ecart_window + petit_decalage - (cellule / 2);
				}
				
				g.fillOval(x, y, taille_mega_cercle, taille_mega_cercle);
			}
			
			Go.logger.trace("Le cercle de coordonnées (" + x + ", " + y + ") vient d'être dessinée au survole");
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
	
	public boolean isDidacticielFini() {
		return moteur.isDidacticielFini();
	}
	
	public void passer() {
		moteur.passer();
	}
	
	public boolean getIsMegaPierre() {
		return moteur.getIsMegaPierre();
	}
	
	public void poseMegaPierre() {
		if(moteur.getIsMegaPierre()) {
			moteur.setIsMegaPierre(false);
		}
		else {
			moteur.setIsMegaPierre(true);
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

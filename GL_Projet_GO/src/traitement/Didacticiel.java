package traitement;

import donnees.Coordonnee;
import donnees.Couleur;
import donnees.MegaPierre;
import donnees.Pierre;

public class Didacticiel {

	private Moteur moteur;
	
	private int taille_goban;
	private int nb_levels = 7;
	private int level;
	
	public Didacticiel(int taille_goban, Moteur moteur) {
		this.moteur = moteur;
		this.taille_goban = taille_goban;
		level = 0;
	}

	private void reinit() {
		moteur.reinitGoban(taille_goban);

		for(Joueur joueur : moteur.getJoueurs()) {
			joueur.initScore();
		}
	}
	
	public int getNbLevels() {
		return nb_levels;
	}
	
	public int getLevel() {
		return level;
	}
	
	public void changeLevel() {
		level++;
		
		reinit();
		chargeLevel();
	}
	
	public void chargeLevel() {
		Coordonnee c;
		
		if(level == 0) {
			c = new Coordonnee(3, 4);
			moteur.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(4, 3);
			moteur.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(4, 5);
			moteur.addPierre(new Pierre(Couleur.NOIR, c));
			
			c = new Coordonnee(4, 4);
			moteur.addPierre(new Pierre(Couleur.BLANC, c));
		}
		
		else if(level == 1) {
			c = new Coordonnee(0, 1);
			moteur.addPierre(new Pierre(Couleur.NOIR, c));
			
			c = new Coordonnee(0, 0);
			moteur.addPierre(new Pierre(Couleur.BLANC, c));
		}
		
		else if(level == 2) {
			c = new Coordonnee(0, 3);
			moteur.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(1, 4);
			moteur.addPierre(new Pierre(Couleur.NOIR, c));
			
			c = new Coordonnee(0, 4);
			moteur.addPierre(new Pierre(Couleur.BLANC, c));
		}
		
		else if(level == 3) {
			c = new Coordonnee(2, 3);
			moteur.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(2, 4);
			moteur.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(3, 2);
			moteur.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(3, 5);
			moteur.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(4, 3);
			moteur.addPierre(new Pierre(Couleur.NOIR, c));
			
			c = new Coordonnee(3, 3);
			moteur.addPierre(new Pierre(Couleur.BLANC, c));
			c = new Coordonnee(3, 4);
			moteur.addPierre(new Pierre(Couleur.BLANC, c));
		}
		
		else if(level == 4) {
			c = new Coordonnee(2, 4);
			moteur.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(3, 5);
			moteur.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(4, 4);
			moteur.addPierre(new Pierre(Couleur.NOIR, c));
			
			c = new Coordonnee(3, 4);
			moteur.addPierre(new Pierre(Couleur.BLANC, c));
			c = new Coordonnee(3, 2);
			moteur.addPierre(new Pierre(Couleur.BLANC, c));		
			c = new Coordonnee(2, 3);
			moteur.addPierre(new Pierre(Couleur.BLANC, c));
			c = new Coordonnee(4, 3);
			moteur.addPierre(new Pierre(Couleur.BLANC, c));		
		}
		
		else if(level == 5) {
			c = new Coordonnee(2, 3);
			moteur.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(2, 4);
			moteur.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(3, 2);
			moteur.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(3, 5);
			moteur.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(4, 2);
			moteur.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(4, 5);
			moteur.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(5, 3);
			moteur.addPierre(new Pierre(Couleur.NOIR, c));
			
			c = new Coordonnee(3, 3);
			moteur.setIsMegaPierre(true);
			moteur.addPierre(new MegaPierre(Couleur.BLANC, c));
			moteur.setIsMegaPierre(false);
		}
		
		else if(level == 6) {
			c = new Coordonnee(2, 3);
			moteur.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(2, 4);
			moteur.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(2, 5);
			moteur.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(3, 2);
			moteur.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(3, 6);
			moteur.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(4, 2);
			moteur.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(4, 6);
			moteur.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(5, 3);
			moteur.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(5, 4);
			moteur.addPierre(new Pierre(Couleur.NOIR, c));
			
			c = new Coordonnee(3, 3);
			moteur.setIsMegaPierre(true);
			moteur.addPierre(new MegaPierre(Couleur.BLANC, c));
			moteur.setIsMegaPierre(false);
			c = new Coordonnee(3, 5);
			moteur.addPierre(new Pierre(Couleur.BLANC, c));
			c = new Coordonnee(4, 5);
			moteur.addPierre(new Pierre(Couleur.BLANC, c));
		}
		
		else if(level == 7) {
			c = new Coordonnee(2, 3);
			moteur.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(2, 4);
			moteur.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(3, 2);
			moteur.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(4, 2);
			moteur.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(5, 3);
			moteur.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(5, 4);
			moteur.addPierre(new Pierre(Couleur.NOIR, c));
			
			c = new Coordonnee(3, 3);
			moteur.addPierre(new Pierre(Couleur.BLANC, c));
			c = new Coordonnee(3, 4);
			moteur.addPierre(new Pierre(Couleur.BLANC, c));
			c = new Coordonnee(4, 3);
			moteur.addPierre(new Pierre(Couleur.BLANC, c));
			c = new Coordonnee(4, 4);
			moteur.addPierre(new Pierre(Couleur.BLANC, c));
		}
	}
}

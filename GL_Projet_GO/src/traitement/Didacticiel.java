package traitement;

import donnees.Coordonnee;
import donnees.Couleur;
import donnees.Joueur;
import donnees.MegaPierre;
import donnees.Pierre;
import gui.Go;
import test.input.Description;
import traitement.moteurs.Moteur;
import traitement.moteurs.MoteurPierre;

public class Didacticiel {

	private Moteur moteur;
	private MoteurPierre moteur_pierre;
	
	private int nb_levels = 12;
	private int level;
	public final static int MAX_LEVEL_DEFENSE = 3;
	
	public Didacticiel(Moteur moteur, MoteurPierre moteur_pierre) {
		this.moteur = moteur;
		this.moteur_pierre = moteur_pierre;
		level = 0;
	}

	private void reinit() {
		moteur.reinitGoban();
		moteur.setPoseMegaPierre(false);

		for(Joueur joueur : moteur.getJoueurs()) {
			joueur.initScore();
			joueur.initNbMegaPierre();
		}
	}
	
	public int getNbLevels() {
		return nb_levels;
	}
	
	public int getLevel() {
		return level;
	}
	
	public int getMaxLevelDefense() {
		return MAX_LEVEL_DEFENSE;
	}
	
	public void initPositionJouable(int x, int y, Couleur couleur, boolean isMegaPierre) {
		moteur.initPositionJouable(new Coordonnee(x, y), couleur, isMegaPierre);
	}
	
	public void changeLevel(boolean suivant) {
		if(suivant) {
			level++;
		}
		
		else {
			level--;
		}
		
		Go.logger.info("Passage au niveau " + level);
		
		reinit();
		chargeLevel();
	}
	
	public void resetLevel() {
		Go.logger.info("Réinitialisation du niveau " + level);
		
		reinit();
		chargeLevel();
	}
	
	public void chargeLevel() {
		Go.logger.info("Chargement du niveau " + level);
		
		moteur.setDescription(Description.getDescription(level));
		
		Coordonnee c;
		
		/*-------------Défense-----------------*/
		
		if(level == 0) {
			c = new Coordonnee(3, 4);
			moteur_pierre.addPierre(new Pierre(Couleur.BLANC, c));
			c = new Coordonnee(4, 3);
			moteur_pierre.addPierre(new Pierre(Couleur.BLANC, c));
			c = new Coordonnee(5, 4);
			moteur_pierre.addPierre(new Pierre(Couleur.BLANC, c));
			
			c = new Coordonnee(4, 4);
			moteur_pierre.addPierre(new Pierre(Couleur.NOIR, c));

			initPositionJouable(4, 5, Couleur.VERT, false);
			moteur.setPositionJouable();
		}
		
		else if(level == 1) {
			c = new Coordonnee(3, 4);
			moteur_pierre.addPierre(new Pierre(Couleur.BLANC, c));
			c = new Coordonnee(4, 3);
			moteur_pierre.addPierre(new Pierre(Couleur.BLANC, c));
			c = new Coordonnee(5, 4);
			moteur_pierre.addPierre(new Pierre(Couleur.BLANC, c));
			
			c = new Coordonnee(4, 4);
			moteur_pierre.addPierre(new Pierre(Couleur.NOIR, c));
			
			moteur.setPoseMegaPierre(true);
			
			initPositionJouable(3, 5, Couleur.VERT, true);
			moteur.setPositionJouable();
		}
		
		else if(level == 2) {
			c = new Coordonnee(3, 4);
			moteur_pierre.addPierre(new Pierre(Couleur.BLANC, c));
			c = new Coordonnee(4, 3);
			moteur_pierre.addPierre(new Pierre(Couleur.BLANC, c));
			c = new Coordonnee(4, 5);
			moteur_pierre.addPierre(new Pierre(Couleur.BLANC, c));
			c = new Coordonnee(5, 3);
			moteur_pierre.addPierre(new Pierre(Couleur.BLANC, c));
			c = new Coordonnee(5, 5);
			moteur_pierre.addPierre(new Pierre(Couleur.BLANC, c));
			
			c = new Coordonnee(4, 4);
			moteur_pierre.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(5, 4);
			moteur_pierre.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(3, 3);
			moteur_pierre.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(3, 5);
			moteur_pierre.addPierre(new Pierre(Couleur.NOIR, c));
			
			initPositionJouable(2, 4, Couleur.VERT, false);
			initPositionJouable(6, 4, Couleur.ROUGE, false);
			moteur.setPositionJouable();
		}
		
		else if(level == 3) {
			c = new Coordonnee(2, 3);
			moteur_pierre.addPierre(new Pierre(Couleur.BLANC, c));
			c = new Coordonnee(2, 4);
			moteur_pierre.addPierre(new Pierre(Couleur.BLANC, c));
			c = new Coordonnee(3, 2);
			moteur_pierre.addPierre(new Pierre(Couleur.BLANC, c));
			c = new Coordonnee(4, 2);
			moteur_pierre.addPierre(new Pierre(Couleur.BLANC, c));
			c = new Coordonnee(5, 3);
			moteur_pierre.addPierre(new Pierre(Couleur.BLANC, c));
			c = new Coordonnee(5, 4);
			moteur_pierre.addPierre(new Pierre(Couleur.BLANC, c));
			c = new Coordonnee(3, 5);
			moteur_pierre.addPierre(new Pierre(Couleur.BLANC, c));
			
			c = new Coordonnee(3, 3);
			moteur_pierre.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(3, 4);
			moteur_pierre.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(4, 3);
			moteur_pierre.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(4, 4);
			moteur_pierre.addPierre(new Pierre(Couleur.NOIR, c));

			moteur.setPoseMegaPierre(true);
			
			initPositionJouable(1, 3, Couleur.VERT, true);
			initPositionJouable(3, 1, Couleur.VERT, true);
			initPositionJouable(5, 3, Couleur.VERT, true);
			initPositionJouable(3, 5, Couleur.VERT, true);
			moteur.setPositionJouable();
		}

		/*-------------Attaque-----------------*/
		
		else if(level == 4) {
			c = new Coordonnee(3, 4);
			moteur_pierre.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(4, 3);
			moteur_pierre.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(4, 5);
			moteur_pierre.addPierre(new Pierre(Couleur.NOIR, c));
			
			c = new Coordonnee(4, 4);
			moteur_pierre.addPierre(new Pierre(Couleur.BLANC, c));
			
			initPositionJouable(5, 4, Couleur.VERT, false);
			moteur.setPositionJouable();
		}
		
		else if(level == 5) {
			c = new Coordonnee(0, 3);
			moteur_pierre.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(1, 4);
			moteur_pierre.addPierre(new Pierre(Couleur.NOIR, c));
			
			c = new Coordonnee(0, 4);
			moteur_pierre.addPierre(new Pierre(Couleur.BLANC, c));
			
			initPositionJouable(0, 5, Couleur.VERT, false);
			moteur.setPositionJouable();
		}
		
		else if(level == 6) {
			c = new Coordonnee(0, 1);
			moteur_pierre.addPierre(new Pierre(Couleur.NOIR, c));
			
			c = new Coordonnee(0, 0);
			moteur_pierre.addPierre(new Pierre(Couleur.BLANC, c));
			
			initPositionJouable(1, 0, Couleur.VERT, false);
			moteur.setPositionJouable();
		}
		
		else if(level == 7) {
			c = new Coordonnee(2, 3);
			moteur_pierre.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(2, 4);
			moteur_pierre.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(3, 2);
			moteur_pierre.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(3, 5);
			moteur_pierre.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(4, 3);
			moteur_pierre.addPierre(new Pierre(Couleur.NOIR, c));
			
			c = new Coordonnee(3, 3);
			moteur_pierre.addPierre(new Pierre(Couleur.BLANC, c));
			c = new Coordonnee(3, 4);
			moteur_pierre.addPierre(new Pierre(Couleur.BLANC, c));
			
			initPositionJouable(4, 4, Couleur.VERT, false);
			moteur.setPositionJouable();
		}
		
		else if(level == 8) {
			c = new Coordonnee(2, 4);
			moteur_pierre.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(3, 5);
			moteur_pierre.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(4, 4);
			moteur_pierre.addPierre(new Pierre(Couleur.NOIR, c));
			
			c = new Coordonnee(3, 4);
			moteur_pierre.addPierre(new Pierre(Couleur.BLANC, c));
			c = new Coordonnee(3, 2);
			moteur_pierre.addPierre(new Pierre(Couleur.BLANC, c));		
			c = new Coordonnee(2, 3);
			moteur_pierre.addPierre(new Pierre(Couleur.BLANC, c));
			c = new Coordonnee(4, 3);
			moteur_pierre.addPierre(new Pierre(Couleur.BLANC, c));
			
			initPositionJouable(3, 3, Couleur.VERT, false);
			moteur.setPositionJouable();
		}
		
		else if(level == 9) {
			c = new Coordonnee(2, 3);
			moteur_pierre.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(2, 4);
			moteur_pierre.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(3, 2);
			moteur_pierre.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(3, 5);
			moteur_pierre.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(4, 2);
			moteur_pierre.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(4, 5);
			moteur_pierre.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(5, 3);
			moteur_pierre.addPierre(new Pierre(Couleur.NOIR, c));
			
			c = new Coordonnee(3, 3);
			moteur.setPoseMegaPierre(true);
			moteur_pierre.addPierre(new MegaPierre(Couleur.BLANC, c));
			moteur.setPoseMegaPierre(false);
			
			moteur.setPoseMegaPierre(true);
			
			initPositionJouable(5, 4, Couleur.VERT, true);
			moteur.setPositionJouable();
		}
		
		else if(level == 10) {
			c = new Coordonnee(2, 3);
			moteur_pierre.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(2, 4);
			moteur_pierre.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(2, 5);
			moteur_pierre.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(3, 2);
			moteur_pierre.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(3, 6);
			moteur_pierre.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(4, 2);
			moteur_pierre.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(4, 6);
			moteur_pierre.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(5, 3);
			moteur_pierre.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(5, 4);
			moteur_pierre.addPierre(new Pierre(Couleur.NOIR, c));
			
			c = new Coordonnee(3, 3);
			moteur.setPoseMegaPierre(true);
			moteur_pierre.addPierre(new MegaPierre(Couleur.BLANC, c));
			moteur.setPoseMegaPierre(false);
			c = new Coordonnee(3, 5);
			moteur_pierre.addPierre(new Pierre(Couleur.BLANC, c));
			c = new Coordonnee(4, 5);
			moteur_pierre.addPierre(new Pierre(Couleur.BLANC, c));
			
			moteur.setPoseMegaPierre(true);
			
			initPositionJouable(5, 5, Couleur.VERT, true);
			moteur.setPositionJouable();
		}
		
		else if(level == 11) {
			c = new Coordonnee(2, 3);
			moteur_pierre.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(2, 4);
			moteur_pierre.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(3, 2);
			moteur_pierre.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(4, 2);
			moteur_pierre.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(5, 3);
			moteur_pierre.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(5, 4);
			moteur_pierre.addPierre(new Pierre(Couleur.NOIR, c));
			
			c = new Coordonnee(3, 3);
			moteur_pierre.addPierre(new Pierre(Couleur.BLANC, c));
			c = new Coordonnee(3, 4);
			moteur_pierre.addPierre(new Pierre(Couleur.BLANC, c));
			c = new Coordonnee(4, 3);
			moteur_pierre.addPierre(new Pierre(Couleur.BLANC, c));
			c = new Coordonnee(4, 4);
			moteur_pierre.addPierre(new Pierre(Couleur.BLANC, c));
			
			moteur.setPoseMegaPierre(true);
			
			initPositionJouable(3, 5, Couleur.VERT, true);
			moteur.setPositionJouable();
		}
		
		else if(level == 12) {
			c = new Coordonnee(2, 3);
			moteur_pierre.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(3, 2);
			moteur_pierre.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(4, 2);
			moteur_pierre.addPierre(new Pierre(Couleur.NOIR, c));
			c = new Coordonnee(5, 3);
			moteur_pierre.addPierre(new Pierre(Couleur.NOIR, c));
			
			c = new Coordonnee(3, 3);
			moteur_pierre.addPierre(new Pierre(Couleur.BLANC, c));
			c = new Coordonnee(3, 4);
			moteur_pierre.addPierre(new Pierre(Couleur.BLANC, c));
			c = new Coordonnee(4, 3);
			moteur_pierre.addPierre(new Pierre(Couleur.BLANC, c));
			c = new Coordonnee(4, 4);
			moteur_pierre.addPierre(new Pierre(Couleur.BLANC, c));
			
			moteur.setPoseMegaPierre(true);
			
			initPositionJouable(3, 4, Couleur.VERT, true);
			moteur.setPositionJouable();
		}
		
		/*-------------Territoires-----------------*/
		
		else if(level == 13) {
			c = new Coordonnee(2, 3);
			moteur_pierre.addPierre(new Pierre(Couleur.NOIR, c));
			
			c = new Coordonnee(3, 3);
			moteur_pierre.addPierre(new Pierre(Couleur.BLANC, c));
		}
	}
}

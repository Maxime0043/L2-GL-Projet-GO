package traitement;

import java.util.ArrayList;

import donnees.AbstractPierre;
import donnees.Cercle;
import donnees.Coordonnee;
import donnees.Couleur;
import donnees.MegaPierre;
import donnees.Pierre;
import gui.Go;
import traitement.FinDePartie;

/**
 * 
 * @author Maxime
 *
 */
public class Moteur {
	
	private int cellule;
	private int ecart_window;
	private int taille_goban;
	private int nb_joueurs;
	
	private Goban goban;
	private Didacticiel didacticiel;
	private FinDePartie fin;
	
	private Joueur[] joueurs;
	private ArrayList<Cercle> cercle;
	private Cercle survole;
	
	private boolean noir = true;
	private boolean blanc = false;
	private boolean rouge = false;
	private boolean isMegaPierre = false;
	private boolean didacticiel_fini = false;
	private boolean suicide = false;
	
	private int pass_compteur = 0;
	
	private AbstractPierre Ko = null;
	private int Ko_compteur = 0;
	private boolean isKo = false;
	
	public Moteur(int cellule, int ecart_window, int taille_goban, int nb_joueur, int nb_ordi, boolean didacticiel) {
		this.cellule = cellule;
		this.ecart_window = ecart_window;
		this.taille_goban = taille_goban;
		nb_joueurs = nb_joueur + nb_ordi;
		
		goban = new Goban(taille_goban);
		fin = new FinDePartie(taille_goban);
		joueurs = new Joueur[nb_joueurs];
		cercle = new ArrayList<Cercle>();

		initJoueur();
		
		this.didacticiel = null;
		
		if(didacticiel) {
			this.didacticiel = new Didacticiel(this);
			
			Go.logger.debug("Début du Didacticiel");
			
			this.didacticiel.chargeLevel();
		}
	}
	
	public void reinitGoban() {
		goban.initPlateau();
		setIsMegaPierre(false);
		cercle.clear();
		changeJoueur();
	}
	
	private void initJoueur() {
		joueurs[0] = new Joueur(Couleur.NOIR, false);
		Go.logger.debug("Initialisation du joueur NOIR");
		
		joueurs[1] = new Joueur(Couleur.BLANC, false);
		Go.logger.debug("Initialisation du joueur BLANC");
		
		if(nb_joueurs == 3) {
			joueurs[2] = new Joueur(Couleur.ROUGE, false);
			Go.logger.debug("Initialisation du joueur ROUGE");
		}
	}
	
	public void passer() {
		changeJoueur();
		pass_compteur++;
		
		if(pass_compteur == nb_joueurs) {
			initPassCompteur();
			fin.setChaineTwoEye(goban.getHmChaine(), goban.getPlateau());
			System.out.println("Fini");
		}
	}
	
	private void initPassCompteur() {
		pass_compteur = 0;
	}
	
	private void incrementeKoCompteur() {
		if(isKo) {
			Ko_compteur++;
			
			if(Ko_compteur == nb_joueurs) {
				setKo(null);
				
				isKo = false;
				Ko_compteur = 0;
			}
		}
	}
	
	public Joueur[] getJoueurs(){
		return joueurs;
	}
	
	public boolean getNoir() {
		return noir;
	}
	
	public boolean getBlanc() {
		return blanc;
	}
	
	public boolean getRouge() {
		return rouge;
	}
	
	public boolean getIsMegaPierre() {
		return isMegaPierre;
	}
	
	public void setIsMegaPierre(boolean bool) {
		isMegaPierre = bool;
	}
	
	public boolean canPlayMegaPierre() {
		return currentJoueur().hasMegaPierre();
	}
	
	private void setSuicide(boolean bool) {
		suicide = bool;
	}
	
	private void setKo (AbstractPierre pierre) {
		Ko = pierre;
	}
	
	private AbstractPierre getKo () {
		return Ko;
	}
	
	public ArrayList<Cercle> getCercleList(){
		return cercle;
	}
	
	public Cercle getSurvoleCercle() {
		return survole;
	}
	
	private Couleur currentCouleur() {
		if(noir) {
			return Couleur.NOIR;
		}
		else if(blanc) {
			return Couleur.BLANC;
		}
		else {
			return Couleur.ROUGE;
		}
	}
	
	public Joueur currentJoueur() {
		Joueur j = null;
		
		for(int i = 0 ; i < nb_joueurs ; i++) {
			if(joueurs[i].getCouleur().equals(currentCouleur())) {
				j = joueurs[i];
			}
		}
		
		return j;
	}
	
	public void changeJoueur() {
		if(noir) {
			noir = false;
			blanc = true;
		}
		else if(blanc) {
			blanc = false;
			
			if(nb_joueurs > 2) {
				rouge = true;
			}
			else {
				noir = true;
			}
		}
		else if(rouge) {
			rouge = false;
			noir = true;
		}
		
		if(didacticiel != null) {
			noir = true;
			blanc = false;
		}
	}
	
	public boolean isDidacticielFini() {
		return didacticiel_fini;
	}
	
	public int getCurrentLevel() {
		return didacticiel.getLevel();
	}
	
	private void changeLevel() {
		if(didacticiel != null) {
			if(currentJoueur().getScore() > 0) {
				if(didacticiel.getLevel() < didacticiel.getNbLevels()) {
					didacticiel.changeLevel();
				}
				else {
					didacticiel_fini = true;
					
					Go.logger.debug("Fin du didacticiel");
				}
			}
		}
	}
	
	public void survoleZone(int coordX, int coordY) {
		int x = (coordY - ecart_window + (cellule / 2)) / cellule;
		int y = (coordX - ecart_window + (cellule / 2)) / cellule;
		
		Couleur couleur;
		
		if((x >= 0) && (x < taille_goban) && (y >= 0) && (y < taille_goban)) {
			couleur = currentCouleur();
			
			if(!isMegaPierre && !goban.existPierre(x, y)) {
				survole = new Cercle(new Coordonnee(x, y), couleur, false);
			}
			
			else if(isMegaPierre && currentJoueur().hasMegaPierre()) {
				if(x == taille_goban - 1) {
					x--;
				}
				if(y == taille_goban - 1) {
					y--;
				}
				
				if(!goban.existPierre(x, y) || !goban.existPierre(x+1, y) || !goban.existPierre(x, y+1) || !goban.existPierre(x+1, y+1)) {
					if(canDestruct(x, y)) {
						survole = new Cercle(new Coordonnee(x, y), couleur, true);						
					}
					
					else {
						survole = null;
					}
				}
				
				else {
					survole = null;
				}
			}
			
			else {
				survole = null;
			}
		}
	}
	
	public void clicEvent(int coordX, int coordY) {
		long startTime = System.currentTimeMillis();
		
		int x = (coordY - ecart_window + (cellule / 2)) / cellule;
		int y = (coordX - ecart_window + (cellule / 2)) / cellule;
		
		if((x >= 0) && (x < taille_goban) && (y >= 0) && (y < taille_goban)) {
			if(!isMegaPierre && !goban.existPierre(x, y)) {
				AbstractPierre pierre = new Pierre(currentCouleur(), x, y);
				
				addPierre(pierre);
				currentJoueur().addPierre(pierre);
				
				if(!suicide) {
					changeJoueur();
					incrementeKoCompteur();
				}
			}
			
			else if(isMegaPierre) {
				if(x == taille_goban - 1) {
					x--;
				}
				
				if(y == taille_goban - 1) {
					y--;
				}
				
				if(!goban.existPierre(x, y) || !goban.existPierre(x+1, y) || !goban.existPierre(x, y+1) || !goban.existPierre(x+1, y+1)) {
					AbstractPierre pierre = new MegaPierre(currentCouleur(), x, y);
					
					if(canDestruct(x, y)) {
						destruct(x, y);
						
						addPierre(pierre);
						currentJoueur().addPierre(pierre);

						if(!suicide) {
							currentJoueur().playMegaPierre();
							changeJoueur();
							incrementeKoCompteur();
						}						
					}
				}
			}
		}
		
		initPassCompteur();
		setIsMegaPierre(false);
		setSuicide(false);
		changeLevel();
		
		Go.logger.info("Temps pour jouer un coup: " + (System.currentTimeMillis() - startTime));
	}
	
	public void addPierre(AbstractPierre pierre) {
		goban.addPierre(pierre);
		Go.logger.info("Pierre créée aux coordonnées : (" + pierre.getX() + ", " + pierre.getY() + ")");
		Go.logger.info("Ajout d'une Pierre / MegaPierre de coordonnées (" + pierre.getX() + ", " + pierre.getY() + ") au joueur " + currentJoueur().getCouleur());
		
		Coordonnee coordPierre = new Coordonnee(pierre.getX(), pierre.getY());
		
		cercle.add(new Cercle(coordPierre, pierre.getCouleur(), isMegaPierre));
		Go.logger.info("Cercle créé aux coordonnées : (" + pierre.getX() + ", " + pierre.getY() + ")");
		
		ArrayList<AbstractPierre> voisin = GoPierre.voisins(goban.getPierre(pierre.getX(), pierre.getY()), goban.getPlateau(), taille_goban);
		
		for(AbstractPierre pierreVoisin : voisin) {
			if(pierreVoisin.hasChaine() && !pierreVoisin.getCouleur().equals(pierre.getCouleur())) {
				if(goban.isPierreCapture(goban.getChaine(pierreVoisin.getNomChaine()))) {
					removePierre(goban.getChaine(pierreVoisin.getNomChaine()));
					
					currentJoueur().addScore(goban.getScoreCapture());
					Go.logger.info("Ajout de " + goban.getScoreCapture() + " points au joueur de couleur " + currentJoueur().getCouleur());
				}
			}
			
			else if(goban.isPierreCapture(pierreVoisin) && !pierreVoisin.getCouleur().equals(pierre.getCouleur()) && !pierreVoisin.equals(getKo())) {
				removePierre(pierreVoisin);
				setKo(pierre);
				isKo = true;
				
				currentJoueur().addScore(goban.getScoreCapture());
				Go.logger.info("Ajout de " + goban.getScoreCapture() + " points au joueur de couleur " + currentJoueur().getCouleur());
			}
				
		}
		
		if(pierre.hasChaine() && goban.isSuicide(goban.getChaine(pierre.getNomChaine()))) {
			Go.logger.info("Suicide Chaine : Suppression de la pierre de coordonnées (" + pierre.getX() + ", " + pierre.getY() + ")");
			removePierre(pierre);
			setSuicide(true);
		}
		
		else if(goban.isSuicide(pierre)) {
			Go.logger.info("Suicide : Suppression de la pierre de coordonnées (" + pierre.getX() + ", " + pierre.getY() + ")");
			removePierre(pierre);
			setSuicide(true);
		}
	}
	
	private void removePierre(AbstractPierre pierre) {
		if(goban.existPierre(pierre.getX(), pierre.getY())) {
			Go.logger.info("Pierre supprimée aux coordonnées : (" + pierre.getX() + ", " + pierre.getY() + ")");
			goban.removePierre(pierre);

			Go.logger.info("Pierre de coordonnées (" + pierre.getX() + ", " + pierre.getY() + ") retirée au joueur " + currentJoueur().getCouleur());
			currentJoueur().removePierre(pierre);
			
			Cercle c = null;
			
			for(Cercle coordCercle : cercle) {
				if((pierre.getX() == coordCercle.getX()) && (pierre.getY() == coordCercle.getY())) {
					c = coordCercle;
				}
			}

			Go.logger.info("Cercle supprimé aux coordonnées : (" + pierre.getX() + ", " + pierre.getY() + ")");
			cercle.remove(c);
		}
	}
	
	private void removePierre(ArrayList<AbstractPierre> chaine) {
		for(AbstractPierre pierre : chaine) {
			removePierre(pierre);
		}
	}
	
	private boolean canDestruct(int x, int y) {
		boolean exist_voisin = false;
		int compteur = 0;
		
		if(goban.existPierre(x, y) && goban.existPierre(x+1, y) && goban.existPierre(x, y+1) && goban.existPierre(x+1, y+1)) {
			return false;
		}
		
		else {
			if(goban.existPierre(x, y)) {
				if(goban.getPierre(x, y).isMegaPierre()) {
					return false;
				}
				
				if(goban.getPierre(x, y).getLiberte() > 0) {
					compteur++;
				}
				
				exist_voisin = true;
			}

			
			if(goban.existPierre(x+1, y)) {
				if(goban.getPierre(x+1, y).isMegaPierre()) {
					return false;
				}
				
				if(goban.getPierre(x+1, y).getLiberte() > 0) {
					compteur++;
				}
				
				exist_voisin = true;
			}

			
			if(goban.existPierre(x, y+1)) {
				if(goban.getPierre(x, y+1).isMegaPierre()) {
					return false;
				}
				
				if(goban.getPierre(x, y+1).getLiberte() > 0) {
					compteur++;
				}
				
				exist_voisin = true;
			}

			
			if(goban.existPierre(x+1, y+1)) {
				if(goban.getPierre(x+1, y+1).isMegaPierre()) {
					return false;
				}
				
				if(goban.getPierre(x+1, y+1).getLiberte() > 0) {
					compteur++;
				}
				
				exist_voisin = true;
			}
		}
		
		if(!exist_voisin || (compteur > 0)) {
			return true;
		}
		
		return false;
	}
	
	private void destruct(int x, int y) {
		Go.logger.debug("Destruction en cours !");
		
		for(int i = x ; i <= x+1 ; i++) {
			for(int j = y ; j <= y+1 ; j++) {
				if(goban.existPierre(i, j)) {
					goban.removePierreChaine(goban.getPierre(i, j));
					removePierre(goban.getPierre(i, j));
				}
			}
		}
		
		Go.logger.debug("Destruction terminée !");
	}
}

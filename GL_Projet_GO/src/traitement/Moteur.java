package traitement;

import java.util.ArrayList;

import donnees.AbstractPierre;
import donnees.Cercle;
import donnees.Coordonnee;
import donnees.Couleur;
import donnees.Goban;
import donnees.MegaPierre;
import donnees.ParametrePartie;
import donnees.Pierre;
import gui.Go;

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
	private GoPierre gopierre;
	private Didacticiel didacticiel;
	
	private AbstractPierre Ko = null;
	private int Ko_compteur = 0;
	private boolean compteur = false;
	
	private Joueur[] joueurs;
	private ArrayList<Cercle> cercle;
	private Cercle survole;
	
	private boolean noir = true;
	private boolean blanc = false;
	private boolean rouge = false;
	private boolean isMegaPierre = false;
	private boolean didacticiel_fini = false;
	private boolean suicide = false;
	
	public Moteur(int cellule, int ecart_window, int taille_goban, int nb_joueur, int nb_ordi, boolean didacticiel) {
		this.cellule = cellule;
		this.ecart_window = ecart_window;
		this.taille_goban = taille_goban;
		nb_joueurs = nb_joueur + nb_ordi;
		
		goban = new Goban(taille_goban);
		gopierre = new GoPierre();
		joueurs = new Joueur[nb_joueurs];
		cercle = new ArrayList<Cercle>();

		initJoueur();
		
		this.didacticiel = null;
		
		if(didacticiel) {
			this.didacticiel = new Didacticiel(taille_goban, this);
			
			Go.logger.debug("D�but du Didacticiel");
			
			this.didacticiel.chargeLevel();
		}
	}
	
	public void reinitGoban(int taille_goban) {
		goban.initPlateau(taille_goban);
		setIsMegaPierre(false);
		cercle.clear();
		changeJoueur();
	}
	
	public void initJoueur() {
		joueurs[0] = new Joueur(Couleur.NOIR, false);
		Go.logger.debug("Initialisation du joueur NOIR");
		
		joueurs[1] = new Joueur(Couleur.BLANC, false);
		Go.logger.debug("Initialisation du joueur BLANC");
		
		if(nb_joueurs == 3) {
			joueurs[2] = new Joueur(Couleur.ROUGE, false);
			Go.logger.debug("Initialisation du joueur ROUGE");
		}
	}
	
	public boolean isDidacticielFini() {
		return didacticiel_fini;
	}
	
	public void incrementeKoCompteur() {
		if(compteur) {
			Ko_compteur++;
			if(Ko_compteur == nb_joueurs) {
				setKo(null);
				compteur = false;
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
	
	public void setSuicide(boolean bool) {
		suicide = bool;
	}
	
	public void setKo (AbstractPierre pierre) {
		Ko = pierre;
	}
	
	public AbstractPierre getKo () {
		return Ko;
	}
	
	public ArrayList<Cercle> getCercleList(){
		return cercle;
	}
	
	public Cercle getSurvoleCercle() {
		return survole;
	}
	
	public Couleur currentCouleur() {
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
		int x = (coordY - ecart_window / 2) / cellule;
		int y = (coordX - ecart_window / 2) / cellule;
		
		if(taille_goban == ParametrePartie.TAILLE_GOBAN[1]) {
			x = (coordY - (int)(ecart_window / 1.5)) / cellule;
			y = (coordX - (int)(ecart_window / 1.5)) / cellule;
		}
		
		Couleur couleur;
		
		if((x >= 0) && (x < taille_goban) && (y >= 0) && (y < taille_goban) && (!goban.existPierre(x, y))) {
			couleur = currentCouleur();
			
			if(isMegaPierre) {
				if(x == taille_goban - 1) {
					x--;
				}
				if(y == taille_goban - 1) {
					y--;
				}
			}
			
			if(isMegaPierre && currentJoueur().hasMegaPierre() && (x < taille_goban - 1) && (y < taille_goban - 1)) {
				if(!goban.existPierre(x+1, y) && !goban.existPierre(x, y+1) && !goban.existPierre(x+1, y+1)) {
					survole = new Cercle(new Coordonnee(x, y), couleur, true);
				}
			}
			else {
				survole = new Cercle(new Coordonnee(x, y), couleur, false);
			}
		}
		
		else {
			survole = null;
		}
	}
	
	public void clicEvent(int coordX, int coordY) {
		long startTime = System.currentTimeMillis();
		
		int x = (coordY - ecart_window / 2) / cellule;
		int y = (coordX - ecart_window / 2) / cellule;
		
		if(taille_goban == ParametrePartie.TAILLE_GOBAN[1]) {
			x = (coordY - (int)(ecart_window / 1.5)) / cellule;
			y = (coordX - (int)(ecart_window / 1.5)) / cellule;
		}
		
		if((x >= 0) && (x < taille_goban) && (y >= 0) && (y < taille_goban)) {
			if(!goban.existPierre(x, y)){
				if(isMegaPierre) {
					if(x == taille_goban - 1) {
						x--;
					}
					if(y == taille_goban - 1) {
						y--;
					}
				}
				
				Coordonnee c = new Coordonnee(x, y);
				
				if(!isMegaPierre) {
					AbstractPierre pierre = new Pierre(currentCouleur(), c);
					
					addPierre(pierre);
					currentJoueur().addPierre(pierre);
					
					Go.logger.info("Ajout d'une Pierre de coordonn�es (" + pierre.getX() + ", " + pierre.getY() + ") au joueur " + currentJoueur().getCouleur());
				}
				else {
					if((x < taille_goban - 1) && (y < taille_goban - 1)) {
						if(!goban.existPierre(x+1, y) && !goban.existPierre(x, y+1) && !goban.existPierre(x+1, y+1)) {
							AbstractPierre pierre = new MegaPierre(currentCouleur(), c);
							
							addPierre(pierre);
							currentJoueur().addPierre(pierre);

							if(!suicide) {
								currentJoueur().playMegaPierre();

								Go.logger.info("Ajout d'une MegaPierre de coordonn�es (" + pierre.getX() + ", " + pierre.getY() + ") au joueur " + currentJoueur().getCouleur());
							}
						}
					}
				}
				if(!suicide) {
					changeJoueur();
					incrementeKoCompteur();
				}
			}
		}
		setIsMegaPierre(false);
		setSuicide(false);
		changeLevel();
		
		Go.logger.info("Temps pour jouer un coup: " + (System.currentTimeMillis() - startTime));
	}
	
	public void addPierre(AbstractPierre pierre) {
		goban.addPierre(pierre);
		Go.logger.info("Pierre cr��e aux coordonn�es : (" + pierre.getX() + ", " + pierre.getY() + ")");
		
		Coordonnee coordPierre = new Coordonnee(pierre.getX(), pierre.getY());
		
		cercle.add(new Cercle(coordPierre, pierre.getCouleur(), isMegaPierre));
		Go.logger.info("Cercle cr�� aux coordonn�es : (" + pierre.getX() + ", " + pierre.getY() + ")");
		
		ArrayList<AbstractPierre> voisin = gopierre.voisins(goban.getPierre(pierre.getX(), pierre.getY()), goban.getPlateau(), taille_goban);
		
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
				compteur = true;
				
				currentJoueur().addScore(goban.getScoreCapture());
				Go.logger.info("Ajout de " + goban.getScoreCapture() + " points au joueur de couleur " + currentJoueur().getCouleur());
			}
				
		}
		
		if(pierre.hasChaine()) {
			if(goban.isSuicide(goban.getChaine(pierre.getNomChaine()))) {
				Go.logger.info("Suicide Chaine : Suppression de la pierre de coordonn�es (" + pierre.getX() + ", " + pierre.getY() + ")");
				removePierre(pierre);
				setSuicide(true);
			}
		}
		else {
			if(goban.isSuicide(pierre)) {
				Go.logger.info("Suicide : Suppression de la pierre de coordonn�es (" + pierre.getX() + ", " + pierre.getY() + ")");
				removePierre(pierre);
				setSuicide(true);
			}
		}
	}
	
	public void removePierre(AbstractPierre pierre) {
		if(goban.existPierre(pierre.getX(), pierre.getY())) {
			Go.logger.info("Pierre supprim�e aux coordonn�es : (" + pierre.getX() + ", " + pierre.getY() + ")");
			goban.removePierre(pierre);

			Go.logger.info("Pierre de coordonn�es (" + pierre.getX() + ", " + pierre.getY() + ") retir�e au joueur " + currentJoueur().getCouleur());
			currentJoueur().removePierre(pierre);
			
			Cercle c = null;
			
			for(Cercle coordCercle : cercle) {
				if((pierre.getX() == coordCercle.getX()) && (pierre.getY() == coordCercle.getY())) {
					c = coordCercle;
				}
			}

			Go.logger.info("Cercle supprim� aux coordonn�es : (" + pierre.getX() + ", " + pierre.getY() + ")");
			cercle.remove(c);
		}
	}
	
	public void removePierre(ArrayList<AbstractPierre> chaine) {
		for(AbstractPierre pierre : chaine) {
			removePierre(pierre);
		}
	}
}

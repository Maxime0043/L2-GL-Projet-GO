package traitement.moteurs;

import donnees.AbstractPierre;
import donnees.Couleur;
import donnees.Joueur;
import gui.Go;
import traitement.Goban;

/**
 * Cette classe permet la gestion des joueurs de la partie avec la classe {@link Joueur}.
 * 
 * @author Maxime, Micael et Houssam
 *
 */
public class MoteurJoueur {

	private Goban goban;
	private Joueur[] joueurs;
	
	private int nb_joueur;
	private int nb_joueurs;
	
	private boolean noir = true;
	private boolean blanc = false;
	private boolean rouge = false;
	private boolean isDidacticiel;
	
	/**
	 * Pour cr�er le moteur de joueur il nous faut la classe permettant de g�rer le plateau,
	 * le nombre de joueurs (humain), le nombre d'ordinateurs et on doit savoir 
	 * si on est en partie ou dans le didacticiel.
	 * 
	 * @param goban D�finit la classe qui permet de g�rer le plateau du jeu.
	 * @param nb_joueur D�finit le nombre de joueurs (humain).
	 * @param nb_ordi D�finit le nombre d'ordinateurs.
	 * @param isDidacticiel D�finit si on est dans le didacticiel.
	 */
	public MoteurJoueur(Goban goban, int nb_joueur, int nb_ordi, boolean isDidacticiel) {
		this.goban = goban;
		nb_joueurs = nb_joueur + nb_ordi;
		joueurs = new Joueur[nb_joueurs];
		
		this.nb_joueur = nb_joueur;
		this.isDidacticiel = isDidacticiel;
		initJoueur();
	}
	
	/**
	 * Permet d'initialiser les joueurs de la partie.
	 */
	private void initJoueur() {
		int i;
		
		for(i = 0 ; i < nb_joueur ; i++) {
			joueurs[i] = new Joueur(Couleur.getCouleurs()[i], false);
			Go.logger.debug("Initialisation du joueur " + Couleur.getCouleurs()[i]);
		}
		
		for(int j = i ; j < nb_joueurs ; j++) {
			joueurs[j] = new Joueur(Couleur.getCouleurs()[j], true);	
			Go.logger.debug("Initialisation du joueur " + Couleur.getCouleurs()[j]);		
		}
	}
	
	public Joueur[] getJoueurs(){
		return joueurs;
	}
	
	/**
	 * Permet de savoir si le joueur courant poss�de une m�ga-pierre ou non.
	 * 
	 * @return Indique si le joueur courant poss�de une m�ga-pierre.
	 */
	public boolean canPlayMegaPierre() {
		return currentJoueur().hasMegaPierre();
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
	
	/**
	 * Permet de r�cup�rer le joueur courant.
	 * 
	 * @return Renvoie le joueur courant.
	 */
	public Joueur currentJoueur() {
		Joueur j = null;
		
		for(int i = 0 ; i < nb_joueurs ; i++) {
			if(joueurs[i].getCouleur().equals(currentCouleur())) {
				j = joueurs[i];
			}
		}
		
		return j;
	}
	
	/**
	 * Permet de r�cup�rer un joueur gr�ce � une couleur donn�e.
	 * 
	 * @param couleur D�finit la couleur du joueur recherch�.
	 * @return Renvoie le joueur ayant la couleur fournie.
	 */
	public Joueur getJoueur(Couleur couleur) {
		Joueur j = null;
		
		for(int i = 0 ; i < nb_joueurs ; i++) {
			if(joueurs[i].getCouleur().equals(couleur)) {
				j = joueurs[i];
			}
		}
		
		return j;
	}
	
	/**
	 * Permet de passer au joueur suivant.
	 */
	public void changeJoueur() {
		if(isDidacticiel) {
			noir = true;
			blanc = false;
		}
		
		else {
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
		}
	}
	
	/**
	 * Permet de revenir au joueur pr�c�dent.
	 */
	public void joueurPrecedent() {
		if(noir) {
			noir = false;
			
			if(nb_joueurs > 2) {
				rouge = true;
			}
			else {
				blanc = true;
			}
		}
		
		else if(blanc) {
			blanc = false;
			noir = true;
		}
		
		else if(rouge) {
			rouge = false;
			blanc = true;
		}
	}
	
	/**
	 * Permet de r�cup�rer l'ensemble des scores de chaque joueur.
	 * 
	 * @return Renvoie les scores des joueurs sous forme de tableau.
	 */
	public double[] getScores() {
		double[] scores = new double[nb_joueurs];
		
		for(int i = 0 ; i < nb_joueurs ; i++) {
			scores[i] = getJoueurs()[i].getScore();
		}
		
		return scores;
	}
	
	/**
	 * Permet de mettre � jour chaque pierre et m�ga-pierre que les joueurs
	 * ont pos� sur le plateau.
	 */
	public void updateJoueurs() {
		int nom;
		
		for(int i = 0 ; i < nb_joueurs ; i++) {
			System.out.println("Taille 1 Joueur " + getJoueurs()[i].getCouleur() + ": " + getJoueurs()[i].getListePierre().size());
			for(AbstractPierre pierre : getJoueurs()[i].getListePierre()) {
				if(goban.existPierre(pierre.getX(), pierre.getY())) {
					nom = goban.getPierre(pierre.getX(), pierre.getY()).getNomChaine();
					pierre.setNomChaine(nom);
				}
			}
		}
	}
}

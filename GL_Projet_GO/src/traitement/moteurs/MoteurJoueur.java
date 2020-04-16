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
	 * Pour créer le moteur de joueur il nous faut la classe permettant de gérer le plateau,
	 * le nombre de joueurs (humain), le nombre d'ordinateurs et on doit savoir 
	 * si on est en partie ou dans le didacticiel.
	 * 
	 * @param goban Définit la classe qui permet de gérer le plateau du jeu.
	 * @param nb_joueur Définit le nombre de joueurs (humain).
	 * @param nb_ordi Définit le nombre d'ordinateurs.
	 * @param isDidacticiel Définit si on est dans le didacticiel.
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
	 * Permet de savoir si le joueur courant possède une méga-pierre ou non.
	 * 
	 * @return Indique si le joueur courant possède une méga-pierre.
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
	 * Permet de récupérer le joueur courant.
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
	 * Permet de récupérer un joueur grâce à une couleur donnée.
	 * 
	 * @param couleur Définit la couleur du joueur recherché.
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
	 * Permet de revenir au joueur précédent.
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
	 * Permet de récupérer l'ensemble des scores de chaque joueur.
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
	 * Permet de mettre à jour chaque pierre et méga-pierre que les joueurs
	 * ont posé sur le plateau.
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

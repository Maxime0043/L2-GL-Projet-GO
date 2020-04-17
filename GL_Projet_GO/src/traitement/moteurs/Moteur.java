package traitement.moteurs;

import java.util.ArrayList;

import donnees.Cercle;
import donnees.Coordonnee;
import donnees.Couleur;
import donnees.Joueur;
import gui.Go;
import traitement.Didacticiel;
import traitement.FinDePartie;
import traitement.Goban;

/**
 * Cette classe est la classe de traitement principale.
 * Elle permet d'accéder à l'ensemble des sous-moteurs
 * {@link MoteurJoueur}, {@link MoteurOrdi} et {@link MoteurPierre}.
 * 
 * @author Maxime, Micael et Houssam
 *
 */
public class Moteur /*implements Runnable*/ {
	private Goban goban;
	private Didacticiel didacticiel;
	private MoteurJoueur moteur_joueur;
	private MoteurOrdi moteur_ordi;
	private MoteurPierre moteur_pierre;
	private FinDePartie fin;

	private int nb_joueurs;
	private int pass_compteur = 0;
	private int x = -1, y = -1;
	
	private String description;

	private ArrayList<Cercle> position_jouable;
	private Moteur instance = this;
	
	private boolean didacticiel_fini = false;
	private boolean is_fin_partie = false;
	
	private String info_texte;
	
	/**
	 * Pour créer le moteur du jeu nous aurons besoin de plusieurs variables.
	 * 
	 * @param taille_goban Définit la taille du goban.
	 * @param nb_joueur Définit le nombre de joueurs (humain) dans la partie.
	 * @param nb_ordi Définit le nombre d'ordinateurs dans la partie.
	 * @param isDidacticiel Définit si le didacticiel est actif.
	 */
	public Moteur(int cellule, int taille_goban, int nb_joueur, int nb_ordi, boolean isDidacticiel) {
		goban = new Goban(taille_goban);
		moteur_joueur = new MoteurJoueur(goban, nb_joueur, nb_ordi, isDidacticiel);
		moteur_pierre = new MoteurPierre(moteur_joueur, goban, taille_goban, nb_joueur, nb_ordi, isDidacticiel);
		fin = new FinDePartie(taille_goban, goban, moteur_joueur, moteur_pierre);
		
		if(nb_ordi > 0) {
			moteur_ordi = new MoteurOrdi(instance, moteur_joueur, moteur_pierre, goban, taille_goban, 2);
		}
		
		nb_joueurs = nb_joueur + nb_ordi; 
		
		didacticiel = null;
		position_jouable = null;
		
		if(isDidacticiel) {
			didacticiel = new Didacticiel(this, moteur_pierre);
			
			Go.logger.debug("Début du Didacticiel");
			
			position_jouable = new ArrayList<Cercle>();
			didacticiel.chargeLevel();
		}
		
		initCoord();
		
//		Thread moteurThread = new Thread(instance);
//		moteurThread.start();
	}
	
	/**
	 * Définit ce qu'il faut faire pour le joueur courant : il fait jouer l'ordinateur si c'est son tour,
	 * sinon il attend d'avoir les coordonnées de clique de l'utilisateur.
	 */
	public void run() {
		if(currentJoueur().isOrdi()) {
			long startTime = System.currentTimeMillis();
			
			System.out.println("-------Ordi Début--------" + moteur_joueur.currentCouleur() + "\n");
			moteur_ordi.jouer();
			System.out.println("\n-------Ordi Fin--------");
			
			System.out.println("Temps ordi: " + (System.currentTimeMillis() - startTime) + "\n\n");
		}
		
		else {
			if(x != -1 && y != -1) {
				clicEvent(x, y);
				initCoord();
			}
		}
	}
	
	/**
	 * Réinitialise totalement le goban et les joueurs.
	 */
	public void reinitGoban() {
		goban.initPlateau();
		setPoseMegaPierre(false);
		getCercleList().clear();
		changeJoueur();
	}
	
	/**
	 * Initialise le nombre de fois où les joueurs passent consécutivement leur tour.
	 */
	public void initPassCompteur() {
		pass_compteur = 0;
	}
	
	/**
	 * Permet à un joueur de passer son tour ou de finir la partie.
	 */
	public void passer() {
		System.out.println("Le joueur " + currentCouleur() + " vient de passer");
		
		initInfoTexte();		
		moteur_joueur.changeJoueur();
		pass_compteur++;
		
		if(pass_compteur == nb_joueurs) {
			initPassCompteur();
			fin.initFin(goban.getPlateau(), goban.getHmChaine());
			is_fin_partie = true;
		}
	}
	
	private void initInfoTexte() {
		info_texte = "Le Joueur " + moteur_joueur.currentCouleur() + " vient de passer son tour";
	}
	
	public String getJoueurPasse() {
		if(pass_compteur > 0) {
			return info_texte;
		}
		
		return "";
	}
	
	public boolean isFinPartie() {
		return is_fin_partie;
	}
	
	public String texteFinPartie() {
		Joueur gagnant = moteur_joueur.getMeilleurJoueur();
		
		return "Bravo au joueur " + gagnant.getCouleur() + " qui remporte la partie avec un score de " + gagnant.getScore() + ".\nLes territoires de chaque joueur sont affichées par cercles\nde la même couleur de chaque joueurs.";
	}
	
	/**
	 * Permet de définir les coordonnées de la zone survolée avec la souris.
	 * 
	 * @param x
	 * @param y
	 */
	public void survoleZone(int x, int y) {
		moteur_pierre.survoleZone(x, y);
	}
	
	/**
	 * Permet d'initialiser les coordonnées pour l'ajout de pierres.
	 */
	private void initCoord() {
		x = -1;
		y = -1;
	}
	
	public void setCoord(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Permet d'ajouter, si possible, une pierre ou une méga-pierre sur le plateau.
	 * 
	 * @param x Ligne à laquelle la pierre va être ajoutée.
	 * @param y Colonne à laquelle la pierre va être ajoutée.
	 */
	public void clicEvent(int x, int y) {
		moteur_pierre.clicEvent(x, y);
		initPassCompteur();
	}
	
	/**
	 * Permet de changer le niveau du didacticiel.
	 * 
	 * @param suivant Définit si on accède au niveau suivant ou précédent.
	 */
	public void changeLevel(boolean suivant) {
		if(didacticiel != null) {
			position_jouable.clear();
			
			if(!suivant) {
				didacticiel.changeLevel(suivant);
			}
			
			else if(didacticiel.getLevel() < didacticiel.getNbLevels()) {
				didacticiel.changeLevel(suivant);
			}
			
			else {
				didacticiel_fini = true;
				position_jouable = null;
			}
		}
	}
	
	/**
	 * Permet de recharger le niveau courant.
	 */
	public void resetLevel() {
		if(didacticiel != null) {
			position_jouable.clear();
			
			didacticiel.resetLevel();
		}
	}
	
	/**
	 * Permier de définir les intersections jouables dans le didacticiel.
	 * 
	 * @param coord Définit la coordonnée d'une position jouable.
	 * @param couleur Définit la couleur qu'aura le cercle quand il sera affiché.
	 * @param isMegaPierre Définit si le cercle sera de la taille d'une méga-pierre.
	 */
	public void initPositionJouable(Coordonnee coord, Couleur couleur, boolean isMegaPierre) {
		position_jouable.add(new Cercle(coord, couleur, isMegaPierre));		
	}
	
	public FinDePartie getFin(){
		return fin;
	}
	
	public double[] getScores() {
		return moteur_joueur.getScores();
	}
	
	public ArrayList<Coordonnee> getHoshis() {
		return goban.getHoshis();
	}
	
	public ArrayList<Cercle> getPositionJouable() {
		return position_jouable;
	}
	
	public void setPositionJouable() {
		moteur_pierre.setPositionJouable(position_jouable);
	}
	
	public boolean isDidacticielFini() {
		return didacticiel_fini;
	}
	
	public int getCurrentLevel() {
		return didacticiel.getLevel();
	}
	
	public int getNbLevel() {
		return didacticiel.getNbLevels();
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String desc) {
		description = desc;
	}
	
	public ArrayList<Cercle> getCercleList(){
		return moteur_pierre.getCercleList();
	}
	
	public Coordonnee getSurvoleCercle() {
		return moteur_pierre.getSurvoleCercle();
	}
	
	public Coordonnee getSurvoleCroix() {
		return moteur_pierre.getSurvoleCroix();
	}
	
	public Couleur currentCouleur() {
		return moteur_joueur.currentCouleur();
	}
	
	public Joueur[] getJoueurs(){
		return moteur_joueur.getJoueurs();
	}
	
	public Joueur currentJoueur() {
		return moteur_joueur.currentJoueur();
	}
	
	public void changeJoueur() {
		moteur_joueur.changeJoueur();
	}
	
	public boolean isMegaPierre() {
		return moteur_pierre.isMegaPierre();
	}
	
	public void setPoseMegaPierre(boolean bool) {
		moteur_pierre.setPoseMegaPierre(bool);
	}
	
	public boolean canPlayMegaPierre() {
		return moteur_joueur.canPlayMegaPierre();
	}
}

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
 * Elle permet d'acc�der � l'ensemble des sous-moteurs
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
//	private boolean is_tour_ordi = false;
//	private Moteur instance = this;
	
	/**
	 * Pour cr�er le moteur du jeu nous aurons besoin de plusieurs variables.
	 * 
	 * @param taille_goban D�finit la taille du goban.
	 * @param nb_joueur D�finit le nombre de joueurs (humain) dans la partie.
	 * @param nb_ordi D�finit le nombre d'ordinateurs dans la partie.
	 * @param isDidacticiel D�finit si le didacticiel est actif.
	 */
	public Moteur(int cellule, int taille_goban, int nb_joueur, int nb_ordi, boolean isDidacticiel) {
		goban = new Goban(taille_goban);
		moteur_joueur = new MoteurJoueur(nb_joueur, nb_ordi, isDidacticiel);
		moteur_pierre = new MoteurPierre(moteur_joueur, goban, taille_goban, nb_joueur, nb_ordi, isDidacticiel);
		fin = new FinDePartie(taille_goban, goban, moteur_joueur, moteur_pierre);
		
		if(nb_ordi > 0) {
			moteur_ordi = new MoteurOrdi(instance, moteur_joueur, moteur_pierre, goban, taille_goban, 1);
		}
		
		nb_joueurs = nb_joueur + nb_ordi; 
		
		didacticiel = null;
		position_jouable = null;
		
		if(isDidacticiel) {
			didacticiel = new Didacticiel(this, moteur_pierre);
			
			Go.logger.debug("D�but du Didacticiel");
			
			position_jouable = new ArrayList<Cercle>();
			didacticiel.chargeLevel();
		}
		
		initCoord();
		
//		Thread moteurThread = new Thread(instance);
//		moteurThread.start();
	}
	
	/**
	 * D�finit ce qu'il faut faire pour le joueur courant : il fait jouer l'ordinateur si c'est son tour,
	 * sinon il attend d'avoir les coordonn�es de clique de l'utilisateur.
	 */
//	@Override
	public void run() {
//		boolean isRunning = true;
//		
//		while (isRunning) {
//			try {
//				Thread.sleep(ParametrePartie.FPS);
//				
//			} catch (InterruptedException e) {
//				System.out.println(e.getMessage());
//			}
//
//			if (isRunning) {
				if(currentJoueur().isOrdi()) {
					long startTime = System.currentTimeMillis();
					
					System.out.println("-------Ordi D�but--------" + moteur_joueur.currentCouleur() + "\n");
					moteur_ordi.jouer();
					System.out.println("\n-------Ordi Fin--------");
					
//					moteur_pierre.addPierre(moteur_ordi.getCoup());
					
					System.out.println("Temps ordi: " + (System.currentTimeMillis() - startTime) + "\n\n");
				}
				
				else {
					if(x != -1 && y != -1) {
						clicEvent(x, y);
						initCoord();
					}
				}
//			}
//		}
	}
	
	/**
	 * R�initialise totalement le goban et les joueurs.
	 */
	public void reinitGoban() {
		goban.initPlateau();
		setPoseMegaPierre(false);
		getCercleList().clear();
		changeJoueur();
	}
	
	/**
	 * Initialise le nombre de fois o� les joueurs passent cons�cutivement leur tour.
	 */
	private void initPassCompteur() {
		pass_compteur = 0;
	}
	
	/**
	 * Permet � un joueur de passer son tour ou de finir la partie.
	 */
	public void passer() {
		moteur_joueur.changeJoueur();
		pass_compteur++;
		
		if(pass_compteur == nb_joueurs) {
			initPassCompteur();
			fin.initFin(goban.getPlateau(), goban.getHmChaine());
			System.out.println("Fini");
		}
	}
	
//	public void tourOrdi() {
//		if(!is_tour_ordi) {
//			System.out.println("-------Ordi D�but--------");
//			is_tour_ordi = true;
//			moteur_pierre.setTourOrdi(true);
//		}
//		
//		else {
//			System.out.println("-------Ordi Fin--------");
//			is_tour_ordi = false;
//			moteur_pierre.setTourOrdi(false);
//		}
//	}
	
	/**
	 * Permet de d�finir les coordonn�es de la zone survol�e avec la souris.
	 * 
	 * @param x
	 * @param y
	 */
	public void survoleZone(int x, int y) {
		moteur_pierre.survoleZone(x, y);
	}
	
	/**
	 * Permet d'initialiser les coordonn�es pour l'ajout de pierres.
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
	 * Permet d'ajouter, si possible, une pierre ou une m�ga-pierre sur le plateau.
	 * 
	 * @param x Ligne � laquelle la pierre va �tre ajout�e.
	 * @param y Colonne � laquelle la pierre va �tre ajout�e.
	 */
	public void clicEvent(int x, int y) {
		moteur_pierre.clicEvent(x, y);
		initPassCompteur();
	}
	
	/**
	 * Permet de changer le niveau du didacticiel.
	 * 
	 * @param suivant D�finit si on acc�de au niveau suivant ou pr�c�dent.
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
	 * Permier de d�finir les intersections jouables dans le didacticiel.
	 * 
	 * @param coord D�finit la coordonn�e d'une position jouable.
	 * @param couleur D�finit la couleur qu'aura le cercle quand il sera affich�.
	 * @param isMegaPierre D�finit si le cercle sera de la taille d'une m�ga-pierre.
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

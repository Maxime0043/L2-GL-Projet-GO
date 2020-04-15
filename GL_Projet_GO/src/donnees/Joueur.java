package donnees;

import java.util.ArrayList;

/**
 * Cette classe va d�finir les diff�rents joueurs
 * et ordinateur qui vont jouer au jeu de go
 * 
 * @author Maxime, Micael et Houssam
 *
 */
public class Joueur {
	
	private Score score;
	private Couleur couleur;
	private boolean isOrdi;
	private int nb_megaPierre;
	
	private ArrayList<AbstractPierre> listePierre;
	
	/**
	 * Pour cr�er un joueur on aura besoin de lui
	 * affecter une couleur et de d�finir si c'est
	 * un ordinateur ou non
	 * 
	 * @param c Repr�sente la couleur que poss�dera le joueur
	 * @param isOrdi D�finit si le joueur est un ordinateur ou non
	 */
	public Joueur(Couleur c, boolean isOrdi) {
		score = new Score();
		couleur = c;
		this.isOrdi = isOrdi;
		
		listePierre = new ArrayList<AbstractPierre>();
		
		initNbMegaPierre();
	}
	
	public int getScore() {
		return score.getScore();
	}
	
	public void initScore() {
		score.initScore();
	}
	
	public void addScore(int nombre) {
		score.addScore(nombre);
	}
	
	public void setScore(int nombre) {
		score.setScore(nombre);
	}
	
	public Couleur getCouleur() {
		return couleur;
	}
	
	public boolean isOrdi() {
		return isOrdi;
	}
	
	public void initNbMegaPierre() {
		nb_megaPierre = 1;
	}
	
	public boolean hasMegaPierre() {
		return nb_megaPierre > 0;
	}
	
	public void playMegaPierre() {
		nb_megaPierre--;
	}
	
	/**
	 * Permet d'ajouter une pierre / m�ga-pierre au joueur
	 * 
	 * @param pierre D�signe la pierre / m�ga-pierre qui va �tre ajout�e
	 */
	public void addPierre(AbstractPierre pierre) {
		if(!listePierre.contains(pierre)) {
			listePierre.add(pierre);
		}
	}
	
	/**
	 * Permet de supprimer une pierre / m�ga-pierre au joueur
	 * 
	 * @param pierreD�signe la pierre / m�ga-pierre qui va �tre supprim�e
	 */
	public void removePierre(AbstractPierre pierre) {
		if(listePierre.contains(pierre)) {
			listePierre.remove(pierre);
		}
	}
	
	public ArrayList<AbstractPierre> getListePierre(){
		return listePierre;
	}
}

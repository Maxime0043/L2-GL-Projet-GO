package donnees;

import java.util.ArrayList;

/**
 * Cette classe va définir les différents joueurs
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
	 * Pour créer un joueur on aura besoin de lui
	 * affecter une couleur et de définir si c'est
	 * un ordinateur ou non
	 * 
	 * @param c Représente la couleur que possédera le joueur
	 * @param isOrdi Définit si le joueur est un ordinateur ou non
	 */
	public Joueur(Couleur c, boolean isOrdi) {
		score = new Score();
		couleur = c;
		this.isOrdi = isOrdi;
		
		listePierre = new ArrayList<AbstractPierre>();
		
		initNbMegaPierre();
	}
	
	public double getScore() {
		return score.getScore();
	}
	
	public void initScore() {
		score.initScore();
	}
	
	public void addScore(double nombre) {
		score.addScore(nombre);
	}
	
	public void setScore(double nombre) {
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
	 * Permet d'ajouter une pierre / méga-pierre au joueur
	 * 
	 * @param pierre Désigne la pierre / méga-pierre qui va être ajoutée
	 */
	public void addPierre(AbstractPierre pierre) {
		if(!listePierre.contains(pierre)) {
			listePierre.add(pierre);
		}
	}
	
	/**
	 * Permet de supprimer une pierre / méga-pierre au joueur
	 * 
	 * @param pierreDésigne la pierre / méga-pierre qui va être supprimée
	 */
	public void removePierre(AbstractPierre pierre) {
		AbstractPierre p = null;
		
		for(AbstractPierre pierre_liste : listePierre) {
			if(pierre_liste.getX() == pierre.getX() && pierre_liste.getY() == pierre.getY()) {
				p = pierre_liste;
			}
		}
		
		if(p != null) {
			listePierre.remove(p);
		}
	}
	
	public ArrayList<AbstractPierre> getListePierre(){
		return listePierre;
	}
}

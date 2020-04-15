package donnees;

import java.util.ArrayList;

/**
 * Cette classe représente un groupe d'AbstractPierre.
 * 
 * @author Maxime, Micael et Houssam
 *
 */
public class Chaine {
	
	private boolean twoEyes;
	private ArrayList<AbstractPierre> chaine;
	
	public Chaine() {
		chaine = new ArrayList<AbstractPierre>();
	}
	
	public void setTwoEyes(boolean b) {
		twoEyes = b;
	}
	
	public boolean getTwoEyes() {
		return twoEyes;
	}
	
	public Couleur getCouleur() {
		return chaine.get(0).getCouleur();
	}
	
	public ArrayList<AbstractPierre> getChaine() {
		return chaine;
	}
	
	/**
	 * Permet d'ajouter une pierre / méga-pierre à la chaine.
	 * 
	 * @param pierre Pierre / Méga-pierre qui sera ajoutée à la chaine.
	 */
	public void addPierre(AbstractPierre pierre) {
		if(!chaine.contains(pierre)) {
			chaine.add(pierre);
		}
	}
	
	/**
	 * Permet de supprimer une pierre / méga-pierre de la chaine.
	 * 
	 * @param pierre Pierre / Méga-pierre qui sera supprimée de la chaine.
	 */
	public void removePierre(AbstractPierre pierre) {
		if(chaine.contains(pierre)) {
			chaine.remove(pierre);
		}
	}
}
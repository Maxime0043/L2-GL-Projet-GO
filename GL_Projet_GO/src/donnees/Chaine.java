package donnees;

import java.util.ArrayList;

/**
 * Cette classe repr�sente un groupe d'AbstractPierre.
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
	 * Permet d'ajouter une pierre / m�ga-pierre � la chaine.
	 * 
	 * @param pierre Pierre / M�ga-pierre qui sera ajout�e � la chaine.
	 */
	public void addPierre(AbstractPierre pierre) {
		if(!chaine.contains(pierre)) {
			chaine.add(pierre);
		}
	}
	
	/**
	 * Permet de supprimer une pierre / m�ga-pierre de la chaine.
	 * 
	 * @param pierre Pierre / M�ga-pierre qui sera supprim�e de la chaine.
	 */
	public void removePierre(AbstractPierre pierre) {
		if(chaine.contains(pierre)) {
			chaine.remove(pierre);
		}
	}
}
package donnees;

/**
 * Cette classe représente les coordonnées que
 * possèderont les pierres / méga-pierres du plateau.
 * 
 * @author Maxime, Micael et Houssam
 *
 */
public class Coordonnee {
	private int x;
	private int y;
	
	/**
	 * Pour créer une coordonnée on a besoin
	 * d'un entier qui définit la ligne du
	 * plateau auquel sera affectée la pierre / 
	 * méga-pierre et d'un entier qui définit
	 * la colonne du plateau
	 * 
	 * @param x Réprésente la ligne du plateau
	 * @param y Réprésente la colonne du plateau
	 */
	public Coordonnee(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public String toString () {
		return "(" + this.getX() + ", " + this.getY() + ")";
	}
}

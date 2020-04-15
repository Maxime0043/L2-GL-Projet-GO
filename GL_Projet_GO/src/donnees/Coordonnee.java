package donnees;

/**
 * Cette classe repr�sente les coordonn�es que
 * poss�deront les pierres / m�ga-pierres du plateau.
 * 
 * @author Maxime, Micael et Houssam
 *
 */
public class Coordonnee {
	private int x;
	private int y;
	
	/**
	 * Pour cr�er une coordonn�e on a besoin
	 * d'un entier qui d�finit la ligne du
	 * plateau auquel sera affect�e la pierre / 
	 * m�ga-pierre et d'un entier qui d�finit
	 * la colonne du plateau
	 * 
	 * @param x R�pr�sente la ligne du plateau
	 * @param y R�pr�sente la colonne du plateau
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

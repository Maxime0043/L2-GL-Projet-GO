package donnees;

/**
 * Cette classe repr�sente les �l�ments qui vont
 * �tre affich�s sur l'IHM graphique d�finissant
 * les pierres et m�ga-pierres.
 * 
 * @author Maxime, Micael et Houssam
 *
 */
public class Cercle {
	Coordonnee coord;
	Couleur couleur;
	boolean isMegaPierre;
	
	/**
	 * Pour cr�er un cercle on aura de sp�cifier sa coordon�e,
	 * sa couleur ainsi que de d�finir si il repr�sente une
	 * m�ga-pierre
	 * 
	 * @param coord La coordonn�e qui d�finit la position du cercle
	 * @param couleur La couleur que poss�dera le cercle  
	 * @param isMegaPierre D�finit si le cercle repr�sente une m�ga-pierre ou non
	 */
	public Cercle(Coordonnee coord, Couleur couleur, boolean isMegaPierre) {
		this.coord = coord;
		this.couleur = couleur;
		this.isMegaPierre = isMegaPierre;
	}
	
	public int getX() {
		return coord.getX();
	}
	
	public int getY() {
		return coord.getY();
	}
	
	public Couleur getCouleur() {
		return couleur;
	}
	
	public boolean getIsMegaPierre() {
		return isMegaPierre;
	}
}

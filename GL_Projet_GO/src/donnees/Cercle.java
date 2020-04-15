package donnees;

/**
 * Cette classe représente les éléments qui vont
 * être affichés sur l'IHM graphique définissant
 * les pierres et méga-pierres.
 * 
 * @author Maxime, Micael et Houssam
 *
 */
public class Cercle {
	Coordonnee coord;
	Couleur couleur;
	boolean isMegaPierre;
	
	/**
	 * Pour créer un cercle on aura de spécifier sa coordonée,
	 * sa couleur ainsi que de définir si il représente une
	 * méga-pierre
	 * 
	 * @param coord La coordonnée qui définit la position du cercle
	 * @param couleur La couleur que possédera le cercle  
	 * @param isMegaPierre Définit si le cercle représente une méga-pierre ou non
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

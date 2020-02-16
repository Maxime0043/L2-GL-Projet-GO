package donnees;

public class Cercle {
	Coordonnee coord;
	Couleur couleur;
	boolean isMegaPierre;
	
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

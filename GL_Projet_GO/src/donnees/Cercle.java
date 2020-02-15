package donnees;

public class Cercle {
	Coordonnee coord;
	Couleur couleur;
	
	public Cercle(Coordonnee coord, Couleur couleur) {
		this.coord = coord;
		this.couleur = couleur;
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
}

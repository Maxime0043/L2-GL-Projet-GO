package donnees;

public class Cercle {
	Coordonnee coord;
	String couleur;
	
	public Cercle(Coordonnee coord, Couleur couleur) {
		this.coord = coord;
		this.couleur = couleur.getCouleur();
	}
	
	public int getX() {
		return coord.getX();
	}
	
	public int getY() {
		return coord.getY();
	}
	
	public String getCouleur() {
		return couleur;
	}
}

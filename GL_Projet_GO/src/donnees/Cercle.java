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
	
	public void setX(int x) {
		coord.setX(x);
	}
	
	public int getY() {
		return coord.getY();
	}
	
	public void setY(int y) {
		coord.setY(y);
	}
	
	public Couleur getCouleur() {
		return couleur;
	}
	
	public void setCouleur(Couleur couleur) {
		this.couleur = couleur;
	}
}

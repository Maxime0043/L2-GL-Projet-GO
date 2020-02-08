package donnees;

public enum Couleur {
	NOIR(0),
	BLANC(1),
	ROUGE(2);
	
	private int couleur;
	
	Couleur(int couleur){
		this.couleur = couleur;
	}
	
	public int getCouleur() {
		return couleur;
	}
}

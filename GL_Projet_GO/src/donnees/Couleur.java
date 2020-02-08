package donnees;

public enum Couleur {
	NOIR("noir"),
	BLANC("blanc"),
	ROUGE("rouge");
	
	private String couleur;
	
	Couleur(String couleur){
		this.couleur = couleur;
	}
	
	public String getCouleur() {
		return couleur;
	}
}

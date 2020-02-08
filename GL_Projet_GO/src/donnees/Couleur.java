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
	
	public static String[] couleurEnnemis(String couleur) {
		String[] ennemis = new String[2];
		
		if(couleur.equals(NOIR.getCouleur())) {
			ennemis[0] = BLANC.getCouleur();
			ennemis[1] = ROUGE.getCouleur();
		}
		
		else if(couleur.equals(BLANC.getCouleur())) {
			ennemis[0] = NOIR.getCouleur();
			ennemis[1] = ROUGE.getCouleur();
		}
		
		else {
			ennemis[0] = NOIR.getCouleur();
			ennemis[1] = BLANC.getCouleur();
		}
		
		return ennemis;
	}
}

package donnees;

public enum Couleur {
	NOIR,
	BLANC,
	ROUGE;
	
	
	public static Couleur[] couleurEnnemis(String couleur) {
		Couleur[] ennemis = new Couleur[2];
		
		if(couleur.equals(NOIR)) {
			ennemis[0] = BLANC;
			ennemis[1] = ROUGE;
		}
		
		else if(couleur.equals(BLANC)) {
			ennemis[0] = NOIR;
			ennemis[1] = ROUGE;
		}
		
		else {
			ennemis[0] = NOIR;
			ennemis[1] = BLANC;
		}
		
		return ennemis;
	}
}

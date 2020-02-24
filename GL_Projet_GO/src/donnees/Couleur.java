package donnees;

public enum Couleur {
	NOIR,
	BLANC,
	ROUGE;
	
	
	public static Couleur[] getCouleurs() {
		Couleur[] couleurs = new Couleur[3];
		
		couleurs[0] = NOIR;
		couleurs[1] = BLANC;
		couleurs[2] = ROUGE;
		
		return couleurs;
	}
}

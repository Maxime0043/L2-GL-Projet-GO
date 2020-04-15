package donnees;

/**
 * Cette classe est une énumération des
 * couleurs que peuvent prendre les
 * pierres, les méga-pierres et les cercles.
 * 
 * @author Maxime, Micael et Houssam
 *
 */
public enum Couleur {
	NOIR,
	BLANC,
	ROUGE,
	VERT;
	
	
	public static Couleur[] getCouleurs() {
		Couleur[] couleurs = new Couleur[3];
		
		couleurs[0] = NOIR;
		couleurs[1] = BLANC;
		couleurs[2] = ROUGE;
		
		return couleurs;
	}
}

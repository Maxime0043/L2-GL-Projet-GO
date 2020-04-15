package donnees;

/**
 * Cette classe représente la plateau du jeu de go.
 * C'est ici que seront placées les différentes pierres
 * et méga-pierres des différents joueurs.
 * 
 * @author Maxime, Micael et Houssam
 *
 */
public class Plateau {
	
	private AbstractPierre[][] plateau;
	
	private int taille_goban;
	
	/**
	 * Pour créer le plateau du jeu on aura besoin
	 * de définir la taille que fera celui-ci.
	 * 
	 * @param taille_goban Définit la taille du plateau.
	 */
	public Plateau(int taille_goban) {
		this.taille_goban = taille_goban;
		
		plateau = new AbstractPierre[taille_goban][taille_goban];
		
		initPlateau(taille_goban);
	}
	
	public void initPlateau(int taille_goban) {
		for(int i = 0 ; i < taille_goban ; i++) {
			for(int j = 0 ; j < taille_goban ; j++) {
				plateau[i][j] = null;
			}
		}
	}
	
	public AbstractPierre[][] getPlateau(){
		return plateau;
	}
	
	/**
	 * Permet d'ajouter une pierre / méga-pierre au plateau.
	 * 
	 * @param pierre Pierre / Méga-pierre qui va être ajouté au plateau.
	 */
	public void addPierre(AbstractPierre pierre) {
		int x = pierre.getX();
		int y = pierre.getY();
		
		plateau[x][y] = pierre;
		
		if(pierre.isMegaPierre()) {
			plateau[x+1][y] = pierre;
			plateau[x+1][y+1] = pierre;
			plateau[x][y+1] = pierre;
		}
	}
	
	/**
	 * Permet de supprimer une pierre / méga-pierre du plateau.
	 * 
	 * @param x Ligne à laquelle la pierre va être retirée.
	 * @param y Colonne à laquelle la pierre va être retirée.
	 */
	public void removePierre(int x, int y) {
		plateau[x][y] = null;
	}
	
	public AbstractPierre getPierre(int x, int y) {
		AbstractPierre pierre = plateau[x][y];
		
		pierre.updateLiberte(plateau, taille_goban);
		
		return pierre;
	}
}
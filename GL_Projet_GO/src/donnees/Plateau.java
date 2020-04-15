package donnees;

/**
 * Cette classe repr�sente la plateau du jeu de go.
 * C'est ici que seront plac�es les diff�rentes pierres
 * et m�ga-pierres des diff�rents joueurs.
 * 
 * @author Maxime, Micael et Houssam
 *
 */
public class Plateau {
	
	private AbstractPierre[][] plateau;
	
	private int taille_goban;
	
	/**
	 * Pour cr�er le plateau du jeu on aura besoin
	 * de d�finir la taille que fera celui-ci.
	 * 
	 * @param taille_goban D�finit la taille du plateau.
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
	 * Permet d'ajouter une pierre / m�ga-pierre au plateau.
	 * 
	 * @param pierre Pierre / M�ga-pierre qui va �tre ajout� au plateau.
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
	 * Permet de supprimer une pierre / m�ga-pierre du plateau.
	 * 
	 * @param x Ligne � laquelle la pierre va �tre retir�e.
	 * @param y Colonne � laquelle la pierre va �tre retir�e.
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
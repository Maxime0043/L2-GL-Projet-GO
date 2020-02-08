package traitement;

import donnees.AbstractPierre;
import donnees.ParametrePartie;

/**
 * 
 * @author 
 *
 */

public class Liberte {
	int nb_liberte;
	AbstractPierre pierre;
	
	/**
	 * 
	 * @param pierre
	 */
	public Liberte(AbstractPierre pierre) {
		this.pierre = pierre;
		
		if(pierre.isMegaPierre())
			nb_liberte = 8;
		else
			nb_liberte = 4;
	}

	/**
	 * 
	 * @return
	 */
	public int getLiberte() {
		return nb_liberte;
	}
	
	/**
	 * 
	 * @param plateau
	 * @param choix
	 */
	public void updateLiberte(AbstractPierre[][] plateau, int choix) {
		String couleur = pierre.getCouleur();
		int x = pierre.getX();
		int y = pierre.getY();
		int taille_goban = ParametrePartie.TAILLE_GOBAN[choix];
		
		if(pierre.isMegaPierre()) {
			
		}
		
		else {
			if(x == 0) {
				nb_liberte--;
				
				if(y == 0) {
					nb_liberte--;
				}
				
				else {
					if((plateau[x][y-1] != null) && !couleur.equals(plateau[x][y-1].getCouleur())) {
						nb_liberte--;
					}
				}
				
				if((plateau[x+1][y] != null) && !couleur.equals(plateau[x+1][y].getCouleur())) {
					nb_liberte--;
				}
				
				if((plateau[x][y+1] != null) && !couleur.equals(plateau[x][y+1].getCouleur())) {
					nb_liberte--;
				}
			}
			
			else if(y == 0) {
				nb_liberte--;
				
				if(x == 0) {
					nb_liberte--;
				}
				
				else {
					if((plateau[x-1][y] != null) && !couleur.equals(plateau[x-1][y].getCouleur())) {
						nb_liberte--;
					}
				}
				
				if((plateau[x+1][y] != null) && !couleur.equals(plateau[x+1][y].getCouleur())) {
					nb_liberte--;
				}
				
				if((plateau[x][y+1] != null) && !couleur.equals(plateau[x][y+1].getCouleur())) {
					nb_liberte--;
				}
			}
			
			else if(x == taille_goban) {
				nb_liberte--;
				
				if(y == taille_goban) {
					nb_liberte--;
				}
				
				else {
					if((plateau[x][y+1] != null) && !couleur.equals(plateau[x][y+1].getCouleur())) {
						nb_liberte--;
					}
				}
				
				if((plateau[x-1][y] != null) && !couleur.equals(plateau[x-1][y].getCouleur())) {
					nb_liberte--;
				}
				
				if((plateau[x][y-1] != null) && !couleur.equals(plateau[x][y-1].getCouleur())) {
					nb_liberte--;
				}
			}
			
			else if(y == taille_goban) {
				nb_liberte--;
				
				if(x == taille_goban) {
					nb_liberte--;
				}
				
				else {
					if((plateau[x+1][y] != null) && !couleur.equals(plateau[x+1][y].getCouleur())) {
						nb_liberte--;
					}
				}
				
				if((plateau[x-1][y] != null) && !couleur.equals(plateau[x-1][y].getCouleur())) {
					nb_liberte--;
				}
				
				if((plateau[x][y-1] != null) && !couleur.equals(plateau[x][y-1].getCouleur())) {
					nb_liberte--;
				}
			}
		}
	}
}

package traitement;

import donnees.AbstractPierre;
import donnees.ParametrePartie;

/**
 * 
 * @author 
 *
 */

public class Liberte {
	private int nb_liberte;
	private AbstractPierre pierre;
	
	private boolean isCoinHautX = false;
	private boolean isCoinHautY = false;
	private boolean isCoinBasX = false;
	private boolean isCoinBasY = false;
	
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
				if(!isCoinHautX) {
					nb_liberte--;
					isCoinHautX = true;
				}
				
				if((y == 0) && !isCoinHautY) {
					nb_liberte--;
					isCoinHautY = true;
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
				if(!isCoinHautY) {
					nb_liberte--;
					isCoinHautY = true;
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
				if(!isCoinBasX) {
					nb_liberte--;
					isCoinBasX = true;
				}
				
				if((y == taille_goban) && !isCoinBasY) {
					nb_liberte--;
					isCoinBasY = true;
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
				if(!isCoinBasY) {
					nb_liberte--;
					isCoinBasY = true;
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
			
			else {
				if((plateau[x+1][y] != null) && !couleur.equals(plateau[x+1][y].getCouleur())) {
					nb_liberte--;
				}
				
				if((plateau[x][y+1] != null) && !couleur.equals(plateau[x][y+1].getCouleur())) {
					nb_liberte--;
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

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
	
	private boolean isBordHaut = false;
	private boolean isBordBas = false;
	private boolean isBordGauche = false;
	private boolean isBordDroit = false;
	
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
		int taille_goban = ParametrePartie.TAILLE_GOBAN[choix];
		
		int x = pierre.getX();
		int y = pierre.getY();
		AbstractPierre haut = plateau[x-1][y];
		AbstractPierre bas = plateau[x+1][y];
		AbstractPierre gauche = plateau[x][y-1];
		AbstractPierre droite = plateau[x][y+1];
		
		if(pierre.isMegaPierre()) {
			
		}
		
		else {
			if(x == 0) {
				if(isBordHaut) {
					nb_liberte--;
					isBordHaut = true;
				}
				
				if((y == 0) && !isBordGauche) {
					nb_liberte--;
					isBordGauche = true;
				}
				else {
					if(gauche != null) {
						nb_liberte--;
					}
				}
				
				if((y == taille_goban) && !isBordDroit) {
					nb_liberte--;
					isBordDroit = true;
				}
				else {
					if(droite != null) {
						nb_liberte--;
					}
				}
				
				if(bas != null) {
					nb_liberte--;
				}
			}
			
			else if(x == taille_goban) {
				if(!isBordBas) {
					nb_liberte--;
					isBordBas = true;
				}
				
				if((y == 0) && !isBordGauche) {
					nb_liberte--;
					isBordGauche = true;
				}
				else {
					if(gauche != null) {
						nb_liberte--;
					}
				}
				
				if((y == taille_goban) && !isBordDroit) {
					nb_liberte--;
					isBordDroit = true;
				}
				else {
					if(droite != null) {
						nb_liberte--;
					}
				}
				
				if(haut != null) {
					nb_liberte--;
				}
			}
			
			else if(y == 0) {
				if(!isBordGauche) {
					nb_liberte--;
					isBordGauche = true;
				}
				
				if(haut != null) {
					nb_liberte--;
				}
				
				if(bas != null) {
					nb_liberte--;
				}
				
				if(droite != null) {
					nb_liberte--;
				}
			}
			
			else if(y == taille_goban) {
				if(!isBordDroit) {
					nb_liberte--;
					isBordDroit = true;
				}
				
				if(haut != null) {
					nb_liberte--;
				}
				
				if(bas != null) {
					nb_liberte--;
				}
				
				if(gauche != null) {
					nb_liberte--;
				}
			}
			
			else {
				if(haut != null) {
					nb_liberte--;
				}
				
				if(bas != null) {
					nb_liberte--;
				}
				
				if(gauche != null) {
					nb_liberte--;
				}
				
				if(droite != null) {
					nb_liberte--;
				}
			}
		}
	}
}

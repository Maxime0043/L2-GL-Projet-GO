package traitement;

import donnees.AbstractPierre;
import donnees.MegaPierre;
import donnees.ParametrePartie;
import donnees.Pierre;


/**
 * 
 * @author 
 *
 */
public class GoPierre {
	public GoPierre() {
		
	}
	
	/**
	 * 
	 * @param pierre
	 * @return
	 */
	public boolean coinHautX(AbstractPierre pierre) {
		if(pierre.getX() == 0) {
				return true;
		}
		
		return false;
	}
	
	/**
	 * 
	 * @param pierre
	 * @return
	 */
	public boolean coinHautY(AbstractPierre pierre) {
		if(pierre.getY() == 0)
			return true;
		
		return false;
	}
	
	/**
	 * 
	 * @param pierre
	 * @param choix
	 * @return
	 */
	public boolean coinBasX(Pierre pierre, int choix) {
		if(pierre.getX() == ParametrePartie.TAILLE_GOBAN[choix]) {
				return true;
		}
		
		return false;
	}
	
	/**
	 * 
	 * @param pierre
	 * @param choix
	 * @return
	 */
	public boolean coinBasX(MegaPierre pierre, int choix) {
		int x = pierre.getX() + 1;
		
		if(x == 0) {
				return true;
		}
		
		return false;
	}
	
	/**
	 * 
	 * @param pierre
	 * @param choix
	 * @return
	 */
	public boolean coinBasY(Pierre pierre, int choix) {
		if(pierre.getY() == ParametrePartie.TAILLE_GOBAN[choix])
			return true;
		
		return false;
	}
	
	/**
	 * 
	 * @param pierre
	 * @param choix
	 * @return
	 */
	public boolean coinBasY(MegaPierre pierre, int choix) {
		int y = pierre.getY() + 1;
		
		if(y == ParametrePartie.TAILLE_GOBAN[choix])
			return true;
		
		return false;
	}
}

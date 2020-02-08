package traitement;

import java.util.ArrayList;
import java.util.HashMap;

import donnees.AbstractPierre;
import donnees.ParametrePartie;


/**
 * 
 * @author 
 *
 */
public class GoPierre {
	private ArrayList<Integer> liste_voisin_Noir;
	private ArrayList<Integer> liste_voisin_Blanc;
	private ArrayList<Integer> liste_voisin_Rouge;
	
	public GoPierre() {
		liste_voisin_Noir = new ArrayList<Integer>();
		liste_voisin_Blanc = new ArrayList<Integer>();
		liste_voisin_Rouge = new ArrayList<Integer>();
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
	public boolean coinBasX(AbstractPierre pierre, int choix) {
		int x = pierre.getX();
		
		if(pierre.isMegaPierre())
			x = pierre.getX() + 1;

		if(x == ParametrePartie.TAILLE_GOBAN[choix]) {
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
	public boolean coinBasY(AbstractPierre pierre, int choix) {
		int y = pierre.getY();
		
		if(pierre.isMegaPierre())
			y = pierre.getY() + 1;
			
		if(y == ParametrePartie.TAILLE_GOBAN[choix])
			return true;
		
		return false;
	}
	
	/**
	 * 
	 * @param pierre
	 * @param plateau
	 * @param choix
	 * @return
	 */
	public ArrayList<Integer> voisins(AbstractPierre pierre, AbstractPierre[][] plateau, int choix) {
		int x = pierre.getX();
		int y = pierre.getY();
		
		if(pierre.isMegaPierre()) {
			
		}
		
		else {
			if(coinHautX(pierre)) {
				if(!coinHautY(pierre)) {
					
				}
			}
			
			else if(coinHautY(pierre)) {
				
			}
			
			else if(coinBasX(pierre, choix)) {
				if(!coinBasY(pierre, choix)) {
					
				}
			}
			
			else if(coinBasY(pierre, choix)) {
				
			}
			
			else {
				
			}
		}
		
		return null;
	}
	
	public boolean pierreEnemieCollee(AbstractPierre pierreEnnemi, String couleurPierre, String couleurEnnemi) {
		if(!couleurPierre.equals(couleurEnnemi)) {
			int numero = pierreEnnemi.getNumero();
			
			if(couleurEnnemi.contentEquals(Couleur.))
		}
		
//		if(liste_voisin.size() > 0) {
//			int numero = pierreEnnemie.getNumero();
//			
//			if(liste_voisin.containsValue(numero)) {
//				if(liste_voisin.get)
//			}
//		}
		
		return false;
	}
}

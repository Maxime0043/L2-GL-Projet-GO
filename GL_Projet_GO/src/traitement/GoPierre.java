package traitement;

import java.util.ArrayList;

import donnees.AbstractPierre;
import donnees.ParametrePartie;


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
	 * @param numero
	 */
	public void addListe(AbstractPierre pierre, ArrayList<AbstractPierre> liste_voisin) {
		if(!liste_voisin.contains(pierre))
			liste_voisin.add(pierre);
	}
	
	/**
	 * 
	 * @param numero
	 */
	public void removeListe(AbstractPierre pierre, ArrayList<AbstractPierre> liste_voisin) {
		if(liste_voisin.contains(pierre) && (liste_voisin.size() > 0))
			liste_voisin.remove(pierre);
	}
	
	/**
	 * 
	 * @param pierre
	 * @return bool
	 */
	public boolean bordHaut(AbstractPierre pierre) {
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
	public boolean bordGauche(AbstractPierre pierre) {
		if(pierre.getY() == 0)
			return true;
		
		return false;
	}
	
	/**
	 * 
	 * @param pierre
	 * @return
	 */
	public boolean bordDroit(AbstractPierre pierre, int choix) {
		int y = pierre.getY();
		
		if(pierre.isMegaPierre()) {
			y++;
		}
		
		if(y == ParametrePartie.TAILLE_GOBAN[choix] - 1)
			return true;
		
		return false;
	}
	
	/**
	 * 
	 * @param pierre
	 * @param choix
	 * @return
	 */
	public boolean bordBas(AbstractPierre pierre, int choix) {
		int x = pierre.getX();
		
		if(pierre.isMegaPierre()) {
			x++;
		}
		
		if(x == ParametrePartie.TAILLE_GOBAN[choix] - 1) {
				return true;
		}
		
		return false;
	}
	
	/**
	 * 
	 * @param pierre
	 * @param plateau
	 * @param choix
	 * @return
	 */
	public ArrayList<AbstractPierre> voisins(AbstractPierre pierre, AbstractPierre[][] plateau, int choix) {
		ArrayList<AbstractPierre> liste_voisin = new ArrayList<AbstractPierre>();
		
		int x = pierre.getX();
		int y = pierre.getY();
		
		AbstractPierre haut = null;
		AbstractPierre bas = null;
		AbstractPierre gauche = null;
		AbstractPierre droite = null;
		
		if(x > 0) {
			haut = plateau[x-1][y];
		}
		if(x < ParametrePartie.TAILLE_GOBAN[choix] - 1) {
			bas = plateau[x+1][y];
		}
		if(y > 0) {
			gauche = plateau[x][y-1];
		}
		if(y < ParametrePartie.TAILLE_GOBAN[choix] - 1) {
			droite = plateau[x][y+1];
		}
		
		if(pierre.isMegaPierre()) {
			//A remplir
		}
		
		else {
			if(bordHaut(pierre)) {
				if(!bordGauche(pierre) && pierreEnemieExiste(gauche)) {
					addListe(gauche, liste_voisin);
				}
				
				if(!bordDroit(pierre, choix) && pierreEnemieExiste(droite)) {
					addListe(droite, liste_voisin);
				}
				
				if(pierreEnemieExiste(bas)) {
					addListe(bas, liste_voisin);
				}
			}
			
			else if(bordBas(pierre, choix)) {
				if(!bordGauche(pierre) && pierreEnemieExiste(gauche)) {
					addListe(gauche, liste_voisin);
				}
				
				if(!bordDroit(pierre, choix) && pierreEnemieExiste(droite)) {
					addListe(droite, liste_voisin);
				}
				
				if(pierreEnemieExiste(haut)) {
					addListe(haut, liste_voisin);
				}
			}
			
			else if(bordGauche(pierre)) {
				if(pierreEnemieExiste(haut)) {
					addListe(haut, liste_voisin);
				}
				
				if(pierreEnemieExiste(bas)) {
					addListe(bas, liste_voisin);
				}
				
				if(pierreEnemieExiste(droite)) {
					addListe(droite, liste_voisin);
				}
			}
			
			else if(bordDroit(pierre, choix)) {
				if(pierreEnemieExiste(haut)) {
					addListe(haut, liste_voisin);
				}
				
				if(pierreEnemieExiste(bas)) {
					addListe(bas, liste_voisin);
				}
				
				if(pierreEnemieExiste(gauche)) {
					addListe(gauche, liste_voisin);
				}
			}
			
			else {
				if(pierreEnemieExiste(haut)) {
					addListe(haut, liste_voisin);
				}
				
				if(pierreEnemieExiste(bas)) {
					addListe(bas, liste_voisin);
				}
				
				if(pierreEnemieExiste(gauche)) {
					addListe(gauche, liste_voisin);
				}
				
				if(pierreEnemieExiste(droite)) {
					addListe(droite, liste_voisin);
				}
			}
		}
		
		return liste_voisin;
	}
	
	/**
	 * 
	 * @param pierreEnnemi
	 * @return
	 */
	public boolean pierreEnemieExiste(AbstractPierre pierreEnnemi) {
		if(pierreEnnemi != null) {
			return true;
		}
		
		return false;
	}
}

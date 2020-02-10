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
	
	ArrayList<AbstractPierre> liste_voisin;
	
	public GoPierre() {
		liste_voisin = new ArrayList<AbstractPierre>();
	}
	
	/**
	 * 
	 * @param numero
	 */
	public void addListe(AbstractPierre pierre) {
		if(!liste_voisin.contains(pierre))
			liste_voisin.add(pierre);
	}
	
	/**
	 * 
	 * @param numero
	 */
	public void removeListe(AbstractPierre pierre) {
		if(liste_voisin.contains(pierre) && (liste_voisin.size() > 0))
			liste_voisin.remove(pierre);
	}
	
	/**
	 * 
	 * @param pierre
	 * @return
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
		
		if(y == ParametrePartie.TAILLE_GOBAN[choix])
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
		
		if(x == ParametrePartie.TAILLE_GOBAN[choix]) {
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
		liste_voisin.clear();
		
		int x = pierre.getX();
		int y = pierre.getY();
		
		AbstractPierre haut = plateau[x-1][y];
		AbstractPierre bas = plateau[x+1][y];
		AbstractPierre gauche = plateau[x][y-1];
		AbstractPierre droite = plateau[x][y+1];
		
		if(pierre.isMegaPierre()) {
			bas = plateau[x+2][y];
			droite = plateau[x][y+2];
		}
		
		if(bordHaut(pierre)) {
			if(!bordGauche(pierre) && pierreEnemieExiste(gauche)) {
				addListe(gauche);
			}
			
			else if(!bordDroit(pierre, choix) && pierreEnemieExiste(droite)) {
				addListe(droite);
			}
			
			if(pierreEnemieExiste(bas)) {
				addListe(bas);
			}
		}
		
		else if(bordBas(pierre, choix)) {
			
			
			if(!bordGauche(pierre) && pierreEnemieExiste(gauche)) {
				addListe(gauche);
			}
			
			else if(!bordDroit(pierre, choix) && pierreEnemieExiste(droite)) {
				addListe(droite);
			}
			
			if(pierreEnemieExiste(haut)) {
				addListe(haut);
			}
		}
		
		else if(bordGauche(pierre)) {
			if(pierreEnemieExiste(haut)) {
				addListe(haut);
			}
			
			if(pierreEnemieExiste(bas)) {
				addListe(bas);
			}
			
			if(pierreEnemieExiste(droite)) {
				addListe(droite);
			}
		}
		
		else if(bordDroit(pierre, choix)) {
			if(pierreEnemieExiste(haut)) {
				addListe(haut);
			}
			
			if(pierreEnemieExiste(bas)) {
				addListe(bas);
			}
			
			if(pierreEnemieExiste(gauche)) {
				addListe(gauche);
			}
		}
		
		else {
			if(pierreEnemieExiste(haut)) {
				addListe(haut);
			}
			
			if(pierreEnemieExiste(bas)) {
				addListe(bas);
			}
			
			if(pierreEnemieExiste(gauche)) {
				addListe(gauche);
			}
			
			if(pierreEnemieExiste(droite)) {
				addListe(droite);
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

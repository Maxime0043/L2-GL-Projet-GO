package traitement;

import java.util.ArrayList;

import donnees.AbstractPierre;


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
	public boolean bordDroit(AbstractPierre pierre, int taille_goban) {
		int y = pierre.getY();
		
		if(pierre.isMegaPierre()) {
			y++;
		}
		
		if(y == taille_goban - 1) {
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
	public boolean bordBas(AbstractPierre pierre, int taille_goban) {
		int x = pierre.getX();
		
		if(pierre.isMegaPierre()) {
			x++;
		}
		
		if(x == taille_goban - 1) {
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
	public ArrayList<AbstractPierre> voisins(AbstractPierre pierre, AbstractPierre[][] plateau, int taille_goban) {
		ArrayList<AbstractPierre> liste_voisin = new ArrayList<AbstractPierre>();
		
		int x = pierre.getX();
		int y = pierre.getY();
		
		AbstractPierre haut = null;
		AbstractPierre haut2 = null;
		AbstractPierre bas = null;
		AbstractPierre bas2 = null;
		AbstractPierre gauche = null;
		AbstractPierre gauche2 = null;
		AbstractPierre droite = null;
		AbstractPierre droite2 = null;
		
		if(x > 0) {
			haut = plateau[x-1][y];
		}
		if(x < taille_goban - 1) {
			bas = plateau[x+1][y];
		}
		if(y > 0) {
			gauche = plateau[x][y-1];
		}
		if(y < taille_goban - 1) {
			droite = plateau[x][y+1];
		}
		
		if(!pierre.isMegaPierre()) {
			if(bordHaut(pierre)) {
				if(!bordGauche(pierre) && pierreEnemieExiste(gauche)) {
					addListe(gauche, liste_voisin);
				}
				
				if(!bordDroit(pierre, taille_goban) && pierreEnemieExiste(droite)) {
					addListe(droite, liste_voisin);
				}
				
				if(pierreEnemieExiste(bas)) {
					addListe(bas, liste_voisin);
				}
			}
			
			else if(bordBas(pierre, taille_goban)) {
				if(!bordGauche(pierre) && pierreEnemieExiste(gauche)) {
					addListe(gauche, liste_voisin);
				}
				
				if(!bordDroit(pierre, taille_goban) && pierreEnemieExiste(droite)) {
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
			
			else if(bordDroit(pierre, taille_goban)) {
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
		
		else {
			if(x > 0) {
				haut2 = plateau[x-1][y+1];
			}
			if(x < taille_goban - 1) {
				bas = plateau[x+2][y];
				bas2 = plateau[x+2][y+1];
			}
			if(y > 0) {
				gauche2 = plateau[x+1][y-1];
			}
			if(y < taille_goban - 1) {
				droite = plateau[x][y+2];
				droite2 = plateau[x+1][y+2];
			}
			
			if(bordHaut(pierre)) {
				if(!bordGauche(pierre)) {
					if(pierreEnemieExiste(gauche)) {
						addListe(gauche, liste_voisin);
					}
					if(pierreEnemieExiste(gauche2)) {
						addListe(gauche2, liste_voisin);
					}
				}
				
				if(!bordDroit(pierre, taille_goban)) {
					if(pierreEnemieExiste(droite)) {
						addListe(droite, liste_voisin);
					}
					if(pierreEnemieExiste(droite2)) {
						addListe(droite2, liste_voisin);
					}
				}
				
				if(pierreEnemieExiste(bas)) {
					addListe(bas, liste_voisin);
				}
				if(pierreEnemieExiste(bas2)) {
					addListe(bas2, liste_voisin);
				}
			}
			
			else if(bordBas(pierre, taille_goban)) {
				if(!bordGauche(pierre)) {
					if(pierreEnemieExiste(gauche)) {
						addListe(gauche, liste_voisin);
					}
					if(pierreEnemieExiste(gauche2)) {
						addListe(gauche2, liste_voisin);
					}
				}
				
				if(!bordDroit(pierre, taille_goban)) {
					if(pierreEnemieExiste(droite)) {
						addListe(droite, liste_voisin);
					}
					if(pierreEnemieExiste(droite2)) {
						addListe(droite2, liste_voisin);
					}
				}
				
				if(pierreEnemieExiste(haut)) {
					addListe(haut, liste_voisin);
				}
				if(pierreEnemieExiste(haut2)) {
					addListe(haut2, liste_voisin);
				}
			}
			
			else if(bordGauche(pierre)) {
				if(pierreEnemieExiste(haut)) {
					addListe(haut, liste_voisin);
				}
				if(pierreEnemieExiste(haut2)) {
					addListe(haut2, liste_voisin);
				}
				
				if(pierreEnemieExiste(bas)) {
					addListe(bas, liste_voisin);
				}
				if(pierreEnemieExiste(bas2)) {
					addListe(bas2, liste_voisin);
				}
				
				if(pierreEnemieExiste(droite)) {
					addListe(droite, liste_voisin);
				}
				if(pierreEnemieExiste(droite2)) {
					addListe(droite2, liste_voisin);
				}
			}
			
			else if(bordDroit(pierre, taille_goban)) {
				if(pierreEnemieExiste(haut)) {
					addListe(haut, liste_voisin);
				}
				if(pierreEnemieExiste(haut2)) {
					addListe(haut2, liste_voisin);
				}
				
				if(pierreEnemieExiste(bas)) {
					addListe(bas, liste_voisin);
				}
				if(pierreEnemieExiste(bas2)) {
					addListe(bas2, liste_voisin);
				}
				
				if(pierreEnemieExiste(gauche)) {
					addListe(gauche, liste_voisin);
				}
				if(pierreEnemieExiste(gauche2)) {
					addListe(gauche2, liste_voisin);
				}
			}
			
			else {
				if(pierreEnemieExiste(haut)) {
					addListe(haut, liste_voisin);
				}
				if(pierreEnemieExiste(haut2)) {
					addListe(haut2, liste_voisin);
				}
				
				if(pierreEnemieExiste(bas)) {
					addListe(bas, liste_voisin);
				}
				if(pierreEnemieExiste(bas2)) {
					addListe(bas2, liste_voisin);
				}
				
				if(pierreEnemieExiste(gauche)) {
					addListe(gauche, liste_voisin);
				}
				if(pierreEnemieExiste(gauche2)) {
					addListe(gauche2, liste_voisin);
				}
				
				if(pierreEnemieExiste(droite)) {
					addListe(droite, liste_voisin);
				}
				if(pierreEnemieExiste(droite2)) {
					addListe(droite2, liste_voisin);
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

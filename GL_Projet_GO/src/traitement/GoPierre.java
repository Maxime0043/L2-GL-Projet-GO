package traitement;

import java.util.ArrayList;

import donnees.AbstractPierre;
import donnees.Coordonnee;


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
	public static void addListe(AbstractPierre pierre, ArrayList<AbstractPierre> liste_voisin) {
		if(!liste_voisin.contains(pierre)) {
			liste_voisin.add(pierre);
		}
	}
	
	/**
	 * 
	 * @param numero
	 */
	public static void addListe(Coordonnee coord, ArrayList<Coordonnee> liste_voisin) {
		if(!liste_voisin.contains(coord)) {
			liste_voisin.add(coord);
		}
	}
	
	/**
	 * 
	 * @param numero
	 */
	public static void removeListe(AbstractPierre pierre, ArrayList<AbstractPierre> liste_voisin) {
		if(liste_voisin.contains(pierre) && (liste_voisin.size() > 0)) {
			liste_voisin.remove(pierre);
		}
	}
	
	/**
	 * 
	 * @param numero
	 */
	public static void removeListe(Coordonnee coord, ArrayList<Coordonnee> liste_voisin) {
		if(liste_voisin.contains(coord) && (liste_voisin.size() > 0)) {
			liste_voisin.remove(coord);
		}
	}
	
	/**
	 * 
	 * @param pierre
	 * @return bool
	 */
	public static boolean bordHaut(AbstractPierre pierre) {
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
	public static boolean bordGauche(AbstractPierre pierre) {
		if(pierre.getY() == 0)
			return true;
		
		return false;
	}
	
	/**
	 * 
	 * @param pierre
	 * @param taille_goban
	 * @return
	 */
	public static boolean bordDroit(AbstractPierre pierre, int taille_goban) {
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
	public static boolean bordBas(AbstractPierre pierre, int taille_goban) {
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
	public static ArrayList<AbstractPierre> voisins(AbstractPierre pierre, AbstractPierre[][] plateau, int taille_goban) {
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
				if(!bordGauche(pierre) && pierreEnnemieExiste(gauche)) {
					addListe(gauche, liste_voisin);
				}
				
				if(!bordDroit(pierre, taille_goban) && pierreEnnemieExiste(droite)) {
					addListe(droite, liste_voisin);
				}
				
				if(pierreEnnemieExiste(bas)) {
					addListe(bas, liste_voisin);
				}
			}
			
			else if(bordBas(pierre, taille_goban)) {
				if(!bordGauche(pierre) && pierreEnnemieExiste(gauche)) {
					addListe(gauche, liste_voisin);
				}
				
				if(!bordDroit(pierre, taille_goban) && pierreEnnemieExiste(droite)) {
					addListe(droite, liste_voisin);
				}
				
				if(pierreEnnemieExiste(haut)) {
					addListe(haut, liste_voisin);
				}
			}
			
			else if(bordGauche(pierre)) {
				if(pierreEnnemieExiste(haut)) {
					addListe(haut, liste_voisin);
				}
				
				if(pierreEnnemieExiste(bas)) {
					addListe(bas, liste_voisin);
				}
				
				if(pierreEnnemieExiste(droite)) {
					addListe(droite, liste_voisin);
				}
			}
			
			else if(bordDroit(pierre, taille_goban)) {
				if(pierreEnnemieExiste(haut)) {
					addListe(haut, liste_voisin);
				}
				
				if(pierreEnnemieExiste(bas)) {
					addListe(bas, liste_voisin);
				}
				
				if(pierreEnnemieExiste(gauche)) {
					addListe(gauche, liste_voisin);
				}
			}
			
			else {
				if(pierreEnnemieExiste(haut)) {
					addListe(haut, liste_voisin);
				}
				
				if(pierreEnnemieExiste(bas)) {
					addListe(bas, liste_voisin);
				}
				
				if(pierreEnnemieExiste(gauche)) {
					addListe(gauche, liste_voisin);
				}
				
				if(pierreEnnemieExiste(droite)) {
					addListe(droite, liste_voisin);
				}
			}
		}
		
		else {
			if(x > 0) {
				haut2 = plateau[x-1][y+1];
			}
			if(x < taille_goban - 2) {
				bas = plateau[x+2][y];
				bas2 = plateau[x+2][y+1];
			}
			if(y > 0) {
				gauche2 = plateau[x+1][y-1];
			}
			if(y < taille_goban - 2) {
				droite = plateau[x][y+2];
				droite2 = plateau[x+1][y+2];
			}
			
			if(bordHaut(pierre)) {
				if(!bordGauche(pierre)) {
					if(pierreEnnemieExiste(gauche)) {
						addListe(gauche, liste_voisin);
					}
					if(pierreEnnemieExiste(gauche2)) {
						addListe(gauche2, liste_voisin);
					}
				}
				
				if(!bordDroit(pierre, taille_goban)) {
					if(pierreEnnemieExiste(droite)) {
						addListe(droite, liste_voisin);
					}
					if(pierreEnnemieExiste(droite2)) {
						addListe(droite2, liste_voisin);
					}
				}
				
				if(pierreEnnemieExiste(bas)) {
					addListe(bas, liste_voisin);
				}
				if(pierreEnnemieExiste(bas2)) {
					addListe(bas2, liste_voisin);
				}
			}
			
			else if(bordBas(pierre, taille_goban)) {
				if(!bordGauche(pierre)) {
					if(pierreEnnemieExiste(gauche)) {
						addListe(gauche, liste_voisin);
					}
					if(pierreEnnemieExiste(gauche2)) {
						addListe(gauche2, liste_voisin);
					}
				}
				
				if(!bordDroit(pierre, taille_goban)) {
					if(pierreEnnemieExiste(droite)) {
						addListe(droite, liste_voisin);
					}
					if(pierreEnnemieExiste(droite2)) {
						addListe(droite2, liste_voisin);
					}
				}
				
				if(pierreEnnemieExiste(haut)) {
					addListe(haut, liste_voisin);
				}
				if(pierreEnnemieExiste(haut2)) {
					addListe(haut2, liste_voisin);
				}
			}
			
			else if(bordGauche(pierre)) {
				if(pierreEnnemieExiste(haut)) {
					addListe(haut, liste_voisin);
				}
				if(pierreEnnemieExiste(haut2)) {
					addListe(haut2, liste_voisin);
				}
				
				if(pierreEnnemieExiste(bas)) {
					addListe(bas, liste_voisin);
				}
				if(pierreEnnemieExiste(bas2)) {
					addListe(bas2, liste_voisin);
				}
				
				if(pierreEnnemieExiste(droite)) {
					addListe(droite, liste_voisin);
				}
				if(pierreEnnemieExiste(droite2)) {
					addListe(droite2, liste_voisin);
				}
			}
			
			else if(bordDroit(pierre, taille_goban)) {
				if(pierreEnnemieExiste(haut)) {
					addListe(haut, liste_voisin);
				}
				if(pierreEnnemieExiste(haut2)) {
					addListe(haut2, liste_voisin);
				}
				
				if(pierreEnnemieExiste(bas)) {
					addListe(bas, liste_voisin);
				}
				if(pierreEnnemieExiste(bas2)) {
					addListe(bas2, liste_voisin);
				}
				
				if(pierreEnnemieExiste(gauche)) {
					addListe(gauche, liste_voisin);
				}
				if(pierreEnnemieExiste(gauche2)) {
					addListe(gauche2, liste_voisin);
				}
			}
			
			else {
				if(pierreEnnemieExiste(haut)) {
					addListe(haut, liste_voisin);
				}
				if(pierreEnnemieExiste(haut2)) {
					addListe(haut2, liste_voisin);
				}
				
				if(pierreEnnemieExiste(bas)) {
					addListe(bas, liste_voisin);
				}
				if(pierreEnnemieExiste(bas2)) {
					addListe(bas2, liste_voisin);
				}
				
				if(pierreEnnemieExiste(gauche)) {
					addListe(gauche, liste_voisin);
				}
				if(pierreEnnemieExiste(gauche2)) {
					addListe(gauche2, liste_voisin);
				}
				
				if(pierreEnnemieExiste(droite)) {
					addListe(droite, liste_voisin);
				}
				if(pierreEnnemieExiste(droite2)) {
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
	public static boolean pierreEnnemieExiste(AbstractPierre pierreEnnemi) {
		if(pierreEnnemi != null) {
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
	public static ArrayList<Coordonnee> intersectionVide(AbstractPierre pierre, AbstractPierre[][] plateau, int taille_goban) {
		ArrayList<Coordonnee> liste_voisin = new ArrayList<Coordonnee>();
		
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
				if(!bordGauche(pierre) && !pierreEnnemieExiste(gauche)) {
					addListe(new Coordonnee(gauche.getX(), gauche.getY()), liste_voisin);
				}
				
				if(!bordDroit(pierre, taille_goban) && !pierreEnnemieExiste(droite)) {
					addListe(new Coordonnee(droite.getX(), droite.getY()), liste_voisin);
				}
				
				if(!pierreEnnemieExiste(bas)) {
					addListe(new Coordonnee(bas.getX(), bas.getY()), liste_voisin);
				}
			}
			
			else if(bordBas(pierre, taille_goban)) {
				if(!bordGauche(pierre) && !pierreEnnemieExiste(gauche)) {
					addListe(new Coordonnee(gauche.getX(), gauche.getY()), liste_voisin);
				}
				
				if(!bordDroit(pierre, taille_goban) && !pierreEnnemieExiste(droite)) {
					addListe(new Coordonnee(droite.getX(), droite.getY()), liste_voisin);
				}
				
				if(!pierreEnnemieExiste(haut)) {
					addListe(new Coordonnee(haut.getX(), haut.getY()), liste_voisin);
				}
			}
			
			else if(bordGauche(pierre)) {
				if(!pierreEnnemieExiste(haut)) {
					addListe(new Coordonnee(haut.getX(), haut.getY()), liste_voisin);
				}
				
				if(!pierreEnnemieExiste(bas)) {
					addListe(new Coordonnee(bas.getX(), bas.getY()), liste_voisin);
				}
				
				if(!pierreEnnemieExiste(droite)) {
					addListe(new Coordonnee(droite.getX(), droite.getY()), liste_voisin);
				}
			}
			
			else if(bordDroit(pierre, taille_goban)) {
				if(!pierreEnnemieExiste(haut)) {
					addListe(new Coordonnee(haut.getX(), haut.getY()), liste_voisin);
				}
				
				if(!pierreEnnemieExiste(bas)) {
					addListe(new Coordonnee(bas.getX(), bas.getY()), liste_voisin);
				}
				
				if(!pierreEnnemieExiste(gauche)) {
					addListe(new Coordonnee(gauche.getX(), gauche.getY()), liste_voisin);
				}
			}
			
			else {
				if(!pierreEnnemieExiste(haut)) {
					addListe(new Coordonnee(haut.getX(), haut.getY()), liste_voisin);
				}
				
				if(!pierreEnnemieExiste(bas)) {
					addListe(new Coordonnee(bas.getX(), bas.getY()), liste_voisin);
				}
				
				if(!pierreEnnemieExiste(gauche)) {
					addListe(new Coordonnee(gauche.getX(), gauche.getY()), liste_voisin);
				}
				
				if(!pierreEnnemieExiste(droite)) {
					addListe(new Coordonnee(droite.getX(), droite.getY()), liste_voisin);
				}
			}
		}
		
		else {
			if(x > 0) {
				haut2 = plateau[x-1][y+1];
			}
			if(x < taille_goban - 2) {
				bas = plateau[x+2][y];
				bas2 = plateau[x+2][y+1];
			}
			if(y > 0) {
				gauche2 = plateau[x+1][y-1];
			}
			if(y < taille_goban - 2) {
				droite = plateau[x][y+2];
				droite2 = plateau[x+1][y+2];
			}
			
			if(bordHaut(pierre)) {
				if(!bordGauche(pierre)) {
					if(!pierreEnnemieExiste(gauche)) {
						addListe(new Coordonnee(gauche.getX(), gauche.getY()), liste_voisin);
					}
					if(!pierreEnnemieExiste(gauche2)) {
						addListe(new Coordonnee(gauche2.getX(), gauche2.getY()), liste_voisin);
					}
				}
				
				if(!bordDroit(pierre, taille_goban)) {
					if(!pierreEnnemieExiste(droite)) {
						addListe(new Coordonnee(droite.getX(), droite.getY()), liste_voisin);
					}
					if(!pierreEnnemieExiste(droite2)) {
						addListe(new Coordonnee(droite2.getX(), droite2.getY()), liste_voisin);
					}
				}
				
				if(!pierreEnnemieExiste(bas)) {
					addListe(new Coordonnee(bas.getX(), bas.getY()), liste_voisin);
				}
				if(!pierreEnnemieExiste(bas2)) {
					addListe(new Coordonnee(bas2.getX(), bas2.getY()), liste_voisin);
				}
			}
			
			else if(bordBas(pierre, taille_goban)) {
				if(!bordGauche(pierre)) {
					if(!pierreEnnemieExiste(gauche)) {
						addListe(new Coordonnee(gauche.getX(), gauche.getY()), liste_voisin);
					}
					if(!pierreEnnemieExiste(gauche2)) {
						addListe(new Coordonnee(gauche2.getX(), gauche2.getY()), liste_voisin);
					}
				}
				
				if(!bordDroit(pierre, taille_goban)) {
					if(!pierreEnnemieExiste(droite)) {
						addListe(new Coordonnee(droite.getX(), droite.getY()), liste_voisin);
					}
					if(!pierreEnnemieExiste(droite2)) {
						addListe(new Coordonnee(droite2.getX(), droite2.getY()), liste_voisin);
					}
				}
				
				if(!pierreEnnemieExiste(haut)) {
					addListe(new Coordonnee(haut.getX(), haut.getY()), liste_voisin);
				}
				if(!pierreEnnemieExiste(haut2)) {
					addListe(new Coordonnee(haut2.getX(), haut2.getY()), liste_voisin);
				}
			}
			
			else if(bordGauche(pierre)) {
				if(!pierreEnnemieExiste(haut)) {
					addListe(new Coordonnee(haut.getX(), haut.getY()), liste_voisin);
				}
				if(!pierreEnnemieExiste(haut2)) {
					addListe(new Coordonnee(haut2.getX(), haut2.getY()), liste_voisin);
				}
				
				if(!pierreEnnemieExiste(bas)) {
					addListe(new Coordonnee(bas.getX(), bas.getY()), liste_voisin);
				}
				if(!pierreEnnemieExiste(bas2)) {
					addListe(new Coordonnee(bas2.getX(), bas2.getY()), liste_voisin);
				}
				
				if(!pierreEnnemieExiste(droite)) {
					addListe(new Coordonnee(droite.getX(), droite.getY()), liste_voisin);
				}
				if(!pierreEnnemieExiste(droite2)) {
					addListe(new Coordonnee(droite2.getX(), droite2.getY()), liste_voisin);
				}
			}
			
			else if(bordDroit(pierre, taille_goban)) {
				if(!pierreEnnemieExiste(haut)) {
					addListe(new Coordonnee(haut.getX(), haut.getY()), liste_voisin);
				}
				if(!pierreEnnemieExiste(haut2)) {
					addListe(new Coordonnee(haut2.getX(), haut2.getY()), liste_voisin);
				}
				
				if(!pierreEnnemieExiste(bas)) {
					addListe(new Coordonnee(bas.getX(), bas.getY()), liste_voisin);
				}
				if(!pierreEnnemieExiste(bas2)) {
					addListe(new Coordonnee(bas2.getX(), bas2.getY()), liste_voisin);
				}
				
				if(!pierreEnnemieExiste(gauche)) {
					addListe(new Coordonnee(gauche.getX(), gauche.getY()), liste_voisin);
				}
				if(!pierreEnnemieExiste(gauche2)) {
					addListe(new Coordonnee(gauche2.getX(), gauche2.getY()), liste_voisin);
				}
			}
			
			else {
				if(!pierreEnnemieExiste(haut)) {
					addListe(new Coordonnee(haut.getX(), haut.getY()), liste_voisin);
				}
				if(!pierreEnnemieExiste(haut2)) {
					addListe(new Coordonnee(haut2.getX(), haut2.getY()), liste_voisin);
				}
				
				if(!pierreEnnemieExiste(bas)) {
					addListe(new Coordonnee(bas.getX(), bas.getY()), liste_voisin);
				}
				if(!pierreEnnemieExiste(bas2)) {
					addListe(new Coordonnee(bas2.getX(), bas2.getY()), liste_voisin);
				}
				
				if(!pierreEnnemieExiste(gauche)) {
					addListe(new Coordonnee(gauche.getX(), gauche.getY()), liste_voisin);
				}
				if(!pierreEnnemieExiste(gauche2)) {
					addListe(new Coordonnee(gauche2.getX(), gauche2.getY()), liste_voisin);
				}
				
				if(!pierreEnnemieExiste(droite)) {
					addListe(new Coordonnee(droite.getX(), droite.getY()), liste_voisin);
				}
				if(!pierreEnnemieExiste(droite2)) {
					addListe(new Coordonnee(droite2.getX(), droite2.getY()), liste_voisin);
				}
			}
		}
		
		return liste_voisin;
	}
}

package traitement;

import java.util.ArrayList;

import donnees.AbstractPierre;
import donnees.Coordonnee;


/**
 * Cette classe permet de définir ce qu'il y a autour d'une pierre ou d'une méga-pierre.
 * 
 * @author Maxime, Micael et Houssam
 *
 */
public class GoPierre {
	
	public GoPierre() {
		
	}
	
	/**
	 * Permet d'ajouter une pierre ou une méga-pierre à la liste de pierres voisines appelée.
	 * 
	 * @param pierre Définit la pierre ou la méga-pierre que l'on ajoute à la liste.
	 * @param liste_voisin Définit la liste de pierres voisines.
	 */
	public static void addListe(AbstractPierre pierre, ArrayList<AbstractPierre> liste_voisin) {
		if(!liste_voisin.contains(pierre)) {
			liste_voisin.add(pierre);
		}
	}
	
	/**
	 * Permet d'ajouter une coordonnée à la liste des intersections voisines vides appelée.
	 * 
	 * @param coord Définit la coordonnée que l'on ajoute à la liste.
	 * @param liste_voisin Définit la liste d'intersections vides voisines.
	 */
	public static void addListe(Coordonnee coord, ArrayList<Coordonnee> liste_voisin) {
			liste_voisin.add(coord);
	}
	
	/**
	 * Permet de retirer une pierre ou une méga-pierre à la liste de pierres voisines appelée.
	 * 
	 * @param pierre Définit la pierre ou la méga-pierre que l'on supprime à la liste.
	 * @param liste_voisin Définit la liste de pierres voisines.
	 */
	public static void removeListe(AbstractPierre pierre, ArrayList<AbstractPierre> liste_voisin) {
		if(liste_voisin.contains(pierre) && (liste_voisin.size() > 0)) {
			liste_voisin.remove(pierre);
		}
	}
	
	/**
	 * Permet de retirer une coordonnée à la liste des intersections voisines vides appelée.
	 * 
	 * @param coord Définit la coordonnée que l'on supprime à la liste.
	 * @param liste_voisin Définit la liste d'intersections vides voisines.
	 */
	public static void removeListe(Coordonnee coord, ArrayList<Coordonnee> liste_voisin) {
		if(liste_voisin.contains(coord) && (liste_voisin.size() > 0)) {
			liste_voisin.remove(coord);
		}
	}
	
	/**
	 * Permet de savoir si une pierre ou une méga-pierre est située sur le bord en haut du plateau.
	 * 
	 * @param pierre Définit la pierre ou la méga-pierre que l'on souhaite vérifier.
	 * @return Indique si la pierre ou la méga-pierre est sur le bord haut.
	 */
	public static boolean bordHaut(AbstractPierre pierre) {
		if(pierre.getX() == 0) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Permet de savoir si une pierre ou une méga-pierre est située sur le bord à gauche du plateau.
	 * 
	 * @param pierre Définit la pierre ou la méga-pierre que l'on souhaite vérifier.
	 * @return Indique si la pierre ou la méga-pierre est sur le bord gauche.
	 */
	public static boolean bordGauche(AbstractPierre pierre) {
		if(pierre.getY() == 0)
			return true;
		
		return false;
	}
	
	/**
	 * Permet de savoir si une pierre ou une méga-pierre est située sur le bord à droite du plateau.
	 * 
	 * @param pierre Définit la pierre ou la méga-pierre que l'on souhaite vérifier.
	 * @param taille_goban Définit la taille du plateau de jeu.
	 * @return Indique si la pierre ou la méga-pierre est sur le bord droit.
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
	 * Permet de savoir si une pierre ou une méga-pierre est située sur le bord en bas du plateau.
	 * 
	 * @param pierre Définit la pierre ou la méga-pierre que l'on souhaite vérifier.
	 * @param taille_goban Définit la taille du plateau de jeu.
	 * @return Indique si la pierre ou la méga-pierre est sur le bord bas.
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
	 * Permet de récupérer les pierres et méga-pierres voisines autour d'une pierre ou d'une méga-pierre.
	 * 
	 * @param pierre Définit la pierre ou la méga-pierre que l'on souhaite regarder.
	 * @param plateau Définir le plateau de jeu sur lequel est la pierre ou la méga-pierre.
	 * @param taille_goban Définit la taille du plateau de jeu.
	 * @return Renvoie la liste de pierres et méga-pierres voisines.
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
				if(!bordGauche(pierre) && pierreVoisineExiste(gauche)) {
					addListe(gauche, liste_voisin);
				}
				
				if(!bordDroit(pierre, taille_goban) && pierreVoisineExiste(droite)) {
					addListe(droite, liste_voisin);
				}
				
				if(pierreVoisineExiste(bas)) {
					addListe(bas, liste_voisin);
				}
			}
			
			else if(bordBas(pierre, taille_goban)) {
				if(!bordGauche(pierre) && pierreVoisineExiste(gauche)) {
					addListe(gauche, liste_voisin);
				}
				
				if(!bordDroit(pierre, taille_goban) && pierreVoisineExiste(droite)) {
					addListe(droite, liste_voisin);
				}
				
				if(pierreVoisineExiste(haut)) {
					addListe(haut, liste_voisin);
				}
			}
			
			else if(bordGauche(pierre)) {
				if(pierreVoisineExiste(haut)) {
					addListe(haut, liste_voisin);
				}
				
				if(pierreVoisineExiste(bas)) {
					addListe(bas, liste_voisin);
				}
				
				if(pierreVoisineExiste(droite)) {
					addListe(droite, liste_voisin);
				}
			}
			
			else if(bordDroit(pierre, taille_goban)) {
				if(pierreVoisineExiste(haut)) {
					addListe(haut, liste_voisin);
				}
				
				if(pierreVoisineExiste(bas)) {
					addListe(bas, liste_voisin);
				}
				
				if(pierreVoisineExiste(gauche)) {
					addListe(gauche, liste_voisin);
				}
			}
			
			else {
				if(pierreVoisineExiste(haut)) {
					addListe(haut, liste_voisin);
				}
				
				if(pierreVoisineExiste(bas)) {
					addListe(bas, liste_voisin);
				}
				
				if(pierreVoisineExiste(gauche)) {
					addListe(gauche, liste_voisin);
				}
				
				if(pierreVoisineExiste(droite)) {
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
					if(pierreVoisineExiste(gauche)) {
						addListe(gauche, liste_voisin);
					}
					if(pierreVoisineExiste(gauche2)) {
						addListe(gauche2, liste_voisin);
					}
				}
				
				if(!bordDroit(pierre, taille_goban)) {
					if(pierreVoisineExiste(droite)) {
						addListe(droite, liste_voisin);
					}
					if(pierreVoisineExiste(droite2)) {
						addListe(droite2, liste_voisin);
					}
				}
				
				if(pierreVoisineExiste(bas)) {
					addListe(bas, liste_voisin);
				}
				if(pierreVoisineExiste(bas2)) {
					addListe(bas2, liste_voisin);
				}
			}
			
			else if(bordBas(pierre, taille_goban)) {
				if(!bordGauche(pierre)) {
					if(pierreVoisineExiste(gauche)) {
						addListe(gauche, liste_voisin);
					}
					if(pierreVoisineExiste(gauche2)) {
						addListe(gauche2, liste_voisin);
					}
				}
				
				if(!bordDroit(pierre, taille_goban)) {
					if(pierreVoisineExiste(droite)) {
						addListe(droite, liste_voisin);
					}
					if(pierreVoisineExiste(droite2)) {
						addListe(droite2, liste_voisin);
					}
				}
				
				if(pierreVoisineExiste(haut)) {
					addListe(haut, liste_voisin);
				}
				if(pierreVoisineExiste(haut2)) {
					addListe(haut2, liste_voisin);
				}
			}
			
			else if(bordGauche(pierre)) {
				if(pierreVoisineExiste(haut)) {
					addListe(haut, liste_voisin);
				}
				if(pierreVoisineExiste(haut2)) {
					addListe(haut2, liste_voisin);
				}
				
				if(pierreVoisineExiste(bas)) {
					addListe(bas, liste_voisin);
				}
				if(pierreVoisineExiste(bas2)) {
					addListe(bas2, liste_voisin);
				}
				
				if(pierreVoisineExiste(droite)) {
					addListe(droite, liste_voisin);
				}
				if(pierreVoisineExiste(droite2)) {
					addListe(droite2, liste_voisin);
				}
			}
			
			else if(bordDroit(pierre, taille_goban)) {
				if(pierreVoisineExiste(haut)) {
					addListe(haut, liste_voisin);
				}
				if(pierreVoisineExiste(haut2)) {
					addListe(haut2, liste_voisin);
				}
				
				if(pierreVoisineExiste(bas)) {
					addListe(bas, liste_voisin);
				}
				if(pierreVoisineExiste(bas2)) {
					addListe(bas2, liste_voisin);
				}
				
				if(pierreVoisineExiste(gauche)) {
					addListe(gauche, liste_voisin);
				}
				if(pierreVoisineExiste(gauche2)) {
					addListe(gauche2, liste_voisin);
				}
			}
			
			else {
				if(pierreVoisineExiste(haut)) {
					addListe(haut, liste_voisin);
				}
				if(pierreVoisineExiste(haut2)) {
					addListe(haut2, liste_voisin);
				}
				
				if(pierreVoisineExiste(bas)) {
					addListe(bas, liste_voisin);
				}
				if(pierreVoisineExiste(bas2)) {
					addListe(bas2, liste_voisin);
				}
				
				if(pierreVoisineExiste(gauche)) {
					addListe(gauche, liste_voisin);
				}
				if(pierreVoisineExiste(gauche2)) {
					addListe(gauche2, liste_voisin);
				}
				
				if(pierreVoisineExiste(droite)) {
					addListe(droite, liste_voisin);
				}
				if(pierreVoisineExiste(droite2)) {
					addListe(droite2, liste_voisin);
				}
			}
		}
		
		return liste_voisin;
	}
	
	/**
	 * Permet de savoir si une pierre ou une méga-pierre existe ou non.
	 * 
	 * @param pierreEnnemi Définit la pierre ou la méga-pierre que l'on souhaite vérifier.
	 * @return Renvoie si oui ou non la pierre ou la méga-pierre existe.
	 */
	public static boolean pierreVoisineExiste(AbstractPierre pierreEnnemi) {
		if(pierreEnnemi != null) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Permet de récupérer les coordonnées d'intersections voisines vides autour d'une pierre ou d'une méga-pierre.
	 * 
	 * @param pierre Définit la pierre ou la méga-pierre que l'on souhaite regarder.
	 * @param plateau Définir le plateau de jeu sur lequel est la pierre ou la méga-pierre.
	 * @param taille_goban Définit la taille du plateau de jeu.
	 * @return Renvoie la liste de coordonnées d'intersections voisines vides.
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
				if(!bordGauche(pierre) && !pierreVoisineExiste(gauche)) {
					addListe(new Coordonnee(x, y-1), liste_voisin);
				}
				
				if(!bordDroit(pierre, taille_goban) && !pierreVoisineExiste(droite)) {
					addListe(new Coordonnee(x, y+1), liste_voisin);
				}
				
				if(!pierreVoisineExiste(bas)) {
					addListe(new Coordonnee(x+1, y), liste_voisin);
				}
			}
			
			else if(bordBas(pierre, taille_goban)) {
				if(!bordGauche(pierre) && !pierreVoisineExiste(gauche)) {
					addListe(new Coordonnee(x, y-1), liste_voisin);
				}
				
				if(!bordDroit(pierre, taille_goban) && !pierreVoisineExiste(droite)) {
					addListe(new Coordonnee(x, y+1), liste_voisin);
				}
				
				if(!pierreVoisineExiste(haut)) {
					addListe(new Coordonnee(x-1, y), liste_voisin);
				}
			}
			
			else if(bordGauche(pierre)) {
				if(!pierreVoisineExiste(haut)) {
					addListe(new Coordonnee(x-1, y), liste_voisin);
				}
				
				if(!pierreVoisineExiste(bas)) {
					addListe(new Coordonnee(x+1, y), liste_voisin);
				}
				
				if(!pierreVoisineExiste(droite)) {
					addListe(new Coordonnee(x, y+1), liste_voisin);
				}
			}
			
			else if(bordDroit(pierre, taille_goban)) {
				if(!pierreVoisineExiste(haut)) {
					addListe(new Coordonnee(x-1, y), liste_voisin);
				}
				
				if(!pierreVoisineExiste(bas)) {
					addListe(new Coordonnee(x+1, y), liste_voisin);
				}
				
				if(!pierreVoisineExiste(gauche)) {
					addListe(new Coordonnee(x, y-1), liste_voisin);
				}
			}
			
			else {
				if(!pierreVoisineExiste(haut)) {
					addListe(new Coordonnee(x-1, y), liste_voisin);
				}
				
				if(!pierreVoisineExiste(bas)) {
					addListe(new Coordonnee(x+1, y), liste_voisin);
				}
				
				if(!pierreVoisineExiste(gauche)) {
					addListe(new Coordonnee(x, y-1), liste_voisin);
				}
				
				if(!pierreVoisineExiste(droite)) {
					addListe(new Coordonnee(x, y+1), liste_voisin);
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
					if(!pierreVoisineExiste(gauche)) {
						addListe(new Coordonnee(x, y-1), liste_voisin);
					}
					if(!pierreVoisineExiste(gauche2)) {
						addListe(new Coordonnee(x+1, y-1), liste_voisin);
					}
				}
				
				if(!bordDroit(pierre, taille_goban)) {
					if(!pierreVoisineExiste(droite)) {
						addListe(new Coordonnee(x, y+2), liste_voisin);
					}
					if(!pierreVoisineExiste(droite2)) {
						addListe(new Coordonnee(x+1, y+2), liste_voisin);
					}
				}
				
				if(!pierreVoisineExiste(bas)) {
					addListe(new Coordonnee(x+2, y), liste_voisin);
				}
				if(!pierreVoisineExiste(bas2)) {
					addListe(new Coordonnee(x+2, y+1), liste_voisin);
				}
			}
			
			else if(bordBas(pierre, taille_goban)) {
				if(!bordGauche(pierre)) {
					if(!pierreVoisineExiste(gauche)) {
						addListe(new Coordonnee(x, y-1), liste_voisin);
					}
					if(!pierreVoisineExiste(gauche2)) {
						addListe(new Coordonnee(x+1, y-1), liste_voisin);
					}
				}
				
				if(!bordDroit(pierre, taille_goban)) {
					if(!pierreVoisineExiste(droite)) {
						addListe(new Coordonnee(x, y+2), liste_voisin);
					}
					if(!pierreVoisineExiste(droite2)) {
						addListe(new Coordonnee(x+1, y+2), liste_voisin);
					}
				}
				
				if(!pierreVoisineExiste(haut)) {
					addListe(new Coordonnee(x-1, y), liste_voisin);
				}
				if(!pierreVoisineExiste(haut2)) {
					addListe(new Coordonnee(x-1, y+1), liste_voisin);
				}
			}
			
			else if(bordGauche(pierre)) {
				if(!pierreVoisineExiste(haut)) {
					addListe(new Coordonnee(x-1, y), liste_voisin);
				}
				if(!pierreVoisineExiste(haut2)) {
					addListe(new Coordonnee(x-1, y+1), liste_voisin);
				}

				if(!pierreVoisineExiste(bas)) {
					addListe(new Coordonnee(x+2, y), liste_voisin);
				}
				if(!pierreVoisineExiste(bas2)) {
					addListe(new Coordonnee(x+2, y+1), liste_voisin);
				}

				if(!pierreVoisineExiste(droite)) {
					addListe(new Coordonnee(x, y+2), liste_voisin);
				}
				if(!pierreVoisineExiste(droite2)) {
					addListe(new Coordonnee(x+1, y+2), liste_voisin);
				}
			}
			
			else if(bordDroit(pierre, taille_goban)) {
				if(!pierreVoisineExiste(haut)) {
					addListe(new Coordonnee(x-1, y), liste_voisin);
				}
				if(!pierreVoisineExiste(haut2)) {
					addListe(new Coordonnee(x-1, y+1), liste_voisin);
				}

				if(!pierreVoisineExiste(bas)) {
					addListe(new Coordonnee(x+2, y), liste_voisin);
				}
				if(!pierreVoisineExiste(bas2)) {
					addListe(new Coordonnee(x+2, y+1), liste_voisin);
				}

				if(!pierreVoisineExiste(gauche)) {
					addListe(new Coordonnee(x, y-1), liste_voisin);
				}
				if(!pierreVoisineExiste(gauche2)) {
					addListe(new Coordonnee(x+1, y-1), liste_voisin);
				}
			}
			
			else {
				if(!pierreVoisineExiste(haut)) {
					addListe(new Coordonnee(x-1, y), liste_voisin);
				}
				if(!pierreVoisineExiste(haut2)) {
					addListe(new Coordonnee(x-1, y+1), liste_voisin);
				}

				if(!pierreVoisineExiste(bas)) {
					addListe(new Coordonnee(x+2, y), liste_voisin);
				}
				if(!pierreVoisineExiste(bas2)) {
					addListe(new Coordonnee(x+2, y+1), liste_voisin);
				}

				if(!pierreVoisineExiste(gauche)) {
					addListe(new Coordonnee(x, y-1), liste_voisin);
				}
				if(!pierreVoisineExiste(gauche2)) {
					addListe(new Coordonnee(x+1, y-1), liste_voisin);
				}

				if(!pierreVoisineExiste(droite)) {
					addListe(new Coordonnee(x, y+2), liste_voisin);
				}
				if(!pierreVoisineExiste(droite2)) {
					addListe(new Coordonnee(x+1, y+2), liste_voisin);
				}
			}
		}
		
		return liste_voisin;
	}
}

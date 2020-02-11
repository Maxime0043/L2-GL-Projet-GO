package traitement;

import java.util.ArrayList;

import donnees.AbstractPierre;
import donnees.Couleur;

public class Capture {
	private GoPierre gopierre;
	
	public Capture() {
		gopierre = new GoPierre();
	}
	
	/**
	 * 
	 * @param pierre
	 * @param plateau
	 * @param choix
	 * @return
	 */
	public boolean isCapture(AbstractPierre pierre, AbstractPierre[][] plateau, int choix) {
		pierre.updateLiberte(plateau, choix);
		
		if(pierre.getLiberte() > 0) {
			return false;
		}
		
		else {
			Couleur couleurPierre = pierre.getCouleur();
			Couleur couleurP = null;
			boolean debut = true;
			
			for(AbstractPierre p : gopierre.voisins(pierre, plateau, choix)) {
				if(debut) {
					couleurP = p.getCouleur();
					debut = false;
				}
				
				else {
					if(couleurP.equals(couleurPierre)) {
						return false;
					}
					
					else {
						if(!couleurP.equals(p.getCouleur())) {
							return false;
						}
					}
				}
			}
		}
		
		return true;
		
//		Couleur couleurPierre = pierre.getCouleur();
//		
//		int x = pierre.getX();
//		int y = pierre.getY();
//		
//		if(pierre.isMegaPierre()) {
//			AbstractPierre haut1 = plateau[x-1][y];
//			AbstractPierre haut2 = plateau[x-1][y+1];
//			AbstractPierre bas1 = plateau[x+2][y];
//			AbstractPierre bas2 = plateau[x+2][y+1];
//			AbstractPierre gauche1 = plateau[x][y-1];
//			AbstractPierre gauche2 = plateau[x+1][y-1];
//			AbstractPierre droite1 = plateau[x][y+2];
//			AbstractPierre droite2 = plateau[x-1][y+2];
//			
//			Couleur couleurHaut1;
//			Couleur couleurHaut2;
//			Couleur couleurBas1;
//			Couleur couleurBas2;
//			Couleur couleurGauche1;
//			Couleur couleurGauche2;
//			Couleur couleurDroite1;
//			Couleur couleurDroite2;
//			
//			if(gopierre.bordHaut(pierre)) {
//				couleurBas1 = bas1.getCouleur();
//				couleurBas2 = bas2.getCouleur();
//				couleurGauche1 = gauche1.getCouleur();
//				couleurGauche2 = gauche2.getCouleur();
//				couleurDroite1 = droite1.getCouleur();
//				couleurDroite2 = droite2.getCouleur();
//				
//				if(gopierre.bordGauche(pierre)) {
//					if(couleurPierre.equals(couleurBas1) ||couleurPierre.equals(couleurBas2) ||  couleurPierre.equals(couleurDroite1) || couleurPierre.equals(couleurDroite2)) {
//						return false;
//					}
//					if(couleurBas1.equals(couleurBas2) && couleurBas1.equals(couleurDroite1) && couleurBas1.equals(couleurDroite2)) {
//						return true;
//					}
//				}
//				else if(gopierre.bordDroit(pierre, choix)) {
//					if(couleurPierre.equals(couleurBas1) ||couleurPierre.equals(couleurBas2) ||  couleurPierre.equals(couleurGauche1) || couleurPierre.equals(couleurGauche2)) {
//						return false;
//					}
//					if(couleurBas1.equals(couleurBas2) && couleurBas1.equals(couleurGauche1) && couleurBas1.equals(couleurGauche2)) {
//						return true;
//					}
//				}
//				else {
//					if(couleurPierre.equals(couleurBas1) ||couleurPierre.equals(couleurBas2) ||  couleurPierre.equals(couleurGauche1) || 
//							couleurPierre.equals(couleurGauche2) ||  couleurPierre.equals(couleurDroite1) || couleurPierre.equals(couleurDroite2)) {
//						return false;
//					}
//					if(couleurBas1.equals(couleurBas2) && couleurBas1.equals(couleurGauche1) && couleurBas1.equals(couleurGauche2) && couleurBas1.equals(couleurDroite1) && couleurBas1.equals(couleurDroite2)) {
//						return true;
//					}
//				}
//			}
//			
//			else if(gopierre.bordBas(pierre, choix)) {
//				couleurHaut1 = haut1.getCouleur();
//				couleurHaut2 = haut2.getCouleur();
//				couleurGauche1 = gauche1.getCouleur();
//				couleurGauche2 = gauche2.getCouleur();
//				couleurDroite1 = droite1.getCouleur();
//				couleurDroite2 = droite2.getCouleur();
//				 
//				if(gopierre.bordGauche(pierre)) {
//					if(couleurPierre.equals(couleurHaut1) ||couleurPierre.equals(couleurHaut2) ||  couleurPierre.equals(couleurDroite1) || couleurPierre.equals(couleurDroite2)) {
//						return false;
//					}
//					if(couleurHaut1.equals(couleurHaut2) && couleurHaut1.equals(couleurDroite1) && couleurHaut1.equals(couleurDroite2)) {
//						return true;
//					}
//				}
//				else if(gopierre.bordDroit(pierre, choix)) {
//					if(couleurPierre.equals(couleurHaut1) ||couleurPierre.equals(couleurHaut2) ||  couleurPierre.equals(couleurGauche1) || couleurPierre.equals(couleurGauche2)) {
//						return false;
//					}
//					if(couleurHaut1.equals(couleurHaut2) && couleurHaut1.equals(couleurGauche1) && couleurHaut1.equals(couleurGauche2)) {
//						return true;
//					}
//				}
//				else {
//					if(couleurPierre.equals(couleurHaut1) ||couleurPierre.equals(couleurHaut2) ||  couleurPierre.equals(couleurGauche1) || 
//							couleurPierre.equals(couleurGauche2) ||  couleurPierre.equals(couleurDroite1) || couleurPierre.equals(couleurDroite2)) {
//						return false;
//					}
//					if(couleurHaut1.equals(couleurHaut2) && couleurHaut1.equals(couleurGauche1) && couleurHaut1.equals(couleurGauche2) && couleurHaut1.equals(couleurDroite1) && couleurHaut1.equals(couleurDroite2)) {
//						return true;
//					}
//				}	
//			}
//			
//			else if(gopierre.bordGauche(pierre)) {
//				couleurHaut1 = haut1.getCouleur();
//				couleurHaut2 = haut2.getCouleur();
//				couleurBas1 = bas1.getCouleur();
//				couleurBas2 = bas2.getCouleur();
//				couleurDroite1 = droite1.getCouleur();
//				couleurDroite2 = droite2.getCouleur();
//				
//				if(couleurPierre.equals(couleurHaut1) ||couleurPierre.equals(couleurHaut2) ||  couleurPierre.equals(couleurBas1) || 
//						couleurPierre.equals(couleurBas2) ||  couleurPierre.equals(couleurDroite1) || couleurPierre.equals(couleurDroite2)) {
//					return false;
//				}
//				if(couleurHaut1.equals(couleurHaut2) && couleurHaut1.equals(couleurBas1) && couleurHaut1.equals(couleurBas2) && couleurHaut1.equals(couleurDroite1) && couleurHaut1.equals(couleurDroite2)) {
//					return true;
//				}
//			}
//			
//			else if(gopierre.bordDroit(pierre, choix)) {
//				couleurHaut1 = haut1.getCouleur();
//				couleurHaut2 = haut2.getCouleur();
//				couleurBas1 = bas1.getCouleur();
//				couleurBas2 = bas2.getCouleur();
//				couleurGauche1 = gauche1.getCouleur();
//				couleurGauche2 = gauche2.getCouleur();
//				
//				if(couleurPierre.equals(couleurHaut1) ||couleurPierre.equals(couleurHaut2) ||  couleurPierre.equals(couleurBas1) || 
//						couleurPierre.equals(couleurBas2) ||  couleurPierre.equals(couleurGauche1) || couleurPierre.equals(couleurGauche2)) {
//					return false;
//				}
//				if(couleurHaut1.equals(couleurHaut2) && couleurHaut1.equals(couleurBas1) && couleurHaut1.equals(couleurBas2) && couleurHaut1.equals(couleurGauche1) && couleurHaut1.equals(couleurGauche2)) {
//					return true;
//				}
//			}
//			
//			else {
//				couleurHaut1 = haut1.getCouleur();
//				couleurHaut2 = haut2.getCouleur();
//				couleurBas1 = bas1.getCouleur();
//				couleurBas2 = bas2.getCouleur();
//				couleurGauche1 = gauche1.getCouleur();
//				couleurGauche2 = gauche2.getCouleur();
//				couleurDroite1 = droite1.getCouleur();
//				couleurDroite2 = droite2.getCouleur();
//				
//				if(couleurPierre.equals(couleurHaut1) ||couleurPierre.equals(couleurHaut2) ||  couleurPierre.equals(couleurBas1) || couleurPierre.equals(couleurBas2) ||  
//						couleurPierre.equals(couleurGauche1) || couleurPierre.equals(couleurGauche2) || couleurPierre.equals(couleurDroite1) || couleurPierre.equals(couleurDroite2)) {
//					return false;
//				}
//				if(couleurHaut1.equals(couleurHaut2) && couleurHaut1.equals(couleurBas1) && couleurHaut1.equals(couleurBas2) && couleurHaut1.equals(couleurGauche1) && 
//						couleurHaut1.equals(couleurGauche2) && couleurHaut1.equals(couleurDroite1) && couleurHaut1.equals(couleurDroite2)) {
//					return true;
//				}
//			}
//		}
//		
//		else {
//			AbstractPierre haut = plateau[x-1][y];
//			AbstractPierre bas = plateau[x+1][y];
//			AbstractPierre gauche = plateau[x][y-1];
//			AbstractPierre droite = plateau[x][y+1];
//			
//			Couleur couleurHaut;
//			Couleur couleurBas;
//			Couleur couleurGauche;
//			Couleur couleurDroite;
//			/*
//			couleurHaut = haut.getCouleur();
//			couleurBas = bas.getCouleur();
//			couleurGauche = gauche.getCouleur();
//			couleurDroite = droite.getCouleur();
//			*/
//			
//			if(gopierre.bordHaut(pierre)) {
//				couleurBas = bas.getCouleur();
//				couleurGauche = gauche.getCouleur();
//				couleurDroite = droite.getCouleur();
//				
//				if(gopierre.bordGauche(pierre)) {
//					if(couleurPierre.equals(couleurBas) || couleurPierre.equals(couleurDroite)) {
//						return false;
//					}
//					if(couleurBas.equals(couleurDroite)) {
//						return true;
//					}
//				}
//				else if(gopierre.bordDroit(pierre, choix)){
//					if(couleurPierre.equals(couleurBas) || couleurPierre.equals(couleurGauche)) {
//						return false;
//					}
//					
//					if(couleurBas.equals(couleurGauche)) {
//						return true;
//					}					
//				}
//				else {
//					if(couleurPierre.equals(couleurBas) || couleurPierre.equals(couleurGauche) || couleurPierre.equals(couleurDroite)) {
//						return false;
//					}
//					if(couleurBas.equals(couleurGauche) && couleurBas.equals(couleurDroite)) {
//						return true;
//					}
//				}
//			}
//			
//			else if(gopierre.bordBas(pierre, choix)) {
//				couleurHaut = haut.getCouleur();
//				couleurGauche = gauche.getCouleur();
//				couleurDroite = droite.getCouleur();
//				
//				if(gopierre.bordGauche(pierre)) {
//					if(couleurPierre.equals(couleurHaut) || couleurPierre.equals(couleurDroite)) {
//						return false;
//					}
//					if(couleurHaut.equals(couleurDroite)) {
//						return true;
//					}
//				}
//				else if(gopierre.bordDroit(pierre, choix)){
//					if(couleurPierre.equals(couleurHaut) || couleurPierre.equals(couleurGauche)) {
//						return false;
//					}
//					
//					if(couleurHaut.equals(couleurGauche)) {
//						return true;
//					}					
//				}
//				else {
//					if(couleurPierre.equals(couleurHaut) || couleurPierre.equals(couleurGauche) || couleurPierre.equals(couleurDroite)) {
//						return false;
//					}
//					if(couleurHaut.equals(couleurGauche) && couleurHaut.equals(couleurDroite)) {
//						return true;
//					}
//				}
//			}
//			
//			else if(gopierre.bordGauche(pierre)) {
//				couleurHaut = haut.getCouleur();
//				couleurBas = bas.getCouleur();
//				couleurDroite = droite.getCouleur();
//				
//				if(couleurPierre.equals(couleurHaut) || couleurPierre.equals(couleurBas) || couleurPierre.equals(couleurDroite)) {
//					return false;
//				}
//				if(couleurHaut.equals(couleurBas) && couleurHaut.equals(couleurDroite)) {
//					return true;
//				}
//			}
//			
//			else if(gopierre.bordDroit(pierre, choix)) {
//				couleurHaut = haut.getCouleur();
//				couleurBas = bas.getCouleur();
//				couleurGauche = gauche.getCouleur();
//				
//				if(couleurPierre.equals(couleurHaut) || couleurPierre.equals(couleurBas) || couleurPierre.equals(couleurGauche)) {
//					return false;
//				}
//				if(couleurHaut.equals(couleurBas) && couleurHaut.equals(couleurGauche)) {
//					return true;
//				}
//			}
//			
//			else {
//				couleurHaut = haut.getCouleur();
//				couleurBas = bas.getCouleur();
//				couleurGauche = gauche.getCouleur();
//				couleurDroite = droite.getCouleur();
//				
//				if(couleurPierre.equals(couleurHaut) || couleurPierre.equals(couleurBas) || couleurPierre.equals(couleurGauche) || couleurPierre.equals(couleurDroite)) {
//					return false;
//				}
//				if(couleurHaut.equals(couleurBas) && couleurHaut.equals(couleurGauche) && couleurHaut.equals(couleurDroite)) {
//					return true;
//				}
//			}
//		}
//		
//		return false;
	}
	
	/**
	 * 
	 * @param chaine
	 * @param plateau
	 * @param choix
	 * @return
	 */
	public boolean isCapture(ArrayList<AbstractPierre> chaine, AbstractPierre[][] plateau, int choix) {
		ArrayList<AbstractPierre> voisin;
		ArrayList<Couleur> couleurPierres = new ArrayList<Couleur>();
		
		for(AbstractPierre pierre : chaine) {
			if(pierre.getLiberte() > 0) {
				return false;
			}
			
			voisin = gopierre.voisins(pierre, plateau, choix);
			
			for(AbstractPierre pierreVoisine : voisin) {
				if(couleurPierres.contains(pierreVoisine.getCouleur())) {
					couleurPierres.add(pierreVoisine.getCouleur());
				}
			}
			
			if(couleurPierres.size() > 1) {
				return false;
			}
		}
		
		return true;
	}
}

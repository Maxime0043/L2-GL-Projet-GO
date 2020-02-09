package traitement;

import java.util.ArrayList;

import donnees.AbstractPierre;

public class Capture {
	private GoPierre gopierre;
	
	public Capture() {
		gopierre = new GoPierre();
	}
	
	public boolean isCapture(AbstractPierre pierre, AbstractPierre[][] plateau, int choix) {
		if(pierre.getLiberte() > 0) {
			return false;
		}
		
		String couleurPierre = pierre.getCouleur();
		
		int x = pierre.getX();
		int y = pierre.getY();
		
		if(pierre.isMegaPierre()) {
			AbstractPierre haut1 = plateau[x-1][y];
			AbstractPierre haut2 = plateau[x-1][y+1];
			AbstractPierre bas1 = plateau[x+2][y];
			AbstractPierre bas2 = plateau[x+2][y+1];
			AbstractPierre gauche1 = plateau[x][y-1];
			AbstractPierre gauche2 = plateau[x+1][y-1];
			AbstractPierre droite1 = plateau[x][y+2];
			AbstractPierre droite2 = plateau[x-1][y+2];
			
			String couleurHaut1 = haut1.getCouleur();
			String couleurHaut2 = haut2.getCouleur();
			String couleurBas1 = bas1.getCouleur();
			String couleurBas2 = bas2.getCouleur();
			String couleurGauche1 = gauche1.getCouleur();
			String couleurGauche2 = gauche2.getCouleur();
			String couleurDroite1 = droite1.getCouleur();
			String couleurDroite2 = droite2.getCouleur();
			
			if(gopierre.bordHaut(pierre)) {
				if(gopierre.bordGauche(pierre)) {
					if(couleurPierre.equals(couleurBas1) ||couleurPierre.equals(couleurBas2) ||  couleurPierre.equals(couleurDroite1) || couleurPierre.equals(couleurDroite2)) {
						return false;
					}
					if(couleurBas1.equals(couleurBas2) && couleurBas1.equals(couleurDroite1) && couleurBas1.equals(couleurDroite2)) {
						return true;
					}
				}
				else if(gopierre.bordDroit(pierre, choix)) {
					if(couleurPierre.equals(couleurBas1) ||couleurPierre.equals(couleurBas2) ||  couleurPierre.equals(couleurGauche1) || couleurPierre.equals(couleurGauche2)) {
						return false;
					}
					if(couleurBas1.equals(couleurBas2) && couleurBas1.equals(couleurGauche1) && couleurBas1.equals(couleurGauche2)) {
						return true;
					}
				}
				else {
					if(couleurPierre.equals(couleurBas1) ||couleurPierre.equals(couleurBas2) ||  couleurPierre.equals(couleurGauche1) || 
							couleurPierre.equals(couleurGauche2) ||  couleurPierre.equals(couleurDroite1) || couleurPierre.equals(couleurDroite2)) {
						return false;
					}
					if(couleurBas1.equals(couleurBas2) && couleurBas1.equals(couleurGauche1) && couleurBas1.equals(couleurGauche2) && couleurBas1.equals(couleurDroite1) && couleurBas1.equals(couleurDroite2)) {
						return true;
					}
				}
			}
			
			else if(gopierre.bordBas(pierre, choix)) {
				if(gopierre.bordGauche(pierre)) {
					if(couleurPierre.equals(couleurHaut1) ||couleurPierre.equals(couleurHaut2) ||  couleurPierre.equals(couleurDroite1) || couleurPierre.equals(couleurDroite2)) {
						return false;
					}
					if(couleurHaut1.equals(couleurHaut2) && couleurHaut1.equals(couleurDroite1) && couleurHaut1.equals(couleurDroite2)) {
						return true;
					}
				}
				else if(gopierre.bordDroit(pierre, choix)) {
					if(couleurPierre.equals(couleurHaut1) ||couleurPierre.equals(couleurHaut2) ||  couleurPierre.equals(couleurGauche1) || couleurPierre.equals(couleurGauche2)) {
						return false;
					}
					if(couleurHaut1.equals(couleurHaut2) && couleurHaut1.equals(couleurGauche1) && couleurHaut1.equals(couleurGauche2)) {
						return true;
					}
				}
				else {
					if(couleurPierre.equals(couleurHaut1) ||couleurPierre.equals(couleurHaut2) ||  couleurPierre.equals(couleurGauche1) || 
							couleurPierre.equals(couleurGauche2) ||  couleurPierre.equals(couleurDroite1) || couleurPierre.equals(couleurDroite2)) {
						return false;
					}
					if(couleurHaut1.equals(couleurHaut2) && couleurHaut1.equals(couleurGauche1) && couleurHaut1.equals(couleurGauche2) && couleurHaut1.equals(couleurDroite1) && couleurHaut1.equals(couleurDroite2)) {
						return true;
					}
				}	
			}
			
			else if(gopierre.bordGauche(pierre)) {
				if(couleurPierre.equals(couleurHaut1) ||couleurPierre.equals(couleurHaut2) ||  couleurPierre.equals(couleurBas1) || 
						couleurPierre.equals(couleurBas2) ||  couleurPierre.equals(couleurDroite1) || couleurPierre.equals(couleurDroite2)) {
					return false;
				}
				if(couleurHaut1.equals(couleurHaut2) && couleurHaut1.equals(couleurBas1) && couleurHaut1.equals(couleurBas2) && couleurHaut1.equals(couleurDroite1) && couleurHaut1.equals(couleurDroite2)) {
					return true;
				}
			}
			
			else if(gopierre.bordDroit(pierre, choix)) {
				if(couleurPierre.equals(couleurHaut1) ||couleurPierre.equals(couleurHaut2) ||  couleurPierre.equals(couleurBas1) || 
						couleurPierre.equals(couleurBas2) ||  couleurPierre.equals(couleurGauche1) || couleurPierre.equals(couleurGauche2)) {
					return false;
				}
				if(couleurHaut1.equals(couleurHaut2) && couleurHaut1.equals(couleurBas1) && couleurHaut1.equals(couleurBas2) && couleurHaut1.equals(couleurGauche1) && couleurHaut1.equals(couleurGauche2)) {
					return true;
				}
			}
			
			else {
				if(couleurPierre.equals(couleurHaut1) ||couleurPierre.equals(couleurHaut2) ||  couleurPierre.equals(couleurBas1) || couleurPierre.equals(couleurBas2) ||  
						couleurPierre.equals(couleurGauche1) || couleurPierre.equals(couleurGauche2) || couleurPierre.equals(couleurDroite1) || couleurPierre.equals(couleurDroite2)) {
					return false;
				}
				if(couleurHaut1.equals(couleurHaut2) && couleurHaut1.equals(couleurBas1) && couleurHaut1.equals(couleurBas2) && couleurHaut1.equals(couleurGauche1) && 
						couleurHaut1.equals(couleurGauche2) && couleurHaut1.equals(couleurDroite1) && couleurHaut1.equals(couleurDroite2)) {
					return true;
				}
			}
		}
		
		else {
			AbstractPierre haut = plateau[x-1][y];
			AbstractPierre bas = plateau[x+1][y];
			AbstractPierre gauche = plateau[x][y-1];
			AbstractPierre droite = plateau[x][y+1];
			
			String couleurHaut = haut.getCouleur();
			String couleurBas = bas.getCouleur();
			String couleurGauche = gauche.getCouleur();
			String couleurDroite = droite.getCouleur();
			
			
			if(gopierre.bordHaut(pierre)) {
				if(gopierre.bordGauche(pierre)) {
					if(couleurPierre.equals(couleurBas) || couleurPierre.equals(couleurDroite)) {
						return false;
					}
					if(couleurBas.equals(couleurDroite)) {
						return true;
					}
				}
				else if(gopierre.bordDroit(pierre, choix)){
					if(couleurPierre.equals(couleurBas) || couleurPierre.equals(couleurGauche)) {
						return false;
					}
					
					if(couleurBas.equals(couleurGauche)) {
						return true;
					}					
				}
				else {
					if(couleurPierre.equals(couleurBas) || couleurPierre.equals(couleurGauche) || couleurPierre.equals(couleurDroite)) {
						return false;
					}
					if(couleurBas.equals(couleurGauche) && couleurBas.equals(couleurDroite)) {
						return true;
					}
				}
			}
			
			else if(gopierre.bordBas(pierre, choix)) {
				if(gopierre.bordGauche(pierre)) {
					if(couleurPierre.equals(couleurHaut) || couleurPierre.equals(couleurDroite)) {
						return false;
					}
					if(couleurHaut.equals(couleurDroite)) {
						return true;
					}
				}
				else if(gopierre.bordDroit(pierre, choix)){
					if(couleurPierre.equals(couleurHaut) || couleurPierre.equals(couleurGauche)) {
						return false;
					}
					
					if(couleurBas.equals(couleurGauche)) {
						return true;
					}					
				}
				else {
					if(couleurPierre.equals(couleurHaut) || couleurPierre.equals(couleurGauche) || couleurPierre.equals(couleurDroite)) {
						return false;
					}
					if(couleurHaut.equals(couleurGauche) && couleurHaut.equals(couleurDroite)) {
						return true;
					}
				}
			}
			
			else if(gopierre.bordGauche(pierre)) {
				if(couleurPierre.equals(couleurHaut) || couleurPierre.equals(couleurBas) || couleurPierre.equals(couleurDroite)) {
					return false;
				}
				if(couleurHaut.equals(couleurBas) && couleurHaut.equals(couleurDroite)) {
					return true;
				}
			}
			
			else if(gopierre.bordDroit(pierre, choix)) {
				if(couleurPierre.equals(couleurHaut) || couleurPierre.equals(couleurBas) || couleurPierre.equals(couleurGauche)) {
					return false;
				}
				if(couleurHaut.equals(couleurBas) && couleurHaut.equals(couleurGauche)) {
					return true;
				}
			}
			
			else {
				if(couleurPierre.equals(couleurHaut) || couleurPierre.equals(couleurBas) || couleurPierre.equals(couleurGauche) || couleurPierre.equals(couleurDroite)) {
					return false;
				}
				if(couleurHaut.equals(couleurBas) && couleurHaut.equals(couleurGauche) && couleurHaut.equals(couleurDroite)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean isCapture(ArrayList<AbstractPierre> chaine, AbstractPierre[][] plateau, int choix) {
		ArrayList<AbstractPierre> voisin;
		ArrayList<String> couleurPierres = new ArrayList<String>();
		
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

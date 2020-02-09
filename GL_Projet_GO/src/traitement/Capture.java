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
		
		AbstractPierre haut = plateau[x-1][y];
		AbstractPierre bas = plateau[x+1][y];
		AbstractPierre gauche = plateau[x][y-1];
		AbstractPierre droite = plateau[x][y+1];
		
		String couleurHaut = haut.getCouleur();
		String couleurBas = bas.getCouleur();
		String couleurGauche = gauche.getCouleur();
		String couleurDroite = droite.getCouleur();
		
		if(pierre.isMegaPierre()) {
			
		}
		
		else {
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

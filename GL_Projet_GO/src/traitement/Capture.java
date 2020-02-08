package traitement;

import donnees.AbstractPierre;

public class Capture {
	public Capture() {

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
			if(couleurPierre.equals(couleurHaut) || couleurPierre.equals(couleurBas) || couleurPierre.equals(couleurGauche) || couleurPierre.equals(couleurDroite)) {
				return false;
			}
			
			if(couleurHaut.equals(couleurBas) && couleurHaut.equals(couleurGauche) && couleurHaut.equals(couleurDroite)) {
				return true;
			}
		}
		
		return false;
	}
}

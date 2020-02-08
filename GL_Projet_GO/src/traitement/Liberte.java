package traitement;

import donnees.AbstractPierre;
import donnees.Couleur;
import donnees.ParametrePartie;

public class Liberte {
	int nb_liberte;
	AbstractPierre pierre;
	
	public Liberte(AbstractPierre pierre) {
		this.pierre = pierre;
		
		if(pierre.isMegaPierre())
			nb_liberte = 8;
		else
			nb_liberte = 4;
	}

	public int getLiberte() {
		return nb_liberte;
	}
	
	public void updateLiberte(AbstractPierre[][] plateau, int choix) {
		Couleur couleur = pierre.getCouleur();
		
		if(pierre.isMegaPierre()) {
			
		}
		
		else {
			if(pierre.getX() == 0) {
				nb_liberte--;
				
				if(pierre.getY() == 0) {
					nb_liberte--;
					
					
				}
				
				
			}
			
			else if(pierre.getY() == 0) {
				nb_liberte--;
				
				if(pierre.getX() == 0) {
					nb_liberte--;
				}
			}
			
			else if(pierre.getX() == ParametrePartie.TAILLE_GOBAN[choix]) {
				nb_liberte--;
				
				if(pierre.getY() == ParametrePartie.TAILLE_GOBAN[choix]) {
					nb_liberte--;
				}
			}
			
			else if(pierre.getY() == ParametrePartie.TAILLE_GOBAN[choix]) {
				nb_liberte--;
				
				if(pierre.getX() == ParametrePartie.TAILLE_GOBAN[choix]) {
					nb_liberte--;
				}
			}
		}
	}
}

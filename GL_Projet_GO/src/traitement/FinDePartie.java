package traitement;

import java.util.ArrayList;
import java.util.HashMap;

import donnees.AbstractPierre;
import donnees.Coordonnee;


public class FinDePartie {

	int taille_goban;
	
	public FinDePartie(int taille_goban) {
		this.taille_goban = taille_goban;
	}
	
	public void setChaineTwoEye(HashMap<Integer, Chaine> hmChaine, AbstractPierre[][] plateau) {
		
		ArrayList<Coordonnee> interVide = new ArrayList<Coordonnee>();
		ArrayList<Coordonnee> listeInterVide = new ArrayList<Coordonnee>();
		
		
		for(Chaine chaine : hmChaine.values()) {
			if(chaine.getChaine().size() >= 6) {
				for(AbstractPierre pierre : chaine.getChaine()) {
					interVide = GoPierre.intersectionVide(pierre, plateau, taille_goban);
					if (!interVide.isEmpty()) {
						for(Coordonnee coord : interVide) {
							listeInterVide.add(coord);
						}
					}
				}
			}
		}	
	}
}


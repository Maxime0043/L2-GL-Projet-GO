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
		ArrayList<Coordonnee> finalList = new ArrayList<Coordonnee>();
		int compteur;
		boolean ajouter = true;
		
		
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
			
				interVide.clear();
				interVide.addAll(listeInterVide);
				for(Coordonnee coord : listeInterVide) {
					compteur = 0;
					for(Coordonnee c : interVide) {
						if(coord.getX() == c.getX() && coord.getY() == c.getY()) {
							compteur ++;
						}
					}
					if(compteur >= 2) {
						for (Coordonnee fl : finalList) {
							if(fl.getX() == coord.getX() && fl.getY() == coord.getY()) {
								ajouter = false;
							}
						}
						if(ajouter) {
							finalList.add(coord);
						}
						ajouter = true;
					}
				}
				for(Coordonnee c : finalList) {
					System.out.println("\n" + c.getX() + "-" + c.getY() + "\n");
				}
			}
	
		}	
	}
}


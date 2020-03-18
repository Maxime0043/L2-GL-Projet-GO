package traitement;

import java.util.ArrayList;
import java.util.HashMap;

import donnees.AbstractPierre;
import donnees.Coordonnee;
import traitement.Goban;


public class FinDePartie {

	private int taille_goban;
	
	private Goban goban;
	
	public FinDePartie(int taille_goban, Goban goban) {
		this.taille_goban = taille_goban;
		this.goban = goban;
	}
	
	public void setChaineTwoEye(HashMap<Integer, Chaine> hmChaine, AbstractPierre[][] plateau) {
		
		ArrayList<Coordonnee> interVide = new ArrayList<Coordonnee>();
		ArrayList<Coordonnee> listeInterVide = new ArrayList<Coordonnee>();
		ArrayList<Coordonnee> finalList = new ArrayList<Coordonnee>();
		
		boolean ajouter = true;
		
		int compteur;
		int compteurYeux;
		int bordHaut = 0;
		int bordBas = taille_goban - 1;
		int bordGauche = 0;
		int bordDroit = taille_goban - 1;
		
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
				
				if(finalList.size() > 1) {
					compteurYeux = finalList.size();
					for(Coordonnee c : finalList) {
						if(c.getX() > bordHaut) {
							if(goban.existPierre(c.getX()-1, c.getY())) {
								if(goban.getPierre(c.getX()-1, c.getY()).getCouleur() != chaine.getCouleur()) {
									compteurYeux--;
								}
							}
							else {
								compteurYeux--;
							}
						}
						else if(c.getX() < bordBas) {
							if(goban.existPierre(c.getX()+1, c.getY())) {
								if(goban.getPierre(c.getX()+1, c.getY()).getCouleur() != chaine.getCouleur()) {
									compteurYeux--;
								}
							}
							else {
								compteurYeux--;
							}
						}
						else if(c.getY() > bordGauche) {
							if(goban.existPierre(c.getX(), c.getY()-1)) {
								if(goban.getPierre(c.getX(), c.getY()-1).getCouleur() != chaine.getCouleur()) {
									compteurYeux--;
								}
							}
							else {
								compteurYeux--;
							}
						}
						else if(c.getY() < bordDroit) {
							if(goban.existPierre(c.getX(), c.getY()+1)) {
								if(goban.getPierre(c.getX(), c.getY()+1).getCouleur() != chaine.getCouleur()) {
									compteurYeux--;
								}
							}
							else {
								compteurYeux--;
							}
						}
					}
					
					if(compteurYeux >= 2) {
						chaine.setTwoEyes(true);
					}
				}
			}
			
			interVide.clear();
			listeInterVide.clear();
			finalList.clear();
		}
	}
}



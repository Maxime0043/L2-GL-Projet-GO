package donnees;

import java.util.HashMap;

import traitement.Chaine;

public class Goban {
	
	private AbstractPierre[][] plateau;
	private HashMap<String, Chaine> hmChaine;
	private HashMap<String, Score> scores;
	
	private int nb_Noir;
	private int nb_Blanc;
	private int nb_Rouge;
	
	public Goban(int choix) {
		plateau = new AbstractPierre[ParametrePartie.TAILLE_GOBAN[choix]][ParametrePartie.TAILLE_GOBAN[choix]];
		hmChaine = new HashMap <String, Chaine>();
		scores = new HashMap <String, Score>();
	}
	
	public void initGoban(int choix) {
		for(int i = 0 ; i < ParametrePartie.TAILLE_GOBAN[choix] ; i++) {
			for(int j = 0 ; j < ParametrePartie.TAILLE_GOBAN[choix] ; j++) {
				plateau[i][j] = null;
			}
		}
		
		nb_Noir = 0;
		nb_Blanc = 0;
		nb_Rouge = 0;
	}
	
	public boolean existPierre (Coordonnee coord) {
		if (plateau[coord.getX()][coord.getY()] != null) {
			return true;
		}
		else 
			return false;
	}
	
	public void addPierre(AbstractPierre pierre) {
		int x = pierre.getX();
		int y = pierre.getY();
		
		if(plateau[x][y] == null) {
			plateau[x][y] = pierre;
			
			if(pierre.getCouleur().equals(Couleur.NOIR.getCouleur())) {
				nb_Noir++;
			}
			else if(pierre.getCouleur().equals(Couleur.BLANC.getCouleur())) {
				nb_Blanc++;
			}
			else if(pierre.getCouleur().equals(Couleur.ROUGE.getCouleur())) {
				nb_Rouge++;
			}
		}
	}
	
	public void removePierre(AbstractPierre pierre) {
		int x = pierre.getX();
		int y = pierre.getY();
		
		if(plateau[x][y] != null) {
			plateau[x][y] = null;
		}
		
		if(pierre.getCouleur().equals(Couleur.NOIR.getCouleur())) {
			nb_Noir--;
		}
		else if(pierre.getCouleur().equals(Couleur.BLANC.getCouleur())) {
			nb_Blanc--;
		}
		else if(pierre.getCouleur().equals(Couleur.ROUGE.getCouleur())) {
			nb_Rouge--;
		}
	}
	
	public int getNbNoir() {
		return nb_Noir;
	}
	
	public int getNbBlanc() {
		return nb_Blanc;
	}
	
	public int getNbRouge() {
		return nb_Rouge;
	}
}
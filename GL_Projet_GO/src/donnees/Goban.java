package donnees;

import java.util.ArrayList;
import java.util.HashMap;

import traitement.Capture;
import traitement.Chaine;

public class Goban {
	
	private AbstractPierre[][] plateau;
	private HashMap<String, Chaine> hmChaine;
	private HashMap<Couleur, Score> scores;
	private Capture capture;
	
	int taille_goban;
	int choix;
	
	private int nb_Noir;
	private int nb_Blanc;
	private int nb_Rouge;
	
	public Goban(int choix) {
		taille_goban = ParametrePartie.TAILLE_GOBAN[choix];
		this.choix = choix;
		
		plateau = new AbstractPierre[taille_goban][taille_goban];
		hmChaine = new HashMap <String, Chaine>();
		scores = new HashMap <Couleur, Score>();
		capture = new Capture();
	}
	
	public void initGoban(int choix) {
		for(int i = 0 ; i < taille_goban ; i++) {
			for(int j = 0 ; j < taille_goban ; j++) {
				plateau[i][j] = null;
			}
		}
		
		hmChaine.clear();
		scores.clear();
		
		nb_Noir = 0;
		nb_Blanc = 0;
		nb_Rouge = 0;
	}
	
	public boolean existPierre(int x, int y) {
		if (plateau[x][y] != null) {
			return true;
		}
		else 
			return false;
	}
	
	public AbstractPierre getPierre(int x, int y) {
		AbstractPierre pierre = plateau[x][y];
		pierre.updateLiberte(plateau, choix);
		
		return pierre;
	}
	
	public void addPierre(AbstractPierre pierre) {
		int x = pierre.getX();
		int y = pierre.getY();
		
		if(plateau[x][y] == null) {
			plateau[x][y] = pierre;
			
			if(pierre.getCouleur().equals(Couleur.NOIR)) {
				nb_Noir++;
			}
			else if(pierre.getCouleur().equals(Couleur.BLANC)) {
				nb_Blanc++;
			}
			else if(pierre.getCouleur().equals(Couleur.ROUGE)) {
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
		
		if(pierre.getCouleur().equals(Couleur.NOIR)) {
			nb_Noir--;
		}
		else if(pierre.getCouleur().equals(Couleur.BLANC)) {
			nb_Blanc--;
		}
		else if(pierre.getCouleur().equals(Couleur.ROUGE)) {
			nb_Rouge--;
		}
	}
	
	public boolean isPierreCapture(AbstractPierre pierre, int choix) {
		return capture.isCapture(pierre, plateau, choix);
	}
	
	public boolean isPierreCapture(ArrayList<AbstractPierre> chaine, int choix) {
		return capture.isCapture(chaine, plateau, choix);
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
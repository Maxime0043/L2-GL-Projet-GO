package data;

import java.util.HashMap;

public class Goban {
	
	private AbstractPierre[][] plateau;
	private HashMap <String, Chaine> hmChaine;
	private HashMap <String, Score> scores;
	
	public Goban(int taille) {
		plateau = new AbstractPierre[taille][taille];
		hmChaine = new HashMap <String, Chaine>();
		scores = new HashMap <String, Score>();
	}
	
	public boolean existPierre (Coordonnee coord) {
		if (plateau[coord.getX()][coord.getY()] != null) {
			return true;
		}
		else return false;
	}
}
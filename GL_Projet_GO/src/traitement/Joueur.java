package traitement;

import donnees.Couleur;
import donnees.Score;

public class Joueur {

	private Score score;
	private Couleur couleur;
	
	public Joueur(Couleur c) {
		score = new Score();
		couleur = c;
	}
	
	public int getScore() {
		return score.getScore();
	}
	
	public void addScore() {
		score.addScore();
	}
	
	public Couleur getCouleur() {
		return couleur;
	}
}

package traitement;

import java.util.ArrayList;

import donnees.AbstractPierre;
import donnees.Couleur;
import donnees.Score;

public class Joueur {
	
	private Score score;
	private Couleur couleur;
	private boolean isOrdi;
	private int nb_megaPierre = 1;
	
	private ArrayList<AbstractPierre> listePierre;
	
	public Joueur(Couleur c, boolean isOrdi) {
		score = new Score();
		couleur = c;
		this.isOrdi = isOrdi;
		
		listePierre = new ArrayList<AbstractPierre>();
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
	
	public boolean isOrdi() {
		return isOrdi;
	}
	
	public boolean hasMegaPierre() {
		return nb_megaPierre > 0;
	}
	
	public void playMegaPierre() {
		nb_megaPierre--;
	}
	
	public void addPierre(AbstractPierre pierre) {
		if(!listePierre.contains(pierre)) {
			listePierre.add(pierre);
		}
	}
	
	public void removePierre(AbstractPierre pierre) {
		if(listePierre.contains(pierre)) {
			listePierre.remove(pierre);
		}
	}
}

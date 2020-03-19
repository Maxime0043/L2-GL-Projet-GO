package traitement;

import java.util.ArrayList;

import donnees.Cercle;
import donnees.Couleur;

public class Moteur {
	MoteurPierre moteur_pierre;
	
	public Moteur(MoteurPierre moteur_pierre) {
		this.moteur_pierre = moteur_pierre;
	}
	
	public void run() {
		boolean isRunning = true;
		
		while(isRunning) {
			if(currentJoueur().isOrdi()) {
				
			}
		}		
	}
	
	public ArrayList<Cercle> getCercleList(){
		return moteur_pierre.getCercleList();
	}
	
	public Cercle getSurvoleCercle() {
		return moteur_pierre.getSurvoleCercle();
	}
	
	public Couleur currentCouleur() {
		return moteur_pierre.currentCouleur();
	}
	
	public Joueur currentJoueur() {
		return moteur_pierre.currentJoueur();
	}
	
	public void changeJoueur() {
		moteur_pierre.changeJoueur();
	}
	
	public void changeLevel() {
		moteur_pierre.changeLevel();
	}
}

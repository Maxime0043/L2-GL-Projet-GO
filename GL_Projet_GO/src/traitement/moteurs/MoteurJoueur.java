package traitement.moteurs;

import donnees.Couleur;
import gui.Go;
import traitement.Joueur;

public class MoteurJoueur {

	private Joueur[] joueurs;
	
	private int nb_joueur;
	private int nb_joueurs;
	
	private boolean noir = true;
	private boolean blanc = false;
	private boolean rouge = false;
	private boolean isDidacticiel;
	
	public MoteurJoueur(int nb_joueur, int nb_ordi, boolean isDidacticiel) {
		nb_joueurs = nb_joueur + nb_ordi;
		joueurs = new Joueur[nb_joueurs];
		
		this.nb_joueur = nb_joueur;
		this.isDidacticiel = isDidacticiel;
		initJoueur();
	}
	
	private void initJoueur() {
		int i;
		
		for(i = 0 ; i < nb_joueur ; i++) {
			joueurs[i] = new Joueur(Couleur.getCouleurs()[i], false);
			Go.logger.debug("Initialisation du joueur " + Couleur.getCouleurs()[i]);
		}
		
		for(int j = i ; j < nb_joueurs ; j++) {
			joueurs[j] = new Joueur(Couleur.getCouleurs()[j], true);	
			Go.logger.debug("Initialisation du joueur " + Couleur.getCouleurs()[j]);		
		}
	}
	
	public Joueur[] getJoueurs(){
		return joueurs;
	}
	
	public boolean canPlayMegaPierre() {
		return currentJoueur().hasMegaPierre();
	}
	
	public Couleur currentCouleur() {
		if(noir) {
			return Couleur.NOIR;
		}
		else if(blanc) {
			return Couleur.BLANC;
		}
		else {
			return Couleur.ROUGE;
		}
	}
	
	public Joueur currentJoueur() {
		Joueur j = null;
		
		for(int i = 0 ; i < nb_joueurs ; i++) {
			if(joueurs[i].getCouleur().equals(currentCouleur())) {
				j = joueurs[i];
			}
		}
		
		return j;
	}
	
	public void changeJoueur() {
		if(isDidacticiel) {
			noir = true;
			blanc = false;
		}
		
		else {
			if(noir) {
				noir = false;
				blanc = true;
			}
			
			else if(blanc) {
				blanc = false;
				
				if(nb_joueurs > 2) {
					rouge = true;
				}
				else {
					noir = true;
				}
			}
			
			else if(rouge) {
				rouge = false;
				noir = true;
			}
		}
	}
}

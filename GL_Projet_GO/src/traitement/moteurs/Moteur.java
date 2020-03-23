package traitement.moteurs;

import java.util.ArrayList;

import donnees.Cercle;
import donnees.Coordonnee;
import donnees.Couleur;
import gui.Go;
import traitement.Didacticiel;
import traitement.FinDePartie;
import traitement.Goban;
import traitement.Joueur;

public class Moteur {
	private Goban goban;
	private Didacticiel didacticiel;
	private MoteurJoueur moteur_joueur;
	private MoteurOrdi moteur_ordi;
	private MoteurPierre moteur_pierre;
	private FinDePartie fin;

	private int nb_joueurs;
	private int pass_compteur = 0;
	
	private String description;

	private ArrayList<Cercle> position_jouable;
	
	private boolean didacticiel_fini = false;
	
	public Moteur(int cellule, int taille_goban, int nb_joueur, int nb_ordi, boolean isDidacticiel) {
		goban = new Goban(taille_goban);
		moteur_joueur = new MoteurJoueur(nb_joueur, nb_ordi, isDidacticiel);
		moteur_pierre = new MoteurPierre(moteur_joueur, goban, cellule, taille_goban, nb_joueur, nb_ordi, isDidacticiel);
		fin = new FinDePartie(taille_goban, goban);
		
		if(nb_ordi > 0) {
			moteur_ordi = new MoteurOrdi(moteur_joueur, moteur_pierre, taille_goban);
		}
		
		nb_joueurs = nb_joueur + nb_ordi; 
		
		didacticiel = null;
		position_jouable = null;
		
		if(isDidacticiel) {
			didacticiel = new Didacticiel(this, moteur_pierre);
			
			Go.logger.debug("Début du Didacticiel");
			
			position_jouable = new ArrayList<Cercle>();
			didacticiel.chargeLevel();
		}
	}
	
	public void run() {
		boolean isRunning = true;
		
		while(isRunning) {
			if(currentJoueur().isOrdi()) {
				moteur_ordi.jouer();
			}
		}		
	}
	
	public void reinitGoban() {
		goban.initPlateau();
		setPoseMegaPierre(false);
		getCercleList().clear();
		changeJoueur();
	}
	
	private void initPassCompteur() {
		pass_compteur = 0;
	}
	
	public void passer() {
		moteur_joueur.changeJoueur();
		pass_compteur++;
		
		if(pass_compteur == nb_joueurs) {
			initPassCompteur();
			fin.setChaineTwoEye(goban.getHmChaine(), goban.getPlateau());
			fin.pierreMorte(goban.getPlateau(), goban.getHmChaine());
			System.out.println("Fini");
		}
	}
	
	public void survoleZone(int coordX, int coordY) {
		moteur_pierre.survoleZone(coordX, coordY);
	}
	
	public void clicEvent(int coordX, int coordY) {
		moteur_pierre.clicEvent(coordX, coordY);
		initPassCompteur();
	}
	
	public void changeLevel(boolean suivant) {
		if(didacticiel != null) {
			position_jouable.clear();
			
			if(didacticiel.getLevel() < didacticiel.getNbLevels()) {
				didacticiel.changeLevel(suivant);
			}
			else {
				didacticiel_fini = true;
				position_jouable = null;
				
				Go.logger.debug("Fin du didacticiel");
			}
		}
	}
	
	public void initPositionJouable(Coordonnee coord, Couleur couleur, boolean isMegaPierre) {
		position_jouable.add(new Cercle(coord, couleur, isMegaPierre));		
	}
	
	public ArrayList<Cercle> getPositionJouable() {
//		if(position_jouable != null && goban.existPierre(position_jouable.getX(), position_jouable.getY())) {
//			position_jouable = null;
//		}
		
		return position_jouable;
	}
	
	public void setPositionJouable() {
		moteur_pierre.setPositionJouable(position_jouable);
	}
	
	public boolean isDidacticielFini() {
		return didacticiel_fini;
	}
	
	public int getCurrentLevel() {
		return didacticiel.getLevel();
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String desc) {
		description = desc;
	}
	
	public ArrayList<Cercle> getCercleList(){
		return moteur_pierre.getCercleList();
	}
	
	public Cercle getSurvoleCercle() {
		return moteur_pierre.getSurvoleCercle();
	}
	
	public Couleur currentCouleur() {
		return moteur_joueur.currentCouleur();
	}
	
	public Joueur[] getJoueurs(){
		return moteur_joueur.getJoueurs();
	}
	
	public Joueur currentJoueur() {
		return moteur_joueur.currentJoueur();
	}
	
	public void changeJoueur() {
		moteur_joueur.changeJoueur();
	}
	
	public boolean isMegaPierre() {
		return moteur_pierre.isMegaPierre();
	}
	
	public void setPoseMegaPierre(boolean bool) {
		moteur_pierre.setPoseMegaPierre(bool);
	}
	
	public boolean canPlayMegaPierre() {
		return moteur_joueur.canPlayMegaPierre();
	}
}

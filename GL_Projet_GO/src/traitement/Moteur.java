package traitement;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

import donnees.AbstractPierre;
import donnees.Cercle;
import donnees.Coordonnee;
import donnees.Couleur;
import donnees.Goban;
import donnees.MegaPierre;
import donnees.ParametrePartie;
import donnees.Pierre;

/**
 * 
 * @author Maxime
 *
 */
public class Moteur {

	private int cellule;
	private int ecart_window;
	private int taille_goban;
	private int nb_joueurs;
	
	private Goban goban;
	private GoPierre gopierre;
	
	private Joueur[] joueurs;
	private ArrayList<Cercle> cercle;
	private Cercle survole;
	
	private boolean noir = true;
	private boolean blanc = false;
	private boolean rouge = false;
	private boolean isMegaPierre = false;
	
	public Moteur(int cellule, int ecart_window, int taille_goban, int nb_joueur, int nb_ordi) {
		this.cellule = cellule;
		this.ecart_window = ecart_window;
		this.taille_goban = taille_goban;
		nb_joueurs = nb_joueur + nb_ordi;
		
		goban = new Goban(taille_goban);
		gopierre = new GoPierre();
		joueurs = new Joueur[nb_joueurs];
		cercle = new ArrayList<Cercle>();
		
		initJoueur();
	}
	
	public void initJoueur() {
		joueurs[0] = new Joueur(Couleur.NOIR, false);
		joueurs[1] = new Joueur(Couleur.BLANC, false);
		
		if(nb_joueurs == 3) {
			joueurs[2] = new Joueur(Couleur.ROUGE, false);
		}
	}
	
	public Joueur[] getJoueurs(){
		return joueurs;
	}
	
	public boolean getNoir() {
		return noir;
	}
	
	public boolean getBlanc() {
		return blanc;
	}
	
	public boolean getRouge() {
		return rouge;
	}
	
	public boolean getIsMegaPierre() {
		return isMegaPierre;
	}
	
	public void setIsMegaPierre(boolean bool) {
		isMegaPierre = bool;
	}
	
	public boolean canPlayMegaPierre() {
		return currentJoueur().hasMegaPierre();
	}
	
	public ArrayList<Cercle> getCercleList(){
		return cercle;
	}
	
	public Cercle getSurvoleCercle() {
		return survole;
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
	
	public void survoleZone(MouseEvent e) {
		int x = (e.getY() - ecart_window / 2) / cellule;
		int y = (e.getX() - ecart_window / 2) / cellule;
		
		if(taille_goban == ParametrePartie.TAILLE_GOBAN[1]) {
			x = (e.getY() - (int)(ecart_window / 1.5)) / cellule;
			y = (e.getX() - (int)(ecart_window / 1.5)) / cellule;
		}
		
		Couleur couleur;
		
		if((x >= 0) && (x < taille_goban) && (y >= 0) && (y < taille_goban) && (!goban.existPierre(x, y))) {
			couleur = currentCouleur();
			
			if(isMegaPierre) {
				if(x == taille_goban - 1) {
					x--;
				}
				if(y == taille_goban - 1) {
					y--;
				}
			}
			
			if(isMegaPierre && currentJoueur().hasMegaPierre() && (x < taille_goban - 1) && (y < taille_goban - 1)) {
				if(!goban.existPierre(x+1, y) && !goban.existPierre(x, y+1) && !goban.existPierre(x+1, y+1)) {
					survole = new Cercle(new Coordonnee(x, y), couleur, true);
				}
			}
			else {
				survole = new Cercle(new Coordonnee(x, y), couleur, false);
			}
		}
		
		else {
			survole = null;
		}
	}
	
	public void clicEvent(MouseEvent e) {
		int x = (e.getY() - ecart_window / 2) / cellule;
		int y = (e.getX() - ecart_window / 2) / cellule;
		
		if(taille_goban == ParametrePartie.TAILLE_GOBAN[1]) {
			x = (e.getY() - (int)(ecart_window / 1.5)) / cellule;
			y = (e.getX() - (int)(ecart_window / 1.5)) / cellule;
		}
		
		if((x >= 0) && (x < taille_goban) && (y >= 0) && (y < taille_goban)) {
			if(!goban.existPierre(x, y)){
				if(isMegaPierre) {
					if(x == taille_goban - 1) {
						x--;
					}
					if(y == taille_goban - 1) {
						y--;
					}
				}
				
				Coordonnee c = new Coordonnee(x, y);
				
				if(!isMegaPierre) {
					addPierre(new Pierre(currentCouleur(), c));
					currentJoueur().addPierre(new Pierre(currentCouleur(), c));
					
					changeJoueur();
				}
				else {
					if((x < taille_goban - 1) && (y < taille_goban - 1)) {
						if(!goban.existPierre(x+1, y) && !goban.existPierre(x, y+1) && !goban.existPierre(x+1, y+1)) {
							addPierre(new MegaPierre(currentCouleur(), c));
							currentJoueur().addPierre(new MegaPierre(currentCouleur(), c));

							currentJoueur().playMegaPierre();
							changeJoueur();
						}
					}
				}
			}
			
			setIsMegaPierre(false);
		}
	}
	
	public void addPierre(AbstractPierre pierre) {
		goban.addPierre(pierre);
		
		Coordonnee coordPierre = new Coordonnee(pierre.getX(), pierre.getY());
		
		cercle.add(new Cercle(coordPierre, pierre.getCouleur(), isMegaPierre));
		
		ArrayList<AbstractPierre> voisin = gopierre.voisins(goban.getPierre(pierre.getX(), pierre.getY()), goban.getPlateau(), taille_goban);
		
		for(AbstractPierre pierreVoisin : voisin) {
			if(pierreVoisin.hasChaine()) {
				if(goban.isPierreCapture(goban.getChaine(pierreVoisin.getNomChaine()), taille_goban)) {
					removePierre(goban.getChaine(pierreVoisin.getNomChaine()));
				}
			}
			
			else if(goban.isPierreCapture(pierreVoisin, taille_goban)) {
				removePierre(pierreVoisin);
			}
			
			currentJoueur().addScore(goban.getScoreCapture());
		}
	}
	
	public void removePierre(AbstractPierre pierre) {
		if(goban.existPierre(pierre.getX(), pierre.getY())) {
			goban.removePierre(pierre);
			currentJoueur().removePierre(pierre);
			
			Cercle c = null;
			
			for(Cercle coordCercle : cercle) {
				if((pierre.getX() == coordCercle.getX()) && (pierre.getY() == coordCercle.getY())) {
					c = coordCercle;
				}
			}
			
			cercle.remove(c);
		}
	}
	
	public void removePierre(ArrayList<AbstractPierre> chaine) {
		for(AbstractPierre pierre : chaine) {
			removePierre(pierre);
		}
	}

//	public void addCercle(Cercle c) {
//		boolean result = true;
//		
//		for(Cercle coordCercle : cercle) {
//			if((c.getX() == coordCercle.getX()) && (c.getY() == coordCercle.getY())) {
//				result = false;
//			}
//		}
//		
//		if(result) {
//			cercle.add(c);
//			
//			Coordonnee coordCercle = new Coordonnee(c.getX(), c.getY());
//			
//			/*A modifier => placer d'abord les pierres puis dessiner les cercles en fonction*/
//			if(!getIsMegaPierre()) {
//				goban.addPierre(new Pierre(c.getCouleur(), coordCercle));
//			}
//			else {
//				goban.addPierre(new MegaPierre(c.getCouleur(), coordCercle));
//			}
//			
//			ArrayList<AbstractPierre> voisin = gopierre.voisins(goban.getPierre(c.getX(), c.getY()), goban.getPlateau(), choix);
//			
//			for(AbstractPierre pierreVoisin : voisin) {
//				if(pierreVoisin.hasChaine()) {
//					System.out.println("pierre " + pierreVoisin.getCouleur() + " [" + pierreVoisin.getX() + "," + pierreVoisin.getY() +"]" + "chaine n° " + pierreVoisin.getNomChaine() + "\n");
//					
//					if(goban.isPierreCapture(goban.getChaine(pierreVoisin.getNomChaine()), choix)) {
//						removeCercle(goban.getChaine(pierreVoisin.getNomChaine()));
//					}
//				}
//				
//				else if(goban.isPierreCapture(pierreVoisin, choix)) {
//					removeCercle(getCercle(pierreVoisin.getX(), pierreVoisin.getY()));
//				}
//			}
//			
////			AbstractPierre pierre;
////			
////			for(int i = 0 ; i < taille_goban ; i++) {
////				for(int j = 0 ; j < taille_goban ; j++) {
////					if(goban.existPierre(i, j)) {	
////						pierre = goban.getPierre(i, j);
////						
////						if(pierre.hasChaine()) {
////							System.out.println("pierre " + pierre.getCouleur() + " [" + i + "," + j +"]" + "chaine n° " + pierre.getNomChaine() + "\n");
////							
////							if(goban.isPierreCapture(goban.getChaine(pierre.getNomChaine()), choix)) {
////								removeCercle(goban.getChaine(pierre.getNomChaine()));
////							}
////						}
////						
////						else if(goban.isPierreCapture(pierre, choix)) {
////							removeCercle(getCercle(i, j));
////						}
////					}
////				}
////			}
//			System.out.println("--------------------------------------------------------------------------------");
//			/*-----------------------------------------------------------------------------*/
//		}
//	}
//	
//	public void removeCercle(Cercle c) {
//		boolean result = false;
//		Cercle coord = null;
//		
//		for(Cercle coordCercle : cercle) {
//			if((c.getX() == coordCercle.getX()) && (c.getY() == coordCercle.getY())) {
//				coord = coordCercle;
//				result = true;
//			}
//		}
//		
//		if(result) {
//			cercle.remove(coord);
//			
//			goban.removePierre(goban.getPierre(c.getX(), c.getY()));
//		}
//	}
//	
//	public void removeCercle(ArrayList<AbstractPierre> chaine) {
//		Cercle cercle;
//		
//		for(AbstractPierre pierre : chaine) {
//			cercle = new Cercle(new Coordonnee(pierre.getX(), pierre.getY()), pierre.getCouleur(), pierre.isMegaPierre());
//			
//			removeCercle(cercle);
//		}
//	}
}

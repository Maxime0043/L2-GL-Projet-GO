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

	int choix = 0;
	
	int cellule = ParametrePartie.LARGEUR_CASE;
	int ecart_window = ParametrePartie.ECART;
	int taille_goban = ParametrePartie.TAILLE_GOBAN[choix];
	
	Goban goban;
	GoPierre gopierre;
	
	private ArrayList<Cercle> cercle;
	private Cercle survole;
	
	private boolean noir = true;
	private boolean blanc = false;
	private boolean rouge = false;
	private boolean isMegaPierre = false;
	
	public Moteur() {
		goban = new Goban(choix);
		gopierre = new GoPierre();
		cercle = new ArrayList<Cercle>();
	}
	
	public int getChoix() {
		return choix;
	}
	
	public void setChoix(int valeur) {
		choix = valeur;
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
	
	public ArrayList<Cercle> getCercleList(){
		return cercle;
	}
	
	public Cercle getSurvoleCercle() {
		return survole;
	}
	
	public Cercle getCercle(int x, int y) {
		Cercle result = null;
		
		for(Cercle c : cercle) {
			if((c.getX() == x) && (c.getY() == y)) {
				result = c;
			}
		}
		
		if(result != null) {
			return result;
		}
		else {
			return null;
		}
	}
	
	public void survoleZone(MouseEvent e) {
		int x = (e.getY() - ecart_window / 2) / cellule;
		int y = (e.getX() - ecart_window / 2) / cellule;
		Couleur couleur;
		
		if((x >= 0) && (x < taille_goban) && (y >= 0) && (y < taille_goban) && (!goban.existPierre(x, y))) {
			if(noir) {
				couleur = Couleur.NOIR;
			}
			else if(blanc) {
				couleur = Couleur.BLANC;
			}
			else {
				couleur = Couleur.ROUGE;
			}
			
			survole = new Cercle(new Coordonnee(x, y), couleur, false);
		}
		
		else {
			survole = null;
		}
	}
	
	public void clicEvent(MouseEvent e) {
		int x = (e.getY() - ecart_window / 2) / cellule;
		int y = (e.getX() - ecart_window / 2) / cellule;
		
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
				
				if(noir) {
					if(!isMegaPierre) {
						addPierre(new Pierre(Couleur.NOIR, c));
					}
					else {
						addPierre(new MegaPierre(Couleur.NOIR, c));
					}
					noir = false;
					blanc = true;
				}
				else if(blanc) {
					if(!isMegaPierre) {
						addPierre(new Pierre(Couleur.BLANC, c));
					}
					else {
						addPierre(new MegaPierre(Couleur.BLANC, c));
					}
					blanc = false;
					rouge = true;
				}
				else if(rouge) {
					if(!isMegaPierre) {
						addPierre(new Pierre(Couleur.ROUGE, c));
					}
					else {
						addPierre(new MegaPierre(Couleur.ROUGE, c));
					}
					noir = true;
					rouge = false;
				}
			}
		}
	}
	
	public void addPierre(AbstractPierre pierre) {
		goban.addPierre(pierre);
		
		Coordonnee coordPierre = new Coordonnee(pierre.getX(), pierre.getY());
		
		cercle.add(new Cercle(coordPierre, pierre.getCouleur(), isMegaPierre));
		
		ArrayList<AbstractPierre> voisin = gopierre.voisins(goban.getPierre(pierre.getX(), pierre.getY()), goban.getPlateau(), choix);
		
		for(AbstractPierre pierreVoisin : voisin) {
			if(pierreVoisin.hasChaine()) {
				if(goban.isPierreCapture(goban.getChaine(pierreVoisin.getNomChaine()), choix)) {
					removePierre(goban.getChaine(pierreVoisin.getNomChaine()));
				}
			}
			
			else if(goban.isPierreCapture(pierreVoisin, choix)) {
				removePierre(pierreVoisin);
			}
		}
	}
	
	public void removePierre(AbstractPierre pierre) {
		if(goban.existPierre(pierre.getX(), pierre.getY())) {
			goban.removePierre(pierre);
			
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

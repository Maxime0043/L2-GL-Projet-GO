package traitement;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

import donnees.Cercle;
import donnees.Coordonnee;
import donnees.Couleur;
import donnees.Goban;
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
	
	private ArrayList<Cercle> cercle;
	
	private boolean noir = true;
	private boolean blanc = false;
	private boolean rouge = false;
	
	public Moteur() {
		goban = new Goban(choix);
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
	
	public ArrayList<Cercle> getCercleList(){
		return cercle;
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
	
	public void clicEvent(MouseEvent e) {
		int x = (e.getY() - ecart_window / 2) / cellule;
		int y = (e.getX() - ecart_window / 2) / cellule;
		
		if((x >= 0) && (x < taille_goban) && (y >= 0) && (y < taille_goban)) {
			Coordonnee c = new Coordonnee(x, y);
			
			if(noir) {
				addCercle(new Cercle(c, Couleur.NOIR));
				noir = false;
				blanc = true;
			}
			else if(blanc) {
				addCercle(new Cercle(c, Couleur.BLANC));
				blanc = false;
				rouge = true;
			}
			else if(rouge) {
				addCercle(new Cercle(c, Couleur.ROUGE));
				noir = true;
				rouge = false;
			}
		}
	}

	public void addCercle(Cercle c) {
		boolean result = true;
		
		for(Cercle coordCercle : cercle) {
			if((c.getX() == coordCercle.getX()) && (c.getY() == coordCercle.getY())) {
				result = false;
			}
		}
		
		if(result) {
			cercle.add(c);
			
			Coordonnee coordCercle = new Coordonnee(c.getX(), c.getY());
			int numero;
			
			if(c.getCouleur().equals(Couleur.NOIR)) {
				numero = goban.getNbNoir();
			}
			else if(c.getCouleur().equals(Couleur.BLANC)) {
				numero = goban.getNbBlanc();
			}
			else {
				numero = goban.getNbRouge();
			}
			
			
			/*A modifier => placer d'abord les pierres puis dessiner les cercles en fonction*/
			goban.addPierre(new Pierre(c.getCouleur(), null, coordCercle, numero));
			System.out.println("Ajout (" + c.getX() + ", " + c.getY() + ") Couleur : " + goban.getPierre(c.getX(), c.getY()).getCouleur());
			
			for(int i = 0 ; i < taille_goban ; i++) {
				for(int j = 0 ; j < taille_goban ; j++) {
					if(goban.existPierre(i, j)) {		
						System.out.println("(" + i + ", " + j + ") liberte : " + goban.getPierre(i, j).getLiberte());
						
						if(goban.isPierreCapture(goban.getPierre(i, j), choix)) {
							removeCercle(getCercle(i, j));
							
						}
					}
				}
			}
			System.out.print("\n");
			/*-----------------------------------------------------------------------------*/
		}
	}
	
	public void removeCercle(Cercle c) {
		boolean result = false;
		Cercle coord = null;
		
		for(Cercle coordCercle : cercle) {
			if((c.getX() == coordCercle.getX()) && (c.getY() == coordCercle.getY())) {
				coord = coordCercle;
				result = true;
			}
		}
		
		if(result) {
			cercle.remove(coord);
			
			goban.removePierre(goban.getPierre(c.getX(), c.getY()));
		}
	}
	
	public ArrayList<Cercle> cercleListe(){
		return cercle;
	}
}

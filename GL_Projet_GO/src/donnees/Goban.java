package donnees;

import java.util.ArrayList;
import java.util.HashMap;

import traitement.Capture;
import traitement.Chaine;
import traitement.GoPierre;

public class Goban {
	
	private AbstractPierre[][] plateau;
	private HashMap<Integer, Chaine> hmChaine;
	private int nb_chaine;
	private HashMap<Couleur, Score> scores;
	private Capture capture;
	private GoPierre gopierre;
	private ArrayList<AbstractPierre> liste_voisin;
	
	private int taille_goban;
	private int choix;
	
	private int nb_Noir;
	private int nb_Blanc;
	private int nb_Rouge;
	
	public Goban(int choix) {
		taille_goban = ParametrePartie.TAILLE_GOBAN[choix];
		this.choix = choix;
		
		plateau = new AbstractPierre[taille_goban][taille_goban];
		hmChaine = new HashMap <Integer, Chaine>();
		nb_chaine = 0;
		scores = new HashMap <Couleur, Score>();
		capture = new Capture();
		gopierre = new GoPierre();
		liste_voisin = new ArrayList<AbstractPierre>();
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
			
			this.addToChaine(pierre);
			
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
	
	/**
	 * 
	 * @param pierre
	 */
	public void addToChaine(AbstractPierre pierre) {
		
		liste_voisin = gopierre.voisins(pierre, plateau, taille_goban);
		
		if(liste_voisin != null) {
			for(AbstractPierre pierreVoisine : liste_voisin) {
				
				if(pierreVoisine.hasChaine()) {
					
					if(pierre.hasChaine()) {
						this.chaineFusion(pierre, pierreVoisine);
					}
					else {
						hmChaine.get(pierreVoisine.getNomChaine()).addPierre(pierre);
						pierre.setNomChaine(pierreVoisine.getNomChaine());
					}
				}
				
				else {
					Chaine c = new Chaine();
					c.addPierre(pierre);
					pierre.setNomChaine(nb_chaine);
					c.addPierre(pierreVoisine);
					pierreVoisine.setNomChaine(nb_chaine);
					hmChaine.put(nb_chaine, c);
					nb_chaine ++;
				}
			}
		}
	}
	
	public void chaineFusion(AbstractPierre p1, AbstractPierre p2) {
		if( p1.getNomChaine() < p2.getNomChaine() ) {
			Chaine c = new Chaine();
			c = hmChaine.get(p2.getNomChaine());
			for (AbstractPierre p) {
				
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
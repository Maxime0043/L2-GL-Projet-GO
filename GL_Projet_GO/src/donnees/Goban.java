package donnees;

import java.util.ArrayList;
import java.util.HashMap;

import traitement.Capture;
import traitement.Chaine;
import traitement.GoPierre;

public class Goban {
	
	private AbstractPierre[][] plateau;
	private HashMap<Integer, Chaine> hmChaine;
	private HashMap<Couleur, Score> scores;
	private Capture capture;
	private GoPierre gopierre;
	
	private int taille_goban;
	private int choix;

	private int nb_chaine;
	private int nb_Noir;
	private int nb_Blanc;
	private int nb_Rouge;
	
	public Goban(int choix) {
		taille_goban = ParametrePartie.TAILLE_GOBAN[choix];
		this.choix = choix;
		nb_chaine = 0;
		
		plateau = new AbstractPierre[taille_goban][taille_goban];
		hmChaine = new HashMap <Integer, Chaine>();
		scores = new HashMap <Couleur, Score>();
		capture = new Capture();
		gopierre = new GoPierre();
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
	
	public ArrayList<AbstractPierre> getChaine(int nom){
		return hmChaine.get(nom).getChaine();
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
	 * apres avoir poser une pierre, permet de creer une chaine avec ses voisines ou de fusionner les chaines deja existante 
	 * @param pierre
	 */
	public void addToChaine(AbstractPierre pierre) {
		Couleur couleurPierre = pierre.getCouleur();
		Couleur couleurVoisin;
		
		ArrayList<AbstractPierre> liste_voisin = gopierre.voisins(pierre, plateau, choix);
		
		if(liste_voisin.size() != 0) {
			for(AbstractPierre pierreVoisine : liste_voisin) {
				couleurVoisin = pierreVoisine.getCouleur();
				
				if(couleurPierre.equals(couleurVoisin)) {
					if(pierreVoisine.hasChaine()) {
						
						if(pierre.hasChaine()) {
							if (pierre.getNomChaine() < pierreVoisine.getNomChaine() ) {
								this.chaineFusion(pierre, pierreVoisine);
							}
							else
								this.chaineFusion(pierreVoisine, pierre);
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
	}
	/**
	 * Permet la fusion de deux chaines.
	 * @param p1, pierre dont on garde la chaine et où on ajoute celle de p2
	 * @param p2, pierre dont on supprime la chaine
	 */
	public void chaineFusion(AbstractPierre p1, AbstractPierre p2) {
		int name = p2.getNomChaine();
		
		for (AbstractPierre p : hmChaine.get(name).getChaine()) {
			p.setNomChaine(p1.getNomChaine());
			hmChaine.get(p1.getNomChaine()).addPierre(p);
		}
		hmChaine.remove(name);
	}
	
	/**
	 * Permet de supprimer la chaine et toute ses pierres du Goban
	 * @param nomChaine
	 */
	public void removeChaine (int nomChaine) {
		for (AbstractPierre p : hmChaine.get(nomChaine).getChaine()) {
			this.removePierre(p);
		}
		hmChaine.remove(nomChaine);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
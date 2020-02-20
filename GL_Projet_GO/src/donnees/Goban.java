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

	private int nb_chaine;
	private int nb_Noir;
	private int nb_Blanc;
	private int nb_Rouge;
	
	public Goban(int taille_goban) {
		this.taille_goban = taille_goban;
		nb_chaine = 0;
		
		plateau = new AbstractPierre[taille_goban][taille_goban];
		hmChaine = new HashMap <Integer, Chaine>();
		scores = new HashMap <Couleur, Score>();
		capture = new Capture();
		gopierre = new GoPierre();
	}
	
	public void initGoban() {
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
	
	public AbstractPierre[][] getPlateau(){
		return plateau;
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
		
		pierre.updateLiberte(plateau, taille_goban);
		
		return pierre;
	}
	
	public void addPierre(AbstractPierre pierre) {
		int x = pierre.getX();
		int y = pierre.getY();
		
		plateau[x][y] = pierre;
			
		this.addToChaine(pierre);
			
		if(pierre.isMegaPierre()) {
			plateau[x][y+1] = pierre;
			plateau[x+1][y] = pierre;
			plateau[x+1][y+1] = pierre;
		}
		
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
	
	public void removePierre(AbstractPierre pierre) {
		int x = pierre.getX();
		int y = pierre.getY();
		
		if(plateau[x][y] != null) {
			plateau[x][y] = null;
			
			if(pierre.isMegaPierre()) {
				plateau[x+1][y] = null;
				plateau[x][y+1] = null;
				plateau[x+1][y+1] = null;
			}
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
	
	public boolean isPierreCapture(AbstractPierre pierre, int taille_goban) {
		return capture.isCapture(pierre, plateau, taille_goban);
	}
	
	public boolean isPierreCapture(ArrayList<AbstractPierre> chaine, int taille_goban) {
		return capture.isCapture(chaine, plateau, taille_goban);
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
		int x = pierre.getX();
		int y = pierre.getY();
		
		Couleur couleurPierre = pierre.getCouleur();
		Couleur couleurVoisin;
		
		ArrayList<AbstractPierre> liste_voisin = gopierre.voisins(pierre, plateau, taille_goban);
		
//		System.out.println("Liste pierres voisines de [" + x + "," + y + "]:");
//		for(AbstractPierre p : liste_voisin) {
//			System.out.println("[" + p.getX() + "," + p.getY() + "]");
//		}
//		System.out.println("Fin Liste\n");
		
		if(liste_voisin.size() != 0) {
			for(AbstractPierre pierreVoisine : liste_voisin) {
				couleurVoisin = pierreVoisine.getCouleur();
				
				if(couleurPierre.equals(couleurVoisin)) {
					if(pierreVoisine.hasChaine()) {
						
						if(pierre.hasChaine() && (pierre.getNomChaine() != pierreVoisine.getNomChaine())) {
							if (pierre.getNomChaine() < pierreVoisine.getNomChaine() ) {
								this.chaineFusion(pierre, pierreVoisine);
							}
							else {
								this.chaineFusion(pierreVoisine, pierre);
							}
						}
						else {
							hmChaine.get(pierreVoisine.getNomChaine()).addPierre(pierre);
							pierre.setNomChaine(pierreVoisine.getNomChaine());
							
							if(pierre.isMegaPierre()) {
								getPierre(x+1, y).setNomChaine(pierreVoisine.getNomChaine());
								getPierre(x, y+1).setNomChaine(pierreVoisine.getNomChaine());
								getPierre(x+1, y+1).setNomChaine(pierreVoisine.getNomChaine());
							}
						}
					}
					
					else {
						if(pierre.hasChaine()) {
							hmChaine.get(pierre.getNomChaine()).addPierre(pierreVoisine);
							pierreVoisine.setNomChaine(pierre.getNomChaine());
						}
						else {
							Chaine c = new Chaine();
							c.addPierre(pierre);
							pierre.setNomChaine(nb_chaine);
							
							if(pierre.isMegaPierre()) {
								getPierre(x+1, y).setNomChaine(nb_chaine);
								getPierre(x, y+1).setNomChaine(nb_chaine);
								getPierre(x+1, y+1).setNomChaine(nb_chaine);
							}
							
							c.addPierre(pierreVoisine);
							pierreVoisine.setNomChaine(nb_chaine);
							hmChaine.put(nb_chaine, c);
							nb_chaine ++;
						}
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
		int x, y;
		boolean verif = false;
		
		int name = p2.getNomChaine();
		
		for (AbstractPierre p : hmChaine.get(name).getChaine()) {
			x = p.getX();
			y = p.getY();
			
			if(!p.isMegaPierre()) {
				p.setNomChaine(p1.getNomChaine());
				
				hmChaine.get(p1.getNomChaine()).addPierre(p);
			}
			else {
				if(!verif) {
					p.setNomChaine(p1.getNomChaine());
					hmChaine.get(p1.getNomChaine()).addPierre(p);
					
					getPierre(x+1, y).setNomChaine(p.getNomChaine());
					hmChaine.get(p1.getNomChaine()).addPierre(getPierre(x+1, y));
					
					getPierre(x, y+1).setNomChaine(p.getNomChaine());
					hmChaine.get(p1.getNomChaine()).addPierre(getPierre(x, y+1));
					
					getPierre(x+1, y+1).setNomChaine(p.getNomChaine());
					hmChaine.get(p1.getNomChaine()).addPierre(getPierre(x+1, y+1));
					
					verif = true;
				}
			}
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
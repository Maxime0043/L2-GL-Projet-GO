package traitement;

import java.util.ArrayList;
import java.util.HashMap;

import donnees.AbstractPierre;
import donnees.Chaine;
import donnees.Coordonnee;
import donnees.Couleur;
import donnees.MegaPierre;
import donnees.ParametrePartie;
import donnees.Pierre;
import donnees.Plateau;

public class Goban {
	
	private Plateau plateau;
	private ArrayList<Coordonnee> hoshis;
	private HashMap<Integer, Chaine> hmChaine;
	private Capture capture;
	
	private int taille_goban;
	private int nb_chaine;

	public Goban(int taille_goban) {
		this.taille_goban = taille_goban;
		nb_chaine = 0;
		
		plateau = new Plateau(taille_goban);
		hoshis = new ArrayList<Coordonnee>();
		hmChaine = new HashMap <Integer, Chaine>();
		capture = new Capture(taille_goban);
		
		initHoshis();
	}
	
	public void initPlateau() {
		plateau.initPlateau(taille_goban);
	}
	
	public void initHoshis() {
		int x;
		
		if(taille_goban == ParametrePartie.TAILLE_GOBAN[0]) {
			x = 2;
		}
		
		else {
			x = 3;
			
			hoshis.add(new Coordonnee(x, (taille_goban - 1) / 2));
			hoshis.add(new Coordonnee(taille_goban - 1 - x, (taille_goban - 1) / 2));
			hoshis.add(new Coordonnee((taille_goban - 1) / 2, x));
			hoshis.add(new Coordonnee((taille_goban - 1) / 2, (taille_goban - 1) / 2));
			hoshis.add(new Coordonnee((taille_goban - 1) / 2, taille_goban - 1 - x));
		}
		
		hoshis.add(new Coordonnee(x, x));
		hoshis.add(new Coordonnee(taille_goban - 1 - x, x));
		hoshis.add(new Coordonnee(x, taille_goban - 1 - x));
		hoshis.add(new Coordonnee(taille_goban - 1 - x, taille_goban - 1 - x));
	}
	
	public AbstractPierre[][] getPlateau(){
		return plateau.getPlateau();
	}
	
	public ArrayList<Coordonnee> getHoshis(){
		return hoshis;
	}
	
	public HashMap<Integer, Chaine> getHmChaine (){
		return hmChaine;
	}
	
	public void addPierre(AbstractPierre pierre) {
		plateau.addPierre(pierre);
		
		this.addToChaine(pierre);
	}
	
	public void removePierre(AbstractPierre pierre) {
		int x = pierre.getX();
		int y = pierre.getY();

		removePierreChaine(pierre);
		plateau.removePierre(x, y);
		
		if(pierre.isMegaPierre()) {
			plateau.removePierre(x+1, y);
			plateau.removePierre(x, y+1);
			plateau.removePierre(x+1, y+1);
		}
	}
	
	public AbstractPierre getPierre(int x, int y) {
		return plateau.getPierre(x, y);
	}
	
	public boolean existPierre(int x, int y) {
		if (getPlateau()[x][y] != null) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public int getScoreCapture() {
		return capture.getCompteur();
	}
	
	public boolean isPierreCapture(AbstractPierre pierre) {
		return capture.isCapture(pierre, getPlateau());
	}
	
	public boolean isPierreCapture(ArrayList<AbstractPierre> chaine) {
		return capture.isCapture(chaine, getPlateau());
	}
	
	public boolean isSuicide(int x, int y, Couleur couleur, boolean isMegaPierre) {
		if(!isMegaPierre) {
			return isPierreCapture(new Pierre(couleur, x, y));
		}
		
		else {
			return isPierreCapture(new MegaPierre(couleur, x, y));
		}
	}
	
	public boolean isSuicide(AbstractPierre pierre) {
		return isPierreCapture(pierre);
	}
	
	public boolean isSuicide(ArrayList<AbstractPierre> chaine) {
		return isPierreCapture(chaine);
	}
	
	public boolean canBeCaptured(AbstractPierre pierre) {
		return capture.canBeCaptured(pierre, getPlateau());
	}
	
	public boolean canBeCaptured(ArrayList<AbstractPierre> chaine) {
		return capture.canBeCaptured(chaine, getPlateau());
	}
	
	public ArrayList<AbstractPierre> getChaine(int nom){
		return hmChaine.get(nom).getChaine();
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
		
		ArrayList<AbstractPierre> liste_voisin = GoPierre.voisins(pierre, plateau.getPlateau(), taille_goban);
		
		if(liste_voisin.size() != 0) {
			for(AbstractPierre pierreVoisine : liste_voisin) {
				couleurVoisin = pierreVoisine.getCouleur();
				
				if(couleurPierre.equals(couleurVoisin)) {
					if(pierreVoisine.hasChaine()) {
						
						if(pierre.hasChaine() && (pierre.getNomChaine() != pierreVoisine.getNomChaine())) {
							if(pierre.getNomChaine() < pierreVoisine.getNomChaine() ) {
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
	
	public void removePierreChaine(AbstractPierre pierre) {
		if(pierre.getNomChaine() != -1) {
			AbstractPierre pierreChaine = null;
			Chaine chaine = hmChaine.get(pierre.getNomChaine());
			
			for(AbstractPierre p : chaine.getChaine()) {
				if(pierre.getX() == p.getX() && pierre.getY() == p.getY()) {
					pierreChaine = p;
				}
			}
			
			if(pierreChaine != null) {
				chaine.removePierre(pierreChaine);
				pierreChaine.setNomChaine(-1);
			}
		}
	}
	
	/**
	 * Permet de supprimer la chaine et toute ses pierres du Goban
	 * @param nomChaine
	 */
	public void removeChaine(int nomChaine) {
		ArrayList<AbstractPierre> chaine = new ArrayList<AbstractPierre>();
		
		chaine.addAll(hmChaine.get(nomChaine).getChaine());
		
		for(AbstractPierre p : chaine) {
			this.removePierre(p);
		}
		
		hmChaine.remove(nomChaine);
	}
	
	public void updateChaines() {
		HashMap<Integer, Chaine> copy_hm = new HashMap<Integer, Chaine>();
		int nom_chaine;
		
		for(int i = 0 ; i < taille_goban ; i++) {
			for(int j = 0 ; j < taille_goban ; j++) {
				if(existPierre(i, j) && getPierre(i, j).hasChaine()) {
					boolean hasChaine = false;
					
					for(AbstractPierre p : GoPierre.voisins(getPierre(i, j), getPlateau(), taille_goban)) {
						if(p.getCouleur() == getPierre(i, j).getCouleur()) {
							hasChaine = true;
						}
					}
					
					if(!hasChaine) {
						removePierreChaine(getPierre(i, j));
					}
				}
			}
		}
		
		copy_hm.putAll(hmChaine);
		
		for(Chaine chaine : copy_hm.values()) {
			if(chaine.getChaine().size() == 1) {
				nom_chaine = chaine.getChaine().get(0).getNomChaine();
				
				chaine.getChaine().get(0).setNomChaine(-1);
				hmChaine.remove(nom_chaine);
			}
		}
	}
	
	public void updateLibertePlateau() {
		for(int i = 0 ; i < taille_goban ; i++) {
			for(int j = 0 ; j < taille_goban ; j++) {
				if(existPierre(i, j)) {
					getPierre(i, j).updateLiberte(getPlateau(), taille_goban);
				}
			}
		}
	}
}

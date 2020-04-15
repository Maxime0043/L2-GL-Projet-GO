package traitement;

import java.util.ArrayList;

import donnees.AbstractPierre;
import donnees.Coordonnee;
import donnees.Couleur;

public class Capture {
	
	private int taille_goban;
	private int compteur;
	
	public Capture(int taille_goban) {
		this.taille_goban = taille_goban;
	}
	
	public void initCompteur() {
		compteur = 0;
	}
	
	public int getCompteur() {
		return compteur;
	}
	
	private void addCompteur(int n) {
		compteur += n;
	}
	
	/**
	 * 
	 * @param pierre
	 * @param plateau
	 * @param taille_goban
	 * @return
	 */
	public boolean isCapture(AbstractPierre pierre, AbstractPierre[][] plateau) {
		initCompteur();
		pierre.updateLiberte(plateau, taille_goban);
		
		if(pierre.getLiberte() > 0) {
			return false;
		}
		
		else {			
			ArrayList<AbstractPierre> listVoisin = GoPierre.voisins(pierre, plateau, taille_goban);
			Couleur couleurPierre = pierre.getCouleur();
			Couleur couleurP = null;
			boolean debut = true;

			for(AbstractPierre p : listVoisin) {
				if(debut) {
					couleurP = p.getCouleur();
					debut = false;
				}
				
				else {
					if(couleurP.equals(couleurPierre)) {
						initCompteur();
						return false;
					}
					
					else {
						if(!couleurP.equals(p.getCouleur())) {
							initCompteur();
							return false;
						}
					}
				}
			}
			
			if(!pierre.isMegaPierre()) {
				addCompteur(1);
			}
			
			else {
				addCompteur(4);
			}
		}

		return true;
	}
	
	/**
	 * 
	 * @param chaine
	 * @param plateau
	 * @param taille_goban
	 * @return
	 */
	public boolean isCapture(ArrayList<AbstractPierre> chaine, AbstractPierre[][] plateau) {
		initCompteur();
		
		ArrayList<AbstractPierre> voisin;
		Couleur couleurPierre, couleurVoisin = null;
		boolean debut = true;
		
		for(AbstractPierre pierre : chaine) {
			pierre.updateLiberte(plateau, taille_goban);
			
			if(pierre.getLiberte() > 0) {
				return false;
			}
			
			couleurPierre = pierre.getCouleur();
			
			voisin = GoPierre.voisins(pierre, plateau, taille_goban);
			
			for(AbstractPierre pierreVoisine : voisin) {
				if(debut) {
					if(!couleurPierre.equals(pierreVoisine.getCouleur())) {
						couleurVoisin = pierreVoisine.getCouleur();
						debut = false;
					}
				}
				
				else {
					if(!couleurVoisin.equals(pierreVoisine.getCouleur()) && !couleurPierre.equals(pierreVoisine.getCouleur())) {
						initCompteur();
						return false;
					}
				}
			}
		}
		
		for(AbstractPierre pierre : chaine) {
			if(!pierre.isMegaPierre()) {
				addCompteur(1);
			}
			
			else {
				addCompteur(4);
			}
		}
		
		return true;
	}
	
	/**
	 * 
	 * @param pierre
	 * @param plateau
	 * @return
	 */
	public boolean canBeCaptured(AbstractPierre pierre, AbstractPierre[][] plateau) {
		pierre.updateLiberte(plateau, taille_goban);
		
		if(pierre.getLiberte() > 2) {
			return false;
		}
		
		else {
			ArrayList<AbstractPierre> listVoisin = GoPierre.voisins(pierre, plateau, taille_goban);
			Couleur couleurPierre = pierre.getCouleur();
			Couleur couleurP = null;
			boolean debut = true;

			for(AbstractPierre p : listVoisin) {
				if(debut) {
					couleurP = p.getCouleur();
					debut = false;
				}
				
				else {
					if(couleurP.equals(couleurPierre)) {
						return false;
					}
					
					else {
						if(!couleurP.equals(p.getCouleur())) {
							return false;
						}
					}
				}
			}
		}
		
		return true;
	}
	
	/**
	 * 
	 * @param chaine
	 * @param plateau
	 * @return
	 */
	public boolean canBeCaptured(ArrayList<AbstractPierre> chaine, AbstractPierre[][] plateau) {
		ArrayList<AbstractPierre> voisin;
		ArrayList<Coordonnee> liste_liberte = new ArrayList<Coordonnee>();
		Couleur couleurPierre, couleurVoisin = null;
		boolean debut = true;
		
		for(AbstractPierre pierre : chaine) {
			pierre.updateLiberte(plateau, taille_goban);

			couleurPierre = pierre.getCouleur();
			
			for(Coordonnee coord : GoPierre.intersectionVide(pierre, plateau, taille_goban)) {
				ArrayList<Coordonnee> copy_liste_liberte = new ArrayList<Coordonnee>();
				copy_liste_liberte.addAll(liste_liberte);
				
				if(!liste_liberte.isEmpty()) {
					for(Coordonnee c : copy_liste_liberte) {
						if(coord.getX() != c.getX() && coord.getY() != c.getY()) {
							liste_liberte.add(coord);
						}
					} 
				}
				
				else {
					liste_liberte.add(coord);
				}
			}
			
			if(liste_liberte.size() > 2) {
				return false;
			}
			
			voisin = GoPierre.voisins(pierre, plateau, taille_goban);
			
			for(AbstractPierre pierreVoisine : voisin) {
				if(debut) {
					if(!couleurPierre.equals(pierreVoisine.getCouleur())) {
						couleurVoisin = pierreVoisine.getCouleur();
						debut = false;
					}
				}
				
				else {
					if(!couleurVoisin.equals(pierreVoisine.getCouleur()) && !couleurPierre.equals(pierreVoisine.getCouleur())) {
						return false;
					}
				}
			}
		}
		
		return true;
	}
}

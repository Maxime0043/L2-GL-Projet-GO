package traitement;

import java.util.ArrayList;

import donnees.AbstractPierre;
import donnees.Couleur;
import donnees.ParametrePartie;


/**
 * 
 * @author 
 *
 */
public class GoPierre {
	private ArrayList<Integer> liste_voisin_Noir;
	private ArrayList<Integer> liste_voisin_Blanc;
	private ArrayList<Integer> liste_voisin_Rouge;
	
	public GoPierre() {
		liste_voisin_Noir = new ArrayList<Integer>();
		liste_voisin_Blanc = new ArrayList<Integer>();
		liste_voisin_Rouge = new ArrayList<Integer>();
	}
	
	/**
	 * 
	 * @param numero
	 */
	public void addListeNoir(int numero) {
		if(!liste_voisin_Noir.contains(numero))
			liste_voisin_Noir.add(numero);
	}
	
	/**
	 * 
	 * @param numero
	 */
	public void removeListeNoir(int numero) {
		if(liste_voisin_Noir.contains(numero) && (liste_voisin_Noir.size() > 0))
			liste_voisin_Noir.remove(numero);
	}
	
	/**
	 * 
	 * @param numero
	 */
	public void addListeBlanc(int numero) {
		if(!liste_voisin_Blanc.contains(numero))
			liste_voisin_Blanc.add(numero);
	}
	
	/**
	 * 
	 * @param numero
	 */
	public void removeListeBlanc(int numero) {
		if(liste_voisin_Blanc.contains(numero) && (liste_voisin_Blanc.size() > 0))
			liste_voisin_Blanc.add(numero);
	}
	
	/**
	 * 
	 * @param numero
	 */
	public void addListeRouge(int numero) {
		if(!liste_voisin_Rouge.contains(numero))
			liste_voisin_Rouge.add(numero);
	}
	
	/**
	 * 
	 * @param numero
	 */
	public void removeListeRouge(int numero) {
		if(liste_voisin_Rouge.contains(numero) && (liste_voisin_Rouge.size() > 0))
			liste_voisin_Rouge.add(numero);
	}
	
	/**
	 * 
	 * @param couleur
	 * @param numero
	 */
	public void addList(String couleur, int numero) {
		if(couleur.equals(Couleur.NOIR.getCouleur())) {
			addListeNoir(numero);
		}
		else if(couleur.equals(Couleur.BLANC.getCouleur())) {
			addListeBlanc(numero);
		}
		else if(couleur.equals(Couleur.ROUGE.getCouleur())) {
			addListeRouge(numero);
		}
	}
	
	/**
	 * 
	 * @param couleur
	 * @param numero
	 */
	public void removeList(String couleur, int numero) {
		if(couleur.equals(Couleur.NOIR.getCouleur())) {
			removeListeNoir(numero);
		}
		else if(couleur.equals(Couleur.BLANC.getCouleur())) {
			removeListeBlanc(numero);
		}
		else if(couleur.equals(Couleur.ROUGE.getCouleur())) {
			removeListeRouge(numero);
		}
	}
	
	/**
	 * 
	 * @param pierre
	 * @return
	 */
	public boolean bordHaut(AbstractPierre pierre) {
		if(pierre.getX() == 0) {
				return true;
		}
		
		return false;
	}
	
	/**
	 * 
	 * @param pierre
	 * @return
	 */
	public boolean bordGauche(AbstractPierre pierre) {
		if(pierre.getY() == 0)
			return true;
		
		return false;
	}
	
	/**
	 * 
	 * @param pierre
	 * @return
	 */
	public boolean bordDroit(AbstractPierre pierre, int choix) {
		int y = pierre.getY();
		
		if(pierre.isMegaPierre()) {
			y++;
		}
		
		if(y == ParametrePartie.TAILLE_GOBAN[choix])
			return true;
		
		return false;
	}
	
	/**
	 * 
	 * @param pierre
	 * @param choix
	 * @return
	 */
	public boolean bordBas(AbstractPierre pierre, int choix) {
		int x = pierre.getX();
		
		if(pierre.isMegaPierre()) {
			x++;
		}
		
		if(x == ParametrePartie.TAILLE_GOBAN[choix]) {
				return true;
		}
		
		return false;
	}
	
	/**
	 * 
	 * @param pierre
	 * @param plateau
	 * @param choix
	 * @return
	 */
	public ArrayList<Integer> voisins(AbstractPierre pierre, AbstractPierre[][] plateau, int choix) {
		String couleurPierre = pierre.getCouleur();
		
		int x = pierre.getX();
		int y = pierre.getY();
		
		if(pierre.isMegaPierre()) {
			x++;
			y++;
		}
		
		AbstractPierre haut = plateau[x-1][y];
		AbstractPierre bas = plateau[x+1][y];
		AbstractPierre gauche = plateau[x][y-1];
		AbstractPierre droite = plateau[x][y+1];
		
		if(bordHaut(pierre)) {
			if(!bordGauche(pierre) && pierreEnemieCollee(gauche, couleurPierre, gauche.getCouleur())) {
				addList(gauche.getCouleur(), gauche.getNumero());
			}
			
			else if(!bordDroit(pierre, choix) && pierreEnemieCollee(droite, couleurPierre, droite.getCouleur())) {
				addList(droite.getCouleur(), droite.getNumero());
			}
			
			if(pierreEnemieCollee(bas, couleurPierre, bas.getCouleur())) {
				addList(bas.getCouleur(), bas.getNumero());
			}
		}
		
		else if(bordBas(pierre, choix)) {
			if(!bordGauche(pierre) && pierreEnemieCollee(gauche, couleurPierre, gauche.getCouleur())) {
				addList(gauche.getCouleur(), gauche.getNumero());
			}
			
			else if(!bordDroit(pierre, choix) && pierreEnemieCollee(droite, couleurPierre, droite.getCouleur())) {
				addList(droite.getCouleur(), droite.getNumero());
			}
			
			if(pierreEnemieCollee(haut, couleurPierre, haut.getCouleur())) {
				addList(haut.getCouleur(), haut.getNumero());
			}
		}
		
		else if(bordGauche(pierre)) {
			if(pierreEnemieCollee(haut, couleurPierre, haut.getCouleur())) {
				addList(haut.getCouleur(), haut.getNumero());
			}
			
			if(pierreEnemieCollee(bas, couleurPierre, bas.getCouleur())) {
				addList(bas.getCouleur(), bas.getNumero());
			}
			
			if(pierreEnemieCollee(droite, couleurPierre, droite.getCouleur())) {
				addList(droite.getCouleur(), droite.getNumero());
			}
		}
		
		else if(bordDroit(pierre, choix)) {
			if(pierreEnemieCollee(haut, couleurPierre, haut.getCouleur())) {
				addList(haut.getCouleur(), haut.getNumero());
			}
			
			if(pierreEnemieCollee(bas, couleurPierre, bas.getCouleur())) {
				addList(bas.getCouleur(), bas.getNumero());
			}
			
			if(pierreEnemieCollee(gauche, couleurPierre, gauche.getCouleur())) {
				addList(gauche.getCouleur(), gauche.getNumero());
			}
		}
		
		else {
			if(pierreEnemieCollee(haut, couleurPierre, haut.getCouleur())) {
				addList(haut.getCouleur(), haut.getNumero());
			}
			
			if(pierreEnemieCollee(bas, couleurPierre, bas.getCouleur())) {
				addList(bas.getCouleur(), bas.getNumero());
			}
			
			if(pierreEnemieCollee(gauche, couleurPierre, gauche.getCouleur())) {
				addList(gauche.getCouleur(), gauche.getNumero());
			}
			
			if(pierreEnemieCollee(droite, couleurPierre, droite.getCouleur())) {
				addList(droite.getCouleur(), droite.getNumero());
			}
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param pierreEnnemi
	 * @param couleurPierre
	 * @param couleurEnnemi
	 * @return
	 */
	public boolean pierreEnemieCollee(AbstractPierre pierreEnnemi, String couleurPierre, String couleurEnnemi) {
		if((pierreEnnemi != null) && (!couleurPierre.equals(couleurEnnemi))) {
			int numero = pierreEnnemi.getNumero();
			String[] couleurEnnemis = Couleur.couleurEnnemis(couleurPierre);
			
			for(String couleur : couleurEnnemis) {
				if(couleur.equals(Couleur.NOIR.getCouleur()) && liste_voisin_Noir.contains(numero))
					return true;
				
				if(couleur.equals(Couleur.BLANC.getCouleur()) && liste_voisin_Blanc.contains(numero))
					return true;
				
				if(couleur.equals(Couleur.ROUGE.getCouleur()) && liste_voisin_Rouge.contains(numero))
					return true;
			}
		}
		
		return false;
	}
}

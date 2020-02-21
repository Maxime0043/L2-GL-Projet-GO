package traitement;

import java.util.ArrayList;
import java.util.HashMap;

import donnees.AbstractPierre;
import donnees.Coordonnee;
import donnees.MegaPierre;

/**
 * 
 * @author 
 *
 */

public class Liberte {
	private int nb_liberte;
	private AbstractPierre pierre;
	private GoPierre gopierre;
//	private ArrayList<AbstractPierre> pierresAutour;
//	
//	private boolean isBordHaut = false;
//	private boolean isBordBas = false;
//	private boolean isBordGauche = false;
//	private boolean isBordDroit = false;
	
	/**
	 * 
	 * @param pierre
	 */
	public Liberte(AbstractPierre pierre) {
		this.pierre = pierre;
		
		if(pierre.isMegaPierre())
			nb_liberte = 8;
		else
			nb_liberte = 4;
		
		gopierre = new GoPierre();
//		pierresAutour = new ArrayList<AbstractPierre>();
	}

	/**
	 * 
	 * @return
	 */
	public int getLiberte() {
		return nb_liberte;
	}
	
	/**
	 * 
	 * @param nomChaine
	 * @param hmChaine
	 * @param plateau
	 * @param choix
	 * @return
	 */
	public boolean liberteChaine(int nomChaine, HashMap<Integer, Chaine> hmChaine, AbstractPierre[][] plateau, int choix) {
		int liberte = 0;
		for (AbstractPierre p : hmChaine.get(nomChaine).getChaine()) {
			liberte += p.getLiberte();
		}
		if(liberte > 0) {
			return true;
		}
		else 
			return false;
	}
	
	/**
	 * 
	 * @param plateau
	 * @param taille_goban
	 */
	public void updateLiberte(AbstractPierre[][] plateau, int taille_goban) {
		nb_liberte = 0;
		
		if(pierre.isMegaPierre()) {
			MegaPierre pierreOrigine = new MegaPierre(pierre.getCouleur(), new Coordonnee(pierre.getX(), pierre.getY()));
			ArrayList<AbstractPierre> voisins = gopierre.voisins(pierreOrigine, plateau, taille_goban);
			
			if(!gopierre.bordHaut(pierreOrigine)) {
				nb_liberte += 2;
			}
			
			if(!gopierre.bordBas(pierreOrigine, taille_goban)) {
				nb_liberte += 2;
			}
			
			if(!gopierre.bordGauche(pierreOrigine)) {
				nb_liberte += 2;
			}
			
			if(!gopierre.bordDroit(pierreOrigine, taille_goban)) {
				nb_liberte += 2;
			}
			
			for(AbstractPierre pierreVoisin : voisins) {
				if(pierreVoisin.isMegaPierre() && !pierre.getCouleur().equals(pierreVoisin.getCouleur())) {
					if((pierre.getX() == pierreVoisin.getX()) || (pierre.getY() == pierreVoisin.getY())) {
						nb_liberte--;
					}
				}
			}
			
			nb_liberte -= voisins.size();
		}
		
		else {
			if(!gopierre.bordHaut(pierre)) {
				nb_liberte++;
			}
			
			if(!gopierre.bordBas(pierre, taille_goban)) {
				nb_liberte++;
			}
			
			if(!gopierre.bordGauche(pierre)) {
				nb_liberte++;
			}
			
			if(!gopierre.bordDroit(pierre, taille_goban)) {
				nb_liberte++;
			}
			
			nb_liberte -= gopierre.voisins(pierre, plateau, taille_goban).size();
		}
	}
}

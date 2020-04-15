package traitement;

import java.util.ArrayList;

import donnees.AbstractPierre;
import donnees.Coordonnee;
import donnees.MegaPierre;

/**
 * Permet de déterminer le nombre de libertés d'une pierre ou d'une méga_pierre.
 * 
 * @author Maxime, Micael et Houssam
 *
 */
public class Liberte {

	private int nb_liberte;
	private AbstractPierre pierre;
	
	/**
	 * Pour créer la liberté d'une pierre ou d'une méga-pierre il suffit de la passer en paramètre.
	 * 
	 * @param pierre Définit la pierre ou méga-pierre où l'on veut définir sa liberté.
	 */
	public Liberte(AbstractPierre pierre) {
		this.pierre = pierre;
		
		if(pierre.isMegaPierre())
			nb_liberte = 8;
		else
			nb_liberte = 4;
	}

	public int getLiberte() {
		return nb_liberte;
	}
	
	/**
	 * Permet d'actualiser le nombre de libertés de la pierre ou méga-pierre définie.
	 * 
	 * @param plateau Définit le tableau dans lequel est la pierre ou méga-pierre.
	 * @param taille_goban Définit la taille du tableau.
	 */
	public void updateLiberte(AbstractPierre[][] plateau, int taille_goban) {
		nb_liberte = 0;
		
		if(pierre.isMegaPierre()) {
			MegaPierre pierreOrigine = new MegaPierre(pierre.getCouleur(), new Coordonnee(pierre.getX(), pierre.getY()));
			ArrayList<AbstractPierre> voisins = GoPierre.voisins(pierreOrigine, plateau, taille_goban);
			
			if(!GoPierre.bordHaut(pierreOrigine)) {
				nb_liberte += 2;
			}
			
			if(!GoPierre.bordBas(pierreOrigine, taille_goban)) {
				nb_liberte += 2;
			}
			
			if(!GoPierre.bordGauche(pierreOrigine)) {
				nb_liberte += 2;
			}
			
			if(!GoPierre.bordDroit(pierreOrigine, taille_goban)) {
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
			if(!GoPierre.bordHaut(pierre)) {
				nb_liberte++;
			}
			
			if(!GoPierre.bordBas(pierre, taille_goban)) {
				nb_liberte++;
			}
			
			if(!GoPierre.bordGauche(pierre)) {
				nb_liberte++;
			}
			
			if(!GoPierre.bordDroit(pierre, taille_goban)) {
				nb_liberte++;
			}
			
			nb_liberte -= GoPierre.voisins(pierre, plateau, taille_goban).size();
		}
	}
}

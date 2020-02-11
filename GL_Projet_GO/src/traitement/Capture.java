package traitement;

import java.util.ArrayList;

import donnees.AbstractPierre;
import donnees.Couleur;

public class Capture {
	private GoPierre gopierre;
	
	public Capture() {
		gopierre = new GoPierre();
	}
	
	/**
	 * 
	 * @param pierre
	 * @param plateau
	 * @param choix
	 * @return
	 */
	public boolean isCapture(AbstractPierre pierre, AbstractPierre[][] plateau, int choix) {
		pierre.updateLiberte(plateau, choix);
		
		if(pierre.getLiberte() > 0) {
			return false;
		}
		
		else {
			ArrayList<AbstractPierre> listVoisin = gopierre.voisins(pierre, plateau, choix);
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
			
			System.out.println("Couleur du preneur d'otage : " + listVoisin.get(0).getCouleur());
		}
		
		return true;
	}
	
	/**
	 * 
	 * @param chaine
	 * @param plateau
	 * @param choix
	 * @return
	 */
	public boolean isCapture(ArrayList<AbstractPierre> chaine, AbstractPierre[][] plateau, int choix) {
		ArrayList<AbstractPierre> voisin;
		ArrayList<Couleur> couleurPierres = new ArrayList<Couleur>();
		
		for(AbstractPierre pierre : chaine) {
			pierre.updateLiberte(plateau, choix);
			if(pierre.getLiberte() > 0) {
				return false;
			}
			
			voisin = gopierre.voisins(pierre, plateau, choix);
			
			for(AbstractPierre pierreVoisine : voisin) {
				if(couleurPierres.contains(pierreVoisine.getCouleur())) {
					couleurPierres.add(pierreVoisine.getCouleur());
				}
			}
			
			if(couleurPierres.size() > 1) {
				return false;
			}
		}
		
		return true;
	}
}

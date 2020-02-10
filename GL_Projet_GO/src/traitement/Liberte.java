package traitement;

import donnees.AbstractPierre;

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
		
//		if(pierre.isMegaPierre())
//			nb_liberte = 8;
//		else
//			nb_liberte = 4;
		
		nb_liberte = 0;
		
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
	 * @param plateau
	 * @param choix
	 */
	public void updateLiberte(AbstractPierre[][] plateau, int choix) {
		nb_liberte = 0;
		
		if(!gopierre.bordHaut(pierre)) {
			nb_liberte++;
		}
		
		if(!gopierre.bordBas(pierre, choix)) {
			nb_liberte++;
		}
		
		if(!gopierre.bordGauche(pierre)) {
			nb_liberte++;
		}
		
		if(!gopierre.bordDroit(pierre, choix)) {
			nb_liberte++;
		}
		
		nb_liberte -= gopierre.voisins(pierre, plateau, choix).size();
		
//		ArrayList<AbstractPierre> voisins = gopierre.voisins(pierre, plateau, choix);
//		
//		if(voisins.size() > 0) {
//			if(pierresAutour.size() == 0) {
//				pierresAutour.addAll(voisins);
//				nb_liberte -= voisins.size();
//			}
//			else {
//				ArrayList<AbstractPierre> tmp = new ArrayList<AbstractPierre>();
//				tmp.addAll(pierresAutour);
//				pierresAutour.clear();
//				
//				int x1, x2, y1, y2;
//			
//				for(AbstractPierre p1 : voisins) {
//					x1 = p1.getX();
//					y1 = p1.getY();
//					
//					for(AbstractPierre p2 : tmp) {
//						x2 = p2.getX();
//						y2 = p2.getY();
//						
//						if((x1 != x2) && (y1 != y2)) {
//							pierresAutour.add(p1);
//							nb_liberte--;
//							break;
//						}
//					}
//				}
//				
//				for(AbstractPierre p1 : tmp) {
//					x1 = p1.getX();
//					y1 = p1.getY();
//					
//					for(AbstractPierre p2 : pierresAutour) {
//						x2 = p2.getX();
//						y2 = p2.getY();
//						
//						if((x1 != x2) && (y1 != y2)) {
//							nb_liberte++;
//							break;
//						}
//					}
//				}
//				System.out.println("Voisins : " + pierresAutour.size());
//			}
//		}
//			
//		if(pierre.isMegaPierre()) {
//			
//		}
//		
//		else {
//			if(gopierre.bordHaut(pierre) && !isBordHaut) {
//				nb_liberte--;
//				isBordHaut = true;
//			}
//			if(gopierre.bordBas(pierre, choix) && !isBordBas) {
//				nb_liberte--;
//				isBordBas = true;
//			}
//			if(gopierre.bordGauche(pierre) && !isBordGauche) {
//				nb_liberte--;
//				isBordGauche = true;
//			}
//			if(gopierre.bordDroit(pierre, choix) && !isBordDroit) {
//				nb_liberte--;
//				isBordDroit = true;
//			}
//		}
	}
}

package traitement;

import donnees.ParametrePartie;

public class CalculFactory {
	public static int getCoordTableau(int coord, int ecart_window, int cellule) {
		return (coord - ecart_window + (cellule / 2)) / cellule;
	}
	
	public static int getCoordWindow(int coord, int ecart_window, int cellule, int petit_decalage) {
		return coord * cellule + ecart_window + petit_decalage - (cellule / 2);
	}
	
	public static int getCoordEcartHorizontal(int taille_goban, int cellule, boolean isDidacticiel) {
		if(isDidacticiel) {
			return ParametrePartie.ECART_HORIZONTAL;
		}
		
		else {
			return (ParametrePartie.WINDOW_WIDTH - (taille_goban - 1) * cellule) / 2;
		}
	}
}

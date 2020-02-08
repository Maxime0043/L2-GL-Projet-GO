package traitement;

import java.util.ArrayList;

import donnees.AbstractPierre;

public class Chaine {
	
	private ArrayList <AbstractPierre> chaine;
	private int liberte;
	
	public Chaine() {
		chaine = new ArrayList <AbstractPierre>();
	}
	
	public void addPierre (AbstractPierre pierre) {
		if(!chaine.contains(pierre))
			chaine.add(pierre);
	}
	
	public boolean vivante(AbstractPierre[][] plateau, int choix) {
		return Capture.isCapture(chaine, plateau, choix);
	}
}
package traitement;

import java.util.ArrayList;

import donnees.AbstractPierre;

public class Chaine {
	
	private boolean twoEye;
	
	private ArrayList<AbstractPierre> chaine;
//	private Capture capture;
	
	public Chaine() {
		chaine = new ArrayList<AbstractPierre>();
//		capture = new Capture();
	}
	
	public ArrayList<AbstractPierre> getChaine() {
		return chaine;
	}
	
	public void addPierre (AbstractPierre pierre) {
		if(!chaine.contains(pierre)) {
			chaine.add(pierre);
		}
	}
	
//	public boolean vivante(AbstractPierre[][] plateau, int choix) {
//		return capture.isCapture(chaine, plateau, choix);
//	}
}
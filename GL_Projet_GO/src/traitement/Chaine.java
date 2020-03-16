package traitement;

import java.util.ArrayList;

import donnees.AbstractPierre;

public class Chaine {
	
	private boolean twoEye;
	
	private ArrayList<AbstractPierre> chaine;
	
	public Chaine() {
		chaine = new ArrayList<AbstractPierre>();
	}
	
	public ArrayList<AbstractPierre> getChaine() {
		return chaine;
	}
	
	public void addPierre(AbstractPierre pierre) {
		if(!chaine.contains(pierre)) {
			chaine.add(pierre);
		}
	}
	
	public void removePierre(AbstractPierre pierre) {
		if(chaine.contains(pierre)) {
			chaine.remove(pierre);
		}
	}
}
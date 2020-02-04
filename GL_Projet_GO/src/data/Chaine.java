package data;

import java.util.ArrayList;

public class Chaine {
	
	private ArrayList <AbstractPierre> chaine;
	private int liberte;
	
	public Chaine() {
		chaine = new ArrayList <AbstractPierre>();
	}
	
	public void addPierre (AbstractPierre pierre) {
		chaine.add(pierre);
	}
	
	public boolean vivante() {
		if (this.liberte == 0) {
			return false;
		}
		else
			return true;
	}
}

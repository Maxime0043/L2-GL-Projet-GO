package donnees;

import traitement.Liberte;

public abstract class AbstractPierre {
	private Couleur couleur;
	private Liberte liberte;
	private String nomChaine;
	
	public AbstractPierre(Couleur couleur, Liberte liberte, String nomChaine) {
		this.couleur = couleur;
		this.liberte = new Liberte(this);
		this.nomChaine = nomChaine;
	}

	public abstract int getX();
	public abstract int getY();
	public abstract boolean voisin(AbstractPierre[][] plateau);
	public abstract boolean vivante();
	public abstract boolean isMegaPierre();
	
	public Couleur getCouleur() {
		return couleur;
	}
}

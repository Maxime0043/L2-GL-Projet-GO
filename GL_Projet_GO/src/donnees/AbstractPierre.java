package donnees;

import traitement.Liberte;

public abstract class AbstractPierre {
	private Couleur couleur;
	private Liberte liberte;
	private String nomChaine;
	private int numero;
	
	
	public AbstractPierre(Couleur couleur, Liberte liberte, String nomChaine, int numero) {
		this.couleur = couleur;
		this.liberte = new Liberte(this);
		this.nomChaine = nomChaine;
		this.numero = numero;
	}

	public abstract int getX();
	public abstract int getY();
	public abstract boolean voisin(AbstractPierre[][] plateau);
	public abstract boolean vivante();
	public abstract boolean isMegaPierre();
	
	public String getCouleur() {
		return couleur.getCouleur();
	}
	
	public int getNumero() {
		return numero;
	}
}

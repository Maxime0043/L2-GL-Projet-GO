package donnees;

import traitement.Liberte;

public abstract class AbstractPierre {
	private Couleur couleur;
	private Liberte liberte;
	private String nomChaine;
	private int numero;
	
	
	public AbstractPierre(Couleur couleur, String nomChaine, int numero) {
		this.couleur = couleur;
		this.liberte = new Liberte(this);
		this.nomChaine = nomChaine;
		this.numero = numero;
	}

	/**
	 * 
	 * @return
	 */
	public abstract int getX();
	
	/***
	 * 
	 * @return
	 */
	public abstract int getY();
	
	/**
	 * 
	 * @param plateau
	 * @return
	 */
	public abstract boolean voisin(AbstractPierre[][] plateau);
	
	/**
	 * 
	 * @return
	 */
	public abstract boolean vivante();
	
	/**
	 * 
	 * @return
	 */
	public abstract boolean isMegaPierre();
	
	/**
	 * 
	 * @return
	 */
	public Couleur getCouleur() {
		return couleur;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getNumero() {
		return numero;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getLiberte() {
		return liberte.getLiberte();
	}
}

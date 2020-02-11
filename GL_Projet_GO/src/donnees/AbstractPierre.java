package donnees;

import traitement.Liberte;

public abstract class AbstractPierre {
	private Couleur couleur;
	private Liberte liberte;
	private String nomChaine;
	private int numero;
	
	
	public AbstractPierre(Couleur couleur, int numero) {
		this.couleur = couleur;
		this.liberte = new Liberte(this);
		this.nomChaine = null;
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
	
	public String getNomChaine() {
		return nomChaine;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getLiberte() {
		return liberte.getLiberte();
	}
	
	public void updateLiberte(AbstractPierre[][] plateau, int choix) {
		liberte.updateLiberte(plateau, choix);
	}
}

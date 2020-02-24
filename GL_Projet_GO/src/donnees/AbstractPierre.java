package donnees;

import traitement.Liberte;

public abstract class AbstractPierre {
	private Couleur couleur;
	private Liberte liberte;
	private int nomChaine;
	
	
	public AbstractPierre(Couleur couleur) {
		this.couleur = couleur;
		this.liberte = new Liberte(this);
		this.nomChaine = -1;
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
	
	public void setNomChaine(int nomChaine) {
		this.nomChaine = nomChaine;
	}
	
	public int getNomChaine() {
		return nomChaine;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean hasChaine() {
		if(this.nomChaine != -1) {
			return true;
		}
		else
			return false;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getLiberte() {
		return liberte.getLiberte();
	}
	
	/**
	 * 
	 * @param plateau
	 * @param taille_goban
	 */
	public void updateLiberte(AbstractPierre[][] plateau, int taille_goban) {
		liberte.updateLiberte(plateau, taille_goban);
	}
}

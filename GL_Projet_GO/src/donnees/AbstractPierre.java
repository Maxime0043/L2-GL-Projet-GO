package donnees;

import traitement.Liberte;

/**
 * Cette classe représente tous les types de pierres.
 * A savoir les Pierres et les Méga-pierres.
 * 
 * @author Maxime, Micael et Houssam
 *
 */
public abstract class AbstractPierre {
	private Couleur couleur;
	private Coordonnee coord;
	private Liberte liberte;
	private int nomChaine;
	private boolean vivante;
	
	/**
	 * Pour construire une Pierre ou une Méga-pierre
	 * nous avons besoin  de lui affecter une couleur
	 * et des coordonnées
	 * 
	 * @param couleur La couleur définissant la pierre / méga-pierre
	 * @param coord La coordonée que possédera la pierre / méga-pierre
	 */
	public AbstractPierre(Couleur couleur, Coordonnee coord) {
		this.couleur = couleur;
		this.coord = coord;
		this.liberte = new Liberte(this);
		this.nomChaine = -1;
	}
	
	public AbstractPierre(Couleur couleur, int x, int y) {
		this(couleur, new Coordonnee(x, y));
	}

	public int getX() {
		return coord.getX();
	}
	
	public int getY() {
		return coord.getY();
	}
	
	/**
	 * Permet de savoir si une pierre est une 
	 * Méga-pierre ou non
	 * 
	 * @return
	 */
	public abstract boolean isMegaPierre();
	
	public Couleur getCouleur() {
		return couleur;
	}
	
	public boolean isVivante() {
		return vivante;
	}
	
	public void setVivante(boolean vivante) {
		this.vivante = vivante;
	}
	
	public void setNomChaine(int nomChaine) {
		this.nomChaine = nomChaine;
	}
	
	public int getNomChaine() {
		return nomChaine;
	}
	
	public boolean hasChaine() {
		if(this.nomChaine != -1) {
			return true;
		}
		else
			return false;
	}
	
	public int getLiberte() {
		return liberte.getLiberte();
	}
	
	/**
	 * Permet de mettre à jour le nombre de libertés
	 * que possède une pierre / méga-pierre
	 * 
	 * @param plateau Le tableau où les pierres / méga-pierres sont ajoutées
	 * @param taille_goban La taille du plateau
	 */
	public void updateLiberte(AbstractPierre[][] plateau, int taille_goban) {
		liberte.updateLiberte(plateau, taille_goban);
	}
}

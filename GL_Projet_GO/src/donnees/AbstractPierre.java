package donnees;

import traitement.Liberte;

/**
 * Cette classe repr�sente tous les types de pierres.
 * A savoir les Pierres et les M�ga-pierres.
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
	 * Pour construire une Pierre ou une M�ga-pierre
	 * nous avons besoin  de lui affecter une couleur
	 * et des coordonn�es
	 * 
	 * @param couleur La couleur d�finissant la pierre / m�ga-pierre
	 * @param coord La coordon�e que poss�dera la pierre / m�ga-pierre
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
	 * M�ga-pierre ou non
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
	 * Permet de mettre � jour le nombre de libert�s
	 * que poss�de une pierre / m�ga-pierre
	 * 
	 * @param plateau Le tableau o� les pierres / m�ga-pierres sont ajout�es
	 * @param taille_goban La taille du plateau
	 */
	public void updateLiberte(AbstractPierre[][] plateau, int taille_goban) {
		liberte.updateLiberte(plateau, taille_goban);
	}
}

package data;

public abstract class AbstractPierre {
	private Couleur couleur;
	private int liberte;
	private String nomChaine;
	
	public AbstractPierre(Couleur couleur, int liberte, String nomChaine) {
		this.couleur = couleur;
		this.liberte = liberte;
		this.nomChaine = nomChaine;
	}
	
	public abstract boolean voisin(AbstractPierre[][] plateau);
}

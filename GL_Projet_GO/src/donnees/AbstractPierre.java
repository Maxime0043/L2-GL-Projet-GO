package donnees;

public abstract class AbstractPierre {
	private Couleur couleur;
	private int liberte;
	private String nomChaine;
	private boolean vivante;
	
	public AbstractPierre(Couleur couleur, int liberte, String nomChaine) {
		this.couleur = couleur;
		this.liberte = liberte;
		this.nomChaine = nomChaine;
	}
	
	public abstract boolean voisin(AbstractPierre[][] plateau);
	public abstract boolean vivante();
}

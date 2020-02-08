package donnees;

public class Pierre extends AbstractPierre{
	private Coordonnee coord;
	
	public Pierre(Couleur couleur, int liberte, String nomChaine, Coordonnee coord) {
		super(couleur, liberte, nomChaine);
		this.coord = coord;
	}

	@Override
	public boolean voisin(AbstractPierre[][] plateau) {
		return false;
	}

	@Override
	public boolean vivante() {
		return true;
	}
}

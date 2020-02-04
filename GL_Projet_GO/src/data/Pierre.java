package data;

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
}

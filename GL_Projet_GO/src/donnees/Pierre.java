package donnees;

public class Pierre extends AbstractPierre{
	private Coordonnee coord;
	
	public Pierre(Couleur couleur, String nomChaine, Coordonnee coord, int numero) {
		super(couleur, numero);
		this.coord = coord;
	}
	
	@Override
	public int getX() {
		return coord.getX();
	}
	
	@Override
	public int getY() {
		return coord.getY();
	}
	
	@Override
	public boolean voisin(AbstractPierre[][] plateau) {
		return false;
	}

	@Override
	public boolean vivante() {
		return true;
	}
	
	@Override
	public boolean isMegaPierre() {
		return false;
	}
}

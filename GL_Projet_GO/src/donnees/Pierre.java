package donnees;

public class Pierre extends AbstractPierre{
	private Coordonnee coord;
	
	public Pierre(Couleur couleur, Coordonnee coord) {
		super(couleur);
		this.coord = coord;
	}
	
	public Pierre(Couleur couleur, int x, int y) {
		this(couleur, new Coordonnee(x, y));
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
	public boolean isMegaPierre() {
		return false;
	}
}

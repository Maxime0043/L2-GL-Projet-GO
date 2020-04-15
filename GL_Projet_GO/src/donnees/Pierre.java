package donnees;

public class Pierre extends AbstractPierre{
	
	public Pierre(Couleur couleur, Coordonnee coord) {
		super(couleur, coord);
	}
	
	public Pierre(Couleur couleur, int x, int y) {
		this(couleur, new Coordonnee(x, y));
	}
	
	@Override
	public boolean isMegaPierre() {
		return false;
	}
}

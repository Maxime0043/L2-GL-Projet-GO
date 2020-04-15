package donnees;

public class MegaPierre extends AbstractPierre{

	public MegaPierre(Couleur couleur, Coordonnee coord) {
		super(couleur, coord);
	}
	
	public MegaPierre(Couleur couleur, int x, int y) {
		this(couleur, new Coordonnee(x, y));
	}

	@Override
	public boolean isMegaPierre() {
		return true;
	}

}

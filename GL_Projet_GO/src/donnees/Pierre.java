package donnees;

import traitement.Liberte;

public class Pierre extends AbstractPierre{
	private Coordonnee coord;
	
	public Pierre(Couleur couleur, Liberte liberte, String nomChaine, Coordonnee coord) {
		super(couleur, liberte, nomChaine);
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
	
	public boolean isMegaPierre() {
		return false;
	}
}

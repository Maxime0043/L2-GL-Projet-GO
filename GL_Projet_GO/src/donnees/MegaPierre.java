package donnees;

import traitement.Liberte;

public class MegaPierre extends AbstractPierre{

	private Coordonnee[] coord;
	
	public MegaPierre(Couleur couleur, Liberte liberte, String nomChaine, Coordonnee[] coord) {
		super(couleur, liberte, nomChaine);
		this.coord = coord;
	}
	
	@Override
	public int getX() {
		return coord[0].getX();
	}
	
	@Override
	public int getY() {
		return coord[0].getX();
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
		return true;
	}

}

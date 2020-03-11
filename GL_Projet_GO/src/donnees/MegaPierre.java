package donnees;

public class MegaPierre extends AbstractPierre{

	private Coordonnee[] coordListe;
	private int x, y;
	
	public MegaPierre(Couleur couleur, Coordonnee coord) {
		super(couleur);

		coordListe = new Coordonnee[4];
		
		x = coord.getX();
		y = coord.getY();
		
		initCoord();
	}
	
	public MegaPierre(Couleur couleur, int x, int y) {
		this(couleur, new Coordonnee(x, y));
	}
	
	private void initCoord(){
		coordListe[0] = new Coordonnee(x, y);
		coordListe[1] = new Coordonnee(x, y+1);
		coordListe[2] = new Coordonnee(x+1, y);
		coordListe[3] = new Coordonnee(x+1, y+1);
	}
	
	@Override
	public int getX() {
		return coordListe[0].getX();
	}
	
	@Override
	public int getY() {
		return coordListe[0].getY();
	}

	@Override
	public boolean isMegaPierre() {
		return true;
	}

}

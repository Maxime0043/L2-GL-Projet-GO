package donnees;

public class Plateau {
	
	private AbstractPierre[][] plateau;
	
	private int taille_goban;
	
	public Plateau(int taille_goban) {
		this.taille_goban = taille_goban;
		
		plateau = new AbstractPierre[taille_goban][taille_goban];
		
		initPlateau(taille_goban);
	}
	
	public void initPlateau(int taille_goban) {
		for(int i = 0 ; i < taille_goban ; i++) {
			for(int j = 0 ; j < taille_goban ; j++) {
				plateau[i][j] = null;
			}
		}
	}
	
	public AbstractPierre[][] getPlateau(){
		return plateau;
	}
	
	public void addPierre(AbstractPierre pierre) {
		int x = pierre.getX();
		int y = pierre.getY();
		
		plateau[x][y] = pierre;
		
		if(pierre.isMegaPierre()) {
			plateau[x+1][y] = pierre;
			plateau[x+1][y+1] = pierre;
			plateau[x][y+1] = pierre;
		}
		
//		if(pierre.isMegaPierre()) {
//			for(Coordonnee c : pierre.getCoordonnees) {
//				
//			}
//		}
	}
	
	public void removePierre(int x, int y) {
		plateau[x][y] = null;
	}
	
	public AbstractPierre getPierre(int x, int y) {
		AbstractPierre pierre = plateau[x][y];
		
		pierre.updateLiberte(plateau, taille_goban);
		
		return pierre;
	}
}
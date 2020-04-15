package donnees;

/**
 * Cette classe représente le score qui sera
 * affecté à chacun des joueurs.
 * 
 * @author Maxime, Micael et Houssam
 *
 */
public class Score {

	private int score;
	private int capture;
	private int territoire;
	private int komi;
	
	public Score () {
		this.score = 0;
		this.capture = 0;
//		this.komi = komi;
	}
	
	public int getScore() {
		return score;
	}
	
	public void initScore() {
		score = 0;
	}
	
	public void addScore(int nombre) {
		score += nombre;
	}
	
	public void setScore(int nombre) {
		score = nombre;
	}

	/**
	 * Permet d'ajouter le komi au score du joueur
	 */
	public void score_final() {
		addScore(komi);
	}

}

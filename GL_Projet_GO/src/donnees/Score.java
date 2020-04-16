package donnees;

/**
 * Cette classe représente le score qui sera
 * affecté à chacun des joueurs.
 * 
 * @author Maxime, Micael et Houssam
 *
 */
public class Score {

	private double score;
	
	public Score () {
		this.score = 0;
	}
	
	public double getScore() {
		return score;
	}
	
	public void initScore() {
		score = 0;
	}
	
	public void addScore(double nombre) {
		score += nombre;
	}
	
	public void setScore(double nombre) {
		score = nombre;
	}
}

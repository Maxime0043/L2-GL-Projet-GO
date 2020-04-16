package donnees;

/**
 * Cette classe repr�sente le score qui sera
 * affect� � chacun des joueurs.
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

package donnees;

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
	
	public void addScore() {
		score++;
	}

	public int total() {
		int total;
		total = capture + territoire + komi;
		return total;
	}

}

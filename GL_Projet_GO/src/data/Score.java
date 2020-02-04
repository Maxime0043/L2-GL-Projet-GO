package data;

public class Score {

	private int capture;
	private int territoire;
	private int komi;
	
	public Score (int komi) {
		this.capture = 0;
		this.komi = komi;
	}

	public int total() {
		int total;
		total = capture + territoire + komi;
		return total;
	}

}

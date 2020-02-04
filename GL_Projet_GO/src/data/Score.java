package data;

public class Score {
	
	private int capture;
	private int territoire;
	private int komi;
	
	public int total(int capture, int territoire, int komi) {
		int total;
		total = capture + territoire + komi;
		return total;
	}

}

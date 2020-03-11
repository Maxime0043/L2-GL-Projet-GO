package donnees;

public class Decision {
	
	public Decision() {
		
	}
	
	public boolean endgame=false;
	
	public void action() {
		
		if (endgame) {
			
			endgame=true;
			
		}
		if (!endgame) {
			
		}
		
	}
	
	public void reinitialTerritoires() {
		if(endgame) {
			
			endgame=false;
			
		}
		
	}
	
	public void next() {
		
	}
	
	public void skip() {
		
	}
	
	
}

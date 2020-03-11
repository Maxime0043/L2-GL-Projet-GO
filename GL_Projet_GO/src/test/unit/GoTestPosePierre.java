package test.unit;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import donnees.AbstractPierre;
import donnees.Coordonnee;
import donnees.Couleur;
import donnees.MegaPierre;
import donnees.ParametrePartie;
import donnees.Pierre;
import traitement.Goban;

public class GoTestPosePierre {
	private Goban goban;
	private int taille_goban;
	
	@Before
	public void prepareGo() {
		taille_goban = ParametrePartie.TAILLE_GOBAN[0];
		goban = new Goban(taille_goban);
	}
	
	@Test
	public void testGoPosePierre0(){
		int x = 4;
		int y = 4;

		goban.addPierre(new Pierre(Couleur.NOIR, new Coordonnee(x, y)));
		
		boolean exist = goban.existPierre(x, y);
		
		assertTrue(exist);
	}
	
	@Test
	public void testGoPosePierre1(){
		int x = 4;
		int y = 4;
		
		goban.initPlateau();

		goban.addPierre(new MegaPierre(Couleur.NOIR, new Coordonnee(x, y)));
		
		boolean exist = goban.existPierre(x, y);
		
		assertTrue(exist);
	}
	
	@Test
	public void testGoPosePierre2(){
		int x = 4;
		int y = 4;
		
		goban.initPlateau();
		
		goban.addPierre(new Pierre(Couleur.NOIR, new Coordonnee(x-1, y)));
		goban.addPierre(new Pierre(Couleur.NOIR, new Coordonnee(x, y-1)));
		goban.addPierre(new Pierre(Couleur.NOIR, new Coordonnee(x, y+1)));
		goban.addPierre(new Pierre(Couleur.NOIR, new Coordonnee(x+1, y)));

		AbstractPierre pierre = new Pierre(Couleur.BLANC, new Coordonnee(x, y));
		goban.addPierre(pierre);
		
		boolean suicide = goban.isSuicide(pierre);
		
		assertTrue(suicide);
	}
}

package test.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import donnees.AbstractPierre;
import donnees.Coordonnee;
import donnees.Couleur;
import donnees.Goban;
import donnees.MegaPierre;
import donnees.ParametrePartie;
import donnees.Pierre;

public class GoTestRetirePierre {
	private Goban goban;
	private int taille_goban;
	
	@Before
	public void prepareGo() {
		taille_goban = ParametrePartie.TAILLE_GOBAN[0];
		goban = new Goban(taille_goban);
	}
	
	@Test
	public void testGoRetirePierre0(){
		int x = 4;
		int y = 4;
		
		AbstractPierre pierre = new Pierre(Couleur.NOIR, new Coordonnee(x, y));
		goban.addPierre(pierre);
		
		goban.removePierre(pierre);
		
		boolean exist = goban.existPierre(x, y);
		
		assertTrue(!exist);
	}
	
	@Test
	public void testGoRetirePierre1(){
		int x = 4;
		int y = 4;
		
		goban.initPlateau(taille_goban);
		
		AbstractPierre pierre = new MegaPierre(Couleur.NOIR, new Coordonnee(x, y));
		goban.addPierre(pierre);
		
		goban.removePierre(pierre);
		
		boolean exist1 = goban.existPierre(x, y);
		boolean exist2 = goban.existPierre(x, y+1);
		boolean exist3 = goban.existPierre(x+1, y);
		boolean exist4 = goban.existPierre(x+1, y+1);
		
		assertTrue(!exist1);
		assertTrue(!exist2);
		assertTrue(!exist3);
		assertTrue(!exist4);
	}
	
	@Test
	public void testGoRetirePierre2(){
		int x = 4;
		int y = 4;
		
		goban.initPlateau(taille_goban);
		
		AbstractPierre pierre = new Pierre(Couleur.NOIR, new Coordonnee(x, y));
		goban.addPierre(pierre);
		goban.addPierre(new Pierre(Couleur.NOIR, new Coordonnee(x, y+1)));

		boolean hasChaine = pierre.hasChaine();
		assertTrue(hasChaine);
		
		goban.removeChaine(pierre.getNomChaine());
		
		boolean exist1 = goban.existPierre(x, y);
		boolean exist2 = goban.existPierre(x, y+1);
		
		assertTrue(!exist1);
		assertEquals(exist2, exist1);
	}
}

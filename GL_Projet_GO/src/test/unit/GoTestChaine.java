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

public class GoTestChaine {
	private Goban goban;
	private int taille_goban;
	
	@Before
	public void prepareGo() {
		taille_goban = ParametrePartie.TAILLE_GOBAN[0];
		goban = new Goban(taille_goban);
	}
	
	@Test
	public void testGoChaine0(){
		int x = 4;
		int y = 4;

		AbstractPierre pierre = new Pierre(Couleur.NOIR, new Coordonnee(x, y));
		goban.addPierre(pierre);
		
		boolean hasChaine = pierre.hasChaine();
		
		assertTrue(!hasChaine);
	}
	
	@Test
	public void testGoChaine1(){
		int x = 4;
		int y = 4;

		AbstractPierre pierre1 = new Pierre(Couleur.NOIR, new Coordonnee(x, y));
		goban.addPierre(pierre1);
		
		AbstractPierre pierre2 = new Pierre(Couleur.NOIR, new Coordonnee(x, y+1));
		goban.addPierre(pierre2);
		
		boolean hasChaine1 = pierre1.hasChaine();
		boolean hasChaine2 = pierre2.hasChaine();
		
		assertTrue(hasChaine1);
		assertTrue(hasChaine2);
		
		assertEquals(pierre2.getNomChaine(), pierre1.getNomChaine());
	}

	@Test
	public void testGoChaine2(){
		int x = 4;
		int y = 4;

		AbstractPierre pierre1 = new Pierre(Couleur.NOIR, new Coordonnee(x, y));
		goban.addPierre(pierre1);
		
		AbstractPierre pierre2 = new MegaPierre(Couleur.BLANC, new Coordonnee(x, y+1));
		goban.addPierre(pierre2);
		
		boolean hasChaine1 = pierre1.hasChaine();
		boolean hasChaine2 = pierre2.hasChaine();
		
		assertTrue(!hasChaine1);
		assertTrue(!hasChaine2);
	}

	@Test
	public void testGoChaine3(){
		int x = 4;
		int y = 4;

		AbstractPierre pierre1 = new Pierre(Couleur.NOIR, new Coordonnee(x, y));
		goban.addPierre(pierre1);
		
		AbstractPierre pierre2 = new Pierre(Couleur.NOIR, new Coordonnee(x+1, y+1));
		goban.addPierre(pierre2);
		
		boolean hasChaine1 = pierre1.hasChaine();
		boolean hasChaine2 = pierre2.hasChaine();
		
		assertTrue(!hasChaine1);
		assertTrue(!hasChaine2);
	}

	@Test
	public void testGoChaine4(){
		int x = 4;
		int y = 4;

		AbstractPierre pierre1 = new Pierre(Couleur.NOIR, new Coordonnee(x, y));
		goban.addPierre(pierre1);
		goban.addPierre(new Pierre(Couleur.NOIR, new Coordonnee(x, y+1)));
		
		AbstractPierre pierre2 = new Pierre(Couleur.NOIR, new Coordonnee(x+1, y));
		goban.addPierre(pierre2);
		
		boolean hasChaine1 = pierre1.hasChaine();
		boolean hasChaine2 = pierre2.hasChaine();
		
		assertTrue(hasChaine1);
		assertTrue(hasChaine2);
		
		assertEquals(pierre2.getNomChaine(), pierre1.getNomChaine());
	}

	@Test
	public void testGoChaine5(){
		int x = 4;
		int y = 4;

		AbstractPierre pierre1 = new Pierre(Couleur.NOIR, new Coordonnee(x, y));
		goban.addPierre(pierre1);
		goban.addPierre(new Pierre(Couleur.NOIR, new Coordonnee(x, y-1)));
		
		AbstractPierre pierre2 = new Pierre(Couleur.NOIR, new Coordonnee(x+1, y+1));
		goban.addPierre(pierre2);
		goban.addPierre(new Pierre(Couleur.NOIR, new Coordonnee(x+1, y+2)));
		
		boolean hasChaine1 = pierre1.hasChaine();
		boolean hasChaine2 = pierre2.hasChaine();
		
		assertTrue(hasChaine1);
		assertTrue(hasChaine2);
		
		AbstractPierre pierre3 = new Pierre(Couleur.NOIR, new Coordonnee(x, y+1));
		goban.addPierre(pierre3);

		boolean hasChaine3 = pierre3.hasChaine();
		
		assertTrue(hasChaine3);
		
		assertEquals(pierre2.getNomChaine(), pierre1.getNomChaine());
	}
}

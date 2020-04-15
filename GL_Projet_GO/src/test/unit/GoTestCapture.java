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

/**
 * Cette classe permet des testes unitaires sur la capture de pierres / méga-pierres.
 * 
 * @author Maxime, Micael et Houssam
 *
 */
public class GoTestCapture {
	
	private Goban goban;
	private int taille_goban;
	
	@Before
	public void prepareGo() {
		taille_goban = ParametrePartie.TAILLE_GOBAN[0];
		goban = new Goban(taille_goban);
	}
	
	@Test
	public void testGoCapture0() {
		int x = 4;
		int y = 4;
		
		goban.addPierre(new Pierre(Couleur.NOIR, new Coordonnee(x-1, y)));
		goban.addPierre(new Pierre(Couleur.NOIR, new Coordonnee(x, y-1)));
		goban.addPierre(new Pierre(Couleur.NOIR, new Coordonnee(x, y+1)));
		goban.addPierre(new Pierre(Couleur.NOIR, new Coordonnee(x+1, y)));

		AbstractPierre pierre = new Pierre(Couleur.BLANC, new Coordonnee(x, y));
		goban.addPierre(pierre);
		
		boolean isCapture = goban.isPierreCapture(pierre);
		
		assertTrue(isCapture);
	}
	
	@Test
	public void testGoCapture1() {
		int x = 3;
		int y = 3;
		
		goban.initPlateau();
		
		goban.addPierre(new Pierre(Couleur.NOIR, new Coordonnee(x-1, y)));
		goban.addPierre(new Pierre(Couleur.NOIR, new Coordonnee(x-1, y+1)));
		goban.addPierre(new Pierre(Couleur.NOIR, new Coordonnee(x, y-1)));
		goban.addPierre(new Pierre(Couleur.NOIR, new Coordonnee(x, y+2)));
		goban.addPierre(new Pierre(Couleur.NOIR, new Coordonnee(x+1, y)));
		goban.addPierre(new Pierre(Couleur.NOIR, new Coordonnee(x+1, y+1)));


		goban.addPierre(new Pierre(Couleur.BLANC, new Coordonnee(x, y)));
		goban.addPierre(new Pierre(Couleur.BLANC, new Coordonnee(x, y+1)));
		
		AbstractPierre pierre = goban.getPierre(x, y);
		
		boolean isCapture = goban.isPierreCapture(goban.getChaine(pierre.getNomChaine()));
		
		assertTrue(isCapture);
	}
	
	@Test
	public void testGoCapture2() {
		int x = 3;
		int y = 3;
		
		goban.initPlateau();
		
		goban.addPierre(new Pierre(Couleur.NOIR, new Coordonnee(x-1, y)));
		goban.addPierre(new Pierre(Couleur.NOIR, new Coordonnee(x-1, y+1)));
		goban.addPierre(new Pierre(Couleur.NOIR, new Coordonnee(x, y-1)));
		goban.addPierre(new Pierre(Couleur.NOIR, new Coordonnee(x, y+2)));
		goban.addPierre(new Pierre(Couleur.NOIR, new Coordonnee(x+1, y-1)));
		goban.addPierre(new Pierre(Couleur.NOIR, new Coordonnee(x+1, y+2)));
		goban.addPierre(new Pierre(Couleur.NOIR, new Coordonnee(x+2, y)));
		goban.addPierre(new Pierre(Couleur.NOIR, new Coordonnee(x+2, y+1)));


		goban.addPierre(new MegaPierre(Couleur.BLANC, new Coordonnee(x, y)));
		
		AbstractPierre pierre = goban.getPierre(x, y);
		
		boolean isCapture = goban.isPierreCapture(pierre);
		
		assertTrue(isCapture);
	}
	
	@Test
	public void testGoCapture3() {
		int x = 3;
		int y = 3;
		
		goban.initPlateau();
		
		goban.addPierre(new Pierre(Couleur.NOIR, new Coordonnee(x-1, y)));
		goban.addPierre(new Pierre(Couleur.NOIR, new Coordonnee(x-1, y+1)));
		goban.addPierre(new Pierre(Couleur.NOIR, new Coordonnee(x-1, y+2)));
		goban.addPierre(new Pierre(Couleur.NOIR, new Coordonnee(x, y-1)));
		goban.addPierre(new Pierre(Couleur.NOIR, new Coordonnee(x, y+3)));
		goban.addPierre(new Pierre(Couleur.NOIR, new Coordonnee(x+1, y-1)));
		goban.addPierre(new Pierre(Couleur.NOIR, new Coordonnee(x+1, y+3)));
		goban.addPierre(new Pierre(Couleur.NOIR, new Coordonnee(x+2, y)));
		goban.addPierre(new Pierre(Couleur.NOIR, new Coordonnee(x+2, y+1)));
		goban.addPierre(new Pierre(Couleur.NOIR, new Coordonnee(x+2, y+2)));


		goban.addPierre(new MegaPierre(Couleur.BLANC, new Coordonnee(x, y)));
		goban.addPierre(new Pierre(Couleur.BLANC, new Coordonnee(x, y+2)));
		goban.addPierre(new Pierre(Couleur.BLANC, new Coordonnee(x+1, y+2)));
		
		AbstractPierre pierre = goban.getPierre(x, y);
		
		boolean isCapture = goban.isPierreCapture(goban.getChaine(pierre.getNomChaine()));
		
		assertTrue(isCapture);
	}
	
}

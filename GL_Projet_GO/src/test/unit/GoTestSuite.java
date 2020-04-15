package test.unit;

import org.junit.runners.Suite;
import org.junit.runner.RunWith;

/**
 * Cette classe est la suite globale des testes unitaires.
 * 
 * Il inclut quatres cas : {@link GoTestPosePierre}, {@link GoTestRetirePierre}, {@link GoTestChaine} et {@link GoTestCapture}.
 * 
 * @author Maxime, Micael et Houssam
 * 
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({GoTestPosePierre.class, GoTestRetirePierre.class, GoTestChaine.class, GoTestCapture.class})
public class GoTestSuite {

}

package traitement.moteurs;

import java.util.ArrayList;

import donnees.AbstractPierre;
import donnees.Cercle;
import donnees.Coordonnee;
import donnees.Couleur;
import donnees.MegaPierre;
import donnees.ParametrePartie;
import donnees.Pierre;
import gui.Go;
import traitement.CalculFactory;
import traitement.GoPierre;
import traitement.Goban;

/**
 * 
 * @author Maxime
 *
 */
public class MoteurPierre {
	
	private int cellule;
	private int ecart_window_horizontal;
	private int taille_goban;
	private int nb_joueurs;
	
	private MoteurJoueur moteur_joueur;
	private Goban goban;
	
	private ArrayList<Cercle> cercle;
	private ArrayList<Cercle> position_jouable;
	private Cercle survole;
	
	private boolean isDidacticiel;
	private boolean isMegaPierre = false;
	private boolean suicide = false;
	
	private AbstractPierre Ko = null;
	private int Ko_compteur = 0;
	private boolean isKo = false;
	
	public MoteurPierre(MoteurJoueur moteur_joueur, Goban goban, int cellule, int taille_goban, int nb_joueur, int nb_ordi, boolean isDidacticiel) {
		this.goban = goban;
		this.moteur_joueur = moteur_joueur;
		this.cellule = cellule;
		this.taille_goban = taille_goban;
		this.isDidacticiel = isDidacticiel;
		
		nb_joueurs = nb_joueur + nb_ordi;
		ecart_window_horizontal = CalculFactory.getCoordEcartHorizontal(taille_goban, cellule, isDidacticiel);
		
		cercle = new ArrayList<Cercle>();
	}
	
	private void incrementeKoCompteur() {
		if(isKo) {
			Ko_compteur++;
			
			if(Ko_compteur == nb_joueurs) {
				setKo(null);
				
				isKo = false;
				Ko_compteur = 0;
			}
		}
	}
	
	public boolean isMegaPierre() {
		return isMegaPierre;
	}
	
	public void setPoseMegaPierre(boolean bool) {
		isMegaPierre = bool;
	}
	
	public void setPositionJouable(ArrayList<Cercle> cercle) {
		position_jouable = cercle;
	}
	
	private void setSuicide(boolean bool) {
		suicide = bool;
	}
	
	private void setKo (AbstractPierre pierre) {
		Ko = pierre;
	}
	
	private AbstractPierre getKo () {
		return Ko;
	}
	
	public ArrayList<Cercle> getCercleList(){
		return cercle;
	}
	
	public Cercle getSurvoleCercle() {
		return survole;
	}
	
	public void survoleZone(int coordX, int coordY) {
		int x = CalculFactory.getCoordTableau(coordY, ParametrePartie.ECART_VERTICAL, cellule);
		int y = CalculFactory.getCoordTableau(coordX, ecart_window_horizontal, cellule);
		
		Couleur couleur;
		
		if((x >= 0) && (x < taille_goban) && (y >= 0) && (y < taille_goban)) {
			couleur = moteur_joueur.currentCouleur();
			
			if(!isMegaPierre && !goban.existPierre(x, y)) {
				survole = new Cercle(new Coordonnee(x, y), couleur, false);
			}
			
			else if(isMegaPierre && moteur_joueur.currentJoueur().hasMegaPierre()) {
				if(x == taille_goban - 1) {
					x--;
				}
				if(y == taille_goban - 1) {
					y--;
				}
				
				if(!goban.existPierre(x, y) || !goban.existPierre(x+1, y) || !goban.existPierre(x, y+1) || !goban.existPierre(x+1, y+1)) {
					if(canDestruct(x, y)) {
						survole = new Cercle(new Coordonnee(x, y), couleur, true);
					}
					
					else {
						survole = null;
					}
				}
				
				else {
					survole = null;
				}
			}
			
			else {
				survole = null;
			}
			
			if(isDidacticiel) {
				boolean exist = false;
				
				for(Cercle pos : position_jouable) {
					if((pos.getX() == x) && (pos.getY() == y)) {
						exist = true;
					}
				}
				
				if(!exist) {
					survole = null;
				}
			}
		}
		
		else {
			survole = null;
		}
	}
	
	public void clicEvent(int coordX, int coordY) {
		long startTime = System.currentTimeMillis();

		int x = CalculFactory.getCoordTableau(coordY, ParametrePartie.ECART_VERTICAL, cellule);
		int y = CalculFactory.getCoordTableau(coordX, ecart_window_horizontal, cellule);
		
		boolean canPose = true;
		
		if((x >= 0) && (x < taille_goban) && (y >= 0) && (y < taille_goban)) {
			if(!isMegaPierre && !goban.existPierre(x, y)) {
				AbstractPierre pierre = new Pierre(moteur_joueur.currentCouleur(), x, y);
				
				if(isDidacticiel) {
					boolean exist = false;
					
					for(Cercle pos : position_jouable) {
						if((pos.getX() == x) && (pos.getY() == y)) {
							exist = true;
						}
					}
					
					if(!exist) {
						canPose = false;
					}
				}
				
				if(canPose) {
					addPierre(pierre);
					moteur_joueur.currentJoueur().addPierre(pierre);
					
					if(!suicide) {
						moteur_joueur.changeJoueur();
						incrementeKoCompteur();
					}
				}
			}
			
			else if(isMegaPierre) {
				if(x == taille_goban - 1) {
					x--;
				}
				
				if(y == taille_goban - 1) {
					y--;
				}
				
				if(!goban.existPierre(x, y) || !goban.existPierre(x+1, y) || !goban.existPierre(x, y+1) || !goban.existPierre(x+1, y+1)) {
					AbstractPierre pierre = new MegaPierre(moteur_joueur.currentCouleur(), x, y);
					
					if(isDidacticiel) {
						boolean exist = false;
						
						for(Cercle pos : position_jouable) {
							if((pos.getX() == x) && (pos.getY() == y)) {
								exist = true;
							}
						}
						
						if(!exist) {
							canPose = false;
						}
					}
					
					if(canPose && canDestruct(x, y)) {
						destruct(x, y);
						
						addPierre(pierre);
						moteur_joueur.currentJoueur().addPierre(pierre);

						if(!suicide) {
							moteur_joueur.currentJoueur().playMegaPierre();
							moteur_joueur.changeJoueur();
							incrementeKoCompteur();
						}						
					}
				}
			}
		}
		
		setPoseMegaPierre(false);
		setSuicide(false);
		
		Go.logger.info("Temps pour jouer un coup: " + (System.currentTimeMillis() - startTime));
	}
	
	public void addPierre(AbstractPierre pierre) {
		goban.addPierre(pierre);
		Go.logger.info("Pierre créée aux coordonnées : (" + pierre.getX() + ", " + pierre.getY() + ")");
		Go.logger.info("Ajout d'une Pierre / MegaPierre de coordonnées (" + pierre.getX() + ", " + pierre.getY() + ") au joueur " + moteur_joueur.currentJoueur().getCouleur());
		
		Coordonnee coordPierre = new Coordonnee(pierre.getX(), pierre.getY());
		
		cercle.add(new Cercle(coordPierre, pierre.getCouleur(), isMegaPierre));
		Go.logger.info("Cercle créé aux coordonnées : (" + pierre.getX() + ", " + pierre.getY() + ")");
		
		ArrayList<AbstractPierre> voisin = GoPierre.voisins(goban.getPierre(pierre.getX(), pierre.getY()), goban.getPlateau(), taille_goban);
		
		for(AbstractPierre pierreVoisin : voisin) {
			if(pierreVoisin.hasChaine() && !pierreVoisin.getCouleur().equals(pierre.getCouleur())) {
				if(goban.isPierreCapture(goban.getChaine(pierreVoisin.getNomChaine()))) {
					removePierre(goban.getChaine(pierreVoisin.getNomChaine()));
					
					moteur_joueur.currentJoueur().addScore(goban.getScoreCapture());
					Go.logger.info("Ajout de " + goban.getScoreCapture() + " points au joueur de couleur " + moteur_joueur.currentJoueur().getCouleur());
				}
			}
			
			else if(goban.isPierreCapture(pierreVoisin) && !pierreVoisin.getCouleur().equals(pierre.getCouleur()) && !pierreVoisin.equals(getKo())) {
				removePierre(pierreVoisin);
				setKo(pierre);
				isKo = true;
				
				moteur_joueur.currentJoueur().addScore(goban.getScoreCapture());
				Go.logger.info("Ajout de " + goban.getScoreCapture() + " points au joueur de couleur " + moteur_joueur.currentJoueur().getCouleur());
			}
				
		}
		
		if(pierre.hasChaine() && goban.isSuicide(goban.getChaine(pierre.getNomChaine()))) {
			Go.logger.info("Suicide Chaine : Suppression de la pierre de coordonnées (" + pierre.getX() + ", " + pierre.getY() + ")");
			removePierre(pierre);
			setSuicide(true);
		}
		
		else if(goban.isSuicide(pierre)) {
			Go.logger.info("Suicide : Suppression de la pierre de coordonnées (" + pierre.getX() + ", " + pierre.getY() + ")");
			removePierre(pierre);
			setSuicide(true);
		}
	}
	
	private void removePierre(AbstractPierre pierre) {
		if(goban.existPierre(pierre.getX(), pierre.getY())) {
			Go.logger.info("Pierre supprimée aux coordonnées : (" + pierre.getX() + ", " + pierre.getY() + ")");
			goban.removePierre(pierre);

			Go.logger.info("Pierre de coordonnées (" + pierre.getX() + ", " + pierre.getY() + ") retirée au joueur " + moteur_joueur.currentJoueur().getCouleur());
			moteur_joueur.currentJoueur().removePierre(pierre);
			
			Cercle c = null;
			
			for(Cercle coordCercle : cercle) {
				if((pierre.getX() == coordCercle.getX()) && (pierre.getY() == coordCercle.getY())) {
					c = coordCercle;
				}
			}

			Go.logger.info("Cercle supprimé aux coordonnées : (" + pierre.getX() + ", " + pierre.getY() + ")");
			cercle.remove(c);
		}
	}
	
	private void removePierre(ArrayList<AbstractPierre> chaine) {
		for(AbstractPierre pierre : chaine) {
			removePierre(pierre);
		}
	}
	
	private boolean canDestruct(int x, int y) {
		boolean exist_voisin = false;
		int compteur = 0;
		
		if(goban.existPierre(x, y) && goban.existPierre(x+1, y) && goban.existPierre(x, y+1) && goban.existPierre(x+1, y+1)) {
			return false;
		}
		
		else {
			if(goban.existPierre(x, y)) {
				if(goban.getPierre(x, y).isMegaPierre() || goban.getPierre(x, y).getCouleur() == moteur_joueur.currentJoueur().getCouleur()) {
					return false;
				}
				
				if(goban.getPierre(x, y).getLiberte() > 0) {
					compteur++;
				}
				
				exist_voisin = true;
			}

			
			if(goban.existPierre(x+1, y)) {
				if(goban.getPierre(x+1, y).isMegaPierre() || goban.getPierre(x+1, y).getCouleur() == moteur_joueur.currentJoueur().getCouleur()) {
					return false;
				}
				
				if(goban.getPierre(x+1, y).getLiberte() > 0) {
					compteur++;
				}
				
				exist_voisin = true;
			}

			
			if(goban.existPierre(x, y+1)) {
				if(goban.getPierre(x, y+1).isMegaPierre() || goban.getPierre(x, y+1).getCouleur() == moteur_joueur.currentJoueur().getCouleur()) {
					return false;
				}
				
				if(goban.getPierre(x, y+1).getLiberte() > 0) {
					compteur++;
				}
				
				exist_voisin = true;
			}

			
			if(goban.existPierre(x+1, y+1)) {
				if(goban.getPierre(x+1, y+1).isMegaPierre() || goban.getPierre(x+1, y+1).getCouleur() == moteur_joueur.currentJoueur().getCouleur()) {
					return false;
				}
				
				if(goban.getPierre(x+1, y+1).getLiberte() > 0) {
					compteur++;
				}
				
				exist_voisin = true;
			}
		}
		
		if(!exist_voisin || (compteur > 0)) {
			return true;
		}
		
		return false;
	}
	
	private void destruct(int x, int y) {
		Go.logger.debug("Destruction en cours !");
		
		for(int i = x ; i <= x+1 ; i++) {
			for(int j = y ; j <= y+1 ; j++) {
				if(goban.existPierre(i, j)) {
					goban.removePierreChaine(goban.getPierre(i, j));
					removePierre(goban.getPierre(i, j));
				}
			}
		}
		
		Go.logger.debug("Destruction terminée !");
	}
}

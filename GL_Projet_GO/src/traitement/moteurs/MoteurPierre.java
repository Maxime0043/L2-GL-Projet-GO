package traitement.moteurs;

import java.util.ArrayList;

import donnees.AbstractPierre;
import donnees.Cercle;
import donnees.Coordonnee;
import donnees.Couleur;
import donnees.MegaPierre;
import donnees.Pierre;
import gui.Go;
import traitement.GoPierre;
import traitement.Goban;

/**
 * Cette classe permet de gérer les actions liées aux pierres dans la partie.
 * 
 * @author Maxime, Micael et Houssam
 *
 */
public class MoteurPierre {
	
	private int taille_goban;
	private int nb_joueurs;
	
	private MoteurJoueur moteur_joueur;
	private Goban goban;
	
	private ArrayList<Cercle> cercle;
	private ArrayList<Cercle> position_jouable;
	private ArrayList<AbstractPierre> dernieres_pierres_mortes;
	private Coordonnee survole;
	private Coordonnee croix;
	
	private boolean isDidacticiel;
	private boolean is_tour_ordi = false;
	private boolean isMegaPierre = false;
	private boolean suicide = false;
	
	private AbstractPierre Ko = null;
	private int Ko_compteur = 0;
	private boolean isKo = false;
	private int nb_pierres_detruites;
	
	public MoteurPierre(MoteurJoueur moteur_joueur, Goban goban, int taille_goban, int nb_joueur, int nb_ordi, boolean isDidacticiel) {
		this.goban = goban;
		this.moteur_joueur = moteur_joueur;
		this.taille_goban = taille_goban;
		this.isDidacticiel = isDidacticiel;
		
		nb_joueurs = nb_joueur + nb_ordi;
		
		cercle = new ArrayList<Cercle>();
		dernieres_pierres_mortes = new ArrayList<AbstractPierre>();
	}
	
	public void setTourOrdi(boolean bool) {
		is_tour_ordi = bool;
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
	
	public void initDernieresPierresMortes() {
		dernieres_pierres_mortes.clear();
	}
	
	public ArrayList<AbstractPierre> getDernieresPierresMortes(){
		return dernieres_pierres_mortes;
	}
	
	public boolean isSuicide() {
		return suicide;
	}
	
	public void setSuicide(boolean bool) {
		suicide = bool;
	}
	
	/**
	 * Permet de gérer les situations de ko.
	 */
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
	
	private void setKo (AbstractPierre pierre) {
		Ko = pierre;
	}
	
	private AbstractPierre getKo () {
		return Ko;
	}
	
	public int getNbPierresDetruites() {
		return nb_pierres_detruites;
	}
	
	public ArrayList<Cercle> getCercleList(){
		return cercle;
	}
	
	public Coordonnee getSurvoleCercle() {
		return survole;
	}
	
	public Coordonnee getSurvoleCroix() {
		return croix;
	}
	
	/**
	 * Permet de vérifier avec des coordonnées si une intersection est jouable ou non.
	 * 
	 * @param x Désigne la ligne du plateau que l'on va analyser.
	 * @param y Désigne la colonne du plateau que l'on va analyser.
	 */
	public void survoleZone(int x, int y) {
		if((x >= 0) && (x < taille_goban) && (y >= 0) && (y < taille_goban)) {
			if(!isMegaPierre && !goban.existPierre(x, y)) {
				survole = new Coordonnee(x, y);
				croix = null;
			}
			
			else if(isMegaPierre && moteur_joueur.currentJoueur().hasMegaPierre()) {
				if(x == taille_goban - 1) {
					x--;
				}
				if(y == taille_goban - 1) {
					y--;
				}
				
				if(canDestruct(x, y)) {
					survole = new Coordonnee(x, y);
					croix = null;
				}
				
				else {
					survole = null;
					croix = new Coordonnee(x, y);
				}
			}
			
			else {
				survole = null;
				croix = new Coordonnee(x, y);
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
			croix = null;
		}
	}
	
	/**
	 * Permet d'ajouter une pierre ou une méga-pierre sur le plateau à partir de coordonnées.
	 * 
	 * @param x Désigne la ligne du plateau que l'on va ajouter.
	 * @param y Désigne la colonne du plateau que l'on va ajouter.
	 */
	public void clicEvent(int x, int y) {
		long startTime = System.currentTimeMillis();

		if((x >= 0) && (x < taille_goban) && (y >= 0) && (y < taille_goban)) {
			posePierre(x, y, moteur_joueur.currentCouleur());
		}
		
		if(!isDidacticiel) {
			setPoseMegaPierre(false);
		}
		
		Go.logger.info("Temps pour jouer un coup: " + (System.currentTimeMillis() - startTime));
	}
	
	public void posePierre(int x, int y, Couleur couleur) {
		boolean canPose = true;
		
		if(is_tour_ordi) {
			dernieres_pierres_mortes.clear();
		}
		
		if(!isMegaPierre && !goban.existPierre(x, y)) {
			AbstractPierre pierre = new Pierre(couleur, x, y);
			
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
				
				if(!suicide) {
					if(!is_tour_ordi) {
						moteur_joueur.changeJoueur();
					}
					
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
			
			AbstractPierre pierre = new MegaPierre(couleur, x, y);
			
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

				if(!suicide) {
					if(!isDidacticiel) {
						moteur_joueur.currentJoueur().playMegaPierre();
					}
					
					if(!is_tour_ordi) {
						moteur_joueur.changeJoueur();
					}
					
					incrementeKoCompteur();
				}						
			}
		}
		
		setSuicide(false);
	}
	
	/**
	 * Permet d'ajouter la pierre ou méga-pierre au plateau et de vérifier si les pierres autours ont été capturées.
	 * 
	 * @param pierre Définit la pierre ou méga-pierre que l'on ajoute au plateau et qui aura un effet sur les pierres autour.
	 */
	public void addPierre(AbstractPierre pierre) {
		goban.addPierre(pierre);
		Go.logger.info("Pierre créée aux coordonnées : (" + pierre.getX() + ", " + pierre.getY() + ")");
		moteur_joueur.currentJoueur().addPierre(pierre);
		Go.logger.info("Ajout d'une Pierre / MegaPierre de coordonnées (" + pierre.getX() + ", " + pierre.getY() + ") au joueur " + moteur_joueur.currentJoueur().getCouleur());
		
		Coordonnee coordPierre = new Coordonnee(pierre.getX(), pierre.getY());
		
		if(!is_tour_ordi) {
			cercle.add(new Cercle(coordPierre, pierre.getCouleur(), isMegaPierre));
			Go.logger.info("Cercle créé aux coordonnées : (" + pierre.getX() + ", " + pierre.getY() + ")");
		}
		
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
	
	/**
	 * Permet de retirer une pierre ou méga-pierre du plateau.
	 * 
	 * @param pierre Désigne la pierre ou méga-pierre que l'on veut supprimer.
	 */
	public void removePierre(AbstractPierre pierre) {
		if(goban.existPierre(pierre.getX(), pierre.getY())) {
			if(is_tour_ordi) {
				dernieres_pierres_mortes.add(pierre);
			}

			Go.logger.info("Pierre supprimée aux coordonnées : (" + pierre.getX() + ", " + pierre.getY() + ")");
			goban.removePierre(pierre);

			Go.logger.info("Pierre de coordonnées (" + pierre.getX() + ", " + pierre.getY() + ") retirée au joueur " + moteur_joueur.getJoueur(pierre.getCouleur()).getCouleur());
			moteur_joueur.getJoueur(pierre.getCouleur()).removePierre(pierre);
			
			if(!is_tour_ordi) {
				Cercle c = null;
				
				for(Cercle coordCercle : cercle) {
					if((pierre.getX() == coordCercle.getX()) && (pierre.getY() == coordCercle.getY())) {
						c = coordCercle;
					}
				}
				
				if(c != null) {
					Go.logger.info("Cercle supprimé aux coordonnées : (" + pierre.getX() + ", " + pierre.getY() + ")");
					cercle.remove(c);
				} 
			}
		}
	}
	
	/**
	 * Permet de retirer une chaine du plateau.
	 * 
	 * @param chaine Désigne la chaine que l'on veut supprimer.
	 */
	public void removePierre(ArrayList<AbstractPierre> chaine) {
		ArrayList<AbstractPierre> c = new ArrayList<AbstractPierre>();
		
		c.addAll(chaine);
		
		for(AbstractPierre pierre : c) {
			removePierre(pierre);
		}
	}
	
	/**
	 * Permet de vérifier avec des coordonnées si une méga-pierre peut détruire les pierres ennemies autour si elles existent.
	 * 
	 * @param x Désigne la ligne du plateau que l'on va analyser.
	 * @param y Désigne la colonne du plateau que l'on va analyser.
	 * @return Indique si la méga-pierre pourra être posée ou non.
	 */
	public boolean canDestruct(int x, int y) {
		boolean exist_voisin = false;
		nb_pierres_detruites = 0;
		
		if(goban.existPierre(x, y) && goban.existPierre(x+1, y) && goban.existPierre(x, y+1) && goban.existPierre(x+1, y+1)) {
			return false;
		}
		
		else if(goban.isSuicide(x, y, moteur_joueur.currentCouleur(), true)) {
			return false;
		}
		
		else {
			if(goban.existPierre(x, y)) {
				if(goban.getPierre(x, y).isMegaPierre() || goban.getPierre(x, y).getCouleur() == moteur_joueur.currentJoueur().getCouleur()) {
					return false;
				}
				
				if(goban.getPierre(x, y).getLiberte() > 0) {
					nb_pierres_detruites++;
				}
				
				exist_voisin = true;
			}

			
			if(goban.existPierre(x+1, y)) {
				if(goban.getPierre(x+1, y).isMegaPierre() || goban.getPierre(x+1, y).getCouleur() == moteur_joueur.currentJoueur().getCouleur()) {
					return false;
				}
				
				if(goban.getPierre(x+1, y).getLiberte() > 0) {
					nb_pierres_detruites++;
				}
				
				exist_voisin = true;
			}

			
			if(goban.existPierre(x, y+1)) {
				if(goban.getPierre(x, y+1).isMegaPierre() || goban.getPierre(x, y+1).getCouleur() == moteur_joueur.currentJoueur().getCouleur()) {
					return false;
				}
				
				if(goban.getPierre(x, y+1).getLiberte() > 0) {
					nb_pierres_detruites++;
				}
				
				exist_voisin = true;
			}

			
			if(goban.existPierre(x+1, y+1)) {
				if(goban.getPierre(x+1, y+1).isMegaPierre() || goban.getPierre(x+1, y+1).getCouleur() == moteur_joueur.currentJoueur().getCouleur()) {
					return false;
				}
				
				if(goban.getPierre(x+1, y+1).getLiberte() > 0) {
					nb_pierres_detruites++;
				}
				
				exist_voisin = true;
			}
		}
		
		if(!exist_voisin || (nb_pierres_detruites > 0)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Permet de supprimer les pierres qui vont être remplacées par la méga-pierre du joueur courant.
	 * 
	 * @param x Désigne la ligne du plateau où l'on va détruire les pierres.
	 * @param y Désigne la colonne du plateau où l'on va détruire les pierres.
	 */
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

package traitement.moteurs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Stack;

import donnees.AbstractPierre;
import donnees.Chaine;
import donnees.Coordonnee;
import donnees.Couleur;
import donnees.MegaPierre;
import donnees.ParametrePartie;
import donnees.Pierre;
import traitement.GoPierre;
import traitement.Goban;

/**
 * Cette classe gère les actions des ordinateurs dans la partie.
 * 
 * @author Maxime, Micael et Houssam
 *
 */
public class MoteurOrdi {

	private Moteur moteur;
	private MoteurJoueur moteur_joueur;
	private MoteurPierre moteur_pierre;
	private Goban goban;
	
	private int taille_goban;
	private int difficulte;
	private int tour;
	private Couleur couleur_ordi;
	
	private Stack<ArrayList<AbstractPierre>> stack_pierres_mortes;
	private Stack<int[]> stack_scores;
	private AbstractPierre cherche_coup;
	private int meilleur_score;
	
	private ArrayList<Coordonnee> territoire_noir;
	private ArrayList<Coordonnee> territoire_blanc;
	private ArrayList<Coordonnee> territoire_rouge;
	
	public MoteurOrdi(Moteur moteur, MoteurJoueur moteur_joueur, MoteurPierre moteur_pierre, Goban goban, int taille_goban, int difficulte) {
		this.moteur = moteur;
		this.moteur_joueur = moteur_joueur;
		this.moteur_pierre = moteur_pierre;
		this.goban = goban;
		this.taille_goban = taille_goban;
		this.difficulte = difficulte;
		
		stack_pierres_mortes = new Stack<ArrayList<AbstractPierre>>();
		stack_scores = new Stack<int[]>();
		
		territoire_noir = new ArrayList<Coordonnee>();
		territoire_blanc = new ArrayList<Coordonnee>();
		
		if(moteur_joueur.getJoueurs().length == 3) {
			territoire_rouge = new ArrayList<Coordonnee>();
		}
	}
	
	public void jouer() {		
		moteur_pierre.setTourOrdi(true);
		initTour();
		
		if(canPlay()) {
			if(isDebut()) {
				recherche_coup_debut();
			}

			else {
				cherche_coup = enDanger();

				if(cherche_coup != null) {
					System.out.println("Pierre/Chaine en danger !");
				}

				else{
					recherche_coup(null);
				}
			}
			
			moteur_pierre.setTourOrdi(false);
			goban.updateLibertePlateau();
			
			if(cherche_coup.isMegaPierre()) {
				moteur_pierre.setPoseMegaPierre(true);
			}
			
			moteur_pierre.posePierre(cherche_coup.getX(), cherche_coup.getY(), moteur_joueur.currentCouleur());
			moteur_pierre.setPoseMegaPierre(false);
			
			System.out.println("Pierre Ordi ajouté en " + cherche_coup.getX() + " " + cherche_coup.getY());
		}
		
		else {
			moteur.passer();
		}
		
		for(int i = 0 ; i < taille_goban ; i++) {
			for(int j = 0 ; j < taille_goban ; j++) {
				if(goban.existPierre(i, j)) {
					System.out.print("\t" + goban.getPierre(i, j).getNomChaine());
				}
				
				else {
					System.out.print("\tX");
				}
			}
			System.out.println();
		}
		
		for(int i = 0; i < taille_goban; i++) {
			for(int j = 0; j < taille_goban; j++) {
				if(goban.existPierre(i, j)) {
					System.out.print("\t" + goban.getPierre(i, j).getCouleur());
				}

				else {
					System.out.print("\t-");
				}
			}
			System.out.println();
		}
		System.out.println();
	}
	
	private void initTour() {
		tour = 0;
		meilleur_score = Integer.MIN_VALUE;
		cherche_coup = null;
		couleur_ordi = moteur_joueur.currentCouleur();
		
		stack_pierres_mortes.clear();
		stack_scores.clear();
		
		territoire_noir.clear();
		territoire_blanc.clear();
		
		if(moteur_joueur.getJoueurs().length == 3) {
			territoire_rouge.clear();
		}
		
		moteur_pierre.initDernieresPierresMortes();
	}
	
	private boolean isDebut() {
		int compteur = 0;
		
		for(int i = 0 ; i < taille_goban ; i++) {
			for(int j = 0 ; j < taille_goban ; j++) {
				if(goban.existPierre(i, j)) {
					compteur++;
				}
			}
		}
		
		if(compteur < moteur_joueur.getJoueurs().length) {
			return true;
		}
		
		return false;
	}
	
	private boolean canPlay() {
		ArrayList<Coordonnee> intersections_vides = new ArrayList<Coordonnee>();
		
		for(int i = 0 ; i < taille_goban ; i++) {
			for(int j = 0 ; j < taille_goban ; j++) {
				if(goban.existPierre(i, j)) {
					intersections_vides.add(new Coordonnee(i, j));
				}
			}
		}
		
		if(intersections_vides.size() < 5) {
			boolean result = false;
			
			for(Coordonnee c : intersections_vides) {
				AbstractPierre pierre = new Pierre(moteur_joueur.currentCouleur(), c);
				
				if(!goban.canBeCaptured(pierre) && !goban.isSuicide(pierre)) {
					return true;
				}
			}
			
			return result;
		}
		
		return true;
	}
	
	private void recherche_coup_debut() {
		ArrayList<Coordonnee> list = new ArrayList<Coordonnee>();

		int milieu = taille_goban / 2;
		int hasMilieu = 0;
		
		if(taille_goban == ParametrePartie.TAILLE_GOBAN[1]) {
			hasMilieu = 3;
		}
		
		for(Coordonnee c : goban.getHoshis()) {
			if(c.getX() < milieu && c.getY() < milieu) {
				if(!ennemi_present_autour(0, milieu - hasMilieu, 0, milieu - hasMilieu)) {
					list.add(c);
				}
			}
			
			else if(c.getX() < milieu && c.getY() == milieu) {
				if(!ennemi_present_autour(0, milieu - hasMilieu, milieu - hasMilieu, milieu + hasMilieu)) {
					list.add(c);
				}
			}
			
			else if(c.getX() < milieu && c.getY() > milieu) {
				if(!ennemi_present_autour(0, milieu - hasMilieu, milieu + hasMilieu, taille_goban)) {
					list.add(c);
				}
			}
			
			else if(c.getX() == milieu && c.getY() < milieu) {
				if(!ennemi_present_autour(milieu - hasMilieu, milieu + hasMilieu, 0, milieu - hasMilieu)) {
					list.add(c);
				}
			}
			
			else if(c.getX() == milieu && c.getY() == milieu) {
				if(!ennemi_present_autour(milieu - hasMilieu, milieu + hasMilieu, milieu - hasMilieu, milieu + hasMilieu)) {
					list.add(c);
				}
			}
			
			else if(c.getX() == milieu && c.getY() > milieu) {
				if(!ennemi_present_autour(milieu - hasMilieu, milieu + hasMilieu, milieu + hasMilieu, taille_goban)) {
					list.add(c);
				}
			}
			
			else if(c.getX() > milieu && c.getY() < milieu) {
				if(!ennemi_present_autour(milieu + hasMilieu, taille_goban, 0, milieu - hasMilieu)) {
					list.add(c);
				}
			}
			
			else if(c.getX() > milieu && c.getY() == milieu) {
				if(!ennemi_present_autour(milieu + hasMilieu, taille_goban, milieu - hasMilieu, milieu + hasMilieu)) {
					list.add(c);
				}
			}
			
			else if(c.getX() > milieu && c.getY() > milieu) {
				if(!ennemi_present_autour(milieu + hasMilieu, taille_goban, milieu + hasMilieu, taille_goban)) {
					list.add(c);
				}
			}
		}
		
		Random rand = new Random();
		int index = rand.nextInt(list.size());
		
		cherche_coup = new Pierre(moteur_joueur.currentCouleur(), list.get(index));
	}
	
	private boolean ennemi_present_autour(int debutX, int finX, int debutY, int finY) {
		for(int i = debutX ; i < finX ; i++) {
			for(int j = debutY ; j < finY ; j++) {
				if(goban.existPierre(i, j)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	private void recherche_coup(AbstractPierre premiere_pierre) {
		tour++;
		
		for(int i = 0 ; i < taille_goban ; i++) {
			for(int j = 0 ; j < taille_goban ; j++) {
				if(!goban.existPierre(i, j) && tour <= difficulte) {
					HashMap<Integer, Chaine> save_chaines = new HashMap<Integer, Chaine>();
					boolean has_pierre_mortes = false;
					int nb_liste_pierres_mortes = stack_pierres_mortes.size();
					
					sauvegarde_scores();
					moteur_pierre.setSuicide(false);
					moteur_pierre.posePierre(i, j, moteur_joueur.currentCouleur());
					save_chaines = sauvegarde_chaines();
					
					if(isCoupValide(i, j)) {
						AbstractPierre pierre = goban.getPierre(i, j);

						sauvegarde_pierres_mortes();

						if(nb_liste_pierres_mortes != stack_pierres_mortes.size()) {
							has_pierre_mortes = true;
						}

						if(tour == 1) {
							premiere_pierre = pierre;
						}

						if(tour == difficulte) {
							compare_score(premiere_pierre);
						}

						if(tour < difficulte) {
							moteur_joueur.changeJoueur();
							recherche_coup(premiere_pierre);
						}

						moteur_pierre.removePierre(pierre);
						goban.updateLibertePlateau();
						restore_scores();
						
						if(has_pierre_mortes) {
							restore_pierres_mortes();
						}
					}
					
					if(goban.existPierre(i, j)) {
						moteur_pierre.removePierre(goban.getPierre(i, j));
					}
					
					restore_chaines(save_chaines);
					goban.updateChaines();
				}
			}
		}
		
		if(tour > 1) {
			moteur_joueur.joueurPrecedent();
		}
		
		tour--;
	}
	
	private boolean isCoupValide(int x, int y) {
		if(!goban.existPierre(x, y) || moteur_pierre.isSuicide()) {
			return false;
		}
		
		AbstractPierre pierre = goban.getPierre(x, y);
		
		if(pierre.hasChaine()) {
			return !goban.canBeCaptured(goban.getChaine(pierre.getNomChaine()));
		}
		
		else {
			return !goban.canBeCaptured(pierre);
		}
	}
	
	private void compare_score(AbstractPierre pierre) {
		int current_score = (int)moteur_joueur.getJoueur(couleur_ordi).getScore() + estimeScoreTerritoire();
		
		if(meilleur_score < current_score) {
			meilleur_score = current_score;
			cherche_coup = pierre;
		}
	}
	
	private int estimeScoreTerritoire() {
		ArrayList<AbstractPierre> pierres = new ArrayList<AbstractPierre>();
		ArrayList<Coordonnee> territoire;
		int ecart_bord = 3;
		int max_distance_bord = 2;
		
		for(int i = 0 ; i < taille_goban ; i++) {
			for(int j = 0 ; j < taille_goban ; j++) {
				if(goban.existPierre(i, j)) {
					if(!pierres.contains(goban.getPierre(i, j))) {
						pierres.add(goban.getPierre(i, j));
					}
				}
			}
		}
		
		territoire_noir.clear();
		territoire_blanc.clear();
		
		if(moteur_joueur.getJoueurs().length == 3) {
			territoire_rouge.clear();
		}
		
		for(AbstractPierre pierre : pierres) {
			if(pierre.getCouleur() == Couleur.NOIR) {
				territoire = territoire_noir;
			}
			
			else if(pierre.getCouleur() == Couleur.BLANC) {
				territoire = territoire_blanc;
			}
			
			else {
				territoire = territoire_rouge;
			}
			
			int ligne, colonne;
			int max_taille_X = 1, max_taille_Y = 1;
			
			if(pierre.getX() <= ecart_bord) {
				ligne = pierre.getX() - max_distance_bord;

				if(ligne < 0) {
					ligne = 0;
				}
				
				if(pierre.getX() <= 1) {
					max_taille_Y++;
				}

				if(pierre.getY() <= 1 || pierre.getY() >= taille_goban - 2) {
					max_taille_X++;
				}
				
				if(pierre.getY() >= taille_goban - 1 - ecart_bord) {
					colonne = pierre.getY() + max_distance_bord;
					
					if(colonne > taille_goban - 1) {
						colonne = taille_goban - 1;
					}
					
					for(int i = ligne ; i <= pierre.getX() + max_taille_X ; i++) {
						for(int j = colonne ; j >= pierre.getY() - max_taille_Y ; j--) {
							if(!goban.existPierre(i, j)) {
								territoire.add(new Coordonnee(i, j));
							}
						}
					}
				}
				
				else {
					if(pierre.getY() <= ecart_bord) {
						colonne = pierre.getY() - max_distance_bord;
					}
					
					else {
						colonne = pierre.getY() - max_taille_Y;
					}
					
					if(colonne < 0) {
						colonne = 0;
					}
					
					for(int i = ligne ; i <= pierre.getX() + max_taille_X ; i++) {
						for(int j = colonne ; j <= pierre.getY() + max_taille_Y ; j++) {
							if(!goban.existPierre(i, j)) {
								territoire.add(new Coordonnee(i, j));
							}
						}
					}
				}
			}
			
			else if(pierre.getX() >= taille_goban - 1 - ecart_bord) {
				ligne = pierre.getX() + max_distance_bord;

				if(ligne > taille_goban - 1) {
					ligne = taille_goban - 1;
				}
				
				if(pierre.getX() >= taille_goban - 2) {
					max_taille_Y++;
				}

				if(pierre.getY() <= 1 || pierre.getY() >= taille_goban - 2) {
					max_taille_X++;
				}
				
				if(pierre.getY() >= taille_goban - 1 - ecart_bord) {
					colonne = pierre.getY() + max_distance_bord;
					
					if(colonne > taille_goban - 1) {
						colonne = taille_goban - 1;
					}
					
					for(int i = ligne ; i >= pierre.getX() - max_taille_X ; i--) {
						for(int j = colonne ; j >= pierre.getY() - max_taille_Y ; j--) {
							if(!goban.existPierre(i, j)) {
								territoire.add(new Coordonnee(i, j));
							}
						}
					}
				}
				
				else {
					if(pierre.getY() <= ecart_bord) {
						colonne = pierre.getY() - max_distance_bord;
					}
					
					else {
						colonne = pierre.getY() - max_taille_Y;
					}
					
					if(colonne < 0) {
						colonne = 0;
					}
					
					for(int i = ligne ; i >= pierre.getX() - max_taille_X ; i--) {
						for(int j = colonne ; j <= pierre.getY() + max_taille_Y ; j++) {
							if(!goban.existPierre(i, j)) {
								territoire.add(new Coordonnee(i, j));
							}
						}
					}
				}
			}
			
			else if(pierre.getY() <= ecart_bord) {
				colonne = pierre.getY() - max_distance_bord;
				
				if(colonne < 0) {
					colonne = 0;
				}
				
				if(pierre.getY() <= 1) {
					max_taille_X++;
				}

				ligne = pierre.getX() - max_taille_X;
				
				for(int i = ligne ; i <= pierre.getX() + max_taille_X ; i++) {
					for(int j = colonne ; j <= pierre.getY() + max_taille_Y ; j++) {
						if(!goban.existPierre(i, j)) {
							territoire.add(new Coordonnee(i, j));
						}
					}
				}
			}
			
			else if(pierre.getY() >= taille_goban - 1 - ecart_bord) {
				colonne = pierre.getY() + max_distance_bord;
				
				if(colonne > taille_goban - 1) {
					colonne = taille_goban - 1;
				}
				
				if(pierre.getY() >= taille_goban - 1 - ecart_bord) {
					max_taille_X++;
				}

				ligne = pierre.getX() - max_taille_X;
				
				for(int i = ligne ; i <= pierre.getX() + max_taille_X ; i++) {
					for(int j = colonne ; j >= pierre.getY() - max_taille_Y ; j--) {
						if(!goban.existPierre(i, j)) {
							territoire.add(new Coordonnee(i, j));
						}
					}
				}
			}
			
			else {
				max_taille_X++;
				max_taille_Y++;
				ligne = pierre.getX() - max_taille_X;
				colonne = pierre.getY() - max_taille_Y;
				
				for(int i = ligne ; i <= pierre.getX() + max_taille_X ; i++) {
					for(int j = colonne ; j <= pierre.getY() + max_taille_Y ; j++) {
						if(!goban.existPierre(i, j)) {
							territoire.add(new Coordonnee(i, j));
						}
					}
				}
			}
			
			removeSameTerritoire();
		}
		
		if(moteur_joueur.getJoueurs().length < 3) {
			if(couleur_ordi == Couleur.NOIR) {
				return territoire_noir.size() - territoire_blanc.size();
			}
			
			else if(couleur_ordi == Couleur.BLANC) {
				return territoire_blanc.size() - territoire_noir.size();
			}
		}
		
		else {
			if(couleur_ordi == Couleur.NOIR) {
				return territoire_noir.size() - territoire_blanc.size() - territoire_rouge.size();
			}
			
			else if(couleur_ordi == Couleur.BLANC) {
				return territoire_blanc.size() - territoire_noir.size() - territoire_rouge.size();
			}
			
			else {
				return territoire_rouge.size() - territoire_noir.size() - territoire_blanc.size();
			}
		}
		
		return 0;
	}
	
	private AbstractPierre enDanger() {
		int priorite = 3;
		int max_taille_chaine = 0;

		ArrayList<AbstractPierre> copy_pierres = new ArrayList<AbstractPierre>();
		ArrayList<AbstractPierre> pierres_parcourus = new ArrayList<AbstractPierre>();
		ArrayList<AbstractPierre> pierres_mortes = new ArrayList<AbstractPierre>();
		ArrayList<Coordonnee> intersection_libre;
		AbstractPierre pierre_final = null;
		Coordonnee coord_pierre = null;
		
		copy_pierres.addAll(moteur_joueur.getJoueur(moteur_joueur.currentCouleur()).getListePierre());
		
		for(AbstractPierre pierre : copy_pierres) {
			AbstractPierre tmp;
			
			if(pierres_parcourus.contains(pierre)) {
				continue;
			}
			
			if(!pierre.hasChaine() && goban.canBeCaptured(pierre) && priorite == 3) {
				intersection_libre = GoPierre.intersectionVide(pierre, goban.getPlateau(), taille_goban);
				
				if(!intersection_libre.isEmpty()) {
					coord_pierre = intersection_libre.get(0);
					sauvegarde_scores();
					moteur_pierre.posePierre(coord_pierre.getX(), coord_pierre.getY(), moteur_joueur.currentCouleur());
					
					pierres_mortes.addAll(moteur_pierre.getDernieresPierresMortes());
					
					if(goban.existPierre(coord_pierre.getX(), coord_pierre.getY())) {
						tmp = goban.getPierre(coord_pierre.getX(), coord_pierre.getY());
						
						if(isCoupValide(coord_pierre.getX(), coord_pierre.getY())) {
							pierre_final = tmp;
							System.out.println("Danger " + priorite + " en (" + pierre.getX() + ", " + pierre.getY() + ")");
						}

						moteur_pierre.removePierre(tmp);
					}
					
					for(AbstractPierre pm : pierres_mortes) {
						moteur_pierre.posePierre(pm.getX(), pm.getY(), pm.getCouleur());
					}
					
					restore_scores();
				}
			}
			
			else if(pierre.hasChaine() && goban.canBeCaptured(goban.getChaine(pierre.getNomChaine()))){
				ArrayList<AbstractPierre> copy_chaine = new ArrayList<AbstractPierre>();
				
				copy_chaine.addAll(goban.getChaine(pierre.getNomChaine()));
				
				for(AbstractPierre pierre_chaine : copy_chaine) {
					System.out.println("Pierre Chaine (" + pierre_chaine.getX() + ", " + pierre_chaine.getY() + ")");
					if(!pierres_parcourus.contains(pierre_chaine)) {
						if(priorite > 1) {
							intersection_libre = GoPierre.intersectionVide(pierre_chaine, goban.getPlateau(), taille_goban);
							
							if(!intersection_libre.isEmpty()) {
								coord_pierre = intersection_libre.get(0);
								sauvegarde_scores();
								moteur_pierre.posePierre(coord_pierre.getX(), coord_pierre.getY(), moteur_joueur.currentCouleur());

								pierres_mortes.addAll(moteur_pierre.getDernieresPierresMortes());
								
								if(goban.existPierre(coord_pierre.getX(), coord_pierre.getY())) {
									tmp = goban.getPierre(coord_pierre.getX(), coord_pierre.getY());
									
									if(isCoupValide(coord_pierre.getX(), coord_pierre.getY())) {
										pierre_final = tmp;
										priorite = 2;
										System.out.println("Danger " + priorite + " en (" + pierre.getX() + ", " + pierre.getY() + ")");
									}

									moteur_pierre.removePierre(tmp);
								}
								
								for(AbstractPierre pm : pierres_mortes) {
									moteur_pierre.posePierre(pm.getX(), pm.getY(), pm.getCouleur());
								}
								
								restore_scores();
							} 
						}
						
						for(AbstractPierre pierre_voisin : GoPierre.voisins(pierre_chaine, goban.getPlateau(), taille_goban)) {
							System.out.println("Pierre Voisin (" + pierre_voisin.getX() + ", " + pierre_voisin.getY() + ")");
							if(!pierre_voisin.hasChaine() && priorite > 0) {
								if(goban.canBeCaptured(pierre_voisin)) {
									intersection_libre = GoPierre.intersectionVide(pierre_voisin, goban.getPlateau(), taille_goban);
									
									if(!intersection_libre.isEmpty()) {
										coord_pierre = intersection_libre.get(0);
										sauvegarde_scores();
										moteur_pierre.posePierre(coord_pierre.getX(), coord_pierre.getY(), moteur_joueur.currentCouleur());

										pierres_mortes.addAll(moteur_pierre.getDernieresPierresMortes());
										
										if(goban.existPierre(coord_pierre.getX(), coord_pierre.getY())) {
											tmp = goban.getPierre(coord_pierre.getX(), coord_pierre.getY());
											
											if(isCoupValide(coord_pierre.getX(), coord_pierre.getY())) {
												pierre_final = tmp;
												priorite = 1;
												System.out.println("Danger " + priorite + " en (" + pierre.getX() + ", " + pierre.getY() + ")");
											}
	
											moteur_pierre.removePierre(tmp);
										}
										
										for(AbstractPierre pm : pierres_mortes) {
											moteur_pierre.posePierre(pm.getX(), pm.getY(), pm.getCouleur());
										}
										
										restore_scores();
									}
								} 
							}
							
							else if(pierre_voisin.hasChaine()) {
								ArrayList<AbstractPierre> chaine = goban.getChaine(pierre_voisin.getNomChaine());
								
								if(goban.canBeCaptured(chaine) && !pierre_voisin.getCouleur().equals(pierre_chaine.getCouleur()) && chaine.size() > max_taille_chaine) {
									intersection_libre = GoPierre.intersectionVide(pierre_voisin, goban.getPlateau(), taille_goban);
									
									if(!intersection_libre.isEmpty()) {
										coord_pierre = intersection_libre.get(0);
										sauvegarde_scores();
										moteur_pierre.posePierre(coord_pierre.getX(), coord_pierre.getY(), moteur_joueur.currentCouleur());

										pierres_mortes.addAll(moteur_pierre.getDernieresPierresMortes());
										
										if(goban.existPierre(coord_pierre.getX(), coord_pierre.getY())) {
											tmp = goban.getPierre(coord_pierre.getX(), coord_pierre.getY());
											
											if(isCoupValide(coord_pierre.getX(), coord_pierre.getY())) {
												tmp = goban.getPierre(coord_pierre.getX(), coord_pierre.getY());
												pierre_final = tmp;
												max_taille_chaine = chaine.size();
												priorite = 0;
												System.out.println("Danger " + priorite + " en (" + pierre.getX() + ", " + pierre.getY() + ")");
											}

											moteur_pierre.removePierre(tmp);
										}
										
										for(AbstractPierre pm : pierres_mortes) {
											moteur_pierre.posePierre(pm.getX(), pm.getY(), pm.getCouleur());
										}
										
										restore_scores();
									}
								}
							}
						}
						
						pierres_parcourus.add(pierre_chaine);
					}
				}
			}
			
			pierres_parcourus.add(pierre);
		}
		
		return pierre_final;
	}
	
	private void removeSameTerritoire() {
		ArrayList<Coordonnee> copy_territoire_noir = new ArrayList<Coordonnee>();
		ArrayList<Coordonnee> copy_territoire_blanc = new ArrayList<Coordonnee>();
		ArrayList<Coordonnee> copy_territoire_rouge = new ArrayList<Coordonnee>();
		
		copy_territoire_noir.addAll(territoire_noir);
		copy_territoire_blanc.addAll(territoire_blanc);
		
		if(moteur_joueur.getJoueurs().length == 3) {
			copy_territoire_rouge.addAll(territoire_rouge);
		}
		
		for(Coordonnee c_noir : copy_territoire_noir) {
			for(Coordonnee c_blanc : copy_territoire_blanc) {
				if(isSameCoordonnee(c_noir, c_blanc)) {
					territoire_noir.remove(c_noir);
					territoire_blanc.remove(c_blanc);
				}
			}
			
			if(moteur_joueur.getJoueurs().length == 3) {
				for(Coordonnee c_rouge : copy_territoire_rouge) {
					if(isSameCoordonnee(c_noir, c_rouge)) {
						territoire_noir.remove(c_noir);
						territoire_rouge.remove(c_rouge);
					}
				}
			}
		}
		
		if(moteur_joueur.getJoueurs().length == 3) {
			for(Coordonnee c_blanc : copy_territoire_blanc) {
				for(Coordonnee c_rouge : copy_territoire_rouge) {
					if(isSameCoordonnee(c_blanc, c_rouge)) {
						territoire_blanc.remove(c_blanc);
						territoire_rouge.remove(c_rouge);
					}
				}
			}
		}
	}
	
	private boolean isSameCoordonnee(Coordonnee c1, Coordonnee c2) {
		if(c1.getX() == c2.getX() && c1.getY() == c2.getY()) {
			return true;
		}
		
		else {
			return false;
		}
	}
	
	private HashMap<Integer, Chaine> sauvegarde_chaines(){
		HashMap<Integer, Chaine> hm_chaines = new HashMap<Integer, Chaine>();
		
		for(Integer nom_chaine : goban.getSauvegardeChaines().keySet()){
			Chaine chaine = new Chaine();
			
			for(AbstractPierre pierre : goban.getSauvegardeChaines().get(nom_chaine).getChaine()) {
				if(!pierre.isMegaPierre()) {
					chaine.addPierre(new Pierre(pierre.getCouleur(), pierre.getX(), pierre.getY()));
				}
				
				else {
					chaine.addPierre(new MegaPierre(pierre.getCouleur(), pierre.getX(), pierre.getY()));
				}
			}
			
			hm_chaines.put(nom_chaine, chaine);
		}
		
		return hm_chaines;
	}
	
	private void restore_chaines(HashMap<Integer, Chaine> hm_chaines) {
		for(Integer nom_chaine : hm_chaines.keySet()) {
			System.out.println("Coucou : " + nom_chaine);
			if(goban.getHmChaine().containsKey(nom_chaine)) {
				goban.getHmChaine().replace(nom_chaine, hm_chaines.get(nom_chaine));
			}

			else {
				goban.getHmChaine().put(nom_chaine, hm_chaines.get(nom_chaine));
			}
			
			for(AbstractPierre pierre_chaine : goban.getHmChaine().get(nom_chaine).getChaine()) {
				pierre_chaine.setNomChaine(nom_chaine);
				goban.getPlateau()[pierre_chaine.getX()][pierre_chaine.getY()] = pierre_chaine;
			}
		} 
	}
	
	private void sauvegarde_pierres_mortes() {
		if(!moteur_pierre.getDernieresPierresMortes().isEmpty()) {
			stack_pierres_mortes.push(moteur_pierre.getDernieresPierresMortes());
		}
	}
	
	private void restore_pierres_mortes() {
		if(!stack_pierres_mortes.empty()) {
			ArrayList<AbstractPierre> pierres_mortes = new ArrayList<AbstractPierre>();
			
			pierres_mortes.addAll(stack_pierres_mortes.pop());
			
			if(!pierres_mortes.isEmpty()) {
				for(AbstractPierre pierre : pierres_mortes) {
					if(pierre.getCouleur() != moteur_joueur.currentCouleur()) {
						if(pierre.isMegaPierre()) {
							moteur_pierre.setPoseMegaPierre(true);
						}
						
						moteur_pierre.posePierre(pierre.getX(), pierre.getY(), pierre.getCouleur());
						moteur_pierre.setPoseMegaPierre(false);
					}
				}
			}
		}
	}
	
	private void sauvegarde_scores() {
		int n = moteur_joueur.getJoueurs().length;
		int[] scores_precedents = new int[n];
				
		for(int i = 0 ; i < n ; i++) {
			scores_precedents[i] = (int)moteur_joueur.getJoueurs()[i].getScore();
		}
		
		stack_scores.push(scores_precedents);
	}
	
	private void restore_scores() {
		int n = moteur_joueur.getJoueurs().length;
		int[] scores_precedents = stack_scores.pop();
				
		for(int i = 0 ; i < n ; i++) {
			moteur_joueur.getJoueurs()[i].setScore(scores_precedents[i]);
		}
	}
}

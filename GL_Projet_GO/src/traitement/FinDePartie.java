package traitement;

import java.util.ArrayList;
import java.util.HashMap;

import donnees.AbstractPierre;
import donnees.Coordonnee;
import donnees.Couleur;
import donnees.Pierre;
import traitement.Goban;
import traitement.moteurs.MoteurJoueur;
import traitement.moteurs.MoteurPierre;


public class FinDePartie {

	private int taille_goban;
	private boolean BordHaut = false;
	private boolean BordDroit = false;
	private boolean BordGauche = false;
	private boolean BordBas = false;
	private boolean vivante = false;
	
	private Goban goban;
	private MoteurJoueur moteur_joueur;
	private MoteurPierre moteur_pierre;
	
	public FinDePartie(int taille_goban, Goban goban, MoteurJoueur moteur_joueur, MoteurPierre moteur_pierre) {
		this.taille_goban = taille_goban;
		this.goban = goban;
		this.moteur_joueur = moteur_joueur;
		this.moteur_pierre = moteur_pierre;
	}
	
	public void initFin(AbstractPierre[][] plateau, HashMap<Integer, Chaine> hmChaine) {
		setChaineTwoEye(plateau, hmChaine);
		pierreMorte(plateau, hmChaine);
		calculTerritoire(plateau, hmChaine);
	}
	
	public void setChaineTwoEye(AbstractPierre[][] plateau, HashMap<Integer, Chaine> hmChaine) {
		
		ArrayList<Coordonnee> interVidePierre = new ArrayList<Coordonnee>();
		ArrayList<Coordonnee> InterVideChaine = new ArrayList<Coordonnee>();
		ArrayList<Coordonnee> ListEye = new ArrayList<Coordonnee>();
		
		boolean ajouter = true;
		
		int compteur;
		int compteurYeux;
		int bordHaut = 0;
		int bordBas = taille_goban - 1;
		int bordGauche = 0;
		int bordDroit = taille_goban - 1;
		
		for(Chaine chaine : hmChaine.values()) {
			if(chaine.getChaine().size() >= 6) {
				for(AbstractPierre pierre : chaine.getChaine()) {
					interVidePierre = GoPierre.intersectionVide(pierre, plateau, taille_goban);
					if (!interVidePierre.isEmpty()) {
						for(Coordonnee coord : interVidePierre) {
							InterVideChaine.add(coord);
						}
					}
				}
				
				interVidePierre.clear();
				interVidePierre.addAll(InterVideChaine);
				
				for(Coordonnee coord : InterVideChaine) {
					compteur = 0;
					for(Coordonnee c : interVidePierre) {
						if(coord.getX() == c.getX() && coord.getY() == c.getY()) {
							compteur ++;
						}
					}
					if(compteur >= 2) {
						for (Coordonnee fl : ListEye) {
							if(fl.getX() == coord.getX() && fl.getY() == coord.getY()) {
								ajouter = false;
							}
						}
						if(ajouter) {
							ListEye.add(coord);
						}
						ajouter = true;
					}
				}
				
				if(ListEye.size() > 1) {
					compteurYeux = ListEye.size();
					for(Coordonnee c : ListEye) {
						if(c.getX() > bordHaut) {
							if(goban.existPierre(c.getX()-1, c.getY())) {
								if(goban.getPierre(c.getX()-1, c.getY()).getCouleur() != chaine.getCouleur()) {
									compteurYeux--;
								}
							}
							else {
								compteurYeux--;
							}
						}
						if(c.getX() < bordBas) {
							if(goban.existPierre(c.getX()+1, c.getY())) {
								if(goban.getPierre(c.getX()+1, c.getY()).getCouleur() != chaine.getCouleur()) {
									compteurYeux--;
								}
							}
							else {
								compteurYeux--;
							}
						}
						if(c.getY() > bordGauche) {
							if(goban.existPierre(c.getX(), c.getY()-1)) {
								if(goban.getPierre(c.getX(), c.getY()-1).getCouleur() != chaine.getCouleur()) {
									compteurYeux--;
								}
							}
							else {
								compteurYeux--;
							}
						}
						if(c.getY() < bordDroit) {
							if(goban.existPierre(c.getX(), c.getY()+1)) {
								if(goban.getPierre(c.getX(), c.getY()+1).getCouleur() != chaine.getCouleur()) {
									compteurYeux--;
								}
							}
							else {
								compteurYeux--;
							}
						}
					}
					
					if(compteurYeux >= 2) {
						chaine.setTwoEyes(true);
					}
				}
			}
			
			interVidePierre.clear();
			InterVideChaine.clear();
			ListEye.clear();
		}
	}
	
	public void pierreMorte (AbstractPierre[][] plateau, HashMap<Integer, Chaine> hmChaine) {
		
		int i, j;
		
		ArrayList<AbstractPierre> ListPierre = new ArrayList<AbstractPierre>();
		ArrayList<Integer> ListChaine = new ArrayList<Integer>();
		ArrayList<Coordonnee> listeInterVide = new ArrayList<Coordonnee>();
		ArrayList<Coordonnee> finalInterVide = new ArrayList<Coordonnee>();
		ArrayList<Coordonnee> InterVideDejaParcourue = new ArrayList<Coordonnee>();
		ArrayList<AbstractPierre> listeInterPleine = new ArrayList<AbstractPierre>();
		
		for(i=0;i<taille_goban;i++) {
			for(j=0;j<taille_goban;j++) {
				if(goban.existPierre(i, j)) {
					ListPierre.add(goban.getPierre(i, j));
				}
			}
		}
		
		for(AbstractPierre pierre : ListPierre) {
			
			if(pierre.hasChaine()) {
				if(!ListChaine.contains(pierre.getNomChaine())) {
					if(!hmChaine.get(pierre.getNomChaine()).getTwoEyes()) {
						
						for(AbstractPierre p : hmChaine.get(pierre.getNomChaine()).getChaine()) {
							setBord(p);
							listeInterVide = GoPierre.intersectionVide(p, plateau, taille_goban);
							
							for(Coordonnee c : listeInterVide) {
								if(!dejaParcourue(c, finalInterVide)) {
									setBord(c);
									finalInterVide.add(c);
								}
							}
							
							listeInterVide.clear();
						}
						
						InterVideDejaParcourue.addAll(finalInterVide);
						
						while (!vivante && finalInterVide.size() > 0) {
							listeInterVide.clear();
							finalInterVide.clear();
							
							for(Coordonnee dc : InterVideDejaParcourue) {
								setBord(dc);
								listeInterVide = GoPierre.intersectionVide(new Pierre(Couleur.NOIR, dc), plateau, taille_goban);
								
								for(Coordonnee c : listeInterVide) {   
									if(!dejaParcourue(c, finalInterVide) && !dejaParcourue(c, InterVideDejaParcourue)) {
										setBord(c);
										finalInterVide.add(c);
									}
								}
							}
							
							for(Coordonnee fc : finalInterVide) {
								InterVideDejaParcourue.add(fc);
							}

							setVivante();
						}
						
						if(!vivante) {
							for(Coordonnee c : InterVideDejaParcourue) {
								listeInterPleine = GoPierre.voisins(new Pierre(Couleur.NOIR, c), plateau, taille_goban);
								for(AbstractPierre p : listeInterPleine) {
									if(p.hasChaine()) {
										if(p.getCouleur() == pierre.getCouleur() && p.getNomChaine() != pierre.getNomChaine() /*&& hmChaine.get(p.getNomChaine()).getTwoEyes()*/) {
											vivante = true;
										}
									}
								}
							}
						}
						
//						if(vivante) {
//							System.out.println("chaine, " + pierre.getCouleur()  + " / " + pierre.getX() + "," + pierre.getY()+ " : vivante");
//						}
//						else {
//							System.out.println("chaine, " + pierre.getCouleur()  + " / " + pierre.getX() + "," + pierre.getY()+ " : morte");
//						}
						for(AbstractPierre p : hmChaine.get(pierre.getNomChaine()).getChaine()) {
							p.setVivante(vivante);
						}
					}
					
					else {
						vivante = true;
						for(AbstractPierre p : hmChaine.get(pierre.getNomChaine()).getChaine()) {
							p.setVivante(vivante);
						}
					}
					
					ListChaine.add(pierre.getNomChaine());
				}
			}
			
			else {
				setBord(pierre);
				finalInterVide = GoPierre.intersectionVide(pierre, plateau, taille_goban);
				InterVideDejaParcourue.addAll(finalInterVide);
						
				while (!vivante && finalInterVide.size() > 0) {
					listeInterVide.clear();
					finalInterVide.clear();
							
					for(Coordonnee dc : InterVideDejaParcourue) {
						setBord(dc);
						listeInterVide = GoPierre.intersectionVide(new Pierre(Couleur.NOIR, dc), plateau, taille_goban);
								
						for(Coordonnee c : listeInterVide) {   
							if(!dejaParcourue(c, finalInterVide) && !dejaParcourue(c, InterVideDejaParcourue)) {
								setBord(c);
								finalInterVide.add(c);
							}
						}
					}
							
					for(Coordonnee fc : finalInterVide) {
						InterVideDejaParcourue.add(fc);
					}

					setVivante();
				}
						
				if(!vivante) {
					for(Coordonnee c : InterVideDejaParcourue) {
						listeInterPleine = GoPierre.voisins(new Pierre(Couleur.NOIR, c), plateau, taille_goban);
						for(AbstractPierre p : listeInterPleine) {
							if(p.hasChaine()) {
								if(p.getCouleur() == pierre.getCouleur() && p.getNomChaine() != pierre.getNomChaine() /*&& hmChaine.get(p.getNomChaine()).getTwoEyes()*/) {
									vivante = true;
								}
							}
						}
					}
				}
						
//				if(vivante){
//					System.out.println("pierre, " + pierre.getCouleur()  + " / " + pierre.getX() + "," + pierre.getY()+ " : vivante");
//				}
//				else {
//					System.out.println("pierre, " + pierre.getCouleur()  + " / " + pierre.getX() + "," + pierre.getY()+ " : morte");
//				}
				pierre.setVivante(vivante);
				
			}
			vivante = false;
			BordHaut = false;
			BordBas = false;
			BordGauche = false;
			BordDroit = false;
			listeInterVide.clear();
			finalInterVide.clear();
			InterVideDejaParcourue.clear();
		}
		for(i=0;i<taille_goban;i++) {
			for(j=0;j<taille_goban;j++) {
				if(goban.existPierre(i, j)) {
					if(!goban.getPierre(i, j).isVivante()) {
						moteur_pierre.removePierre(goban.getPierre(i, j));
					}
				}
			}
		}
	}
	
	public void calculTerritoire(AbstractPierre[][] plateau, HashMap<Integer, Chaine> hmChaine) {
		
		int i, j;
		boolean end;
		boolean endCote;
		boolean neutre = false;
		
		ArrayList<Coordonnee> Noir = new ArrayList<Coordonnee>();
		ArrayList<Coordonnee> Blanc = new ArrayList<Coordonnee>();
		ArrayList<Coordonnee> Rouge = new ArrayList<Coordonnee>();
		
		ArrayList<AbstractPierre> ListPierre = new ArrayList<AbstractPierre>();
		ArrayList<Integer> ListChaine = new ArrayList<Integer>();
		
		ArrayList<Coordonnee> listeInterVide = new ArrayList<Coordonnee>();
		ArrayList<Coordonnee> finalInterVide = new ArrayList<Coordonnee>();
		ArrayList<Coordonnee> InterVideDejaParcourue = new ArrayList<Coordonnee>();
		ArrayList<Coordonnee> InterVideAutourChaine = new ArrayList<Coordonnee>();
		
		ArrayList<AbstractPierre> InterPleine = new ArrayList<AbstractPierre>();
		
		for(i=0;i<taille_goban;i++) {
			for(j=0;j<taille_goban;j++) {
				if(goban.existPierre(i, j)) {
					ListPierre.add(goban.getPierre(i, j));
				}
			}
		}
		
		for(AbstractPierre pierre : ListPierre) {
			if(pierre.hasChaine()) {				
				if(!ListChaine.contains(pierre.getNomChaine())) {					
					for(AbstractPierre p : hmChaine.get(pierre.getNomChaine()).getChaine()) {
						listeInterVide = GoPierre.intersectionVide(p, plateau, taille_goban);
							
						for(Coordonnee c : listeInterVide) {
							if(!dejaParcourue(c, finalInterVide)) {
								finalInterVide.add(c);
							}
						}
							
						listeInterVide.clear();
					}
						
					InterVideAutourChaine.addAll(finalInterVide);
					
					end = true;
					
					while(end) {
						endCote = true;
						
						InterVideDejaParcourue.add(InterVideAutourChaine.get(0));
						InterVideAutourChaine.remove(0);
						
						while(endCote) {
							listeInterVide.clear();
							finalInterVide.clear();
							
							for(Coordonnee cdp : InterVideDejaParcourue) {
								listeInterVide = GoPierre.intersectionVide(new Pierre(Couleur.NOIR, cdp), plateau, taille_goban);
								
								for(Coordonnee c : listeInterVide) {
									ArrayList<Coordonnee> list = new ArrayList<Coordonnee>();
									list.addAll(InterVideAutourChaine);
									for(Coordonnee l : list) {
										if(c.getX() == l.getX() && c.getY() == l.getY()) {
											InterVideAutourChaine.remove(l);
										}
									}
									if(!dejaParcourue(c, finalInterVide) &&!dejaParcourue(c, InterVideDejaParcourue)) {
										finalInterVide.add(c);
									}
								}
								listeInterVide.clear();
							}
							for(Coordonnee ca : finalInterVide) {
								InterVideDejaParcourue.add(ca);
							}
							if(finalInterVide.size()==0) {
								endCote = false;
							}
						}
						
						for(Coordonnee n : InterVideDejaParcourue) {
							InterPleine = GoPierre.voisins(new Pierre(Couleur.NOIR, n), plateau, i);
							for(AbstractPierre ap : InterPleine) {
								if(ap.getCouleur() != pierre.getCouleur()) {
									neutre = true;
								}
							}
						}
						
						if(!neutre) {
							if(pierre.getCouleur() == Couleur.NOIR) {
								if(Noir.isEmpty() || !dejaParcourue(InterVideDejaParcourue.get(0), Noir)) {
									moteur_joueur.getJoueurs()[0].addScore(InterVideDejaParcourue.size());
									for(Coordonnee c : InterVideDejaParcourue) {
										Noir.add(c);
									}
								}
							}
							else if(pierre.getCouleur() == Couleur.BLANC) {
								if(Blanc.isEmpty() || !dejaParcourue(InterVideDejaParcourue.get(0), Blanc)) {
									moteur_joueur.getJoueurs()[1].addScore(InterVideDejaParcourue.size());
									for(Coordonnee c : InterVideDejaParcourue) {
										Blanc.add(c);
									}
								}
							}
							else if(pierre.getCouleur() == Couleur.ROUGE) {
								if(Rouge.isEmpty() || !dejaParcourue(InterVideDejaParcourue.get(0), Rouge)) {
									moteur_joueur.getJoueurs()[2].addScore(InterVideDejaParcourue.size());
									for(Coordonnee c : InterVideDejaParcourue) {
										Rouge.add(c);
									}
								}
							}
						}
						
						neutre = false;
						
						InterVideDejaParcourue.clear();
						if(InterVideAutourChaine.size()==0) {
							end = false;
						}
					}
					
					listeInterVide.clear();
					finalInterVide.clear();
					InterVideDejaParcourue.clear();
					InterVideAutourChaine.clear();
				}
				
				ListChaine.add(pierre.getNomChaine());
			}
			else {
				InterVideAutourChaine = GoPierre.intersectionVide(pierre, plateau, taille_goban);
				
				end = true;
				while(end) {
					endCote = true;
					
					InterVideDejaParcourue.add(InterVideAutourChaine.get(0));
					InterVideAutourChaine.remove(0);
					
					while(endCote) {
						listeInterVide.clear();
						finalInterVide.clear();
						
						for(Coordonnee cdp : InterVideDejaParcourue) {
							listeInterVide = GoPierre.intersectionVide(new Pierre(Couleur.NOIR, cdp), plateau, taille_goban);
							
							for(Coordonnee c : listeInterVide) {
								ArrayList<Coordonnee> list = new ArrayList<Coordonnee>();
								list.addAll(InterVideAutourChaine);
								for(Coordonnee l : list) {
									if(c.getX() == l.getX() && c.getY() == l.getY()) {
										InterVideAutourChaine.remove(l);
									}
								}
								if(!dejaParcourue(c, finalInterVide) &&!dejaParcourue(c, InterVideDejaParcourue)) {
									finalInterVide.add(c);
								}
							}
							listeInterVide.clear();
						}
						for(Coordonnee ca : finalInterVide) {
							InterVideDejaParcourue.add(ca);
						}
						if(finalInterVide.size()==0) {
							endCote = false;
						}
					}
					
					for(Coordonnee n : InterVideDejaParcourue) {
						InterPleine = GoPierre.voisins(new Pierre(Couleur.NOIR, n), plateau, i);
						for(AbstractPierre ap : InterPleine) {
							if(ap.getCouleur() != pierre.getCouleur()) {
								neutre = true;
							}
						}
					}
					
					if(!neutre) {
						if(pierre.getCouleur() == Couleur.NOIR) {
							if(Noir.isEmpty() || !dejaParcourue(InterVideDejaParcourue.get(0), Noir)) {
								moteur_joueur.getJoueurs()[0].addScore(InterVideDejaParcourue.size());
								for(Coordonnee c : InterVideDejaParcourue) {
									Noir.add(c);
								}
							}
						}
						else if(pierre.getCouleur() == Couleur.BLANC) {
							if(Blanc.isEmpty() || !dejaParcourue(InterVideDejaParcourue.get(0), Blanc)) {
								moteur_joueur.getJoueurs()[1].addScore(InterVideDejaParcourue.size());
								for(Coordonnee c : InterVideDejaParcourue) {
									Blanc.add(c);
								}
							}
						}
						else if(pierre.getCouleur() == Couleur.ROUGE) {
							if(Rouge.isEmpty() || !dejaParcourue(InterVideDejaParcourue.get(0), Rouge)) {
								moteur_joueur.getJoueurs()[2].addScore(InterVideDejaParcourue.size());
								for(Coordonnee c : InterVideDejaParcourue) {
									Rouge.add(c);
								}
							}
						}
					}
					
					neutre = false;
					
					InterVideDejaParcourue.clear();
					if(InterVideAutourChaine.size()==0) {
						end = false;
					}
				}
				
				
				InterVideDejaParcourue.clear();
				InterVideAutourChaine.clear();
			}
		}
	}
	
	public boolean dejaParcourue(Coordonnee nc, ArrayList<Coordonnee> InterVideDejaParcourue) {
		boolean ajouter = true; 
		for(Coordonnee c : InterVideDejaParcourue) {
			if(nc.getX() == c.getX() && nc.getY() == c.getY()) {
				ajouter = false;
			}
		}
		if(!ajouter) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void setBord(AbstractPierre p) {
		if(p.getX() == 0) {
			BordHaut = true;
		}
		if (p.getX() == taille_goban-1) {
			BordBas = true;
		}
		if (p.getY() == 0) {
			BordGauche = true;
		}
		if (p.getY() == taille_goban-1) {
			BordDroit = true;
		}
	}
	
	public void setBord(Coordonnee c) {
		if(c.getX() == 0) {
			BordHaut = true;
		}
		if (c.getX() == taille_goban-1) {
			BordBas = true;
		}
		if (c.getY() == 0) {
			BordGauche = true;
		}
		if (c.getY() == taille_goban-1) {
			BordDroit = true;
		}
	}
	
	public void setVivante() {
		if(BordHaut && BordBas && BordGauche && BordDroit) {
			vivante = true;
		}
	}
}



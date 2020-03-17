package donnees;

public class ParametrePartie {
	
	public static final int ECART_HORIZONTAL = 30;
	
	public static final int ECART_VERTICAL = 50;

	public static final int LARGEUR_CASE_9 = 42;
	
	public static final int TAILLE_CERCLE_9 = LARGEUR_CASE_9 - 4;
	
	public static final int TAILLE_MEGA_CERCLE_9 = TAILLE_CERCLE_9 * 2;
	
	public static final int PETITE_DECALAGE_9 = (LARGEUR_CASE_9 - TAILLE_CERCLE_9) / 2;

	public static final int LARGEUR_CASE_19 = 20;
	
	public static final int TAILLE_CERCLE_19 = LARGEUR_CASE_19 - 2;
	
	public static final int TAILLE_MEGA_CERCLE_19 = TAILLE_CERCLE_19 * 2;
	
	public static final int PETITE_DECALAGE_19 = (LARGEUR_CASE_19 - TAILLE_CERCLE_19) / 2;
	
	public static final int[] NOMBRE_JOUEUR = {1, 2, 3};
	
	public static final int[] NOMBRE_IA = {0, 1, 2};
	
	public static final int[] DIFIICULTE_IA = {0, 1};
	
	public static final int[] TAILLE_GOBAN = {9, 19};
	
	public static final int FPS = 16;
}

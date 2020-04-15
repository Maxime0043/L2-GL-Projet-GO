package test.input;

/**
 * Cette classe permet de récupérer chaque description de chaque situation
 * pour le didacticiel du jeu de go.
 * 
 * @author Maxime, Micael et Houssam
 *
 */
public class Description {
	
	/**
	 * Pour récupérer la description d'un niveau, grâce à un indice, dans le didacticiel du jeu de go.
	 * 
	 * @param index Indice permettant de retourner la description à un endroit du tableau.
	 * @return Renvoie la description à un indice donné.
	 */
	public static String getDescription(int index) {
		String[] tab = new String[13];
		
		tab[0] = "Pour capturer une pierre ennemie,\n"
				+ "il faut l'entourer avec nos pierres\n"
				+ "pour plus qu'elle n'ait de libertés.\n"
				+ "Le nombre de liberté d’une pierre\n"
				+ "est le nombre d’intersections\n"
				+ "voisines inoccupées de\n"
				+ "cette pierre. Pour vous sortir\n"
				+ "de cette situation, vous pouvez\n"
				+ "augmenter le nombre de libertés\n"
				+ "de votre pierre en la liant avec\n"
				+ "une autre pierre de même couleur.";
		
		tab[1] = "Pour éviter de vous faire capturer\n"
				+ "vous pouvez aussi lier votre\n"
				+ "pierre à une Mega-Pierre.\n"
				+ "Une Mega-Pierre sera une pierre qui\n"
				+ "aura une taille de 2x2 et ne pourra pas\n"
				+ "être tué par une autre Mega-Pierre.\n"
				+ "Chaque joueur pourra utiliser\n"
				+ "au maximum 1 Mega-Pierre par partie.";
		
		tab[2] = "Une chaîne est un ensemble de\n"
				+ "pierres voisines de même couleur.\n"
				+ "Des intersections sont dites\n"
				+ "voisines quand elles sont sur la même\n"
				+ "ligne/colonne et sans\n"
				+ "intersection entre elles.\n"
				+ "Pour éviter de se faire capturer\n"
				+ "(en rouge par exemple),\n"
				+ "vous pouvez soit capturer une\n"
				+ "pierre ennemie pour récupérer\n"
				+ "une ou plusieurs liberté(s)\n"
				+ "(en vert) ou prolonger\n"
				+ "votre chaine avec une autre pierre\n"
				+ "(en rouge).";
	
		tab[3] = "Une Mega-Pierre peut prendre un\n"
				+ "ensemble d’intersections (2x2) où il\n"
				+ "y aura au maximum 3 pierres adverses\n"
				+ "et devra avoir au minimum une liberté.\n"
				+ "Dans les situation de danger, on peut\n"
				+ "les utiliser pour renvercer la\n"
				+ "situation. Lorsque vous détruisez\n"
				+ "des pierres ennemies avec une\n"
				+ "Mega-Pierre, vous ne recevrez\n"
				+ "aucun point.";
		
		tab[4] = "Pour capturer une pierre ennemie,\n"
				+ "il faut l'entourer avec nos pierres\n"
				+ "pour plus qu'elle n'ait de libertés.\n"
				+ "Le nombre de liberté d’une pierre\n"
				+ "est le nombre d’intersections\n"
				+ "voisines inoccupées de\n"
				+ "cette pierre. Vous recevrez un\n"
				+ "point par pierre capturé.";
		
		tab[5] = "Une pierre qui est posée sur l'un des\n"
				+ "4 bords du plateau se vera\n"
				+ "retirer 1 liberté. Il faudra donc\n"
				+ "3 pierres pour la capturer.";
		
		tab[6] = "Si elle est sur l'un des 4 coins du\n"
				+ "plateau, elle se vera retirer\n"
				+ "2 libertés. Il faudra donc\n"
				+ "2 pierres pour la capturer.";
		
		tab[7] = "Une chaîne est un ensemble de\n"
				+ "pierres voisines de même couleur.\n"
				+ "Des intersections sont dites\n"
				+ "voisines quand elles sont sur la même\n"
				+ "ligne/colonne et sans\n"
				+ "intersection entre elles.\n"
				+ "Pour pouvoir capturer une chaîne\n"
				+ "il faudra l'entourée de pierres\n"
				+ "dont la couleur est différente\n"
				+ "de celle de la chaine à capturer.";
		
		tab[8] = "Cette situation se nomme le Ko.\n"
				+ "C'est une position où une pierre est\n"
				+ "sur le point de se faire capturer\n"
				+ "et où l'adversaire, après avoir\n"
				+ "pris cette pierre, se retrouve\n"
				+ "lui-même dans cette situation.\n"
				+ "Dans une situation de ko,\n"
				+ "il est interdit de reprendre\n"
				+ "immédiatemment la pierre qui\n"
				+ "vient de prendre ce Ko.";
		
		tab[9] = "Comme pour les Pierres,\n"
				+ "il faut entourer une Mega-Pierre\n"
				+ "pour pouvoir la capturer et recevoir,\n"
				+ "cette fois ci 4 points.\n"
				+ "Une Mega-Pierre sera une pierre qui\n"
				+ "aura une taille de 2x2 et ne pourra pas\n"
				+ "être tué par une autre Mega-Pierre.\n"
				+ "Elle peut prendre un ensemble\n"
				+ "d’intersections (2x2) où il y aura\n"
				+ "au maximum 3 pierres adverses et\n"
				+ "devra avoir au minimum une liberté.";
		
		tab[10] = "Une chaine peut être composé\n"
				+ "à la fois de Pierres et de Mega-Pierres.\n"
				+ "De même, pour la capturer il faut\n"
				+ "l'entourer de pierres de même couleur.";
		
		tab[11] = "Une Mega-Pierre peut être utilisé\n"
				+ "pour capturer une ou plusieurs\n"
				+ "Pierres / Mega-Pierres.";
		
		tab[12] = "Comme une Mega-Pierre peut être\n"
				+ "posée sur des pierres ennemies\n"
				+ "et ainsi les détruires, on peut\n"
				+ "se servir de cette option pour\n"
				+ "pour capturere une chaine de pierres\n"
				+ "qui aurait été difficile de capturer\n"
				+ "en temps normal.";
		
		return tab[index];
	}
}

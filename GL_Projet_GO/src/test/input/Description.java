package test.input;

public class Description {
	
	public static String getDescription(int index) {
		String[] tab = new String[8];
		
		tab[0] = "Pour capturer une pierre ennemie,\n"
				+ "il faut l'entourer avec nos pierres\n"
				+ "pour plus qu'il n'ait de libertés.\n"
				+ "Le nombre de liberté d’une pierre\n"
				+ "est le nombre d’intersections\n"
				+ "voisines inoccupées de\n"
				+ "cette pierre. Vous recevrez un\n"
				+ "point par pierre capturé.";
		
		tab[1] = "Une pierre qui est posée sur l'un des\n"
				+ "4 bords du plateau se vera\n"
				+ "retirer 1 liberté. Il faudra donc\n"
				+ "3 pierres pour la capturer.";
		
		tab[2] = "Si elle est sur l'un des 4 coins du\n"
				+ "plateau, elle se vera retirer\n"
				+ "2 libertés. Il faudra donc\n"
				+ "2 pierres pour la capturer.";
		
		tab[3] = "Une chaîne est un ensemble de\n"
				+ "pierres voisines de même couleur.\n"
				+ "Des intersections sont dites\n"
				+ "voisines quand elles sont sur la même\n"
				+ "ligne/colonne et sans\n"
				+ "intersection entre elles.\n"
				+ "Pour pouvoir capturer une chaîne\n"
				+ "il faudra l'entourée de pierres\n"
				+ "dont la couleur est différente\n"
				+ "de celle de la chaine à capturer.";
		
		tab[4] = "";
		
		tab[5] = "Comme pour les Pierres,\n"
				+ "il faut entourer une Mega-Pierre\n"
				+ "pour pouvoir la capturer et recevoir,\n"
				+ "cette fois ci 4 points.\n"
				+ "Une Mega-Pierre sera une pierre qui\n"
				+ "aura une taille de 2x2 et ne pourra pas\n"
				+ "être tué par une autre Mega-Pierre.\n"
				+ "Il peut prendre un ensemble\n"
				+ "d’intersection (2x2) où il y aura\n"
				+ "au maximum 3 pierres adverses et\n"
				+ "devra avoir au minimum une liberté.";
		
		tab[6] = "Une chaine peut être composé\n"
				+ "à la fois de Pierres et de Mega-Pierres.\n"
				+ "De même, pour la capturer il faut\n"
				+ "l'entourer de pierres de même couleur.";
		
		tab[7] = "Une Mega-Pierre peut être utilisé\n"
				+ "pour capturer une ou plusieurs\n"
				+ "Pierres / Mega-Pierres.";
		
		return tab[index];
	}
}

package test.input;

public class Description {
	
	public static String getDescription(int index) {
		String[] tab = new String[8];
		
		tab[0] = "Pour capturer une pierre ennemie,\n"
				+ "il faut l'entourer avec nos pierres\n"
				+ "pour plus qu'il n'ait de libert�s.\n"
				+ "Le nombre de libert� d�une pierre\n"
				+ "est le nombre d�intersections\n"
				+ "voisines inoccup�es de\n"
				+ "cette pierre. Vous recevrez un\n"
				+ "point par pierre captur�.";
		
		tab[1] = "Une pierre qui est pos�e sur l'un des\n"
				+ "4 bords du plateau se vera\n"
				+ "retirer 1 libert�. Il faudra donc\n"
				+ "3 pierres pour la capturer.";
		
		tab[2] = "Si elle est sur l'un des 4 coins du\n"
				+ "plateau, elle se vera retirer\n"
				+ "2 libert�s. Il faudra donc\n"
				+ "2 pierres pour la capturer.";
		
		tab[3] = "Une cha�ne est un ensemble de\n"
				+ "pierres voisines de m�me couleur.\n"
				+ "Des intersections sont dites\n"
				+ "voisines quand elles sont sur la m�me\n"
				+ "ligne/colonne et sans\n"
				+ "intersection entre elles.\n"
				+ "Pour pouvoir capturer une cha�ne\n"
				+ "il faudra l'entour�e de pierres\n"
				+ "dont la couleur est diff�rente\n"
				+ "de celle de la chaine � capturer.";
		
		tab[4] = "";
		
		tab[5] = "Comme pour les Pierres,\n"
				+ "il faut entourer une Mega-Pierre\n"
				+ "pour pouvoir la capturer et recevoir,\n"
				+ "cette fois ci 4 points.\n"
				+ "Une Mega-Pierre sera une pierre qui\n"
				+ "aura une taille de 2x2 et ne pourra pas\n"
				+ "�tre tu� par une autre Mega-Pierre.\n"
				+ "Il peut prendre un ensemble\n"
				+ "d�intersection (2x2) o� il y aura\n"
				+ "au maximum 3 pierres adverses et\n"
				+ "devra avoir au minimum une libert�.";
		
		tab[6] = "Une chaine peut �tre compos�\n"
				+ "� la fois de Pierres et de Mega-Pierres.\n"
				+ "De m�me, pour la capturer il faut\n"
				+ "l'entourer de pierres de m�me couleur.";
		
		tab[7] = "Une Mega-Pierre peut �tre utilis�\n"
				+ "pour capturer une ou plusieurs\n"
				+ "Pierres / Mega-Pierres.";
		
		return tab[index];
	}
}

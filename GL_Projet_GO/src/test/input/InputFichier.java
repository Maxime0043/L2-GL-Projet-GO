package test.input;

public class InputFichier {
//	public static final String PATH_FILE = "./description.txt";
//	
//	public static ArrayList<String> getDescription() {
//		ArrayList<String> description = new ArrayList<String>();
//		
//		try {
//			BufferedReader reader = new BufferedReader(new FileReader(PATH_FILE));
//			String line = reader.readLine();
//			
//			while (line != null) {
//				description.add(line.substring(2));
//				System.out.println(line);
//				
//				line = reader.readLine();
//			}
//			
//			reader.close();
//		} catch (FileNotFoundException e) {
//			System.err.println(e.getMessage());
//			Go.logger.fatal("Fichier description.txt introuvable");
//		} catch (IOException e) {
//			System.err.println(e.getMessage());
//			Go.logger.fatal("Erreur rencontr�e lors de la lecture du fichier description.txt");
//		}
//		
//		return description;
//	}
	
	public static String getDesciption(int index) {
		String[] tab = new String[8];
		
		tab[0] = "Pour capturer une pierre ennemie,\nil faut l'entourer avec nos pierres pour plus\nqu'il n'ait de libert�s.\nLe nombre de libert� d�une pierre est le nombre\nd�intersections voisines inoccup�es de cette pierre.\nVous recevrez un point par pierre captur�.";
		tab[1] = "Une pierre qui est pos�e sur l'un des 4 bords\ndu plateau se vera retir�e 1 libert�.\nIl faudra donc 3 pierres pour la capturer.";
		tab[2] = "Si elle est sur l'un des 4 coins du plateau, elle se vera retir�e 2 libert�es. Il faudra donc 2 pierres pour la capturer.";
		tab[3] = "Une cha�ne est un ensemble de pierres voisines de m�me couleur. Des intersections sont dites voisines quand elles sont sur la m�me ligne/colonne et sans intersection entre elles. Pour pouvoir capturer une cha�ne il faudra l'entour�e de pierres dont la couleur est diff�rente de celle de la chaine � capturer.";
		tab[4] = "";
		tab[5] = "Comme pour les Pierres, il faut entourer une Mega-Pierre pour pouvoir la capturer et recevoir, cette fois ci 4 points. Une Mega-Pierre sera une pierre qui aura une taille de 2x2 et ne pourra pas �tre tu� par une autre Mega-Pierre. Il peut prendre un ensemble d�intersection (2x2) o� il y aura au maximum 3 pierres adverses et devra avoir au minimum une libert�.";
		tab[6] = "Une chaine peut �tre compos� � la fois de Pierres et de Mega-Pierres. De m�me, pour la capturer il faut l'entourer de pierres de m�me couleur.";
		tab[7] = "Une Mega-Pierre peut �tre utilis� pour capturer une ou plusieurs Pierres / Mega-Pierres.";
		
		return tab[index];
	}
}

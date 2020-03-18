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
//			Go.logger.fatal("Erreur rencontrée lors de la lecture du fichier description.txt");
//		}
//		
//		return description;
//	}
	
	public static String getDesciption(int index) {
		String[] tab = new String[8];
		
		tab[0] = "Pour capturer une pierre ennemie,\nil faut l'entourer avec nos pierres pour plus\nqu'il n'ait de libertés.\nLe nombre de liberté d’une pierre est le nombre\nd’intersections voisines inoccupées de cette pierre.\nVous recevrez un point par pierre capturé.";
		tab[1] = "Une pierre qui est posée sur l'un des 4 bords\ndu plateau se vera retirée 1 liberté.\nIl faudra donc 3 pierres pour la capturer.";
		tab[2] = "Si elle est sur l'un des 4 coins du plateau, elle se vera retirée 2 libertées. Il faudra donc 2 pierres pour la capturer.";
		tab[3] = "Une chaîne est un ensemble de pierres voisines de même couleur. Des intersections sont dites voisines quand elles sont sur la même ligne/colonne et sans intersection entre elles. Pour pouvoir capturer une chaîne il faudra l'entourée de pierres dont la couleur est différente de celle de la chaine à capturer.";
		tab[4] = "";
		tab[5] = "Comme pour les Pierres, il faut entourer une Mega-Pierre pour pouvoir la capturer et recevoir, cette fois ci 4 points. Une Mega-Pierre sera une pierre qui aura une taille de 2x2 et ne pourra pas être tué par une autre Mega-Pierre. Il peut prendre un ensemble d’intersection (2x2) où il y aura au maximum 3 pierres adverses et devra avoir au minimum une liberté.";
		tab[6] = "Une chaine peut être composé à la fois de Pierres et de Mega-Pierres. De même, pour la capturer il faut l'entourer de pierres de même couleur.";
		tab[7] = "Une Mega-Pierre peut être utilisé pour capturer une ou plusieurs Pierres / Mega-Pierres.";
		
		return tab[index];
	}
}

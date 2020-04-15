package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import org.apache.log4j.Logger;

import donnees.ParametrePartie;
import log.LoggerUtility;
import traitement.moteurs.Moteur;
import traitement.moteurs.MoteurPierre;

/**
 * C'est la principale classe graphique. Elle contient un {@link GoPanel}
 * et un {@link DescriptionPanel} qui permettent l'affichage du jeu de go
 * et du didacticiel.
 * 
 * @author Maxime, Micael et Houssam
 *
 */
public class Go extends JFrame implements Runnable {
	
	private static final long serialVersionUID = 1L;

	public static Logger logger = LoggerUtility.getLogger(MoteurPierre.class, "html");
	
	/**
	 * La prartie moteur est gérée dans une autre classe.
	 */
	private Moteur moteur;
	
	private JPanel menuPanel, menuGauchePanel, menuDroitPanel, goPanel, actionPanel;
	
	/**
	 * Les prarties gobanPanel et descPanel sont gérées dans d'autres classes.
	 */
	private GoPanel gobanPanel;
	private DescriptionPanel descPanel;
	
	private int choix = 0;
	private int cellule = ParametrePartie.LARGEUR_CASE_9;
	private int taille_goban = ParametrePartie.TAILLE_GOBAN[0];
	private int nb_joueur = 2, nb_ordi = 0;
	
	private JRadioButton taille9, taille19;
	private JRadioButton joueur1, joueur2, joueur3;
	private JRadioButton ordi0, ordi1, ordi2;
	
	/**
	 * Cette instance sevira à créer un thread.
	 */
	private Go instance = this;

	/**
	 * Statut initial pour l'actualisation de l'affichage de la fenêtre.
	 */
	private boolean stop = true;
	
	private boolean checked = false;
	private boolean isDidacticiel = false;
	private JCheckBox megaPierre;
	
	private JButton passer, revenirMenu, precedent, suivant, reinit;
	
	public Go() {
		super("Jeu de GO");
		
		menuPanel = new JPanel();
		menuGauchePanel = new JPanel();
		menuDroitPanel = new JPanel();
		goPanel = new JPanel();
		actionPanel = new JPanel();
		
		initLayout();
	}

	private void initLayout() {
		initMenu();
		initGoban();
		
		this.setVisible(true);
		this.setSize(ParametrePartie.WINDOW_WIDTH, ParametrePartie.WINDOW_HEIGHT);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
	}
	
	private void initMenu() {
		menuPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 1;
		gbc.gridy = 1;
		
		JLabel titre = new JLabel("Jeu de Go");
		titre.setFont(new Font("Impact", Font.PLAIN, 44));
		menuPanel.add(titre, gbc);

		gbc.gridy++;
		JButton start = new JButton("Jouer");
		start.addActionListener(new Lancer());
		menuPanel.add(start, gbc);
		
		gbc.gridy++;
		JButton didacticiel = new JButton("Didacticiel");
		didacticiel.addActionListener(new Didacticiel());
		menuPanel.add(didacticiel, gbc);
		
		gbc.gridy++;
		JLabel taille = new JLabel("Taille du goban :");
		menuPanel.add(taille, gbc);
		
		ButtonGroup tailleGroupe = new ButtonGroup();

		gbc.gridx--;
		gbc.gridy++;
		taille9 = new JRadioButton("9*9", true);
		taille9.addActionListener(new ChoixTaille());
		tailleGroupe.add(taille9);
		menuPanel.add(taille9, gbc);
		
		gbc.gridx += 2;
		taille19 = new JRadioButton("19*19");
		taille19.addActionListener(new ChoixTaille());
		tailleGroupe.add(taille19);
		menuPanel.add(taille19, gbc);

		gbc.gridx--;
		gbc.gridy++;
		JLabel joueurs = new JLabel("Nombre de joueurs :");
		menuPanel.add(joueurs, gbc);
		
		ButtonGroup joueurGroup = new ButtonGroup();

		gbc.gridx--;
		gbc.gridy++;
		joueur1 = new JRadioButton("1");
		joueur1.addActionListener(new SelectionJoueur());
		joueurGroup.add(joueur1);
		menuPanel.add(joueur1, gbc);
		
		gbc.gridx++;
		joueur2 = new JRadioButton("2", true);
		joueur2.addActionListener(new SelectionJoueur());
		joueurGroup.add(joueur2);
		menuPanel.add(joueur2, gbc);
		nb_joueur = 2;
		
		gbc.gridx++;
		joueur3 = new JRadioButton("3");
		joueur3.addActionListener(new SelectionJoueur());
		joueurGroup.add(joueur3);
		menuPanel.add(joueur3, gbc);

		gbc.gridx--;
		gbc.gridy++;
		JLabel ordis = new JLabel("Nombre d'ordinateurs :");
		menuPanel.add(ordis, gbc);
		
		ButtonGroup ordiGroup = new ButtonGroup();
		
		gbc.gridx--;
		gbc.gridy++;
		ordi0 = new JRadioButton("0", true);
		ordi0.addActionListener(new SelectionJoueur());
		ordiGroup.add(ordi0);
		menuPanel.add(ordi0, gbc);
		nb_ordi = 0;
		
		gbc.gridx++;
		ordi1 = new JRadioButton("1");
		ordi1.addActionListener(new SelectionJoueur());
		ordiGroup.add(ordi1);
		menuPanel.add(ordi1, gbc);
		
		gbc.gridx++;
		ordi2 = new JRadioButton("2");
		ordi2.addActionListener(new SelectionJoueur());
		ordiGroup.add(ordi2);
		menuPanel.add(ordi2, gbc);

		gbc.gridx--;
		gbc.gridy++;
		JButton quitterMenu = new JButton("Quitter");
		quitterMenu.addActionListener(new QuitterMenu());
		menuPanel.add(quitterMenu, gbc);
		
		taille9.setBackground(ParametrePartie.BACKGROUND_COLOR);
		taille19.setBackground(ParametrePartie.BACKGROUND_COLOR);
		joueur1.setBackground(ParametrePartie.BACKGROUND_COLOR);
		joueur2.setBackground(ParametrePartie.BACKGROUND_COLOR);
		joueur3.setBackground(ParametrePartie.BACKGROUND_COLOR);
		ordi0.setBackground(ParametrePartie.BACKGROUND_COLOR);
		ordi1.setBackground(ParametrePartie.BACKGROUND_COLOR);
		ordi2.setBackground(ParametrePartie.BACKGROUND_COLOR);
		menuPanel.setBackground(ParametrePartie.BACKGROUND_COLOR);
		
		this.setContentPane(menuPanel);
	}
	
	private void initGoban() {
		goPanel.setLayout(new BorderLayout());
		actionPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 5));
		
		megaPierre = new JCheckBox("MegaPierre");
		megaPierre.addActionListener(new Cocher());
		
		passer = new JButton("Passer");
		passer.addActionListener(new Passer());
		
		revenirMenu = new JButton("Revenir Menu");
		revenirMenu.addActionListener(new RevenirMenu());
		
		precedent = new JButton("Précédent");
		precedent.addActionListener(new ChangeLevel());
		precedent.setEnabled(false);
		
		suivant = new JButton("Suivant");
		suivant.addActionListener(new ChangeLevel());
		
		reinit = new JButton("Reset");
		reinit.addActionListener(new ChangeLevel());
	}

	/**
	 * Définit ce qu'il faut faire pour chaque unité de temps (par défaut 1 seconde) : il actualise l'affichage de la fenêtre
	 * et permet à l'utilisateur de jouer.
	 */
	@Override
	public void run() {	
		int fps = 0, compteur = 0;;
		
		while (!stop) {
			try {
				Thread.sleep(ParametrePartie.FPS); // Environ 60fps
				fps += ParametrePartie.FPS;
				compteur++;
				
				if(fps >= 1000) {
					logger.debug("Le logiciel tourne actuellement à " + compteur + " FPS (frames par seconde)");
					
					fps = 0;
					compteur = 0;
				}
				
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}

			if (!stop) {				
				update();
				moteur.run();
			}
		}
	}
	
	/**
	 * Actualise l'affichage de la fenêtre et modifie le champs d'actions possibles avec les boutons
	 */
	private void update() {
		updateFrame();
		updatePlayMegaPierre();
		
		if(isDidacticiel) {
			if(moteur.isDidacticielFini()) {
				revenirMenu();
			}
			
			else {
				descPanel.update_didacticiel();
			}
		}
	}

	/**
	 * Actualise l'affichage de la fenêtre.
	 */
	private void updateFrame() {
		gobanPanel.repaint();
	}
	
	/**
	 * Modifie le champs d'actions possibles avec les boutons présentes sur la fenêtre.
	 */
	private void updatePlayMegaPierre() {
		if(checked && !moteur.isMegaPierre()) {
			megaPierre.setSelected(false);
			checked = false;
		}
		
		if(moteur.canPlayMegaPierre()) {
			if(!megaPierre.isEnabled()) {
				megaPierre.setEnabled(true);
			}
		}
		else {
			if(megaPierre.isEnabled()) {
				megaPierre.setEnabled(false);
			}
		}
	}
	
	/**
	 * Permet de changer de sous-fenêtre.
	 * 
	 * @param panel Sous-fenêtre qui va remplacer celle présente.
	 */
	public void changeFenetre(JPanel panel) {
		this.setContentPane(panel);
		this.revalidate();
	}
	
	/**
	 * Parmet d'initialiser les sous-fenêtres du logiciel ainsi que le moteur du jeu de go.
	 */
	public void lancer() {
		moteur = new Moteur(cellule, taille_goban, nb_joueur, nb_ordi, isDidacticiel);
		gobanPanel = new GoPanel(moteur, choix, nb_joueur + nb_ordi, isDidacticiel);
		gobanPanel.setLayout(new FlowLayout());
		
		goPanel.add(gobanPanel, BorderLayout.CENTER);
		goPanel.add(actionPanel, BorderLayout.SOUTH);
		
		if(isDidacticiel) {
			precedent.setEnabled(false);
			descPanel = new DescriptionPanel(moteur, precedent, suivant, reinit);
			
			goPanel.add(descPanel, BorderLayout.EAST);
		}
		
		else {
			actionPanel.add(megaPierre);
			actionPanel.add(passer);
		}
		
		actionPanel.add(revenirMenu);
		
		changeFenetre(goPanel);
		
		if(stop) {
			stop = false;
			
			Thread goThread = new Thread(instance);
			goThread.start();
		}
	}
	
	/**
	 * Parmet de revenir la sous-fenêtre du menu et de retirer les précédentes.
	 */
	public void revenirMenu() {
		stop = true;
		
		actionPanel.removeAll();
		goPanel.removeAll();
		
		if(isDidacticiel) {
			isDidacticiel = false;
			descPanel.removeAll();
		}
		
		megaPierre.setSelected(false);
		
		changeFenetre(menuPanel);
	}
	
	/**
	 * Parmet de fermer le logiciel.
	 */
	public void fermer() {
		this.dispose();
	}
	
	/**
	 * Permet de lancer une partie du jeu de go.
	 * 
	 * @author Maxime, Micael et Houssam
	 *
	 */
	private class Lancer implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			lancer();
		}
	}
	
	/**
	 * Permet de lancer le didacticiel du jeu de go.
	 * 
	 * @author Maxime, Micael et Houssam
	 *
	 */
	private class Didacticiel implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			isDidacticiel = true;
			nb_joueur = 2;
			nb_ordi = 0;
			choix = 0;
			taille_goban = ParametrePartie.TAILLE_GOBAN[0];
			cellule = ParametrePartie.LARGEUR_CASE_9;
			
			lancer();
		}
	}
	
	/**
	 * Permet de naviguer entre les niveaux du didacticiel du jeu de go.
	 * 
	 * @author Maxime, Micael et Houssam
	 *
	 */
	private class ChangeLevel implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			
			if(source == precedent) {				
				moteur.changeLevel(false);
				
				if(moteur.getCurrentLevel() == 0) {
					precedent.setEnabled(false);
				}
				
				suivant.setText("Suivant");
			}
			
			else if(source == suivant) {				
				moteur.changeLevel(true);
				
				if(moteur.getCurrentLevel() > 0) {
					precedent.setEnabled(true);
				}
				
				if(moteur.getCurrentLevel() == moteur.getNbLevel()) {
					suivant.setText("Fin !");
				}
			}
			
			else if(source == reinit) {				
				moteur.resetLevel();
			}
		}
	}
	
	/**
	 * Permet de choisir quelle taille fera le plateau du jeu de go.
	 * 
	 * @author Maxime, Micael et Houssam
	 *
	 */
	private class ChoixTaille implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			
			if(source == taille9) {
				choix = 0;
				cellule = ParametrePartie.LARGEUR_CASE_9;
			}
			
			else if(source == taille19) {
				choix = 1;
				cellule = ParametrePartie.LARGEUR_CASE_19;
			}

			taille_goban = ParametrePartie.TAILLE_GOBAN[choix];
		}
		
	}
	
	/**
	 * Permet de sélectionner combien de joueurs seront présents dans une partie de jeu de go.
	 * 
	 * @author Maxime, Micael et Houssam
	 *
	 */
	private class SelectionJoueur implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			
			if(source == joueur1) {
				if(ordi0.isSelected()) {
					ordi1.setSelected(true);
					nb_ordi = 1;
				}
				nb_joueur = 1;
			}
			else if(source == joueur2) {
				if(ordi2.isSelected()) {
					ordi0.setSelected(true);
					nb_ordi = 0;
				}
				nb_joueur = 2;
			}
			else if(source == joueur3) {
				ordi0.setSelected(true);
				nb_joueur = 3;
				nb_ordi = 0;
			}
			
			if(source == ordi0) {
				if(joueur1.isSelected()) {
					joueur2.setSelected(true);
					nb_joueur = 2;
				}
				nb_ordi = 0;
			}
			else if(source == ordi1) {
				if(joueur3.isSelected()) {
					joueur1.setSelected(true);
					nb_joueur = 1;
				}
				nb_ordi = 1;
			}
			else if(source == ordi2) {
				joueur1.setSelected(true);
				nb_ordi = 2;
				nb_joueur = 1;
			}
		}
		
	}
	
	/**
	 * Permet au joueur courant de jouer une méga-pierre dans une partie de jeu de go.
	 * 
	 * @author Maxime, Micael et Houssam
	 *
	 */
	private class Cocher implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(megaPierre != null) {
				if(moteur.isMegaPierre()) {
					moteur.setPoseMegaPierre(false);
				}
				else {
					moteur.setPoseMegaPierre(true);
				}
				
				if(!checked) {
					checked = true;
				}
				else {					
					checked = false;
				}
			}
		}
		
	}
	
	/**
	 * Permet au joueur courant de passer son tour dans une partie de jeu de go.
	 * 
	 * @author Maxime, Micael et Houssam
	 *
	 */
	private class Passer implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			moteur.passer();
		}
		
	}
	
	/**
	 * Permet de revenir à la sous-fenêtre du menu.
	 * 
	 * @author Maxime, Micael et Houssam
	 *
	 */
	private class RevenirMenu implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {			
			revenirMenu();
		}
		
	}
	
	/**
	 * Permet de fermer le logiciel.
	 * 
	 * @author Maxime, Micael et Houssam
	 *
	 */
	private class QuitterMenu implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			fermer();
		}
		
	}
}

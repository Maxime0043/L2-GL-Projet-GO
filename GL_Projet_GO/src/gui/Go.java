package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
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

public class Go extends JFrame implements Runnable {
	
	private static final long serialVersionUID = 1L;

	public static Logger logger = LoggerUtility.getLogger(MoteurPierre.class, "html");
	private Moteur moteur;
	
	private JPanel menuPanel, menuGauchePanel, menuDroitPanel, goPanel, actionPanel;
	private GoPanel gobanPanel;
	private DescriptionPanel descPanel;
	
	private int choix = 0;
	private int cellule = ParametrePartie.LARGEUR_CASE_9;
	private int taille_goban = ParametrePartie.TAILLE_GOBAN[0];
	private int nb_joueur = 2, nb_ordi = 0;
	
	private JRadioButton taille9, taille19;
	private JRadioButton joueur1, joueur2, joueur3;
	private JRadioButton ordi0, ordi1, ordi2;
	
	private Go instance = this;

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
		menuPanel.setLayout(new BorderLayout(0, 0));
		menuDroitPanel.setLayout(new GridBagLayout());
		menuDroitPanel.setPreferredSize(new Dimension(ParametrePartie.WINDOW_WIDTH/2, ParametrePartie.WINDOW_HEIGHT));
		menuGauchePanel.setLayout(new GridBagLayout());
		menuGauchePanel.setPreferredSize(new Dimension(ParametrePartie.WINDOW_WIDTH/2, ParametrePartie.WINDOW_HEIGHT));
		
		GridBagConstraints gbcDoite = new GridBagConstraints();
		gbcDoite.insets = new Insets(5, 5, 25, ParametrePartie.WINDOW_WIDTH / 10);
		gbcDoite.gridx = 1;
		gbcDoite.gridy = 1;
		
		JButton start = new JButton("Lancer");
		start.addActionListener(new Lancer());
		menuDroitPanel.add(start, gbcDoite);
		
		gbcDoite.gridy++;
		JButton didacticiel = new JButton("Didacticiel");
		didacticiel.addActionListener(new Didacticiel());
		menuDroitPanel.add(didacticiel, gbcDoite);

		gbcDoite.gridy++;
		JButton quitterMenu = new JButton("Quitter");
		quitterMenu.addActionListener(new QuitterMenu());
		menuDroitPanel.add(quitterMenu, gbcDoite);
		
		GridBagConstraints gbcGauche = new GridBagConstraints();
		gbcGauche.insets = new Insets(5, ParametrePartie.WINDOW_WIDTH / 10, 5, 5);
		gbcGauche.gridx = 0;
		gbcGauche.gridy = 1;
		
		JLabel taille = new JLabel("Taille du goban :");
		menuGauchePanel.add(taille, gbcGauche);
		
		ButtonGroup tailleGroupe = new ButtonGroup();
		
		gbcGauche.gridy++;
		taille9 = new JRadioButton("9*9", true);
		taille9.addActionListener(new ChoixTaille());
		tailleGroupe.add(taille9);
		menuGauchePanel.add(taille9, gbcGauche);
		
		gbcGauche.gridy++;
		taille19 = new JRadioButton("19*19");
		taille19.addActionListener(new ChoixTaille());
		tailleGroupe.add(taille19);
		menuGauchePanel.add(taille19, gbcGauche);

		gbcGauche.gridy++;
		JLabel joueurs = new JLabel("Nombre de joueurs :");
		menuGauchePanel.add(joueurs, gbcGauche);
		
		ButtonGroup joueurGroup = new ButtonGroup();

		gbcGauche.gridy++;
		joueur1 = new JRadioButton("1");
		joueur1.addActionListener(new SelectionJoueur());
		joueurGroup.add(joueur1);
		menuGauchePanel.add(joueur1, gbcGauche);
		
		gbcGauche.gridy++;
		joueur2 = new JRadioButton("2", true);
		joueur2.addActionListener(new SelectionJoueur());
		joueurGroup.add(joueur2);
		menuGauchePanel.add(joueur2, gbcGauche);
		nb_joueur = 2;
		
		gbcGauche.gridy++;
		joueur3 = new JRadioButton("3");
		joueur3.addActionListener(new SelectionJoueur());
		joueurGroup.add(joueur3);
		menuGauchePanel.add(joueur3, gbcGauche);
		
		gbcGauche.gridy++;
		JLabel ordis = new JLabel("Nombre d'ordinateurs :");
		menuGauchePanel.add(ordis, gbcGauche);
		
		ButtonGroup ordiGroup = new ButtonGroup();
		
		gbcGauche.gridy++;
		ordi0 = new JRadioButton("0", true);
		ordi0.addActionListener(new SelectionJoueur());
		ordiGroup.add(ordi0);
		menuGauchePanel.add(ordi0, gbcGauche);
		nb_ordi = 0;
		
		gbcGauche.gridy++;
		ordi1 = new JRadioButton("1");
		ordi1.addActionListener(new SelectionJoueur());
		ordiGroup.add(ordi1);
		menuGauchePanel.add(ordi1, gbcGauche);
		
		gbcGauche.gridy++;
		ordi2 = new JRadioButton("2");
		ordi2.addActionListener(new SelectionJoueur());
		ordiGroup.add(ordi2);
		menuGauchePanel.add(ordi2, gbcGauche);
		
		joueur1.setEnabled(false);
		ordi0.setEnabled(false);
		ordi1.setEnabled(false);
		ordi2.setEnabled(false);

		menuPanel.add(menuDroitPanel, BorderLayout.EAST);
		menuPanel.add(menuGauchePanel, BorderLayout.WEST);
		
		taille9.setBackground(ParametrePartie.BACKGROUND_COLOR);
		taille19.setBackground(ParametrePartie.BACKGROUND_COLOR);
		joueur1.setBackground(ParametrePartie.BACKGROUND_COLOR);
		joueur2.setBackground(ParametrePartie.BACKGROUND_COLOR);
		joueur3.setBackground(ParametrePartie.BACKGROUND_COLOR);
		ordi0.setBackground(ParametrePartie.BACKGROUND_COLOR);
		ordi1.setBackground(ParametrePartie.BACKGROUND_COLOR);
		ordi2.setBackground(ParametrePartie.BACKGROUND_COLOR);
		menuPanel.setBackground(ParametrePartie.BACKGROUND_COLOR);
		menuDroitPanel.setBackground(ParametrePartie.BACKGROUND_COLOR);
		menuGauchePanel.setBackground(ParametrePartie.BACKGROUND_COLOR);
		
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
			}
		}
	}
	
	private void update() {
		updateFrame();
		updatePlayMegaPierre();
		
		if(isDidacticiel) {
			if(moteur.isDidacticielFini()) {
				revenirMenu();
			}
			
			else {
				descPanel.setDescription(moteur.getDescription());
			}
		}
	}

	private void updateFrame() {
		gobanPanel.repaint();
	}
	
	private void updatePlayMegaPierre() {
		if(checked && !moteur.isMegaPierre()) {
			megaPierre.setSelected(false);
			checked = false;
		}
		
		if(gobanPanel.canPlayMegaPierre()) {
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
	
	public void changeFenetre(JPanel panel) {
		this.setContentPane(panel);
		this.revalidate();
	}
	
	public void lancer() {
		moteur = new Moteur(cellule, taille_goban, nb_joueur, nb_ordi, isDidacticiel);
		gobanPanel = new GoPanel(moteur, choix, nb_joueur + nb_ordi, isDidacticiel);
		gobanPanel.setLayout(new FlowLayout());
		
		goPanel.add(gobanPanel, BorderLayout.CENTER);
		goPanel.add(actionPanel, BorderLayout.SOUTH);
		
		if(isDidacticiel) {
			precedent.setEnabled(false);
			descPanel = new DescriptionPanel(precedent, suivant, reinit);
			
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
			moteur.start();
		}
	}
	
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
	
	public void fermer() {
		this.dispose();
	}
	
	private class Lancer implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			lancer();
		}
	}
	
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
	
	private class ChangeLevel implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			
			if(source == precedent) {				
				moteur.changeLevel(false);
				
				if(moteur.getCurrentLevel() == 0) {
					precedent.setEnabled(false);
				}
			}
			
			else if(source == suivant) {				
				moteur.changeLevel(true);
				
				if(moteur.getCurrentLevel() > 0) {
					precedent.setEnabled(true);
				}
			}
			
			else if(source == reinit) {				
				moteur.resetLevel();
			}
		}
	}
	
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
	
	private class Passer implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			moteur.passer();
		}
		
	}
	
	private class RevenirMenu implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {			
			revenirMenu();
		}
		
	}
	
	private class QuitterMenu implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			fermer();
		}
		
	}
	
	public static void main(String[] args) {
		new Go();
	}
}

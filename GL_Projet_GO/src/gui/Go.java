package gui;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JTextArea;

import org.apache.log4j.Logger;

import donnees.Couleur;
import donnees.ParametrePartie;
import log.LoggerUtility;
import test.input.InputFichier;
import traitement.Moteur;

public class Go extends JFrame implements Runnable {
	
	private static final long serialVersionUID = 1L;

	public static Logger logger = LoggerUtility.getLogger(Moteur.class, "html");
	
	private JPanel menuPanel, menuGauchePanel, menuDroitPanel, goPanel, actionPanel, descPanel;
	private GoPanel gobanPanel;
	
	private int choix = 0;
	private int nb_joueur, nb_ordi;
	private JRadioButton taille9, taille19;
	private JRadioButton joueur1, joueur2, joueur3;
	private JRadioButton ordi0, ordi1, ordi2;
	
	private JLabel[] scores;
	
	private int window_width, window_height;
	
	private Go instance = this;

	private boolean stop = true;
	private boolean checked = false;
	private boolean isDidacticiel = false;
	private JCheckBox megaPierre;
	
	private JTextArea desc_label;
	
	public Go() {
		super("Jeu de GO");
		
		menuPanel = new JPanel();
		menuGauchePanel = new JPanel();
		menuDroitPanel = new JPanel();
		goPanel = new JPanel();
		actionPanel = new JPanel();
		descPanel = new JPanel();
		scores = new JLabel[3];
		
		window_width = 700;
		window_height = 500;
		
		initLayout();
	}

	private void initLayout() {
		initMenu();
		initGoban();
		
		this.setVisible(true);
		this.setSize(window_width, window_height);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
	}
	
	private void initMenu() {
		menuPanel.setLayout(new BorderLayout(0, 0));
		menuDroitPanel.setLayout(new GridBagLayout());
		menuDroitPanel.setPreferredSize(new Dimension(window_width/2, window_height));
		menuGauchePanel.setLayout(new GridBagLayout());
		menuGauchePanel.setPreferredSize(new Dimension(window_width/2, window_height));
		
		GridBagConstraints gbcDoite = new GridBagConstraints();
		gbcDoite.insets = new Insets(5, 5, 25, window_width / 10);
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
		gbcGauche.insets = new Insets(5, window_width / 10, 5, 5);
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
		
		taille9.setBackground(Color.decode("#F2B352"));
		taille19.setBackground(Color.decode("#F2B352"));
		joueur1.setBackground(Color.decode("#F2B352"));
		joueur2.setBackground(Color.decode("#F2B352"));
		joueur3.setBackground(Color.decode("#F2B352"));
		ordi0.setBackground(Color.decode("#F2B352"));
		ordi1.setBackground(Color.decode("#F2B352"));
		ordi2.setBackground(Color.decode("#F2B352"));
		menuPanel.setBackground(Color.decode("#F2B352"));
		menuDroitPanel.setBackground(Color.decode("#F2B352"));
		menuGauchePanel.setBackground(Color.decode("#F2B352"));
		
		this.setContentPane(menuPanel);
	}
	
	private void initGoban() {
		goPanel.setLayout(new BorderLayout());
		actionPanel.setLayout(new FlowLayout());
		
		megaPierre = new JCheckBox("MegaPierre");
		megaPierre.addActionListener(new Cocher());
		actionPanel.add(megaPierre);
		
		JButton passer = new JButton("Passer");
		passer.addActionListener(new Passer());
		actionPanel.add(passer);
		
		JButton revenirMenu = new JButton("Revenir Menu");
		revenirMenu.addActionListener(new RevenirMenu());
		actionPanel.add(revenirMenu);
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
		updateScore();
		updatePlayMegaPierre();
		
		if(isDidacticiel) {
			if(gobanPanel.isDidacticielFini()) {
				revenirMenu();
			}
			
			else {
				desc_label.setText(InputFichier.getDesciption(gobanPanel.getCurrentLevel()));
			}
		}
	}

	private void updateFrame() {
		gobanPanel.repaint();
	}
	
	private void initLabels() {
		for(int i = 0 ; i < nb_joueur ; i++) {
			scores[i] = new JLabel();
		}
	}
	
	private void updateScore() {
		int scoreJoueur[] = gobanPanel.getScores();
		Couleur[] couleurs = Couleur.getCouleurs();
		String texte;
		
		for(int i = 0 ; i < nb_joueur ; i++) {
			texte = couleurs[i].name() + " : " + String.valueOf(scoreJoueur[i]);
			
			scores[i].setText(texte + "  ");
		}
	}
	
	private void updatePlayMegaPierre() {
		if(checked && !gobanPanel.getIsMegaPierre()) {
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
		gobanPanel = new GoPanel(choix, window_width, nb_joueur, nb_ordi, isDidacticiel);
		gobanPanel.setLayout(new FlowLayout());
		
		initLabels();
		
		for(int i = 0 ; i < nb_joueur ; i++) {
			gobanPanel.add(scores[i]);
		}
		
		goPanel.add(gobanPanel, BorderLayout.CENTER);
		goPanel.add(actionPanel, BorderLayout.SOUTH);
		
		if(isDidacticiel) {
			descPanel.setLayout(new FlowLayout());
			descPanel.setPreferredSize(new Dimension((int)(window_width / 2.5), gobanPanel.getHeight()));
			descPanel.setBackground(Color.decode("#F2B352"));
			
			desc_label = new JTextArea();
			desc_label.setEditable(false);
			desc_label.setOpaque(false);
			desc_label.setPreferredSize(new Dimension((int)(window_width / 2.5), 200));
			
			desc_label.setText(InputFichier.getDesciption(0));
			descPanel.add(desc_label);
			
			goPanel.add(descPanel, BorderLayout.EAST);
		}
		
		changeFenetre(goPanel);
		
		if(stop) {
			stop = false;
			Thread goThread = new Thread(instance);
			goThread.start();
		}
	}
	
	public void revenirMenu() {
		stop = true;
		
		goPanel.remove(gobanPanel);
		goPanel.remove(actionPanel);
		
		if(isDidacticiel) {
			isDidacticiel = false;
			descPanel.remove(desc_label);
			goPanel.remove(descPanel);
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
			
			lancer();
		}
	}
	
	private class ChoixTaille implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			
			if(source == taille9) {
				choix = 0;
			}
			else if(source == taille19) {
				choix = 1;
			}
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
				gobanPanel.poseMegaPierre();
				
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
			gobanPanel.passer();
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

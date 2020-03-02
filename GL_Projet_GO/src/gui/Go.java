package gui;

import java.awt.BorderLayout;
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

import donnees.Couleur;

public class Go extends JFrame implements Runnable {
	
	private static final long serialVersionUID = 1L;
	
	private JPanel menuPanel, goPanel, actionPanel;
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
	JCheckBox megaPierre;

	public Go() {
		super("Jeu de GO");
		
		menuPanel = new JPanel();
		goPanel = new JPanel();
		actionPanel = new JPanel();
		scores = new JLabel[3];
		
		window_width = 700;
		window_height = 500;
		
		initLayout();
	}

	private void initLayout() {
		/*----------------Menu------------------*/
		
		menuPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 1;
		
		JButton start = new JButton("Lancer");
		start.addActionListener(new Lancer());
		menuPanel.add(start, gbc);
		
		gbc.gridy++;
		JButton didacticiel = new JButton("Didacticiel");
		didacticiel.addActionListener(new Didacticiel());
		menuPanel.add(didacticiel, gbc);
		
		ButtonGroup tailleGroupe = new ButtonGroup();

		gbc.gridy++;
		taille9 = new JRadioButton("Taille 9*9", true);
		taille9.addActionListener(new ChoixTaille());
		tailleGroupe.add(taille9);
		menuPanel.add(taille9, gbc);
		
		gbc.gridy++;
		taille19 = new JRadioButton("Taille 19*19");
		taille19.addActionListener(new ChoixTaille());
		tailleGroupe.add(taille19);
		menuPanel.add(taille19, gbc);
		
		gbc.gridy++;
		JLabel joueurs = new JLabel("Nombre de joueurs :");
		menuPanel.add(joueurs, gbc);
		
		ButtonGroup joueurGroup = new ButtonGroup();
		
		gbc.gridy++;
		gbc.gridx--;
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
		
		gbc.gridy++;
		gbc.gridx--;
		JLabel ordis = new JLabel("Nombre d'ordinateurs :");
		menuPanel.add(ordis, gbc);
		
		ButtonGroup ordiGroup = new ButtonGroup();
		
		gbc.gridy++;
		gbc.gridx--;
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

		gbc.gridy++;
		gbc.gridx--;
		JButton quitterMenu = new JButton("Quitter");
		quitterMenu.addActionListener(new QuitterMenu());
		menuPanel.add(quitterMenu, gbc);
		
		joueur1.setEnabled(false);
		ordi0.setEnabled(false);
		ordi1.setEnabled(false);
		ordi2.setEnabled(false);
		
		this.setContentPane(menuPanel);
		
		/*----------------Go------------------*/
		
		goPanel.setLayout(new BorderLayout());
		actionPanel.setLayout(new FlowLayout());
		
		megaPierre = new JCheckBox("MegaPierre");
		megaPierre.addActionListener(new Cocher());
		actionPanel.add(megaPierre);
		
		JButton revenirMenu = new JButton("Revenir Menu");
		revenirMenu.addActionListener(new RevenirMenu());
		actionPanel.add(revenirMenu);
//		revenirMenu.setEnabled(false);
		
		/*----------------Parametres------------------*/
		
		this.setVisible(true);
		this.setSize(window_width, window_height);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
	}

	@Override
	public void run() {	
		while (!stop) {
			try {
				Thread.sleep(15); // Environ 64fps
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
		gobanPanel = new GoPanel(choix, nb_joueur, nb_ordi, isDidacticiel);

		gobanPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		initLabels();
		
		for(int i = 0 ; i < nb_joueur ; i++) {
			gobanPanel.add(scores[i]);
		}
		
		goPanel.add(gobanPanel, BorderLayout.CENTER);
		goPanel.add(actionPanel, BorderLayout.SOUTH);
		
		changeFenetre(goPanel);
		
		if(stop) {
			stop = false;
			Thread goThread = new Thread(instance);
			goThread.start();
		}
	}
	
	public void revenirMenu() {
		stop = true;
		isDidacticiel = false;
		
		goPanel.remove(gobanPanel);
		goPanel.remove(actionPanel);
		
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

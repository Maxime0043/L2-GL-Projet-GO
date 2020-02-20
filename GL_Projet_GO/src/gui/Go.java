package gui;

import java.awt.BorderLayout;
import java.awt.Container;
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

public class Go extends JFrame implements Runnable {
	
	private static final long serialVersionUID = 1L;
	
	private Container container;
	private JPanel menuPanel, goPanel, actionPanel;
	private GoPanel gobanPanel;
	
	private int choix = 0;
	private JRadioButton taille9, taille19;
	private JRadioButton joueur1, joueur2, joueur3;
	private JRadioButton ordi0, ordi1, ordi2;
	
	private int window_width, window_height;
	
	private Go instance = this;
	private Thread goThread = new Thread(instance);

	
	private boolean stop = false;
	JCheckBox megaPierre;

	public Go() {
		super("Jeu de GO");
		
		menuPanel = new JPanel();
		goPanel = new JPanel();
		actionPanel = new JPanel();
		
//		window_width = ParametrePartie.ECART + ParametrePartie.TAILLE_GOBAN[panel.getChoix()] * ParametrePartie.LARGEUR_CASE;
//		window_height = 3* ParametrePartie.ECART + ParametrePartie.TAILLE_GOBAN[panel.getChoix()] * ParametrePartie.LARGEUR_CASE;
		
		window_width = 700;
		window_height = 500;
		
		initLayout();
	}

	private void initLayout() {
		container = this.getContentPane();
		container.setLayout(new FlowLayout());
		
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
		joueur1 = new JRadioButton("1", true);
		joueur1.addActionListener(new SelectionJoueur());
		joueurGroup.add(joueur1);
		menuPanel.add(joueur1, gbc);
		
		gbc.gridx++;
		joueur2 = new JRadioButton("2");
		joueur2.addActionListener(new SelectionJoueur());
		joueurGroup.add(joueur2);
		menuPanel.add(joueur2, gbc);
		
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
		ordi0 = new JRadioButton("0", false);
		ordi0.addActionListener(new SelectionJoueur());
		ordiGroup.add(ordi0);
		menuPanel.add(ordi0, gbc);
		
		gbc.gridx++;
		ordi1 = new JRadioButton("1", true);
		ordi1.addActionListener(new SelectionJoueur());
		ordiGroup.add(ordi1);
		menuPanel.add(ordi1, gbc);
		
		gbc.gridx++;
		ordi2 = new JRadioButton("2", false);
		ordi2.addActionListener(new SelectionJoueur());
		ordiGroup.add(ordi2);
		menuPanel.add(ordi2, gbc);

		gbc.gridy++;
		gbc.gridx--;
		JButton quitter = new JButton("Quitter");
		quitter.addActionListener(new Quitter());
		menuPanel.add(quitter, gbc);
		
		this.setContentPane(menuPanel);
		
		/*----------------Go------------------*/
		
		goPanel.setLayout(new BorderLayout());
		
		megaPierre = new JCheckBox("MegaPierre");
		megaPierre.addActionListener(new Cocher());
		actionPanel.add(megaPierre);
		
		
		
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
				updateFrame();
			}
		}
	}

	private void updateFrame() {
		gobanPanel.repaint();
	}
	
	public void changeFenetre(JPanel panel) {
		this.setContentPane(panel);
		this.revalidate();
	}
	
	public void fermer() {
		this.dispose();
	}
	
	private class Lancer implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			gobanPanel = new GoPanel(choix);
			
			goPanel.add(gobanPanel, BorderLayout.CENTER);
			goPanel.add(actionPanel, BorderLayout.SOUTH);
			
			changeFenetre(goPanel);
			
			goThread.start();
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
			
			if(source == joueur3) {
				ordi0.setSelected(true);
			}
			else if(source == joueur1) {
				if(ordi0.isSelected()) {
					ordi1.setSelected(true);
				}
			}
			else if(source == joueur2) {
				if(ordi2.isSelected()) {
					ordi0.setSelected(true);
				}
			}
			else if(source == ordi0) {
				if(joueur1.isSelected()) {
					joueur2.setSelected(true);
				}
			}
			else if(source == ordi1) {
				if(joueur3.isSelected()) {
					joueur1.setSelected(true);
				}
			}
			else if(source == ordi2) {
				joueur1.setSelected(true);
			}
		}
		
	}
	
	private class Cocher implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(megaPierre != null) {
				gobanPanel.poseMegaPierre();
			}
		}
		
	}
	
	private class Quitter implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			fermer();
		}
		
	}
	
	public static void main(String[] args) {
		new Go();
	}
}

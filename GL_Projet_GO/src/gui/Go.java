package gui;

import javax.swing.JFrame;

import donnees.ParametrePartie;

public class Go extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	GoPanel panel;
	private int window_width, window_height;

	public Go() {
		super("Jeu de GO");
		
		panel = new GoPanel();
		
		window_width = ParametrePartie.ECART + ParametrePartie.TAILLE_GOBAN[panel.getChoix()] * ParametrePartie.LARGEUR_CASE;
		window_height = 2* ParametrePartie.ECART + ParametrePartie.TAILLE_GOBAN[panel.getChoix()] * ParametrePartie.LARGEUR_CASE;
		
		initLayout();
	}

	private void initLayout() {
		this.getContentPane().add(panel);
		this.setVisible(true);
		this.setSize(window_width, window_height);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
	}
	
	public static void main(String[] args) {
		new Go();
	}
}

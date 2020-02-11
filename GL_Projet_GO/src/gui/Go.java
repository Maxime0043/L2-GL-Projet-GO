package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Go extends JFrame implements Runnable {
	
	private static final long serialVersionUID = 1L;
	
	JPanel actionJeu;
	GoPanel panel = new GoPanel();
	private int window_width, window_height;
	
	private Go instance = this;
	
	private boolean stop = false;

	public Go() {
		super("Jeu de GO");
		
		actionJeu = new JPanel();
		panel = new GoPanel();
		
//		window_width = ParametrePartie.ECART + ParametrePartie.TAILLE_GOBAN[panel.getChoix()] * ParametrePartie.LARGEUR_CASE;
//		window_height = 3* ParametrePartie.ECART + ParametrePartie.TAILLE_GOBAN[panel.getChoix()] * ParametrePartie.LARGEUR_CASE;
		
		window_width = 700;
		window_height = 700;
		
		initLayout();
	}

	private void initLayout() {
		Container container = this.getContentPane();
		container.setLayout(new BorderLayout());
		
		JButton button = new JButton("Lancer");
		button.addActionListener(new Lancer());
		actionJeu.add(button);
		
		container.add(panel, BorderLayout.CENTER);
		container.add(actionJeu, BorderLayout.SOUTH);
		
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
				Thread.sleep(200);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}

			if (!stop) {
				updateFrame();
			}
		}
	}

	private void updateFrame() {
		//Il faut ajouter tous les cercles ici (/!\ArrayList) et non dans GoPanel
		
		panel.repaint();
	}
	
	private class Lancer implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			Thread goThread = new Thread(instance);
			goThread.start();
		}
		
	}
	
	public static void main(String[] args) {
		new Go();
	}
}

package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import donnees.ParametrePartie;

public class Go extends JFrame implements Runnable {
	
	private static final long serialVersionUID = 1L;
	
	
	GoPanel panel = new GoPanel();
	private int window_width, window_height;
	
	private Go instance = this;
	
	private boolean stop = false;

	public Go() {
		super("Jeu de GO");
		
		panel = new GoPanel();
		
		window_width = ParametrePartie.ECART + ParametrePartie.TAILLE_GOBAN[panel.getChoix()] * ParametrePartie.LARGEUR_CASE;
		window_height = 2* ParametrePartie.ECART + ParametrePartie.TAILLE_GOBAN[panel.getChoix()] * ParametrePartie.LARGEUR_CASE;
		
		initLayout();
		
		JButton button = new JButton("Lancer");
		button.addActionListener(new Lancer());
		panel.add(button);
	}

	private void initLayout() {
		this.getContentPane().add(panel);
		this.setVisible(true);
		this.setSize(window_width, window_height);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(true);
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

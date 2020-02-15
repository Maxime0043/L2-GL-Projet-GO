package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Go extends JFrame implements Runnable {
	
	private static final long serialVersionUID = 1L;
	
	JPanel actionJeu;
	GoPanel panel = new GoPanel();
	private int window_width, window_height;
	
	private Go instance = this;
	
	private boolean stop = false;
	JCheckBox megaPierre;

	public Go() {
		super("Jeu de GO");
		
		actionJeu = new JPanel();
		panel = new GoPanel();
		
//		window_width = ParametrePartie.ECART + ParametrePartie.TAILLE_GOBAN[panel.getChoix()] * ParametrePartie.LARGEUR_CASE;
//		window_height = 3* ParametrePartie.ECART + ParametrePartie.TAILLE_GOBAN[panel.getChoix()] * ParametrePartie.LARGEUR_CASE;
		
		window_width = 700;
		window_height = 500;
		
		initLayout();
	}

	private void initLayout() {
		Container container = this.getContentPane();
		container.setLayout(new BorderLayout());
		
		JButton button = new JButton("Lancer");
		button.addActionListener(new Lancer());
		actionJeu.add(button);
		
		megaPierre = new JCheckBox("MegaPierre");
		megaPierre.addActionListener(new Cocher());
		actionJeu.add(megaPierre);
		
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
		panel.repaint();
	}
	
	private class Lancer implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			Thread goThread = new Thread(instance);
			goThread.start();
		}
		
	}
	
	private class Cocher implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(megaPierre != null)
				System.out.println("Cocher");
		}
		
	}
	
	public static void main(String[] args) {
		new Go();
	}
}

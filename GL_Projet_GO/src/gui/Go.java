package gui;

import javax.swing.JFrame;

public class Go extends JFrame{
	private static final long serialVersionUID = 1L;
	GoPanel panel;

	public Go() {
		super("Jeu de GO");
		
		panel = new GoPanel();
		
		initLayout();
	}

	private void initLayout() {
		getContentPane().add(panel);
		setVisible(true);
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(true);
	}
	
	public static void main(String[] args) {
		new Go();
	}
}

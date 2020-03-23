package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import donnees.ParametrePartie;
import test.input.Description;

public class DescriptionPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private JPanel haut, bas;
	
	private JTextArea desc_text;

	public DescriptionPanel(JButton precedent, JButton suivant) {
		init(precedent, suivant);
	}
	
	private void init(JButton precedent, JButton suivant) {
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(ParametrePartie.LARGEUR_DESCRIPTION, ParametrePartie.WINDOW_HEIGHT));
		this.setBackground(Color.decode("#F2B352"));
		
		initHaut();
		initBas(precedent, suivant);
		
		this.add(haut, BorderLayout.CENTER);
		this.add(bas, BorderLayout.SOUTH);
	}
	
	private void initHaut() {
		haut = new JPanel();
		haut.setLayout(new FlowLayout(FlowLayout.LEFT, 0, ParametrePartie.WINDOW_HEIGHT / 8));
		haut.setBackground(ParametrePartie.BACKGROUND_COLOR);
		
		desc_text = new JTextArea();
		desc_text.setEditable(false);
		desc_text.setOpaque(false);
		desc_text.setFont(new Font("Impact", Font.PLAIN, 18));
		
		desc_text.setText(Description.getDescription(0));
		
		haut.add(desc_text);
	}
	
	private void initBas(JButton precedent, JButton suivant) {
		bas = new JPanel();
		bas.setLayout(new GridBagLayout());
		bas.setBackground(ParametrePartie.BACKGROUND_COLOR);
		
		GridBagConstraints gbcBoutton = new GridBagConstraints();
		gbcBoutton.insets = new Insets(5, 5, 5, ParametrePartie.LARGEUR_DESCRIPTION / 5);
		gbcBoutton.gridx = 1;
		gbcBoutton.gridy = 1;
		
		bas.add(precedent, gbcBoutton);
		
		gbcBoutton.gridx++;
		bas.add(suivant, gbcBoutton);
	}
	
	public void setDescription(String desc) {
		desc_text.setText(desc);
	}
}

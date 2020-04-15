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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import donnees.ParametrePartie;
import test.input.Description;
import traitement.Didacticiel;
import traitement.moteurs.Moteur;

/**
 * Cette classe gère l'affichage graphique de la partie description du Didacticiel.
 * 
 * Cette classe est seulement responsable de l'affichage des résultats graphique.
 * 
 * Il n'y a pas de traitement algorithmique dans cette classe.
 * 
 * @author Maxime, Micael et Houssam
 *
 */
public class DescriptionPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private Moteur moteur;
	
	private JPanel haut, milieu, bas;
	
	private JLabel label_categorie;
	private JTextArea desc_text;

	public DescriptionPanel(Moteur moteur, JButton precedent, JButton suivant, JButton reinit) {
		this.moteur = moteur;
		
		init(precedent, suivant, reinit);
	}
	
	private void init(JButton precedent, JButton suivant, JButton reinit) {
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(ParametrePartie.LARGEUR_DESCRIPTION, ParametrePartie.WINDOW_HEIGHT));
		this.setBackground(Color.decode("#F2B352"));
		
		initHaut();
		initCenter();
		initBas(precedent, suivant, reinit);
		
		this.add(haut, BorderLayout.NORTH);
		this.add(milieu, BorderLayout.CENTER);
		this.add(bas, BorderLayout.SOUTH);
	}
	
	private void initHaut() {
		haut = new JPanel();
		haut.setLayout(new FlowLayout(FlowLayout.CENTER));
		haut.setBackground(ParametrePartie.BACKGROUND_COLOR);
		
		label_categorie = new JLabel("DEFENSE");
		label_categorie.setFont(new Font("Impact", Font.PLAIN, 24));
		
		haut.add(label_categorie);
	}
	
	private void initCenter() {
		milieu = new JPanel();
		milieu.setLayout(new FlowLayout(FlowLayout.LEFT, 0, ParametrePartie.WINDOW_HEIGHT / 25));
		milieu.setBackground(ParametrePartie.BACKGROUND_COLOR);
		
		desc_text = new JTextArea();
		desc_text.setEditable(false);
		desc_text.setOpaque(false);
		desc_text.setFont(new Font("Impact", Font.PLAIN, 18));
		
		desc_text.setText(Description.getDescription(0));
		
		milieu.add(desc_text);
	}
	
	private void initBas(JButton precedent, JButton suivant, JButton reinit) {
		bas = new JPanel();
		bas.setLayout(new GridBagLayout());
		bas.setBackground(ParametrePartie.BACKGROUND_COLOR);
		
		GridBagConstraints gbcBoutton = new GridBagConstraints();
		gbcBoutton.insets = new Insets(5, 5, 5, 5);
		gbcBoutton.gridx = 1;
		gbcBoutton.gridy = 1;
		
		bas.add(precedent, gbcBoutton);

		gbcBoutton.gridx++;
		bas.add(reinit, gbcBoutton);
		
		gbcBoutton.gridx++;
		bas.add(suivant, gbcBoutton);
	}
	
	public void update_didacticiel() {
		if(moteur.getCurrentLevel() > Didacticiel.MAX_LEVEL_DEFENSE) {
			label_categorie.setText("ATTAQUE");
		}
		
		else {
			label_categorie.setText("DEFENSE");
		}
		
		desc_text.setText(moteur.getDescription());
	}
}

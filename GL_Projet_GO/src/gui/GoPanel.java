package gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import donnees.ParametrePartie;

public class GoPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	int choix = 0;
	
	int sizepx = ParametrePartie.LARGEUR_CASE;
	int border = ParametrePartie.ECART;
	int size = ParametrePartie.TAILLE_GOBAN[choix];
	
	public GoPanel() {
		
		
	}
	
	public int getChoix() {
		return choix;
	}
	
	public void setChoix(int valeur) {
		choix = valeur;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.white);
		
		drawGrid(g);
	}
	
	private void drawGrid(Graphics g){
		g.setColor(Color.BLACK);
		
		for(int i = 0 ; i < size ; i++){
			g.drawLine(border, border+i*sizepx, border+(size-1)*sizepx, border+i*sizepx);
			g.drawLine(border+i*sizepx, border, border+i*sizepx, border+(size-1)*sizepx);
		}
	}

}

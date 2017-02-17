package br.rodrigoneto.tetris;

import java.awt.BorderLayout;
import java.awt.Label;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class TetrisPrincipal extends JFrame{
	
	public Painel painel;
	
	public TetrisPrincipal(){
		JLabel label = new JLabel("Score: 0");
		add(label,BorderLayout.SOUTH);
		setTitle("Tetris");
		setSize(200, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);;
		setResizable(false);
		painel = new Painel(label);
		add(painel);
		setVisible(true);
		setFocusable(true);
		addKeyListener(painel);
		painel.run();
		
		
	}
	public static void main(String[] args) {
		
		new TetrisPrincipal();
		
	}

}

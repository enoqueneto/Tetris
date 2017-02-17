package br.rodrigoneto.tetris;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.synth.SynthSpinnerUI;

public class Painel extends JPanel implements KeyListener {

	private static final int QUADRADOS_HORIZONTAL = 10;
	private static final int QUADRADOS_VERTICAL = 22;
	private int[][] tela;
	private int x;
	private int y;
	private Peca pecaAtual;
	private JLabel scoreLabel;
	private int score;
	private boolean pausado;
	private boolean executando;

	public Painel(JLabel label) {
		setFocusable(true);
		scoreLabel = label;
		score = 0;
		tela = new int[QUADRADOS_VERTICAL][QUADRADOS_HORIZONTAL];
		pecaAtual = new Peca();
		pecaAtual.peca = pecaAtual.pecaAleatoria();
		pausado = false;
		executando = true;
		x = 5;
		y = 1;
	}

	public int getLarguraQuad() {
		return (int) getSize().getWidth() / QUADRADOS_HORIZONTAL;
	}

	public int getAlturaQuad() {
		return (int) getSize().getHeight() / QUADRADOS_VERTICAL;
	}

	private boolean tentarMover(int novoX, int novoY) {

		for (int i = 0; i < 4; i++) {

			int x = novoX + pecaAtual.getX(i);
			int y = novoY - pecaAtual.getY(i);

			if (x < 0 || x >= QUADRADOS_HORIZONTAL || y >= QUADRADOS_VERTICAL) {
				return false;
			}
			if ((x >= 0 && y >= 0) && (x < QUADRADOS_HORIZONTAL && y < QUADRADOS_VERTICAL)) {
				if (tela[y][x] == 1) {

					return false;
				}
			}
		}
		return true;

	}

	private void moverEsq() {
		int novoX = x - 1;
		int novoY = y;

		if (tentarMover(novoX, novoY)) {
			x = novoX;
			y = novoY;
		}
	}

	private void moverDir() {
		int novoX = x + 1;
		int novoY = y;

		if (tentarMover(novoX, novoY)) {
			x = novoX;
			y = novoY;
		}
	}

	private void moverBaixo() {
		int novoX = x;
		int novoY = y + 1;

		if (tentarMover(novoX, novoY)) {
			x = novoX;
			y = novoY;
		} else {
			criarPecaNova();
		}
	}

	private void rotacionarEsq() {

		if (pecaAtual.peca == Peca.Tetromino.PECA_I && x == 1) {
			return;
		}

		if (x != 0 && x != QUADRADOS_HORIZONTAL - 1) {
			pecaAtual.rotacionarEsq();
		}
	}

	private void rotacionarDir() {

		if (pecaAtual.peca == Peca.Tetromino.PECA_I && x == 1) {
			return;
		}

		if (x != 0 && x != QUADRADOS_HORIZONTAL - 1) {
			pecaAtual.rotacionarDir();
		}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		Color c = new Color(0, 255, 0);

		for (int i = 0; i < 4; i++) {
			int curX = x + pecaAtual.getX(i);
			int curY = y - pecaAtual.getY(i);

			g.fillRect(curX * getLarguraQuad(), curY * getAlturaQuad(), getLarguraQuad() - 1, getAlturaQuad() - 1);
		}

		for (int i = 0; i < QUADRADOS_VERTICAL; i++) {
			for (int j = 0; j < QUADRADOS_HORIZONTAL; j++) {

				if (tela[i][j] == 1) {

					g.fillRect(getLarguraQuad() * j, getAlturaQuad() * i, getLarguraQuad() - 1, getAlturaQuad() - 1);

				}
			}

		}

	}

	private void criarPecaNova() {

		for (int i = 0; i < 4; i++) {
			int curX = x + pecaAtual.getX(i);
			int curY = y - pecaAtual.getY(i);
			tela[curY][curX] = 1;
		}
		pecaAtual = new Peca();
		pecaAtual.peca = pecaAtual.pecaAleatoria();
		x = 5;
		y = 2;

		if (!tentarMover(x, y)) {
			executando = false;
			scoreLabel.setText("Game Over");
		}

	}

	private void descerPeca() {

		while (true) {

			int novoX = x;
			int novoY = y + 1;

			if (tentarMover(novoX, novoY)) {
				x = novoX;
				y = novoY;

			} else {

				break;
			}
			

		}
	}

	private void removeLinha() {

		int qtdLinhasCheias = 0;
		for (int i = QUADRADOS_VERTICAL - 1; i >= 0; i--) {
			boolean linhaCheia = true;
			for (int j = 0; j < QUADRADOS_HORIZONTAL; j++) {
				if (tela[i][j] == 0) {
					linhaCheia = false;
					break;
				}
			}

			if (linhaCheia) {
				qtdLinhasCheias++;
				for (int k = i; k > 0; k--) {
					for (int j = 0; j < QUADRADOS_HORIZONTAL; j++) {
						tela[k][j] = tela[k - 1][j];
					}
				}
			}
		}

		if (qtdLinhasCheias > 0) {
			score += qtdLinhasCheias;
			scoreLabel.setText("Score: " + score);

		}

	}

	private void pausar() {

		if (!pausado) {
			scoreLabel.setText("Pausa");
			pausado = true;

		} else {
			scoreLabel.setText("Score: " + score);
			pausado = false;

		}
	}

	public void run() {
		while (executando) {
			try {
				if (!pausado) {
					moverBaixo();
					removeLinha();
					repaint();
				}
				Thread.sleep(400);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {

		case KeyEvent.VK_LEFT:
			if (!pausado) {
				moverEsq();
			}
			break;
		case KeyEvent.VK_RIGHT:
			if (!pausado) {
				moverDir();
			}
			break;

		case KeyEvent.VK_UP:
			if (!pausado) {
				rotacionarEsq();
			}
			break;

		case KeyEvent.VK_DOWN:
			if (!pausado) {
				rotacionarDir();
			}
			break;

		case KeyEvent.VK_SPACE:
			if (!pausado) {
				descerPeca();
			}
			break;
		case KeyEvent.VK_P:
			pausar();
			break;
		}

		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}

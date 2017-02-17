package br.rodrigoneto.tetris;

import java.util.Random;

public class Peca {

	enum Tetromino {
		NENHUM(new int[][] { { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 } }),
		PECA_Z(new int[][] { { 0, -1 }, { 0, 0 },{ 1, 0 }, { 1, 1 } }), 
		PECA_S(new int[][] { { 0, -1 }, { 0, 0 }, { -1, 0 }, { -1, 1 } }), 
		PECA_QUAD(new int[][] { { 0, -1 }, { -1, -1 }, { 0, 0 }, { -1, 0 } }), 
		PECA_L(new int[][] { { 1,-1  }, { 0, -1 }, { 0, 0 }, { 0, 1 } }), 
		PECA_J(new int[][] { { -1, -1 }, { 0, -1 }, { 0, 0 }, { 0, 1 } }), 
		PECA_I(new int[][] { { 0, -1 }, { 0, 0 }, { 0, 1 }, { 0, 2 } }), 
		PECA_T(new int[][] { { 0, -1 }, { 0, 0 }, { -1, 0 }, { 1, 0 } });

		public final int cords[][];

		int[][] getCords() {
			return cords;
		}

		Tetromino(int[][] cords) {
			this.cords = cords;
		}
	}

	public Tetromino peca;
	private Random r;
	private int[][] cords;

	public Peca() {
		peca = Tetromino.NENHUM;
		cords = peca.getCords();
		r = new Random();
	}

	public Tetromino pecaAleatoria() {

		Tetromino[] valores = Tetromino.values();
		Tetromino peca = valores[Math.abs(r.nextInt()) % 7 + 1];
		cords = peca.getCords();
		return peca;
	}

	public int getX(int index) {
		return cords[index][0];
	}

	public int getY(int index) {
		return cords[index][1];
	}

	public void setX(int index, int x) {
		cords[index][0] = x;
	}

	public void setY(int index, int y) {
		cords[index][1] = y;
	}

	public void rotacionarEsq() {
		
		if(peca == Tetromino.PECA_QUAD){
			return;
		}

		for (int i = 0; i < 4; i++) {
			int aux = cords[i][0];
			cords[i][0] = cords[i][1];
			cords[i][1] = -aux;
		}
	}

	public void rotacionarDir() {
		if(peca == Tetromino.PECA_QUAD){
			return;
		}
		for (int i = 0; i < 4; i++) {
			int aux = cords[i][0];
			cords[i][0] = -cords[i][1];
			cords[i][1] = aux;
		}
	}
	
	public int menorX() {
		int m = cords[0][0];
		for (int i = 0; i < cords.length; i++) {
			m = Math.min(m, cords[i][0]);
		}

		return m;
	}

	public int menorY() {
		int m = cords[0][1];
		for (int i = 0; i < cords.length; i++) {
			m = Math.min(m, cords[i][1]);
		}

		return m;
	}
}

package tabuleiroJogo;

import excecoes.TabuleiroException;

public abstract class Tabuleiro {
	protected int linhas;
	protected int colunas;
	protected boolean[][] tabuleiro;
	
	public Tabuleiro() {
	}
	
	//faz o controle do tamanho do tabuleiro digitado pelo usuário.
	public Tabuleiro(int linhas, int colunas) {
		if (linhas > 16 || colunas > 16) {
			throw new TabuleiroException("O tabuleiro pode ter no máximo 16 linhas e 16 colunas\n");
		} else if(linhas < 2 || colunas < 2) {
			throw new TabuleiroException("O tabuleiro pode ter no mínimo 2 linhas e 2 colunas\n");
		}
			this.linhas = linhas;
			this.colunas = colunas;
			tabuleiro = new boolean[linhas][colunas];
	}

	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}
	
	public boolean[][] getTabuleiro() {
		return tabuleiro;
	}
}

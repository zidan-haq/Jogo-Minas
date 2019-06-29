package tabuleiroJogo;

import java.util.Random;

public class Bombas extends Tabuleiro {
	private int quantidadeBombas = (int) Math.round(linhas * colunas * 0.16);
	Random random = new Random();

	public Bombas(int linhas, int colunas) {
		super(linhas, colunas);
	}

	public int getQuantidadeBombas() {
		return quantidadeBombas;
	}

	//esse metódo deve ser invocado uma única vez no início da partida. Ele quem irá posicionar as bombas no tabuleiro
	public void posicionarBombas() {
		int bombasNoTabuleiro = 0;
		while(quantidadeBombas != bombasNoTabuleiro){
			int aLinha = random.nextInt(linhas);
			int aColuna = random.nextInt(colunas);
			if (tabuleiro[aLinha][aColuna] == false) {
				tabuleiro[aLinha][aColuna] = true;
				bombasNoTabuleiro++;
			}	
		}
	}
}
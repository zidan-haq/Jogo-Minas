package tabuleiroJogo;

import java.util.ArrayList;
import java.util.List;

import aplicacao.InterfacePrograma;

public class Posicao {
	private int linha;
	private int coluna;
	private Bombas bombas;
	
	public Posicao() {
	}
	
	public Posicao(Bombas bombas) {
		this.bombas = bombas;
	}
	public Posicao(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}
	
	public int getLinha() {
		return linha;
	}
	public int getColuna() {
		return coluna;
	}
	
	//exemplo: converte 5, 7 (tabuleiro do java) para 6, h (tabuleiro que o usuário vê)
	public String tabuleiroParaUsuario(int linha, int coluna) {
		linha += 1;
		char colunaLetra = InterfacePrograma.alfabeto[coluna];
		return linha + ", " + colunaLetra;
	}
	
	//exemplo: converte 6, h (tabuleiro que o usuário vê) para 5, 7 (tabuleiro que o java entende)
	public void usuarioParaTabuleiro(int linha, char colunaLetra) {
		this.linha = linha-1;
		for(int x = 0; x<16;x++) {
			if(colunaLetra == InterfacePrograma.alfabeto[x]) {
				coluna = x;
				break;
			}
		}
	}

	//verifica se há bombas ao redor da posicao selecionada
	public int bombasAoRedor(Bombas bombas) {
		this.bombas = bombas;
		int contBombas = 0;
		for(Posicao lpc : pontosCardeais()) {
			if(bombas.getTabuleiro()[lpc.getLinha()][lpc.getColuna()] == true) {
				contBombas++;
			}
		}
		return contBombas;
	}
	
	//gera uma lista com todas as posições possíveis imediatamente ao lado da posição seleicionada
	public List<Posicao> pontosCardeais(){
		List<Posicao> listaPontosCardeais = new ArrayList<>();
		//norte
		if(linha>0) {
			Posicao n = new Posicao(linha - 1, coluna);
			if(!listaPontosCardeais.contains(n)) {
				listaPontosCardeais.add(n);
			}
		}
		//sul
		if(linha < bombas.getLinhas()-1) {
			Posicao n = new Posicao(linha + 1, coluna);
			if(!listaPontosCardeais.contains(n)) {
				listaPontosCardeais.add(n);
			}
		}
		//oeste
		if(coluna>0) {
			Posicao n = new Posicao(linha, coluna - 1);
			if(!listaPontosCardeais.contains(n)) {
				listaPontosCardeais.add(n);
			}
		}
		//leste
		if(coluna<bombas.getColunas()-1) {
			Posicao n = new Posicao(linha, coluna + 1);
			if(!listaPontosCardeais.contains(n)) {
				listaPontosCardeais.add(n);
			}
		}
		//noroeste
		if(linha>0 && coluna>0) {
			Posicao n = new Posicao(linha - 1, coluna - 1);
			if(!listaPontosCardeais.contains(n)) {
				listaPontosCardeais.add(n);
			}
		}
		//nordeste
		if(linha>0 && coluna<bombas.getColunas()-1) {
			Posicao n = new Posicao(linha - 1, coluna + 1);
			if(!listaPontosCardeais.contains(n)) {
				listaPontosCardeais.add(n);
			}
		}
		//suldoeste
		if(linha<bombas.getLinhas()-1 && coluna>0) {
			Posicao n = new Posicao(linha + 1, coluna - 1);
			if(!listaPontosCardeais.contains(n)) {
				listaPontosCardeais.add(n);
			}
		}
		//suldeste
		if(linha<bombas.getLinhas()-1 && coluna<bombas.getColunas()-1) {
			Posicao n = new Posicao(linha + 1, coluna + 1);
			if(!listaPontosCardeais.contains(n)) {
				listaPontosCardeais.add(n);
			}
		}
		return listaPontosCardeais;
	}
}


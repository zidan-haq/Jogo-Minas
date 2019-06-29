package aplicacao;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import excecoes.TabuleiroException;
import tabuleiroJogo.Bombas;
import tabuleiroJogo.Posicao;

public class InterfacePrograma {
	private boolean gameOver = false;
	private boolean ganhou = false;
	private char continuar = 's';

	private List<Posicao> posicoesSelecionadas = new ArrayList<>();
	boolean contem;

	Bombas bombas;

	public char getContinuar() {
		return continuar;
	}

	public static Scanner scanner = new Scanner(System.in);

	public static char[] alfabeto = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p' };
	
	//Cores para o terminal
	String ANSI_Reset = "\u001B[0m";
	String ANSI_Vermelho = "\u001B[31m";
	String ANSI_Verde = "\u001B[32m";
	String ANSI_Amarelo = "\u001B[33m";

	//cria o tabuleiro do tamanho escolhido pelo usuário, também lança algumas exceções.
	public void tamanhoTabuleiro() {
		while (true) {
			try {
				System.out.println("Escolha o tamanho do tabuleiro: ");
				System.out.print("Linhas (entre 2 e 16): ");
				int linhas = scanner.nextInt();
				System.out.print("Colunas (entre 2 e 16): ");
				int colunas = scanner.nextInt();

				bombas = new Bombas(linhas, colunas);
				bombas.posicionarBombas();
				break;

			} catch (TabuleiroException e) {
				System.out.print("\033[H\033[2J");
				System.out.flush();
				System.out.print(ANSI_Amarelo + e.getMessage() + ANSI_Reset);
				scanner.nextLine();
			} catch (InputMismatchException e) {
				System.out.print("\033[H\033[2J");
				System.out.flush();
				System.out.println(ANSI_Amarelo + "Use apenas números" + ANSI_Reset);
				scanner.nextLine();
			}
		}
	}

	//lógica de controle da partida.
	public void jogadas() {
		String erro = "";
		while (!gameOver) {
			try {
				printTabuleiro();
				System.out.println(ANSI_Amarelo + erro + ANSI_Reset);
				
				System.out.println("Escolha uma posição:");

				System.out.print("Linha: ");
				int linha = scanner.nextInt();
				System.out.print("Coluna: ");
				char colunaLetra = scanner.next().charAt(0);

				selecionarPosicao(linha, colunaLetra);

				if (gameOver) {
					printTabuleiro();
					System.out.println("VOCÊ PERDEU!");
					posicoesSelecionadas.clear();
					gameOver = false;
					break;
				} else if (ganhou) {
					printTabuleiro();
					System.out.println("VOCÊ GANHOU!");
					posicoesSelecionadas.clear();
					ganhou = false;
					break;
				}
			} catch (TabuleiroException e) {
				printTabuleiro();
				erro = e.getMessage();
				scanner.nextLine();
			} catch (InputMismatchException e) {
				printTabuleiro();
				erro = "Use apenas números";
				scanner.nextLine();
			}
		}

		System.out.print("\nDeseja jogar novamente (s/n)? ");
		scanner.nextLine();
		continuar = scanner.nextLine().toLowerCase().charAt(0);
		if (continuar != 's') {
			scanner.close();
		}
	}

	//imprime o tabuleiro na tela
	public void printTabuleiro() {
		//limpa a tela
		System.out.print("\033[H\033[2J");
		System.out.flush();
		
		//quando der game over o programa mostrará o tabuleiro completo
		if(gameOver) {
			posicoesSelecionadas.clear();
			for(int t = 0; t < bombas.getTabuleiro().length; t++)
				for(int t2 = 0; t2 < bombas.getTabuleiro()[t].length; t2++)
					posicoesSelecionadas.add(new Posicao(t,t2));
		}
		
		for (int i = 0; i < bombas.getLinhas(); i++) {
			System.out.println();
			for (int j = 0; j < bombas.getColunas(); j++) {
				boolean auxiliar = true;

				for (Posicao x : posicoesSelecionadas) {
					if (x.getLinha() == i && x.getColuna() == j && bombas.getTabuleiro()[i][j] == false) {
						if(x.bombasAoRedor(bombas) > 0) {
							System.out.print(ANSI_Verde + " " + x.bombasAoRedor(bombas) + ANSI_Reset);
						} else {
							System.out.print("  ");
						}
						auxiliar = false;
					} else if (x.getLinha() == i && x.getColuna() == j && bombas.getTabuleiro()[i][j] == true) {
						System.out.print(ANSI_Vermelho + " *" + ANSI_Reset);
						auxiliar = false;
					}
				}

				if (auxiliar) {
					System.out.print(" -");
				}
			}
			System.out.printf(" %d", i + 1);
		}

		System.out.println();
		for (int x = 0; x < bombas.getColunas(); x++) {
			System.out.print(" " + alfabeto[x]);
		}
		System.out.println("\n\n");
	}

	public void selecionarPosicao(int linha, char colunaLetra) {
		//verifica se a posição selecionada está no limite do tabuleiro
		if (linha < 1 || linha > bombas.getLinhas() || colunaLetra < 'a'
				|| colunaLetra > alfabeto[bombas.getColunas() - 1]) {
			throw new TabuleiroException("A posição escolhida não existe");
		}

		Posicao posicao = new Posicao(bombas);
		posicao.usuarioParaTabuleiro(linha, colunaLetra);

		//verifica se a posição já foi selecionada
		posicoesSelecionadas.forEach(x -> {
			if (x.getLinha() == posicao.getLinha() && x.getColuna() == posicao.getColuna()) {
				throw new TabuleiroException("Essa posição já foi selecionada!");
			}
		});

		//lógica de game over
		if (bombas.getTabuleiro()[posicao.getLinha()][posicao.getColuna()] == true) {
			gameOver = true;
		}

		//aciona o método posicoesVazias
		posicoesVazias(posicao);

		//Lógica usada para verificar se o usuário ganhou o jogo
		if (posicoesSelecionadas.size() + bombas.getQuantidadeBombas() == bombas.getLinhas() * bombas.getColunas()) {
			ganhou = true;
		}
	}

	//se a posição estiver vazia ele adicionará os elementos vazios na lista posicoesSelecionadas
	public void posicoesVazias(Posicao posicao) {
		List<Posicao> listaAuxiliar = new ArrayList<>();
		List<Posicao> listaAuxiliar2 = new ArrayList<>();
		listaAuxiliar.add(posicao);
		int quantItens = 0;
		
		while(quantItens < listaAuxiliar.size()) {
			quantItens = listaAuxiliar.size();
			listaAuxiliar2.clear();
			listaAuxiliar2.addAll(listaAuxiliar);
			
			//verifica se os "pontos cardeais" dos elementos da listaAuxiliar2 estão na listaAuxiliar, se não estiverem serão adicionados
			for (Posicao n : listaAuxiliar2) {
				if(n.bombasAoRedor(bombas) == 0) {
					for(Posicao n2 : n.pontosCardeais()) {
						boolean tem = false;
						for(Posicao p : listaAuxiliar) {
							if(n2.getLinha()==p.getLinha() && n2.getColuna() == p.getColuna()) {
								tem = true;
								break;
							}
						}
						if(!tem) {
							listaAuxiliar.add(n2);
						}
					}
				}
			}
		}
		//verifica se os elementos da lista auxiliar estão presentes na lista de posicoesSelecionadas, se não estiverem serão adicionados
		for (Posicao x : listaAuxiliar) {
			boolean tem = false;
			for (Posicao y : posicoesSelecionadas) {
				if(y.getLinha()==x.getLinha() && y.getColuna() == x.getColuna()) {
					tem = true;
				}
			}
			if(!tem) {
				posicoesSelecionadas.add(x);
			}
		}
		listaAuxiliar.clear();
	}
}
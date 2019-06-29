/* Jogo MINAS
 * Criado por Zidan Araújo Haq em 29/06/2019
 */

package aplicacao;

public class Programa {

	public static void main(String[] args) {
		InterfacePrograma IP = new InterfacePrograma();

		while (IP.getContinuar() == 's') {
			//limpa a tela e imprime o nome do jogo
			System.out.print("\033[H\033[2J");
			System.out.flush();
			System.out.println("			-----------\n			***Minas***\n			-----------");
			System.out.println();
			
			IP.tamanhoTabuleiro();

			IP.jogadas();
		}
	}
}
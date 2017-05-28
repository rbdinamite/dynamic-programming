package ProgramacaoDinamica;

/**
 * 
 * @author Roberto Abreu Bento
 * 
 * UM CAMINH�O DE UMA EMPRESA DISTRIBUIDORA DE REM�DIOS PRECISA SER ABASTECIDO COM UMA S�RIE DE R�MEDIOS PARA ENTREGA.
 * ACONTECE QUE OS REM�DIOS DEVEM SER ESCOLHIDOS DE ACORDO COM SUA PRIORIDADE DE ENTREGA, OU SEJA, OS REM�DIOS QUE NECESSITAM DE ENTREGA O MAIS R�PIDO POSS�VEL.
 * A ESCOLHA DEVE SER REALIZADA LEVANDO EM CONSIDERA��O O LIMITE NA CAPACIDADE DO CAMINH�O. COMO S�O REM�DIOS REFRIGERADOS, N�O � POSS�VEL REALIZAR O PARTICIONAMENTO DO �TEM.
 * 
 * A SOLU��O PROPOSTA � A APLICA��O DE UM ALGORITMO DE PROGRAMA��O DIN�MICA PARA PREENCHER O CAMINH�O COM REM�DIOS, COM O OBJETIVO DE MAXIMIXAR O N�VEL DE PRIORIDADE DOS �TENS.
 *
 * NESTE ALGORITMO, TEM-SE:
 * 
 * listaTamanho -> LISTA COM A TAMANHO DE CADA ITEM
 * listaPrioridade -> LISTA COM O GRAU DE PRIORIDADE DE CADA ITEM
 * capacidade -> TAMANHO M�XIMO QUE O CAMINH�O SUPORTA
 * listaDados -> MATRIZ DE INFORMA��ES COM AS CAPACIDADES �TIMAS PARA CADA METRO C�BICO
 * 
 * REGRA UTILIZADA PARA CALCULO:
 * 
 * listaDados[i][j] ser�:
 * 	* listaDados[i-1][j], se listaTamanho[i] > j
 *  * listaDados[i-1][j-listaTamanho[i]]+listaPrioridade[i], se listaTamanho[i] <= j
 *					
 * RESULTADO OBTIDO:
 * 
 *  Matriz completa:
 * 0 | 0  | 3  | 3  | 3  | 3  | 3  | 3  | 3  | 3  | 3  | 3  | 3  | 3  | 3  | 3  | 3  | 3  | 3  | 3
 * 0 | 0  | 3  | 3  | 5  | 5  | 5  | 8  | 8  | 8  | 8  | 8  | 8  | 8  | 8  | 8  | 8  | 8  | 8  | 8
 * 0 | 0  | 3  | 4  | 5  | 5  | 7  | 8  | 9  | 9  | 9  | 12 | 12 | 12 | 12 | 12 | 12 | 12 | 12 | 12
 * 0 | 0  | 3  | 4  | 5  | 7  | 7  | 8  | 10 | 11 | 12 | 12 | 14 | 15 | 16 | 16 | 16 | 19 | 19 | 19
 * 0 | 4  | 4  | 4  | 7  | 8  | 9  | 11 | 11 | 12 | 14 | 15 | 16 | 16 | 18 | 19 | 20 | 20 | 20 | 23
 * 0 | 4  | 4  | 4  | 7  | 8  | 9  | 11 | 11 | 12 | 14 | 15 | 16 | 16 | 18 | 19 | 20 | 20 | 20 | 23
 * 5 | 5  | 9  | 9  | 9  | 12 | 13 | 14 | 16 | 16 | 17 | 19 | 20 | 21 | 21 | 23 | 24 | 25 | 25 | 25
 * 5 | 5  | 9  | 9  | 9  | 12 | 13 | 14 | 16 | 16 | 17 | 19 | 20 | 21 | 22 | 23 | 24 | 25 | 26 | 27
 * 6 | 11 | 11 | 15 | 15 | 15 | 18 | 19 | 20 | 22 | 22 | 23 | 25 | 26 | 27 | 28 | 29 | 30 | 31 | 32
 * 6 | 11 | 11 | 15 | 15 | 15 | 18 | 19 | 20 | 22 | 22 | 23 | 25 | 26 | 27 | 28 | 29 | 30 | 31 | 32
 * 
 * Resultado: [32]
 */
public class Principal {

	public static Integer capacidade = 20;
	public static Integer[] listaTamanho = new Integer[11];
	public static Integer[] listaPrioridade = new Integer[11];
	public static Integer[][] listaDados = new Integer[listaTamanho.length][capacidade+1];
	
	
	/**
	 * LOOP PRINCIPAL DO PROGRAMA. CHAMA DAS OUTRAS FUN��ES
	 * @param args
	 */
	public static void main(String[] args) {
		inicializaArray();
		processaAlgoritmo();
		imprimeResultado();
	}
	
	/**
	 * INICIALIZA A LISTA DE TAMANHOS E PRIORIDADES DOS ITENS
	 */
	public static void inicializaArray() {
		listaTamanho[1] = 3;
		listaTamanho[2] = 5;
		listaTamanho[3] = 4;
		listaTamanho[4] = 6;
		listaTamanho[5] = 2;
		listaTamanho[6] = 7;
		listaTamanho[7] = 1;
		listaTamanho[8] = 3;
		listaTamanho[9] = 1;
		listaTamanho[10] = 4;
		
		listaPrioridade[1] = 3;
		listaPrioridade[2] = 5;
		listaPrioridade[3] = 4;
		listaPrioridade[4] = 7;
		listaPrioridade[5] = 4;
		listaPrioridade[6] = 2;
		listaPrioridade[7] = 5;
		listaPrioridade[8] = 3;
		listaPrioridade[9] = 6;
		listaPrioridade[10] = 2;
		
		return;
	}
	
	/**
	 * CALCULA O ALGORITMO DE PROGRAMA��O DIN�MICA PARA O PROBLEMA PROPOSTO
	 */
	public static void processaAlgoritmo() {
		// INICIALIZA A PRIMEIRA LINHA DA MATRIZ COM ZEROS
		for (int i = 0; i <= capacidade; i++) {
			listaDados[0][i] = 0;
		}
		// INICIALIZA A PRIMEIRA COLUNA DA MATRIZ COM ZEROS
		for (int i = 0; i < listaTamanho.length; i++) {
			listaDados[i][0] = 0;
		}
		
		Integer novoValor = 0;
		for (int i = 1; i < listaTamanho.length; i++) {
			for (int j = 1; j <= capacidade; j++) {
				listaDados[i][j] = listaDados[i-1][j];
				if (listaTamanho[i] <= j) {
					novoValor = listaDados[i-1][j-listaTamanho[i]]+listaPrioridade[i];
					if (novoValor > listaDados[i][j]) {
						listaDados[i][j] = novoValor;
					}
				}
			}
		}
		
		return;
	}
	
	public static void imprimeResultado() {
		String resultado = "";
		String pipe = "";
		System.out.println("Matriz completa:");
		for (int i = 1; i < listaTamanho.length; i++) {
			resultado = "";
			pipe = "";
			for (int j = 1; j <= capacidade; j++) {
				resultado += pipe + "" + listaDados[i][j];
				pipe = " | ";
			}
			System.out.println(resultado);
		}
		
		System.out.println("");
		System.out.println("Resultado: ["+listaDados[listaTamanho.length-1][capacidade]+"]");
		return;
	}
}

package conway;

import java.util.Scanner;

public class JuegoVida {

	private int[][] currentState; // declarar
	private int generation;

	public JuegoVida() {

	}

	public void runGame() throws Exception {
		final String RUTA_LECTURA = "Ficheros/Libro.csv";

		int[][] valores = CSVFileManager.leerDeFichero(RUTA_LECTURA);

		// imprimo los valores leidos
		for (int i = 0; i < valores.length; i++) {
			for (int k = 0; k < valores[i].length; k++) {
				System.out.format("%2d", valores[i][k]);
			}
			System.out.println();

		}
		currentState = setMatrizBoard(valores);

		imprimirBoardGrid(currentState);
		// pruebas de los vecinos vicos
		System.out.println(getNumeroVecinosVivos(0, 0));
		System.out.println(getNumeroVecinosVivos(1, 1));
		System.out.println(getNumeroVecinosVivos(2, 2));
		System.out.println(getNumeroVecinosVivos(3, 3));
		System.out.println(getNumeroVecinosVivos(4, 4));
		System.out.println(getNumeroVecinosVivos(5, 3));
		System.out.println(getNumeroVecinosVivos(7, 1));
		System.out.println(getNumeroVecinosVivos(8, 0));

		printIntroduction();

		getGeneration();
		for (int i = 1; i <= generation; i++) {
			System.out.println("=====     GENERATION " + i + "     =====");

			Thread.sleep(500);

			applyGameRules();
			imprimirBoardGrid(currentState);
		}

		// final String RUTA_ESCRITURA = "Ficheros/Libro_copy.csv";
		final String RUTA_ESCRITURA = "Ficheros/Libro.csv";
		// y ahora copio los valores de matrizBoard en el fichero
		boolean resultado = CSVFileManager.escribirAFichero(currentState, RUTA_ESCRITURA);

		System.out.println(resultado ? "Operación de escritura correcta" : "Operación de escritura fallida");

		imprimirBoardGrid(currentState);

	}

	/**
	 * imprima la introducci�n
	 */
	private void printIntroduction() {
		System.out.println("\t Conway's game of life SIMULATOR.");
		System.out.println("Las reglas del juego son los siguientes: ");
		System.out.println("1. Cualquier c�lula con menos de 2 vecinos muere en la siguiente generaci�n por soledad.\n"
				+ "2.    Cualquier c�lula que tenga 2 � 3 vecinos sobrevive en la siguiente generaci�n.\n"
				+ "3.    Cualquier c�lula con m�s de 3 vecinos muere en la siguiente generaci�n por sobrepoblaci�n.\n"
				+ "4.    En cualquier celda vac�a que est� rodeada exactamente de 3 c�lulas, nace por �generaci�n "
				+ "espont�nea� una nueva c�lula en la generaci�n siguiente.");

	}

	/**
	 * Conseguir el n�mero de simulaciones que el usuario quiere simular
	 * 
	 */
	private void getGeneration() {
		System.out.print("Cuantos generaciones quieres simular?: ");
		generation = readInteger();
	}

	/**
	 * Leer un entero desde teclado (standard input)
	 * 
	 * @return
	 */
	private int readInteger() {
		boolean correctInput = false;
		Scanner scanner = new Scanner(System.in);
		int integer = 0;
		while (!correctInput) {
			try {
				integer = scanner.nextInt();
				correctInput = true;
			} catch (Exception e) {
				System.out.println(e.getMessage());
				System.out.print("Input error. Please try again: ");
				scanner.nextLine();
			}
		}
		scanner.close();
		return integer;
	}

	/**
	 * M�todo para rellenar la currentState con los valores que hemeos leydo desde
	 * fichero de tipo csv
	 * 
	 * @param valores matriz bidimensional que es el resuldado del metodo
	 *                leerDeFichero()
	 * @return currentState con contenido del fichero que hemos leydo (son valores
	 *         de 0 y 1)
	 */
	public int[][] setMatrizBoard(int valores[][]) {
		currentState = new int[valores.length][valores[0].length]; // iniciar
		for (int i = 0; i < valores.length; i++) {
			for (int j = 0; j < valores[0].length; j++) {

				currentState[i][j] = valores[i][j];

			}

		}
		return currentState;
	}

	/**
	 * M�todo de tipo void para imprimir el contenido de la currentState de la clase
	 * en funci�n si es viva o no
	 */

	public void imprimirBoardGrid(int[][] grid) {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j] == 1)
					System.out.print("*");
				else if (grid[i][j] == 0)
					System.out.print(" ");
			}
			System.out.println();
		}
	}

	/**
	 * Obtener el numero de los vecinos de una celda
	 * 
	 * @param i Coordinate X
	 * @param j Coordinate Y
	 * @return Returns el numero de los vecinos
	 */

	private int getNumeroVecinosVivos(int i, int j) {
		int numberOfNeighbours = 0;
		try {
			if (currentState[i - 1][j - 1] == 1)
				numberOfNeighbours++;
		} catch (Exception ignored) {
		}
		;

		try {
			if (currentState[i - 1][j] == 1)
				numberOfNeighbours++;
		} catch (Exception ignored) {
		}
		;

		try {
			if (currentState[i - 1][j + 1] == 1)
				numberOfNeighbours++;
		} catch (Exception ignored) {
		}
		;

		try {
			if (currentState[i][j + 1] == 1)
				numberOfNeighbours++;
		} catch (Exception ignored) {
		}
		;

		try {
			if (currentState[i + 1][j + 1] == 1)
				numberOfNeighbours++;
		} catch (Exception ignored) {
		}
		;

		try {
			if (currentState[i + 1][j] == 1)
				numberOfNeighbours++;
		} catch (Exception ignored) {
		}
		;

		try {
			if (currentState[i + 1][j - 1] == 1)
				numberOfNeighbours++;
		} catch (Exception ignored) {
		}
		;

		try {
			if (currentState[i][j - 1] == 1)
				numberOfNeighbours++;
		} catch (Exception ignored) {
		}
		;

		return numberOfNeighbours;
	}

	/**
	 * M�todo para establecer los ciclos de la matriz currentState . Si la celda es
	 * viva y tiene 0 or 1 vecinos vivos, en el ciclo siguiente, desparesca por
	 * soledad; Si la celda es viva y tine 2 or 3 vecinos vivos, en el ciclo
	 * siguiente, viva; Si la celda es viva y tiene mas de 3 vecinos vivos, en el
	 * ciclo siguiente, desparesca por sobrepoblaci�n; Si la celda no es viva pero
	 * tiene exactamente 3 vecinos vivos, en el ciclo siguiente, nace
	 * 
	 */
	public int[][] applyGameRules() {
		int newBoard[][] = new int[currentState.length][currentState[0].length];
		for (int i = 0; i < currentState.length; i++) {
			for (int j = 0; j < currentState[0].length; j++) {
				int vecinosVivos = getNumeroVecinosVivos(i, j);
				if (currentState[i][j] == 1) {
					if (vecinosVivos < 2) {
						newBoard[i][j] = 0;

					} else if (vecinosVivos == 2 || vecinosVivos == 3) {
						newBoard[i][j] = 1;

					} else if (vecinosVivos > 3) {
						newBoard[i][j] = 0;

					}

				} else if (currentState[i][j] == 0) {
					if (vecinosVivos == 3) {
						newBoard[i][j] = 1;

					}

				}

			}
		}
		return currentState = newBoard;
	}

}

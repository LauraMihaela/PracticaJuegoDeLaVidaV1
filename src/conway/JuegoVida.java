package conway;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class JuegoVida {
	static final String SEPARADOR = ";";

	static final int FILAS = 50;
	static final int COLUMNAS = 50;
	static int[][] matrizBoard = new int[FILAS][COLUMNAS];
	
	

	/**
	 * Método para leer un fichero CSV que contiene valores enteros Usando la clase
	 * BufferedReader
	 * 
	 * @param ruta nombre y ruta completa del fichero al que escribimos
	 * @return tabla o array de 2-D con los valores enteros leídos del fichero
	 */
	static int[][] leerDeFichero(String ruta) {

		int[][] valores;
		int[] valores_fila;
		String linea;
		String[] elementos;

		try (BufferedReader lector = new BufferedReader(new FileReader(ruta))) {

			ArrayList<String> lineas = new ArrayList<>();
			// leemos primera línea
			linea = lector.readLine();
			while (linea != null) {
				// agregamos a la lista y leemos la siguiente línea
				lineas.add(linea.trim());
				linea = lector.readLine();
			} // fin del recorrido del fichero

			/*
			 * ahora sabemos cuántas líneas tenemos, que coincide con las filas de la matriz
			 * La segunda dimensión la dejamos sin definir
			 */
			valores = new int[lineas.size()][];

			int n_fila = 0;

			for (String fila : lineas) {
				// separo los valores de la fila leída
				elementos = fila.split(SEPARADOR);
				// ahora podemos dimensionar esta fila
				valores_fila = new int[elementos.length];
				for (int k = 0; k < elementos.length; k++) {
					valores_fila[k] = Integer.parseInt(elementos[k]);
				}
				// y agrego la fila en la posición correspondiente
				valores[n_fila] = valores_fila;
				n_fila++; // incremento el contador de la fila
			}
			return valores;

		} catch (IOException e) {
			System.out.println("Se produjo el siguiente error al acceder al fichero \n " + e.getMessage());
			return null;
		} catch (NumberFormatException e) {
			System.out.println("Revise el fichero porque tiene valores que no pueden convertirse a enteros");
			return null;
		}

	} // fin del método

	/**
	 * Método para rellenar la MatrizBoard con los valores que hemeos leydo desde
	 * fichero de tipo csv
	 * 
	 * @param valores matriz bidimensional que es el resuldado del metodo
	 *                leerDeFichero()
	 * @return matrizBoard con contenido del fichero que hemos leydo (son valores de
	 *         0 y 1)
	 */
	public static int[][] setMatrizBoard(int valores[][]) {

		for (int i = 0; i < matrizBoard.length; i++) {
			for (int j = 0; j < matrizBoard[0].length; j++) {

				matrizBoard[i][j] = valores[i][j];

			}

		}
		return matrizBoard;
	}

	/**
	 * Método de tipo void para imprimir el contenido de la matrizBoard de la clase
	 * en función si es viva o no
	 */
	public static void imprimirBoard() {

		for (int i = 0; i < FILAS; i++) {
			String celda = "";
			for (int j = 0; j < COLUMNAS; j++) {
				// No es viva
				if (matrizBoard[i][j] == 0) {
					celda += " ";
					// es viva
				} else if (matrizBoard[i][j] == 1) {
					celda += "*";
				}
			}

			System.out.println(celda);
		}

	}

	/**
	 * Método para contar las celulas vivas de una celda de la matrizBoard
	 * 
	 * @param i posición de la celda, el numero de FILA
	 * @param j posición de la celda, el numero de COLUMNA
	 * @return el número de los vecinos vivos
	 * @see getState(i,j) ;
	 */
	public static int countVecinosVivos(int i, int j) {
		int count = 0;
		// los valores son 0 or 1..entonces cuando encuentra 1, cumula con valor
		// inicial..
		// voy a tener error IndexOutOfBound si i=0 (0-1 devuelve negativo)
		// voy a utilizar getState()

		count += getState(i - 1, j - 1);
		count += getState(i, j - 1);
		count += getState(i + 1, j - 1);

		count += getState(i - 1, j);
		count += getState(i + 1, j);

		count += getState(i - 1, j + 1);
		count += getState(i, j + 1);
		count += getState(i + 1, j + 1);

		return count;
	}

	/**
	 * Método para establecer si la celda es vina o no
	 * 
	 * @param i posición de la celda, el numero de FILA
	 * @param j posición de la celda, el numero de COLUMNA
	 * @return int 0 en caso IndexOutOfBound OR el el contenido de la celda 0-1
	 */
	public static int getState(int i, int j) {
		if (i < 0 || i >= FILAS) {
			return 0;
		}
		if (j < 0 || j >= COLUMNAS) {
			return 0;

		}
		return matrizBoard[i][j];

	}

	/**
	 * Método para establecer los ciclos de la matrizBoard de la clase . Si la celda
	 * es viva y tiene 0 or 1 vecinos vivos, en el ciclo siguiente, desparesca por
	 * soledad; Si la celda es viva y tine 2 or 3 vecinos vivos, en el ciclo
	 * siguiente, viva; Si la celda es viva y tiene mas de 3 vecinos vivos, en el
	 * ciclo siguiente, desparesca por sobrepoblación; Si la celda no es viva pero
	 * tiene exactamente 3 vecinos vivos, en el ciclo siguiente, nace
	 * 
	 */
	public static int [][] etapa() {
		int newBoard[][] = new int[FILAS][COLUMNAS];
		for (int i = 0; i < matrizBoard.length; i++) {
			for (int j = 0; j < matrizBoard[0].length; j++) {
				int vecinosVivos = countVecinosVivos(i, j);
				if (getState(i, j) == 1) {
					if (vecinosVivos < 2) {
						newBoard[i][j] = 0;

					} else if (vecinosVivos == 2 || vecinosVivos == 3) {
						newBoard[i][j] = 1;

					} else if (vecinosVivos > 3) {
						newBoard[i][j] = 0;

					}

				} else if (getState(i, j) == 0) {
					if (vecinosVivos == 3) {
						newBoard[i][j] = 1;

					}

				}

			}
		}
		return matrizBoard = newBoard;
	}
/**
 * Método para poner en marcha las etapas del JuegoVida
 * @param numeroVecez entero con el número de veces que queremos correr
 */
	public static void run (int numeroVecez) {
		try {
			for (int i = 0; i <numeroVecez ; i++) {

				Thread.sleep(1000);

				etapa();
				imprimirBoard();
			}
		} catch (Exception e) {

			System.out.println(e);
		}
	

	}
	
	/**
	 * Método para escribir una tabla - array 2D- de valores enteros en un fichero
	 * CSV Usamos la clase BufferedWriter
	 * 
	 * @param valores array de 2D con los valores enteros que queremos escribir
	 * @param ruta    nombre y ruta completa del fichero al que escribimos
	 * @return Verdadero si se puede escribir sin errores, Falso si hay algÃºn error
	 */
	static boolean escribirAFichero(int[][] valores, String ruta) {

		String linea;

		try (BufferedWriter escritor = new BufferedWriter(new FileWriter(ruta))) {
			// abrimos el fichero en modo "destructivo"
			// recorrido del array por filas
			for (int i = 0; i < valores.length; i++) {
				linea = "";
				for (int k = 0; k < valores[i].length - 1; k++) {
					linea = linea + Integer.toString(valores[i][k]) + SEPARADOR;
					// linea=linea+valores[i][k]+SEPARADOR; //mÃ¡s conciso
				}
				// el Ãºltimo elemento lo agregamos sin el carÃ¡cter separador
				linea = linea + Integer.toString(valores[i][valores[i].length - 1]);
				// y agregamos la nueva lÃ­nea al fichero, aÃ±adiendo un salto de lÃ­nea
				escritor.write(linea);
				// si no estamos en la Ãºltima linea...
				if (i != valores.length - 1) {
					escritor.newLine();
				}

			} // fin de recorrido de lÃ­neas
			return true;

		} catch (IOException e) {
			System.out.println("Se produjo el siguiente error al acceder al fichero \n " + e.getMessage());
			return false;

		}
		// no necesitamos finally al haber usado la estructura try-resources
	} // fin del método de escritura
	

}

package conway;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CSVFileManager {
	static final String SEPARADOR = ";";


	

	/**
	 * M�todo para leer un fichero CSV que contiene valores enteros Usando la clase
	 * BufferedReader
	 * 
	 * @param ruta nombre y ruta completa del fichero al que escribimos
	 * @return tabla o array de 2-D con los valores enteros le�dos del fichero
	 */
	static int[][] leerDeFichero(String ruta) {

		int[][] valores;
		int[] valores_fila;
		String linea;
		String[] elementos;

		try (BufferedReader lector = new BufferedReader(new FileReader(ruta))) {

			ArrayList<String> lineas = new ArrayList<>();
			// leemos primera l�nea
			linea = lector.readLine();
			while (linea != null) {
				// agregamos a la lista y leemos la siguiente l�nea
				lineas.add(linea.trim());
				linea = lector.readLine();
			} // fin del recorrido del fichero

			/*
			 * ahora sabemos cu�ntas l�neas tenemos, que coincide con las filas de la matriz
			 * La segunda dimensi�n la dejamos sin definir
			 */
			valores = new int[lineas.size()][];

			int n_fila = 0;

			for (String fila : lineas) {
				// separo los valores de la fila le�da
				elementos = fila.split(SEPARADOR);
				// ahora podemos dimensionar esta fila
				valores_fila = new int[elementos.length];
				for (int k = 0; k < elementos.length; k++) {
					valores_fila[k] = Integer.parseInt(elementos[k]);
				}
				// y agrego la fila en la posici�n correspondiente
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

	} // fin del m�todo
	

	/**
	 * M�todo para escribir una tabla - array 2D- de valores enteros en un fichero
	 * CSV Usamos la clase BufferedWriter
	 * 
	 * @param valores array de 2D con los valores enteros que queremos escribir
	 * @param ruta    nombre y ruta completa del fichero al que escribimos
	 * @return Verdadero si se puede escribir sin errores, Falso si hay algún error
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
					// linea=linea+valores[i][k]+SEPARADOR; //más conciso
				}
				// el último elemento lo agregamos sin el carácter separador
				linea = linea + Integer.toString(valores[i][valores[i].length - 1]);
				// y agregamos la nueva línea al fichero, añadiendo un salto de línea
				escritor.write(linea);
				// si no estamos en la última linea...
				if (i != valores.length - 1) {
					escritor.newLine();
				}

			} // fin de recorrido de líneas
			return true;

		} catch (IOException e) {
			System.out.println("Se produjo el siguiente error al acceder al fichero \n " + e.getMessage());
			return false;

		}
		// no necesitamos finally al haber usado la estructura try-resources
	} // fin del m�todo de escritura
	
	

}

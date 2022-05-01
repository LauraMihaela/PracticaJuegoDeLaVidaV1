package conway;

public class Principal {

	public static void main(String[] args) {

		final String RUTA_LECTURA = "Ficheros/Libro.csv";

		int[][] valores = JuegoVida.leerDeFichero(RUTA_LECTURA);

		// imprimo los valores leidos
		for (int i = 0; i < valores.length; i++) {
			for (int k = 0; k < valores[i].length; k++) {
				System.out.format("%2d", valores[i][k]);
			}
			System.out.println(); 

		}
		JuegoVida.matrizBoard = JuegoVida.setMatrizBoard(valores);

		JuegoVida.imprimirBoard();
		// pruebas de los vecinos vicos
		System.out.println(JuegoVida.countVecinosVivos(0, 0));
		System.out.println(JuegoVida.countVecinosVivos(1, 1));
		System.out.println(JuegoVida.countVecinosVivos(2, 2));
		System.out.println(JuegoVida.countVecinosVivos(3, 3));
		System.out.println(JuegoVida.countVecinosVivos(4, 4));
		System.out.println(JuegoVida.countVecinosVivos(5, 3));
		System.out.println(JuegoVida.countVecinosVivos(7, 1));
		System.out.println(JuegoVida.countVecinosVivos(8, 0));

		 JuegoVida.run(5);

		final String RUTA_ESCRITURA = "Ficheros/Libro.csv";
		// y ahora  copio los valores de matrizBoard  en el fichero
		boolean resultado = JuegoVida.escribirAFichero(JuegoVida.matrizBoard, RUTA_ESCRITURA);

		System.out.println(resultado ? "Operación de escritura correcta" : "Operación de escritura fallida");

		JuegoVida.imprimirBoard();

	}

}

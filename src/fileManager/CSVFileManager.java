package fileManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CSVFileManager {
    /**
     * Abre un fichero existente
     */
    public static File openExistentCSVFile(String fileName) throws Exception{
        String directory = "./" + fileName + ".csv";
        File file = new File(directory);
        if(file.exists()){
            return file;
        } else {
            throw new Exception("El Fichero no existe");
        }
    }
    /*
     * Crea un nuevo fichero
     */
    public static File createNewCSVFile(String fileName) throws Exception {
        String directory = "./" + fileName + ".csv";
        File file = new File(directory);
        if(file.exists()){
            throw new Exception("File already exists.");
        } else {
            if (file.createNewFile()) {
                return file;
            } else throw new Exception("File could not be created. ");
        }

    }

    /**
     * Comprueba si el fichero existe
     */
    public static boolean checksIfFileExists(String fileName){
        String directory = "./" + fileName + ".csv";
        File file = new File(directory);
        if(file.exists()){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Imprima el contenido del fichero en consola
     */
    public static void printCSVFileOnTerminal(File file, String delimiter) throws FileNotFoundException {
        Scanner lineScanner = new Scanner(file);
        Scanner fieldScanner;

        while(lineScanner.hasNextLine()){
            fieldScanner = new Scanner(lineScanner.nextLine());
            fieldScanner.useDelimiter(delimiter);
            while(fieldScanner.hasNext()){
                System.out.print(fieldScanner.next() + ';');
            }
            fieldScanner.close();
            System.out.println();
        }
        lineScanner.close();
    }

    /**
     * Escriba el contenido de la matrice in a CSV file
     */
    public static void writeIntMatrixToCSVFile(File file, int[][] matrix, String delimiter) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        for(int i = 0; i < matrix.length; i++){
            StringBuilder line = new StringBuilder();
            for(int j = 0; j < matrix[i].length; j++){
                line.append(matrix[i][j]);
                line.append(";");
            }
            line.append("\n");
            fileWriter.write(line.toString());
        }
        fileWriter.close();
    }

    /**
     * Leer una matrice de enteros de un CSV file
     */
    public static int[][] readMatrixFromCSVFile(File file, String delimiter) throws Exception {
        Scanner lineScanner = new Scanner(file);
        Scanner fieldScanner;
        //we use arrayLists because we don't know the exact number of columns and rows of the file.
        //the file must have the same number of columns for each row.
        ArrayList<ArrayList<Integer>> auxiliaryMatrix = new ArrayList<ArrayList<Integer> >();
        int[][] matrix;
        boolean correctSize = true;

        int i = 0;
        while(lineScanner.hasNextLine()){
            fieldScanner = new Scanner(lineScanner.nextLine());
            fieldScanner.useDelimiter(delimiter);
            auxiliaryMatrix.add(new ArrayList<Integer>());
            while(fieldScanner.hasNext()){
                auxiliaryMatrix.get(i).add(fieldScanner.nextInt());
            }
            fieldScanner.close();
            i++;
        }
        lineScanner.close();

        // check if all the rows have the same size
        i = 1;
        while (correctSize && i < auxiliaryMatrix.size()){
            int firstRowSize = auxiliaryMatrix.get(0).size();
            if(auxiliaryMatrix.get(i).size() != firstRowSize) correctSize = false;
            i++;
        }

        if(correctSize) {
            matrix = new int[auxiliaryMatrix.size()][auxiliaryMatrix.get(0).size()];
            for(i = 0; i < auxiliaryMatrix.size(); i++){
                for(int j = 0; j < auxiliaryMatrix.get(i).size(); j++){
                    matrix[i][j] = auxiliaryMatrix.get(i).get(j);
                }
            }
            return matrix;
        }
        else throw new Exception("CSV field sizes not normalized. Todas las filas tiene que tener el mismo numero con las columnas.");
    }

}

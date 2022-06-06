package gameOfLife;

import fileManager.CSVFileManager;

import java.io.File;
import java.util.Random;
import java.util.Scanner;

public class GameOfLife {
    private int[][] currentState;
    private int generation;
	private int gridRows; 
    private int gridColumns; 
    private boolean loadFromFile; 

    public GameOfLife(){
    	loadFromFile = true; 
    }
    
    public int getGridRows() {
		return gridRows;
	}


	public void setGridRows(int gridRows) {
		this.gridRows = gridRows;
	}


	public int getGridColumns() {
		return gridColumns;
	}

	public void setGridColumns(int gridColumns) {
		this.gridColumns = gridColumns;
	}
	
	public int[][] getCurrentStateGrid(){
		return currentState; 
	}
	
	public void setGeneration(int generation) {
		this.generation = generation; 
	}
	
	public int getGenerationPropierty() {
		return generation; 
	}
    
	public void loadGame() throws Exception 
	{
		boolean fileOpened = false;
        File file = null;
        try {
            file = CSVFileManager.openExistentCSVFile("initialState");
            fileOpened = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        if(fileOpened){
            if (!loadFromFile){
                randomizeInitialState();
            } else {
                loadInitialGameStateFromCSV(file, ";");
                setGridRows(currentState.length); 
                setGridColumns(currentState[0].length); 
                printGameGridOnTerminal(currentState);
            }
        }
	}
	
    public void runGame() throws Exception {
        boolean fileOpened = false;
        File file = null;
        try {
            file = CSVFileManager.openExistentCSVFile("initialState");
            fileOpened = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        if(fileOpened){
            printIntroduction();
            int option = getMenuOption();
            if (option == 1){
                randomizeInitialState();
                System.out.println("=====     INITIAL STATE     =====");
                printGameGridOnTerminal(currentState);
            } else {
                loadInitialGameStateFromCSV(file, ";");
                System.out.println("=====     INITIAL STATE     =====");
                printGameGridOnTerminal(currentState);
            }
            getGeneration();
            for(int i = 1; i <= generation; i++){
                System.out.println("=====     GENERATION " + i + "     =====");
             
                applyGameRules();
                printGameGridOnTerminal(currentState);
                Thread.sleep(500);
              
            }

            if(CSVFileManager.checksIfFileExists("finalGeneration"))
                file = CSVFileManager.openExistentCSVFile("finalGeneration");
            else
                file = CSVFileManager.createNewCSVFile("finalGeneration");
            CSVFileManager.writeIntMatrixToCSVFile(file, currentState, ";");
        }
    }



    /**
	 * imprima la introducción
	 */
	public void printIntroduction() {
		System.out.println("\t Conway's game of life SIMULATOR.");
		System.out.println("Las reglas del juego son los siguientes: ");
		System.out.println("1. Cualquier célula con menos de 2 vecinos muere en la siguiente generación por soledad.\n"
				+ "2.    Cualquier célula que tenga 2 ó 3 vecinos sobrevive en la siguiente generación.\n"
				+ "3.    Cualquier célula con más de 3 vecinos muere en la siguiente generación por sobrepoblación.\n"
				+ "4.    En cualquier celda vacía que esté rodeada exactamente de 3 células, nace por “generación "
				+ "espontánea” una nueva célula en la generación siguiente.");

	}
    private int getMenuOption(){
        boolean correctInput = false;
        int option = 0;
        System.out.println("Quieres rellenar la matriz (currentState) con  números aleatorias o con el file?");
        System.out.println("1: Randomize. ");
        System.out.println("2: Load from file. ");
        System.out.println("Introduce your option: ");
        while(!correctInput){
            option = readInteger();
            if(option == 1 || option == 2) correctInput = true;
            else System.out.println("Incorrect option. Please try again: ");
        }
        return option;
    }
    /**
     * Conseguir el número de simulaciones que el usuario quiere simular
     */
    private void getGeneration(){
        System.out.print("Cuantos generaciones quieres simular?: ");
        generation = readInteger();
    }


    /**
     * Asks for the size of the inital board and fills it with random numbers between 0 and 1.
     */
    private void randomizeInitialState(){
        Random random = new Random();
        System.out.print("Please introduce the number of rows of the grid: ");
        int rows = readInteger();
        System.out.println("Please introduce the number of columns of the grid: ");
        int columns = readInteger();
        currentState = new int[rows][columns];

        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                currentState[i][j] = random.nextInt(2);
            }
        }

    }

    /**
     * Loads the initial state from a file.
     * @param file File from where to load the initial state
     * @param delimiter Delimiter of the CSV fields
     * @throws Exception
     */
    private void loadInitialGameStateFromCSV(File file, String delimiter) throws Exception {
        currentState = CSVFileManager.readMatrixFromCSVFile(file,delimiter);
    }

    /**
     * Prints the game grid on the terminal.
     * @param grid The game grid
     */
    public void printGameGridOnTerminal(int[][] grid){
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[0].length; j++){
                if(grid[i][j] == 1) System.out.print("1");
                else if(grid[i][j] == 0) System.out.print("0");
            }
            System.out.println();
        }
    }

    /**
     * Applies the game rules
     */
   

    
    public int[][] applyGameRules() {
		int newBoard[][] = new int[currentState.length][currentState[0].length];
		for (int i = 0; i < currentState.length; i++) {
			for (int j = 0; j < currentState[0].length; j++) {
				int vecinosVivos = getNumberOfNeighbours(i, j);
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

    /**
     * Gets the number of neighbours of a given cell
     * @param i Coordinate X
     * @param j Coordinate Y
     * @return Returns the number of neighbours
     */
    private int getNumberOfNeighbours(int i, int j){
        int numberOfNeighbours = 0;
        try{
            if(currentState[i-1][j-1] == 1) numberOfNeighbours++;
        } catch (Exception ignored){};

        try{
            if(currentState[i-1][j] == 1) numberOfNeighbours++;
        } catch (Exception ignored){};

        try{
            if(currentState[i-1][j+1] == 1) numberOfNeighbours++;
        } catch (Exception ignored){};

        try{
            if(currentState[i][j+1] == 1) numberOfNeighbours++;
        } catch (Exception ignored){};

        try{
            if(currentState[i+1][j+1] == 1) numberOfNeighbours++;
        } catch (Exception ignored){};

        try{
            if(currentState[i+1][j] == 1) numberOfNeighbours++;
        } catch (Exception ignored){};

        try{
            if(currentState[i+1][j-1] == 1) numberOfNeighbours++;
        } catch (Exception ignored){};

        try{
            if(currentState[i][j-1] == 1) numberOfNeighbours++;
        } catch (Exception ignored){};

        return numberOfNeighbours;
    }


    /**
     * Reads an integer from the standard input.
     * @return
     */
    private int readInteger(){
        boolean correctInput = false;
        Scanner scanner = new Scanner(System.in);
        int integer = 0;
        while (!correctInput){
            try{
                integer = scanner.nextInt();
                correctInput = true;
            } catch (Exception e){
                System.out.println(e.getMessage());
                System.out.print("Input error. Please try again: ");
                scanner.nextLine();
            }
        }
        scanner.close(); 
        return integer;
    }
}

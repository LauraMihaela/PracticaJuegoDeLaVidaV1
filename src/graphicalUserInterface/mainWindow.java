package graphicalUserInterface;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import javax.swing.JEditorPane;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import gameOfLife.*;
import javax.swing.JList; 

public class mainWindow {

	private static JFrame frame;
	private static int numberOfGenerations = 10; 
	private static JLabel[][] cells; 
	private static JPanel gameGridPanel;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameOfLife gof = new GameOfLife(); 
					gof.setGeneration(numberOfGenerations);
					mainWindow window = new mainWindow(gof);
					
					window.frame.setVisible(true);
					for(int i = 0; i < numberOfGenerations; i++) {
					
						gof.applyGameRules(); 
					
						drawGameState(gof, gameGridPanel);
					       Thread.sleep(500);
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public void runGame() throws Exception {
		
	}
	
	
	
	
	/**
	 * Create the application.
	 */
	public mainWindow(GameOfLife gof) {
		try {
			gof.loadGame();
		} catch (Exception e) {
            System.out.println(e.getMessage());
        }
		initialize(gof);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	 static void initialize(GameOfLife gof) {
		frame = new JFrame();
		frame.setBounds(500, 500, 850, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null); 
		 
		
	    gameGridPanel = new JPanel();
		gameGridPanel.setBounds(12, 70, 826, 581);
		frame.getContentPane().add(gameGridPanel);
		gameGridPanel.setLayout(new GridLayout(1, 1, 0, 0));
		gameGridPanel.setVisible(true);
		
		drawGameState(gof,gameGridPanel);
		
		JPanel informationPanel = new JPanel();
		informationPanel.setBounds(12, 12, 826, 46);
		frame.getContentPane().add(informationPanel);
		informationPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel generationInfoLabel = new JLabel("GENERATIONS: ");
		generationInfoLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		informationPanel.add(generationInfoLabel);
		
		JLabel generationValueLabel = new JLabel(Integer.toString(gof.getGenerationPropierty()));
		informationPanel.add(generationValueLabel);
	}
	
	private static void drawGameState(GameOfLife gof, JPanel gridPanel) {
		int[][] currentState = gof.getCurrentStateGrid(); 
		int rows = gof.getGridRows(); 
		int columns = gof.getGridColumns();
		cells = new JLabel[rows][columns];
		gridPanel.removeAll(); 
		gridPanel.setLayout(new GridLayout(rows, columns, 1, 1));
		
		
		for(int i = 0; i < currentState.length; i++) {
			for(int j = 0; j < currentState[i].length; j++) {
				cells[i][j] = new JLabel(""); 
				if(currentState[i][j] == 1) {
					cells[i][j].setBackground(Color.RED);
					cells[i][j].setOpaque(true);
					gridPanel.add(cells[i][j]); 
				} else {
					cells[i][j].setBackground(Color.WHITE);
					cells[i][j].setOpaque(true); 
					gridPanel.add(cells[i][j]); 
				}
			}
		}
		
	}
	
}

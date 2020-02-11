import java.io.FileReader;
import java.util.*;
/*
 * Created by: James Kyle Harrison
 * Date: 02/11/2020
 * Purpose: To create an algorithm that helps in solving Sudoku Puzzles as efficiently as possible.
 * Rules of Sudoku: Each row, column, and nonet contains a number (1 to 9) exactly once,
 * however not all of the data is filled in. The player must use the information given to fill all of the cells.
 */
public class SudokuReader 
{
	private int[][] board; //Value that is filled after being scanned to use in Methods
	public static final int EMPTY = 0; // Number used to represent Empty Cell
	public static final int SIZE = 9; // Size of the Sudoku Grids
	public static void main(String[] args) throws Exception
	{
		FileReader fileReader = new FileReader("input.txt");
		Scanner input = new Scanner(fileReader);
	    int[][] GRID = new int [9][9];
		
		int row=0, col=0;
		while (input.hasNext()) //Reads input file into a sudoku Grid
		{
            if(col < 9){   //colSize is size of column
                GRID[row][col]= input.nextInt();
                col++;
            }
            else{
                col=0;
                row++;
            }
        }
		input.close();
		
		SudokuReader sudoku = new SudokuReader(GRID);
		System.out.println("Sudoku Grid to Solve");
		sudoku.sudokuDisplay();
		
		// we try resolution
		if (sudoku.sudokuSolve()) {
			System.out.println("Sudoku Grid Solved");
			sudoku.sudokuDisplay();
		} else {
			System.out.println("Unsolvable");
		}
	}
	
	public SudokuReader(int[][] board) //Reads the input Scanner and inserts data into 'board'
	{
		this.board = new int[SIZE][SIZE];
		
		for (int i = 0; i < SIZE; i++) 
		{
			for (int j = 0; j < SIZE; j++) 
			{
				this.board[i][j] = board[i][j];
			}
		}
	}
	
	public void sudokuDisplay() //Displays Data in User Friendly grid
	{
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				System.out.print(" " + board[i][j]);
			}
		
			System.out.println();
		}
		
		System.out.println();
	}
	
	private boolean isInRow(int row, int number) //Checks if a possible number is already in a row
	{
		for (int i = 0; i < SIZE; i++)
			if (board[row][i] == number)
				return true;
		
		return false;
	}
	
	private boolean isInCol(int col, int number) //Checks if a possible number is already in a column
	{
		for (int i = 0; i < SIZE; i++)
			if (board[i][col] == number)
				return true;
		
		return false;
	}
	
	private boolean isInBox(int row, int col, int number) //Checks if a possible number is in its 3x3 box
	{
		int r = row - row % 3;
		int c = col - col % 3;
		
		for (int i = r; i < r + 3; i++)
			for (int j = c; j < c + 3; j++)
				if (board[i][j] == number)
					return true;
		
		return false;
	}
	
	private boolean isOk(int row, int col, int number) //Method to check if a number follows the correct logic for being inserted
	{
		return !isInRow(row, number)  &&  !isInCol(col, number)  &&  !isInBox(row, col, number);
	}
	
    public boolean sudokuSolve() // Solve method using a recursive BackTracking algorithm.
    {
    	for (int row = 0; row < SIZE; row++) 
    	{
    		for (int col = 0; col < SIZE; col++)
    		{
    			if (board[row][col] == EMPTY) // Searches for an empty cell
    		    {
    				for (int number = 1; number <= SIZE; number++) //Tries possible numbers
    				{
    					if (isOk(row, col, number)) //Checks to see if it respects logic
    					{
    						board[row][col] = number;

    						if (sudokuSolve()) //Recursive backtracking
    							{
    								return true;
    							} else  //If not a solution, empty the cell and continue search
    							{
    								board[row][col] = EMPTY;
    							}
    					}
    				}

    				return false; //Fails if not possible
    		    }
    		}
         }

         return true; //Solved if possible
	}
}

package xsudoku;

public class PencilMark {
    private static final int SIZE = 9;

    public static void main(String[] args) {
        int[][] sudoku = {
            {0,3,0,0,7,0,0,5,0},
            {5,0,0,1,0,6,0,0,9},
            {0,0,1,0,0,0,4,0,0},
            {0,9,0,0,5,0,0,6,0},
            {6,0,0,4,0,2,0,0,7},
            {0,4,0,0,1,0,0,3,0},
            {0,0,2,0,0,0,8,0,0},
            {9,0,0,3,0,5,0,0,2},
            {0,1,0,0,2,0,0,7,0}
        };
        
       
        if (solveSudoku(sudoku)) {
            System.out.println("Sudoku solved:");
            printSudoku(sudoku);
        } else {
            System.out.println("No solution exists.");
        }
    }

    // Method to solve the Sudoku puzzle using backtracking
    private static boolean solveSudoku(int[][] sudoku) {
        int row = -1;
        int col = -1;
        boolean isEmpty = true;

        // Find first empty cell in the Sudoku puzzle
        for (int i = 0; i < SIZE && isEmpty; i++) {
            for (int j = 0; j < SIZE && isEmpty; j++) {
                if (sudoku[i][j] == 0) {
                    row = i;
                    col = j;
                    isEmpty = false;
                }
            }
        }

        // If no empty cell found, Sudoku puzzle is solved
        if (isEmpty) {
            return true;
        }

        // Try placing numbers 1 to 9 in the empty cell
        for (int num = 1; num <= SIZE; num++) {
            if (isValid(sudoku, row, col, num)) {
                sudoku[row][col] = num;
                if (solveSudoku(sudoku)) {
                    return true;
                }
                sudoku[row][col] = 0; // Backtrack if placing num leads to invalid solution
            }
        }

        // No solution found for this cell
        return false;
    }

    // Method to check if a number can be placed in a cell without violating Sudoku rules
    private static boolean isValid(int[][] sudoku, int row, int col, int num) {
        // Check the row and column
        for (int i = 0; i < SIZE; i++) {
            if (sudoku[row][i] == num || sudoku[i][col] == num) {
                return false;
            }
        }

        // Check the 3x3 subgrid
        int subgridRowStart = row - row % 3;
        int subgridColStart = col - col % 3;
        for (int i = subgridRowStart; i < subgridRowStart + 3; i++) {
            for (int j = subgridColStart; j < subgridColStart + 3; j++) {
                if (sudoku[i][j] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    // Method to print the Sudoku puzzle
    private static void printSudoku(int[][] sudoku) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(sudoku[i][j] + " ");
            }
            System.out.println();
        }
    }
}

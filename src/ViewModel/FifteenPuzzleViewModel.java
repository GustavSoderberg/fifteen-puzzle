package ViewModel;

import Model.FifteenPuzzleModel;

public class FifteenPuzzleViewModel
{
    private final FifteenPuzzleModel puzzleModel;
    public final boolean startShuffled = false;

    // Constructor initializes the puzzle model
    public FifteenPuzzleViewModel()
    {
        puzzleModel = new FifteenPuzzleModel();
    }

    // Retrieves the current state of the puzzle board
    public String[][] getBoard()
    {
        return puzzleModel.getBoard();
    }

    // Retrieves the size of the puzzle board
    public int getBoardSize()
    {
        return puzzleModel.getBoardSize();
    }

    // Shuffles the puzzle board by trying to make random valid moves multiple times
    public void shuffleBoard() {
        for (int i = 0; i < 1000; i++)
        {
            int randRow = (int) (Math.random() * this.getBoardSize());
            int randCol = (int) (Math.random() * this.getBoardSize());

            if (isMoveValid(randRow, randCol))
            {
                moveTile(randRow, randCol);
            }
        }
    }

    // Checks if the puzzle is solved by comparing current board state with the goal state
    public boolean isSolved()
    {
        int count = 1;
        for (int row = 0; row < this.getBoardSize(); row++)
        {
            for (int col = 0; col < this.getBoardSize(); col++)
            {
                if (!this.getBoard()[row][col].isEmpty())
                {
                    if (!this.getBoard()[row][col].equals(String.valueOf(count)))
                    {
                        return false; // If any cell doesn't match the goal state, puzzle is not solved
                    }
                    count++;
                }
            }
        }
        return true; // Puzzle is solved if all cells match the goal state
    }

    // Moves a tile to an empty space if it's a valid move
    public boolean moveTile(int row, int col)
    {
        if (isMoveValid(row, col))
        {
            int emptyRow = -1;
            int emptyCol = -1;

            // Finding the empty cell
            for (int r = 0; r < this.getBoardSize(); r++)
            {
                for (int c = 0; c < this.getBoardSize(); c++)
                {
                    if (this.getBoard()[r][c].equals(""))
                    {
                        emptyRow = r;
                        emptyCol = c;
                    }
                }
            }

            // Check if the move is valid (next to the empty space in any direction)
            if ((Math.abs(row - emptyRow) == 1 && col == emptyCol) || (Math.abs(col - emptyCol) == 1 && row == emptyRow))
            {
                // Swap the corresponding numbers
                String temp = this.getBoard()[row][col];
                this.getBoard()[row][col] = this.getBoard()[emptyRow][emptyCol];
                this.getBoard()[emptyRow][emptyCol] = temp;

                return true; // Move successful
            }
        }
        return false; // Invalid move
    }

    // Checks if a move to a particular cell is valid and not out of bounds of the board
    public boolean isMoveValid(int row, int col) {
        return (row >= 0 && row < this.getBoardSize() &&    //Checks if the row is within the size of the board(array)
                col >= 0 && col < this.getBoardSize() &&    //Checks if the column is within the size of the board(array)
                !this.getBoard()[row][col].equals("") &&    //Checks if the move isn't equal to the empty tile ("")
                this.getBoard()[row][col] != null);         //Does a null check on the array position (TODO: Find a better way to check this, try - catch?)
    }
}

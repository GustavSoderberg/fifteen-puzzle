package Model;

public class FifteenPuzzleModel
{
    // Size of the puzzle board, the game board consisting of a nested array of strings equally sized based on boardSize
    private final int boardSize = 4;
    private final String[][] board;

    // Constructor initializes the puzzle board and sets up the initial board
    public FifteenPuzzleModel()
    {
        board = new String[boardSize][boardSize];
        initializeBoard();
    }

    // Sets up the initial configuration of the puzzle board
    private void initializeBoard()
    {
        int count = 1;
        for (int row = 0; row < boardSize; row++)
        {
            for (int col = 0; col < boardSize; col++)
            {
                if (count == boardSize * boardSize)
                {
                    board[row][col] = ""; // Last cell is empty
                }
                else
                {
                    board[row][col] = String.valueOf(count); // Fill cells with numbers in order
                    count++;
                }
            }
        }
    }

    // Returns the current state of the puzzle board
    public String[][] getBoard()
    {
        return board;
    }

    // Returns the size of the puzzle board
    public int getBoardSize() {
        return boardSize;
    }
}

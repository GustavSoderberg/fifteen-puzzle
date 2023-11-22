package View;

import ViewModel.FifteenPuzzleViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FifteenPuzzleView extends JFrame {
    private final JButton[][] puzzleButtons;
    private final FifteenPuzzleViewModel puzzleViewModel;

    // Constructor to create the board interface
    public FifteenPuzzleView(FifteenPuzzleViewModel puzzleViewModel)
    {
        this.puzzleViewModel = puzzleViewModel;

        setTitle("15 Puzzle");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel puzzlePanel = new JPanel(new GridLayout(puzzleViewModel.getBoardSize(), puzzleViewModel.getBoardSize()));
        puzzleButtons = new JButton[puzzleViewModel.getBoardSize()][puzzleViewModel.getBoardSize()];

        // Creating buttons for the puzzle tiles
        for (int row = 0; row < puzzleViewModel.getBoardSize(); row++)
        {
            for (int col = 0; col < puzzleViewModel.getBoardSize(); col++)
            {
                puzzleButtons[row][col] = new JButton();
                puzzleButtons[row][col].addActionListener(new ButtonClickListener(row, col));
                puzzlePanel.add(puzzleButtons[row][col]);
            }
        }
        puzzleButtons[puzzleViewModel.getBoardSize() - 1][puzzleViewModel.getBoardSize() - 1].setVisible(false);

        // Adds an ActionListener to the shuffle button (when clicked calls for shuffleBoard() in view model)
        JButton shuffleButton = new JButton("Shuffle");
        shuffleButton.addActionListener(e -> {
            puzzleViewModel.shuffleBoard();
            updateButtons();
        });

        add(puzzlePanel, BorderLayout.CENTER);  //Alignment of tiles
        add(shuffleButton, BorderLayout.SOUTH); //Alignment of shuffle button

        setSize(400, 400); //Size of the board
        setVisible(true);
        updateButtons();

    }

    // Update the button labels and visibility based on the current board state (nested array)
    private void updateButtons()
    {
        String[][] board = puzzleViewModel.getBoard();

        for (int row = 0; row < board.length; row++)
        {
            for (int col = 0; col < board[0].length; col++)
            {
                puzzleButtons[row][col].setText(board[row][col]); //Sets the number stored in each array position to the corresponding tile
                puzzleButtons[row][col].setVisible(!board[row][col].equals("")); //Sets all tiles visibility to the inverse value of the empty tile ("") & the empty one to false :)
            }
        }
    }

    // ActionListener for each button representing a puzzle tile
    private class ButtonClickListener implements ActionListener
    {
        private final int clickedRow;
        private final int clickedCol;

        public ButtonClickListener(int row, int col) {
            this.clickedRow = row;
            this.clickedCol = col;
        }

        //ActionEvent for each tile. Goes through the methods of the vm to make sure it's a valid move, and calls moveTile() which will update the nested array of the boards,
        //then calls to update GUI and finally calls and evaluates the board (isSolved() in vm) to see if the user has won, and displays a prompt if so
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (puzzleViewModel.isMoveValid(clickedRow, clickedCol))
            {
                if (puzzleViewModel.moveTile(clickedRow, clickedCol))
                {
                    updateButtons();
                    if (puzzleViewModel.isSolved()) {
                        JOptionPane.showMessageDialog(FifteenPuzzleView.this, "Congratulations! You won! \n Thank you for playing.");
                    }
                }
            }
        }
    }

    // Entry point to start the puzzle game, creates an instance of this class and runs it asynchronously on a separate Thread with SwingUtilities.invokeLater()
    public static void main(String[] args)
    {
        FifteenPuzzleViewModel puzzleViewModel = new FifteenPuzzleViewModel();
        SwingUtilities.invokeLater(() -> new FifteenPuzzleView(puzzleViewModel));

        if (puzzleViewModel.startShuffled)
        {
            puzzleViewModel.shuffleBoard();
        }
    }
}
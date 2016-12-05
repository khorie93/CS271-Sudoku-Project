package edu.uci.ics.cs271.SudokuSolver;

import edu.uci.ics.cs271.Sudoku.ThreeSudoku;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import static java.lang.System.out;



public class InitialBoard 
{
    public static void main(String[] args) throws FileNotFoundException
    {
        ThreeSudoku puzz = new ThreeSudoku();
        System.out.println("Enter sequence of 81 integers ranging [0,9], "
                + "encoding the initial board position, left-to-right and top"
                + "-down, with 0 for empty squares:");
        puzz.initialize(new FileInputStream("/Users/kanabee/Downloads/benchmarks/Sudoku/sudoku.txt"));
        System.out.println();
        puzz.outputSolution(System.out);
    }   
}

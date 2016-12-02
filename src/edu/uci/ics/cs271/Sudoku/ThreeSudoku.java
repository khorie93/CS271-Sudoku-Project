package edu.uci.ics.cs271.Sudoku;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ThreeSudoku 
{
    Integer[][] puzzle = new Integer[9][9];
    
    public void initialize(InputStream in)
    {
        int value, rowVal;
        Scanner input = new Scanner(in);
        
        for (int i = 0; i < 81; i++)
        {
            value = input.nextInt();
            if (value < 0)
            {
                throw new InputMismatchException("Input value is less than 0.");
            }
            if (value > 9)
            {
                throw new InputMismatchException("Input value is greater than 9.");
            }
            rowVal = (int) Math.floor(i/9);
            if (rowVal == 0)
            {
                if (value == 0)
                {
                    puzzle[rowVal][i] = null;
                }
                else
                {
                    puzzle[rowVal][i] = value;
                }
            }
            else if (rowVal == 1)
            {
                if (value == 0)
                {
                    puzzle[rowVal][i-9] = null;
                }
                else
                {
                    puzzle[rowVal][i-9] = value;
                }
            }
            else if (rowVal == 2)
            {
                if (value == 0)
                {
                    puzzle[rowVal][i-18] = null;
                }
                else
                {
                    puzzle[rowVal][i-18] = value;
                }
            }
            else if (rowVal == 3)
            {
                if (value == 0)
                {
                    puzzle[rowVal][i-27] = null;
                }
                else
                {
                    puzzle[rowVal][i-27] = value;
                }
            }
            else if (rowVal == 4)
            {
                if (value == 0)
                {
                    puzzle[rowVal][i-36] = null;
                }
                else
                {
                    puzzle[rowVal][i-36] = value;
                }
            }
            else if (rowVal == 5)
            {
                if (value == 0)
                {
                    puzzle[rowVal][i-45] = null;
                }
                else
                {
                    puzzle[rowVal][i-45] = value;
                }
            }
            else if (rowVal == 6)
            {
                if (value == 0)
                {
                    puzzle[rowVal][i-54] = null;
                }
                else
                {
                    puzzle[rowVal][i-54] = value;
                }
            }
            else if (rowVal == 7)
            {
                if (value == 0)
                {
                    puzzle[rowVal][i-63] = null;
                }
                else
                {
                    puzzle[rowVal][i-63] = value;
                }
            }
            else if (rowVal == 8)
            {
                if (value == 0)
                {
                    puzzle[rowVal][i-72] = null;
                }
                else
                {
                    puzzle[rowVal][i-72] = value;
                }
            }
        }
    }
    
    public void outputInitialize(OutputStream out)
    {
        PrintStream output = new PrintStream(out);
        
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                output.print(puzzle[i][j] + " ");
            }
            output.println();
        }
    }
}

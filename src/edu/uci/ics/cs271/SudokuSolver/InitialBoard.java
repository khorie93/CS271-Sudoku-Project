package edu.uci.ics.cs271.SudokuSolver;

import java.util.Scanner;
import java.lang.Integer;
import java.lang.Math;


public class InitialBoard 
{
    public static void main(String[] args)
    {
        int value, rowVal;
        Integer[][] puzzle = new Integer[9][9];
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter sequence of 81 integers ranging [0,9], "
                + "encoding the initial board position, left-to-right and top"
                + "-down, with 0 for empty squares:");
        
        for (int i = 0; i < 81; i++)
        {
            value = keyboard.nextInt();
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
            if (rowVal == 1)
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
            if (rowVal == 2)
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
            if (rowVal == 3)
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
            if (rowVal == 4)
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
            if (rowVal == 5)
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
            if (rowVal == 6)
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
            if (rowVal == 7)
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
            if (rowVal == 8)
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
}

package edu.uci.ics.cs271.Sudoku;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ThreeSudoku 
{
    Integer[][] puzzle = new Integer[9][9];
    ArrayList<Integer[][]> myPuzzles = new ArrayList<Integer[][]>(8);
    
    public void initialize(InputStream in)
    {
        int row;
        String line;
        String[] lineArray;
        row = 0;
        Scanner input = new Scanner(in);
        while (input.hasNextLine())
        {
            line = input.nextLine();
            if (!line.equals(""))
            {
                if (line.contains(","))
                {
                    lineArray = line.split(",");
                    for (int i = 0; i < 9; i++)
                    {
                        puzzle[row][i] = Integer.parseInt(lineArray[i]);
                    }
                    row = row + 1;
                }
            }
            if (row == 9)
            {
                myPuzzles.add(puzzle);
                row = 0;
                puzzle = new Integer[9][9];
            }
        }
    }
    
    public void outputInitialize(OutputStream out)
    {
        PrintStream output = new PrintStream(out);
        
        for (int i = 0; i <8; i++)
        {
            for (int row = 0; row < 9; row++)
            {
                for (int j = 0; j < 9; j++)
                {
                    if (j == 8)
                    {
                        output.print(myPuzzles.get(i)[row][j]);
                    }
                    else
                    {
                        output.print(myPuzzles.get(i)[row][j] + ",");
                    }
                }
                output.println();
            }
            output.println();
        }
    }
}

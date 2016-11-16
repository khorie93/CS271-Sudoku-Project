package edu.uci.ics.cs271.SudokuSolver;

public class Solution 
{
    public int[] Solution(Integer[][] puzzle)
    {
        int[] values = new int[81];
        
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                if (i == 0)
                {
                    if (puzzle[i][j] == null)
                    {
                        values[j] = 0;
                    }
                    else
                    {
                        values[j] = puzzle[i][j];
                    }
                }
                else
                {
                    if (puzzle[i][j] == null)
                    {
                        values[9*i + j] = 0;
                    }
                    else
                    {
                        values[9*i + j] = puzzle[i][j];
                    }
                }
            }
        }
        return values;
    }
}
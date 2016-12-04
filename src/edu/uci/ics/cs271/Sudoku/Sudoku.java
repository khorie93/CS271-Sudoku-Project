package edu.uci.ics.cs271.Sudoku;

import java.util.HashSet;
import java.util.Set;

public class Sudoku
{
	private int size;
	private int base;

	public int board[][];

	public Sudoku(int board[][])
	{
		this.size = board.length;
		this.base = (int) Math.sqrt(size);

		this.board = new int[this.size][this.size];

		for (int i = 0; i < this.size; i++)
			for (int j = 0; j < this.size; j++)
				this.board[i][j] = board[i][j];
	}
	public Sudoku(int base)
	{
		this.base = base;
		this.size = base * base;
		
		this.board = new int[this.size][this.size];
	}
	public Sudoku clone()
	{
		return new Sudoku(this.board);
	}

	public int getSize()
	{
		return this.size;
	}

	public int getBase()
	{
		return this.base;
	}
	public static double calcPuzzDifficulty(Sudoku puzz)
	{
		double baseDiff = 0.0;
		double distDiff = 0.0;
		
		int totalUnsetSlots = 0;
		int totalSlots = puzz.size * puzz.size;

		int[] numCount = new int[puzz.size];
		
		for(int i = 0; i < puzz.size; i++)
		{
			for(int j = 0; j < puzz.size; j++)
			{
				if(puzz.board[i][j] == 0)
					totalUnsetSlots++;
				else
					numCount[puzz.board[i][j] - 1]++;
			}
		}
		
		double idealNum = (totalSlots - totalUnsetSlots) / puzz.size;
		
		for(int i = 0; i < puzz.size; i++)
			distDiff += Math.pow(idealNum - numCount[i], 2);
		distDiff = Math.sqrt(distDiff)/puzz.size;

		baseDiff = totalUnsetSlots/puzz.size;
		
		return baseDiff + distDiff;
	}

	public String toString()
	{
		StringBuilder boardString = new StringBuilder();

		for (int x = 0; x < this.size; x++)
		{
			if (x % this.getBase() == 0)
			{
				for (int i = 0; i < this.size + Math.sqrt(this.size) + 1; i++)
					boardString.append(" -");
				boardString.append("\n");
			}

			for (int y = 0; y < this.size; y++)
			{
				if (y % this.base == 0)
					boardString.append(" |");
				boardString.append(" " + this.board[x][y]);
			}
			boardString.append(" |");
			boardString.append("\n");
		}

		return boardString.toString();
	}
	public static boolean confirmSolution(Sudoku initial, Sudoku solved)
	{
		if(!Sudoku.consistentSolution(solved))
			return false;
		
		for(int i = 0; i < solved.base; i++)
			for(int j = 0; j < solved.base; j++)
				if(initial.board[i][j] != 0 && initial.board[i][j] != solved.board[i][j])
					return false;

		return true;
	}
	public static boolean consistentSolution(Sudoku solved)
	{
		Set<Integer> rowSet = new HashSet<>();
		Set<Integer> colSet = new HashSet<>();
		Set<Integer> boxSet = new HashSet<>();
		
		for(int i = 0; i < solved.size; i++)
		{
			rowSet.clear();
			colSet.clear();
			for(int j = 0; j < solved.size; j++)
			{
				// Check for duplicate on a row.
				if(!rowSet.add(solved.board[i][j]) || solved.board[i][j] == 0)
					return false;
				// Check for duplicate on a col.
				if(!colSet.add(solved.board[j][i]) || solved.board[j][i] == 0)
					return false;
			}
		}
		for(int k = 0; k < solved.size; k++)
		{
			boxSet.clear();
			int boxX = k % solved.base;
			int boxY = k / solved.base;

			for(int i = 0; i < solved.base; i++)
			{
				for(int j = 0; j < solved.base; j++)
				{
					int value = solved.board[boxX*solved.base + i][boxY*solved.base + j];
					if(!boxSet.add(value) || value == 0)
						return false;
				}
			}
		}

		return true;
	}
}

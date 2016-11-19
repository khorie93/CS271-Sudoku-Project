package edu.uci.ics.cs271.Sudoku;

public class Sudoku
{
	private int size;
	private int base;

	private int board[][];

	public Sudoku(int board[][])
	{
		this.size = board.length;
		this.base = (int) Math.sqrt(size);

		this.board = new int[this.size][this.size];

		for (int i = 0; i < this.size; i++)
			for (int j = 0; j < this.size; j++)
				this.board[i][j] = board[i][j];
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
}

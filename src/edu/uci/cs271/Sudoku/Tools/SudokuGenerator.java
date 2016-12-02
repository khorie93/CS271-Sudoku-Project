package edu.uci.cs271.Sudoku.Tools;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

import edu.uci.ics.cs271.Sudoku.Sudoku;
import edu.uci.ics.cs271.Sudoku.Solvers.SudokuSolver;
import edu.uci.ics.cs271.Sudoku.Solvers.BTSConstraint.BTSConstraint;
import edu.uci.ics.cs271.Sudoku.Solvers.SudokuSolver.InconsistentSudokuException;

public class SudokuGenerator
{
	private int base;
	private int size;
	
	public SudokuGenerator(int base)
	{
		this.base = base;
		this.size = base * base;
	}
	public Sudoku genSudokuPuzzle(float difficulty) throws InconsistentSudokuException
	{
		Sudoku blank = new Sudoku(this.base);
		SudokuSolver s = new BTSConstraint(blank);
		
		Sudoku solved = s.solve();
		
		List<Coordinate> l = new ArrayList<>();
		
		for(int i = 0; i < this.size; i++)
			for(int j = 0; j < this.size; j++)
				l.add(new Coordinate(i,j));

		Collections.shuffle(l);
		
		Queue<Coordinate> q = new ArrayDeque<>(l);
		
		int numberOfVars = size * size;
		
		for(int i = 0; i < numberOfVars - difficulty * numberOfVars; i++)
		{
			Coordinate cur = q.remove();
			solved.board[cur.x][cur.y] = 0;
		}

		return solved;
	}
	private class Coordinate
	{
		int x;
		int y;

		public Coordinate(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
	}
}

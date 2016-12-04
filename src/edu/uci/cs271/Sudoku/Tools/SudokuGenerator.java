package edu.uci.cs271.Sudoku.Tools;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

import edu.uci.ics.cs271.Sudoku.Sudoku;
import edu.uci.ics.cs271.Sudoku.Solvers.SudokuSolver;
import edu.uci.ics.cs271.Sudoku.Solvers.BTSConstraint.BTSConstraintMRV;
import edu.uci.ics.cs271.Sudoku.Solvers.SudokuSolver.InconsistentSudokuException;

public class SudokuGenerator
{
	public static Sudoku genSudokuPuzzle(int base, double d) throws InconsistentSudokuException
	{
		int size = base * base;
		Sudoku blank = new Sudoku(base);
		SudokuSolver s = new BTSConstraintMRV(blank);
		
		Sudoku solved = s.solve();
		
		List<Coordinate> l = new ArrayList<>();
		
		for(int i = 0; i < size; i++)
			for(int j = 0; j < size; j++)
				l.add(new Coordinate(i,j));

		Collections.shuffle(l);
		
		Queue<Coordinate> q = new ArrayDeque<>(l);
		
		int numberOfVars = size * size;
		
		for(int i = 0; i < numberOfVars - d * numberOfVars; i++)
		{
			Coordinate cur = q.remove();
			solved.board[cur.x][cur.y] = 0;
		}

		return solved;
	}
	private static class Coordinate
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

package edu.uci.ics.cs271.Sudoku;

import edu.uci.ics.cs271.Sudoku.Solvers.SudokuSolver;
import edu.uci.ics.cs271.Sudoku.Solvers.ConstraintSolver.ConstraintSolver;
import edu.uci.ics.cs271.Sudoku.Solvers.DFSSolver.DFSSolver;
import edu.uci.ics.cs271.Sudoku.Solvers.SudokuSolver.InconsistentSudokuException;

public class Main
{

	public static void main(String[] args)
	{
		int base = 3;
		int size = base * base;
		int[][] sudokuPuz = new int[size][size];
		
		System.out.println("Testing Sudoku Solver");
		Sudoku s = new Sudoku(sudokuPuz);
		System.out.println(s);
		
		SudokuSolver sSolver = new DFSSolver(s);
		SudokuSolver sSolver2 = new ConstraintSolver(s);

		try
		{

			long startTime = System.nanoTime();
			Sudoku solved = sSolver2.solve();
			long endTime = System.nanoTime();

			System.out.println(solved);
			System.out.println("Elapse time: " + (endTime - startTime) / 1e9 + "s");
		} catch (InconsistentSudokuException e)
		{
			e.printStackTrace();
		}

	}

}

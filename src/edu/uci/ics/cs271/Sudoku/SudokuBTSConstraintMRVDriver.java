package edu.uci.ics.cs271.Sudoku;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Random;

import edu.uci.ics.cs271.Sudoku.Solvers.SudokuSolver;
import edu.uci.ics.cs271.Sudoku.Solvers.BTSConstraint.BTSConstraintMRV;
import edu.uci.ics.cs271.Sudoku.Solvers.SudokuSolver.InconsistentSudokuException;
import edu.uci.ics.cs271.Sudoku.Tools.SudokuGenerator;

public class SudokuBTSConstraintMRVDriver
{

	private static PrintStream ps;

	public static void main(String[] args) throws FileNotFoundException, InconsistentSudokuException
	{
		int n = 100000;
		int base = 4;
		int size = base * base;

		ps = new PrintStream("./BTSConstraintMRV-4Sudoku.csv");

		long startTime;
		long endTime;
		Sudoku solved;

		for (int i = 0; i < n; i++)
		{
			Random gen = new Random(System.currentTimeMillis());
			//Sudoku puzz = SudokuGenerator.genSudokuPuzzle(base,gen.nextDouble()/2.0);
			Sudoku puzz = SudokuGenerator.genSudokuPuzzle(base,gen.nextDouble());
			//Sudoku puzz = sg.genSudokuPuzzle(0.2);
			SudokuSolver sSolver = new BTSConstraintMRV(puzz);
			double puzzDifficulty = Sudoku.calcPuzzDifficulty(puzz);

			System.out.println(puzz);
			System.out.println("BTSConstraintMRV: " + i);
			System.out.println("Puzz Difficulty : " + puzzDifficulty);

			startTime = System.nanoTime();
			solved = sSolver.solve();
			endTime = System.nanoTime();
			double elapsedTime = (endTime - startTime) / 1e6;

			ps.println("" + i + ',' + puzzDifficulty + ',' + elapsedTime + ',' + sSolver.getBacktracks());

			//System.out.println(solved);
			System.out.println("Elapse time     : " + elapsedTime + "ms");
			System.out.println("Backtracks      : " + sSolver.getBacktracks());
		}
	}

}

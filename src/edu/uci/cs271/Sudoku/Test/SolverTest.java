package edu.uci.cs271.Sudoku.Test;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

import edu.uci.ics.cs271.Sudoku.Sudoku;
import edu.uci.ics.cs271.Sudoku.Solvers.SudokuSolver;
import edu.uci.ics.cs271.Sudoku.Solvers.SudokuSolver.InconsistentSudokuException;
import edu.uci.ics.cs271.Sudoku.Tools.SudokuGenerator;
import edu.uci.ics.cs271.Sudoku.Solvers.BTSConstraint.BTSConstraint;
import edu.uci.ics.cs271.Sudoku.Solvers.BTSConstraint.BTSConstraintMRV;
import edu.uci.ics.cs271.Sudoku.Solvers.BTSConstraint.BTSConstraintMRVnMAV;

public class SolverTest
{

	@Test
	public void testSolveBTSConstraint()
	{
		int base = 3;

		Sudoku puzz;
		
		SudokuSolver s;

		for (int i = 0; i < 1000; i++)
		{
			Random gen = new Random(System.currentTimeMillis());
			try
			{
				puzz = SudokuGenerator.genSudokuPuzzle(base,gen.nextDouble());

				s = new BTSConstraint(puzz);
				
				assertTrue(Sudoku.confirmSolution(puzz,s.solve()));
			} catch (InconsistentSudokuException e)
			{
				System.err.println("Could not generate a puzzle!.");
				assertTrue(false);
			}

		}
	}
	@Test
	public void testSolveBTSConstraintMRV()
	{
		int base = 3;

		Sudoku puzz;
		
		SudokuSolver s;

		for (int i = 0; i < 1000; i++)
		{
			Random gen = new Random(System.currentTimeMillis());
			try
			{
				puzz = SudokuGenerator.genSudokuPuzzle(base,gen.nextDouble());

				s = new BTSConstraintMRV(puzz);
				
				assertTrue(Sudoku.confirmSolution(puzz,s.solve()));
			} catch (InconsistentSudokuException e)
			{
				System.err.println("Could not generate a puzzle!.");
				assertTrue(false);
			}

		}
		
	}
	@Test
	public void testSolveBTSConstraintMRVnMAV()
	{
		int base = 3;

		Sudoku puzz;
		
		SudokuSolver s;

		for (int i = 0; i < 1000; i++)
		{
			Random gen = new Random(System.currentTimeMillis());
			try
			{
				puzz = SudokuGenerator.genSudokuPuzzle(base,gen.nextDouble());

				s = new BTSConstraintMRVnMAV(puzz);
				
				assertTrue(Sudoku.confirmSolution(puzz,s.solve()));
			} catch (InconsistentSudokuException e)
			{
				System.err.println("Could not generate a puzzle!.");
				assertTrue(false);
			}

		}
	}

}

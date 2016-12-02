package edu.uci.ics.cs271.Sudoku;

import java.io.FileNotFoundException;
import java.io.PrintStream;

import edu.uci.cs271.Sudoku.Tools.SudokuGenerator;
import edu.uci.ics.cs271.Sudoku.Solvers.SudokuSolver;
import edu.uci.ics.cs271.Sudoku.Solvers.BTSConstraint.BTSConstraint;
import edu.uci.ics.cs271.Sudoku.Solvers.BTSConstraintMRV.BTSConstraintMRV;
import edu.uci.ics.cs271.Sudoku.Solvers.SudokuSolver.InconsistentSudokuException;

public class Main
{

	public static void main(String[] args) throws FileNotFoundException, InconsistentSudokuException
	{
		int n = 100;
		int base = 3;
		int size = base * base;
		int[][] sudokuPuz = new int[size][size];

		System.out.println("Testing Sudoku Solver");
		Sudoku s = new Sudoku(sudokuPuz);
		System.out.println(s);

		//SudokuSolver sSolver = new ConstraintSolver(s);
		//System.out.println(sSolver.solve());
		
		SudokuGenerator sg = new SudokuGenerator(base);
		
		Sudoku puzz = sg.genSudokuPuzzle(0.25f);
		System.out.println(puzz);

		PrintStream conFileStream= new PrintStream("./constraint.csv");
		PrintStream dfsFileStream = new PrintStream("./dfs.csv");

		for (int i = 0; i < n; i++)
		{
			try
			{
				//System.out.println(s);

				SudokuSolver sSolver = new BTSConstraint(puzz);
				long startTime = System.nanoTime();
				Sudoku solved = sSolver.solve();
				long endTime = System.nanoTime();
				double elapsedTime = (endTime - startTime) / 1e6;
				
				dfsFileStream.println("" + i + ',' + elapsedTime + ',' + sSolver.getBacktracks());

				//System.out.println(solved);
				System.out.println("Iteration DFS: " + i);
				System.out.println("Elapse time: " + elapsedTime + "ms");
				System.out.println("Backtracks : " + sSolver.getBacktracks());
			} catch (InconsistentSudokuException e)
			{
				e.printStackTrace();
			}
		}
		for (int i = 0; i < n; i++)
		{
			try
			{
				SudokuSolver sSolver = new BTSConstraint(puzz);

				long startTime = System.nanoTime();
				Sudoku solved = sSolver.solve();
				long endTime = System.nanoTime();
				double elapsedTime = (endTime - startTime) / 1e6;

				conFileStream.println("" + i + ',' + elapsedTime + ',' + sSolver.getBacktracks());

				//System.out.println(solved);
				System.out.println("Iteration Constraint: " + i);
				System.out.println("Elapse time: " + elapsedTime + "ms");
				System.out.println("Backtracks : " + sSolver.getBacktracks());
			} catch (InconsistentSudokuException e)
			{
				e.printStackTrace();
			}
		}
		conFileStream.close();
		dfsFileStream.close();
		

		/*
		 * List<Integer> l = new DoublyLinkedList<>();
		 * 
		 * for(int i = 0; i < 10; i++) l.add(i);
		 * 
		 * l.remove(5);
		 * 
		 * Heap<Integer> fh = new MinArrayHeap<>(100);
		 * 
		 * for(int i = 9; i >= 4; i--) fh.add(i);
		 * 
		 * System.out.println(fh);
		 * 
		 * while(!fh.isEmpty()) System.out.println("Smallest:" + fh.remove());
		 */

	}

}

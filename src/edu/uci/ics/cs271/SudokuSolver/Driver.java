package edu.uci.ics.cs271.SudokuSolver;

public class Driver
{
	public static void main(String[] args)
	{
		Sudoku s = new Sudoku(3);

		System.out.println("Testing Sudoku");
		System.out.println(s);

		long startTime = System.nanoTime();
		boolean success = Sudoku.solveDFS(s);
		long endTime = System.nanoTime();

		if(success)
			System.out.println("solved!");
		else
			System.out.println("Failure!");

		System.out.println("Testing Solve");
		System.out.println(s);

		System.out.println("Elapse time: " + (endTime - startTime) / 1e9 + "s");
	}
	/*
	 * public static String nanoTimeToStr(long nano) { hours = nano / (1e06 * 60
	 * * 60);
	 * 
	 * return "" + nano / (1e06 * 60 * 60) + "h " + nano % (1e06 * 60)"m " +
	 * nano % (1e06)"s " + nano % (1e03)"ms " + nano "s "; }
	 */
}

package edu.uci.ics.cs271.Sudoku;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.uci.ics.cs271.Sudoku.Solvers.SudokuSolver;
import edu.uci.ics.cs271.Sudoku.Solvers.SudokuSolver.InconsistentSudokuException;
import edu.uci.ics.cs271.Sudoku.Solvers.BTSConstraint.BTSConstraint;
import edu.uci.ics.cs271.Sudoku.Solvers.BTSConstraint.BTSConstraintMRV;
import edu.uci.ics.cs271.Sudoku.Solvers.BTSConstraint.BTSConstraintMRVnMAV;

public class ThreeSudoku
{
	static List<Sudoku> myPuzzles = new ArrayList<>();
	static List<Sudoku> mySolvedPuzzles = new ArrayList<>();

	static int n = 10000;

	private static Scanner input;

	public static void main(String[] args)
	{
		if (args.length != 1)
		{
			System.err.println("Incorrect Usage!");
			System.err.println("Usage: java ThreeSudokuBenchmark ./inputFile.txt");
			return;
		}

		System.out.println("Preparing to solve puzzles provided...\n");

		try
		{
			FileInputStream fis = new FileInputStream(args[0]);

			initialize(fis);

			System.out.println("" + myPuzzles.size() + " puzzles found.\n");
			System.out.println("Do to the random component of our solvers, we will solve each puzzle " + n
					+ " times and present the average runtime and backtracks.");

			for (Sudoku puzz : myPuzzles)
			{
				List<SolverResult> srList = new ArrayList<>(n);

				long startTime;
				long endTime;
				Sudoku solved = null;

				SudokuSolver ss = null;

				for (int j = 0; j < 3; j++)
				{
					for (int i = 0; i < n; i++)
					{
						switch (j) {
						case 0:
							ss = new BTSConstraint(puzz);
							break;
						case 1:
							ss = new BTSConstraintMRV(puzz);
							break;
						case 2:
							ss = new BTSConstraintMRVnMAV(puzz);
							break;
						}

						try
						{
							startTime = System.nanoTime();
							solved = ss.solve();
							endTime = System.nanoTime();

							double runTime = (endTime - startTime) / 1e6;

							srList.add(new SolverResult(ss.getBacktracks(), runTime));
						} catch (InconsistentSudokuException e)
						{
							System.out.println("Sudoku puzzle is incosistent. No solution exists!");
							System.out.println("Will not attempt to solve again...");
							break;
						}
					}

					double avgBacktracks = 0.0;
					double avgRuntime = 0.0;

					for (SolverResult i : srList)
					{
						avgBacktracks += i.backtracks;
						avgRuntime += i.runTimeInMilli;
					}

					avgBacktracks /= srList.size();
					avgRuntime /= srList.size();

					System.out.println("Initial Puzzle:");
					System.out.println(puzz);
					System.out.println("Solved Puzzle:");
					System.out.println(solved);
					switch (j) {
					case 0:
						System.out.println("\nRunning using BTSConstraint:\n");
						break;
					case 1:
						System.out.println("\nRunning using BTSConstraintMRV:\n");
						break;
					case 2:
						System.out.println("\nRunning using BTSConstraintMRVnMAV:\n");
						break;
					}
					System.out.println("Average Backtracks: " + avgBacktracks);
					System.out.println("Average Runtime   : " + avgRuntime);
					srList.clear();
				}

			}
			outputSolution(System.out);
		} catch (FileNotFoundException e)
		{
			System.err.println("Double check the file path or name!");
			e.printStackTrace();
		}

		return;
	}

	public static void initialize(InputStream in)
	{
		int row;
		String line;
		String[] lineArray;
		row = 0;

		Sudoku puzzle = new Sudoku(3);

		input = new Scanner(in);

		while (input.hasNextLine())
		{
			line = input.nextLine();
			if (!line.equals(""))
			{
				if (line.contains(","))
				{
					lineArray = line.split(",");
					for (int i = 0; i < 9; i++)
					{
						puzzle.board[row][i] = Integer.parseInt(lineArray[i]);
					}
					row = row + 1;
				}
			}
			if (row == 9)
			{
				myPuzzles.add(puzzle);
				row = 0;
				puzzle = new Sudoku(3);
			}
		}
	}

	public static void outputSolution(OutputStream out)
	{
		PrintStream output = new PrintStream(out);

		for (Sudoku puzz : mySolvedPuzzles)
		{
			for (int row = 0; row < 9; row++)
			{
				for (int j = 0; j < 9; j++)
				{
					if (j == 8)
					{
						output.print(puzz.board[row][j]);
					} else
					{
						output.print(puzz.board[row][j] + ",");
					}
				}
				output.println();
			}
			output.println();
		}
	}

}

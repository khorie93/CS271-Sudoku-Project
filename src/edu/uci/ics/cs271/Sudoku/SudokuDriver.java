package edu.uci.ics.cs271.Sudoku;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import edu.uci.cs271.Sudoku.Tools.SudokuGenerator;
import edu.uci.ics.cs271.Sudoku.Solvers.SudokuSolver;
import edu.uci.ics.cs271.Sudoku.Solvers.SudokuSolver.InconsistentSudokuException;
import edu.uci.ics.cs271.Sudoku.Solvers.BTSConstraint.BTSConstraint;
import edu.uci.ics.cs271.Sudoku.Solvers.BTSConstraint.BTSConstraintMRV;
import edu.uci.ics.cs271.Sudoku.Solvers.BTSConstraint.BTSConstraintMRVnMAV;

public class SudokuDriver
{
	private static Scanner keyboard;
	private static DriverState state = DriverState.SIZE_SELECTION;
	private static Sudoku puzz;
	private static Sudoku solvedPuzz;
	private static SudokuSolver ss;
	
	private static int base = 3;
	
	public static void main(String[] args)
	{
		keyboard = new Scanner(System.in);

		System.out.println("Welcome to Sudoku Solver!\n");

		while (state != DriverState.EXIT)
		{
			switch (state) {
			case SIZE_SELECTION:
				state = sizeMenu();
				break;
			case MAIN_MENU:
				state = mainMenu();
				break;
			case SOLVER_CHOICE_MENU:
				state = solverChoiceMenu();
				break;
			case SOLVER_MENU:
				state = solverMenu();
				break;
			default:
				return;
			}
		}
		
		System.out.println("\nGoodbye!");
		keyboard.close();
	}
	private static DriverState sizeMenu()
	{
		System.out.println("What size Sudoku would you to use?");
		System.out.print("> ");
		
		int size = keyboard.nextInt();
		
		if(size < 2)
		{
			System.out.println("Size should be greater than 1!");
			return state;
		}
		base = size;
		return DriverState.MAIN_MENU;
	}

	private static DriverState solverChoiceMenu()
	{
		System.out.println("How would you like to solve this puzzle?");
		System.out.println("1) BTS Constraint");
		System.out.println("2) BTS Constraint with MRV");
		System.out.println("3) BTS Constraint with MRV and MAV");
		System.out.println("4) Return to main menu");
		System.out.println("0) Quit");
		System.out.print("> ");

		int option = keyboard.nextInt();

		switch (option) {
		case 0:
			return DriverState.EXIT;
		case 1:
			ss = new BTSConstraint(puzz);
			break;
		case 2:
			ss = new BTSConstraintMRV(puzz);
			break;
		case 3:
			ss = new BTSConstraintMRVnMAV(puzz);
			break;
		case 4:
			return DriverState.MAIN_MENU;
		default:
			System.out.println("INVALID INPUT!");
			return state;
		}

		return DriverState.SOLVER_MENU;
	}

	public static DriverState mainMenu()
	{
		System.out.println("What would you like to do:");
		System.out.println("1) Generate a random puzzle");
		System.out.println("2) Enter a puzzle");
		System.out.println("0) Quit");
		System.out.print("> ");

		int option = keyboard.nextInt();

		switch (option) {
		case 0:
			return DriverState.EXIT;
		case 1:
			puzz = generateRandomSudokuPuzzle();
			break;
		case 2:
			puzz = readSudokuPuzzle(keyboard);
			break;
		default:
			System.out.println("INVALID INPUT!");
			return state;
		}
		
		System.out.println("Puzzle:");
		System.out.println(puzz);
		
		return DriverState.SOLVER_CHOICE_MENU;
	}

	public static DriverState solverMenu()
	{
		System.out.println("How many times would you like to solve your puzzle?");
		System.out.print("> ");
		

		int n = keyboard.nextInt();
		
		if(n < 1)
		{
			System.out.println("Please enter a value greater than 0!");
			return state; 
		}

		List<SolverResult> runList = new ArrayList<>(n);
		
		long startTime;
		long endTime;
		double runTime;
		
		System.out.println("Starting solves...");
		
		for(int i = 0; i < n; i++)
		{
			try
			{
				startTime = System.nanoTime();
				solvedPuzz = ss.solve();
				endTime = System.nanoTime();
				runTime = (endTime - startTime) / 1e6;
				runList.add(new SolverResult(ss.getBacktracks(), runTime));
			} catch (InconsistentSudokuException e)
			{
				System.out.println("The Sudoku puzzle provided is incosistent!");
				e.printStackTrace();
			}
			ss.reset();
		}
		double totalTime = 0.0;
		long totalBacktracks = 0;

		for(SolverResult i : runList)
		{
			totalTime += i.runTimeInMilli;
			totalBacktracks += i.backtracks;
		}
		
		System.out.println("Solved Puzzle:");
		System.out.println(solvedPuzz);

		System.out.println("Total time        : " + totalTime + "ms");
		System.out.println("Average Runtime   : " + totalTime/runList.size() + "ms");
		System.out.println("Average Backtracks: " + (double)totalBacktracks/runList.size());
			
		return DriverState.SOLVER_CHOICE_MENU;
	}

	public static Sudoku readSudokuPuzzle(Scanner sc)
	{
		int size = base * base;
		int[][] puzzleBoard = new int[size][size];

		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
			{
				int val = sc.nextInt();
				if (val > size || val < 0)
					throw new InputMismatchException(
							"Read a value outside of allowed range: [0" + "-" + size + "]\nValue read: " + val);
				puzzleBoard[i][j] = sc.nextInt();
			}

		return new Sudoku(puzzleBoard);
	}

	public static Sudoku generateRandomSudokuPuzzle()
	{
		Random gen = new Random(System.currentTimeMillis());

		return SudokuGenerator.genSudokuPuzzle(base, gen.nextDouble() / 4.0 + 0.25);
	}

	private enum DriverState
	{
		SIZE_SELECTION,MAIN_MENU, SOLVER_CHOICE_MENU, SOLVER_MENU, EXIT
	}
}

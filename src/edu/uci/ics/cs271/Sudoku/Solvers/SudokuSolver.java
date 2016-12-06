package edu.uci.ics.cs271.Sudoku.Solvers;

import edu.uci.ics.cs271.Sudoku.Sudoku;
import edu.uci.ics.cs271.Sudoku.Solvers.SudokuSolver.InconsistentSudokuException;

public abstract class SudokuSolver
{
	protected int size;
	protected int base;
	protected int backtracks;
	protected int max_backtracks;
	protected Sudoku init;
	protected Sudoku isSolved;
	protected boolean solved;

	public SudokuSolver(Sudoku s)
	{
		this.size = s.getSize();
		this.base= s.getBase();
		this.solved = false;
		this.init = s.clone();
	}

	public SudokuSolver(int board[][])
	{
		this.size = board.length;
		this.base= (int)Math.sqrt(size);
		this.solved = false;
		this.init = new Sudoku(board);
	}

	public abstract Sudoku solve() throws InconsistentSudokuException;

	public String printInit()
	{
		return this.init.toString();
	}

	public boolean isSolved()
	{
		return this.solved;
	}

	public class InconsistentSudokuException extends Exception
	{
		private static final long serialVersionUID = 569574072112641717L;
	}

	public int getBacktracks()
	{
		if(!isSolved())
			return -1;

		return this.backtracks;
	}
	protected abstract void initialize(int backtracks, int max_backtracks);
	public abstract void reset();
}

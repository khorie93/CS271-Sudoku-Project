package edu.uci.ics.cs271.Sudoku.Solvers.BTSConstraint;


import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import edu.uci.ics.cs271.Sudoku.Sudoku;
import edu.uci.ics.cs271.Sudoku.Solvers.MaxSearchDepthExceeded;
import edu.uci.ics.cs271.Sudoku.Solvers.SudokuSolver;
import edu.uci.ics.cs271.Sudoku.Solvers.BTSConstraint.Slot;

public class BTSConstraint extends SudokuSolver
{
	Slot puzzleGrid[][];

	Set<Integer> rows[];
	Set<Integer> cols[];
	Set<Integer> boxs[];
	
	public BTSConstraint(Sudoku s)
	{
		super(s);
		this.initialize(0,1);
	}

	public BTSConstraint(int board[][])
	{
		super(board);
		this.initialize(0,1);
	}

	@SuppressWarnings("unchecked")
	protected void initialize(int backtracks, int max_backtracks)
	{
		int size = this.init.getSize();
		this.backtracks = backtracks;
		this.max_backtracks = max_backtracks * this.base;

		this.puzzleGrid = this.genPuzzleMatrix(); 

		// this.rows = (HashSet<Integer>[]) new HashSet[this.size];
		this.rows = new HashSet[size];
		this.cols = new HashSet[size];
		this.boxs = new HashSet[size];

		for (int i = 0; i < size; i++)
		{
			this.rows[i] = new HashSet<Integer>();
			this.cols[i] = new HashSet<Integer>();
			this.boxs[i] = new HashSet<Integer>();

			for (int j = 1; j <= size; j++)
			{
				this.rows[i].add(new Integer(j));
				this.cols[i].add(new Integer(j));
				this.boxs[i].add(new Integer(j));
			}
		}

		for (int x = 0; x < size; x++)
		{
			for (int y = 0; y < size; y++)
			{
				int boxNum = x / this.init.getBase() + this.init.getBase() * (y / this.init.getBase());
				puzzleGrid[x][y] = new Slot(x, y, rows[x], cols[y], boxs[boxNum]);
			}
		}
		for (int x = 0; x < size; x++)
			for (int y = 0; y < size; y++)
				this.puzzleGrid[x][y].calcPosVals();

		for (int x = 0; x < size; x++)
		{
			for (int y = 0; y < size; y++)
			{
				if(this.init.board[x][y] != 0)
					this.puzzleGrid[x][y].setValue(this.init.board[x][y]);
			}
		}
		
	}
	private Slot[][] genPuzzleMatrix()
	{
		return new Slot[size][size];
	}

	public Sudoku solve() throws InconsistentSudokuException
	{
		Deque<Slot> q = new LinkedList<Slot>();
		this.backtracks =0;

		int size = this.init.getSize();

		for (int x = 0; x < size; x++)
			for (int y = 0; y < size; y++)
				if (this.puzzleGrid[x][y].getValue() == null)
					q.add(this.puzzleGrid[x][y]);

		try
		{
			if (!this.solve(q))
				throw new InconsistentSudokuException();
		} catch (MaxSearchDepthExceeded e)
		{
			this.initialize(this.backtracks, this.max_backtracks);
			return this.solve();
		}
		solved = true;
		
		return getSolved();
	}

	private boolean solve(Deque<Slot> q) throws MaxSearchDepthExceeded
	{
		if (q.isEmpty())
			return true;

		Slot cur = q.pop();
		List<Integer> posVals = cur.getPosVals();
		Collections.shuffle(posVals);

		for (Integer i : posVals)
		{
			cur.setValue(i);
			if (solve(q))
				return true;
			cur.setValue(null);
		}
		
		if(backtracks > this.max_backtracks)
			throw new MaxSearchDepthExceeded();

		q.push(cur);
		this.backtracks++;
		return false;
	}

	public Sudoku getSolved() throws InconsistentSudokuException
	{
		if (!this.isSolved())
			this.solve();

		int size = this.init.getSize();

		int[][] solved = new int[size][size];

		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				solved[i][j] = this.puzzleGrid[i][j].getValue();

		return new Sudoku(solved);
	}

	@Override
	public void reset()
	{
		this.initialize(0,1);
	}
}

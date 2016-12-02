package edu.uci.ics.cs271.Sudoku.Solvers.BTSConstraint;

import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import edu.uci.ics.cs271.Sudoku.Sudoku;
import edu.uci.ics.cs271.Sudoku.Solvers.SudokuSolver;

public class BTSConstraint extends SudokuSolver
{
	Slot puzzleGrid[][];

	Set<Integer> rows[];
	Set<Integer> cols[];
	Set<Integer> boxs[];
	
	@SuppressWarnings("unchecked")
	public BTSConstraint(Sudoku s)
	{
		super(s);

		int size = this.init.getSize();
		this.backtracks = 0;

		this.puzzleGrid = new Slot[size][size];

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
	}

	public BTSConstraint(int board[][])
	{
		super(board);
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

		if (!this.solve(q))
			throw new InconsistentSudokuException();
		solved = true;
		
		return getSolved();
	}

	private boolean solve(Deque<Slot> q)
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
}

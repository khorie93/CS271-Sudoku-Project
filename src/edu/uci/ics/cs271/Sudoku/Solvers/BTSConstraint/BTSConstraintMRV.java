package edu.uci.ics.cs271.Sudoku.Solvers.BTSConstraint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import edu.uci.ics.cs271.Sudoku.Sudoku;
import edu.uci.ics.cs271.Sudoku.DataStructures.Heap;
import edu.uci.ics.cs271.Sudoku.DataStructures.MinArrayHeap;
import edu.uci.ics.cs271.Sudoku.DataStructures.PriorityQHeap;
import edu.uci.ics.cs271.Sudoku.Solvers.MaxSearchDepthExceeded;
import edu.uci.ics.cs271.Sudoku.Solvers.SudokuSolver;
import edu.uci.ics.cs271.Sudoku.Solvers.BTSConstraintMRV.Slot;

public class BTSConstraintMRV extends SudokuSolver
{
	Slot puzzleGrid[][];

	List<Slot> rows[];
	List<Slot> cols[];
	List<Slot> boxs[][];

	int[] valUsedCount;

	public BTSConstraintMRV(int[][] board)
	{
		super(board);
		this.initialize(0, this.base);
	}

	public BTSConstraintMRV(Sudoku s)
	{
		super(s);
		this.initialize(0, this.base);
	}

	@SuppressWarnings("unchecked")
	protected void initialize(int backtracks, int max_backtracks)
	{
		this.backtracks = backtracks;
		this.max_backtracks = max_backtracks * this.base;

		this.puzzleGrid = new Slot[size][size];

		this.rows = new ArrayList[this.size];
		this.cols = new ArrayList[this.size];
		this.boxs = new ArrayList[this.base][this.base];

		for (int i = 0; i < this.size; i++)
		{
			this.rows[i] = new ArrayList<Slot>();
			this.cols[i] = new ArrayList<Slot>();
			if (i % this.base == 0)
				for (int j = 0; j < this.base; j++)
					this.boxs[i / this.base][j] = new ArrayList<Slot>();
		}

		for (int x = 0; x < this.size; x++)
		{
			for (int y = 0; y < this.size; y++)
			{
				Slot s = new Slot(x, y, this.size);
				puzzleGrid[x][y] = s;
				this.rows[x].add(s);
				this.cols[y].add(s);
				this.boxs[x / this.base][y / this.base].add(s);
			}
		}

		int initCur;
		Slot cur = null;

		this.valUsedCount = new int[this.size];

		for (int x = 0; x < this.size; x++)
			for (int y = 0; y < this.size; y++)
			{
				initCur = this.init.board[x][y];

				if (initCur != 0)
				{
					cur = this.puzzleGrid[x][y];
					cur.setValue(initCur);

					int boxX = cur.getXCoordinate() / this.base;
					int boxY = cur.getYCoordinate() / this.base;

					for (Slot j : this.rows[cur.getXCoordinate()])
						j.removeFromDomain(initCur);
					for (Slot j : this.cols[cur.getYCoordinate()])
						j.removeFromDomain(initCur);
					for (Slot j : this.boxs[boxX][boxY])
						j.removeFromDomain(initCur);
					this.valUsedCount[initCur - 1]++;
				}
			}
	}

	public Sudoku solve() throws InconsistentSudokuException
	{
		Heap<Slot> q = new MinArrayHeap<>(this.size * this.size, Slot.getComparator());
		// Heap<Slot> q = new PriorityQHeap<>(Slot.getComparator());

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

		return this.getSolved();
	}

	protected List<Integer> arrangePosVals(Set<Integer> cur)
	{
		List<Integer> posVals = new ArrayList<>(cur);
		Collections.shuffle(posVals);
		return posVals;
	}

	protected boolean solve(Heap<Slot> q) throws MaxSearchDepthExceeded
	{
		if (q.isEmpty())
			return true;

		Slot cur = q.remove();

		List<Integer> posVals = this.arrangePosVals(cur.getDomain());

		int boxX = cur.getXCoordinate() / this.base;
		int boxY = cur.getYCoordinate() / this.base;

		List<Slot> undo = new ArrayList<>(this.size);
		for (Integer i : posVals)
		{
			undo.clear();
			cur.setValue(i);
			this.valUsedCount[i - 1]++;

			// Update the possible value domains for each slot.
			for (Slot j : this.rows[cur.getXCoordinate()])
				if (j.removeFromDomain(i))
				{
					q.decreaseUpdate(j);
					undo.add(j);
				}
			for (Slot j : this.cols[cur.getYCoordinate()])
				if (j.removeFromDomain(i))
				{
					q.decreaseUpdate(j);
					undo.add(j);
				}
			for (Slot j : this.boxs[boxX][boxY])
				if (j.removeFromDomain(i))
				{
					q.decreaseUpdate(j);
					undo.add(j);
				}

			if (solve(q))
				return true;

			// If we get here than the value did not work. Add it back!
			for (Slot s : undo)
			{
				s.addToDomain(i);
				q.increaseUpdate(s);
			}

			this.valUsedCount[i - 1]--;
			cur.setValue(null);
		}

		if (backtracks > this.max_backtracks)
			throw new MaxSearchDepthExceeded();

		q.add(cur);

		this.backtracks++;
		return false;
	}

	public String toString()
	{
		StringBuilder boardString = new StringBuilder();

		for (int x = 0; x < this.size; x++)
		{
			if (x % this.base == 0)
			{
				for (int i = 0; i < this.size + this.base + 1; i++)
					boardString.append(" -");
				boardString.append("\n");
			}

			for (int y = 0; y < this.size; y++)
			{
				if (y % this.base == 0)
					boardString.append(" |");
				boardString.append(" " + this.puzzleGrid[x][y]);
			}
			boardString.append(" |");
			boardString.append("\n");
		}

		return boardString.toString();
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

	public int getBacktracks()
	{
		return this.backtracks;
	}

	@Override
	public void reset()
	{
		this.initialize(0, this.base);
	}
}

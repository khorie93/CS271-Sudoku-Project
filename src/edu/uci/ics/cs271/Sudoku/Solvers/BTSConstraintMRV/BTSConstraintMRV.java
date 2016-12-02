package edu.uci.ics.cs271.Sudoku.Solvers.BTSConstraintMRV;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import edu.uci.ics.cs271.Sudoku.Sudoku;
import edu.uci.ics.cs271.Sudoku.DataStructures.Heap;
import edu.uci.ics.cs271.Sudoku.DataStructures.MinArrayHeap;
import edu.uci.ics.cs271.Sudoku.DataStructures.PriorityQHeap;
import edu.uci.ics.cs271.Sudoku.Solvers.SudokuSolver;
import edu.uci.ics.cs271.Sudoku.Solvers.SudokuSolver.InconsistentSudokuException;

public class BTSConstraintMRV extends SudokuSolver
{
	Slot puzzleGrid[][];

	List<Slot> rows[];
	List<Slot> cols[];
	List<Slot> boxs[][];

	public BTSConstraintMRV(int[][] board)
	{
		super(board);
		this.initialize();
	}

	public BTSConstraintMRV(Sudoku s)
	{
		super(s);
		this.initialize();
	}

	@SuppressWarnings("unchecked")
	private void initialize()
	{
		this.backtracks = 0;
		this.puzzleGrid = new Slot[size][size];

		// this.rows = (HashSet<Integer>[]) new HashSet[this.size];
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

	}

	public Sudoku solve() throws InconsistentSudokuException
	{
		this.backtracks = 0;
		Heap<Slot> q = new MinArrayHeap<>(this.size * this.size, Slot.getComparator());
		//Heap<Slot> q = new PriorityQHeap<>(Slot.getComparator());
		
		//TreeMap<Slot, Slot> map = new TreeMap<>();

		int size = this.init.getSize();

		for (int x = 0; x < size; x++)
			for (int y = 0; y < size; y++)
				if (this.puzzleGrid[x][y].getValue() == null)
					q.add(this.puzzleGrid[x][y]);

		if (!this.solve(q))
			throw new InconsistentSudokuException();

		solved = true;

		return this.getSolved();
	}

	private boolean solve(Heap<Slot> q)
	{
		if (q.isEmpty())
			return true;

		Slot cur = q.remove();

		List<Integer> posVals = new ArrayList<>(cur.getDomain());
		Collections.shuffle(posVals);

		int boxX = cur.getXCoordinate() / this.base;
		int boxY = cur.getYCoordinate() / this.base;

		List<Slot> undo = new ArrayList<>(this.size);
		for (Integer i : posVals)
		{
			undo.clear();
			cur.setValue(i);

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

			/*
			Slot col = null;
			Slot row = null;
			Slot box = null;

			for (int j = 0; j < this.size; j++)
			{
				row = rows[cur.getXCoordinate()].get(j);
				col = cols[cur.getYCoordinate()].get(j);
				box = boxs[boxX][boxY].get(j);

				if (row.removeFromDomain(i))
					undo.add(rows[i].get(j));
				if (col.removeFromDomain(i))
					undo.add(cols[i].get(j));
				if (box.removeFromDomain(i))
					undo.add(boxs[boxX][boxY].get(j));
			}
			*/

			if (solve(q))
				return true;

			// If we get here than the value did not work. Add it back!
			for (Slot s : undo)
			{
				s.addToDomain(i);
				q.increaseUpdate(s);
			}

			cur.setValue(null);
		}

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
}

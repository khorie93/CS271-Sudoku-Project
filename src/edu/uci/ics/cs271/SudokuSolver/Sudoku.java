package edu.uci.ics.cs271.SudokuSolver;

import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class Sudoku
{
	Slot puzzleGrid[][];

	Set<Integer> rows[];
	Set<Integer> cols[];
	Set<Integer> boxs[];

	private int size;

	@SuppressWarnings("unchecked")
	public Sudoku(int n)
	{
		this.size = n * n;

		this.puzzleGrid = new Slot[this.size][this.size];

		//this.rows = (HashSet<Integer>[]) new HashSet[this.size];
		this.rows = new HashSet[this.size];
		this.cols = new HashSet[this.size];
		this.boxs = new HashSet[this.size];

		for (int i = 0; i < this.size; i++)
		{
			this.rows[i] = new HashSet<Integer>();
			this.cols[i] = new HashSet<Integer>();
			this.boxs[i] = new HashSet<Integer>();
			
			for (int j = 1; j <= this.size; j++)
			{
				this.rows[i].add(new Integer(j));
				this.cols[i].add(new Integer(j));
				this.boxs[i].add(new Integer(j));
			}
		}

		for (int x = 0; x < this.size; x++)
		{
			for (int y = 0; y < this.size; y++)
			{
				int boxNum = x / n + n * (y / n);
				puzzleGrid[x][y] = new Slot(x, y, rows[x], cols[y], boxs[boxNum]);
			}
		}
		for (int x = 0; x < this.size; x++)
			for (int y = 0; y < this.size; y++)
				this.puzzleGrid[x][y].calcPosVals();
	}

	public String toString()
	{
		StringBuilder board = new StringBuilder();
		for (int x = 0; x < this.size; x++)
		{
			if (x % Math.sqrt(this.size) == 0)
			{
				for (int i = 0; i < this.size + Math.sqrt(this.size) + 1; i++)
					board.append(" -");
				board.append("\n");
			}

			for (int y = 0; y < this.size; y++)
			{
				if (y % Math.sqrt(this.size) == 0)
					board.append(" |");
				board.append(this.puzzleGrid[x][y]);
			}
			board.append(" |");
			board.append("\n");
		}

		return board.toString();
	}

	public static boolean solveDFS(Sudoku puz)
	{
		Deque<Slot> q = new LinkedList<Slot>();

		for(int x = 0; x < puz.size; x++)
			for(int y = 0; y < puz.size; y++)
				if(puz.puzzleGrid[x][y].getValue() == null)
					q.add(puz.puzzleGrid[x][y]);


		return Sudoku.solveDFS(q);
	}
	public static boolean solveDFS(Deque<Slot> q)
	{
		if(q.isEmpty())
			return true;
		
		Slot cur = q.pop();
		List<Integer> posVals = cur.getPosVals();
		//Collections.shuffle(posVals);

		for(Integer i : posVals)
		{
			cur.setValue(i);
			if(solveDFS(q))
				return true;
			cur.setValue(null);
		}
		
		q.push(cur);
		return false;
	}

	public static boolean solveConstraintProp(Sudoku puz)
	{
		PriorityQueue<Slot> q = new PriorityQueue<>();

		for(int x = 0; x < puz.size; x++)
			for(int y = 0; y < puz.size; y++)
				if(puz.puzzleGrid[x][y].getValue() == null)
					q.add(puz.puzzleGrid[x][y]);

		return Sudoku.solveConstraintProp(q);
	}
	public static boolean solveConstraintProp(PriorityQueue <Slot> q)
	{
		if(q.isEmpty())
			return true;
		
		Slot cur = q.remove();
		List<Integer> posVals = cur.getPosVals();
		Collections.shuffle(posVals);

		for(Integer i : posVals)
		{
			cur.setValue(i);
			if(solveConstraintProp(q))
				return true;
			cur.setValue(null);
		}
		
		q.add(cur);
		return false;
	}
}

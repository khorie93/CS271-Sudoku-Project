package edu.uci.ics.cs271.Sudoku.Solvers.BTSConstraint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import edu.uci.ics.cs271.Sudoku.Sudoku;

public class BTSConstraintMRVnMAV extends BTSConstraintMRV
{
	private Comparator<Integer> comp;

	public BTSConstraintMRVnMAV(int[][] board)
	{
		super(board);
		this.comp = new CompareValues(this.valUsedCount);
		this.max_backtracks = Integer.MAX_VALUE;
	}
	public BTSConstraintMRVnMAV(Sudoku s)
	{
		super(s);
		this.comp = new CompareValues(this.valUsedCount);
		this.max_backtracks = Integer.MAX_VALUE;
	}
	@Override
	protected List<Integer> arrangePosVals(Set<Integer> cur)
	{
		List<Integer> posVals = new ArrayList<>(cur);
		//Collections.shuffle(posVals);
		Collections.sort(posVals, this.comp);
		return posVals;
	}
	private class CompareValues implements Comparator<Integer>
	{
		public int[] posValsCount;
		public CompareValues(int[] posValslCount)
		{
			this.posValsCount = posValslCount;
		}
		public int compare(Integer a, Integer b)
		{
			return this.posValsCount[b-1] - this.posValsCount[a-1];
		}
		
	}
}

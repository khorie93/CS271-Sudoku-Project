package edu.uci.ics.cs271.SudokuSolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Slot implements Comparable<Slot>
{
	private Coordinate cor;
	private Integer value;

	private Set<Integer> col;
	private Set<Integer> row;
	private Set<Integer> box;

	public Slot(int x, int y, Set<Integer> row, Set<Integer> col, Set<Integer> box)
	{
		this.cor = new Coordinate(x,y);
		this.row = row;
		this.col = col;
		this.box = box;

		this.value = null;
	}

	public List<Integer> calcPosVals()
	{
		Set<Integer> a = null;
		Set<Integer> b = null;
		Set<Integer> c = null;
		
		List<Integer> posVals = new ArrayList<>();

		if(this.row.size() >= this.col.size())
		{
			b = this.col;
			if(this.row.size() >= this.box.size())
			{
				a = this.row;
				c = this.box;
			}
			else
			{
				a = this.box;
				c = this.row;
			}
		}
		else
		{
			b = this.row;
			if(this.col.size() >= this.box.size())
			{
				a = this.col;
				c = this.box;
			}
			else
			{
				a = this.box;
				c = this.col;
			}
		}

		for (Integer i : a)
			if (b.contains(i) &&  c.contains(i))
				posVals.add(i);
		
		return posVals;
	}

	public int compareTo(Slot arg0)
	{
		return this.calcPosVals().size();
	}

	public String toString()
	{
		if (this.value == null)
			return " x";

		return " " + this.value;
	}

	public Integer getValue()
	{
		return value;
	}

	public void setValue(Integer value)
	{
		if(value == null)
		{
			this.col.add(this.value);
			this.row.add(this.value);
			this.box.add(this.value);
		}
		else
		{
			this.col.remove(value);
			this.row.remove(value);
			this.box.remove(value);
		}

		this.value = value;
	}
	private class Coordinate
	{
		public int x;
		public int y;
		
		public Coordinate(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
	}
	public List<Integer> getPosVals()
	{
		return this.calcPosVals();
	}
}

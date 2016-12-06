package edu.uci.ics.cs271.Sudoku.Solvers.BTSConstraint;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Slot extends edu.uci.ics.cs271.Sudoku.Solvers.Slot<Integer> implements Comparable<Slot>
{
	private Coordinate cor;
	private Integer value;

	private Set<Integer> col;
	private Set<Integer> row;
	private Set<Integer> box;
	
	private int size;

	public Slot(int x, int y, int size, Set<Integer> row, Set<Integer> col, Set<Integer> box)
	{
		super(null, size);

		this.cor = new Coordinate(x,y);
		this.row = row;
		this.col = col;
		this.box = box;
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
		StringBuilder s = new StringBuilder();
		
		for(int i = 0; i < Math.log10(this.size); i++)
			s.append(' ');

		if (this.value == null)
			return s.append(" 0").toString();

		return s.append(" " + this.value).toString();
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
		public String toString()
		{
			return "x:" + this.x + " y:" + this.y;
		}
	}
	public List<Integer> getPosVals()
	{
		return this.calcPosVals();
	}
}

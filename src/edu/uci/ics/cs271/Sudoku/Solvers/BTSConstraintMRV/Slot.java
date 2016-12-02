package edu.uci.ics.cs271.Sudoku.Solvers.BTSConstraintMRV;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class Slot implements Comparable<Slot>
{
	private static final SlotDomainComparator comp;
	private Coordinate cor;
	private Integer value;
	private Integer size;

	static
	{
		comp = new SlotDomainComparator();
	}

	private Set<Integer> domain;

	public Slot(int x, int y, int size)
	{
		this.size = size;
		this.cor = new Coordinate(x, y);
		this.domain = new HashSet<>(this.size);

		this.value = null;

		for (int i = 1; i <= this.size; i++)
			this.domain.add(i);
	}

	/*
	 * public List<Integer> calcPosVals() { Set<Integer> a = null; Set<Integer>
	 * b = null; Set<Integer> c = null;
	 * 
	 * List<Integer> posVals = new ArrayList<>();
	 * 
	 * if(this.row.size() >= this.col.size()) { b = this.col; if(this.row.size()
	 * >= this.box.size()) { a = this.row; c = this.box; } else { a = this.box;
	 * c = this.row; } } else { b = this.row; if(this.col.size() >=
	 * this.box.size()) { a = this.col; c = this.box; } else { a = this.box; c =
	 * this.col; } }
	 * 
	 * for (Integer i : a) if (b.contains(i) && c.contains(i)) posVals.add(i);
	 * 
	 * return posVals; }
	 */

	public int compareTo(Slot that)
	{
		return this.cor.x * this.cor.x + this.cor.y * this.cor.y;
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
		this.value = value;
	}

	public Set<Integer> getDomain()
	{
		return this.domain;
	}

	public class Coordinate
	{
		public int x;
		public int y;

		public Coordinate(int x, int y)
		{
			this.x = x;
			this.y = y;
		}

		public boolean equals(Coordinate that)
		{
			return this.x == that.x && this.y == that.y;
		}
	}

	private static class SlotDomainComparator implements Comparator<Slot>
	{
		public int compare(Slot a, Slot b)
		{
			return a.domain.size() - b.domain.size();
		}
	}

	public int getXCoordinate()
	{
		return this.cor.x;
	}

	public int getYCoordinate()
	{
		return this.cor.y;
	}

	public boolean removeFromDomain(Integer i)
	{
		return this.domain.remove(i);
	}

	public void addToDomain(Integer i)
	{
		this.domain.add(i);
	}
	public static Comparator<Slot> getComparator()
	{
		return Slot.comp;
	}
}

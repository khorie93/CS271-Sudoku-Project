package edu.uci.ics.cs271.Sudoku.DataStructures;

import java.util.Comparator;
import java.util.PriorityQueue;

public class PriorityQHeap<T extends Comparable<? extends T>> extends PriorityQueue<T> implements Heap<T>
{
	private static final long serialVersionUID = 1L;

	public PriorityQHeap()
	{
		super();
	}
	public PriorityQHeap(Comparator<T> comp)
	{
		super(comp);
	}
	public boolean update(T entry)
	{
		if(this.remove(entry) && this.add(entry))
			return true;

		return false;
	}
	public boolean remove(T entry)
	{
		return super.remove(entry);
	}
	public boolean decreaseUpdate(T entry)
	{
		return this.update(entry);
	}
	public boolean increaseUpdate(T entry)
	{
		return this.update(entry);
	}
}

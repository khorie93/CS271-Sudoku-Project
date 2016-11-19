package edu.uci.ics.cs271.Sudoku.DataStructures;

import java.util.PriorityQueue;

public class PriorityQHeap<T> extends PriorityQueue<T> implements Heap<T>
{
	private static final long serialVersionUID = 1L;

	public boolean update(T entry)
	{
		if(this.remove(entry) && this.add(entry))
			return true;

		return false;
	}
	
}

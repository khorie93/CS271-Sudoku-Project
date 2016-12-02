package edu.uci.ics.cs271.Sudoku.DataStructures;

import java.util.Comparator;

public class MinArrayHeap<T extends Comparable<T>> extends ArrayHeap<T>
{
	public MinArrayHeap(int size)
	{
		super(size);
	}

	public MinArrayHeap(int size, Comparator<T> comp)
	{
		super(size, comp);
	}

	protected boolean isParentHeap(int index)
	{
		T parent = this.arr[index];
		T lChild = null;
		T rChild = null;

		int lChildIndex = 2 * index + 1;
		int rChildIndex = lChildIndex + 1;

		if (rChildIndex < this.size)
		{
			lChild = this.arr[lChildIndex];
			rChild = this.arr[rChildIndex];
		} else if (lChildIndex < this.size)
			lChild = this.arr[lChildIndex];
		else
			return true;

		if (this.comp.compare(parent, lChild) > 0)
			return false;

		// Only child
		if (rChild == null)
			return true;

		return !(this.comp.compare(parent, rChild) > 0);
	}

	protected boolean isChildHeap(int index)
	{
		// Root!
		if (index == 0)
			return true;

		T child = this.arr[index];
		T parent = this.arr[(index - 1) / 2];

		return (this.comp.compare(parent, child) <= 0);
	}
}
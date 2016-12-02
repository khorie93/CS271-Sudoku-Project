package edu.uci.ics.cs271.Sudoku.DataStructures;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public abstract class ArrayHeap<T extends Comparable<T>> implements Heap<T>
{
	protected T[] arr;
	protected Map<T, Integer> indexMap;
	protected int size;
	protected final Comparator<T> comp;

	@SuppressWarnings("unchecked")
	public ArrayHeap(int size)
	{
		this.comp = new DefaultCompare();
		this.arr = (T[]) new Comparable[size];
		this.indexMap = new HashMap<>(size);
		this.size = 0;
	}

	@SuppressWarnings("unchecked")
	public ArrayHeap(int size, Comparator<T> comp)
	{
		this.arr = (T[]) new Comparable[size];
		this.comp = comp;
		this.indexMap = new HashMap<>(size);
		this.size = 0;
	}

	protected int heapUp(int index)
	{
		if(index == 0)
			return 0;

		int pIndex = (index - 1) / 2;
		
		T parent = this.arr[pIndex];
		T child = this.arr[index];

		while(this.comp.compare(parent, child) > 0)
		{
			this.swapParentChild(pIndex, index);
			this.indexMap.put(arr[index], index);
			index = pIndex;
			pIndex = (index - 1) / 2;
			parent = this.arr[pIndex];
			child = this.arr[index];
		}
		
		return index;
	}
	protected void swapParentChild(int pIndex, int cIndex)
	{
		T tmp = this.arr[pIndex];
		arr[pIndex] = arr[cIndex];
		arr[cIndex] = tmp;
	}

	protected int heapDown(int index)
	{
		int lChild, rChild;

		while(!this.isParentHeap(index))
		{
			lChild = 2 * index + 1;
			rChild = 2 * (index + 1);

			if(rChild >= this.size || this.comp.compare(arr[lChild], arr[rChild]) <= 0)
			{
				this.swapParentChild(index, lChild);
				this.indexMap.put(arr[index], index);
				index = lChild;
			}
			else
			{
				this.swapParentChild(index, rChild);
				this.indexMap.put(arr[index], index);
				index = rChild;
			}
		}

		return index;
	}

	protected abstract boolean isParentHeap(int index);

	protected abstract boolean isChildHeap(int index);

	public boolean add(T anEntry)
	{
		// Maybe throw an exception instead.
		if (this.size >= this.arr.length)
			return false;

		this.arr[size] = anEntry;
		this.indexMap.put(anEntry, this.heapUp(size));

		this.size++;

		return true;
	}

	public boolean isEmpty()
	{
		return this.size == 0;
	}

	public T remove()
	{
		if(this.isEmpty())
			return null;

		T ret = this.arr[0];
		this.size--;
		this.arr[0] = this.arr[this.size];
		this.arr[this.size] = null;
		this.indexMap.remove(ret);

		T tmp = this.arr[0];
		this.indexMap.put(tmp,this.heapDown(0));

		return ret;
	}

	public boolean remove(T anEntry)
	{
		if(!this.indexMap.containsKey(anEntry))
			return false;
		
		int index = this.indexMap.get(anEntry);

		return remove(index) != null;
	}
	
	protected T remove(int index)
	{
		if(index >= this.size || index < 0)
			return null;

		T rem = this.arr[index];
		
		this.size--;
		this.arr[index] = this.arr[this.size];
		this.arr[this.size] = null;

		index  = this.heapDown(index);
		this.indexMap.put(this.arr[index], index);
		this.indexMap.remove(rem);

		return rem;
	}

	public boolean update(T anEntry)
	{
		if(!this.indexMap.containsKey(anEntry))
			return false;
		
		int index = this.indexMap.get(anEntry);
		int newIndex = this.heapUp(index);

		if(index != newIndex)
		{
			this.indexMap.put(anEntry, newIndex);
			return true;
		}

		newIndex = this.heapDown(index);

		if(index != newIndex)
			this.indexMap.put(anEntry, newIndex);
		
		return true;
	}
	public boolean decreaseUpdate(T anEntry)
	{
		if(!this.indexMap.containsKey(anEntry))
			return false;
		
		int index = this.indexMap.get(anEntry);
		int newIndex = this.heapUp(index);

		if(index != newIndex)
			this.indexMap.put(anEntry, newIndex);

		return true;
	}
	public boolean increaseUpdate(T anEntry)
	{
		if(!this.indexMap.containsKey(anEntry))
			return false;

		int index = this.indexMap.get(anEntry);
		int newIndex = this.heapDown(index);

		if(index != newIndex)
			this.indexMap.put(anEntry, newIndex);
		
		return true;
	}

	private class DefaultCompare implements Comparator<T>
	{
		public int compare(T arg0, T arg1)
		{
			return arg0.compareTo(arg1);
		}

	}
	public String toString()
	{
		StringBuilder sb = new StringBuilder("[");

		for(int i = 0; i < this.size; i++) 
			sb.append(" " + this.arr[i]);
		
		sb.append("]");
		
		return sb.toString();
	}
	public int size()
	{
		return this.size;
	}
}

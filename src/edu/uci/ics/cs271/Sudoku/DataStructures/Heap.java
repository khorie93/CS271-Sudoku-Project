package edu.uci.ics.cs271.Sudoku.DataStructures;

public interface Heap<T extends Comparable<? extends T>>
{
	public boolean add(T entry);
	public T remove();
	public boolean remove(T entry);
	public boolean update(T entry);
	public boolean decreaseUpdate(T entry);
	public boolean increaseUpdate(T entry);
	public boolean isEmpty();
	public int size();
}

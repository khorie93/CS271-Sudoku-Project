package edu.uci.ics.cs271.Sudoku.DataStructures;

public interface Heap<T>
{
	public boolean add(T entry);
	public T remove();
	public boolean remove(T entry);
	public boolean update(T entry);
	public boolean isEmpty();
}

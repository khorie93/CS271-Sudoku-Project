package edu.uci.ics.cs271.Sudoku.Solvers;

public class Slot<T>
{
	protected T value;
	protected int size; // Max domain size
	
	public Slot(T value, int size)
	{
		this.value =value;
		this.size=size;
	}

	public String toString()
	{
		StringBuilder s = new StringBuilder();

		for (int i = 0; i < Math.log10(this.size); i++)
			s.append(' ');

		if (this.value == null)
			return s.append(" 0").toString();

		return s.append(" " + this.value).toString();
	}

	public T getValue()
	{
		return value;
	}

	public void setValue(T newValue)
	{
		value = newValue;
	}
}

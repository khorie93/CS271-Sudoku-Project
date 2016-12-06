package edu.uci.ics.cs271.Sudoku;

public class SolverResult
{
	int backtracks;
	double runTimeInMilli;

	public SolverResult(int b, double r)
	{
		this.backtracks = b;
		this.runTimeInMilli = r;
	}

	public String toString()
	{
		return "Time: " + this.runTimeInMilli + "ms" + "\nBacktracks: " + this.backtracks;
	}
}

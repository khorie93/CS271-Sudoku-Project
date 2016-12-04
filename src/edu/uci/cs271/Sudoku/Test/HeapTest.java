package edu.uci.cs271.Sudoku.Test;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

import edu.uci.ics.cs271.Sudoku.DataStructures.MinArrayHeap;

public class HeapTest
{

	@Test
	public void testAdd()
	{
		MinArrayHeap<Integer> heap = new MinArrayHeap<>(8);

		assertEquals(heap.size(), 0);
		assertTrue(heap.isEmpty());

		heap.add(8);
		heap.add(6);
		heap.add(3);

		assertEquals(heap.size(), 3);
	}

	@Test
	public void testRemove()
	{
		Random ran = new Random(System.currentTimeMillis());
		MinArrayHeap<Integer> heap = new MinArrayHeap<>(200);

		for (int i = 0; i < 100; i++)
			heap.add(ran.nextInt());

		int min = heap.remove();
		for (int i = 1; i < 100; i++)
		{
			int newMin = heap.remove();

			assertTrue(newMin >= min);
			min = newMin;
		}
	}

}

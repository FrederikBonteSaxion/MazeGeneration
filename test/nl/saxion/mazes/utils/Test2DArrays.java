package nl.saxion.mazes.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Test2DArrays {
	private Array2D<String> data;

	@BeforeEach
	public void createData() {
		data = new Array2D<>(String.class, 2,3);
	}

	@Test
	public void testAfterCreationDimensionsAreCorrect() {
		assertEquals(2, data.getSizeX());
		assertEquals(3, data.getSizeY());
	}

	@Test
	public void testAfterCreationAllValuesAreNull() {
		assertEmpty();
	}

	private void assertEmpty() {
		assertNull(data.get(0,0));
		assertNull(data.get(1,0));
		assertNull(data.get(0,1));
		assertNull(data.get(1,1));
		assertNull(data.get(0,2));
		assertNull(data.get(1,2));
	}

	@Test
	public void testGetOutOfBoundsReturnsNull() {
		assertNull(data.get(-1,0));
		assertNull(data.get(-1,1));
		assertNull(data.get(-1,2));
		assertNull(data.get(0,-1));
		assertNull(data.get(1,-1));
		assertNull(data.get(2,0));
		assertNull(data.get(0,3));
		assertNull(data.get(Integer.MIN_VALUE, Integer.MAX_VALUE));
	}

	@Test
	public void testSetOutOfBoundsFailsSilently() {
		data.set(-1,0, "A");
		data.set(-1,1, "B");
		data.set(-1,2, "C");
		data.set(0,-1, "D");
		data.set(1,-1, "E");
		data.set(2,0,"F");
		data.set(0,3, "G");
		data.set(Integer.MIN_VALUE, Integer.MAX_VALUE, "H");
		assertEmpty();
	}

	@Test
	public void testAfterSetValueChangesAreStored() {
		for (int i = 0; i < 3; i++) {
			data.set(0,i,"Fnork");
			data.set(1,i,"Bloep");
			assertEquals("Fnork", data.get(0,i));
			assertEquals("Bloep", data.get(1,i));
		}
	}
}

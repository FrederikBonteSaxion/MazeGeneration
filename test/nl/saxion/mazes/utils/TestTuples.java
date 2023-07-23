package nl.saxion.mazes.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestTuples {
	private Tuple<Integer, String> test;

	@BeforeEach
	public void createTestTuple() {
		test = new Tuple<>(5, "Block");
		assertNotNull(test);
	}

	@Test
	public void testTupleConstructorStoresDataCorrectly() {
		assertEquals(5, test.getLeft());
		assertEquals("Block", test.getRight());
		assertEquals("<5, Block>", test.toString());
	}

	@Test
	public void testSetLeftValueChangesStoredValue() {
		test.setLeft(-17);
		assertEquals(-17, test.getLeft());
		assertEquals("Block", test.getRight());
		assertEquals("<-17, Block>", test.toString());
	}

	@Test
	public void testSetRightValueChangesStoredValue() {
		test.setRight("Fnork");
		assertEquals(5, test.getLeft());
		assertEquals("Fnork", test.getRight());
		assertEquals("<5, Fnork>", test.toString());
	}
}

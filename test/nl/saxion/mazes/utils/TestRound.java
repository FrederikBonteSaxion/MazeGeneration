package nl.saxion.mazes.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestRound {
	@Test
	public void testRoundDown() {
		assertEquals(2, Round.round(2.49999));
		assertEquals(2, Round.round(2.4999));
		assertEquals(2, Round.round(2.499));
		assertEquals(2, Round.round(2.49));
		assertEquals(2, Round.round(2.45));
	}

	@Test
	public void testRoundUp() {
		assertEquals(3, Round.round(2.5));
		assertEquals(3, Round.round(2.5001));
		assertEquals(3, Round.round(2.6));
		assertEquals(3, Round.round(2.8));
		assertEquals(3, Round.round(2.999));
	}
}

package com.l1p.interop;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class JsonTransformerExceptionTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		JsonTransformerException e = new JsonTransformerException();
		assertTrue("no arg constructor", e != null);
	}

}

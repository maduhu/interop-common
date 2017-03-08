package com.l1p.interop.domain;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import com.l1p.interop.ilp.ledger.domain.TransferParams;

public class TransferParamsTest {

	@Test
	public void test() {
		TransferParams params = new TransferParams("event", "resource");
		String event = params.getEvent();
		String resource = (String) params.getResource();
		
		assertTrue(event != null);
		assertTrue(resource != null);
		
	}

}

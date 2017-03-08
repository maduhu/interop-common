package com.l1p.interop;

import org.junit.Test;
import static org.junit.Assert.*;

import com.l1p.interop.ilp.ledger.domain.SubscriptionResponse;

public class SubscriptionResponseTest {

	@Test
	public void testConstructor() {
		SubscriptionResponse sr = new SubscriptionResponse();
		assertTrue(sr != null);
	}
	
	
	@Test
	public void testConstructorWithArguments() {
		final int id = 23423452;
		final String jsonrpc = "SomeJson";
		final int size =27;
		SubscriptionResponse sr = new SubscriptionResponse(id, jsonrpc, size);
		
		assertEquals("id", sr.getId(), id);
		assertEquals("jsonrpc",sr.getJsonrpc(), jsonrpc);
		assertEquals("size", sr.getResult(), size);
	}
	
	
	@Test
	public void testGetters() {
		final int id = 23423452;
		final String jsonrpc = "SomeJson";
		final int size =27;
		SubscriptionResponse sr = new SubscriptionResponse();
		
		sr.setId(id);
		sr.setJsonrpc(jsonrpc);
		sr.setResult(size);
		
		assertEquals("id", sr.getId(), id);
		assertEquals("jsonrpc",sr.getJsonrpc(), jsonrpc);
		assertEquals("size", sr.getResult(), size);
	}

}

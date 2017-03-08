package com.l1p.interop.domain;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import com.l1p.interop.ilp.ledger.domain.SubscriptionParams;
import com.l1p.interop.ilp.ledger.domain.SubscriptionRequest;

public class ScriptionRequestTest {

	@Test
	public void testId() {
		SubscriptionRequest request = new SubscriptionRequest();
		request.setId(2342);
		assertEquals("id", request.getId(), 2342);
	}
	
	
	@Test
	public void testJsonrpc() {
		SubscriptionRequest request = new SubscriptionRequest();
		request.setJsonrpc("some string");
		assertEquals("setJsonrpc", request.getJsonrpc(), "some string");
	}
	
	
	@Test
	public void testMethod() {
		SubscriptionRequest request = new SubscriptionRequest();
		request.setMethod("method string");
		assertEquals("setJsonrpc", request.getMethod(), "method string");
	}
	
	
	@Test
	public void testSubscriptionParams() {
		SubscriptionRequest request = new SubscriptionRequest();
		
		List<String> accounts = new ArrayList<String>();
		accounts.add("Apple");
		accounts.add("Facebook");
		
		
		SubscriptionParams params = new SubscriptionParams();
		params.setAccounts(accounts);
		params.setEventType("Connect2017");
		
		request.setParams(params);
		SubscriptionParams paramsResponse = request.getParams();
		
		
		assertTrue("set and get params", request.getParams() != null);
		assertTrue("set and get params", paramsResponse != null);
		assertEquals("test size", params.getAccounts().size(), 2);
		assertEquals("test first account", params.getAccounts().get(0), "Apple");
		assertEquals("test second account", params.getAccounts().get(1), "Facebook");
		
	}

}

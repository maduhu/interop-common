package com.l1p.interop.domain;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.l1p.interop.ilp.ledger.domain.SubscriptionParams;

public class SubscriptionParamsTest {

	@Test
	public void testGetEvent() {
		
		SubscriptionParams params = new SubscriptionParams();
		params.setEventType("Skiing");
		
		assertEquals("test get event type", params.getEventType(), "Skiing");
	}
	
	
	@Test
	public void testGetAccounts() {
		
		List<String> accounts = new ArrayList<String>();
		accounts.add("Apple");
		accounts.add("Facebook");
		
		
		SubscriptionParams params = new SubscriptionParams();
		params.setAccounts(accounts);
		
		assertEquals("test size", params.getAccounts().size(), 2);
		assertEquals("test first account", params.getAccounts().get(0), "Apple");
		assertEquals("test second account", params.getAccounts().get(1), "Facebook");
	}

}

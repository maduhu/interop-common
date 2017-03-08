package com.l1p.interop.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.l1p.interop.ilp.ledger.domain.Debit;

public class DebitTest {
	
	private Debit d = null;
	
	@Before
	public void createDebit() {
		String account = "account";
		String amount = "2342.32";
		JsonNode memo = null;
		boolean authorized = true;
		d = new Debit(account, amount, memo, authorized);
	}

	@Test
	public void testConstructor() {
		assertTrue("debit constructor", d != null);
	}
	
	
	@Test 
	public void testIsAuthorized() {
		assertEquals("isAuthorized", d.isAuthorized(), true);
	}
	
	
	@Test 
	public void testSetAuthorized() {
		d.setAuthorized(false);
		assertEquals("set Authorized", d.isAuthorized(), false);
	}

}

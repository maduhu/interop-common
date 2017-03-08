package com.l1p.interop.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.l1p.interop.ilp.ledger.domain.Credit;

public class CreditTest {

	private String account = "account";
	private String amount = "234.23";
	private JsonNode memo = null;
	private Credit c = null;
	  
	@Before
	public void setUp() throws Exception {
		c = new Credit();
	}

	@Test
	public void testConstructor() {
		Credit c = new Credit();
		assertTrue("empty constructor", c != null);
	}
	
	
	@Test
	public void testConstuctorWithArgs() {
		Credit c = new Credit(account, amount, memo);
		assertTrue("empty constructor", c != null);
	}
	
	
	@Test
	public void testGetAccount() {
		c.setAccount(this.account);
		assertEquals("get account", c.getAccount(), this.account);
	}
	
	
	@Test
	public void testGetAmount() {
		c.setAmount(this.amount);
		assertEquals("get amount", c.getAmount(), this.amount);
	}
	
	
	@Test
	public void testGetMemo() {
		c.setMemo(memo);
		assertEquals("memo", c.getMemo(), memo);
	}

}

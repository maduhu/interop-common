package com.l1p.interop.domain;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.l1p.interop.ilp.ledger.domain.Message;

public class MessageTest {
	
	static String LEDGER = "someLedgerValue";
	static String FROM = "someFromValue";
	static String TO = "someToValue";
	static Object DATA = "someData";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLedger() {
		Message msg = new Message();
		msg.setLedger(LEDGER);
		assertEquals("Ledger", msg.getLedger(), LEDGER);
	}
	
	@Test
	public void testFrom() {
		Message msg = new Message();
		msg.setFrom(FROM);
		assertEquals("From", msg.getFrom(), FROM);
	}

	@Test
	public void testTo() {
		Message msg = new Message();
		msg.setLedger(TO);
		assertEquals("To", msg.getLedger(), TO);
	}
	
	@Test
	public void testData() {
		Message msg = new Message();
		msg.setData(DATA);
		assertEquals("Data", msg.getData(), DATA);
	}
}

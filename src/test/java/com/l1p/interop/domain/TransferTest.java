package com.l1p.interop.domain;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.l1p.interop.ilp.ledger.domain.Credit;
import com.l1p.interop.ilp.ledger.domain.Debit;
import com.l1p.interop.ilp.ledger.domain.Timeline;
import com.l1p.interop.ilp.ledger.domain.Transfer;

public class TransferTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testAdditionalinfo() {
		Transfer x = new Transfer();
		x.setAdditionalInfo("hi there");
		assertEquals("additional info", x.getAdditionalInfo(), "hi there");
	}
	
	
	@Test
	public void testCancellationCondition() {
		Transfer x = new Transfer();
		x.setCancellationCondition("hi there too");
		assertEquals("additional info", x.getCancellationCondition(), "hi there too");
	}

	
	@Test
	public void testDebits() {
		Transfer x = new Transfer();
		List<Debit> debits = new ArrayList<Debit>();
		x.setDebits(debits);
		assertEquals("additional info", x.getDebits().size(), 0);
	}
	
	
	@Test
	public void testCredits() {
		Transfer x = new Transfer();
		List<Credit> credits = new ArrayList<Credit>();	
		x.setCredits(credits);
		assertEquals("additional info", x.getCredits().size(), 0);
	}
	
	
	@Test
	public void testExpiresAt() {
		Date now = new Date();
		Transfer x = new Transfer();
		x.setExpiresAt(now);
		assertEquals("additional info", x.getExpiresAt(), now);
	}
	
	
	@Test
	public void testId() {
		String id = "23423423423";
		Transfer x = new Transfer();
		x.setId(id);
		assertEquals("additional info", x.getId(), id);
	}
	
	
	@Test
	public void testRejectionReason() {
		String reason = "Too much money";
		Transfer x = new Transfer();
		x.setRejectionReason(reason);
		assertEquals("additional info", x.getRejectionReason(), reason);
	}
	
	
	@Test
	public void testLedger() {
		String reason = "Too much money";
		Transfer x = new Transfer();
		x.setLedger(reason);
		assertEquals("additional info", x.getLedger(), reason);
	}
	
	
	@Test
	public void testTimeline() {
		String reason = "Too much money";
		Transfer x = new Transfer();
		Timeline tl = new Timeline();
		tl.setExecutedAt(new Date());
		tl.setPreparedAt(new Date());
		tl.setRejectedAt(new Date());
		
		x.setTimeline(tl);
		assertTrue("additional info", x.getTimeline() != null);
	}
	
}

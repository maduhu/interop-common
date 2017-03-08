package com.l1p.interop.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.l1p.interop.ilp.ledger.domain.Timeline;

public class TimelineTest {

		
	@Test
	public void testConstructor() {
		Timeline tl = new Timeline();
		assertTrue(tl != null);
	}
	
	@Test
	public void testGetPreparedAt() {
		Date now = new Date();
		Timeline tl = new Timeline();
		tl.setPreparedAt(now);
		assertEquals("preparedAt", tl.getPreparedAt(), now);
	}

	@Test
	public void testGetExecutedAt() {
		Date now = new Date();
		Timeline tl = new Timeline();
		tl.setExecutedAt(now);
		assertEquals("ExecxutedAt", tl.getExecutedAt(), now);
	}
	
	@Test
	public void testGetRejectedAt() {
		Date now = new Date();
		Timeline tl = new Timeline();
		tl.setRejectedAt(now);
		assertEquals("RejectedAt", tl.getRejectedAt(), now);
	}
	
	
	@Test
	public void testSerialization() throws JsonProcessingException, JSONException {
		Date now = new Date();
		Timeline tl = new Timeline();
		tl.setPreparedAt(now);
		tl.setExecutedAt(now);
		tl.setRejectedAt(now);
		
		String json = "";

        ObjectMapper mapper = new ObjectMapper();
        json = mapper.writeValueAsString(tl) ; 
        
        System.out.println("json = " + json);
        
        JSONObject newJson = new JSONObject(json);
        String prepared_at = newJson.getString("prepared_at");
        String executed_at = newJson.getString("executed_at");
        String rejected_at = newJson.getString("rejected_at");
        
        assertTrue("prepared_at", prepared_at != null);
        assertTrue("executed_at", executed_at != null);
        assertTrue("rejected_at", rejected_at != null);


	}
	
	
}

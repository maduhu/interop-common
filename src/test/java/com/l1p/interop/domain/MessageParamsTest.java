package com.l1p.interop.domain;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;

import com.l1p.interop.ilp.ledger.domain.MessageParams;

/*
 * By testing this class, we will also be testing the super class as well.
 * 
 */
public class MessageParamsTest {

	@Test
	public void testConstructor() {
		
		final String EVENT_VALUE = "message.send";  // this value comes the hard coded value in MessageParams constructor
		final HashMap<String,String> RESOURCE_VALUE = new HashMap<String,String>() {{
					  put("key1", "value1");   
					  put("key2", "value2"); 
					}};
		
		MessageParams params = new MessageParams(EVENT_VALUE, RESOURCE_VALUE);
	
		HashMap<String,String> getResultsMap = (HashMap<String, String>) params.getResource();
		
		assertEquals("test getEvent", params.getEvent(), EVENT_VALUE);
		assertEquals("test getResource1", getResultsMap.get("key1"), "value1");
		assertEquals("test getResource2", getResultsMap.get("key2"), "value2");
	}
	
	
	@Test
	public void testSuperClassGetters() {
		final String EVENT_VALUE = "message.send";  // this value comes the hard coded value in MessageParams constructor
		final Double RESOURCE_VALUE = 123.45D;
		final HashMap<String,String> OBJECT_VALUE = new HashMap<String,String>() {{
					  put("key1", "value1");   
					  put("key2", "value2"); 
					}};
		
		MessageParams params = new MessageParams(EVENT_VALUE, RESOURCE_VALUE);
		params.setRelatedResources(OBJECT_VALUE);
		params.setResource(123.45);  // this should overide the constructor value (second position)
	
		HashMap<String,String> getResultsMap = (HashMap<String, String>) params.getRelatedResources();
		
		assertEquals("test getEvent", params.getResource(), RESOURCE_VALUE);
		assertEquals("test getResource1", getResultsMap.get("key1"), "value1");
		assertEquals("test getResource2", getResultsMap.get("key2"), "value2");
		
	}

}

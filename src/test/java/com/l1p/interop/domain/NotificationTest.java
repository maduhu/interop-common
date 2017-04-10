package com.l1p.interop.domain;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Test;

import com.l1p.interop.ilp.ledger.domain.MessageParams;
import com.l1p.interop.ilp.ledger.domain.Notification;
import com.l1p.interop.ilp.ledger.domain.NotificationParams;

public class NotificationTest {

	@Test
	public void testSetNotificationParams() {
		
		final String EVENT_VALUE = "message.send";  // this value comes the hard coded value in MessageParams constructor
		final Double RESOURCE_VALUE = 123.45D;
		final HashMap<String,String> OBJECT_VALUE = new HashMap<String,String>() {{
			put("key1", "value1");   
			put("key2", "value2"); 
		}};

		Notification notification = new Notification();
		
		// MessageParams extends NotificationParams so by testing MessageParams, we are testing NotificationsParams
		MessageParams params = new MessageParams(EVENT_VALUE, RESOURCE_VALUE);
		notification.setParams(params);
		
		MessageParams paramsResponse = (MessageParams) notification.getParams();

		Double resourceValue = (Double) params.getResource();
		
		assertEquals("test get event", params.getEvent(), EVENT_VALUE);
		assertEquals("test resource values", resourceValue, RESOURCE_VALUE);

	}

}

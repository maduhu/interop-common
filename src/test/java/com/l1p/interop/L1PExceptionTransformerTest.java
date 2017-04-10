package com.l1p.interop;

import java.util.Map;
import java.util.UUID;
import static org.junit.Assert.*;

import org.codehaus.jettison.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mule.api.ExceptionPayload;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.transport.PropertyScope;
import org.mule.component.SimpleCallableJavaComponentTestCase;

public class L1PExceptionTransformerTest extends SimpleCallableJavaComponentTestCase {

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
	public void testMessageWithInteropIDPresent() throws Exception {
		
		MuleEvent event = getTestEvent("payload", muleContext);
		MuleMessage muleMessage = event.getMessage();
        muleMessage.setProperty("id", "some name", PropertyScope.SESSION);
        String interopID = UUID.randomUUID().toString();
        final String messageID = "errorMsgId-123";
        
        // setup Mule message properties for test
        muleMessage.setProperty("errorMessageId", messageID, PropertyScope.SESSION);
        muleMessage.setProperty("interopID", interopID, PropertyScope.SESSION);
        
        muleMessage.setExceptionPayload( createExceptionMessagePayload() );
        
        L1PExceptionTransformer transformer = new L1PExceptionTransformer();
        
        String response = (String) transformer.transformMessage(muleMessage, "UTF-8");
        System.out.println("WithInteropIdPresent:" + response);
        
        JSONObject json = new JSONObject(response);
        JSONObject body = json.getJSONObject("error");
        String returnParsedMessageID = (String) body.get("id");
        String message = (String) body.get("message");
        
        assertEquals("test for ID", returnParsedMessageID, messageID);
        assertTrue("message body contains ", message.indexOf("This is the Root exception message")>-1);
        
	}
	
	
	@Test
	public void testMessageWithInteropIDMissing() throws Exception {
		
		final String messageID = "errorMsgId-456";
		final String traceID="traceId-123";
		
		MuleEvent event = getTestEvent("payload", muleContext);
		MuleMessage muleMessage = event.getMessage();
        muleMessage.setProperty("id", "some name", PropertyScope.SESSION);
        
        // setup Mule message properties for test
        muleMessage.setProperty("errorMessageId", messageID, PropertyScope.SESSION);
        muleMessage.setProperty("traceID", traceID, PropertyScope.SESSION);
        muleMessage.setExceptionPayload( createExceptionMessagePayload() );
        
        L1PExceptionTransformer transformer = new L1PExceptionTransformer();
        
        String response = (String) transformer.transformMessage(muleMessage, "UTF-8");
        System.out.println("WithTraceIDPresent:" + response);
        
        JSONObject json = new JSONObject(response);
        JSONObject body = json.getJSONObject("error");
        
        String returnParsedMessageID = (String) body.get("id");
        String message = (String) body.get("message");
        
        String traceIdTestMessage = "traceID="+traceID;
        
        assertEquals("test for ID", returnParsedMessageID, messageID);
        assertTrue("message body contains ", message.indexOf(traceIdTestMessage)>-1);
        
	}
	
	
	private ExceptionPayload createExceptionMessagePayload() {
		ExceptionPayloadImpl exceptionPayload = new ExceptionPayloadImpl();
		return exceptionPayload;
	}

}

class ExceptionPayloadImpl implements ExceptionPayload {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Throwable getRootException() {
		return new Throwable("This is the Root exception message");
	}
	
	public String getMessage() {
		return "TestErrorMessage";
	}
	
	public Map getInfo() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Throwable getException() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int getCode() {
		// TODO Auto-generated method stub
		return 0;
	}
};

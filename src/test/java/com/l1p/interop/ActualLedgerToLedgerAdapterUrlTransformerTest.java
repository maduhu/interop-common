package com.l1p.interop;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.codehaus.jettison.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.transport.PropertyScope;
import org.mule.component.SimpleCallableJavaComponentTestCase;
import org.mule.util.UUID;

import com.l1p.interop.ilp.ledger.LedgerUrlMapper;

public class ActualLedgerToLedgerAdapterUrlTransformerTest extends SimpleCallableJavaComponentTestCase {

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
	public void testSetAndGetLedgerUrlMapper() {
		ActualLedgerToLedgerAdapterUrlTransformer transformer = new ActualLedgerToLedgerAdapterUrlTransformer();
		
		final LedgerUrlMapper ledgerUrlMapper = new LedgerUrlMapper(".*/ledger", "http://ec2-35-163-231-111.us-west-2.compute.amazonaws.com:8081/ilp/ledger/v1", ".*/v1", "http://ec2-35-163-231-111.us-west-2.compute.amazonaws.com:8088/ledger");
        final String[] fields = {"id", "ledger", "debits.account", "credits.account", "sourceAccount", "account"};
        
		transformer.setLedgerUrlMapper(ledgerUrlMapper);
		LedgerUrlMapper getLedgerUrlMapper = transformer.getLedgerUrlMapper();
		
		assertTrue("test set and get ledgerUrlMapper", ledgerUrlMapper == getLedgerUrlMapper);
	}
	

	@Test
	public void testFullTransformPositive() throws Exception {
		
		MuleEvent event = getTestEvent("payload", muleContext);
		MuleMessage muleMessage = event.getMessage();
        muleMessage.setProperty("id", "some name", PropertyScope.SESSION);
        
        final LedgerUrlMapper ledgerUrlMapper = new LedgerUrlMapper(".*/ledger", "http://ec2-35-163-231-111.us-west-2.compute.amazonaws.com:8081/ilp/ledger/v1", ".*/v1", "http://ec2-35-163-231-111.us-west-2.compute.amazonaws.com:8088/ledger");
        
        /*
         * Message and payload requirements
         * 
         * need Session property with "interopID" as the key.
         * 
         * 
         */
        muleMessage.setProperty("interopID", UUID.getUUID(), PropertyScope.SESSION);
                
        String json = FileUtils.readFileToString(new File("src/test/resources/testData/genericTestJson.json"));
        muleMessage.setPayload(json);
        
        ActualLedgerToLedgerAdapterUrlTransformer transformer = new ActualLedgerToLedgerAdapterUrlTransformer();
        transformer.setLedgerUrlMapper(ledgerUrlMapper);
        
        String response = (String) transformer.transformMessage(muleMessage, "something");
        JSONObject jsonObj = new JSONObject(response);
        String nameValue = jsonObj.getString("name");
        String balanceValue = jsonObj.getString("balance");
        
        System.out.println("response: " + response);
        
        assertTrue("transform response is not null", response != null);
        assertEquals("name value response", nameValue, "alice");
        assertEquals("balance value response", balanceValue, "1000");
	}
	
	
//	@Test(expected=IOException.class)
//	public void testFullTransformErrorException() throws Exception {
//		
//		MuleEvent event = getTestEvent("payload", muleContext);
//		MuleMessage muleMessage = event.getMessage();
//        muleMessage.setProperty("id", "some name", PropertyScope.SESSION);
//        
//        final LedgerUrlMapper ledgerUrlMapper = new LedgerUrlMapper(".*/ledger", "http://ec2-35-163-231-111.us-west-2.compute.amazonaws.com:8081/ilp/ledger/v1", ".*/v1", "http://ec2-35-163-231-111.us-west-2.compute.amazonaws.com:8088/ledger");
//        
//        /*
//         * Message and payload requirements
//         * 
//         * need Session property with "interopID" as the key.
//         * 
//         * 
//         */
//        muleMessage.setProperty("interopID", UUID.getUUID(), PropertyScope.SESSION);
//                
//        String json = FileUtils.readFileToString(new File("src/test/resources/testData/genericBadTestJson.json"));
//        muleMessage.setPayload(json);
//        
//        ActualLedgerToLedgerAdapterUrlTransformer transformer = new ActualLedgerToLedgerAdapterUrlTransformer();
//        transformer.setLedgerUrlMapper(ledgerUrlMapper);
//        
//        String response = (String) transformer.transformMessage(muleMessage, "something");
//        System.out.println("response: " + response);
//	}
	
	
	@Test
	public void setAndGetLedgerUrlMapperTest() {
		
		LedgerUrlMapper mapper  = mock(LedgerUrlMapper.class);
		ActualLedgerToLedgerAdapterUrlTransformer transformer = new ActualLedgerToLedgerAdapterUrlTransformer();
		
		transformer.setLedgerUrlMapper(mapper);
		LedgerUrlMapper getMapper = transformer.getLedgerUrlMapper();
		
		assertTrue("test get LedgerMapper", getMapper != null);
		assertEquals("test get and set LedgerMapper", mapper, getMapper);
		
		
	}

}

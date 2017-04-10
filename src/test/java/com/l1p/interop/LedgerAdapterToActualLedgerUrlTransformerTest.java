package com.l1p.interop;

import static org.junit.Assert.*;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.codehaus.jettison.json.JSONArray;
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

public class LedgerAdapterToActualLedgerUrlTransformerTest extends SimpleCallableJavaComponentTestCase {

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
	public void test() throws Exception {
		
		MuleEvent event = getTestEvent("payload", muleContext);
		MuleMessage muleMessage = event.getMessage();
        muleMessage.setProperty("id", "some name", PropertyScope.SESSION);
        
        muleMessage.setProperty("interopID", UUID.getUUID(), PropertyScope.SESSION);
        muleMessage.setProperty("traceID", UUID.getUUID(), PropertyScope.SESSION);
        
        String json = FileUtils.readFileToString(new File("src/test/resources/testData/sampleTransferJson.json"));
        muleMessage.setPayload(json);
        
        LedgerAdatperToActualLedgerUrlTransformer transformer = new LedgerAdatperToActualLedgerUrlTransformer();
        
        final LedgerUrlMapper ledgerUrlMapper = new LedgerUrlMapper(".*/ledger", "http://ec2-35-163-231-111.us-west-2.compute.amazonaws.com:8081/ilp/ledger/v1", ".*/v1", "http://ec2-35-163-231-111.us-west-2.compute.amazonaws.com:8088/ledger");

        transformer.setLedgerUrlMapper(ledgerUrlMapper);
        String response = (String) transformer.transformMessage(muleMessage, "UTF-8");
        System.out.println("response from transform :: " + response);
        
        JSONObject resultJson = new JSONObject(response);
        
        String id = (String) resultJson.get("id");
        String ledger = (String) resultJson.get("ledger");
        JSONArray debits = resultJson.getJSONArray("debits");
        JSONArray credits = resultJson.getJSONArray("credits");
        String executionCondition = (String) resultJson.get("execution_condition");
        String state = (String) resultJson.get("state");
        String expiresAt = (String) resultJson.get("expires_at");
        
        
        assertTrue("id exists", id != null);
        assertTrue("ledger exists", ledger != null);
        assertTrue("debits exists", debits != null);
        assertTrue("credits exists", credits != null);
        assertTrue("executionCondition exists", executionCondition != null);
        assertTrue("state exists", state != null);
        assertTrue("expiresAt exists", expiresAt != null);
        
        assertEquals("id", id, "http://ec2-35-163-231-111.us-west-2.compute.amazonaws.com:8088/ledger/transfers/6ca071e5-09e2-4e20-8843-d3e8a0dd1f55");
        assertEquals("ledger", ledger, "http://ec2-35-163-231-111.us-west-2.compute.amazonaws.com:8088/ledger");
        assertEquals("executionCondition", executionCondition, "cc:0:3:iLS9w-CiypRGXk3VmvJsKlcfOnH0kXvdxL5EIcdjSjI:32");
        assertEquals("state", state, "executed");
        assertEquals("expiresAt", expiresAt, "2016-12-23T13:59:24.173Z");
        
        
        
        
        
	}

}

package com.l1p.interop.ilp.ledger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.util.Assert;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.l1p.interop.ilp.ledger.domain.Credit;
import com.l1p.interop.ilp.ledger.domain.Debit;
import com.l1p.interop.ilp.ledger.domain.SubscriptionRequest;
import com.l1p.interop.ilp.ledger.domain.Transfer;

public class LedgerUrlMapperTest {

    @Test
    public void testJsonConversion() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String value = "{\"id\":\"http://usd-ledger.example/transfers/3a2a1d9e-8640-4d2d-b06c-84f2cd613204\",\"ledger\":\"http://usd-ledger.example\",\"debits\":[{\"account\":\"http://usd-ledger.example/accounts/alice\",\"amount\":\"50\",\"authorized\":true}],\"credits\":[{\"account\":\"http://usd-ledger.example/accounts/bob\",\"amount\":\"50\"}],\"execution_condition\":\"cc:0:3:8ZdpKBDUV-KX_OnFZTsCWB_5mlCFI3DynX5f5H2dN-Y:2\",\"expires_at\":\"2015-06-16T00:00:01.000Z\",\"state\":\"prepared\"}";
        String value2 = "{\n" +
                "  \"id\": \"http://ec2-35-163-231-111.us-west-2.compute.amazonaws.com:8088/ledger/transfers/6ca071e5-09e2-4e20-8843-d3e8a0dd1f55\",\n" +
                "  \"ledger\": \"http://ec2-35-163-231-111.us-west-2.compute.amazonaws.com:8088/ledger\",\n" +
                "  \"debits\": [\n" +
                "    {\n" +
                "      \"account\": \"http://ec2-35-163-231-111.us-west-2.compute.amazonaws.com:8088/ledger/accounts/dfsp1connector\",\n" +
                "      \"memo\": {\n" +
                "        \"source_transfer_ledger\": \"levelone.ist.\",\n" +
                "        \"source_transfer_id\": \"e829a9bf-b87b-4d84-87f3-18dc467ae7c7\"\n" +
                "      },\n" +
                "      \"amount\": \"7.00\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"credits\": [\n" +
                "    {\n" +
                "      \"account\": \"http://ec2-35-163-231-111.us-west-2.compute.amazonaws.com:8088/ledger/accounts/merchant\",\n" +
                "      \"memo\": {\n" +
                "        \"ilp_header\": {\n" +
                "          \"account\": \"levelone.dfsp1.merchant.4bf0ee9d-1244-4e77-8951-f05a07f4f8a1\",\n" +
                "          \"amount\": \"7\",\n" +
                "          \"data\": {\n" +
                "            \"data\": {\n" +
                "              \"memo\": {\n" +
                "                \"fee\": 0,\n" +
                "                \"transferCode\": \"invoice\",\n" +
                "                \"debitName\": \"bob\",\n" +
                "                \"creditName\": \"merchant\"\n" +
                "              }\n" +
                "            },\n" +
                "            \"expires_at\": \"2016-12-23T13:59:26.173Z\"\n" +
                "          }\n" +
                "        }\n" +
                "      },\n" +
                "      \"amount\": \"7.00\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"execution_condition\": \"cc:0:3:iLS9w-CiypRGXk3VmvJsKlcfOnH0kXvdxL5EIcdjSjI:32\",\n" +
                "  \"cancellation_condition\": null,\n" +
                "  \"state\": \"executed\",\n" +
                "  \"expires_at\": \"2016-12-23T13:59:24.173Z\"\n" +
                "}\n";
        String value3 = "{   \"id\": \"http://ec2-35-163-231-111.us-west-2.compute.amazonaws.com:8088/ledger/transfers/6ca071e5-09e2-4e20-8843-d3e8a0dd1f55\",   \"ledger\": \"http://ec2-35-163-231-111.us-west-2.compute.amazonaws.com:8088/ledger\",   \"debits\": [     {       \"account\": \"http://ec2-35-163-231-111.us-west-2.compute.amazonaws.com:8088/ledger/accounts/dfsp1connector\",       \"memo\": {         \"source_transfer_ledger\": \"levelone.ist.\",         \"source_transfer_id\": \"e829a9bf-b87b-4d84-87f3-18dc467ae7c7\"       },       \"amount\": \"7.00\"     }   ],   \"credits\": [     {       \"account\": \"http://ec2-35-163-231-111.us-west-2.compute.amazonaws.com:8088/ledger/accounts/merchant\",       \"memo\": {         \"ilp_header\": {           \"account\": \"levelone.dfsp1.merchant.4bf0ee9d-1244-4e77-8951-f05a07f4f8a1\",           \"amount\": \"7\",           \"data\": {             \"data\": {               \"memo\": {                 \"fee\": 0,                 \"transferCode\": \"invoice\",                 \"debitName\": \"bob\",                 \"creditName\": \"merchant\"               }             },             \"expires_at\": \"2016-12-23T13:59:26.173Z\"           }         }       },       \"amount\": \"7.00\"     }   ],   \"execution_condition\": \"cc:0:3:iLS9w-CiypRGXk3VmvJsKlcfOnH0kXvdxL5EIcdjSjI:32\",   \"cancellation_condition\": null,   \"state\": \"executed\",   \"expires_at\": \"2016-12-23T13:59:24.173Z\" }";
        final Transfer transfer = mapper.readValue(value3, Transfer.class);
        Assert.notNull(transfer.getExecutionCondition());
    }

    @Test
    public void testReplaceWithRegex() {
        String srcUrl = "http://ec2-35-163-231-111.us-west-2.compute.amazonaws.com:8088/ledger/transfers/6ca071e5-09e2-4e20-8843-d3e8a0dd1f55";
        final String result = srcUrl.replaceFirst(".*/ledger/", "/banana/");
        assertEquals("/banana/transfers/6ca071e5-09e2-4e20-8843-d3e8a0dd1f55", result);
    }

    @Test
    public void testLedgerUrlConversion() throws IOException {
        String transferJson = "{\n" +
                "  \"id\": \"http://ec2-35-163-231-111.us-west-2.compute.amazonaws.com:8088/ledger/transfers/6ca071e5-09e2-4e20-8843-d3e8a0dd1f55\",\n" +
                "  \"ledger\": \"http://ec2-35-163-231-111.us-west-2.compute.amazonaws.com:8088/ledger\",\n" +
                "  \"debits\": [\n" +
                "    {\n" +
                "      \"account\": \"http://ec2-35-163-231-111.us-west-2.compute.amazonaws.com:8088/ledger/accounts/dfsp1connector\",\n" +
                "      \"memo\": {\n" +
                "        \"source_transfer_ledger\": \"levelone.ist.\",\n" +
                "        \"source_transfer_id\": \"e829a9bf-b87b-4d84-87f3-18dc467ae7c7\"\n" +
                "      },\n" +
                "      \"amount\": \"7.00\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"credits\": [\n" +
                "    {\n" +
                "      \"account\": \"http://ec2-35-163-231-111.us-west-2.compute.amazonaws.com:8088/ledger/accounts/merchant\",\n" +
                "      \"memo\": {\n" +
                "        \"ilp_header\": {\n" +
                "          \"account\": \"levelone.dfsp1.merchant.4bf0ee9d-1244-4e77-8951-f05a07f4f8a1\",\n" +
                "          \"amount\": \"7\",\n" +
                "          \"data\": {\n" +
                "            \"data\": {\n" +
                "              \"memo\": {\n" +
                "                \"fee\": 0,\n" +
                "                \"transferCode\": \"invoice\",\n" +
                "                \"debitName\": \"bob\",\n" +
                "                \"creditName\": \"merchant\"\n" +
                "              }\n" +
                "            },\n" +
                "            \"expires_at\": \"2016-12-23T13:59:26.173Z\"\n" +
                "          }\n" +
                "        }\n" +
                "      },\n" +
                "      \"amount\": \"7.00\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"execution_condition\": \"cc:0:3:iLS9w-CiypRGXk3VmvJsKlcfOnH0kXvdxL5EIcdjSjI:32\",\n" +
                "  \"cancellation_condition\": null,\n" +
                "  \"state\": \"executed\",\n" +
                "  \"expires_at\": \"2016-12-23T13:59:24.173Z\"\n" +
                "}\n";
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> interopGenericMap = mapper.readValue(transferJson, new TypeReference<Map<String, Object>>() {
        });
        final LedgerUrlMapper ledgerUrlMapper = new LedgerUrlMapper(".*/ledger", "http://ec2-35-163-231-111.us-west-2.compute.amazonaws.com:8081/ilp/ledger/v1", ".*/v1", "http://ec2-35-163-231-111.us-west-2.compute.amazonaws.com:8088/ledger");
        final String[] fields = {"id", "ledger", "debits.account", "credits.account", "sourceAccount", "account"};
        String actualLedgerUrlId = (String) interopGenericMap.get("id");
        ledgerUrlMapper.mapUrlToLedgerAdapterUrl(fields, interopGenericMap);
        assertNotEquals(actualLedgerUrlId, interopGenericMap.get("id"));
        ledgerUrlMapper.mapUrlToActualLedgerUrl(fields, interopGenericMap);
        assertEquals(actualLedgerUrlId, interopGenericMap.get("id"));
    }

    @Test
    public void testSubscriptionRequestParsing() throws IOException {
        String subscriptionRequest = "{ \"jsonrpc\": \"2.0\", \"id\": 1, \"method\": \"subscribe_account\", \"params\": { \"eventType\": \"*\", \"accounts\": [ \"http://ec2-35-166-189-14.us-west-2.compute.amazonaws.com:8088/ilp/ledger/v1/accounts/dfsp1-testconnector\"] } }";
        ObjectMapper mapper = new ObjectMapper();
        SubscriptionRequest readValue = mapper.readValue(subscriptionRequest, SubscriptionRequest.class);
        assertEquals("*", readValue.getParams().getEventType());
    }
    
    
    @Test
    public void testMapUrlToLedgerAdapter() throws MalformedURLException {
    	final LedgerUrlMapper ledgerUrlMapper = new LedgerUrlMapper(".*/ledger", "http://ec2-35-163-231-111.us-west-2.compute.amazonaws.com:8081/ilp/ledger/v1", ".*/v1", "http://ec2-35-163-231-111.us-west-2.compute.amazonaws.com:8088/ledger");
        
    	List<Debit> debits = new ArrayList<Debit>();
    	List<Credit> credits = new ArrayList<Credit>();
    	
    	Debit debit = null;
    	Credit credit = null;
    	
    	debit = new Debit();
    	debit.setAccount("Test1");
    	debit.setAmount("100.01");
    	debit.setAuthorized(true);

    	debits.add(debit);
    	
    	credit = new Credit();
    	credit.setAccount("CreditAccount");
    	credit.setAmount("100.01");
    	
    	credits.add(credit);
    	
        Transfer xfer = new Transfer();
        xfer.setId("id");
        xfer.setLedger("ledger");
        xfer.setCredits(credits);
        xfer.setDebits(debits);
        
        ledgerUrlMapper.mapUrlToLedgerAdapter(xfer);
    }

}

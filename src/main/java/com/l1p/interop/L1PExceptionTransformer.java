package com.l1p.interop;

import com.l1p.interop.L1PErrorResponse;

import java.util.Map;
import java.util.HashMap;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.api.transport.PropertyScope;
import org.mule.transformer.AbstractMessageTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class takes a mule exception and creates a L1PErrorResponse that is formatted as json string. 
 * Created by Bryan on 8/31/2016
 */
public class L1PExceptionTransformer extends AbstractMessageTransformer {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public synchronized Object transformMessage(MuleMessage muleMessage, String outputEncoding) throws TransformerException {
        final String errorMessageId = muleMessage.getProperty( "errorMessageId", PropertyScope.SESSION );
        final String l1pTraceId = (muleMessage.getProperty( "L1p-Trace-Id", PropertyScope.SESSION ) != null ? muleMessage.getProperty( "L1p-Trace-Id", PropertyScope.SESSION ).toString() : muleMessage.getProperty( "TraceID", PropertyScope.SESSION ).toString());    
        final String rootExceptionCause = muleMessage.getExceptionPayload().getRootException().getMessage();
        final String errorMessage = "Failed to process request for L1p-Trace-Id=" + l1pTraceId + ": " + rootExceptionCause;
        
        log.warn(errorMessageId + ": " + rootExceptionCause);
        log.warn(errorMessage);
        
        Map<String, Object> addProperty = new HashMap<String, Object>();
        addProperty.put("Content-Type", "application/json");
        addProperty.put("http.status", ( muleMessage.getInboundProperty("http.status")!= null ? muleMessage.getInboundProperty("http.status") : 500 ) );

        Throwable t = null;
        if ( muleMessage.getExceptionPayload() != null ) {
            t = muleMessage.getExceptionPayload().getException();
        }

        muleMessage.addProperties(addProperty, PropertyScope.INVOCATION);
        L1PErrorResponse errorResponse = new L1PErrorResponse( errorMessageId, errorMessage, t );
        
        return errorResponse.getJsonString();
    }

}



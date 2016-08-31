package com.l1p.interop;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit Test for L1PErrorResponse
 *
 * Created by Bryan on 8/18/2016.
 */
public class L1PErrorResponseTest {

    @Test
    /**
     * This test proves the response creates a JSON String that is convertible back to a Map structure containing
     * the correct data.
     */
    public void responseShouldBeConvertibleToJSON() throws Exception {
        String message = "a message";
        String id = "an id";

        L1PErrorResponse errorResponse = new L1PErrorResponse( id, message, null );
        String errorResponseJson = errorResponse.toString();

        //convert output to map
        Map<String,Object> header = JsonTransformer.stringToMap( errorResponseJson );
        validateErrorResponse( header, id, message, false );
    }

    @Test
    public void errorResponseShouldIncludeExceptionInformation() throws Exception {
        String message = "a message";
        String id = "an id";
        String exceptionMessage = "test exception message";
        String rootCauseExceptionMessage = "test root cause exception message";

        Exception causeException = new RuntimeException( rootCauseExceptionMessage );
        Exception testException = new RuntimeException( exceptionMessage, causeException );

        L1PErrorResponse errorResponse = new L1PErrorResponse( id, message, testException );
        String errorResponseJson = errorResponse.toString();

        //convert output to map
        Map<String,Object> header = JsonTransformer.stringToMap( errorResponseJson );
        validateErrorResponse( header, id, message, true );

        Map<String,Object> debug = (Map<String,Object>)header.get( L1PErrorResponse.DEBUG_FIELD );
        assertEquals( "Debug message did not contain expected value", exceptionMessage, debug.get( L1PErrorResponse.MESSAGE_FIELD ) );
        assertTrue( "Debug object did not contain expected field " + L1PErrorResponse.CAUSE_FIELD, debug.containsKey( L1PErrorResponse.CAUSE_FIELD ) );
        Map<String,Object> cause = (Map<String,Object>)debug.get( L1PErrorResponse.CAUSE_FIELD );
        assertTrue( "Debug object contained a null value for field " + L1PErrorResponse.CAUSE_FIELD, cause != null );
        assertEquals( "Cause message did not contain expected value", rootCauseExceptionMessage, cause.get( L1PErrorResponse.MESSAGE_FIELD ) );

    }

    public void validateErrorResponse( Map<String,Object> errorResponseHeader, String expectedId, String expectedMessage, boolean shouldContainDebugInfo ) throws Exception {
        assertTrue( "Header map was null", errorResponseHeader != null );

        Map<String,Object> error = (Map<String,Object>)errorResponseHeader.get( "error" );
        assertTrue( "Error map was null", error != null );
        assertEquals( "Header map did not contain correct data for " + L1PErrorResponse.MESSAGE_FIELD + " element", expectedMessage, error.get( L1PErrorResponse.MESSAGE_FIELD ) );
        assertEquals( "Header map did not contain correct data for " + L1PErrorResponse.ID_FIELD + " element", expectedId, error.get( L1PErrorResponse.ID_FIELD ) );

        Map<String, Object> debug = (Map<String, Object>)errorResponseHeader.get( L1PErrorResponse.DEBUG_FIELD );
        if ( shouldContainDebugInfo ) {
            assertTrue( "Response contained null debug information", debug != null );
            assertTrue( "Response contained empty debug information", debug.size() > 0 );
        } else {
            assertTrue( "Response incorrectly contained debug information", debug == null );
        }
    }

}

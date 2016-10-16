package com.modusbox.common;

import org.junit.Test;

import com.modusbox.common.ErrorResponse;
import com.modusbox.common.JsonTransformer;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit Test for ErrorResponse
 *
 * Created by Bryan on 8/18/2016.
 */
public class ErrorResponseTest {

    @Test
    /**
     * This test proves the response creates a JSON String that is convertible back to a Map structure containing
     * the correct data.
     */
    public void responseShouldBeConvertibleToJSON() throws Exception {
        String message = "a message";
        String id = "an id";

        ErrorResponse errorResponse = new ErrorResponse( id, message, null );
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

        ErrorResponse errorResponse = new ErrorResponse( id, message, testException );
        String errorResponseJson = errorResponse.toString();

        //convert output to map
        Map<String,Object> header = JsonTransformer.stringToMap( errorResponseJson );
        validateErrorResponse( header, id, message, true );

        Map<String,Object> debug = (Map<String,Object>)header.get( ErrorResponse.DEBUG_FIELD );
        assertEquals( "Debug message did not contain expected value", exceptionMessage, debug.get( ErrorResponse.MESSAGE_FIELD ) );
        assertTrue( "Debug object did not contain expected field " + ErrorResponse.CAUSE_FIELD, debug.containsKey( ErrorResponse.CAUSE_FIELD ) );
        Map<String,Object> cause = (Map<String,Object>)debug.get( ErrorResponse.CAUSE_FIELD );
        assertTrue( "Debug object contained a null value for field " + ErrorResponse.CAUSE_FIELD, cause != null );
        assertEquals( "Cause message did not contain expected value", rootCauseExceptionMessage, cause.get( ErrorResponse.MESSAGE_FIELD ) );

    }

    public void validateErrorResponse( Map<String,Object> errorResponseHeader, String expectedId, String expectedMessage, boolean shouldContainDebugInfo ) throws Exception {
        assertTrue( "Header map was null", errorResponseHeader != null );

        Map<String,Object> error = (Map<String,Object>)errorResponseHeader.get( "error" );
        assertTrue( "Error map was null", error != null );
        assertEquals( "Header map did not contain correct data for " + ErrorResponse.MESSAGE_FIELD + " element", expectedMessage, error.get( ErrorResponse.MESSAGE_FIELD ) );
        assertEquals( "Header map did not contain correct data for " + ErrorResponse.ID_FIELD + " element", expectedId, error.get( ErrorResponse.ID_FIELD ) );

        Map<String, Object> debug = (Map<String, Object>)errorResponseHeader.get( ErrorResponse.DEBUG_FIELD );
        if ( shouldContainDebugInfo ) {
            assertTrue( "Response contained null debug information", debug != null );
            assertTrue( "Response contained empty debug information", debug.size() > 0 );
        } else {
            assertTrue( "Response incorrectly contained debug information", debug == null );
        }
    }

}

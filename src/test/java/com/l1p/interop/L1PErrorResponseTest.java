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
        String message = "User Add Failed";
        String technicalMessage = "User Add Failed due to primary key violation";
        String type = "test.type";

        L1PErrorResponse errorResponse = new L1PErrorResponse( message, technicalMessage, type, null );
        String errorResponseJson = errorResponse.toString();

        //convert output to map
        Map<String,Object> header = JsonTransformer.stringToMap( errorResponseJson );
        validateErrorResponse( header, message, technicalMessage, type );
    }

    @Test
    public void errorResponseShouldIncludeExceptionInformation() throws Exception {
        String message = "short";
        String technicalMessage = "verbose";
        String type = "test.type";
        Exception causeException = new RuntimeException( "test cause exception" );
        Exception testException = new RuntimeException( "test actual exception", causeException );

        L1PErrorResponse errorResponse = new L1PErrorResponse( message, technicalMessage, type, testException );
        String errorResponseJson = errorResponse.toString();

        //convert output to map
        Map<String,Object> header = JsonTransformer.stringToMap( errorResponseJson );
        validateErrorResponse( header, message, technicalMessage, type );
    }

    public void validateErrorResponse( Map<String,Object> errorResponseHeader, String expectedMessage, String expectedTechnicalMessage, String expectedType ) throws Exception {
        assertTrue( "Header map was null", errorResponseHeader != null );

        Map<String,Object> error = (Map<String,Object>)errorResponseHeader.get( "error" );
        assertTrue( "Error map was null", error != null );
        assertTrue( "Size of Error map was incorrect, expected 3, received " + error.size(), error.size() == 3 );
        assertEquals( "Header map did not contain correct data for message element", expectedMessage, error.get( "message" ) );
        assertEquals( "Header map did not contain correct data for type element", expectedType, error.get( "type" ) );
        assertEquals( "Header map did not contain correct data for errorPrint element", expectedTechnicalMessage, error.get( "errorPrint" ) );

    }

}

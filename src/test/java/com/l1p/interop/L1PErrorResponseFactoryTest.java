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
public class L1PErrorResponseFactoryTest {
    String message = "a message";
    String id = "an id";

    @Test
    public void createShouldIncludeDebugInfoWhenFlagTrue() throws Exception {
        L1PErrorResponseFactory factory = new L1PErrorResponseFactory( true );
        L1PErrorResponse response = factory.create( id, message, new Throwable( "this is a throwable" ) );

        validateErrorResponse( response, id, message, true );

        response = factory.create( id, message, null );

        validateErrorResponse( response, id, message, false );

        response = factory.create( id, message );

        validateErrorResponse( response, id, message, false );
    }

    @Test
    public void createShouldNotIncludeDebugInfoWhenFlagFalse() throws Exception {
        L1PErrorResponseFactory factory = new L1PErrorResponseFactory( false );
        L1PErrorResponse response = factory.create( id, message, new Throwable( "this is a throwable" ) );

        validateErrorResponse( response, id, message, false );

        response = factory.create( id, message, null );

        validateErrorResponse( response, id, message, false );

        response = factory.create( id, message );

        validateErrorResponse( response, id, message, false );
    }


    public void validateErrorResponse( L1PErrorResponse response, String expectedId, String expectedMessage, boolean shouldContainDebugInfo ) throws Exception {
        Map<String,Object> errorResponseHeader = response.getResponseData();

        assertTrue( "Header map was null", errorResponseHeader != null );
        Map<String,Object> error = (Map<String,Object>)errorResponseHeader.get( L1PErrorResponse.ERROR_FIELD );
        assertTrue( "Error map was null", error != null );
        assertEquals( "Header map did not contain correct data for message element", expectedMessage, error.get( L1PErrorResponse.MESSAGE_FIELD ) );
        assertEquals( "Header map did not contain correct data for type element", expectedId, error.get( L1PErrorResponse.ID_FIELD ) );

        Map<String, Object> debug = (Map<String, Object>)errorResponseHeader.get( L1PErrorResponse.DEBUG_FIELD );
        if ( shouldContainDebugInfo ) {
            assertTrue( "Response contained null debug information", debug != null );
            assertTrue( "Response contained empty debug information", debug.size() > 0 );
        } else {
            assertTrue( "Response incorrectly contained debug information", debug == null );
        }
    }

}

package com.modusbox.common;

import org.junit.Test;

import com.modusbox.common.ErrorResponse;
import com.modusbox.common.ErrorResponseFactory;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit Test for ErrorResponse
 *
 * Created by Bryan on 8/18/2016.
 */
public class ErrorResponseFactoryTest {
    String message = "a message";
    String id = "an id";

    @Test
    public void createShouldIncludeDebugInfoWhenFlagTrue() throws Exception {
        ErrorResponseFactory factory = new ErrorResponseFactory( true );
        ErrorResponse response = factory.create( id, message, new Throwable( "this is a throwable" ) );

        validateErrorResponse( response, id, message, true );

        response = factory.create( id, message, null );

        validateErrorResponse( response, id, message, false );

        response = factory.create( id, message );

        validateErrorResponse( response, id, message, false );
    }

    @Test
    public void createShouldNotIncludeDebugInfoWhenFlagFalse() throws Exception {
        ErrorResponseFactory factory = new ErrorResponseFactory( false );
        ErrorResponse response = factory.create( id, message, new Throwable( "this is a throwable" ) );

        validateErrorResponse( response, id, message, false );

        response = factory.create( id, message, null );

        validateErrorResponse( response, id, message, false );

        response = factory.create( id, message );

        validateErrorResponse( response, id, message, false );
    }


    public void validateErrorResponse( ErrorResponse response, String expectedId, String expectedMessage, boolean shouldContainDebugInfo ) throws Exception {
        Map<String,Object> errorResponseHeader = response.getResponseData();

        assertTrue( "Header map was null", errorResponseHeader != null );
        Map<String,Object> error = (Map<String,Object>)errorResponseHeader.get( ErrorResponse.ERROR_FIELD );
        assertTrue( "Error map was null", error != null );
        assertEquals( "Header map did not contain correct data for message element", expectedMessage, error.get( ErrorResponse.MESSAGE_FIELD ) );
        assertEquals( "Header map did not contain correct data for type element", expectedId, error.get( ErrorResponse.ID_FIELD ) );

        Map<String, Object> debug = (Map<String, Object>)errorResponseHeader.get( ErrorResponse.DEBUG_FIELD );
        if ( shouldContainDebugInfo ) {
            assertTrue( "Response contained null debug information", debug != null );
            assertTrue( "Response contained empty debug information", debug.size() > 0 );
        } else {
            assertTrue( "Response incorrectly contained debug information", debug == null );
        }
    }

}

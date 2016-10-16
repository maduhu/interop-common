package com.l1p.interop;

/**
 * Factory class to support creation of L1PErrorResponse.  Allows inclusion of debug info to be toggled
 * per environment.
 *
 * Created by Bryan on 8/31/2016.
 */
public class L1PErrorResponseFactory {

    private final boolean includeDebugInfo;

    /**
     * Constructs an instance of L1PErrorResponseFactory.
     *
     * @param includeDebugInfo If true debug information will be included when the provided instance
     *                         of Throwable is not null
     */
    public L1PErrorResponseFactory( final boolean includeDebugInfo ) {
        this.includeDebugInfo = includeDebugInfo;
    }

    /**
     * Creates an instance of L1PErrorResponse.  No debug information will be included.
     *
     * @param id - Application defined error type, usually part of well defined list of error types
     * @param message - Error message suitable to be logged in application logs
     * @return an instance of L1PErrorResponse
     */
    public L1PErrorResponse create( final String id, final String message ) {
        return create( id, message, null );
    }

    /**
     * Creates an instance of L1PErrorResponse.  If throwable is not null and includeDebugInfo is true
     * then a debug section will be included in the response as well.
     *
     * @param id - Application defined error type, usually part of well defined list of error types
     * @param message - Error message suitable to be logged in application logs
     * @return an instance of L1PErrorResponse
     */
    public L1PErrorResponse create( final String id, final String message, final Throwable throwable ) {
        return new L1PErrorResponse( id, message, includeDebugInfo ? throwable : null );
    }
}

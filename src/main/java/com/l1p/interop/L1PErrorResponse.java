package com.l1p.interop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class to handle construction of standard error response for the level 1 project.
 *
 * Created by Bryan on 8/17/2016.
 */
public class L1PErrorResponse {

    public static final String ERROR_FIELD = "error";
    public static final String ID_FIELD = "id";
    public static final String MESSAGE_FIELD = "message";
    public static final String DEBUG_FIELD = "debug";
    public static final String CAUSE_FIELD = "cause";
    public static final String STACK_INFO_FIELD = "stackInfo";
    public static final String IS_ROOT_FIELD = "isRootCause";

    private final int MAX_STACK_LENGTH = 10;
    private final int MAX_RECURSION_DEPTH = 3;

    private final String jsonString;
    private final Map<String,Object> responseData;

    /**
     * Constructs an instance of L1PErrorResponse.  If throwable is not null and includeDebugInfo is true
     * then a debug section will be included in the response as well.
     *
     * @param id - Application defined error type, usually part of well defined list of error types
     * @param message - Error message suitable to be logged in application logs.
     * @param throwable - Instance of Throwable that will be included as debug information.
     */
    public L1PErrorResponse( final String id, final String message, Throwable throwable ) {
        final Map<String, Object> errorMap = new HashMap<String, Object>();

        updateField( MESSAGE_FIELD, message, errorMap );
        updateField( ID_FIELD, id, errorMap );

        responseData = new HashMap<String, Object>();

        responseData.put( ERROR_FIELD, errorMap );

        if ( throwable != null )
            responseData.put( DEBUG_FIELD, parseCause( throwable, 1 ) );

        jsonString = JsonTransformer.mapToString( responseData );
    }

    /**
     * Recursively builds up a nested map structure representing an exception hierarchy.  The depth is
     * limited to MAX_RECURSION_DEPTH levels to protect against debug information creating a
     * stack overflow.
     *
     * @param throwable - the top level Throwable
     * @param level - current recursion depth
     * @return Map representing the exception hierarchy
     */
    private Map<String,Object> parseCause( final Throwable throwable, final int level ) {
        Map<String, Object> result = new HashMap<String,Object>();

        List<String> stackMessages = parseStackTrace( throwable.getStackTrace() );
        result.put( MESSAGE_FIELD, throwable.getMessage() );
        result.put( STACK_INFO_FIELD, stackMessages);

        Throwable nextCause = throwable.getCause();

        if ( nextCause == null || nextCause == throwable ) {
            result.put(IS_ROOT_FIELD, nextCause == null);
        } else if ( level < MAX_RECURSION_DEPTH ) {
            result.put( CAUSE_FIELD, parseCause( nextCause, ( level + 1 ) ) );
        }

        return result;
    }

    /**
     * Parses an array of StackTraceElement.  The first MAX_STACK_LENGTH entries will be included.  If the
     * number of actual entries exceeds this value a row indication how many entries were skipped will be
     * included followed by the final entry in the stack.
     *
     * @param stackTrace array of StackTraceElement to parse
     * @return
     */
    private List<String> parseStackTrace( StackTraceElement[] stackTrace ) {

        List<String> result = new ArrayList<String>();

        if ( stackTrace != null && stackTrace.length > 0 ) {
            int stackSize = stackTrace.length;
            int linesToPrint = stackSize <= MAX_STACK_LENGTH ? stackSize : MAX_STACK_LENGTH;
            int skippedLines = stackSize - linesToPrint;

            for ( int i = 0; i < linesToPrint; i++ ) {
                StackTraceElement nextElement = stackTrace[i];
                result.add( nextElement.toString() );
            }

            if ( skippedLines > 0 ) {
                if ( skippedLines > 1 ) {
                    result.add("... " + (skippedLines - 1) + " stack entries skipped, final entry below ...");
                }

                result.add( stackTrace[ stackSize - 1 ].toString() );   //add final entry
            }
        }

        return result;
    }

    /**
     * Adds a field to a map if it is neither null or empty
     * @param key - key field in the map
     * @param value - value to add
     * @param target - map that will be updated
     */
    private void updateField( final String key, final String value, final Map<String, Object>target ) {
        if ( value != null && value.length() > 0 )
            target.put( key, value );
    }

    /**
     * Returns the error response data as a JSON encoded String
     *
     * @return JSON encoded String representing the error response
     */
    public String getJsonString() {
        return jsonString;
    }

    /**
     * Returns the error response data as a Map
     *
     * @return Map representing the error response
     */
    public Map<String,Object> getResponseData() {
        return responseData;
    }

    @Override
    /**
     * Returns the error response data as a JSON encoded String
     */
    public String toString() {
        return getJsonString();
    }
}

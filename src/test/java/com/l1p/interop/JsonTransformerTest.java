package com.l1p.interop;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * JUNit Test for JsonMapStringTransformer.
 *
 * Created by Bryan on 8/18/2016.
 */
public class JsonTransformerTest {

    @Test
    public void testStringToMap() throws Exception {
        final String jsonInput = "{\"key1\":\"value1\",\"Key2\":\"Value2\",\"key3\":null,\"key4\":{\"nestedKey2\":null,\"nestedKey1\":\"nestedValue1\"}}";
        //verify output converts back to its source
        try {
            Map<String, Object> target = JsonTransformer.stringToMap(jsonInput);
            assertTrue( "Output map not of expected size, expected = 4, actual = " + target.size(), target.size() == 4 );
            assertEquals( "Value for key1 did not match expected", "value1", target.get( "key1" ) );
            assertEquals( "Value for Key2 did not match expected", "Value2", target.get( "Key2" ) );
            assertEquals( "Value for key3 did not match expected", null, target.get( "key3" ) );

            Map<String, Object> nestedMap = (Map<String, Object>)target.get( "key4" );
            assertTrue( "Nested map not of expected size, expected = 2, actual = " + nestedMap.size(), nestedMap.size() == 2 );
            assertEquals( "Value for nestedKey1 did not match expected", "nestedValue1", nestedMap.get( "nestedKey1" ) );
            assertEquals( "Value for nestedKey2 did not match expected", null, nestedMap.get( "nestedKey2" ) );

        } catch( Exception e ) {
            fail( "Conversion of JSON input to a map produced an unexpected exception: " + e.getMessage() );
        }

        //test map creation from non-json input
        try {
            JsonTransformer.stringToMap( "<NotJSON/>" );
        } catch( NullPointerException e ) {
            fail( "Conversion of invalid data to map produced a NullPointerException" );
        } catch( JsonTransformerException e ) {
            assertTrue( "Conversion of invalid data to map produced a RuntimeException of an unexpected type: " + e.getMessage(), e.getMessage().contains( "Failed to convert source String" ) );
            System.out.println("here");
        } catch( Exception e ) {
            fail( "Conversion of invalid data to map produced an unexpected exception: " + e.getClass().getName() );
        }

        //test map creation from null input - should return empty Map
        try {
            Map<String,Object> map = JsonTransformer.stringToMap( null );
            assertTrue("Map produced from null input was not empty", map.isEmpty());
        } catch( Exception e ) {
            fail( "Creation of a map from null input produced an unexpected exception: " + e.getMessage() );
        }
    }

    @Test
    public void testMapToString() throws Exception {

        //verify good input produces output that converts back to a Map via JSON
        Map<String,Object> source = new HashMap<String,Object>();
        source.put( "key1", "value1" );
        source.put( "Key2", "Value2" );
        source.put( "key3", null );

        Map<String,Object> nestedMap = new HashMap<String,Object>();
        nestedMap.put( "nestedKey1", "nestedValue1" );
        nestedMap.put( "nestedKey2", null );

        source.put( "key4", nestedMap );

        String jsonResult = null;
        try {
            jsonResult = JsonTransformer.mapToString(source);
        } catch( Exception e ) {
            fail( "Conversion of a valid map to a JSON string produced an unexpected exception: " + e.getMessage() );
        }

        //verify output converts back to its source
        try {
            Map target = JsonTransformer.stringToMap(jsonResult);
            assertTrue( "Output map not of expected size, expected = 4, actual = " + target.size(), target.size() == 4 );
            assertEquals( "Value for key1 did not match expected", "value1", target.get( "key1" ) );
            assertEquals( "Value for Key2 did not match expected", "Value2", target.get( "Key2" ) );
            assertEquals( "Value for key3 did not match expected", null, target.get( "key3" ) );

            nestedMap = (Map)target.get( "key4" );
            assertTrue( "Nested map not of expected size, expected = 2, actual = " + nestedMap.size(), nestedMap.size() == 2 );
            assertEquals( "Value for nestedKey1 did not match expected", "nestedValue1", nestedMap.get( "nestedKey1" ) );
            assertEquals( "Value for nestedKey2 did not match expected", null, nestedMap.get( "nestedKey2" ) );

        } catch( Exception e ) {
            fail( "Conversion of JSON output back to a map produced an unexpected exception: " + e.getMessage() );
        }

        //verify bad input produces the expected exception
        Map badDataMap = new HashMap();
        badDataMap.put( "key", System.err );    //map contains an object that is not serializable as JSON
        try {
            JsonTransformer.mapToString( badDataMap );
        } catch( NullPointerException e ) {
            fail( "Conversion of invalid map data to string produced a NullPointerException" );
        } catch( JsonTransformerException e ) {
            assertTrue( "Conversion of invalid map data to string produced a RuntimeException of an unexpected type: " + e.getMessage(), e.getMessage().contains( "Failed to convert source Map to JSON" ) );
        } catch( Exception e ) {
            fail( "Conversion of invalid map data to string produced an unexpected exception: " + e.getClass().getName() );
        }

        //test string creation from null input
        try {
            assertEquals("Conversion of null data to string produced unexpected result", JsonTransformer.mapToString(null), "{}");
        } catch( Exception e ) {
            fail( "Conversion of null data to a string produced an unexpected exception: " + e.getMessage() );
        }
    }
}

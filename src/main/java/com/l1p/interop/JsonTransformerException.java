package com.l1p.interop;

/**
 * Exception class for json transformation exception
 */
public class JsonTransformerException extends RuntimeException{
    private static final long serialVersionUID = 1L;

	public JsonTransformerException(){
        super();
    }

    public JsonTransformerException(String message){
        super(message);
    }
}

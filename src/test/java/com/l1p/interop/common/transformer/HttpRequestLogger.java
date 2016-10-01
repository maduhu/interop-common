package com.l1p.interop.common.transformer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.api.transport.PropertyScope;
import org.mule.transformer.AbstractMessageTransformer;

/**
 * Responsible for logging details about a request and assigning a requestId so that actions on the request can be
 * correlated
 *
 * mule config:
 *     	<custom-transformer class="com.rh.mule.common.transformer.HttpRequestLogger" name="HttpRequestLogger" doc:name="Java">
 			<spring:property name="requestIdPrefix" value="test_"/>
 			<spring:property name="logQueryParameters" value="true"/>
  		 	<spring:property name="setMessageID" value="true"/>
		 	<spring:property name="logContentType" value="true"/>
 		</custom-transformer>
 *
 * all parameters are optional:
 * 	requestPrefixId - string to prefix the generated requestid with.  useful to denote which system generated the id
 * 	logQueryParameters - if true the query parameter map will be logged.  Default is false.
 * 	setMessageID - if true a session variable named messageID will be set in addition to requestId.  Default is false.
 * 	logContentType - if true the content type and payload class name will be logged.  Default is true.
 *
 * If an inbound property named requestId exists on the message it will be reused otherwise a new uuid will be generated
 */
public class HttpRequestLogger extends AbstractMessageTransformer {
	public static final String REQUEST_ID_KEY = "requestId";
	private static final Logger logger = LogManager.getLogger(HttpRequestLogger.class);
	private String requestIdPrefix = "";
	private boolean logQueryParameters = false;
	private boolean setMessageID = false;
	private boolean logContentType = true;

	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {
		String requestId = null;
		boolean containedReqId = true;

		if ( message.getInboundPropertyNames().contains( REQUEST_ID_KEY ) ) {
			requestId = message.getInboundProperty( REQUEST_ID_KEY );
		}

		if ( requestId == null || requestId.trim().length() == 0 ) {
			requestId = requestIdPrefix + java.util.UUID.randomUUID().toString();
			containedReqId = false;
		}

		final String queryParams = message.getProperty( "http.query.params", PropertyScope.INBOUND ).toString();

		message.setProperty( "requestId", requestId, PropertyScope.SESSION );
		message.setProperty( "requestId", requestId, PropertyScope.OUTBOUND );

		if ( setMessageID )
			message.setProperty( "messageID", requestId, PropertyScope.SESSION );

		final String muleRemoteClientAddress = message.getInboundProperty("MULE_REMOTE_CLIENT_ADDRESS");
		final String httpRemoteAddress = message.getInboundProperty("http.remote.address");

		StringBuffer result = new StringBuffer( "Received " );
		result.append( message.getInboundProperty("http.method") ).append( " request for path " ).append( message.getInboundProperty("http.request.path") );

		if ( httpRemoteAddress == null || httpRemoteAddress.trim().length() == 0 ) {
			result.append( " from remote client address " ).append( muleRemoteClientAddress );
		} else {
			result.append( " from remote address " ).append( httpRemoteAddress );
		}

		result.append( ", " ).append( ( containedReqId ? "using" : "assigning" ) ).append( setMessageID ? " messageID=" : " requestId=" ).append( requestId );

		if ( logContentType )
			result.append( ": content-type=" ).append( message.getInboundProperty("content-type") ).append( ": payload-class=" ).append( message.getPayload() != null ? message.getPayload().getClass() : "NULL" );

		if ( logQueryParameters )
			result.append( ": queryParams=" ).append( queryParams );


		logger.info( result.toString() );
		return message;
	}

	public String getRequestIdPrefix() {
		return requestIdPrefix;
	}

	public void setRequestIdPrefix(String requestIdPrefix) {
		this.requestIdPrefix = requestIdPrefix;
	}

	public boolean isLogQueryParameters() {
		return logQueryParameters;
	}

	public void setLogQueryParameters(boolean logQueryParameters) {
		this.logQueryParameters = logQueryParameters;
	}

	public boolean isSetMessageID() {
		return setMessageID;
	}

	public void setSetMessageID(boolean setMessageID) {
		this.setMessageID = setMessageID;
	}

	public boolean isLogContentType() {
		return logContentType;
	}

	public void setLogContentType(boolean logContentType) {
		this.logContentType = logContentType;
	}
}

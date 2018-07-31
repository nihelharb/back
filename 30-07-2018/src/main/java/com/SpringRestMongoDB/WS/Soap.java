package com.SpringRestMongoDB.WS;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.SpringRestMongoDB.exception.IntegrationEnvironmentException;




public class Soap {
	
	public long spentTime=0;
	public static final Logger logger = LoggerFactory.getLogger(Soap.class);
	
	  /**
     * This method send a Soap request to a web service via his endpoint
     *
     * @param requestMessage : the message to send
     * @param endpoint : the endpoint of the web service
     * @param requestFileName
     * @return the response
     */
	
	 public SOAPMessage submitRequestMessageToSoapWebService(SOAPMessage requestMessage, String endpoint) {
	        
	        if (logger.isDebugEnabled()) {
	            logger.debug("Submitting request message to Soap web service with endpoint "+endpoint);
	        }
	        
	        if (requestMessage==null) {
	            throw new NullPointerException("SOAP message cannot be null");
	        }
	        
	        if (endpoint==null) {
	            throw new NullPointerException("Endpoint cannot be null");
	        }
	        
	        SOAPConnectionFactory sfc = null;
	        SOAPConnection connection = null;
	        SOAPMessage responseMessage = null;
	        try {
	            sfc = SOAPConnectionFactory.newInstance();
	            connection = sfc.createConnection();
	            long longDeb = System.currentTimeMillis();
	            responseMessage = connection.call(requestMessage, endpoint);
	            this.spentTime = System.currentTimeMillis() -longDeb ;
	            
	          //  appendToStatFile(requestFileName +" : "+spentTime);
	            
	            
	        } catch (UnsupportedOperationException e) {
	            throw new IntegrationEnvironmentException("Cannot create SOAP connection", e);
	        } catch (SOAPException e) {
	            throw new IntegrationEnvironmentException("Cannot submit request message to Soap web service with endpoint "+endpoint, e);
	        }
	        
	        return responseMessage;
	    }
	 

	 
	 
	 
	 /**
	     * This method creates a Soap message from a file via his url
	     * @param messageFileURL : the url of the file containing the Soap message
	     * @return the Soap message
	     */
	    public SOAPMessage createSOAPMessageFromFile(URL messageFileURL) {
	        
	        if (logger.isDebugEnabled()) {
	            logger.debug("Creating Soap message from file "+messageFileURL);
	        }
	        
	        if (messageFileURL==null) {
	            throw new NullPointerException("Cannot create Soap message from file because URL file is null");
	        }
	        
	        SOAPMessage message = null;
	        
	        try {
	            message = MessageFactory.newInstance().createMessage();
	        } catch (SOAPException e) {
	            throw new IntegrationEnvironmentException("Cannot create SOAP message",e);
	        }
	                
	        SOAPPart soapPart = message.getSOAPPart();

	        try {
	        	StreamSource source = new StreamSource(new FileInputStream(new File(messageFileURL.toURI())));
	            soapPart.setContent(source);
	        } catch (FileNotFoundException e) {
	            throw new IntegrationEnvironmentException("Cannot find the file "+messageFileURL,e);
	        } catch (SOAPException e) {
	            throw new IntegrationEnvironmentException("Cannot load file content of the file "+messageFileURL+" into Soap message",e);    
	        } catch (URISyntaxException e) {
	            throw new IntegrationEnvironmentException("Cannot load file content of the file "+messageFileURL+" into Soap message because URL not valid",e);
	        }
	       
	        return message;
	    }
	 

	
}

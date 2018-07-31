
package com.SpringRestMongoDB.WS;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Assert;

import com.SpringRestMongoDB.Controller.AccountController;
import com.SpringRestMongoDB.exception.IntegrationEnvironmentException;

public class SoapHelperTest {

    private Soap soapHelper = new Soap();
    
	public static final Logger logger = LoggerFactory.getLogger(SoapHelperTest.class);
    
    @Test
    public void testCreateSOAPMessageFromFile() { 
        SOAPMessage message = soapHelper.createSOAPMessageFromFile(this.getClass().getResource("SoapTest.xml"));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            message.writeTo(outputStream);
        } catch (SOAPException e) {
        	logger.error(e.toString());
            Assert.fail();
        } catch (IOException e) {
        	logger.error(e.toString());
            Assert.fail();
        }
        String result = new String(outputStream.toString());
        try {
			Assert.assertEquals(getSoapString(), outputStream.toString("UTF8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
   
   /* 
    @Test(expected=NullPointerException.class)
    public void testCreateSOAPMessageFromFileWithNull() {
        soapHelper.createSOAPMessageFromFile(null);
    }
    */
    /*
    @Test(expected=NullPointerException.class)
    public void testSubmitRequestMessageToSoapWebServiceWithSoapNull() {
        soapHelper.submitRequestMessageToSoapWebService(null,"http://bsb.com", "SoapTest.xml");
    }
    */
    /*
    @Test(expected=NullPointerException.class)
    public void testSubmitRequestMessageToSoapWebServiceWithEndpointNull() {
        
        SOAPMessage message = null;
        
        try {
            message = MessageFactory.newInstance().createMessage();
        } catch (SOAPException e) {
            Assert.fail();
        }
        
        soapHelper.submitRequestMessageToSoapWebService(message,null, "SoapTest.xml");
    }
   */ 
    
    
    @Test(expected=IntegrationEnvironmentException.class)
    public void testSubmitRequestMessageToFakeSoapWebService() {
        
        SOAPMessage message = null;
 
     message =  soapHelper.createSOAPMessageFromFile(this.getClass().getResource("SoapTest.xml"));
      
     SOAPMessage message_response= soapHelper.submitRequestMessageToSoapWebService(message,"http://localhost:37901/Conversion_Monnaie/Converion_Monnaie?WSDL");
     ByteArrayOutputStream out = new ByteArrayOutputStream();
     try {
		message_response.writeTo(out);
	} catch (SOAPException e) {
		
		logger.error(e.toString());
	} catch (IOException e) {
		
		logger.error(e.toString());
	}
     String strMsg = new String(out.toByteArray());
     logger.debug("this is the response ::::::: " +strMsg);
     
    }
    
 
    /*
    @Test
    public void testCreateSOAPMessageFromFileWithWrongURL() {
        try {
            soapHelper.createSOAPMessageFromFile(new URL("http:\\wwww.test"));
        } catch (MalformedURLException e) {
            Assert.fail();
        }
    }
    
    */
    private String getSoapString() {
        String soap = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        		"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns=\"http://www.bsb.com/\">"+
                        "<soapenv:Header/>"+
                           "<soapenv:Body>"+
                               "<ns:data>"+
                                   "<ns:element>�lement</ns:element>"+
                               "</ns:data>"+
                           "</soapenv:Body>"+
                      "</soapenv:Envelope>";
        return soap;
    }
    
   
}

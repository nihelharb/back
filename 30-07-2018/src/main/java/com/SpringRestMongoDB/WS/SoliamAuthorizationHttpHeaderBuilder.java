package com.SpringRestMongoDB.WS;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.util.StringUtils;

import com.SpringRestMongoDB.exception.IntegrationEnvironmentException;



/**
 * This class builds the authorization http header needed in the SOAP request for Soliam Data Web service.
 *
 *
 */
public class SoliamAuthorizationHttpHeaderBuilder {

	public static final Logger logger = LoggerFactory.getLogger(Soap.class);
    
    public static final String HEADER_BASIC = "Basic";
    public static final String HEADER_NAME = "Authorization";
    
    /**
     * This method creates the string representation of the authorization http
     * header used during the web service call. The header is the base 64 of the
     * following string 'Basic [entity]/[user]:[password]
     * @param entity : the entity of the user
     * @param user : the user
     * @param pwd : the password of the user
     * @return the authorization http header
     */
    public String createAuthorizationHttpHeader(String entity, String user, String pwd) {

        if (logger.isDebugEnabled()) {
            logger.debug("Creating authorization http header for entity = " + entity + " / user = " + user + " / pwd = " + pwd);
        }

        if (StringUtils.isEmpty(entity)) {
            throw new NullPointerException("Entity cannot be null");
        }
        
        if (StringUtils.isEmpty(user)) {
            throw new NullPointerException("User cannot be null");
        }
        
        if (StringUtils.isEmpty(pwd)) {
            throw new NullPointerException("Password cannot be null");
        }

        StringBuffer authorization = null;
        String authorizationHeader = null;

        StringBuffer buffer = new StringBuffer();
        buffer.append(entity);
        buffer.append("/");
        buffer.append(user);
        buffer.append(":");
        buffer.append(pwd);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        StringReader in = new StringReader(buffer.toString());
        OutputStreamWriter writer = new OutputStreamWriter(out);

        char[] charBuffer = new char[1024];
        int read = 0;
        try {
            while ((read = in.read(charBuffer)) != -1) {
                writer.write(charBuffer, 0, read);
            }
            writer.close();
        } catch (IOException e) {
            // I hate checked exceptions !!!
            throw new IntegrationEnvironmentException("Cannot create authorization header", e);
        }

        // example : Basic BSB/INI:INI (BSB/INI:INI in Base64)
        authorization = new StringBuffer();
        authorization.append(HEADER_BASIC);
        authorization.append(" ");
        authorization.append(Base64.encodeBase64String(out.toByteArray()).trim());

        authorizationHeader = authorization.toString();

        if (logger.isDebugEnabled()) {
            logger.debug("Authorization http header  = '" + authorizationHeader + "'");
        }

        return authorizationHeader;
    }
}

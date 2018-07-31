 /*******************************************************************************
  * Copyright (C) 2016 VERMEG. All rights reserved. This software is the
  * confidential and proprietary information of VERMEG
  * ("Confidential Information")
  *******************************************************************************/
package com.SpringRestMongoDB.exception;

/**
 * This class is the unchecked exception for the integration environment. Sonar
 * doesn't like to use directly RuntimeException.
 * 
 * @author SBL
 */
public class IntegrationEnvironmentException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public IntegrationEnvironmentException(String message, Throwable e) {
        super(message, e);
    }
    
    public IntegrationEnvironmentException(String message) {
        super(message);
    }

    public IntegrationEnvironmentException(Throwable e) {
        super(e);
    }
}

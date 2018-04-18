/*
 * Copyright (C) 2003 by SkillSoft, Inc.
 * All Rights Reserved.
 *
 */
package @namespace@;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.log4j.Logger;
import org.apache.ws.security.WSPasswordCallback;


/**
 * PWCallback for the web service client 
 * This class must be implemented for WSS4J. It's name is referred in 
 * olsa_client.wsdd file
 *  
 * @author Zakir.Magdum
 */
public class PWCallback implements CallbackHandler {
	// set the config/olsa_user.properties right
	public static String customerId = "comp62106sr1_bad"  ; // dummy value
	public static String token = "pfUk3VhLClYxnzx_bad" ; // dummy value
	private static Logger logger = null ;
	static {
    	logger = Logger.getLogger( "skillsoft.olsa" ); 
		customerId = OlsaClientConfig.getCustomerId() ;
		token = OlsaClientConfig.getSharedSecret() ;
    	logger.debug("Using customer id " + customerId + " and shared secret " + token ) ;
	}

    /**
     * Need to implement this method to provide password 
     * in UsernameToken profile 
     * @see javax.security.auth.callback.CallbackHandler#handle(javax.security.auth.callback.Callback[])
     */
    public void handle(Callback[] callbacks) throws IOException,
                    UnsupportedCallbackException {
        for (int i = 0; i < callbacks.length; i++) {
            if (callbacks[i] instanceof WSPasswordCallback) {
                WSPasswordCallback pc = (WSPasswordCallback)callbacks[i];
                // set the password given a username
                if ( customerId.equals(pc.getIdentifer())) {
                    pc.setPassword(token);
                } else {
                	logger.error("Customer ids are different " + customerId + " != " + pc.getIdentifer()) ;
                	throw new IOException("Customer Id does not match to one set in config/olsa_user.properties") ;
                }
            } else {
                throw new UnsupportedCallbackException(callbacks[i], "Unrecognized Callback");
            }
        }
    }
}
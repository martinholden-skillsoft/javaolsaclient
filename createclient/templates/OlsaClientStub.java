/*
 * Copyright (C) 2003-2004 SkillSoft, PLC.  
 * All Rights Reserved.
 */
package @namespace@;

import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.rpc.Service;
import javax.xml.soap.SOAPException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

import org.apache.axis.AxisFault;
import org.apache.axis.Message;
import org.apache.axis.client.Call;
import org.apache.axis.description.OperationDesc;
import org.apache.axis.description.ParameterDesc;
import org.apache.axis.message.RPCElement;
import org.apache.axis.message.RPCParam;
import org.apache.axis.message.SOAPBodyElement;
import org.apache.axis.message.SOAPEnvelope;
import org.dom4j.io.DocumentResult;
import org.dom4j.io.DocumentSource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;

import @namespace@.OlsaSoapBindingStub;
import @namespace@.VoidResponse;

/**
 * OlsaClientStub class.  This class helps to make web service call and 
 * return the result as xml string
 * Add all the function whose return value need to be a xml string rather 
 * than java object. 
 * This class requires some changes to be made generated  OlsaSoapBindingStub.java 
 * and OlsaServiceLocator.java. See build.xml for details 
 * 
 * @author Zakir.Magdum
 */
public class OlsaClientStub extends OlsaSoapBindingStub {
    private static final String fixSearchParamsXsl =  
        "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">" +
    		"<xsl:output method=\"xml\" omit-xml-declaration=\"yes\"/>" +
    		"<xsl:template match=\"*[local-name() != 'searchParams']\">" +
    			"<xsl:element name=\"{local-name()}\">" +
    				"<xsl:apply-templates select=\"@*\"/>" +
    				"<xsl:apply-templates select=\"node()\"/>" +
    			"</xsl:element>" +
    		"</xsl:template>" +
    		"<xsl:template match=\"@*\">" +
    			"<xsl:attribute name=\"{local-name()}\"><xsl:value-of select=\".\"/></xsl:attribute>" +
    		"</xsl:template>" +
    		"<xsl:template match=\"/\">" +
    			"<xsl:copy>" +
    				"<xsl:apply-templates/>" +
    			"</xsl:copy>" +
    		"</xsl:template>" +
    		"<xsl:template match=\"*[local-name()='SearchParams']\">" +
    			"<xsl:element name=\"searchParams\">" +
    				"<xsl:apply-templates select=\"@*\"/>" +
    				"<xsl:apply-templates select=\"node()\"/>" +
    			"</xsl:element>" +
    		"</xsl:template>" +
    		"<xsl:template match=\"comment() | processing-instruction() | text()\">" +
    			"<xsl:copy/>" +
    		"</xsl:template>" +
    	"</xsl:stylesheet>" ;

	public OlsaClientStub() throws AxisFault {
		super();
	}

	public OlsaClientStub(Service service) throws AxisFault {
		super(service);
	}

	public OlsaClientStub(URL endpointURL, Service service) throws AxisFault {
		super(endpointURL, service);
	}

	/** SL_FederatedSearchXml
     * Makes a SL_FederatedSearch call to the web service   
     *
     * @param parameters - Parameters needed for the call
     * @return XML string of SOAPBody return from the function 
     * if the operation failed.
     * @throws 	java.rmi.RemoteException
     * 			skillsoft.olsa.client.GeneralFault
     */
    public String SL_FederatedSearchXml(@namespace@.FederatedSearchRequest parameters) throws java.rmi.RemoteException, @namespace@.GeneralFault {
    	// _operations[13] is hairy need to be matched with the right number 
    	// in generated OlsaSoapBindingStub.java
    	return makeCall( _operations[13], parameters ) ;
    }	
    
    public String SL_DetailedSearchXml(@namespace@.DetailedSearchRequest parameters) throws java.rmi.RemoteException, @namespace@.GeneralFault {
    	return makeCall( _operations[14], parameters ) ;
    }

    public String SL_RelatedSearchXml(@namespace@.RelatedSearchRequest parameters) throws java.rmi.RemoteException, @namespace@.GeneralFault {
    	return makeCall( _operations[15], parameters ) ;
    }

    public String SL_PaginateSearchXml(@namespace@.PaginateSearchRequest parameters) throws java.rmi.RemoteException, @namespace@.GeneralFault {
    	return makeCall( _operations[16], parameters ) ;
    }

    public String SL_GetAttributesXml(@namespace@.GetAttributesRequest parameters) throws java.rmi.RemoteException, @namespace@.GeneralFault {
    	return makeCall( _operations[17], parameters ) ;
    }

    public String SL_GetSearchParameterXml(@namespace@.GetSearchParameterRequest parameters) throws java.rmi.RemoteException, @namespace@.GeneralFault {
    	return makeCall( _operations[18], parameters ) ;
    }

    public String SL_GetAssetDetailXml(@namespace@.GetAssetDetailRequest parameters) throws java.rmi.RemoteException, @namespace@.GeneralFault {
    	return makeCall( _operations[20], parameters ) ;
    }

    public String UD_GetAssetResultsXml(@namespace@.GetAssetResultsRequest parameters) throws java.rmi.RemoteException, @namespace@.GeneralFault {
    	return makeCall( _operations[21], parameters ) ;
    }
    
    public String AS_GetSubscriptionDataXml(@namespace@.GetSubscriptionDataRequest parameters) throws java.rmi.RemoteException, @namespace@.GeneralFault {
    	return makeCall( _operations[33], parameters ) ;
    }
    
    /**
     * This method is responsible for getting the raw xml string from
     * AI_GetXmlAssetMetaData WS. NOTE: the operation order number used here
     * MUST BE CHANGED accordingly and properly every time it is changed in
     * OlsaSoapBindingStub class due to regenerating of stub/client classes based on
     * WSDL file
     * 
     * @param parameters Request parameters  
     * @return XML string of metadata 
     * @throws java.rmi.RemoteException
     * @throws @namespace@.GeneralFault
     */
    public String AI_GetXmlAssetMetaDataString(@namespace@.GetXmlAssetMetaDataRequest parameters) throws java.rmi.RemoteException, @namespace@.GeneralFault {
        return makeCall( _operations[63], parameters ) ;
    }
  
	/** makeCall
     * Makes a call to the web service   
     *
     * @param oper - Description of operation that need to call
     * @param parameters - Parameters needed for the call
     * @return XML string of SOAPBody return from the function 
     * if the operation failed.
     * @throws 	java.rmi.RemoteException
     * 			@namespace@.GeneralFault
     */
    private String makeCall( OperationDesc oper, Object parameters) throws java.rmi.RemoteException, @namespace@.GeneralFault {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(oper);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", oper.getName()));

        setRequestHeaders(_call);
        setAttachments(_call);
        try {
    	 	ArrayList ps = oper.getParameters();
            RPCParam rpcParam = null;
            // This works as we are using document style and there is only 
            // one parameter
            for (int i = 0; i < ps.size(); i++) {
                ParameterDesc param = (ParameterDesc)ps.get(i);
                if (param.getMode() != ParameterDesc.OUT) {
                    QName paramQName = param.getQName();
                    rpcParam = new RPCParam(paramQName.getNamespaceURI(),
                                                paramQName.getLocalPart(),
                                                parameters);
                    rpcParam.setParamDesc(param);
                }
            }
            if ( null == rpcParam ) throw new AxisFault("Could not construct RPCParam") ;
        	
        	RPCElement body = new RPCElement("", oper.getName(), new java.lang.Object[] {rpcParam} ) ;
        	SOAPEnvelope reqEnv = new SOAPEnvelope(_call.getMessageContext().getSOAPConstants(),
            		_call.getMessageContext().getSchemaVersion());
        	Message  reqMsg = new Message( reqEnv );
	        try {
				body.setEncodingStyle(_call.getEncodingStyle());
			} catch (SOAPException e) {
				throw AxisFault.makeFault( e ) ;
			}
			reqEnv.addBodyElement(body);
			reqEnv.setMessageType(Message.REQUEST);
       
			java.lang.Object _resp = _call.invoke(reqMsg);

			if (_resp instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException)_resp;
			}
			else {
				extractAttachments(_call);
        		// make sure the response is of the type SOAPEnvelope 
        		SOAPEnvelope envResponse = ( SOAPEnvelope ) _resp ;
        		try {
        			// may need to handle case where body is coming as empty
        			return envResponse.getBody().getFirstChild().toString() ;
				} catch (Exception e) {
					throw AxisFault.makeFault( e ) ;
				}
			}
        } catch (org.apache.axis.AxisFault axisFaultException) {
        	if (axisFaultException.detail != null) {
        		if (axisFaultException.detail instanceof java.rmi.RemoteException) {
        			throw (java.rmi.RemoteException) axisFaultException.detail;
        		}
        		if (axisFaultException.detail instanceof @namespace@.GeneralFault) {
        			throw (@namespace@.GeneralFault) axisFaultException.detail;
        		}
        	}
        	throw axisFaultException;
        }
    }	

	/** setupCall
     * Initialize call object with operationdescription   
     *
     * @param oper - Description of operation that need to call
	 * @return org.apache.axis.client.Call
     * if the operation failed.
     * @throws 	java.rmi.RemoteException
     * 			@namespace@.GeneralFault
	 *			org.apache.axis.NoEndPointException
     */
    private Call setupCall( OperationDesc oper) throws java.rmi.RemoteException, @namespace@.GeneralFault {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        Call _call = createCall();
        _call.setOperation(oper);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", oper.getName()));

        setRequestHeaders(_call);
        setAttachments(_call);
        return _call ;
    }
    	/** makeSetCall
     * Makes a call to the web service with customerid and xml provided.  
     *
     * @param customerId - The OLSA customer id
     * @param xml - An XML representation of the parameters to set
     * @return @namespace@.VoidResponse
     * if the operation failed.
    	 * @throws Exception 
     */
    public @namespace@.VoidResponse SL_SetSearchParameterXml(String customerId, String xml) throws Exception {
    	OperationDesc oper = _operations[19] ;
    	Call _call = setupCall(oper) ;
    	
    	try {
    	 	// build soap enevelope
	        SOAPBodyElement[] input = new SOAPBodyElement[1];
			try {
		        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		        Document doc            = builder.newDocument();
		        Element e1 = doc.createElementNS("", oper.getName());
		        Element e2 = doc.createElementNS("", "customerId");
		        e1.appendChild(e2) ;
		        Text cdata = doc.createTextNode(customerId);
		        e2.appendChild(cdata);
		        
		        // strip out namespaces from xml and change the SearchParams to searchParams
    			org.dom4j.Document docSrc = org.dom4j.DocumentHelper.parseText(xml);
    			TransformerFactory factory = TransformerFactory.newInstance();
    			Transformer transformer = factory.newTransformer( new StreamSource( new StringReader(fixSearchParamsXsl) ) );
//    			Transformer transformer = factory.newTransformer( new StreamSource( "D:/Projects/BigHorn/xslt/FixSetSearchParams.xsl" ) );
    			// Fix the document for searchParams
    			DocumentSource source = new DocumentSource( docSrc );
    			DocumentResult result = new DocumentResult();
    			transformer.transform( source, result );
    			// return the transformed document
    			org.dom4j.Document transformedDoc = result.getDocument();
    			String str = transformedDoc.asXML() ;
    		        
		        Document doc1 = builder.parse( new InputSource(new StringReader(str)));
		        e1.appendChild(doc.importNode(doc1.getDocumentElement(),true)) ;        
		        input[0] = new SOAPBodyElement(e1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw AxisFault.makeFault( e ) ;
			} 
     	
	        Vector elems = (Vector)_call.invoke(input);
	        SOAPBodyElement elem  = null ;
	        elem = (SOAPBodyElement) elems.get(0);
	        if ( null == elem ) {
	        	throw new AxisFault("NULL response received") ;
	        }
	        if ( elem.getName().equals("VoidResponse")) {
	        	return new VoidResponse() ;
	        }
	        throw new AxisFault("Invalid response received " + elem.getAsString()) ;
	        
        } catch (org.apache.axis.AxisFault axisFaultException) {
        	if (axisFaultException.detail != null) {
        		if (axisFaultException.detail instanceof java.rmi.RemoteException) {
        			throw (java.rmi.RemoteException) axisFaultException.detail;
        		}
        		if (axisFaultException.detail instanceof @namespace@.GeneralFault) {
        			throw (@namespace@.GeneralFault) axisFaultException.detail;
        		}
        	}
        	throw axisFaultException;
        }
    }	
    

}

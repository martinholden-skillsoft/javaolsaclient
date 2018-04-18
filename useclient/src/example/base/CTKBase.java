package example.base;

import java.util.Map;
import java.util.TreeMap;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.skillsoft.olsa.client.OlsaClientConfig;
import com.skillsoft.olsa.client.OlsaClientStub;
import com.skillsoft.olsa.client.OlsaServiceLocator;
import com.skillsoft.olsa.client.ProfileFieldValue;
import com.skillsoft.olsa.client.ProfileFieldValues;

import example.util.CTKConstants;
import example.util.StringUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.ws.security.handler.WSHandlerConstants;

/**
 * This is the base class for the client toolkit. It has the
 * setup methods and common methods for the different examples.
 * All the client toolkit example classes finally extend this
 * class
 *
 * @author Shweta Sidhu
 *
 */
public class CTKBase {

	private Properties properties = new Properties();
	private Properties usageProps = new Properties();
	private static Logger ctkLogger = Logger.getLogger("example");
	private static Logger ctkSoapLogger = Logger.getLogger("org.apache.axis.transport");
	private int debugLevel = 0;

	/**
	 * Polling interval during a cycle. This is set to 1 minute. An appropriate
	 * interval is in the order of minutes.
	 */
	protected static final long POLLING_INTERVAL = 60 * 1000;

	/**
	 * returns the usage of the web service call.
	 *
	 * @param webservice
	 */
	public void usage (String webservice) {
		InputStream is = null;
		try {
			is = new FileInputStream(CTKConstants.USAGE_FILE);
			usageProps.load(is);
		} catch (FileNotFoundException fe) {
			ctkLogger.fatal("Usage File Missing : " + CTKConstants.USAGE_FILE);
		} catch (Exception e) {
			ctkLogger.fatal("Invalid Usage File : " + CTKConstants.USAGE_FILE);
		} finally {
			try {
				if(is != null)
					is.close();
			} catch(Exception e) {
				// ignore the exception
			}
		}
		if(StringUtils.isNullOrEmpty(webservice)) {
			getCtkLogger().info("Usage for the base class CTKBase is " + usageProps.getProperty(CTKConstants.CTK_BASE));
			System.out.println("Usage for the base class CTKBase is " + usageProps.getProperty(CTKConstants.CTK_BASE));
		} else {
			getCtkLogger().info("Usage for web service "+ webservice + " is " + usageProps.getProperty(webservice.toLowerCase()));
			System.out.println("Usage for web service "+ webservice + " is " + usageProps.getProperty(webservice.toLowerCase()));
		}
	}

	/**
	 * Reads the parameters from the properties file and overwrites the parameters
	 * passed in the command line arguments.
	 * It populates the hash table with key and value pairs.
	 *
	 * @param webservice
	 * @param args
	 */
	public void readParameters (String webservice, String args[]) {

		readFromPropertiesFile();

		/*
		 * If command line arguments are not passed to the calling
		 * web service example then the parameters defined in the
		 * properties file are used
		 */
		if(args.length > 0 ) {
			overwriteWithCommandLineArguments(webservice, args);
		}

	}

 	/**
	 * This method is used to set the properties object based on the
	 * properties read from the properties file
	 */
	private void readFromPropertiesFile() {
		InputStream is = null;
		try {
			is = new FileInputStream(CTKConstants.PROP_FILE);
			properties.load(is);
		} catch (FileNotFoundException fe) {
			ctkLogger.fatal("Configuration File Missing : " + CTKConstants.PROP_FILE);
		} catch (Exception e) {
			ctkLogger.fatal("Invalid Configuration File : " + CTKConstants.PROP_FILE);
		} finally {
			try {
				if(is != null)
					is.close();
			} catch(Exception e) {
				// ignore the exception
			}
		}
	}

	/**
	 * This method is used to overwrite the web service related
	 * properties defined in the properties file by the command
	 * line arguments specified
	 *
	 * @param webservice
	 * @param args
	 */
	private void overwriteWithCommandLineArguments(String webservice, String[] args) {

		for(int i = 0;i<args.length;i++) {
			/*
			 * If the argument passed is not separated by the delimiter
			 * then ignore the argument or if the delimiter is the last
			 * character in the string then also ignore the argument.
			 */
			if(args[i].indexOf(CTKConstants.COMMAND_LINE_DELIMITER) != -1
					&& args[i].indexOf(CTKConstants.COMMAND_LINE_DELIMITER) != (args[i].length()-1)) {
				String key = webservice + "." + args[i].substring(0,args[i].indexOf(CTKConstants.COMMAND_LINE_DELIMITER));
				String value = args[i].substring(args[i].indexOf(CTKConstants.COMMAND_LINE_DELIMITER)+1);
				if(properties.containsKey(key)) {
					properties.remove(key);
				}
				properties.put(key,value);
			}
		}
	}


	/**
	 * This method is used to retrieve the value for the parameter. It
	 * returns null if parameter is not found in the property file.
	 *
	 * @param className
	 * @param paramName
	 * @return String returns the value for the parameter
	 * @throws Exception
	 */
	public String retrieveParameter(String className, String paramName) {
		String paramValue = null;
		String key = null;
		int inLoop = 0;
		String parameter = className + CTKConstants.PROPERTY_DELIMITER + paramName;
		parameter = parameter.substring(0,parameter.lastIndexOf(CTKConstants.PROPERTY_DELIMITER));
		boolean found = false;
		while (!found) {
			if(parameter.trim().length() == 0)
				key = paramName;
			else
				key = parameter + CTKConstants.PROPERTY_DELIMITER + paramName;

			//CR#64114(Have the ability to pass command line arguments through Ant)
			String sysProp = System.getProperty(key) ;
			if (sysProp !=null){
	    		paramValue = sysProp;
				found = true;
	    	}
	    	else {
	    		if(properties.containsKey(key)) {
					paramValue = properties.getProperty(key);
					found = true;
				} else {
					if (parameter.lastIndexOf(CTKConstants.PROPERTY_DELIMITER) > 0) {
						parameter = parameter.substring(0,parameter.lastIndexOf(CTKConstants.PROPERTY_DELIMITER));
					} else {
						parameter = "";
						if(inLoop > 0) {
							break;
						}
						inLoop++;
					}
				}
	    	}
		}
		if(paramValue != null)
			return paramValue.trim();
		else
			return null;
	}

	/**
	 * This method is used to check the parameters
	 *
	 * @param params
	 * @throws Exception
	 */
	public void checkParameters (String[] params) throws Exception {
		if (params == null || params.length == 0) {
			throw new Exception (CTKConstants.INVALID_PROP_FILE);
		}
		for(int i = 0; i < params.length; i++) {
			if(StringUtils.isNullOrEmpty(params[i])) {
				throw new Exception (CTKConstants.INVALID_PROP_FILE);
			}
		}
	}


	/**
	 * This method will set the debug level class variable. It
	 * takes the class name as parameter and retrieves the
	 * value for the debug parameter set at the example class
	 * level or at a higher level.
	 *
	 * @param className
	 */
	protected void setDebugLevel (String className) {
		String debug = retrieveParameter (className,CTKConstants.DEBUG);
		// default level
		int debugLevel = 0;
		try {
			debugLevel = Integer.parseInt(debug);
		} catch (Exception e) {
			//if debug parameter is not passed  or not defined in the
			// properties file then it will have a default value of "0"
			debugLevel = 0;
		}

		this.debugLevel = debugLevel;
		setupLogger();
	}


	/**
	 * This method will retrieve the value of the class variable
	 * debugLevel
	 *
	 * @return int the debug level set
	 */
	protected int getDebugLevel () {
		return debugLevel;
	}


	/**
	 * This method is used to setup the console appender
	 * logger in the CTK for logging messages and soap
	 * messages.
	 *
	 * The debug flag can either be passed as command line argument or set
	 * in the property file.
	 *
	 * The allowed value of the debug flag is
	 * 0: log just the messages
	 * 1: log just the soap messages
	 * 2: log both messages and soap messages
	 */
	public void setupLogger () {
		/*
		 * Debug level of 0: print out just the messages
		 * Debug level of 1: print out just the soap messages
		 * Debug level of 2: print out messages + soap messages
		 */
		switch(debugLevel) {
			case 0:
		 		if (ctkLogger != null)
		 			ctkLogger.setLevel(Level.DEBUG);
		 		if (ctkSoapLogger != null)
		 			ctkSoapLogger.setLevel(Level.OFF);
				break;
			case 1:
		 		if (ctkSoapLogger != null)
		 			ctkSoapLogger.setLevel(Level.DEBUG);
		 		if (ctkLogger != null)
		 			ctkLogger.setLevel(Level.OFF);
				break;
			case 2:
		 		if (ctkLogger != null)
		 			ctkLogger.setLevel(Level.DEBUG);
		 		if (ctkSoapLogger != null)
		 			ctkSoapLogger.setLevel(Level.DEBUG);
				break;
		}
	}

	/**
     * This method generates the client stub and returns the binding object
     *
     * @return OlsaClientStub
     * @throws Exception
     */
 	public OlsaClientStub getBinding() throws Exception {
		Date start = new Date();
  	    OlsaClientStub binding;
 	    try {
 	    	OlsaServiceLocator locator = new OlsaServiceLocator() ;
 	    	if ( null != OlsaClientConfig.getEndpoint() && OlsaClientConfig.getEndpoint().length() > 0 )
 		        binding = (OlsaClientStub) locator.getOlsa(new URL(OlsaClientConfig.getEndpoint()));
 	    	else
 	    		binding = (OlsaClientStub) locator.getOlsa();
 	    	if ( OlsaClientConfig.getEndpoint().indexOf("https") != -1 ) {
 	    		// OLSA server may be using the temporary certificate created by
 	    		// keytool. Need to change the factory
 				System.setProperty("axis.socketSecureFactory", "org.apache.axis.components.net.SunFakeTrustSocketFactory");
 	    	}
 	    }
 	    catch (javax.xml.rpc.ServiceException jre) {
 	        if(jre.getLinkedCause()!=null)
 	            jre.getLinkedCause().printStackTrace();
 	        throw new Exception("JAX-RPC ServiceException caught: " + jre);
 	    }
 	    if ( null == binding ) throw new Exception("binding is null");
         // Time out after a minute
         binding.setTimeout(60000);
         binding._setProperty(WSHandlerConstants.USER, OlsaClientConfig.getCustomerId()) ;
		 
		 //binding._setProperty(org.apache.axis.client.Call.STREAMING_PROPERTY, Boolean.TRUE);
		 
		 Date end = new Date();
		 
		 getCtkLogger().info("Creating Olsa AXIS Client. Time consumed: " +(end.getTime() - start.getTime())+"ms");
         return binding ;
 	}


 	/**
 	 * This method returns the CTK Logger which writes
 	 * normal debug messages
 	 *
 	 * @return Logger
 	 */
 	public static Logger getCtkLogger() {
 		return ctkLogger;
 	}


 	/**
 	 * This method returns the CTK Logger which writes
 	 * SOAP messages
 	 *
 	 * @return Logger
 	 */
 	public static Logger getCtkSoapLogger() {
 		return ctkSoapLogger;
 	}


 	/**
 	 * This method is used to ask the question and pass the reply
 	 * when the scenario example is called in interactive mode.
 	 * Please Note that not all scenario examples support interactive
 	 * mode.
 	 *
 	 * @param br
 	 * @param ques
 	 * @return String
 	 * @throws Exception
 	 */
 	public String askInInteractiveMode (BufferedReader br, String ques) throws Exception {
 		String ans=null;
 		System.out.println(ques);
 		ans = br.readLine();
 		return ans;
 	}

	/**
	 * It is used to call usage of the web service. If
	 * command line argument is not specified then it
	 * returns the usage for the CTK BASE class.
	 *
	 * @param args
	 */
	public static void main(String[] args) {

		CTKBase ctkBase = new CTKBase();
		if(args.length == 0 ) {
			ctkBase.usage(CTKConstants.CTK_BASE);
		} else {
			ctkBase.usage(args[0]);
		}
	}

    /**
     * Process user profile field values
     * Input appears like:
     *       <ProfileFieldValues>
     *           <FieldValue id="{FieldId1}"><Value>{FieldValue1}</Value></FieldValue>
     *           <FieldValue id="{FieldId2}"><Value>{FieldValue21}</Value><Value>{FieldValue22}</Value></FieldValue>
     *       </ProfileFieldValues>
     */
    protected ProfileFieldValues getUserProfileFieldValues(String profileFieldValues) {
        if (profileFieldValues != null) {
            List<ProfileFieldValue> values = new ArrayList<ProfileFieldValue>();

            Map<String, List<String>> valuesMap = parseProfileFieldValues(profileFieldValues);
            Set<String> keys = valuesMap.keySet();

            for (String key : keys) {
                if (valuesMap.get(key) != null && !valuesMap.get(key).isEmpty()) {
                    ProfileFieldValue value = new ProfileFieldValue();
                    value.setId(key);
                    value.setValue(valuesMap.get(key).toArray(new String[valuesMap.get(key).size()]));

                    values.add(value);
                }
            }

            ProfileFieldValues pfValues = new ProfileFieldValues(values.toArray(new ProfileFieldValue[values.size()]));

            return pfValues;
        }
        else {
            return null;
        }
    }

    /**
     * Parse XML's string representation into a key-value map.
     *
     */
    private Map<String, List<String>> parseProfileFieldValues(final String xmlString)
    {
        Map<String, List<String>> profileFieldValuesMap = new TreeMap<String, List<String>>(String.CASE_INSENSITIVE_ORDER);

        if (xmlString != null && xmlString.trim().length() > 0) {
            try {
                final Document resultDoc = DocumentHelper.parseText(xmlString);
                Element rootElement = resultDoc.getRootElement();

                List<Element> profileFields = rootElement.elements();

                for (Element profileField : profileFields) {
                    String fieldId = profileField.attributeValue("id");

                    List<Element> valueFields = profileField.elements();

                    if (valueFields != null && !valueFields.isEmpty()) {
                        List<String> values = new ArrayList<String>();

                        for (Element valueField : valueFields) {
                            if (valueField.getText() != null) {
                                values.add(valueField.getText().trim());
                            }
                        }

                        profileFieldValuesMap.put(fieldId, values);
                    }
                }
            } catch (DocumentException ex) {
                ex.printStackTrace();
            }
        }

        return profileFieldValuesMap;
    }
}

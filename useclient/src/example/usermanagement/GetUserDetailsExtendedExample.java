package example.usermanagement;

import example.base.CTKBase;
import com.skillsoft.olsa.client.OlsaClientConfig;
import com.skillsoft.olsa.client.GeneralFault;
import com.skillsoft.olsa.client.GetUserDetailsExtendedRequest;
import com.skillsoft.olsa.client.GetUserDetailsExtendedResponse;
import com.skillsoft.olsa.client.UserExtended;
import com.skillsoft.olsa.client.UserGroupExtended;
import com.skillsoft.olsa.client.ProfileFieldValue;
import com.skillsoft.olsa.client.ObjectNotFoundFault;
import com.skillsoft.olsa.client.OlsaClientStub;
import example.util.CTKConstants;
import example.util.StringUtils;
import java.util.Date;
/**
 * This class is the example class for UM_GetUserDetailsExtended
 * web service call.
 *
 * @author jchen
 * @since SP80P20
 *
 */
public class GetUserDetailsExtendedExample extends CTKBase {

    private String username = null;
    private String firstName = null;
    private String lastName = null;
    private String emailAddress = null;
    private String orgCode = null;
	private int repeat = 1;

    /**
     * This is just a setup method. This method has setup methods
     * involved for successfully executing the CTK example.
     *
     * @param args
     * @throws Exception
     */
    private void setup(String []args) throws Exception {
        readParameters(this.getClass().getName(), args);
        username = retrieveParameter (this.getClass().getName(), CTKConstants.UserManagement.USERNAME);
        firstName = retrieveParameter (this.getClass().getName(), CTKConstants.UserManagement.FIRSTNAME);
        lastName = retrieveParameter (this.getClass().getName(), CTKConstants.UserManagement.LASTNAME);
        emailAddress = retrieveParameter (this.getClass().getName(), CTKConstants.UserManagement.EMAIL);
        orgCode = retrieveParameter (this.getClass().getName(), CTKConstants.UserManagement.GROUPCODE);
		repeat =  Integer.parseInt(retrieveParameter (this.getClass().getName(), CTKConstants.REPEAT));
		
        setDebugLevel(this.getClass().getName());
		
		//reuseStub = getBinding();
    }

    /**
     * This is the entry point for executing this CTK example class
     *
     * @param args
     */
    public static void main(String[] args) {

        try {
            GetUserDetailsExtendedExample example = new GetUserDetailsExtendedExample();
            example.setup(args);
			
            //example.getUserDetails(null);
			
			example.pump(null);
			
        } catch (Exception ex) {
            System.out.println("Exception is " + ex);
            ex.printStackTrace();
        }
    }

	protected void getUserDetails(OlsaClientStub stub) throws Exception {
		getUserDetails(stub, true);
	}
	
    /**
     * This method is used to get user's details.
     * This method will call the web service
     * UM_GetUserDetailsExtended to perform get user's details operation.
     *
     * @throws Exception
     */
    protected void getUserDetails(OlsaClientStub stub, boolean showdata) throws Exception {
		Date start = new Date();
		
		if (stub == null) {
			stub = getBinding();
		}

        GetUserDetailsExtendedRequest req;
        try {
            req = new GetUserDetailsExtendedRequest();
            req.setCustomerId(OlsaClientConfig.getCustomerId());
			
			if(!StringUtils.isNullOrEmpty(username)) {
				req.setUsername(username);
			}
			if(!StringUtils.isNullOrEmpty(firstName)) {
				req.setFirstName(firstName);
			}
			if(!StringUtils.isNullOrEmpty(lastName)) {
				req.setLastName(lastName);
			}
			if(!StringUtils.isNullOrEmpty(emailAddress)) {
				req.setEmail(emailAddress);
			}
			if(!StringUtils.isNullOrEmpty(orgCode)) {
				req.setOrgCode(orgCode);
			}

            GetUserDetailsExtendedResponse res = stub.UM_GetUserDetailsExtended(req);
			UserExtended[] users = res.getUser();
			Date end = new Date();
			
			getCtkLogger().info("UM_GetUserDetailsExtended. Time consumed: " +(end.getTime() - start.getTime()) + "ms");

			
            
			if (showdata) {
            if(users != null) {
                StringBuilder sb = new StringBuilder();
				for (UserExtended user : users) {
				    sb.append("\n\nDetails of user '" + user.getUsername() + "' has been retrieved successfully!");

					sb.append("\nUser profile fields:\n");

					if (user.getProfileFieldValues() != null) {
						ProfileFieldValue[] pfValues = user.getProfileFieldValues().getFieldValue();

						if (pfValues != null) {
							for (ProfileFieldValue pfValue : pfValues) {
								String[] values = pfValue.getValue();

								if (values != null && values.length > 0) {
									for (String value : values) {
										sb.append(pfValue.getId()).append("=").append(value).append("\n");
									}
								}
								else {
									sb.append(pfValue.getId()).append("=\n");
								}
							}
						}
					}

					sb.append("\nUser is member of the following groups:\n");

					for (UserGroupExtended ug : user.getUserGroup()) {
						sb.append("OrgCode=").append(ug.getOrgCode()).append(", ")
						  .append("Title=").append(ug.getTitle()).append("\n");
					}
				}
                getCtkLogger().info(sb.toString());
			}
            }
        } catch (ObjectNotFoundFault e1) {
            // If user does not exist then we get this fault
            getCtkLogger().error("User " + username + " does not exist. Service UM_GetUserDetailsExtended failed!");
            e1.printStackTrace();
        } catch (GeneralFault e2) {
            getCtkLogger().error("GeneralFaultMessage Exception caught: " + e2);
            e2.printStackTrace();
        } catch (Exception e3) {
            getCtkLogger().error("Exception caught: " + e3);
            e3.printStackTrace();
        }
    }

	protected void pump(OlsaClientStub binding) throws Exception  {
        Date start = new Date();
		for(int i=0; i<repeat; i++){
			getUserDetails(binding, false);
        }
        Date end = new Date();
		long elapsed = end.getTime() - start.getTime();
		long average = elapsed/repeat;
		getCtkLogger().info("Count: " + repeat + "\tTime consumed: " +elapsed + "ms"+ "\tAverage: " +average + "ms");
    }
	
}

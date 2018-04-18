package example.usermanagement;

import example.base.CTKBase;
import com.skillsoft.olsa.client.OlsaClientConfig;
import com.skillsoft.olsa.client.GeneralFault;
import com.skillsoft.olsa.client.CreateUserExtendedRequest;
import com.skillsoft.olsa.client.VoidResponse;
import com.skillsoft.olsa.client.UserExtended;
import com.skillsoft.olsa.client.UserGroupExtended;
import com.skillsoft.olsa.client.ProfileFieldValue;
import com.skillsoft.olsa.client.ObjectExistsFault;
import com.skillsoft.olsa.client.OlsaClientStub;
import example.util.CTKConstants;
import example.util.StringUtils;
import java.util.Date;


public class CreateUserExtendedExample extends CTKBase {

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
            CreateUserExtendedExample example = new CreateUserExtendedExample();
            example.setup(args);
			
			//example.createUser(null);
			example.pump(null);
			
        } catch (Exception ex) {
            System.out.println("Exception is " + ex);
            ex.printStackTrace();
        }
    }
	
    /**
     * This method is used to get user's details.
     * This method will call the web service
     * UM_GetUserDetailsExtended to perform get user's details operation.
     *
     * @throws Exception
     */
    protected void createUser(OlsaClientStub stub,int counter) throws Exception {
		Date start = new Date();
		String localUsername = ""+counter;
		if (stub == null) {
			stub = getBinding();
		}

        CreateUserExtendedRequest req;
        try {
            req = new CreateUserExtendedRequest();
            req.setCustomerId(OlsaClientConfig.getCustomerId());
			
			if(!StringUtils.isNullOrEmpty(username)) {
				long epoch = System.currentTimeMillis()/1000;
				localUsername = username+Long.toString(epoch)+Integer.toString(counter);
				req.setUserName(localUsername);
			}
			
			if(!StringUtils.isNullOrEmpty(orgCode)) {
				
				req.setGroupCode(StringUtils.convertToArray(orgCode));
			}

            VoidResponse res = stub.UM_CreateUserExtended(req);

			Date end = new Date();
			
			getCtkLogger().info("UM_CreateUserExtended. Time consumed: " +(end.getTime() - start.getTime()) + "ms");
         
        } catch (ObjectExistsFault e1) {
            // If user does not exist then we get this fault
            getCtkLogger().error("User " + localUsername + " already exists. Service UM_CreateUserExtended failed!");
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
			createUser(binding,i);
        }
        Date end = new Date();
		long elapsed = end.getTime() - start.getTime();
		long average = elapsed/repeat;
		getCtkLogger().info("Count: " + repeat + "\tTime consumed: " +elapsed + "ms"+ "\tAverage: " +average + "ms");
    }
	
}

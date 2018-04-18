package example.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * This is the utility class written for String 
 * manipulations.
 * 
 * @author Shweta Sidhu
 *
 */
public class StringUtils {

	/**
	 * Checks if the value is null or empty
	 * 
	 * @param value
	 * @return boolean true if string is null or empty
	 */
	public static boolean isNullOrEmpty(String value) {
		if(value == null || value.trim().length() == 0)
			return true;
		return false;
	}
	
	/**
	 * returns boolean value of the string
	 * 
	 * @param value
	 * @return boolean 
	 */
	public static boolean returnBooleanValue(String value) {
		if ((!StringUtils.isNullOrEmpty(value)) && (value.trim().equalsIgnoreCase("true") || value.trim().equalsIgnoreCase("1")))
			return true;
		return false;
	}
	
	/**
	 * returns the date value for the string passed. It assumes the format 
	 * as mm-dd-yyyy
	 * 
	 * @param value
	 * @return Date
	 */
	public static Date returnDateValue (String value) {
		Date date = null;
		SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
		if(!StringUtils.isNullOrEmpty(value)) {
			try {
				date = df.parse(value);
			} catch (Exception e) {
				date = null;
			}
		}
		return date;
	}
	
	/**
	 * converts the string to array and returns the 
	 * string array for the string passed. 
	 * It assumes the delimiter as ,
	 * 
	 * @param value
	 * @return String[]
	 */
	public static String[] convertToArray (String value) {
		String[] array = null;
		if (!StringUtils.isNullOrEmpty(value)) {
			StringTokenizer st = new StringTokenizer(value, CTKConstants.VALUE_DELIMITER);
			array = new String[st.countTokens()];
			for (int i = 0; st.hasMoreTokens();i++) {
				array[i] = st.nextToken();
			}
		}
		return array;
	}
}

package businesscomponents;

import java.util.Properties;

import util.Settings;

public class UtilTool {

	private static Properties properties = Settings.getInstance();
	static String env;

	// ##########################################//
	// Functionality to get environment details from system environment or
	// through pom.xml
	// ###########################################################//
	public static String getEnv() {
		if (properties.getProperty("ALMExecutionFlag").equals("0")) {
			env = System.getenv("ENV");

		} else {
			env = System.getProperty("environment"); //reading from pom.xml
		}
		env = "DEV";  //When you want to run localy.
		return env;
	}
}

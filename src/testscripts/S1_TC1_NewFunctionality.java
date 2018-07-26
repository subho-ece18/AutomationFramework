package testscripts;

import org.testng.annotations.Test;

import supportlibraries.DriverScript;
import supportlibraries.TestCase;
import util.Util;
import util.IterationOptions;
import util.OnError;
import util.Settings;
import util.Status;
import util.TimeStamp;
public class S1_TC1_NewFunctionality extends TestCase {
	
	@Test
	public void runS1_TC1_NewFunctionality()
	{
		
		testParameters.setCurrentTestDescription("verify Message");
		testParameters.setIterationMode(IterationOptions.RunOneIterationOnly);
		driverScript= new DriverScript(testParameters);
		driverScript.driveTestExecution();
	}

}

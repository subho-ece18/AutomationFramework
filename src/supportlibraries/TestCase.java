package supportlibraries;

import java.io.FileNotFoundException;
import java.util.Date;

//import businesscomponents.ALMResultUpdater;

import util.FrameworkException;
import util.Util;

import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.*;


/**
 * Abstract base class for all the test cases to be automated
 * @author Cognizant
 */
public abstract class TestCase extends ResultSummaryManager
{
	/**
	 * The {@link SeleniumTestParameters} object to be used to specify the test parameters
	 */
	protected static SeleniumTestParameters testParameters;
	/**
	 * The {@link DriverScript} object to be used to execute the required test case
	 */
	protected static DriverScript driverScript;
	
	private Date startTime, endTime;
	
	//ALMResultUpdater almObj=new ALMResultUpdater();
	
	@BeforeSuite
	public void suiteSetup(ITestContext testContext)
	{
		setRelativePath();
		initializeTestBatch();
		
		int nThreads;
		if (testContext.getSuite().getParallel().equalsIgnoreCase("false")) {
			nThreads = 1;
		} else {
			nThreads = testContext.getCurrentXmlTest().getThreadCount();
		}
		initializeSummaryReport(testContext.getSuite().getName(), nThreads);
		
		try {
			setupErrorLog();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new FrameworkException("Error while setting up the Error log!");
		}
		
		/*Connect to ALM if execution is to be done from Framework*/
		if(properties.getProperty("ALMExecutionFlag").equals("0"))
		{
			//almObj.connect();
		}
	}
	
	/*@BeforeMethod
	public void testMethodSetup()
	{
		if(frameworkParameters.getStopExecution()) {
			suiteTearDown();
			
			throw new SkipException("Aborting all subsequent tests!");
		} else {
			startTime = Util.getCurrentTime();
			
			String currentScenario =
					capitalizeFirstLetter(this.getClass().getPackage().getName().substring(12));
			String currentTestcase = this.getClass().getSimpleName();
			testParameters = new SeleniumTestParameters(currentScenario, currentTestcase);
		}
	}*/
	
	@BeforeMethod
	@Parameters("browser")
	public void testMethodSetup(@Optional String strBrowser)
	{
		if(frameworkParameters.getStopExecution()) {
			suiteTearDown();

			throw new SkipException("Aborting all subsequent tests!");
		} 
		else if(strBrowser==null)
			{
			startTime = Util.getCurrentTime();
			String package_name= this.getClass().getPackage().getName();
			int index = package_name.lastIndexOf(".");
			String currentScenario =
					capitalizeFirstLetter(package_name.substring(index +1));
			String currentTestcase = this.getClass().getSimpleName();
			testParameters = new SeleniumTestParameters(currentScenario, currentTestcase);
				testParameters.setBrowser(Browser.valueOf(properties.get("DefaultBrowser").toString()));
				System.out.println(testParameters.getBrowser());
			}
	
		
		 else
			{
			startTime = Util.getCurrentTime();
			String package_name=this.getClass().getPackage().getName();
			int index = package_name.lastIndexOf(".");
			String currentScenario = capitalizeFirstLetter(package_name.substring(index +1));
				String currentTestcase = this.getClass().getSimpleName();
				//@For Selenium Grid
				currentTestcase = currentTestcase+"__"+strBrowser;
				testParameters = new SeleniumTestParameters(currentScenario, currentTestcase);
				if (testParameters.getBrowser() == null) {
					testParameters.setBrowser(Browser.valueOf(strBrowser));
				}
			}
		}

	
	private String capitalizeFirstLetter(String myString)
	{
		StringBuilder stringBuilder = new StringBuilder(myString);
		stringBuilder.setCharAt(0, Character.toUpperCase(stringBuilder.charAt(0)));
		return stringBuilder.toString();
	}
	
	@AfterMethod
	public void testMethodTearDown()
	{
		String testStatus = driverScript.getTestStatus();
		endTime = Util.getCurrentTime();
		String executionTime = Util.getTimeDifference(startTime, endTime);
		summaryReport.updateResultSummary(testParameters.getCurrentScenario(),
									testParameters.getCurrentTestcase(),
									testParameters.getCurrentTestDescription(),
									executionTime, testStatus);
	}
	
	@AfterSuite
	public void suiteTearDown()
	{
		wrapUp();
		//launchResultSummary();
	}
}
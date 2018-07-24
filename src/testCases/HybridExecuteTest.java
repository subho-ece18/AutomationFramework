package testCases;

import org.testng.annotations.Test;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import pages.HomePage;
import operation.ReadObject;
import operation.UIOperation;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import excelExportAndFileIO.ReadExcelFile;
import pages.HomePage;
import pagemanagers.PageObjectManager;
public class HybridExecuteTest {
	HomePage homePage;
    PageObjectManager pageObjectManager;
    
	WebDriver webdriver = null;

	@Test
	public void executeTest() throws IOException{
		//HomePage 
		File file = new File("Env.properties");
		FileInputStream fileInput = new FileInputStream(file);
		Properties properties = new Properties();
		properties.load(fileInput);
		fileInput.close();
		
		String browservalue = properties.getProperty("BROWSER");
		
		if(browservalue.toUpperCase().equalsIgnoreCase("CHROME"))
		{
		System.setProperty("webdriver.chrome.driver", "D:\\chrome driver\\chromedriver.exe");
		webdriver=new ChromeDriver();
		webdriver.manage().window().maximize();
		webdriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		}
		else if(browservalue.toUpperCase().equalsIgnoreCase("FIREFOX"))
		{
		//
		}
		
		else if(browservalue.toUpperCase().equalsIgnoreCase("IE"))
		{
		//
		}
		
		else
		{
			System.out.print("Driver Instance can not be initialized...");
		}
		pageObjectManager= new PageObjectManager(webdriver);
		homePage = pageObjectManager.getHomePage(webdriver);
		homePage.loadUrl("https://www.google.com");
		homePage.typeInSearchBox("ABC");
		homePage.closeBrowser();
	}
	/*@Test(dataProvider = "hybridData")
	public void testLogin(String testcaseName, String keyword, String objectName, String objectType, String value)
			throws Exception {
		// TODO Auto-generated method stub

		if (testcaseName != null && testcaseName.length() != 0) {
			System.out.println("Hi");
			
			// Initialize Chrome browser
			System.setProperty("webdriver.chrome.driver", "D:\\chrome driver\\chromedriver.exe");
			webdriver=new ChromeDriver();
			
			// Initialize Firefox browser
			//System.setProperty("webdriver.firefox.marionette","D:\\mozilla driver\\geckodriver.exe");
			//WebDriver webdriver = new FirefoxDriver();
			System.out.println("Hi");

		}
		ReadObject object = new ReadObject();
		Properties allObjects = object.getObjectRepository();
		UIOperation operation = new UIOperation(webdriver);
		// Call perform function to perform operation on UI
		operation.perform(allObjects, keyword, objectName, objectType, value);

	}

	@DataProvider(name = "hybridData")
	public Object[][] getDataFromDataprovider() throws IOException {
		Object[][] object = null;
		ReadExcelFile file = new ReadExcelFile();

		// Read keyword sheet
		Sheet guru99Sheet = file.readExcel(System.getProperty("user.dir") + "\\", "TestCase.xlsx", "KeywordFramework");
		// Find number of rows in excel file
		int rowCount = guru99Sheet.getLastRowNum() - guru99Sheet.getFirstRowNum();
		object = new Object[rowCount][5];
		for (int i = 0; i < rowCount; i++) {
			// Loop over all the rows
			Row row = guru99Sheet.getRow(i + 1);
			// Create a loop to print cell values in a row
			for (int j = 0; j < row.getLastCellNum(); j++) {
				// Print excel data in console
				object[i][j] = row.getCell(j).toString();
			}

		}
		System.out.println("");
		return object;
	}*/
}

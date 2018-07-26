package supportlibraries;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import businesscomponents.WaitTool;

import util.CraftDataTable;
import util.FrameworkParameters;
import util.Settings;
import util.Status;

/**
 * Abstract base class for reusable libraries created by the user
 * 
 * @author Cognizant
 */
public abstract class ReusableLibrary {
	/**
	 * The {@link CraftDataTable} object (passed from the test script)
	 */
	protected CraftDataTable dataTable;
	/**
	 * The {@link SeleniumReport} object (passed from the test script)
	 */
	static protected SeleniumReport report;
	/**
	 * The {@link WebDriver} object
	 */
	protected WebDriver driver;
	/**
	 * The {@link ScriptHelper} object (required for calling one reusable
	 * library from another)
	 */
	protected ScriptHelper scriptHelper;

	/**
	 * The {@link Properties} object with settings loaded from the framework
	 * properties file
	 */
	protected Properties properties;
	/**
	 * The {@link FrameworkParameters} object
	 */
	protected FrameworkParameters frameworkParameters;

	/**
	 * Constructor to initialize the {@link ScriptHelper} object and in turn the
	 * objects wrapped by it
	 * 
	 * @param scriptHelper
	 *            The {@link ScriptHelper} object
	 */

	public static String path = System.getProperty("user.dir")
			+ "\\src\\main\\resources\\DataTables\\SPRINTS.xls";
	public static String sheetName = "Frequencies";
	public static int rowNumber = 0;

	public ReusableLibrary(ScriptHelper scriptHelper) {
		this.scriptHelper = scriptHelper;
		this.dataTable = scriptHelper.getDataTable();
		this.report = scriptHelper.getReport();
		this.driver = scriptHelper.getDriver();

		properties = Settings.getInstance();
		frameworkParameters = FrameworkParameters.getInstance();
	}

	public void sync() {
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}

	public void sync(int sec) {
		try {
			Thread.sleep(sec * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void clickToLink(String link, String type) {
		WebElement button;
		if (type.equals("anchor"))
			button = driver.findElement(By.xpath("//a[contains(text(),'" + link
					+ "')]"));
		else
			button = driver.findElement(By.xpath("//span[contains(text(),'"
					+ link + "')]"));
		WaitTool.waitForElementDisplayed(driver, button, 18);
		if (button.isDisplayed()) {
			button.click();
			report.updateTestLog("<i>ClickToLink</i>",
					"Clicked to the link : <b>'" + link + "'</b>.", Status.DONE);
		}
		sync(1);
	}

	public void clickToButton(String button) {
		sync(1);
		WebElement buttonWE = driver.findElement(By
				.xpath("//button[contains(text(),'" + button + "')]"));
		WaitTool.waitForElementDisplayed(driver, buttonWE, 18);
		if (buttonWE.isDisplayed()) {
			buttonWE.click();
			sync(2);
			report.updateTestLog("<i>ClickToButton</i>",
					"Button exists & clicked to the button : <b>" + button
							+ "</b>.", Status.DONE);
		} else
			report.updateTestLog("<i>ClickToButton</i>", "Button <b>" + button
					+ "</b> doesn't exists.", Status.FAIL);
	}

	// #########################################################################################################//
	// Functionality : To Check Configure Dropdown, Minimum Start Freq, Maximum
	// Stop Freq.
	// Application : FBC
	// Author : Charanjeet Singh
	// #########################################################################################################//

	/*public void scrollDown() {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("scroll(0, 700);");
		// jse.executeScript("window.scrollTo(0,Math.max(document.documentElement.scrollHeight,document.body.scrollHeight,document.documentElement.clientHeight));");
		sync(1);
	}*/

	public void scrollUp() {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("scroll(650, 0);");
		// jse.executeScript("window.scrollTo(0,Math.max(document.documentElement.scrollHeight,document.body.scrollHeight,document.documentElement.clientHeight));");
		sync(1);
	}

	/*public void scrollDown(String howMuch) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		// jse.executeScript("scroll(0, 650);");
		jse.executeScript("window.scrollTo(0,Math.max(document.documentElement.scrollHeight,document.body.scrollHeight,document.documentElement.clientHeight));");
		sync(1);
	}*/

	public void scrollUp(int x, int y) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("scroll(" + x + "," + y + ");");

		// jse.executeScript("window.scrollTo(0,Math.max(document.documentElement.scrollHeight,document.body.scrollHeight,document.documentElement.clientHeight));");
		sync(1);
	}

	public void refresh() {
		driver.findElement(By.tagName("body")).sendKeys(Keys.F5);
	}

	public void takeScreenShot() {
		String path = DriverScript.reportPath;
		File scrFile = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);
		// Now you can do whatever you need to do with it, for example copy
		// somewhere
		try {
			if ((new File(path + "\\screenshot_1.png")).exists()) {
				if ((new File(path + "\\screenshot_2.png")).exists()) {
					if ((new File(path + "\\screenshot_3.png")).exists()) {
						if ((new File(path + "\\screenshot_4.png")).exists()) {
							if ((new File(path + "\\screenshot_5.png"))
									.exists()) {
								if ((new File(path + "\\screenshot_6.png"))
										.exists()) {
									FileUtils.copyFile(scrFile, new File(path
											+ "\\screenshot_7.png"));
								} else {
									FileUtils.copyFile(scrFile, new File(path
											+ "\\screenshot_6.png"));
								}
								// FileUtils.copyFile(scrFile, new
								// File(path+"\\screenshot_2.png"));
							} else {
								FileUtils.copyFile(scrFile, new File(path
										+ "\\screenshot_5.png"));
							}
							// FileUtils.copyFile(scrFile, new
							// File(path+"\\screenshot_2.png"));
						} else {
							FileUtils.copyFile(scrFile, new File(path
									+ "\\screenshot_4.png"));
						}
						// FileUtils.copyFile(scrFile, new
						// File(path+"\\screenshot_2.png"));
					} else {
						FileUtils.copyFile(scrFile, new File(path
								+ "\\screenshot_3.png"));
					}
					// FileUtils.copyFile(scrFile, new
					// File(path+"\\screenshot_3.png"));
				} else {
					FileUtils.copyFile(scrFile, new File(path
							+ "\\screenshot_2.png"));
				}
				// FileUtils.copyFile(scrFile, new
				// File(path+"\\screenshot_1.png"));
			} else {
				FileUtils.copyFile(scrFile, new File(path
						+ "\\screenshot_1.png"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
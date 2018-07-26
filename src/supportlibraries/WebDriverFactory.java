package supportlibraries;



import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;

import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.Proxy.ProxyType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.gargoylesoftware.htmlunit.DefaultCredentialsProvider;
import com.gargoylesoftware.htmlunit.WebClient;

import org.openqa.selenium.ie.InternetExplorerDriver;

//import com.opera.core.systems.OperaDriver;

import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.remote.*;

import util.FrameworkException;
import util.Settings;


/**
 * Factory class for creating the {@link WebDriver} object as required
 * @author Cognizant
 */
public class WebDriverFactory
{
	private static Properties properties;
	
	private WebDriverFactory()
	{
		// To prevent external instantiation of this class
	}
	
	
	/**
	 * Function to return the appropriate {@link WebDriver} object based on the parameters passed
	 * @param browser The {@link Browser} to be used for the test execution
	 * @return The corresponding {@link WebDriver} object
	 */
	public static WebDriver getDriver(Browser browser)
	{
		WebDriver driver = null;
		properties = Settings.getInstance();
		boolean proxyRequired =
				Boolean.parseBoolean(properties.getProperty("ProxyRequired"));
		
		switch(browser) {
		case Chrome:
			// Takes the system proxy settings automatically
			
			System.setProperty("webdriver.chrome.driver",
									System.getProperty("user.dir") +"\\"+ properties.getProperty("ChromeDriverPath"));
			String[] switches = {"--ignore-certificate-errors"};
			DesiredCapabilities dc = DesiredCapabilities.chrome();
			LoggingPreferences logPrefs = new LoggingPreferences();
		    logPrefs.enable(LogType.BROWSER, Level.ALL);
			dc.setCapability("chrome.switches", Arrays.asList(switches));
			dc.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
			driver = new ChromeDriver(dc);
			break;
			
		case MobileChrome:
            // Takes the system proxy settings automatically
            
           System.setProperty("webdriver.chrome.driver",properties.getProperty("ChromeDriverPath"));
            String[] switches1 = {"--ignore-certificate-errors"};
            DesiredCapabilities dc1 = DesiredCapabilities.chrome();
            dc1.setCapability("chrome.switches", Arrays.asList(switches1));

            Map<String, String> mobileEmulation = new HashMap<String, String>();
            mobileEmulation.put("deviceName", properties.getProperty("DeviceName"));

            Map<String, Object> chromeOptions = new HashMap<String, Object>();
            chromeOptions.put("mobileEmulation", mobileEmulation);
     //     DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            dc1.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
            driver = new ChromeDriver(dc1);
            
            

            //driver = new ChromeDriver(dc);
            break;
			
		case Firefox:
			// Takes the system proxy settings automatically
			try {
			driver = new FirefoxDriver();
			} catch(WebDriverException wdEx) {
				if(wdEx.getMessage().contains("Cannot find firefox binary in PATH")) {
					FirefoxBinary ffBin = new FirefoxBinary(new File(properties.getProperty("FirefoxBinary")));
					FirefoxProfile ffProf = new FirefoxProfile(); 
					driver = new FirefoxDriver();
				} else 
					throw new FrameworkException("Unable to open firefox driver", wdEx.getLocalizedMessage());
			}
			break;
			
		case HtmlUnit:
			// Does not take the system proxy settings automatically!
			
			driver = new HtmlUnitDriver();
			
			if (proxyRequired) {
				boolean proxyAuthenticationRequired =
						Boolean.parseBoolean(properties.getProperty("ProxyAuthenticationRequired"));
				if(proxyAuthenticationRequired) {
					// NTLM authentication for proxy supported
					
					driver = new HtmlUnitDriver() {
					@Override
					protected WebClient modifyWebClient(WebClient client) {
						DefaultCredentialsProvider credentialsProvider = new DefaultCredentialsProvider();
						credentialsProvider.addNTLMCredentials(properties.getProperty("Username"),
																properties.getProperty("Password"),
																properties.getProperty("ProxyHost"),
																Integer.parseInt(properties.getProperty("ProxyPort")),
																"", properties.getProperty("Domain"));
						client.setCredentialsProvider(credentialsProvider);
						return client;
						}
					};
				}
				
				((HtmlUnitDriver) driver).setProxy(properties.getProperty("ProxyHost"),
											Integer.parseInt(properties.getProperty("ProxyPort")));
			}
			
			break;
			
		case InternetExplorer:
			// Takes the system proxy settings automatically
			
			properties = Settings.getInstance();
			System.setProperty("webdriver.ie.driver",
					System.getProperty("user.dir") +"\\"+ properties.getProperty("InternetExplorerDriverPath"));
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
		    capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true); 
		    capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		    capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true); 
		    capabilities.setJavascriptEnabled(true); 
		    DesiredCapabilities.internetExplorer().setCapability("ignoreProtectedModeSettings", true);
			driver = new InternetExplorerDriver(capabilities);
			driver.manage().deleteAllCookies();
			break;

			
	/*	case Opera:
			// Does not take the system proxy settings automatically!
			// NTLM authentication for proxy NOT supported
			
			if (proxyRequired) {
				DesiredCapabilities desiredCapabilities = getProxyCapabilities();
				driver = new OperaDriver(desiredCapabilities);
			} else {
				driver = new OperaDriver();
			}
			
			break;*/
			
		case Safari:
			// Takes the system proxy settings automatically
			
			driver = new SafariDriver();
			break;
			
		default:
			throw new FrameworkException("Unhandled browser!");
		}
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		return driver;
	}
	
	private static DesiredCapabilities getProxyCapabilities()
	{
		Proxy proxy = new Proxy();
		proxy.setProxyType(ProxyType.MANUAL);
		
		properties = Settings.getInstance();
		String proxyUrl = properties.getProperty("ProxyHost") + ":" +
									properties.getProperty("ProxyPort");
		
		proxy.setHttpProxy(proxyUrl);
		proxy.setFtpProxy(proxyUrl);
		proxy.setSslProxy(proxyUrl);
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		desiredCapabilities.setCapability(CapabilityType.PROXY, proxy);
		
		return desiredCapabilities;
	}
	
	/**
	 * Function to return the appropriate {@link WebDriver} object based on the parameters passed
	 * @param browser The {@link Browser} to be used for the test execution
	 * @param remoteUrl The URL of the remote machine to be used for the test execution
	 * @return The corresponding {@link WebDriver} object
	 */
	public static WebDriver getDriver(Browser browser, String remoteUrl)
	{
		return getDriver(browser, null, null, remoteUrl);
	}
	
	/**
	 * Function to return the appropriate {@link WebDriver} object based on the parameters passed
	 * @param browser The {@link Browser} to be used for the test execution
	 * @param browserVersion The browser version to be used for the test execution
	 * @param platform The {@link Platform} to be used for the test execution
	 * @param remoteUrl The URL of the remote machine to be used for the test execution
	 * @return The corresponding {@link WebDriver} object
	 */
	/*public static WebDriver getDriver(Browser browser, String browserVersion,
												Platform platform, String remoteUrl)
	{
		// For running RemoteWebDriver tests in Chrome and IE:
		// The ChromeDriver and IEDriver executables needs to be in the PATH of the remote machine
		// To set the executable path manually, use:
		// java -Dwebdriver.chrome.driver=/path/to/driver -jar selenium-server-standalone.jar
		// java -Dwebdriver.ie.driver=/path/to/driver -jar selenium-server-standalone.jar
		
		properties = Settings.getInstance();
		boolean proxyRequired =
				Boolean.parseBoolean(properties.getProperty("ProxyRequired"));
		
		DesiredCapabilities desiredCapabilities = null;
		if ((browser.equals(Browser.HtmlUnit) || browser.equals(Browser.Opera))
																&& proxyRequired) {
			desiredCapabilities = getProxyCapabilities();
		} else {
			desiredCapabilities = new DesiredCapabilities();
		}
		
		desiredCapabilities.setBrowserName(browser.getValue());
		
		if (browserVersion != null) {
			desiredCapabilities.setVersion(browserVersion);
		}
		if (platform != null) {
			desiredCapabilities.setPlatform(platform);
		}
		
		desiredCapabilities.setJavascriptEnabled(true);	// Pre-requisite for remote execution
		
		URL url;
		try {
			url = new URL(remoteUrl);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new FrameworkException("The specified remote URL is malformed");
		}
		
		return new RemoteWebDriver(url, desiredCapabilities);
	}*/
	
	public static WebDriver getDriver(Browser browser, String browserVersion,
			Platform platform, String remoteUrl)
	{
		 DesiredCapabilities desireCapabilities;
		System.out.println(browser+"===="+browserVersion+"===="+remoteUrl);
		
		switch (browser) {

		case Chrome:
			desireCapabilities = DesiredCapabilities.chrome();
		
			break;
		case InternetExplorer:
			desireCapabilities =  DesiredCapabilities.internetExplorer();
			desireCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true); 
			desireCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			desireCapabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true); 
			desireCapabilities.setJavascriptEnabled(true); 
			desireCapabilities.setCapability("requireWindowFocus", true);
			desireCapabilities.setCapability("ignoreProtectedModeSettings", true);
		
			break;
		case Firefox:
			desireCapabilities =  DesiredCapabilities.firefox();
			
			break;

		default:
			desireCapabilities = DesiredCapabilities.firefox();
		}
		URL url;
		try {
			url = new URL(remoteUrl);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new FrameworkException("The specified remote URL is malformed");
		}
		
		return new RemoteWebDriver(url, desireCapabilities);
	}
	

}
package pagemanagers;

import org.openqa.selenium.WebDriver;

import pages.HomePage;

public class PageObjectManager {
	private WebDriver driver;
	 
	private HomePage homepage;
 
	public PageObjectManager()
	{
		
	}
	
 
	public PageObjectManager(WebDriver driver) {
 
		this.driver = driver;
 
	}
	
	public HomePage getHomePage(WebDriver webdriver){
		
		 
		return (homepage == null) ? homepage = new HomePage(webdriver) : homepage;
 
	}



}

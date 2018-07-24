package pages;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.FindBy;

import org.openqa.selenium.support.PageFactory;


public class HomePage {
	
	
	WebDriver driver =null;

    @FindBy(name="q")
    public WebElement searchBox ;


    @FindBy(xpath="//*[@value='Google Search']")
    public WebElement searchButton;
    
    public HomePage(WebDriver driver){

        this.driver = driver;

        //This initElements method will create all WebElements

        PageFactory.initElements(driver, this);

    }

    //Set user name in textbox

    
    public HomePage loadUrl(String url){

        driver.get(url);
        return this;

    }
    public HomePage typeInSearchBox(String strUserName){

    	searchBox.sendKeys(strUserName);

        return this;

    }

    public HomePage closeBrowser(){

        driver.quit();
        return this;

    }
    

}

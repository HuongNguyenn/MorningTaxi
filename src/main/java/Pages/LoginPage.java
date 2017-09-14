package Pages;

import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import Providers.WebDriverProvider;
import Utils.Utils;

public class LoginPage extends WebDriverProvider {
	public WebDriver driver;
	public By loc_Username;
	public By loc_Password;
	public By loc_Login;
	public By loc_LoginSuccess;
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		Properties prop  	= Utils.loadProperties("ObjectRepo/LoginPage.properties");
		loc_Username 		= By.xpath(prop.getProperty("LoginPage.Username "));
		loc_Password		= By.xpath(prop.getProperty("LoginPage.Password"));
		loc_Login   		= By.xpath(prop.getProperty("LoginPage.LoginButton"));
		loc_LoginSuccess 	= By.xpath(prop.getProperty("LoginPage.LoginSuccess"));
	}
	
	public void LoginMorningTaxi(String userName, String passWord){	
		getElement(loc_Username).sendKeys(userName);
		getElement(loc_Password).sendKeys(passWord);
		getElement(loc_Login).click();
	}
	
	public void checkLoginSuccess() {
		waitTimeSecond(2);
		checkElementExist(loc_LoginSuccess);
	}
	
}

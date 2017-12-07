package Pages;

import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import Providers.WebDriverProvider;
import Utils.Utils;

public class Login extends WebDriverProvider {
	public WebDriver driver;
	public By loc_Username;
	public By loc_Password;
	public By loc_Login;
	public By loc_LoginSuccess;
	public By lob_LoginSuccessWithAdminAccount;

	public Login(WebDriver driver) {
		this.driver = driver;
		Properties prop = Utils.loadProperties("ObjectRepo/LoginPage.properties");
		loc_Username = By.xpath(prop.getProperty("LoginPage.Username"));
		loc_Password = By.xpath(prop.getProperty("LoginPage.Password"));
		loc_Login = By.xpath(prop.getProperty("LoginPage.LoginButton"));
		loc_LoginSuccess = By.xpath(prop.getProperty("LoginPage.LoginSuccess"));
		lob_LoginSuccessWithAdminAccount = By.xpath(prop.getProperty("LoginPage.LoginSuccessWithAdminAccount"));
	}

	public void LoginMorningTaxi(String userName, String passWord) {
		EnterText(loc_Username, userName);
		EnterText(loc_Password, passWord);
		ClickElement(loc_Login);
	}

	public void VerifyLoginSuccess() {
		CheckElementExist(loc_LoginSuccess);
	}
	
	public void VerifyLoginSuccessWithAdminAccount() {
		CheckElementExist(lob_LoginSuccessWithAdminAccount);
	}

}

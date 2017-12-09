package Pages;

import org.testng.AssertJUnit;
import java.net.Inet4Address;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import Providers.ConnectDB;
import Providers.WebDriverProvider;
import Utils.Utils;
import java.util.regex.Matcher; 
import java.util.regex.Pattern;

public class Driver extends WebDriverProvider {

	public WebDriver driver;
	public By loc_CreateAccount;
	public By loc_InformationTable;
	public By loc_PhoneNumber;
	public By loc_Name;
	public By loc_Email;
	public By loc_Password;
	public By loc_SaveButton;
	public By loc_IncorrectNotice;
	public By loc_FirstPhonenumber;

	public String result_TestCase;
	String collection = "users";
	ConnectDB connect = new ConnectDB(collection);

	public Driver(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
		Properties prop = Utils.loadProperties("ObjectRepo/DriverPage.properties");
		loc_CreateAccount = By.xpath(prop.getProperty("DriverPage.CreateAccount"));
		loc_InformationTable = By.xpath(prop.getProperty("DriverPage.InformationTable"));
		loc_PhoneNumber = By.xpath(prop.getProperty("DriverPage.PhoneNumber"));
		loc_Name = By.xpath(prop.getProperty("DriverPage.Name"));
		loc_Email = By.xpath(prop.getProperty("DriverPage.Email"));
		loc_Password = By.xpath(prop.getProperty("DriverPage.Password"));
		loc_SaveButton = By.xpath(prop.getProperty("DriverPage.SaveButton"));
		loc_IncorrectNotice = By.xpath(prop.getProperty("DriverPage.IncorrectNotice"));
		loc_FirstPhonenumber = By.xpath(prop.getProperty("DriverPage.FirstPhonenumber"));
	}

	public void CreateDriverAccountButton() {
		ClickElement(loc_CreateAccount);
	}

	public void EnterPhoneNumber(String value) {
		EnterText(loc_PhoneNumber, value);
	}

	public void EnterName(String value) {
		EnterText(loc_Name, value);
	}
	
	public void EnterEmail(String value) {
		EnterText(loc_Email, value);
	}
	
	public void EnterPassword(String value){
		EnterText(loc_Password, value);
	}

	public void ClickSaveButton() {
		ClickElement(loc_SaveButton);
	}

	public void CreateDriverAccount(String phonenumber, String name, String email, String password){
		CreateDriverAccountButton();
		EnterPhoneNumber(phonenumber);
		EnterName(name);
		EnterPassword(password);
		EnterEmail(email);
		ClickSaveButton();
		WaitTimeSecond(5);
	}
	
	public void VerifyInformation(String phonenumber, String name, String email, String password){
		if (phonenumber==null || name==null || email==null || password ==null 
				|| IsValidPhonenumber(phonenumber)==false || IsValidName(name)==false || IsValidEmail(email)==false 
				|| IsSpecialCharacterInPassword(password) || IsValidPassword(password)==false	) {
			CheckElementExist(loc_IncorrectNotice);
			
		} else if (IsExitsPhonenumber(phonenumber)== true || IsExitsEmail(email)== true ) {
			
		}
		else {
			VerifyDriverAccountInTable(phonenumber);
		}
	}
	
	public boolean IsValidPhonenumber(String phonenumber){
		try {
			long phonenumber1 = Long.parseLong(phonenumber);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean IsValidName(String name){
		int size = name.length();
		if (size<6) {
			return false;
		}
		return true;
	
	}
	public boolean IsValidPassword(String name){
		int size = name.length();
		if (size<5) {
			return false;
		}
		return true;
	}
	
	public boolean IsSpecialCharacterInPassword(String name){
		Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(name);
		boolean b = m.find();

		if (b){
			return true;
		}
		 return false;
	}
	
	public  boolean IsValidEmail(String email){
		    if (email != null)
		    {
		        Pattern p = Pattern.compile("^[A-Za-z].*?@gmail\\.com$");
		        Matcher m = p.matcher(email);
		        return m.find();
		    }
		    return false;
		}
	
	public  boolean IsExitsEmail(String email){
	    if (connect.CheckEmailIsExits(email))
	    {
	       return true;
	    }
	    return false;
	}
	
	public  boolean IsExitsPhonenumber(String phone){
	    if (connect.CheckPhonenumberIsExits(phone))
	    {
	        return true;
	    }
	    return false;
	}

	public void VerifyDriverAccountInTable(String phonenumber) {
		String actual = GetText(loc_FirstPhonenumber);
		String message = "Information is not in table..............";
		AssertJUnit.assertEquals(message, phonenumber, actual);
	}
	
	public void DeleteDriver(String email){
		if (connect.CheckEmailIsExits(email)) {
			connect.DeleteDriver(email);
		}
	}
}

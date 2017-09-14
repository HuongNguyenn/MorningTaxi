package Providers;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import Utils.Utils;

public class WebDriverProvider {
	public static WebDriver wd;

	public void gotoUrl(String browser, String url) {
//		if ("firefox".equals(browser)) {
//			System.setProperty("webdriver.gecko.driver", new File(
//					"src/main/resources/drivers/geckodriver.exe")
//					.getAbsolutePath());
//
//			ProfilesIni proIni = new ProfilesIni();
//			FirefoxProfile profileFF = proIni.getProfile("default");
//			wd = new FirefoxDriver(profileFF);
//
//		} else if ("chrome".equals(browser)) {
//			System.setProperty("webdriver.chrome.driver", new File(
//					"src/main/resources/drivers/chromedriver.exe")
//					.getAbsolutePath());
//			wd = new ChromeDriver();
//		} else if ("ie".equals(browser)) {
//			System.setProperty("webdriver.ie.driver", new File(
//					"src/main/resources/drivers/IEDriverServer.exe")
//					.getAbsolutePath());
//			wd = new InternetExplorerDriver();
//		}
		System.setProperty("webdriver.chrome.driver","E:\\Download\\Appium\\geckodriver\\least_vr\\chromedriver.exe");
        // Create a new instance of the Firefox driver
        wd = new FirefoxDriver();
 
        //Put a Implicit wait, this means that any search for elements on the page could take the time the implicit wait is set for before throwing exception
        wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
 
        //Launch the facebook
        wd.get("http://kientructrinhgia.vn/tinh-gia/");
		// wd.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		
		waitTimeSecond(5);
	}
	public void getURL()
	{
		System.setProperty("webdriver.chrome.driver","E:\\Download\\Appium\\geckodriver\\least_vr\\chromedriver.exe");
        wd = new ChromeDriver();
        wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wd.get("https://localhost/kientructrinhgia.vn/tinh-gia/");
        wd.manage().window().maximize();
	    
	}
	
	public void exits(){
		wd.close();
	}

	public WebElement getElement(By loc) {
		WebElement ele = null;
		try {
			ele = wd.findElement(loc);
		} catch (Exception e) {
			System.out.println("Can not find Element with locator : " + loc);
			e.printStackTrace();
		}
		return ele;
	}

	public void checkElementExist(By loc) {
		if (getElement(loc) != null) {
			System.out.println("Element with locator : " + loc + " is exist");
		} else {
			System.out.println("Element with locator : " + loc
					+ " is not exist");
			Utils.captureScreen(wd, "JiraTest", "CheckElementExist");
			Assert.assertTrue(false);
		}
	}

	public void clickCheckBox(By loc, String value){
		WebElement element = getElement(loc);
		if (value.equals("yes")) {
			element.click();
		}else {
			System.out.println("Don't click checkbox:"+loc);
		}
	}
	
	public void sendKeys(By loc, String value){
		getElement(loc).sendKeys(value);
	}
	
	public void selectDropdown(By loc, String value){
		if (value != null) {
		Select dropdownDT = new Select(getElement(loc));
		dropdownDT.selectByVisibleText(value);
		}
	}
	public void selectDropdown1(By loc, String value){
		if (value != null) {
		Select dropdownDT = new Select(getElement(loc));
		dropdownDT.selectByIndex(Integer.parseInt(value));
		}
	}
	public WebElement getElementWithWait(By loc, int time) {
		WebDriverWait wait = new WebDriverWait(wd, time);
		WebElement ele = wait.until(ExpectedConditions
				.elementToBeClickable(loc));
		return ele;
	}
	
	public void implicitWaitTimeSecond(By loc, int time) {
		WebDriverWait wait = new WebDriverWait(wd, time);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(loc));	
	}
	

	public void waitTimeSecond(int time) {
		try {
			Thread.sleep(time * 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getTextElement(By loc) {
		String text = "";
		WebElement ele = getElement(loc);
		if (ele != null) {
			if (ele.getTagName().contains("input")
					|| ele.getTagName().contains("textarea")) {
				text = ele.getAttribute("value");
			} else {
				text = ele.getText();
			}
		}
		return text;
	}

	public void compareText(String exp, String act) {
		System.out.println("Expected is :" + exp);
		System.out.println("Actual is :" + act);
		if (!exp.equals(act)) {
			Utils.captureScreen(wd, "JiraTest", "CompareText");
			Assert.assertTrue(false);
		}
	}

	public boolean isAlertPresent() {
		boolean foundAlert = false;
		WebDriverWait wait = new WebDriverWait(wd, 2 /* timeout in seconds */);
		try {
			wait.until(ExpectedConditions.alertIsPresent());
			foundAlert = true;
		} catch (TimeoutException eTO) {
			foundAlert = false;
		}
		return foundAlert;
	}

	public void quit() {
		wd.quit();
	}

}

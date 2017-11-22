package Providers;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import Utils.Utils;

public class WebDriverProvider {
	public static WebDriver wd;

	public void GoToUrl(String browser) {
		if ("firefox".equals(browser)) {
			System.setProperty("webdriver.gecko.driver",
					new File("src/main/resources/drivers/geckodriver.exe").getAbsolutePath());

			ProfilesIni proIni = new ProfilesIni();
			FirefoxProfile profileFF = proIni.getProfile("default");
			wd = new FirefoxDriver(profileFF);

		} else if ("chrome".equals(browser)) {
			System.setProperty("webdriver.chrome.driver",
					new File("src/main/resources/drivers/chromedriver.exe").getAbsolutePath());
			wd = new ChromeDriver();
		} else if ("ie".equals(browser)) {
			System.setProperty("webdriver.ie.driver",
					new File("src/main/resources/drivers/IEDriverServer.exe").getAbsolutePath());
			wd = new InternetExplorerDriver();
		}
		System.setProperty("webdriver.chrome.driver", "E:\\Download\\Appium\\geckodriver\\least_vr\\chromedriver.exe");
		// Create a new instance of the Firefox driver

		// Put a Implicit wait, this means that any search for elements on the
		// page could take the time the implicit wait is set for before throwing
		// exception
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// Launch the facebook
		wd.get("http://morning.taxionline.vn");

		// wd.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		WaitTimeSecond(5);
	}

	public void GoToURL(String url) {
		System.setProperty("webdriver.chrome.driver", "E:\\Download\\Appium\\geckodriver\\least_vr\\chromedriver.exe");
		wd = new ChromeDriver();
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		wd.get(url);
		wd.manage().window().maximize();

	}

	public void exits() {
		wd.close();
	}

	public WebElement GetElement(By loc) {
		WebElement ele = null;
		try {
			ele = wd.findElement(loc);
		} catch (Exception e) {
			System.out.println("Can not find Element with locator : " + loc);
			e.printStackTrace();
		}
		return ele;
	}

	public void CheckElementExist(By loc) {
		if (GetElement(loc) != null) {
			System.out.println("Element with locator : " + loc + " is exist");
		} else {
			System.out.println("Element with locator : " + loc + " is not exist");
			Utils.captureScreen(wd, "JiraTest", "CheckElementExist");
			Assert.assertTrue(false);
		}
	}

	public void clickCheckBox(By loc, String value) {
		WebElement element = GetElement(loc);
		if (value.equals("yes")) {
			element.click();
		} else {
			System.out.println("Don't click checkbox:" + loc);
		}
	}

	public void EnterText(By loc, String value) {
		System.out.println("Enter " + loc + ":" + value + "...");
		GetElement(loc).sendKeys(value);
	}

	public void ClickElement(By loc) {
		System.out.println("Click on " + loc + "...");
		GetElement(loc).click();
	}

	public void SelectDropdownByValue(By loc, String value) {
		if (value != null) {
			System.out.println("Select " + loc + ":" + value + "...");
			Select dropdownDT = new Select(GetElement(loc));
			dropdownDT.selectByVisibleText(value);

		}
	}

	public void SelectDropdownByIndex(By loc, String value) {
		if (value != null) {
			Select dropdownDT = new Select(GetElement(loc));
			dropdownDT.selectByIndex(Integer.parseInt(value));
		}
	}

	public WebElement GetElementWithWait(By loc, int time) {
		WebDriverWait wait = new WebDriverWait(wd, time);
		WebElement ele = wait.until(ExpectedConditions.elementToBeClickable(loc));
		return ele;
	}

	public void ImplicitWaitTimeSecond(By loc, int time) {
		WebDriverWait wait = new WebDriverWait(wd, time);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(loc));
	}

	public void WaitTimeSecond(int time) {
		try {
			Thread.sleep(time * 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String GetTextElement(By loc) {
		String text = "";
		WebElement ele = GetElement(loc);
		System.out.println(ele);
		if (ele != null) {
			if (ele.getTagName().contains("input") || ele.getTagName().contains("textarea")) {
				text = ele.getAttribute("value");
			} else {
				text = ele.getText();
			}
		}
		return text;
	}

	public void CompareText(String exp, String act) {
		System.out.println("Expected is :" + exp);
		System.out.println("Actual is :" + act);
		if (!exp.equals(act)) {
			Utils.captureScreen(wd, "JiraTest", "CompareText");
			Assert.assertTrue(false);
		}
	}

	public boolean IsAlertPresent() {
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

	public String GetNowTime() {
		return new SimpleDateFormat("HH:mm dd/MM/yyyy").format(Calendar.getInstance().getTime());
	}

	public String GetText(By loc) {
		return wd.findElement(loc).getText();
	}

	public void RefreshPage() {
		wd.navigate().refresh();
	}

	public void ValidateFieldContainsText(By loc, String value) {
		String innerText = GetElement(loc).getText();
		System.out.println("Verify " + innerText + " has '" + value + "'");
		String failureText = "Validation failed for '" + innerText + "'";
		Assert.assertTrue(innerText.contains(value), failureText);
	}
}

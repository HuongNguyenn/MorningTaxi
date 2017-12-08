package Pages;

import java.sql.Connection;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import javax.xml.xpath.XPath;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import Providers.ConnectDB;
import Providers.WebDriverProvider;
import Utils.Utils;

public class Control extends WebDriverProvider {

	public WebDriver driver;
	public By loc_CreateTicket;
	public By loc_ControlTable;
	public By loc_NameInput;
	public By loc_PhoneNumberInput;
	public By loc_TypeInput;
	public By loc_Address;
	public By loc_Note;
	public By loc_BookTicket;
	public By loc_AddressFocused;
	public By loc_Phonenumber;
	public By loc_Statues;
	public By loc_StatuesSuccess;
	public By loc_SuccessTab;
	public By loc_NoticeAdrressIncorrect;

	public String result_TestCase;
	String collection = "requests";
	ConnectDB connect = new ConnectDB(collection);

	public Control(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
		Properties prop = Utils.loadProperties("ObjectRepo/ControlPage.properties");
		loc_CreateTicket = By.xpath(prop.getProperty("ControlPage.CreateTicket"));
		loc_ControlTable = By.xpath(prop.getProperty("ControlPage.ControlTable"));
		loc_NameInput = By.xpath(prop.getProperty("ControlPage.NameInput"));
		loc_PhoneNumberInput = By.xpath(prop.getProperty("ControlPage.PhoneNumberInput"));
		loc_TypeInput = By.xpath(prop.getProperty("ControlPage.TypeInput"));
		loc_Address = By.xpath(prop.getProperty("ControlPage.Address"));
		loc_Note = By.xpath(prop.getProperty("ControlPage.Note"));
		loc_BookTicket = By.xpath(prop.getProperty("ControlPage.BookTicket"));
		loc_AddressFocused = By.xpath(prop.getProperty("ControlPage.AddressFocused"));
		loc_Phonenumber = By.xpath(prop.getProperty("ControlPage.PhoneNumber"));
		loc_Statues = By.xpath(prop.getProperty("ControlPage.Statues"));
		loc_StatuesSuccess = By.xpath(prop.getProperty("ControlPage.StatuesSuccess"));
		loc_SuccessTab = By.xpath(prop.getProperty("ControlPage.SuccessTab"));
		loc_NoticeAdrressIncorrect = By.xpath(prop.getProperty("ControlPage.NoticeAdrressIncorrect"));
	}

	public void CreateTicket() {
		ClickElement(loc_CreateTicket);
	}

	public void EnterPhonenumber(String value) {
		EnterText(loc_PhoneNumberInput, value);
	}

	public void EnterName(String value) {
		EnterText(loc_NameInput, value);
	}

	public void SelectType(String value) {
	
			int loaiXe = Integer.parseInt(value);
			if (loaiXe < 0 && loaiXe > 3) {
				loaiXe = 0;
			}
			SelectDropdownByIndex(loc_TypeInput, value);

	}

	public void EnterAddress(String diemDon) {
		EnterText(loc_Address, diemDon);
		ClickElement(loc_AddressFocused);
	}

	public void EnterNotes(String value) {
		EnterText(loc_Note, value);
	}

	public void ClickBookTicketButton() {
		ClickElement(loc_BookTicket);
	}

	public void BookTicket(String tenKhachHang, String soDienThoai, String loaiXe, String diemDon, String ghiChu) {
		CreateTicket();
		EnterName(tenKhachHang);
		EnterPhonenumber(soDienThoai);
		SelectType(loaiXe);
		EnterAddress(diemDon);
		EnterNotes(ghiChu);
		ClickBookTicketButton();
	}

	public void VerifyCreateTicketSuccess(String value) {
		WaitTimeSecond(5);
		CheckElementExist(loc_Phonenumber);
		String actual = GetText(loc_Phonenumber);
		System.out.println(actual);
		String failureText = "Create data is failed....";
		Assert.assertEquals(failureText, value, actual);
	}

	public void VerifyDataOfTicketInDB(String value) {
		CheckElementExist(loc_Phonenumber);
		String actual = connect.GetPhoneNumber();
		System.out.println("Verify the created ticket is saved in database");
		String failureText = "Data of Ticket is not exits....";
		Assert.assertEquals(failureText, value, actual);
	}

	public void DeleteTicket(String numberphone) {
		if (connect.CheckPhonenumberIsExits(numberphone)) {
			connect.DeleteData(numberphone);
		}
	}

	public void ChanngeStatus(String phone, int status) {
		connect.EditStatus(phone, status);
		WaitTimeSecond(10);
	}

	public void RefreshControlPage() {
		RefreshPage();
	}

	public void GotoSuccessTab() {
		ClickElement(loc_SuccessTab);
	}

	public void VerifyStatus(String value) {
		ValidateFieldContainsText(loc_Statues, value);
	}

	public void VerifyStatusSuccess(String value) {
		ValidateFieldContainsText(loc_StatuesSuccess, value);
	}
	
	public void VerifyAddressIncorrect(){
		CheckElementExist(loc_NoticeAdrressIncorrect);
	}

	
}

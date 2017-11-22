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
	public By loc_taoChuyenMoi;
	public By loc_bangDieuXe;
	public By loc_khachHang;
	public By loc_soDienThoai;
	public By loc_loaiXe;
	public By loc_diemDon;
	public By loc_ghiChu;
	public By loc_datChuyen;
	public By loc_diemDenFocused;
	public By loc_phonenumber;
	public By loc_statues;
	public By loc_statuesSuccess;
	public By loc_successTab;

	public String result_TestCase;
	ConnectDB connect = new ConnectDB();

	public Control(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
		Properties prop = Utils.loadProperties("ObjectRepo/DieuXePage.properties");
		loc_taoChuyenMoi = By.xpath(prop.getProperty("DieuXePage.TaoChuyenMoi"));
		loc_bangDieuXe = By.xpath(prop.getProperty("DieuXePage.BangDieuXe"));
		loc_khachHang = By.xpath(prop.getProperty("DieuXePage.TenKhachHang"));
		loc_soDienThoai = By.xpath(prop.getProperty("DieuXePage.SoDienThoai"));
		loc_loaiXe = By.xpath(prop.getProperty("DieuXePage.LoaiXe"));
		loc_diemDon = By.xpath(prop.getProperty("DieuXePage.DiemDon"));
		loc_ghiChu = By.xpath(prop.getProperty("DieuXePage.GhiChu"));
		loc_datChuyen = By.xpath(prop.getProperty("DieuXePage.DatChuyen"));
		loc_diemDenFocused = By.xpath(prop.getProperty("DieuXePage.DiemDenFocused"));
		loc_phonenumber = By.xpath(prop.getProperty("DieuXePage.PhoneNumber"));
		loc_statues = By.xpath(prop.getProperty("DieuXePage.Statues"));
		loc_statuesSuccess = By.xpath(prop.getProperty("DieuXePage.StatuesSuccess"));
		loc_successTab = By.xpath(prop.getProperty("DieuXePage.SuccessTab"));
	}

	public void TaoChuyenMoi() {
		ClickElement(loc_taoChuyenMoi);
	}

	public void NhapSoDienThoai(String value) {
		try {
			long soDienThoai = Long.parseLong(value);
			EnterText(loc_soDienThoai, value);
		} catch (Exception e) {
			System.out.println("");
		}
	}

	public void NhapKhachHang(String value) {
		EnterText(loc_khachHang, value);
	}

	public void ChonLoaiXe(String value) {
		try {
			int loaiXe = Integer.parseInt(value);
			if (loaiXe < 0 && loaiXe > 3) {
				loaiXe = 0;
			}
			SelectDropdownByIndex(loc_loaiXe, value);

		} catch (Exception e) {
			System.out.println("Data is not correct...");
		}

	}

	public void ChonDiemDon(String diemDon) {
		EnterText(loc_diemDon, diemDon);
		ClickElement(loc_diemDenFocused);
	}

	public void NhapGhiChu(String value) {
		EnterText(loc_ghiChu, value);
	}

	public void ClickNutDatChuyen() {
		ClickElement(loc_datChuyen);
	}

	public void DieuXe(String tenKhachHang, String soDienThoai, String loaiXe, String diemDon, String ghiChu) {
		TaoChuyenMoi();
		NhapKhachHang(tenKhachHang);
		NhapSoDienThoai(soDienThoai);
		ChonLoaiXe(loaiXe);
		ChonDiemDon(diemDon);
		NhapGhiChu(ghiChu);
		ClickNutDatChuyen();

	}

	public void VerifyCreateTicketSuccess(String value) {
		WaitTimeSecond(5);
		CheckElementExist(loc_phonenumber);
		String actual = GetText(loc_phonenumber);
		System.out.println(actual);
		String failureText = "Create data is failed....";
		Assert.assertEquals(failureText, value, actual);
	}

	public void VerifyDataOfTicketInDB(String value) {
		CheckElementExist(loc_phonenumber);
		ConnectDB db = new ConnectDB();
		String actual = db.GetPhoneNumber();
		String failureText = "Data of Ticket is not exits....";
		Assert.assertEquals(failureText, value, actual);
	}

	public void DeleteTicket(String numberphone) {
		connect.DeleteData(numberphone);
	}

	public void ChanngeStatus(String phone, int status) {
		connect.EditStatus(phone, status);
		WaitTimeSecond(5);
	}

	public void RefreshDieuXePage() {
		RefreshPage();
	}

	public void GotoSucessTab() {
		ClickElement(loc_successTab);
	}

	public void VerifyStatus(String value) {
		ValidateFieldContainsText(loc_statues, value);
	}

	public void VerifyStatusSuccess(String value) {
		ValidateFieldContainsText(loc_statuesSuccess, value);
	}
}

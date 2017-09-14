package Pages;

import java.util.Properties;
import java.util.concurrent.TimeUnit;
import javax.xml.xpath.XPath;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import Providers.WebDriverProvider;
import Utils.Utils;

public class DieuXe extends WebDriverProvider {
	
	public WebDriver driver;
	public By loc_taoChuyenMoi;
	public By Loc_bangDieuXe;
	public By loc_khachHang;
	public By loc_soDienThoai;
	public By loc_loaiXe;
	public By loc_diemDon;
	public By loc_ghiChu;
	public By loc_datChuyen;
	
	public String result_TestCase;
	
	public DieuXe (WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver 		= driver;
		Properties prop 	= Utils.loadProperties("ObjectRepo/DieuXePage.properties");
		loc_taoChuyenMoi	= By.xpath(prop.getProperty("DieuXePage.TaoChuyenMoi"));
		Loc_bangDieuXe 		= By.xpath(prop.getProperty("DieuXePage.BangDieuXe"));
		loc_khachHang 		= By.xpath(prop.getProperty("DieuXePage.TenKhachHang"));
		loc_soDienThoai 	= By.xpath(prop.getProperty("DieuXePage.SoDienThoai"));
		loc_loaiXe 			= By.xpath(prop.getProperty("DieuXePage.LoaiXe"));
		loc_ghiChu	 		= By.xpath(prop.getProperty("DieuXePage.GhiChu"));
		loc_datChuyen 		= By.xpath(prop.getProperty("DieuXePage.DatChuyen"));
	}
	
	public void TaoChuyenMoi(){
		getElement(loc_taoChuyenMoi).click();
	}
	
	public void DatXe(String tenKhachHang, String soDienThoai, String loaiXe, String diemDon, String ghiChu){
		try {
			long soDT = Long.parseLong(soDienThoai);
			sendKeys(loc_khachHang, tenKhachHang);
			sendKeys(loc_soDienThoai , soDienThoai);
			selectDropdown(loc_loaiXe, loaiXe);
			
			
		} catch (Exception e) {
			
		}
		
	}
	
}

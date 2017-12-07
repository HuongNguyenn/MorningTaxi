package Scenarios;

import java.util.HashMap;
import java.util.List;
import org.testng.annotations.Test;
import Pages.Control;
import Pages.Login;
import Providers.ConstantsProvider;
import Providers.WebDriverProvider;
import Utils.Utils;

/*
 * Author: HuongNguyen
 * Date: 11/10/2017
 * Scenario: Create Ticket
	
		Setup:
			1. Dang nhap trang web TaxiMorning		
		TestCase:
			1. Click nut Tao Chuyen Moi
			2. Nhap thong tin chuyen di
			7. Click Huy chuyen
			8. Verify huy chuyen
			9. Nhap so dien thoai
			10. Chon loai xe
			11. Nhap diem don
			12. Nhap ghi chu
			13. Click Dat chuyen
			15. Verify thông tin chuyến đi được lưu trên màn hình
			14. Verify data duoc luu trong CSDL
			
			16. Verify trạng thái "Dang cho"
			17. Verify Tai xe nhan
			18. Verify Tai xe don, trang thai "Dang don"
			19. Verify trạng thái "Tong dai moi khach"
			20. Verify du lieu hien thi trong tab Hoan Thanh voi trang thai "Co khach"
			21. Verify trang thai Tra khach
			
		Cleanup:
			1. Xoa du lieu trong DB
			2. Verify du lieu khong con hien thi tren man hinh*/

public class CreateTicket extends WebDriverProvider {

	Control control = new Control(wd);
	Utils utils = new Utils();

	@Test
	public void TestRunner() throws Exception {
		Login login = new Login(wd);
		List<HashMap<String, String>> listData = utils.getTestData(ConstantsProvider.pathFile,
				ConstantsProvider.sheetName1, ConstantsProvider.tableName);
		for (int i = 0; i < listData.size(); i++) {
			try {

				// Go to website
				GoToUrl(ConstantsProvider.Firefox);

				// Login
				login.LoginMorningTaxi(ConstantsProvider.userName1, ConstantsProvider.passWord1);
				login.VerifyLoginSuccess();

				// Tao chuyen moidieu
				control.BookTicket(listData.get(i).get("Name"), listData.get(i).get("Phonenumber"),
						listData.get(i).get("Type"), listData.get(i).get("Address"), listData.get(i).get("Note"));

				// Verify tao chuyen thanh cong
				control.VerifyCreateTicketSuccess(listData.get(i).get("Phonenumber"));

				// Verify data in database
				control.VerifyDataOfTicketInDB(listData.get(i).get("Phonenumber"));

				// Verify trang thai Dang cho
				control.VerifyStatus("Đang chờ");

				// Thay doi trang thai cua chuyen -> Dang don
				control.ChanngeStatus(listData.get(i).get("Phonenumber"), 2);

				// Refresh trang
				control.RefreshControlPage();

				// Verify trang thai Dang don
				control.VerifyStatus("Đang đón");

				// Thay doi trang thai cua chuyen -> Tong dai moi khach
				control.ChanngeStatus(listData.get(i).get("Phonenumber"), 9);

				// Refresh trang
				control.RefreshControlPage();

				// Verify trang thai Tong dai moi khach
				control.VerifyStatus("Tổng đài mời khách");

				// Thay doi trang thai cua chuyen -> Co khach
				control.ChanngeStatus(listData.get(i).get("Phonenumber"), 3);

				// Refresh trang
				control.RefreshControlPage();

				// Refresh trang
				control.GotoSucessTab();

				// Verify trang thai Tong dai moi khach
				control.VerifyStatus("Có khách");

				// Ghi ket qua tren file excel
				utils.writeFile(ConstantsProvider.pathFile, ConstantsProvider.sheetName1, ConstantsProvider.result_pass,
						i + 2, 7);

			} catch (Exception e) {
				// Delete data
				control.DeleteTicket(listData.get(i).get("Phonenumber"));
				// Ghi ket qua tren file excel
				utils.writeFile(ConstantsProvider.pathFile, ConstantsProvider.sheetName1, ConstantsProvider.result_false,
						i + 2, 7);
			}
			control.exits();
		}
	}

}

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
			1. Login TaxiMorning		
		TestCase:
			1. Click "Tao Chuyen Moi" button
			2. Enter name 
			3. Enter phonenumber
			4. Select type of taxi
			5. Enter address
			6. Enter notes
			7. Click "Dat chuyen" button
			8. Verify the created ticket is displayed in table
			9. Verify the created ticket is saved in database
			10. Verify status of ticket is "Dang cho"
			11. Change status of ticket in database
			12. Refresh page
			13. Verify status of ticket is "Dang don"
			14. Change status of ticket in database
			15. Refresh page
			16. Verify status of ticket is "Tong dai moi khach"
			17. Change status of ticket in database
			18. Refresh page
			19. Verify status of ticket is "Co khach"
			20. Change status of ticket in database
			21. Refresh page
			22. Verify status of ticket is "Tra khach"
			
		Cleanup:
			1. Delete ticket
			2. Verify a create ticket is not dislayed*/

public class CreateTicket extends WebDriverProvider {

	Control control = new Control(wd);
	Utils utils = new Utils();

	@Test
	public void TestRunner() throws Exception {
		Login login = new Login(wd);
		List<HashMap<String, String>> listData = utils.GetTestData(ConstantsProvider.pathFile,
				ConstantsProvider.sheetName1, ConstantsProvider.tableName);
		for (int i = 0; i < listData.size(); i++) {
			try {

				// Go to website
				GoToUrl(ConstantsProvider.Firefox);

				// Login
				login.LoginMorningTaxi(ConstantsProvider.userName1, ConstantsProvider.passWord1);
				login.VerifyLoginSuccess();

				// Create ticket
				control.CreateTicket();
				control.EnterName(listData.get(i).get("Name"));
				control.EnterPhonenumber(listData.get(i).get("Phonenumber"));
				control.SelectType(listData.get(i).get("Type"));
				control.EnterNotes(listData.get(i).get("Note"));
				System.out.println("nsdbsjdfh:"+ listData.get(i).get("Address"));
				if (listData.get(i).get("Address")=="") {
					control.ClickBookTicketButton();
					control.VerifyAddressIncorrect();
				}else {
					control.EnterAddress(listData.get(i).get("Address"));
					control.ClickBookTicketButton();
					// Verify the created ticket is displayed in table
					control.VerifyCreateTicketSuccess(listData.get(i).get("Phonenumber"));

					// Verify the created ticket is saved in database
					control.VerifyDataOfTicketInDB(listData.get(i).get("Phonenumber"));

					// Verify status of ticket is "Dang cho"
					control.VerifyStatus("Đang chờ");

					// Change status of ticket in database
					control.ChanngeStatus(listData.get(i).get("Phonenumber"), 2);

					// Refresh page
					control.RefreshControlPage();

					//  Verify status of ticket is "Dang don"
					control.VerifyStatus("Đang đón");

					// Change status of ticket in database
					control.ChanngeStatus(listData.get(i).get("Phonenumber"), 9);

					// Refresh page
					control.RefreshControlPage();

					// Verify status of ticket is "Tong dai moi khach"
					control.VerifyStatus("Tổng đài mời khách");

					// Change status of ticket in database
					control.ChanngeStatus(listData.get(i).get("Phonenumber"), 3);

					// Refresh page
					control.RefreshControlPage();

					// Go to Success Tab
					control.GotoSuccessTab();

					// Verify status of ticket is "Tong dai moi khach"
					control.VerifyStatusSuccess("Có khách");
					
					// Change status of ticket in database
					control.ChanngeStatus(listData.get(i).get("Phonenumber"), 4);
					
					// Refresh page
					control.RefreshControlPage();
					
					// Go to Success Tab
					control.GotoSuccessTab();
					
					// Verify status of ticket is "Tra khach"
					control.VerifyStatusSuccess("Trả khách");
				}
				// Ghi ket qua tren file excel
				utils.writeFile(ConstantsProvider.pathFile, ConstantsProvider.sheetName1, ConstantsProvider.result_pass,
						i + 2, 7);
			} catch (Exception e) {
				// Ghi ket qua tren file excel
				utils.writeFile(ConstantsProvider.pathFile, ConstantsProvider.sheetName1, ConstantsProvider.result_false,
						i + 2, 7);
			}
			// Delete data
			control.DeleteTicket(listData.get(i).get("Phonenumber"));
			control.exits();
		}
	}

}

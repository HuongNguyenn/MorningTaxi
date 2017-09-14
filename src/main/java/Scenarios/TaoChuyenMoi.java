package Scenarios;

import java.util.HashMap;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import Pages.DieuXe;
import Providers.ConstantsProvider;
import Providers.WebDriverProvider;
import Utils.Utils;

public class TaoChuyenMoi extends WebDriverProvider {
	/*
	 * Author: HuongNguyen
	 * Date: 04/08/2017
	 * Scenario: TinhGia_VerifyValue
		
			Setup:
						
			TestCase:
				1. Click on TinhGia.
				2. Enter the Details.
				3. Click Add Person Button.
				
			Cleanup:			
				1. Back to HomePage*/
	WebDriver driver = null;
	@Test
	public void TestRunner() throws Exception{
//		
//		TaoChuyenMoi taoChuyenMoi = new TaoChuyenMoi(driver);
//		Utils utils = new Utils();
//		List<HashMap<String, String>> listData = utils.getTestData(ConstantsProvider.pathFile,
//				ConstantsProvider.sheetName,ConstantsProvider.tableName);
//		for (int i = 0; i < listData.size(); i++) 
//		 {
//			getURL();
//			
//			taoChuyenMoi.addNewValue(listData.get(i).get("DienTich"), listData.get(i).get("LoaiNha"), listData.get(i).get("MucDoDauTu"), listData.get(i).get("Ham"),
//					listData.get(i).get("Lung"), listData.get(i).get("Lau1"), 
//					listData.get(i).get("Lau2"), listData.get(i).get("Lau3"), listData.get(i).get("SanThuong"), listData.get(i).get("DacDiemNenDat"), 
//					listData.get(i).get("SanLung"), listData.get(i).get("KetCauMai"), 
//					listData.get(i).get("DT_TangHam"), listData.get(i).get("DT_SanTret"), listData.get(i).get("DT_Lung"),
//					listData.get(i).get("DT_Lau1"), listData.get(i).get("DT_Lau2"), listData.get(i).get("DT_Lau3"), 
//					listData.get(i).get("DT_SanThuong"), listData.get(i).get("DT_Mai"));
//
//			Boolean result = taoChuyenMoi.VerifyTinhGia(listData.get(i).get("TongDT_San"),listData.get(i).get("TongDT_SanChiPhi"),listData.get(i).get("TongChiPhi"));
//			if(result = true){
//				utils.writeFile(ConstantsProvider.pathFile, ConstantsProvider.sheetName, ConstantsProvider.result_pass, i+2, 25);
//			} else{
//				utils.writeFile(ConstantsProvider.pathFile, ConstantsProvider.sheetName, ConstantsProvider.result_false, i+2, 25);
//			}
//			
//			exits();
//
//		 }
<<<<<<< HEAD
//
=======
		//
>>>>>>> 8f66a8eebc3fb9af04146e54d84ad7513611c7a5
	}
}

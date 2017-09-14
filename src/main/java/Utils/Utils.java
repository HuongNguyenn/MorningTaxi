package Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Utils {
	private HSSFWorkbook wb;
	private HSSFSheet sheet;
	public static String getAbsoluteFilePath(String relativeFilepath) {
		String curDir = System.getProperty("user.dir");
		String absolutePath = curDir + "/src/main/resources/"
				+ relativeFilepath;
		return absolutePath;

	}

	public static Properties loadProperties(String fileName) {
		Properties configProperies = null;
		FileReader in;
		try {
			in = new FileReader(getAbsoluteFilePath(fileName));
			configProperies = new Properties();
			configProperies.load(in);
			in.close();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return configProperies;
	}

	public static List<HashMap<String, String>> getTestData(String xlFilePath,
			String sheetName, String tableName) {
		List<HashMap<String, String>> listData = new ArrayList<HashMap<String, String>>();
		try {
			WorkbookSettings ws = new WorkbookSettings();
			ws.setEncoding("Cp1252");
			Workbook workbook = Workbook.getWorkbook(new File(xlFilePath));
			Sheet sheet = workbook.getSheet(sheetName);
			int startRow, startCol, endRow, endCol;
			Cell tableStart = sheet.findCell(tableName);
			startRow = tableStart.getRow() + 1;
			startCol = tableStart.getColumn();

			Cell tableEnd = sheet.findCell(tableName, startCol + 1,
					startRow + 1, 100, 64000, false);

			endRow = tableEnd.getRow();
			endCol = tableEnd.getColumn();

			for (int i = startRow + 1; i < endRow; i++) {
				HashMap<String, String> valSet = new HashMap<String, String>();
				for (int j = startCol + 1; j < endCol; j++) {
					valSet.put(sheet.getCell(j, startRow).getContents().trim(),
							sheet.getCell(j, i).getContents().trim());
				}
				listData.add(valSet);
			}

		} catch (NullPointerException ne) {
			System.out.println("Not found data table: " + tableName);
			assert false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (listData);
	}

	public static void captureScreen(WebDriver driver, String testcase,
			String fileName) {
		System.out.println("Capture Screenshoot " + fileName);
		String path;
		File scrFile = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);
		String curDir = System.getProperty("user.dir");
		Date date = new Date();
		path = curDir + "/target/screenshoot/" + testcase + "/"
				+ date.getTime() + "_";
		File f = new File(path);
		if (!f.exists())
			f.mkdirs();
		String filePath = path + fileName + ".png";
		try {
			FileUtils.copyFile(scrFile, new File(filePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	
		
		
		//lay ra danh sach cac sheetName
		public List<String> getAllSheetName(String pathName) throws IOException{
			File file = new File(pathName);
			FileInputStream fis = new FileInputStream(file);
			//tao workbook tro den file excel
			wb = new HSSFWorkbook(fis);
			List<String> sheetNames = new ArrayList<String>();
			for (int i = 0; i < wb.getNumberOfSheets(); i++) {
				sheetNames.add(wb.getSheetName(i));
			}
			return sheetNames;
		}
		//lay ra tong so hang
		public int getRowTotal(){
			return (sheet.getLastRowNum()+1);
		}
		//lay ra tong so cot
		public int getColTotal(){
			return (sheet.getRow(0).getLastCellNum());
		}
		//lay gia tri cua mot o
		public String getCellData(int rownum, int colnum){
			String data= null;
			int t;
			
			//lay ra 1 hang
			HSSFRow row= sheet.getRow(rownum);
			//lay 1 cot tai hang do
			HSSFCell cell = row.getCell(colnum);
			if (cell == null) {
				data = "";
			}else if(cell.getCellType()== cell.CELL_TYPE_NUMERIC){
				 t= (int) cell.getNumericCellValue();
				 data = String.valueOf(t);
			}else {
				data = cell.getStringCellValue();
			}
			return data;
			
		}
		//doc file->ma tran 2 chieu
		public String[][] readFile(String pathName, String sheetName ) throws Exception{
			
			File file = new File(pathName);
			System.out.println("ok");
			String[][] tabArray = null;
			try {
				FileInputStream fis = new FileInputStream(file);
				System.out.println("ok");
				wb = new HSSFWorkbook(fis);
				
				sheet = wb.getSheet(sheetName);
				System.out.println("ok");
				
				int rowTotal = getRowTotal();
				int colTotal = getColTotal();
				
				tabArray = new String[rowTotal][colTotal];
				
				for (int i = 0; i < rowTotal; i++) {
					
					for (int j = 0; j < colTotal; j++) {
						tabArray[i][j] = getCellData(i, j);
					}
				}

			} catch (FileNotFoundException e) {

				System.out.println("#1 getLocator " + e.getMessage());

			} catch (IOException e) {

				System.out.println("#2 getLocator " + e.getMessage());

			}
			return (tabArray);

		}
		//ghi vao file tai 1 o voi 1 gia tri data
		public void writeFile(String pathName, String sheetName, String data, int rownum, int colnum) throws Exception{
			File file = new File(pathName);
			
			FileInputStream fis = new FileInputStream(pathName);
			wb = new HSSFWorkbook(fis);
			sheet = wb.getSheet(sheetName);
			
			HSSFRow row = sheet.getRow(rownum);
			HSSFCell cell = null;
			if (row==null) {
				row = sheet.createRow(rownum);
			}else{
				cell= row.getCell(colnum);
				if (cell== null) {
					cell = row.createCell(colnum);
				}else{
					cell.setCellValue("");
				}
				
			}
			cell.setCellValue(data);
			
			FileOutputStream fos = new FileOutputStream(pathName);
			wb.write(fos);
			fos.close();

		}
		
	

}

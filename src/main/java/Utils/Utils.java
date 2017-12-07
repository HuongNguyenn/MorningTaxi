package Utils;

import java.io.File;
import java.io.FileInputStream;
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
import jxl.read.biff.BiffException;
import jxl.write.Formula;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import Providers.ConstantsProvider;

public class Utils {
	private HSSFWorkbook wb;
	private HSSFSheet sheet;

	public static String getAbsoluteFilePath(String relativeFilepath) {
		String curDir = System.getProperty("user.dir");
		String absolutePath = curDir + "/src/main/resources/" + relativeFilepath;
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
			e.printStackTrace();
		}
		return configProperies;
	}
	
	// Read file
	public List<HashMap<String, String>> getTestData(String xlFilePath, String sheetName, String tableName) {
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

			Cell tableEnd = sheet.findCell(tableName, startCol + 1, startRow + 1, 100, 64000, false);

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
	
	public void WriteFileExcel(String pathName, String sheetName, String data, int rownum, int colnum) {
		Workbook workbook;
		WritableWorkbook writeWorkbook;
		try {
			// open file
			workbook = Workbook.getWorkbook(new File(pathName));
			// create file copy of root file to write file
			writeWorkbook = Workbook.createWorkbook(new File(pathName), workbook);
			// get sheet to write
			WritableSheet sheet1 = writeWorkbook.getSheet(sheetName);
			Formula f = new Formula(colnum, rownum, data);
			sheet1.addCell(f);
			writeWorkbook.write();
			// close
			writeWorkbook.close();
		} catch (IOException e) {
			System.out.println("File not found\n" + e.toString());
		} catch (RowsExceededException e) {
			System.out.println("File not found\n" + e.toString());
		} catch (WriteException e) {
			System.out.println("File not found\n" + e.toString());
		} catch (BiffException e) {
			System.out.println("File not found\n" + e.toString());
		}
		System.out.println("open and write success");
	}
	
	// Capture screen
	public static void captureScreen(WebDriver driver, String testcase, String fileName) {
		System.out.println("Capture Screenshoot " + fileName);
		String path;
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String curDir = System.getProperty("user.dir");
		Date date = new Date();
		path = curDir + "/target/screenshoot/" + testcase + "/" + date.getTime() + "_";
		File f = new File(path);
		if (!f.exists())
			f.mkdirs();
		String filePath = path + fileName + ".png";
		try {
			FileUtils.copyFile(scrFile, new File(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// write file
	public void writeFile(String pathName, String sheetName, String data, int rownum, int colnum) throws Exception {
		FileInputStream fis = new FileInputStream(pathName);
		wb = new HSSFWorkbook(fis);
		sheet = wb.getSheet(sheetName);

		HSSFRow row = sheet.getRow(rownum);
		HSSFCell cell = null;
		if (row == null) {
			row = sheet.createRow(rownum);
		} else {
			cell = row.getCell(colnum);
			if (cell == null) {
				cell = row.createCell(colnum);
			} else {
				cell.setCellValue("");
			}

		}
		cell.setCellValue(data);

		FileOutputStream fos = new FileOutputStream(pathName);
		wb.write(fos);
		fos.close();

	}
	

}

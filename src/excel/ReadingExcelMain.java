package excel;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class ReadingExcelMain {

	public static void main(String[] args) {
		String fileName = "bookList.xls";
		List<ExcelVO> data = new ArrayList<ExcelVO>();
		try(FileInputStream fis = new FileInputStream(fileName)) {
			HSSFWorkbook workbook = new HSSFWorkbook(fis);
			HSSFSheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rows = sheet.rowIterator();
			rows.next(); //jumping a column name
			String[] imsi = new String[5];
			while(rows.hasNext()) {
				HSSFRow row = (HSSFRow)rows.next();
				Iterator<Cell> cells = row.cellIterator();
				int index = 0;
				while(cells.hasNext()) {
					HSSFCell cell = (HSSFCell)cells.next();
					imsi[index++] = cell.toString();
				}
				data.add(new ExcelVO(imsi[0], imsi[1], imsi[2], imsi[3], imsi[4]));
			}
			showExcelData(data);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	private static void showExcelData(List<ExcelVO> data) {
		for(ExcelVO vo : data) {
			System.out.println(vo);
		}
	}
	
}

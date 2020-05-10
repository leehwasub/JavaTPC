package excel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class SavingImageMain {

	public static void main(String[] args) {
		try {
			// make work book
			Workbook wb = new HSSFWorkbook();
			// make sheet
			Sheet sheet = wb.createSheet("My Sample Excel");
			// get the image with Stream
			InputStream is = new FileInputStream("pic.jpg");
			// convert the image to byte array
			byte[] bytes = IOUtils.toByteArray(is);
			int pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
			is.close();
			
			CreationHelper helper = wb.getCreationHelper();
			
			Drawing drawing = sheet.createDrawingPatriarch();
			
			ClientAnchor anchor = helper.createClientAnchor();
			anchor.setCol1(1);
			anchor.setRow1(2);
			anchor.setCol2(2);
			anchor.setRow2(3);
			
			Picture pic = drawing.createPicture(anchor, pictureIdx);
			
			// create a cell on row 2, col 1
			Cell cell = sheet.createRow(2).createCell(1);
			
			// multiply 256 to make original width
			int w = 20 * 256;
			sheet.setColumnWidth(1, w);
			
			// multiply 256 to make original height
			short h = 120 * 20;
			cell.getRow().setHeight(h);
			
			FileOutputStream fileOut = new FileOutputStream("myFile.xls");
			wb.write(fileOut);
			fileOut.close();
			System.out.println("이미지 생성 성공");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}

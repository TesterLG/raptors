package io.raptor.util;

import java.io.File;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;



public class ExcleParser {
	Workbook book= null;
	public  ExcleParser(String file) {
		 try {
			book=WorkbookFactory.create(new File(file));
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
public String getValue(String sheetName,int x,int y){
	String value="";
	Sheet sheet=null;
	if (book!=null) {
		sheet=book.getSheet(sheetName);
		if (sheet!=null) {
			Row row=sheet.getRow(x);
			if (row!=null) {
				getCellValue(row.getCell(y));
				
			}
		}
	}
	return value;
	
}

	private static String getCellValue(Cell cell){
		String value="";
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			value=cell.getStringCellValue();
			break;
        case Cell.CELL_TYPE_BOOLEAN:
			value=String.valueOf(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_NUMERIC:
			double nvalue=cell.getNumericCellValue();
			if (nvalue-(int)nvalue>0) {
				value=String.valueOf(nvalue);
			}else {
				value=String.valueOf((int)nvalue);
			}
			break;
		default:
			break;
		}
		;
		
		return value;
	}

}


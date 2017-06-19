package io.raptor.utils;

import java.io.File;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
//poi-3.8.jar
import org.apache.poi.ss.usermodel.Workbook;
//poi-ooxml-3.8.jar 官方包里默认是不包含xmlbean.jar包的，需要自己添加xmlbeans.jar这个包.坑...
import org.apache.poi.ss.usermodel.WorkbookFactory;

/*
 * 根据 x y 坐标拿excle中的数据.
 */
public class ExcelParser {

	private Workbook book = null;

	public ExcelParser(String file) {
		try {
			book = WorkbookFactory.create(new File(file));
		} catch (InvalidFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Object[] getRowValue(String sheetName, int rowNumber) {
		Object[] result = null;
		Sheet sheet = null;

		if (book != null) {
			sheet = book.getSheet(sheetName);
			if (sheet != null) {
				Row row = sheet.getRow(rowNumber);// 从0开始,返回值,接一下
				int cellnumber = row.getPhysicalNumberOfCells();
				result = new Object[cellnumber];
				for (int i = 0; i < cellnumber; i++) {
					result[i] = getCellValue(row.getCell(i));
				}
			}
		}
		// Object[] result=new Object[0];
		return result;
	}

	public String getValue(String sheetName, int x, int y) {
		String value = "";
		Sheet sheet = null;
		if (book != null) {
			sheet = book.getSheet(sheetName);
			if (sheet != null) {
				Row row = sheet.getRow(x);// 从0开始,返回值,接一下
				if (row != null) {
					value = getCellValue(row.getCell(y)); // 取完行取列
				}
			}
		}
		return value;
	}

	public static void main(String[] args) {
		ExcelParser ep = new ExcelParser("data.xlsx");
		Object[] result=ep.getRowValue("case01", 1);
		for(int i=0;i<result.length;i++){
			System.out.println(result[i]);
		}
	}

	/*
	 * public static void main(String[] args) { // excel基本概念:workbook > sheet >
	 * 行row , 列column // 2个处理的类,一个是07之前的03.xls,一个是之后.xlsx;可以用WorkbookFactory //
	 * sheet.createRow(arg0) 可读可写 try { // 对异常的处理考虑的多是开发的好习惯. //
	 * 这个里可能sheet不存在.故意写个错的case00,没报错...可能是个空值. //
	 * sheet.getPhysicalNumberOfRows()取到最大有效行号,可以用它写个if判断取到的行号是否有效. //
	 * 数据如果不是string会报 Cannot get a text value from a numeric //
	 * cell,转成string先知道是什么格式 // Sheet sheet = book.getSheet("case01"); if (sheet
	 * != null) { Row row = sheet.getRow(1);// 从0开始,返回值,接一下 if (row != null) {
	 * System.out.println(getCellValue(row.getCell(0))); // 取完行取列 } } } catch
	 * (Exception e) { // TODO Auto-generated catch block e.printStackTrace(); }
	 * 
	 * }
	 */
	private static String getCellValue(Cell cell) {
		// Cell.CELL_TYPE_BLANK//原来有值,删掉了
		String value = "";
		switch (cell.getCellType()) { // 返回是int,不是枚举就是常量
		case Cell.CELL_TYPE_STRING:
			value = cell.getStringCellValue();
			break;

		case Cell.CELL_TYPE_BOOLEAN:
			// String 中有方法,不好处容易丢精度.
			value = String.valueOf(cell.getBooleanCellValue());
			break;

		case Cell.CELL_TYPE_NUMERIC:
			double nvalue = cell.getNumericCellValue();
			if (nvalue - (int) nvalue > 0) {// 有小数
				value = String.valueOf(nvalue);
			} else {
				value = String.valueOf((int) nvalue);
			}
			break;

		default:
			break;
		}

		return value;
	}
}

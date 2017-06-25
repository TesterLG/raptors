package io.raptor.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 * Dec 12, 2016 Excel组件
 * 
 * @author perlly
 * @version 1.0
 * @since 1.0
 */
//根据格式来读,否则不知道去哪里去读.
public class ExcelReader {

	private Workbook book = null;
	private Sheet sheet;
	private Cell[] cell;
	private int r_m, l_m;
	private List<String> line = null;

	// 初始化excle文件
	public ExcelReader(String filePath) {
		try {
			Workbook book1 = Workbook.getWorkbook(new File(filePath));
			book = book1;
			// System.out.println("?????"+book.getSheet(0).getCell(3, 1).toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("log::error：读取Excel文件失败。");
			e.printStackTrace();
			book = null;
		}
	}

	// 获取文件行数
	public int getRows(int shet) {
		if (book != null) {
			sheet = book.getSheet(shet);
			return sheet.getRows();
		} else
			System.out.println("log::error：没有打开Excel文件，无法获取行数。");

		return 0;
	}

	// 获取文件列数
	public int getCols(int shet) {
		if (book != null) {
			sheet = book.getSheet(shet);
			return sheet.getColumns();
		} else
			System.out.println("log::error：没有打开Excel文件，无法获取列数。");

		return 0;
	}

	// 指定sheet表格
	public void useSheet(int shet) {
		if (book != null) {
			sheet = book.getSheet(shet);
			r_m = sheet.getRows();
			l_m = sheet.getColumns();
		} else
			System.out.println("log::error：没有打开Excel文件，无法选的表格。");
	}

	// 读取每一行的数据
	public List<String> ReadLine(int l) {
		line = new ArrayList<String>();
		try {
			if (sheet != null) {
				if (l < r_m) {
					cell = sheet.getRow(l);
					for (int i = 0; i < cell.length; i++) {
						line.add(cell[i].getContents());
					}
				}
			} else
				System.out.println("log::error：没有打开Excel文件，无法读取文件。");
		} catch (Exception e) {
			System.out.println("log::error：Excel操作不合法，请检查程序。");
			e.printStackTrace();
		}
		return line;
	}

	public void close() {
		if (book != null) {
			try {
				book.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}

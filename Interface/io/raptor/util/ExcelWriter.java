package io.raptor.util;

import java.io.File;
import java.util.ArrayList;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * Dec 12, 2016 Excel组件
 * 
 * @author perlly
 * @version 1.0
 * @since 1.0
 */

public class ExcelWriter {

	private WritableWorkbook wbook = null;
	private Workbook book = null;
	private WritableSheet sheet;
	private Label label;
	public int line=0;
	public int clo=9;

	// 初始化excle文件
	public ExcelWriter(String file1, String file2) {
		try {
			book = Workbook.getWorkbook(new File(file1));
			//read是写在缓存中,最后要存在一个新文件中.
			wbook = Workbook.createWorkbook(new File(file2), book);
			sheet = wbook.getSheet(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("log::error：写入Excel文件失败。");
			e.printStackTrace();
			book = null;
			wbook=null;
		}
	}

	// 从某一列开始，写入一行数据
	public int writeRows(int r, ArrayList<String> list) {
		if (wbook != null) {
			for (int i = 0; i < list.size(); i++) {
				try {
					label = new Label(r, i, list.get(i));
					sheet.addCell(label);
					return 0;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("log::error：写入行内容失败。");
					e.printStackTrace();
				}
			}
		} else
			System.out.println("log::error：没有打开Excel文件，无法写入数据。");
		return -1;
	}

	// 写入一个单元格数据
	public int writeCell(int i, int j, String c) {
		if (wbook != null) {
			try {
				label = new Label(j, i, c);
				sheet.addCell(label);
				return 0;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("log::error：写入单元格失败。");
				e.printStackTrace();
			}
		} else
			System.out.println("log::error：没有打开Excel文件，无法写入数据。");
		return -1;
	}

	//最好close,避免被占用,不别的不能用.
	public void close() {
		if (wbook != null) {
			try {
				//记得保存.
				wbook.write();
				wbook.close();
				book.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}

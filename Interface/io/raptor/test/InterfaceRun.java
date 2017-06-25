package io.raptor.test;

import java.util.List;

import io.raptor.util.ExcelReader;
import io.raptor.util.ExcelWriter;

public class InterfaceRun {

	public static void main(String[] args) {
		Interface inter=new Interface();
//		inter.Post("http://localhost/Case02/login", "loginName=abcdf&password=123456");
//		System.out.println(inter.json.jsonList);
		ExcelReader excelr=new ExcelReader("D:\\DNtest\\idata\\login.xls");
		ExcelWriter excelw=new ExcelWriter("D:\\DNtest\\idata\\login.xls","D:\\DNtest\\idata\\loginrs.xls");
//	
		//存读出来的数据
		List<String> l=null;
		
		int r=excelr.getRows(0);
	    excelr.useSheet(0);
//	    boolean flg=false;
	    if(r<=2){
	    	System.out.println("log::error:文件格式有误,请检查!");
	    }else{
	    	inter.excelw= excelw;//这样就可以直接在Interface类中写
	    	for(int i=1;i<r;i++){
	    	    inter.row=i;
	    		l=excelr.ReadLine(i);
	    		if(l.get(0).equals("")){//针对那一行开始没有文字就开始读用例.自己规定的.
	    			
	    		switch (l.get(2)){
	    		case "post":
	    			inter.Post(l);
	    			inter.checkRes();
	    			break;
	    			
	    		default:
	    			break;
	    		}
	    		
	    	   }
	    	}
	    	excelr.close();
	    	excelw.close();
	    	}
	}
}

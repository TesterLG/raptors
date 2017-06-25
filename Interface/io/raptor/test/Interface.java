package io.raptor.test;

import java.util.List;

import io.raptor.util.ExcelWriter;
import io.raptor.util.JsonParse;
import io.raptor.util.SendURL;

public class Interface {
	public SendURL u=null;
	public JsonParse json=null;	
	List<String> l=null;
	public int row=0;
	public ExcelWriter excelw=null;
	public String res;


	public Interface(){
		//u定义成url,后面url.sendPost报错. 写成this.url也行,不如用u直接.
		u=new SendURL();
		json=new JsonParse();
	}
	
	public void Post(List<String> list){
		l=list;
		res =u.sendPost(l.get(3), l.get(4));
		System.out.println(res);
		json.jsonList.clear();//不clear,json越来越长.后句设置成1,也行,在Parse代码中clear
		json.Parse(res,0, false);
	}
	
	public void checkRes(){
	   try {
		switch (l.get(5).toString()){
		   case"equal":
			   System.out.println(json.jsonList);
		   if(l.get(7).equals(json.jsonList.get(Integer.parseInt(l.get(8))-1).get(l.get(6)))){
//			   if (l.get(7).equals(json.jsonList.get(json.jsonList.size() - Integer.parseInt(l.get(8))).get(l.get(6)))) {  
			   System.out.println("Pass");
				   excelw.writeCell(row, 9, "Pass");//结果保存到想要的结果中
				   excelw.writeCell(row, 10, res);
			   }else{
				   System.out.println("Fail");
				   excelw.writeCell(row, 9, "Fail");
				   excelw.writeCell(row, 10, res);
			   }
			   break;
			   //自己可以接着写unequal等等...
			   default:
				   break;
		   }
	} catch (Exception e) {
		System.out.println("Fail");
		excelw.writeCell(row, 9, "Fail");
		excelw.writeCell(row, 10, u.getExp());
	}
	}
	
}

package io.raptor.test;

import java.util.ArrayList;
import java.util.List;

import io.raptor.util.SendURL;

public class test {

	public static void main(String[] args) {
		Interface inter=new Interface();
		
		List<String> l=new ArrayList<String>();
//		l.add("http://localhost/Class2/login");
		l.add("http://localhost/Class2/login");
		l.add("loginName=perlly&password=teacher");
//		l.add("loginName=tese&password=dlkajf5");
		inter.Post(l);

		System.out.println(inter.json.jsonList.toString());
//		SendURL url = new SendURL();
//		String str = url.sendPost("http://localhost/Case02/login", "loginName=perlly3&password=teacher2");
//		System.out.println(str);
		
	}

}

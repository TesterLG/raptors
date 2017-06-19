package io.raptor.test;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import io.raptor.utils.RunAppCase;

public class TestChrome {
	RunAppCase apprun=new RunAppCase();
	/*
	@Before
	public void setup(){
	apprun.runAppium("Android", "NRI74L8P99999999", "com.google.android.apps.chrome.Main", "com.android.chrome");
	apprun.wait(5000);
	apprun.runApp("NRI74L8P99999999", "com.google.android.apps.chrome.Main", 3000);
	}
	@Test
	public void testbaidu(){
		apprun.driver.get("http://www.baidu.com");
		apprun.driver.findElementById("index-kw").sendKeys("test");
		apprun.driver.findElementById("index-bn").click();
		apprun.wait(5000);
		apprun.runAdb("adb shell input swipe 200 200 200 400");
	}
	@After
	public void tearDown(){
		apprun.driver.quit();
	}
	*/
	
public static void main(String[] args) {
	RunAppCase apprun=new RunAppCase();
	apprun.runAppium("Android", "NRI74L8P99999999", "com.google.android.apps.chrome.Main", "com.android.chrome");
	apprun.wait(5000);
	apprun.runApp("NRI74L8P99999999", "com.google.android.apps.chrome.Main", 3000);
	try {
		apprun.driver.get("http://www.baidu.com");
		apprun.driver.findElementById("index-kw").sendKeys("test");
		apprun.driver.findElementById("index-bn").click();

		apprun.runAdb("adb shell input swipe 400 400 400 1000");
		apprun.wait(5000);
		apprun.driver.quit();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
}

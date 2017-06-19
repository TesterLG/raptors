package io.raptor.test;

import io.raptor.app.appDriver;
import io.raptor.utils.RunAppCase;

public class TestQQLike {
	/*
	 * 实现QQ添加QQ（已添加的，可以搜一下），然后实现查看倩倩老师动态，并对倩倩老师的日志点赞、好评、转发。
	 * 
	 */
	public static void main(String[] args) {
		// appDriver app=new appDriver("NRI74L8P99999999",
		// "com.tencent.mobileqq.activity.SplashActivity",
		// "http://127.0.0.1:4723/wd/hub", "10000");
		// AndroidDriver driver=app.getdriver();
		RunAppCase rac = new RunAppCase();
		// 启动qq,qq之前已经登录.Appium 客户端已经运行下的方式
		// rac.runApp("NRI74L8P99999999",
		// "com.tencent.mobileqq.activity.SplashActivity",1000);

		// 命令行方式启动appium
		rac.adbDevice("NRI74L8P99999999");
		rac.runAppium("Android", "NRI74L8P99999999", "com.tencent.mobileqq.activity.SplashActivity",
				"com.tencent.mobileqq");
		rac.wait(5000);
		rac.runApp("NRI74L8P99999999", "com.tencent.mobileqq.activity.SplashActivity", 5000);
		// 点击联系人
		rac.clickElement(
				"//android.widget.TabWidget[@resource-id='android:id/tabs']/android.widget.FrameLayout[1]/android.widget.RelativeLayout[1]/android.view.View[1]");
		// 点击搜索qq号,随便一个qq号.
		rac.inputText("//android.widget.EditText[@resource-id='com.tencent.mobileqq:id/et_search_keyword']", "12345");
		// 一系列点击,进入日志列表页面.
		// rac.clickElement("//android.widget.AbsListView[@resource-id='com.tencent.mobileqq:id/name']/android.widget.LinearLayout[2]/android.widget.RelativeLayout[1]/android.widget.RelativeLayout[1]");
		// rac.clickElement(
		// "//android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.RelativeLayout[1]/android.widget.RelativeLayout[1]/android.widget.AbsListView[1]/android.widget.LinearLayout[2]/android.widget.LinearLayout[1]/android.widget.LinearLayout[4]/android.widget.LinearLayout[1]/android.widget.TextView[1]");
		// rac.clickElement("//android.widget.LinearLayout/android.widget.FrameLayout[2]/android.widget.LinearLayout[1]");
		rac.clickElement(
				"//android.widget.AbsListView[@resource-id='com.tencent.mobileqq:id/name']/android.widget.LinearLayout[2]/android.widget.RelativeLayout[1]/android.widget.RelativeLayout[1]");
		rac.clickElement(
				"//android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.RelativeLayout[1]/android.widget.RelativeLayout[1]/android.widget.AbsListView[1]/android.widget.LinearLayout[2]/android.widget.LinearLayout[1]/android.widget.LinearLayout[4]/android.widget.LinearLayout[1]/android.widget.TextView[1]");
		// 点击日志
		rac.clickElement(
				"//android.widget.LinearLayout/android.widget.FrameLayout[2]/android.widget.LinearLayout[1]/android.widget.TextView[1]");
		rac.wait(5000);

		// 因为内嵌浏览器,要用adb命令进行测试
		// 点击第一篇日志
		rac.runAdb("adb shell input tap 300 300");
		// 向下翻页
		rac.runAdb("adb shell input keyevent 93");
		rac.pressKey(93);
		// 点击'赞'
		rac.runAdb("adb shell input tap 730 830");
		// 点击'留言',并留言
		rac.runAdb("adb shell input tap 870 870");
		rac.runAdb("adb shell input text hello");
		rac.runAdb("adb shell input tap 990 1000");
		// 转发
		rac.runAdb("adb shell input tap 990 650");
		rac.runAdb("adb shell input text test");
		rac.runAdb("adb shell input tap 1000 1000");
		
		rac.wait(4000);
		rac.driver.quit();
	}

}

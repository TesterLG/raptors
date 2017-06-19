package io.raptor.app;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;

public class appDriver {//Android驱动程序
	private AndroidDriver driver=null;
	//设备名称、app的main Activity类、appium服务器ip端口、等待启动时间
	public appDriver(String deviceName,String appActivity,String AppiumServerIP,int time){
	String platformVersion="4.3";
	//seleium指定属性,装填参数
	DesiredCapabilities capabilities=new DesiredCapabilities();
	capabilities.setCapability("deviceName", deviceName);
	capabilities.setCapability("platformVersion",platformVersion);
	capabilities.setCapability("appActivity", appActivity);
	capabilities.setCapability("noSign", true);
	capabilities.setCapability("noReset", true);
	capabilities.setCapability("unicodeKeyboard", "True");
	capabilities.setCapability("resetKeyboard", "True");
	//电脑连接了多个手机/设备,指定设备
	capabilities.setCapability("udid", deviceName);

	try {
		driver=new AndroidDriver(new URL(AppiumServerIP),capabilities);
		System.out.println("App启动...");
		Thread.sleep(time);
	} catch (MalformedURLException | InterruptedException e) {
		System.out.println("log:error:App启动失败.");
		e.printStackTrace();
	}
	}
	public AndroidDriver getdriver(){
		return this.driver;
	}
}

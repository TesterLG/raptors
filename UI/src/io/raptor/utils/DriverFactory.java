package io.raptor.utils;


import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;

import io.raptor.web.Config;

public class DriverFactory {
	
	
	public static WebDriver getDriver(String type){
		return createDriver(type, null);
	}
	
		
	public static WebDriver getDriver(String type, Object parameter){
		return createDriver(type, parameter);
	}
	
	private static WebDriver createDriver(String type, Object parameter){
		WebDriver driver = null;
		switch (type.toLowerCase()) {
		case "firefox":
			driver = createFireFoxDriver(parameter);
			break;
		case "chrome":
			System.setProperty("webdriver.chrome.driver", "D:\\chromedriver.exe");
			driver = createChromeDriver(parameter);
			break;
		case "ie":
			System.setProperty("webdriver.ie.driver", "D:\\IEDriverServer.exe");
			driver = createIEDriver();
			break;			
		default:
			System.out.println("Error:Invalid Browser Type");
			break;
		}
		
		return driver;
		
	}
	
	private static WebDriver createFireFoxDriver(Object parameter){
		WebDriver driver = null;

		if(parameter==null){
			driver = new FirefoxDriver();	
		}else{
			driver = new FirefoxDriver((FirefoxProfile)parameter);	
		}		
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		return driver;
	}
	
	private static WebDriver createChromeDriver(Object parameter){
		WebDriver driver = null;
		if(parameter==null){
			driver = new ChromeDriver();
		}else{
			driver = new ChromeDriver((ChromeOptions)parameter);	
		}		
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(Config.pageLoadTimeout, TimeUnit.SECONDS);
		return driver;	
	}
	
	private static WebDriver createIEDriver(){
		return new InternetExplorerDriver();		
	}
	
}

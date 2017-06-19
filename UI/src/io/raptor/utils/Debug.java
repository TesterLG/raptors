package io.raptor.utils;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;

import io.raptor.listener.CheckPoint;

public class Debug {

	protected WebDriver driver;
	protected CheckPoint checkPoit;
	@BeforeMethod
	public void init(){
		driver= DriverFactory.getDriver("chrome");
		checkPoit=new CheckPoint(this.getClass().getSimpleName(), "");
	}
	

}

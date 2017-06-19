package io.raptor.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Listeners;

import io.raptor.utils.DriverFactory;
import io.raptor.utils.XmlParser;

public class Locator {

	private XmlParser xp;
	private WebDriver driver;
	private int timeout;
	
	public Locator(WebDriver driver, String path, int timeout){
		this.timeout = timeout;
		this.driver = driver;
		xp = new XmlParser(path);
	}
	
	public void wait(int time){
		int millis = time * 1000;
	    try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public boolean linkTo(String url){
		try {
			driver.get(url);
			return true;
		} catch (Exception e) {
			System.out.println("页面:"+url+" 加载超时");
			return false;
		}
	}
	
	public  void click(String page, String object){
		getElement(page, object).click();
	}
	
	public  void sendKeys(String page, String object, String text){	
		WebElement element = getElement(page, object);
		element.click();
		element.clear();
		element.sendKeys(text);
	}
	
	public Select select(String page, String object){
		return new Select(getElement(page, object));
	}
	
	//下拉菜单随机选择
	public void doRandomSelect(String page, String object, int start){
		Select select1 =  new Select(getElement(page, object));
		double random = Math.random()*(select1.getOptions().size()-start)+start;		
		int k = (int)random;
		select1.selectByIndex(k);		
	}
	
	public void doSelect(String page, String object, String type, String value){
		Select select = new Select(getElement(page, object));
		switch(type.toLowerCase()){
		case "index":
			select.selectByIndex(Integer.parseInt(value.toString()));
			break;
		case "value":
			select.selectByValue(value.toString());
			break;
		case "text":
			select.selectByVisibleText(value.toString());
			break;
		default:
			System.out.println("类型不存在");
			break;
		}
	}
	
	public int getElementsCount(String page, String object){

		return getElements(page, object).size();
		
	}

	  
public  boolean swtichWindowByTitle(String title){
		
		Set<String> handles = driver.getWindowHandles();
		
		for (String handle : handles) {
			driver.switchTo().window(handle);
			if(driver.getTitle().contains(title)){
				return true;
			}
		}
		System.out.println("没有匹配的title!");	
		return false;
	}
	
	//Switch to new window opened 
	public void swtichNextWindow(){
		Set<String> handles = driver.getWindowHandles();
		if(handles.size()>0){
			Object[] arrayHandle = handles.toArray();
			String nextHandle = String.valueOf(arrayHandle[arrayHandle.length-1]);
			driver.switchTo().window(nextHandle);
		}
	}

	public  void scrollToBottom(){
		String jsString =  "scrollTo(0,10000);";
		addJS(jsString);
	}
	public void addJS(String jsCodes) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor)driver;
		jsExecutor.executeScript(jsCodes);	
	}
	
	public  void removeReadonly(String id){
		String jsString = "document.getElementById(\"" + id + "\").removeAttribute(\"readonly\");";
		addJS(jsString);
		
	}
	
	public boolean elementIsPresent(String page, String object){
		final By by = getBy(page, object);
		Boolean flag = false;
		try {
			flag = new WebDriverWait(driver, timeout).until(new ExpectedCondition<Boolean>() {
				@Override
				public Boolean apply(WebDriver d) {
					d.findElement(by);
					return true;
				}
			});
		} catch (Exception e) {

		}
		return flag;
	}
	
	public  void addCookies(Map<String, String> cookies){
		driver.get("");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (String key : cookies.keySet()) {
			driver.manage().addCookie(new Cookie(key,cookies.get(key)));
		}
		
		driver.get("http://www.huicewang.com/ecshop");
	}
//----------------------------------------------------------------------------------	
	
	private  boolean elementIsDisplay(final WebElement element){
		Boolean flag = false;

		flag = new WebDriverWait(driver, timeout).until(new ExpectedCondition<Boolean>() {

				@Override
				public Boolean apply(WebDriver d) {
					return element.isDisplayed();		
				}		
			});	
		return flag;
	}
	
	private WebElement getElement(String page, String object){
		By by = getBy(page, object);
		WebElement element = waitElement(by);
		if(elementIsDisplay(element)){
			return element;
		}else {
			System.out.println("对象存在但不可见");
			return null;
		}
	}
	
	private List<WebElement> getElements(String page, String object){
		By by = getBy(page, object);
		List<WebElement> elements = driver.findElements(by);
		if(elements==null){
			System.out.println("对象查询失败");
			return null;
		}else {
			return elements;
		}
	}
	
	private WebElement waitElement(final By by){
		   WebElement element = null;
			try {
				element = new WebDriverWait(driver, timeout).until(new ExpectedCondition<WebElement>(){
					@Override
					public WebElement apply(WebDriver d) {
						return d.findElement(by);
					}
				});
			} catch (Exception e) {
				System.out.println("超过"+timeout+"s,对象："+by.toString()+"没有找到");
			}
			return element;		
		}
	
	private  By getBy(String page, String object){
		By by = null;
		String type = xp.getElementText("/对象/"+page+"/"+object+"/type");
		String value = xp.getElementText("/对象/"+page+"/"+object+"/value");
		
		if(xp.isExist("/对象/"+page+"/"+object)){
			switch (type.toLowerCase().trim()) {
			case "id":
				by = By.id(value);
				break;
			case "classname":
				by = By.className(value);
				break;
			case "linktext":
				by = By.linkText(value);
				break;
			case "name":
				by = By.name(value);
				break;
			case "partiallinktext":
				by = By.partialLinkText(value);
				break;
			case "tagname":
				by = By.tagName(value);
				break;
				
			case "xpath":
				by = By.xpath(value);
				break;
			
			default:
				System.out.println("对象定位类型不存在");
				break;
			}
		
		}else{
			System.out.println("对象："+page+"-"+object+"在对象库中不存在");
		}

		return by;

	}
	
	
}

package io.raptor.utils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Api{
	private WebDriver driver = null;
	
	public Api(WebDriver driver){
		this.driver=driver;
	}
	

	public void wait(int time){
		int millis = time * 1000;
	    try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	//下拉菜单随机选择
	public void doRandomSelect(By by,int start){
		WebElement element= driver.findElement(by);
		Select select1 = new Select(element);
		double random = Math.random()*(select1.getOptions().size()-start)+start;		
		int k = (int)random;
		select1.selectByIndex(k);		
	}
	
	//下拉菜单选择
	public void select(By by,String type,Object value){
		WebElement element = driver.findElement(by);
		Select select2 = new Select(element);
		switch(type.toLowerCase()){
		case "index":
			select2.selectByIndex(Integer.parseInt(value.toString()));
			break;
		case "value":
			select2.selectByValue(value.toString());
			break;
		case "text":
			select2.selectByVisibleText(value.toString());
			break;
		default:
			System.out.println("类型不存在");
			break;
		}

		
 	}
	
	//文本框
	public void sendkeys(By by,String text){
		WebElement searchText = driver.findElement(by);
		searchText.clear();
		searchText.click();
		searchText.sendKeys(text);
	}
	
	//登录
	public boolean Login(By by,String user,String pwd){
		
		WebElement loginBotton = driver.findElement(by);
		loginBotton.click();
		WebElement setuser =driver.findElement(By.name("username"));
		setuser.click();
		setuser.clear();
		setuser.sendKeys(user);
		WebElement setpwd = driver.findElement(By.name("password"));
		setpwd.click();
		setpwd.clear();
		setpwd.sendKeys(pwd);
		WebElement submit =driver.findElement(By.className("us_Submit"));
		submit.click();
		String result = driver.findElement(By.xpath("//div[@class='boxCenterList RelaArticle']/div/p")).getText();
		String textString = "登录成功";
		if(textString.equals(result)){
			return true;
		}else{
			return false;
		}	
	}
	

	
	//判断订单Map<String,String> 
	
     public static Map<String, String> searchOrderInfoById(WebDriver driver, String id){
		
		Map<String, String> result = new LinkedHashMap<String, String>();
		driver.get("http://www.huicewang.com/ecshop/user.php?act=order_list");
		String trXpath = "//div[@class='userCenterBox boxCenterList clearfix']/table//tr";
		List<WebElement> tr = driver.findElements(By.xpath(trXpath));
		
		for(int i=2;i<=tr.size();i++){
			String tdXpath = trXpath+"["+i+"]//td";		
			List<WebElement> td = driver.findElements(By.xpath(tdXpath));		
			if(td.get(0).getText().equals(id)){
				result.put("订单号", td.get(0).getText());
				result.put("下单时间", td.get(1).getText());
				result.put("订单总金额", td.get(1).getText());
				result.put("订单状态", td.get(1).getText());
				result.put("操作", td.get(1).getText());
			}
		}
		
		return result;
		
	}
	
   public static void doRandomSelect(WebElement select, int start){
		
		Select s = new Select(select);//
		List<WebElement> list = s.getOptions();
		
		if(start>list.size()){  
			start = list.size()-1;
		}
		
		
		Random random = new Random();  
		int index = random.nextInt(list.size()-start)+start;
		//int index = random.nextInt(n);    [0,n） 
		s.selectByIndex(index);
	}

    //标签
	public static void clickByText(WebDriver driver, String linkName){

		List<WebElement> list = driver.findElements(By.xpath("//div[@id='mainNav']/a"));
		
		for (WebElement link : list) {
			link.getText();        
			if(link.getText().equals(linkName)){
				link.click();
				break;
			}
		}
			
	}	
	
	//句柄
	public static boolean swtichWindowByTitle(WebDriver driver, String title){
		
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
	public static void swtichNextWindow(WebDriver driver){
		Set<String> handles = driver.getWindowHandles();
		if(handles.size()>0){
			Object[] arrayHandle = handles.toArray();
			String nextHandle = String.valueOf(arrayHandle[arrayHandle.length-1]);
			driver.switchTo().window(nextHandle);
		}
	}
	
	
	private static void addJS(WebDriver driver, String jsCodes) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor)driver;
		jsExecutor.executeScript(jsCodes);	
	}
	
	
	public static void scrollToBottom(WebDriver driver){
		String jsString =  "scrollTo(0,10000);";
		addJS(driver, jsString);
	}
	
	public static void removeReadonly(WebDriver driver, String id){
		String jsString = "document.getElementById(\"" + id + "\").removeAttribute(\"readonly\");";
		addJS(driver, jsString);
		
	}
	

	//Referenced Libraries中需要导入guava.jar,否则报错
   public static WebElement waitElement(WebDriver driver,final By by,int timeout){
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
	
	
	public static boolean elementIsPresent(WebDriver driver,final By by,int timeout){
		try {
			new WebDriverWait(driver, timeout).until(new ExpectedCondition<Boolean>() {

				@Override
				public Boolean apply(WebDriver d) {
					d.findElement(by);
					return true;
				}
			});
		} catch (Exception e) {
			return false;
		}
		
		return false;
		
	}
	
	public static boolean elementIsDisplay(WebDriver driver,final By by,int timeout){
		boolean isPresent = elementIsPresent(driver, by, timeout);   //先判断元素是否存在
		if(isPresent){  //元素存在，判断元素是否可见
			new WebDriverWait(driver, timeout).until(new ExpectedCondition<Boolean>() {

				@Override
				public Boolean apply(WebDriver d) {
					return d.findElement(by).isDisplayed();		
				}		
			});	
		}else{
			return false;
		}
		return false;
	}
	
	
	public static void addCookies(WebDriver driver,Map<String, String> cookies){
		driver.get("");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (String key : cookies.keySet()) {
			driver.manage().addCookie(new Cookie(key,cookies.get(key)));
		}
		
		driver.get("http://www.huicewang.com/ecshop");
	}
   
   
   
   
}
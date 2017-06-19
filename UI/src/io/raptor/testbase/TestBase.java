package io.raptor.testbase;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
//extentreports-2.41.jar
import com.relevantcodes.extentreports.ExtentReports;

import io.raptor.listener.CheckPoint;
import io.raptor.utils.Api;
import io.raptor.utils.DriverFactory;
import io.raptor.utils.ExtentManager;
import io.raptor.utils.XmlParser;
import io.raptor.web.Config;
import io.raptor.web.Locator;

public class TestBase {

	public static ExtentReports extent = null;
	protected static Map<String, WebDriver> driverMap = new LinkedHashMap<String, WebDriver>();
	protected CheckPoint checkPoint = null;
	protected Api api = null;
	protected WebDriver driver = null;
	protected Locator locator = null;
	
	@BeforeSuite
	public void beforeSuite(){
		extent = ExtentManager.getReporter("report/report.html");
	}
	
	@AfterSuite
	public void afterSuite(){
		extent.flush();
		extent.close();
	}
	
	@Parameters({"browserType"})
	@BeforeTest
	public void beforeTest(String browserType){
		WebDriver driver = DriverFactory.getDriver(browserType);
		driverMap.put(Thread.currentThread().getId()+"", driver);
	}
	
	@Parameters({"browserType"})
	@BeforeClass
	public void beforeClass(String browserType){
		api = new Api(driverMap.get(Thread.currentThread().getId()+""));	
		checkPoint = new CheckPoint(browserType, this.getClass().getSimpleName());
		driver = driverMap.get(Thread.currentThread().getId()+"");
		locator = new Locator(driver, Config.objectRespository, Config.objectTimeOut);

	}
	
	@AfterTest
	public void afterTest(){
		driverMap.get(Thread.currentThread().getId()+"").close();
		driverMap.get(Thread.currentThread().getId()+"").quit();
	}
	
	@DataProvider(name="demo")
	public Object[][] getdata(){

		XmlParser xmlParser = new XmlParser("data.xml");
		String caseName = this.getClass().getSimpleName();
		List<Element> list = xmlParser.getElements("//"+caseName);
		Map<String, String> common = xmlParser.getChildElementInfo("/data/common");
		Object[][] result = new Object[list.size()][];
				
		for (int i=0;i<list.size();i++) {
			Map<String, String> map = xmlParser.getChildElementInfo(list.get(i));			
			Object[] temp = new Object[]{xmlParser.mapMerge(common, map)};
			result[i] = temp;
		}
		return result;

	}
}

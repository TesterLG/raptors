package io.raptor.listener;


import org.testng.Reporter;
import org.testng.asserts.Assertion;
import org.testng.asserts.IAssert;

import com.relevantcodes.extentreports.LogStatus;

public class CheckPoint extends Assertion{

	  private int flag = 0;
	  private String caseName = "";
	  private String testName = "";
	  
	  public CheckPoint(String testName, String caseName) {
		this.testName = testName;
		this.caseName = caseName;
	  }
	  
	  
	  @Override
	  public void onAssertFailure(IAssert assertCommand) {
		  System.out.println(testName+":"+caseName+" 断言失败：实际结果"+assertCommand.getActual()+"预期结果："
				  		+assertCommand.getExpected()+"--"+assertCommand.getMessage());
		  flag = flag+1;
	  }
	
	  public void equals(int a, int b, String message){
		  try {
			assertEquals(a, b, message);
		} catch (Error e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	 
	  public void equals(String a, String b, String message){
		  try {
			assertEquals(a, b, message);
		} catch (Error e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	  
	  public void notEquals(int a, int b, String message){
		  try {
			assertNotEquals(a, b, message);
		} catch (Error e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	  
	  public void result(String message){
		  assertEquals(flag,0);
		  System.out.println(testName+":"+caseName+":"+message);
		  Reporter.log(testName+":"+caseName+":"+message);
	  }
	
	  public void isFail(String message){
		  assertEquals(true,false,message);
	  }
}

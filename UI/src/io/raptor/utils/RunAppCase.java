package io.raptor.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

import org.apache.commons.collections.bag.SynchronizedSortedBag;
import org.openqa.selenium.By;

import io.appium.java_client.android.AndroidDriver;
import io.raptor.app.appDriver;

public class RunAppCase {
public AndroidDriver driver;

public RunAppCase(){
	//打开一个新cmd窗口
//	runCmd("cmd /c start");
}

   public String getText(String xpath){
	   try {
		Thread.sleep(2000);
		   return driver.findElementByXPath(xpath).getText();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   return "";
	   
   }
   
   //点击元素
	public  void inputText(String xpath, String vlaue) {
		try {
			Thread.sleep(2000);
			driver.findElementByXPath(xpath).sendKeys(vlaue);
		} catch (Exception e) {
			System.out.println("Log:error:输入文字失败!");
			e.printStackTrace();
		}
	}

	public  void clickElementCSS(AndroidDriver driver,String css){
		try {
			driver.findElement(By.cssSelector(css));
		} catch (Exception e) {
			System.out.println("log:error:元素获取失败!");
			e.printStackTrace();
		}
	}
	public  void clickElement(String xpath){

		try {
			Thread.sleep(2000);
			driver.findElement(By.xpath(xpath)).click();
		} catch (Exception e) {
			System.out.println("log:error:元素获取失败!");
			e.printStackTrace();
		}
	}
	
	public void wait(int t){
		try {
			Thread.sleep(t);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//启动appium模拟器,创建app对象.
	public void runApp(String deviceName,String appActivity, int wait) {
		appDriver app=new appDriver(deviceName,appActivity,"http://127.0.0.1:4723/wd/hub",wait);
		driver=app.getdriver();
	}
	
	//通过路径启动模拟器,并等待启动完成
	public void runAVD(String avd,int t){
		Run(avd);
		try {
			Thread.sleep(t);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//启动Appium服务
	public void runAppium(String platform,String device,String appactivity,String apppkg){
		if(!isPortUsing(4723)){
		String cmd="cmd /c start appium -a 127.0.0.1 --port 4723 --platform-name "+ platform +" --platform-version 18 --device-name \""+ device + "\" --app-activity "+appactivity +" --app-pkg "+ apppkg+" --automation-name Appium  --log-no-color";
		Run(cmd);
		}else{
			System.out.println("Log::error:Appium 端口4723已被占用,请检查服务是否已经启动.");
		}
		return;
	}
	
	//执行并获取dos命令返回值.
	public ArrayList<String> getCmd(String str){
		String cmd=str;
		Runtime runtime=Runtime.getRuntime();
		Process p=null;
		try {
			p=runtime.exec(cmd);
		} catch (Exception e) {
			System.out.println("log::error：执行cmd命令错误!");
			e.printStackTrace();
		}
		if(p!=null){
			return convertStreamToString(p.getInputStream());
		}else{
			return null;
		}
	}

	//启动adb,连接设备
	public int adbDevice(String device){
		ArrayList<String> ret = null;
		try {
			ret=getCmd("adb connect "+device);
			System.out.println(ret);
			return 0;
		} catch (Exception e) {
			System.out.println("log::error：adb连接Device错误!");
			e.printStackTrace();
		}
//			if (ret.get(ret.size() - 1).contains("connected"))
//				return 0;
//			else {
//				System.out.println("log::error：adb连接Device失败!");
//				return -1;
//			}
			return -1;
	}
	
	public ArrayList<String> convertStreamToString(InputStream ins) {
		ArrayList<String> res=new ArrayList<String>();
		BufferedReader br=null;
		try {
			br=new BufferedReader(new InputStreamReader(ins));
			String line=null;
			while((line=br.readLine())!=null){
				res.add(line);
			}
			return res;
		} catch (Exception e) {
			return null;
		}finally{
			if(br!=null){
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	//查看端口是否被占用
	public boolean isPortUsing(int port) {
		boolean flag=false;
		try {
			InetAddress address=InetAddress.getByName("127.0.0.1");
			Socket socket=new Socket(address, port);
			flag=true;
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	//多线程启动appi服务
	public void Run(String avd) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				runCmd(avd);
				System.out.println("开始运行程序:");
			}
		}).start();;
		
	}

	public void runCmd(String cmdstr) {
		String cmd=cmdstr;
		Runtime runtime=Runtime.getRuntime();
		try {
			runtime.exec(cmd);
		} catch (IOException e) {
			System.out.println("Log::error:执行cmd命令出错!");
			e.printStackTrace();
		}
		return;
	}
	
	public void quit(){
		try {
			driver.quit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//adb命令不涉及坐标,用按键
	public void runAdb(String c){
		String cmd="cmd /c start "+c;
		runCmd(cmd);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void pressKey(int i) {
		//数值键可以用这个方法,每次打开一个cmd框
		String cmd="cmd /c start adb input keyevent "+i;
		runCmd(cmd);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void getUrl(String url){
		try {
			driver.get(url);
		} catch (Exception e) {
			System.out.println("log::error:网址打开失败!");
			e.printStackTrace();
		}
	}
	
}

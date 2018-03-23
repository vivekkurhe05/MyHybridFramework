package com.hybridFramework.testBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.apache.commons.io.FileUtils;

public class TestBase {
	
	static WebDriver driver;
	static Properties OR;
	static File f1;
	static FileInputStream fis;
	
	public void getBrowser(String browser){
		
		
		if(System.getProperty("os.name").contains("Window")){
			if(browser.equalsIgnoreCase("firefox")){
				System.out.println(System.getProperty("user.dir"));
				System.setProperty("webdriver.gecko.driver", "C:\\Users\\sai\\workspace\\geckodriver-v0.16.1-win32\\geckodriver.exe");
				DesiredCapabilities capabilities = DesiredCapabilities.firefox();
				capabilities.setCapability("marionette", true);
				driver = new FirefoxDriver();
			}
			else if((browser.equalsIgnoreCase("chrome"))){
				//We have used new chrome driver downloaded not older. New chrome driver version is 2.28
				System.setProperty("webdriver.chrome.driver", "C:\\workspace\\hybridFramework\\driver"
						+ "\\chromedriver_win32\\chromedriver.exe");
				driver = new ChromeDriver();
				
			}
			
		}
		else if(System.getProperty("os.name").contains("Mac")){
			if(browser.equalsIgnoreCase("firefox")){
				System.out.println(System.getProperty("user.dir"));
				System.setProperty("webdriver.gecko.driver", "C:\\Users\\sai\\workspace\\geckodriver-v0.16.1-win32\\geckodriver.exe");
				driver = new FirefoxDriver();
			}
			else if((browser.equalsIgnoreCase("chrome"))){
				
				System.setProperty("webdriver.chrome.driver", "");
				driver = new ChromeDriver();
				
			}
			
			
		}
				
	}
	
	public static void loadPropertiesFile() throws IOException{
		
		OR = new Properties();
		f1 = new File(System.getProperty("user.dir")+"\\src\\main\\java\\com\\hybridFramework\\config\\config.properties");
		fis = new FileInputStream(f1);
		OR.load(fis);
		
		
		f1 = new File(System.getProperty("user.dir")+"\\src\\main\\java\\com\\hybridFramework\\config\\or.properties");
		fis = new FileInputStream(f1);
		OR.load(fis);

		
	}
	
	
	public static void getScreenShot(String imageName) throws IOException{
		if(imageName.equals("")){
			imageName = "_blank";
		}
		File image =((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		String imageLocation = System.getProperty("user.dir")+"\\src\\main\\java\\com\\hybridFramework\\screenshot";
		
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		String actualImageName = imageLocation+imageName+"_"+formater.format(calendar.getTime())+".png";
		File destFile = new File(actualImageName);
		
		FileUtils.copyFile(image, destFile);
		
	}
	
	public void getPropertiesData(){
		
	}

	
	
	public static void main(String args[]) throws IOException{
		
		TestBase test = new TestBase();
//		test.getBrowser("firefox");
		test.loadPropertiesFile();
		System.out.println(test.OR.getProperty("url"));
		System.out.println(test.OR.getProperty("testname"));
		
	}
	

}

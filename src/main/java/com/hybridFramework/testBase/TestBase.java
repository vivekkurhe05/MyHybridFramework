package com.hybridFramework.testBase;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.hybridFramework.excelReader.Excel_reader;
import com.hybridFramework.helper.Wait.WaitHelper;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class TestBase {
	
	public static final Logger logger = Logger.getLogger(TestBase.class.getName());
	public static WebDriver driver;
	public static Properties OR;
	static File f1;
	static FileInputStream fis;
	
	public static ExtentReports extent;
	public static ExtentTest test;
	public Excel_reader excelreader;
	
	public ITestResult result;
	
	static{
		
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		extent = new ExtentReports(System.getProperty("user.dir")+"/src/main/java/com/hybridFramework/report/test"+formater.format(calendar.getTime())+".html", false);
		
	}
	
	@BeforeTest
	public void launchBrowser(){
		try {
			loadPropertiesFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Config config = new Config(OR);
		getBrowser(config.getBrowser());
		WaitHelper waitHelper = new WaitHelper(driver);
		waitHelper.setImplicitWait(config.getImplicitWait(), TimeUnit.SECONDS);
		waitHelper.setPageLoadTimeout(config.getPageLoadTimeOut(), TimeUnit.SECONDS);
	}
	
	
	public void getBrowser(String browser){
		
		System.out.println("Check Selenium version in pom.xml file - Version should be 3.4.0");
		
		if(System.getProperty("os.name").contains("Window")){
			if(browser.equalsIgnoreCase("firefox")){
				
				System.out.println("Tests running on "+System.getProperty("os.name"));
				System.out.println(System.getProperty("user.dir"));
				System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"/drivers/geckodriver.exe");
				DesiredCapabilities capabilities = DesiredCapabilities.firefox();
				capabilities.setCapability("marionette", true);
				driver = new FirefoxDriver();
			}else if((browser.equalsIgnoreCase("chrome"))){
				//We have used new chrome driver downloaded not older. New chrome driver version is 2.28
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/drivers/chromedriver.exe");
				driver = new ChromeDriver();
				
			}
			
		}else if(System.getProperty("os.name").contains("Linux")){
			if(browser.equalsIgnoreCase("firefox")){
				
				System.out.println("Tests running on "+System.getProperty("os.name"));
				System.out.println(System.getProperty("user.dir"));
				System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"/drivers/geckodriver.exe");
				driver = new FirefoxDriver();
			}else if((browser.equalsIgnoreCase("chrome"))){
				
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/drivers/chromedriver.exe");
				driver = new ChromeDriver();
				
			}
			
		}else if(System.getProperty("os.name").contains("Mac")){
			if(browser.equalsIgnoreCase("firefox")){
				
				System.out.println("Tests running on "+System.getProperty("os.name"));
				System.out.println(System.getProperty("user.dir"));
				System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"/drivers/geckodriver.exe");
				driver = new FirefoxDriver();
			}else if((browser.equalsIgnoreCase("chrome"))){
				
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/drivers/chromedriver.exe");
				driver = new ChromeDriver();
				
			}
			
			
		}
		
					
	}
	
	public void loadPropertiesFile() throws IOException{
		
		String log4jConfigPath = "Log4j.properties";
		PropertyConfigurator.configure(log4jConfigPath);
		OR = new Properties();
		f1 = new File(System.getProperty("user.dir")+"/src/main/java/com/hybridFramework/config/config.properties");
		fis = new FileInputStream(f1);
		OR.load(fis);
		logger.info("Loading config.properties");
		
		
		f1 = new File(System.getProperty("user.dir")+"/src/main/java/com/hybridFramework/config/or.properties");
		fis = new FileInputStream(f1);
		OR.load(fis);
		logger.info("Loading or.properties");
		
		f1 = new File(System.getProperty("user.dir")+"/src/main/java/com/hybridFramework/properties/homepage.properties");
		fis = new FileInputStream(f1);
		OR.load(fis);
		logger.info("Loading homepage.properties");


		
	}
	
	
	public String getScreenShot(String imageName) throws IOException{
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
		
		return actualImageName;
		
	}
	
	public WebElement waitForElement(WebDriver driver, long timeOutInSeconds, WebElement element){
		
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		return wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	
	public WebElement waitForElementWithPollingInterval(WebDriver driver, long timeOutInSeconds, WebElement element){
		
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.pollingEvery(5, TimeUnit.SECONDS);
		wait.ignoring(NoSuchElementException.class);
		return wait.until(ExpectedConditions.elementToBeClickable(element)); 
	}

	
	public void implicitWait(long time){
		
		driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
	}
	
	public void getPropertiesData(){
		
	}
	
	
	public void getResult(ITestResult result) throws IOException{
		
		if(result.getStatus() == ITestResult.SUCCESS){
			test.log(LogStatus.PASS, result.getName() + " test is pass");
		}else if(result.getStatus() == ITestResult.SKIP){
			test.log(LogStatus.SKIP, result.getName() + " test is skipped and skip reason is " + result.getThrowable());
		}else if(result.getStatus() == ITestResult.FAILURE){	
			test.log(LogStatus.FAIL, result.getName() + " test is failed " + result.getThrowable());
			String screen = getScreenShot("");
			test.log(LogStatus.FAIL, test.addScreenCapture(screen));
		}else if(result.getStatus() == ITestResult.STARTED){
			test.log(LogStatus.INFO, result.getName() + " test is started");
		}
		
	}
	
	
	@AfterMethod
	public void afterMethod(ITestResult result) throws IOException{
		
		getResult(result);
	}
	
	
	@BeforeMethod
	public void beforeMethod(Method result){
		test = extent.startTest(result.getName());
		test.log(LogStatus.INFO, result.getName() + " test started");
		
	}
	
	
	@AfterClass(alwaysRun = true)
	public void endTest(){
		
		driver.quit();
		extent.endTest(test);
		extent.flush();
	}
	
	
	
	// This method is for finding single elements
	public WebElement getLocator(String locator) throws Exception{
		
		System.out.println(locator);
		String[] split = locator.split(":");
		String locatorType = split[0];
		String locatorValue = split[1];
		System.out.println("Locator type : " + locatorType);
		System.out.println("Locator value : " + locatorValue);

		
		if(locatorType.toLowerCase().equals("id"))
			return driver.findElement(By.id(locatorValue));
		else if(locatorType.toLowerCase().equals("name"))
			return driver.findElement(By.name(locatorValue));
		else if(locatorType.toLowerCase().equals("classname") || locatorType.toLowerCase().equals("class"))
			return driver.findElement(By.className(locatorValue));
		else if(locatorType.toLowerCase().equals("tagname") || locatorType.toLowerCase().equals("tag"))
			return driver.findElement(By.tagName(locatorValue));
		else if(locatorType.toLowerCase().equals("linktext") || locatorType.toLowerCase().equals("link"))
			return driver.findElement(By.linkText(locatorValue));
		else if(locatorType.toLowerCase().equals("partiallinktext"))
			return driver.findElement(By.partialLinkText(locatorValue));
		else if(locatorType.toLowerCase().equals("cssselector") || locatorType.toLowerCase().equals("css"))
			return driver.findElement(By.cssSelector(locatorValue));
		else if(locatorType.toLowerCase().equals("xpath"))
			return driver.findElement(By.xpath(locatorValue));
		else
			throw new Exception("Unknown locator type '" + locatorType + "'");
		
	}
	
	
	public List<WebElement> getLocators(String locator) throws Exception{
		
		String[] split = locator.split(":");
		String locatorType = split[0];
		String locatorValue = split[1];
		System.out.println(locatorType);
		System.out.println(locatorValue);
		
		if(locatorType.toLowerCase().equals("id"))
			return driver.findElements(By.id(locatorValue));
		else if(locatorType.toLowerCase().equals("name"))
			return driver.findElements(By.name(locatorValue));
		else if(locatorType.toLowerCase().equals("classname") || locatorType.toLowerCase().equals("class"))
			return driver.findElements(By.className(locatorValue));
		else if(locatorType.toLowerCase().equals("tagname") || locatorType.toLowerCase().equals("tag"))
			return driver.findElements(By.tagName(locatorValue));
		else if(locatorType.toLowerCase().equals("linktext") || locatorType.toLowerCase().equals("link"))
			return driver.findElements(By.linkText(locatorValue));
		else if(locatorType.toLowerCase().equals("partiallinktext"))
			return driver.findElements(By.partialLinkText(locatorValue));
		else if(locatorType.toLowerCase().equals("cssselector") || locatorType.toLowerCase().equals("css"))
			return driver.findElements(By.cssSelector(locatorValue));
		else if(locatorType.toLowerCase().equals("xpath"))
			return driver.findElements(By.xpath(locatorValue));
		else
			throw new Exception("Unknown locator type '" + locatorType + "'");
	}
	
	
	public WebElement getWebElement(String locator) throws Exception{
		
		return getLocator(OR.getProperty(locator));
	}
	
	
	public List<WebElement> getWebElements(String locator) throws Exception{
		
		return getLocators(OR.getProperty(locator));
	}
	
	
	public String[][] getData(String excelName, String sheetName) throws Exception{
		String excelLocation = System.getProperty("user.dir")+"/src/main/java/com/hybridFramework/data/"+excelName;
		excelreader = new Excel_reader();
		return excelreader.getExcelData(excelLocation, sheetName);	
	}
	
	
	public static void updateResultupdateResult(int indexSI,  String screenShotLocation,String response) throws IOException {
		String startDateTime = new SimpleDateFormat("MM-dd-yyyy_HH-ss").format(new GregorianCalendar().getTime());
	    System.out.println("startDateTime---"+startDateTime);
		String userDirector = System.getProperty("user.dir");

		String resultFile = userDirector + "/src/main/java/com/hybridFramework/screenshot/TestReport.html";
		
		File file = new File(resultFile);
		System.out.println(file.exists());

		if (!file.exists()) {
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("<html>" + "\n");
			bw.write("<head><title>" + "Test execution report" + "</title>" + "\n");
			bw.write("</head>" + "\n");
			bw.write("<body>");
			bw.write("<font face='Tahoma'size='2'>" + "\n");
			bw.write("<u><h1 align='center'>" + "Test execution report" + "</h1></u>" + "\n");
			bw.flush();
			bw.close();
		}
		BufferedWriter bw1 = new BufferedWriter(new FileWriter(file, true));
		if (indexSI == 1) {

			bw1.write("<table align='center' border='0' width='70%' height='10'>");
			bw1.write("<tr><td width='70%' </td></tr>");
			bw1.write("<table align='center' border='1' width='70%' height='47'>");
			bw1.write("<tr>");
			bw1.write("<td colspan='1' align='center'><b><font color='#000000' face='Tahoma' size='2'>ScriptName :&nbsp;&nbsp;&nbsp;</font><font color='#0000FF'' face='Tahoma' size='2'>Resiliency Test </font></b></td>");
			bw1.write("<td colspan='2' align='left'><b><font color='#000000' face='Tahoma' size='2'>Start Time :&nbsp;</font></b><font color='#0000FF'' face='Tahoma' size='1'>" + startDateTime + " </font></td>");
			bw1.write("</tr>");
			bw1.write("</tr>");
			bw1.write("<td  bgcolor='#CCCCFF' align='center'><b><font color='#000000' face='Tahoma' size='2'>S.No</font></b></td>");
			bw1.write("<td  bgcolor='#CCCCFF' align='left'><b><font color='#000000' face='Tahoma' size='2'>Screen Shot</font></b></td>");
			bw1.write("<td  bgcolor='#CCCCFF' align='center'><b><font color='#000000' face='Tahoma' size='2'>Response </font></b></td>");
			bw1.write("</tr>");
		}

		bw1.write("<tr>" + "\n");
		bw1.write("<td bgcolor='#FFFFDC'align='Center'><font color='#000000' face='Tahoma' size='2'>" + indexSI + "</font></td>" + "\n");
		bw1.write("<td  bgcolor='#FFFFDC' valign='middle' align='left'><b><font color='#000000' face='Tahoma' size='2'>" + "<img src="+screenShotLocation+" alt='Smiley face' height='500' width='750'>" + "</font></b></td>" + "\n");
		bw1.write("<td  bgcolor='#FFFFDC' valign='middle' align='left'><b><font color='#000000' face='Tahoma' size='2'>" + response + "</font></b></td>" + "\n");
		bw1.write("</tr>" + "\n");
		bw1.write("</body>" + "\n");
		bw1.write("</html>");
		bw1.flush();
		bw1.close();
	}

	
	public static void main(String args[]) throws Exception{
		
		TestBase test = new TestBase();
//		test.getBrowser("firefox");
		test.loadPropertiesFile();
		System.out.println(TestBase.OR.getProperty("username"));
//		test.getWebElement("username");
//		test.getLocator(test.OR.getProperty("password"));
		
	}
	

}

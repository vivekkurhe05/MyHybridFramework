package com.hybridFramework.homePage;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.hybridFramework.testBase.TestBase;

public class TestDataDrivenTest1 extends TestBase {
	
	WebDriver driver;

	@DataProvider(name="testData")
	public Object[][] dataSource() throws Exception{
		
		return getData("TestData.xlsx", "Registration");
	}

	
	@Test(dataProvider="testData")
	public void testLogin(String variable1, String variable2, String variable3, String variable4, String variable5, String variable6){
		
		System.out.println("variable2 : "+variable1);
		System.out.println("variable2 : "+variable2);
		System.out.println("variable3 : "+variable3);
		System.out.println("variable4 : "+variable4);
		System.out.println("variable5 : "+variable5);
		System.out.println("variable6 : "+variable6);
		
//		driver.findElement(By.xpath("")).sendKeys(userName);
//		driver.findElement(By.xpath("")).sendKeys(password);
//		driver.findElement(By.xpath("")).sendKeys(runmode);
	}


}

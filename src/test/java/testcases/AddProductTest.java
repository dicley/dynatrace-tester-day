package testcases;

import base.TestBase;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import jxl.read.biff.BiffException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;
import utilities.ExcelReader;
import utilities.Reporter;

import java.awt.*;
import java.io.IOException;

public class AddProductTest extends TestBase{

	LoginPage LoginPageObject;

	String URL = utilities.LoadProperties.userData.getProperty("SiteURL");

	@BeforeMethod
	public void beforeMethod() throws IOException {
		openBrowser(URL);
		LoginPageObject = new LoginPage(driver);

	}

	@DataProvider
	public Object[][] data() throws IOException, BiffException {
		ExcelReader xl = new ExcelReader("Sheet1", "FirstTestCase");
		Object[][] data = xl.getTestdata();
		return data;
	}


	@Test(dataProvider = "data")
	@Severity(SeverityLevel.NORMAL)
	@Description("Check Login Page")
	public void DebugTest()throws InterruptedException, AWTException, BiffException, IOException
	{
			

		System.out.println("###############################################################################################");
		Reporter.Log	  ("################################## Test Case Passed  ##########################################");
		System.out.println("###############################################################################################");
	}
}

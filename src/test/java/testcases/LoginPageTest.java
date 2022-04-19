package testcases;

import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.TestBase;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import pages.InventoryPage;
import pages.LoginPage;
import utilities.Helper;
import utilities.JsonDataReader;
import utilities.Perform;

public class LoginPageTest extends TestBase{

	JsonDataReader jsonReader;
	LoginPage LoginPageObject;
	InventoryPage InventoryPageObject;

	String URL = utilities.LoadProperties.userData.getProperty("SiteURL");

	@BeforeMethod
	public void beforeMethod() {
		openBrowser(URL);
		jsonReader = new JsonDataReader();
		LoginPageObject = new LoginPage(driver);
		InventoryPageObject = new InventoryPage(driver);
	}

	@Severity(SeverityLevel.NORMAL)
	@Description("Check Login Page")
	@Test(testName = "Login Test",alwaysRun = true)
	public void LoginTest() throws  IOException, ParseException {
		jsonReader.JsonReader();

		String actualResult = Perform.getText(driver,LoginPageObject.getLoginButton());
		Helper.assertEquals("Login",actualResult,1,true);

		LoginPageObject.checkLoginButton();

		LoginPageObject.login(jsonReader.userName,jsonReader.password);

		String actualProductResult = Perform.getText(driver,InventoryPageObject.getProductsHeaderTitle());
		Helper.assertEquals("PRODUCTS",actualProductResult,1,true);
	}
}

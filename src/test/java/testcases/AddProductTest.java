package testcases;

import base.TestBase;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import jxl.read.biff.BiffException;
import org.json.simple.parser.ParseException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.InventoryItemPage;
import pages.InventoryPage;
import pages.LoginPage;
import utilities.ExcelReader;
import utilities.Helper;
import utilities.JsonDataReader;
import utilities.Perform;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class AddProductTest extends TestBase{
	JsonDataReader jsonReader;

	LoginPage LoginPageObject;
	InventoryPage InventoryPageObject;
	InventoryItemPage InventoryItemPageObject;

	String URL = utilities.LoadProperties.userData.getProperty("SiteURL");

	@BeforeMethod
	public void beforeMethod() throws IOException {
		jsonReader = new JsonDataReader();
		openBrowser(URL);
		LoginPageObject = new LoginPage(driver);
		InventoryPageObject = new InventoryPage(driver);
		InventoryItemPageObject = new InventoryItemPage(driver);
	}

	@DataProvider
	public Object[][] data() throws IOException, BiffException {
		ExcelReader xl = new ExcelReader("Sheet1", "CheckProductTest");
		Object[][] data = xl.getTestdata();
		return data;
	}


	@Test(dataProvider = "data")
	@Severity(SeverityLevel.NORMAL)
	@Description("Check Login Page")
	public void AddProductTest(String selectedProductName,String wantedProductName, String addProductToCart, String addSecondProductToCart
	,String sortingList) throws IOException, ParseException {
		jsonReader.JsonReader();

		LoginPageObject.login(jsonReader.userName,jsonReader.password);

		String actualProductResult = Perform.getText(driver,InventoryPageObject.getProductsHeaderTitle());
		Helper.assertEquals("PRODUCTS",actualProductResult,1,true);

		InventoryPageObject.getProductName(selectedProductName);
		Helper.assertEquals(wantedProductName,InventoryPageObject.getProductName(selectedProductName),1,true);

		InventoryPageObject.addProductToCart(addProductToCart);
		InventoryPageObject.getSelectedProductPriceAndAddToCart();
		Helper.assertEquals("REMOVE",InventoryItemPageObject.getRemoveButtonName(),1,true);

		InventoryItemPageObject.clickBackToProducts();

		InventoryPageObject.addProductToCart(addSecondProductToCart);
		InventoryPageObject.getSelectedProductPriceAndAddToCart();
		Helper.assertEquals("REMOVE",InventoryItemPageObject.getRemoveButtonName(),1,true);

		InventoryItemPageObject.clickBackToProducts();

		InventoryPageObject.sortProductsBtName(sortingList);

		InventoryPageObject.logOut();
		String actualResult = Perform.getText(driver,LoginPageObject.getLoginButton());
		Helper.assertEquals("Login",actualResult,1,true);

	}
}

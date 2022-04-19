package pages;

import base.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import utilities.Perform;
import utilities.Reporter;

import java.util.List;

public class InventoryPage extends PageBase{

	public InventoryPage(WebDriver driver) {
		super(driver);
	}

	private By productsTitle		 		= By.xpath("//span[text()='Products']");
	private By inventoryListTable			= By.xpath("//div[@class='inventory_list']");
	private By productPrice					= By.xpath("//div[@class='inventory_details_price']");
	private By addToCartBtn					= By.xpath("//button[text()='Add to cart']");
	private By nameAToZList					= By.xpath("//select[@class='product_sort_container']");
	private By burgerMenuBtn				= By.xpath("//button[text()='Open Menu']");
	private By logOutBtn					= By.xpath("//a[text()='Logout']");


	public By getProductsHeaderTitle(){
		return productsTitle;
	}

	public String getProductName(String productName){
		String prodName = Perform.getText(driver,By.xpath("//div[text()='"+productName+"']"));
		return prodName;
	}

	public void addProductToCart(String productName) {
		WebElement productsTable = driver.findElement(inventoryListTable);
		List<WebElement> products = productsTable.findElements(By.xpath("//div[text()='" + productName + "']"));

		for (WebElement requiredProduct : products) {
			if (requiredProduct.getText().contains(productName)) {
				requiredProduct.click();
				Reporter.Log("Product [" + productName+ "] Added to cart");
			break;
			}
		}
	}

	public String getSelectedProductPriceAndAddToCart(){
		String prodPrice = Perform.getText(driver,productPrice);
		Perform.click(driver,addToCartBtn);
		return prodPrice;
	}

	public void sortProductsBtName(String sortText){
		Select productList = new Select(driver.findElement(nameAToZList));
		productList.selectByVisibleText(sortText);
	}

	public void logOut(){
		Perform.click(driver,burgerMenuBtn);
		Perform.click(driver,logOutBtn);
	}

}

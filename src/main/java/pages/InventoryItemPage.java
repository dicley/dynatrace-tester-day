package pages;

import base.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utilities.Perform;

import java.util.List;

public class InventoryItemPage extends PageBase{

	public InventoryItemPage(WebDriver driver) {
		super(driver);
	}

	private By backToProductsBtn			= By.id("back-to-products");
	private By removeBtn					= By.xpath("//button[text()='Remove']");

	public String getRemoveButtonName(){
		return Perform.getText(driver,removeBtn);
	}

	public void clickBackToProducts(){
		Perform.click(driver,backToProductsBtn);
	}
}

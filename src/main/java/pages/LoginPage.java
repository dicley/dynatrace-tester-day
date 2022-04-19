package pages;

import base.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utilities.Perform;

public class LoginPage extends PageBase{

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	private By userNameTxt		 		= By.id("user-name");
	private By passwordFieldTxt			= By.id("password");
	private By loginBtn					= By.id("login-button");

	public By getLoginButton(){
		return loginBtn;
	}

	public void login(String userNameUsed, String passwordUsed){
		Perform.type(driver,userNameTxt,userNameUsed);
		Perform.type(driver,passwordFieldTxt,passwordUsed);
		Perform.click(driver,loginBtn);
	}

}

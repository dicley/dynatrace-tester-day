/**
 * Engineer Mohamed Moustafa 2022.
 * All Rights Reserved.
 *
 * ver          Creator          Date        Comments
 * ----- ---------------------  ----------  ----------------------------------------
 * 1.00     Mohamed Moustafa    08/02/2022  - Script created.
 */
package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class PageBase {
	public WebDriver driver;

	//Super constructor
	protected PageBase(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	//you can add below more methods for different types of inputs like checkboxes or dropdown lists and so on
}



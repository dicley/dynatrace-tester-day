/**
 * Engineer Mohamed Moustafa 2022.
 * All Rights Reserved.
 *
 * ver          Creator          Date        Comments
 * ----- ---------------------  ----------  ----------------------------------------
 * 1.00     Mohamed Moustafa    08/02/2022  - Script created.
 */
package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import utilities.Helper;
import utilities.Reporter;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;

public class TestBase {
	public static WebDriver driver;

	static String driverType = utilities.LoadProperties.userData.getProperty("Browser");
	
	public static ChromeOptions chromeOption() {
		ChromeOptions options = new ChromeOptions();
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default.content_settings.popups", 0);
		options.setExperimentalOption("prefs", chromePrefs);
		options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
		options.addArguments("--start-maximized");
		options.addArguments("--incognito");
		options.setHeadless(false);
		return options;
	}


	@BeforeSuite
	public static WebDriver getDriver() {
		if (driverType.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver= new FirefoxDriver();

		} else if (driverType.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver= new ChromeDriver(chromeOption());

		} else if (driverType.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			driver= new EdgeDriver();
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

		Reporter.startRecording();

		return driver;
	}


	@AfterSuite
	public static void closeDriver() throws IOException {
		Reporter.stopRecording();
		driver.manage().deleteAllCookies();
		driver.quit();
		Reporter.attachRecording();
	}

	public static void openBrowser(String URL) {
		driver.navigate().to(URL);
		Reporter.Log("Navigated to browser : " + URL);
	}

	//////////////////////// take screenshot when test case failed and add it in the Screenshots folder ////////////////////////
	@AfterMethod(alwaysRun = true)
	public void screenShotOnFailure(ITestResult result) throws IOException {
		if (result.getStatus() == ITestResult.FAILURE) {
			System.out.println("Failed");
			System.out.println("Taking Screenshot....");
			Helper.captureScreenShot(driver, result.getName());
			Helper.addAttachmenetsToAllure( result.getName(), "Screenshots\\"+ result.getName()+ ".png");
		}
	}
}

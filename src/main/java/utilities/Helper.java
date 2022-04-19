/**
 * Engineer Mohamed Moustafa 2020.
 * All Rights Reserved.
 *
 * ver          Creator          Date        Comments
 * ----- ---------------------  ----------  ----------------------------------------
 * 1.00     Mohamed Moustafa    01/11/2020  - Script created.
 */
package utilities;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Helper {
	public static WebDriver driver;

	public static void navigateToURL(String targetUrl) {
		driver.navigate().to(targetUrl);
		Reporter.Log("Navigated to browser : " + targetUrl);
	}

	/** Method to take screenshot in case the test case fail
	 * 
	 * @param driver used WebDriver
	 * @param screenshotname takes the screenshot wanted name
	 */
	public static void captureScreenShot(WebDriver driver, String screenshotname) {
		Path dest = Paths.get("./Screenshots", screenshotname + ".png");
		try {
			Files.createDirectories(dest.getParent());
			FileOutputStream out = new FileOutputStream(dest.toString());
			out.write(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
			out.close();

		} catch (IOException e) {
			System.out.println("Exception while taking screenshot" + e.getMessage());
		}
	}


	/**
	 * Method to add attachments to allure report 
	 * @param screenShotName insert the screenshotName
	 * @param screenShotPath insert the screenshot path
	 * @throws IOException
	 */
	public static void addAttachmenetsToAllure(String screenShotName, String screenShotPath) throws IOException 
	{
		Path content = Paths.get(System.getProperty("user.dir")+"\\"+screenShotPath);
		try (InputStream is = Files.newInputStream(content)) {
			Allure.addAttachment(screenShotName, is);
		}
	}

	/**
	 * Add the created video as attachments into allure report
	 * @param attachmentType insert attachment Type 
	 * @param attachmentName insert attachment name
	 * @param attachmentContent insert the attachment content  
	 * @throws IOException
	 */
	public static void addAttachmenetsVideoToAllure(String attachmentType, String attachmentName, InputStream attachmentContent) throws IOException 
	{
		createAttachment(attachmentType, attachmentName, attachmentContent);
	}


	/**
	 * Create attachment to be inserted 
	 * @param attachmentType
	 * @param attachmentName
	 * @param attachmentContent
	 */
	private static void createAttachment(String attachmentType, String attachmentName, InputStream attachmentContent) {
		//InputStream attachmentContentCopy = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len;
		try {
			while ((len = attachmentContent.read(buffer)) > -1) {
				baos.write(buffer, 0, len);
			}
			baos.flush();
		} catch (IOException e) {
			//slf4jLogger.info("Error while creating Attachment", e);
		}

		attachmentContent = new ByteArrayInputStream(baos.toByteArray());
		//attachmentContentCopy = new ByteArrayInputStream(baos.toByteArray());

		String attachmentDescription = "Attachment: " + attachmentType + " - " + attachmentName;

		if (attachmentType.toLowerCase().contains("screenshot")) {
			Allure.addAttachment(attachmentDescription, "image/png", attachmentContent, ".png");
		} else if (attachmentType.toLowerCase().contains("recording")) {
			Allure.addAttachment(attachmentDescription, "video/quicktime", attachmentContent, ".mov");
			// attachmentName, "video/mp4", attachmentContent, ".mp4"
		} else if (attachmentType.toLowerCase().contains("gif")) {
			Allure.addAttachment(attachmentDescription, "image/gif", attachmentContent, ".gif");
		} else if (attachmentType.toLowerCase().contains("engine logs")) {
			if (attachmentName.equals("Current Method log")) {
				//Allure.addAttachment(attachmentDescription, "text/plain", new StringInputStream(currentTestLog.trim()),	".txt");
			} else {
				Allure.addAttachment(attachmentDescription, "text/plain", attachmentContent, ".txt");
			}
		} else if (attachmentType.toLowerCase().contains("csv") || attachmentName.toLowerCase().contains("csv")) {
			Allure.addAttachment(attachmentDescription, "text/csv", attachmentContent, ".csv");
		} else if (attachmentType.toLowerCase().contains("xml") || attachmentName.toLowerCase().contains("xml")) {
			Allure.addAttachment(attachmentDescription, "text/xml", attachmentContent, ".xml");
		} else if (attachmentType.toLowerCase().contains("json") || attachmentName.toLowerCase().contains("json")) {
			Allure.addAttachment(attachmentDescription, "text/json", attachmentContent, ".json");
		} else {
			Allure.addAttachment(attachmentDescription, attachmentContent);
		}

	}

	/**
	 * this method is used to convert string number to int
	 * @param number number string
	 * @return number as int
	 */
	public static int convertStringToInteger(String number) {
		return Integer.parseInt(number);
	}

	/**
	 * this method to format the number
	 * @param number number to be formatted
	 * @param pattern needed pattern for formatter
	 * @return formatted number as string
	 */
	public static String formatNumber(int number, String pattern) {
		DecimalFormat formatter = new DecimalFormat(pattern);
		return formatter.format(number);
	}


	/**
	 * Compares two objects (that can be cast to a string value) based on the
	 * selected comparisonType and ValidationType, then returns the result in an
	 * integer value
	 * 
	 * @param expectedValue  the expected value (test data) of this assertion
	 * @param actualValue    the actual value (calculated data) of this assertion
	 * @param comparisonType 1 is literalComparison, 2 is regexComparison, 3 is
	 *                       containsComparison, 4 is caseInsensitiveComparison
	 * @param validationType either 'true' for a positive assertion that the objects
	 *                       are equal, or 'false' for a negative assertion that the
	 *                       objects are not equal
	 * @return integer value; 1 in case of match, 0 in case of no match, -1 in case
	 *         of invalid comparison operator, -2 in case of another unhandled
	 *         exception
	 */
	private static int compareTwoObjects(Object expectedValue, Object actualValue, int comparisonType,
			Boolean validationType) {
		if (validationType) {
			try {
				switch (comparisonType) {
				case 1:
					// case sensitive literal equivalence
					Assert.assertTrue(actualValue.equals(expectedValue));
					break;
				case 2:
					// regex comparison
					Assert.assertTrue((String.valueOf(actualValue)).matches(String.valueOf(expectedValue)));
					break;
				case 3:
					// contains
					Assert.assertTrue((String.valueOf(actualValue)).contains(String.valueOf(expectedValue)));
					break;
				case 4:
					// case insensitive equivalence
					Assert.assertTrue((String.valueOf(actualValue)).equalsIgnoreCase(String.valueOf(expectedValue)));
					break;
				default:
					// unhandled case
					return -1;
				}
				return 1;
			} catch (AssertionError e) {
				return 0;
			} catch (Exception e) {
				Reporter.Log(e.toString());

				return -2;
			}
		} else {
			try {
				switch (comparisonType) {
				case 1:
					// case sensitive literal equivalence
					Assert.assertFalse(actualValue.equals(expectedValue));
					break;
				case 2:
					// regex comparison
					Assert.assertFalse((String.valueOf(actualValue)).matches(String.valueOf(expectedValue)));
					break;
				case 3:
					// contains
					Assert.assertFalse((String.valueOf(actualValue)).contains(String.valueOf(expectedValue)));
					break;
				case 4:
					// case insensitive equivalence
					Assert.assertFalse((String.valueOf(actualValue)).equalsIgnoreCase(String.valueOf(expectedValue)));
					break;
				default:
					// unhandled case
					return -1;
				}
				return 1;
			} catch (AssertionError e) {
				return 0;
			} catch (Exception e) {
				Reporter.Log(e.toString());
				return -2;
			}
		}

	}


	private static void pass(String message) {
		Reporter.Log(message);
	}

	private static void fail(String message) {
		Reporter.Log(message);
		Assert.fail(message);
	}

	/**
	 * Asserts that two objects are equal if AssertionType is true, or not equal if
	 * AssertionType is false.
	 * 
	 * @param expectedValue  the expected value (test data) of this assertion
	 * @param actualValue    the actual value (calculated data) of this assertion
	 * @param comparisonType 1 is literalComparison, 2 is regexComparison, 3 is
	 *                       containsComparison, 4 is caseInsensitiveComparison
	 * @param assertionType  either 'true' for a positive assertion that the objects
	 *                       are equal, or 'false' for a negative assertion that the
	 *                       objects are not equal
	 */
	public static void assertEquals(Object expectedValue, Object actualValue, int comparisonType,
			Boolean assertionType) {
		Reporter.Log("Assertion [" + "assertEquals" + "] is being performed, with expectedValue ["
				+ expectedValue + "], actualValue [" + actualValue + "], comparisonType [" + comparisonType
				+ "], and assertionType [" + assertionType + "].");

		switch (compareTwoObjects(expectedValue, actualValue, comparisonType, assertionType)) {
		case 1:
			if (assertionType) {
				pass("Assertion Passed; actual value [" + actualValue + "] does match expected value [" + expectedValue
						+ "].");

			} else {
				pass("Assertion Passed; actual value [" + actualValue + "] does not match expected value ["
						+ expectedValue + "].");
			}
			break;
		case 0:
			if (assertionType) {
				fail("Assertion Failed; actual value [" + actualValue + "] does not match expected value ["
						+ expectedValue + "].");
			} else {
				fail("Assertion Failed; actual value [" + actualValue + "] does match expected value [" + expectedValue
						+ "].");
			}
			break;
		case -1:
			fail("Assertion Failed; invalid comparison operator used.");
			break;
		default:
			fail("Assertion Failed; an unhandled exception occured.");
			break;
		}
	}

	/**
	 * Method to get system date
	 * @return
	 */
	public static String getSystemDate(String format) {
			DateFormat dateFormat = new SimpleDateFormat(format);
			Date date = new Date();
			String sysDate = dateFormat.format(date);
			Reporter.Log("System date found as ["+sysDate+"]");
			return sysDate;
		}
}

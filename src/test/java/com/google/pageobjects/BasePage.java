package com.google.pageobjects;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cucumber.listener.Reporter;
import com.google.common.io.Files;
import com.google.runner.TestRunner;
import com.google.utils.Constants;

import org.testng.Assert;
import org.openqa.selenium.interactions.Actions;

public class BasePage extends TestRunner {

	protected WebDriverWait wait = new WebDriverWait(getDriver(), Constants.WAIT_TIME);
	private Actions builder = new Actions(getDriver());
	protected Logger log = Logger.getLogger("Action Class");

	public void resizeBrowserWindow(int width, int height) {
		Dimension dimension = new Dimension(width, height);
		getDriver().manage().window().setSize(dimension);
	}

	public void maximizeWindow() {
		getDriver().manage().window().maximize();
	}

	public void switchToNextTab() {
		try {
			ArrayList<String> browserTabs = new ArrayList<String>(getDriver().getWindowHandles());
			getDriver().switchTo().window(browserTabs.get(1));
			Reporter.addStepLog("Current url = " + getURL());
		} catch (Exception e) {
			log.warning("switchToNextTab: Exception while switching to next tab");
			throw e;
		}
	}

	public void closeNewTab() {
		ArrayList<String> browserTabs = new ArrayList<String>(getDriver().getWindowHandles());
		getDriver().close();
		getDriver().switchTo().window(browserTabs.get(0));
		Reporter.addStepLog("Current url = " + getURL());
	}

	public void closeWindow() {
		getDriver().close();
	}

	public String getTitle() {
		return getDriver().getTitle();
	}

	public String getURL() {
		return getDriver().getCurrentUrl();
	}

	public void checkIfCheckboxSelected(String checked) {
		try {
			Assert.assertTrue(true, checked);
		} catch (AssertionError e) {
			log.warning("isCheckboxSelected: checkbox not selected " + e);
			throw e;
		}

	}

	public void checkPageTitleForEqual(String expectedTitle) {
		Reporter.addStepLog("Current page title = " + getTitle() + " >>> Passed in title: " + expectedTitle);
		Reporter.addStepLog("Current url = " + getURL());
		try {
			Assert.assertTrue(getTitle().equals(expectedTitle));
		} catch (AssertionError e) {
			log.warning("checkPageTitle: AssertionError: page title " + getTitle() + " does not match " + expectedTitle
					+ " " + e);
			throw e;
		}
	}

	public void checkPageTitleForContains(String expectedTitle) {
		Reporter.addStepLog("Current page title = " + getTitle() + " >>> Passed in title: " + expectedTitle);
		Reporter.addStepLog("Current url = " + getURL());
		try {
			isSubStrContainedInSuperStr(getTitle(), expectedTitle);
		} catch (AssertionError e) {
			log.warning("checkPageTitle: AssertionError: page title " + getTitle() + " does not contain "
					+ expectedTitle + " " + e);
			throw e;
		}
	}

	public void checkURL(String url) {
		Reporter.addStepLog("Current url = " + getURL() + " >>> Passed in url: " + url);
		try {
			Assert.assertTrue(getURL().contains(url));
		} catch (AssertionError e) {
			log.warning(
					"checkURL: AssertionError: current url " + getURL() + " does not contain the url " + url + " " + e);
			throw e;
		}
	}

	public void isSubStrContainedInSuperStr(String superString, String subString) {
		Reporter.addStepLog("Current url = " + getURL());
		Reporter.addStepLog("Checking if " + superString + " contains " + subString);
		try {
			Assert.assertTrue(superString.contains(subString));
		} catch (AssertionError e) {
			log.warning("isSubStrContainedInSuperStr: " + superString + " does not contain " + subString);
			throw e;
		}

	}

	public void isSubStrNotContainedInSuperStr(String superString, String subString) {
		Reporter.addStepLog("Current url = " + getURL());
		try {
			Assert.assertFalse(superString.contains(subString));
		} catch (AssertionError e) {
			log.warning("isSubStrContainedInSuperStr: : " + superString + " contains " + subString);
			throw e;
		}

	}

	public void areStringValuesIdentical(String string1, String string2) {
		Reporter.addStepLog("Current url = " + getURL());
		try {
			Assert.assertTrue(string1.equalsIgnoreCase(string2));
		} catch (AssertionError e) {
			log.warning("areStringValuesIdentical: " + string1 + " does not equal " + string2 + " " + e);
			throw e;
		}
	}

	public String getElementText(WebElement element) {

		String text = "";
		try {
			wait.until(ExpectedConditions.elementToBeClickable(element));
			text = element.getText();
		} catch (Exception e) {
			log.warning("getElementText: Exception while getting text " + element);
			throw e;
		}
		return text;
	}

	public void highlightElement(WebElement element) {
		try {
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			String attributevalue = "border:3px solid red;";
			String getattrib = element.getAttribute("style");
			executor.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, attributevalue);
			executor.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, getattrib);
		} catch (Exception e) {
			log.warning("highlightElement: Exception: " + element);
		}
	}

	public void hoverOverElement(WebElement element) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(element));
			builder.moveToElement(element).perform();
			Thread.sleep(500);
		} catch (Exception e) {
			log.warning("hoverOverElement: Exception: " + element);
		}
	}

	public void hoverOverElementById(String id) {
		WebElement element = getDriver().findElement(By.id(id));
		try {
			wait.until(ExpectedConditions.elementToBeClickable(element));
			WebElement elementToHover = getDriver().findElement(By.id(id));
			builder.moveToElement(elementToHover).build().perform();
			Thread.sleep(1000);
		} catch (Exception e) {
			log.warning("hoverOverElement: Exception while hovering over the element by id " + element);
		}
	}

	public void click(WebElement element) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(element));
			element.click();
			Reporter.addStepLog("Current url = " + getURL());
		} catch (Exception e) {
			log.warning("click: error while clicking " + element);
		}
	}

	public void JSClick(WebElement element) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(element));
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].click();", element);
			Reporter.addStepLog("Current url = " + getURL());
		} catch (Exception e) {
			log.warning("JSClick: Exception while clicking the " + element);
		}
	}
	
	public void clearAndType(WebElement element, String text) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(element));
			element.clear();
			element.sendKeys(text);
			Reporter.addStepLog("Current url = " + getURL());
		} catch (Exception e) {
			log.warning("clearAndType: Exception while typing into: " + element);
		}

	}
	public void javascriptType(WebElement element, String text) {
		try {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].value='" + text + "';", element);
			Reporter.addStepLog("Current url = " + getURL());
		} catch (Exception e) {
			log.warning("javascriptType: Exception while entering value into: " + element);
		}
	}

	public void clearAndType(String elementName, String text) {
		try {
			WebElement element = getDriver().findElement(By.name(elementName));
			wait.until(ExpectedConditions.elementToBeClickable(element));
			element.clear();
			element.sendKeys(text);
			element.sendKeys(Keys.TAB);
			Reporter.addStepLog("Current url = " + getURL());
		} catch (Exception e) {
			log.warning("clearAndType: Exception while typing into element");
		}
	}

	public void selectElement(WebElement element, String text) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(element));
			click(element);
			Select dropDown = new Select(element);
			dropDown.selectByVisibleText(text);
			Reporter.addStepLog("Current url = " + getURL());
		} catch (Exception e) {
			log.warning("selectElement: Exception while selecting a value for the: " + element.getText());
		}
	}

	public void selectElement(WebElement element, int index) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(element));
			Select dropDown = new Select(element);
			dropDown.selectByIndex(index);
			Reporter.addStepLog("Current url = " + getURL());
		} catch (Exception e) {
			log.warning("selectElement: Exception while selecting a value for the  " + element);
		}
	}

	public boolean verifyElementPresent(WebElement element) {
		boolean status = false;
		try {
			if (element.isDisplayed()) {
				status = true;
			}
		} catch (Exception e) {
			status = false;
		}
		return status;
	}

	public void scrollIntoElementView(WebElement element) {
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
		} catch (Exception e) {
			log.warning("scrollIntoElementView: Exception while scrolling to " + element.getText());
		}
	}

	public void selectFirstOption(WebElement option) {
		try {
			option.sendKeys(Keys.ARROW_DOWN);
			option.sendKeys(Keys.ENTER);
		} catch (Exception e) {
			log.warning("selectFirstOption: Exception reading text of " + e);
		}
	}

	public String getBrowserName() {
		return browser;
	}

	public static String folder() {
		String screenshot = "Reports/";
		File file = new File(screenshot);
		if (!file.exists()) {
			if (file.mkdir()) {
				System.out.println("Report directory is created!");
			} else {
				System.out.println("Failed to create report directory!");
			}
		}
		return screenshot;
	}

	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		return dir.delete();
	}

	public static void takeScreenshot(String screenshotName) {

		try {
			String folderName = folder();
			String filename = folderName + screenshotName + "_"
					+ new SimpleDateFormat("yyyyMMddhhmmss'.png'").format(new Date());
			File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

			File destinationPath = new File(filename);
			Files.copy(file, destinationPath);
			String modifiedDestinationPath = destinationPath.toString().replace("Reports\\", "");
			Reporter.addScreenCaptureFromPath(modifiedDestinationPath.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static WebElement takeScreenshot(WebElement screenshotName) {

		try {
			String file1 = folder() + screenshotName + ".png";
			File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

			File destinationPath = new File(file1);
			Files.copy(file, destinationPath);
			Reporter.addScreenCaptureFromPath(destinationPath.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return screenshotName;
	}

	public void waiting(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
package com.google.pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.cucumber.dataprovider.ConfigManager;
import com.cucumber.listener.Reporter;

public class HomePage extends BasePage {

	ConfigManager appData = new ConfigManager("Sys");

	@FindBy(css = "#tsf > div:nth-child(2) > div.A8SBwf > div.RNNXgb > div > div.a4bIc > input")
	private WebElement searchInput;
	
	@FindBy(xpath = "//input[@value='Google Search']")
	private WebElement searchBtn;
	
	@FindBy(css = "#rso > div:nth-child(1) > div > div > div > div > div.r > a > h3")
	private WebElement searchResult;

	public HomePage() {
		PageFactory.initElements(getDriver(), this);
	}

	public void openAtHomepage() {

		String jenkinsURL = "";
		if (System.getProperty("url") != null) {
			jenkinsURL = System.getProperty("url");
		} else if (jenkinsURL.equals("")) {
			jenkinsURL = appData.getProperty("TestUrl");
		}

		getDriver().get(jenkinsURL);
		getDriver().manage().window().maximize();

		Reporter.addStepLog("Current browser = " + browser);
		Reporter.addStepLog("Current url is: " + getURL());
	}

	public void enterSearchField(String search) {
		clearAndType(searchInput, search);
	}
	
	public void clickSearchBtn() {
		click(searchBtn);
	}
	
	public void resultContains(String search) {
		try {
			Assert.assertTrue(getElementText(searchResult).contains(search));
		} catch (Exception e) {
			log.warning("resultContains: google search did not work " + e);
			throw e;
		}
	}

}
package com.cucumber.configuration;

import java.net.URL;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

/**
 * @author vikas
 *
 */
public class BrowserFactory {

	private ConfigManager appData = new ConfigManager();
	private WebDriver driver;

	/**
	 * 
	 * This method initiates Chrome browser with default profile and returns the
	 * driver object
	 *
	 * @return , returns the driver object after initiating Chrome browser
	 */
	public WebDriver initChromeDriver() {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\drivers\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		return new ChromeDriver(options);
	}

	/**
	 * 
	 * This method initiates Firefox browser with default profile and returns the
	 * driver object
	 *
	 * @return , returns the driver object after initiating Firefox browser
	 */
	public WebDriver initFirefoxDriver() {
		System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "\\drivers\\geckodriver.exe");
		return new FirefoxDriver();
	}

	/**
	 * 
	 * This method is used initiate IE browser
	 * 
	 * @return , returns IE browser driver object
	 */
	public WebDriver initIEBrowser() {
		System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "\\drivers\\IEDriverServer.exe");
		InternetExplorerOptions options = new InternetExplorerOptions();
		options.setCapability("IntroduceInstabilityByIgnoringProtectedModeSettings", true);
		options.setCapability("EnablePersistentHover", false);
		options.setCapability("EnableNativeEvents", false);
		options.setCapability("EnsureCleanSession", true);
		options.setCapability("IgnoreZoomLevel", true);
		options.setCapability("IgnoreZoomSetting", true);
		options.setCapability("UnexpectedAlertBehavior", "accept");
		return new InternetExplorerDriver(options);

	}

	/**
	 * 
	 * This method initiates remote Chrome browser with default profile and returns the
	 * driver object
	 *
	 * @return , returns the driver object after initiating Chrome browser
	 */
	public WebDriver initRemoteChrome(String browserType, String browserVersion, String OSName, String OSVersion,
			String session) {

		ChromeOptions options = new ChromeOptions();
		try {
			String cloud = appData.getProperty("Cloud.Host.URL");

			options.addArguments("start-maximized");
			options.setCapability("username", appData.getProperty("sauceUsername"));
			options.setCapability("accessKey", appData.getProperty("sauceAccessKey"));
			options.setCapability("platform", OSName);
			options.setCapability("name", session);
			driver = new RemoteWebDriver(new URL(cloud), options);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		return driver;
	}

	/**
	 * 
	 * This method initiates Firefox browser with default profile and returns the
	 * driver object
	 *
	 * @return , returns the driver object after initiating Firefox browser
	 */
	public WebDriver initRemoteFirefox(String browserType, String browserVersion, String OSName, String OSVersion,
			String session) {

		FirefoxOptions options = new FirefoxOptions();
		try {
			String cloud = appData.getProperty("Cloud.Host.URL");

			options.addArguments("start-maximized");
			options.setCapability("username", "vikasmathur");
			options.setCapability("accessKey", "76ffb152-d07e-42f0-a41a-0f9bb8c99078");
			options.setCapability("platform", OSName);
			options.setCapability("name", session);
			driver = new RemoteWebDriver(new URL(cloud), options);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		return driver;
	}
}
package com.cucumber.base;

import java.net.URL;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import com.cucumber.dataprovider.ConfigManager;

public class BrowserFactory {

	ConfigManager sys = new ConfigManager();
	WebDriver driver;
	FirefoxProfile firefoxProfile;
	String fileSeperator = System.getProperty("file.separator");
	DesiredCapabilities capabilities;

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

	public WebDriver initRemoteChromeDriver() {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\drivers\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		return new ChromeDriver(options);
	}

	public WebDriver initFirefoxDriver() {
		System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "\\drivers\\geckodriver.exe");
		return new FirefoxDriver();
	}

	/**
	 * 
	 * This method sets different profile preferences to firefox browser
	 *
	 */
	public void setProfilePreferences() {
		firefoxProfile.setAcceptUntrustedCertificates(true);
		firefoxProfile.setPreference("app.update.enabled", false);
		firefoxProfile.setPreference("browser.download.folderList", 2);
		firefoxProfile.setPreference("browser.helperApps.neverAsk.openFile",
				"application/pdf, application/x-pdf, application/acrobat, applications/vnd.pdf, text/pdf, text/x-pdf, application/octet-stream, application/vnd.openxmlformats-officedocument.wordprocessingml.document, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/x-rar-compressed, application/zip");
		firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk",
				"application/pdf, application/x-pdf, application/acrobat, applications/vnd.pdf, text/pdf, text/x-pdf, application/octet-stream, application/vnd.openxmlformats-officedocument.wordprocessingml.document, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/x-rar-compressed, application/zip");
		firefoxProfile.setPreference("browser.helperApps.alwaysAsk.force", false);
		firefoxProfile.setPreference("browser.download.manager.showAlertOnComplete", false);
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

	public WebDriver init(String browserType, String browserVersion, String OSName, String OSVersion, String session) {
		try {
			sys = new ConfigManager();
			String cloud = sys.getProperty("Cloud.Host.URL");
			capabilities = DesiredCapabilities.chrome();
	        capabilities.setCapability("username", "vikasmathur");
	        capabilities.setCapability("accessKey", "76ffb152-d07e-42f0-a41a-0f9bb8c99078");
			capabilities.setCapability("platform", OSName);
			capabilities.setCapability("name", session);
			driver = new RemoteWebDriver(new URL(cloud), capabilities);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		return driver;
	}

}

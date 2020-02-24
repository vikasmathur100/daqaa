package com.cucumber.configuration;

import org.openqa.selenium.WebDriver;

/**
 * @author vikas
 *
 */
public class BaseConfiguration {

	BrowserFactory browserFactory = new BrowserFactory();

	/**
	 * 
	 * This method sets the desired capabilities based on browser type
	 *
	 * @param browserType
	 *            , Need to pass the browser type
	 */
	public WebDriver setBrowser(String browserType)	{
		WebDriver driver = null;
		switch (browserType) {
		case "chrome":
			driver = browserFactory.initChromeDriver();
			break;
	
		case "firefox":
			driver = browserFactory.initFirefoxDriver();
			break;

		case "iexplorer":
			driver = browserFactory.initIEBrowser();
			break;

		default:
			driver = browserFactory.initChromeDriver();
		}
		return driver;
	}

	/**
	 * 
	 * This method sets the remote desired capabilities based on browser type
	 *
	 * @param browserType
	 *            , Need to pass the browser type
	 */
	public WebDriver setRemoteBrowser(String browserType, String browserVersion, String OSName, String OSVersion, String session) {
		WebDriver driver = null;
		switch (browserType) {
		case "chrome":
			driver = browserFactory.initRemoteChrome(browserType, browserVersion, OSName, OSVersion, session);
			break;
			
		case "firefox":
			driver = browserFactory.initRemoteFirefox(browserType, browserVersion, OSName, OSVersion, session);
			break;
			
		default:
			driver = browserFactory.initChromeDriver();
		}
		return driver;
	}
}
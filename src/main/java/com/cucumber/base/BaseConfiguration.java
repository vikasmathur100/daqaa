package com.cucumber.base;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import com.cucumber.dataprovider.ConfigManager;

public class BaseConfiguration {

	DesiredCapabilities capabilities = new DesiredCapabilities();
	ConfigManager sys;
	BrowserFactory browserFactory = new BrowserFactory();

	/**
	 * Gets the desiredCapability of respective CloudType(saucelabs)
	 * 
	 * @param CloudType
	 * @return DesiredCapability
	 * @throws CloudNotFoundException
	 * @throws Exception
	 *             browserType, browserVersion, OSName, OSVersion, session
	 */
	public DesiredCapabilities addCapability(String browserType, String browserVersion, String OSName, String OSVersion,
			String session) {
		sys = new ConfigManager();
		String cloudURL = sys.getProperty("Cloud.Host.URL").toLowerCase();

		if (cloudURL.contains("saucelabs")) {
			setCloudCapabulities(browserVersion, session, browserType, OSName, OSVersion);
		} else if (cloudURL.contains("browserstack")) {
			setCloudCapabulities(browserVersion, session, browserType, OSName, OSVersion);
		} else if (cloudURL.contains("Testingbot")) {
			setCloudCapabulities(browserVersion, session, browserType, OSName, OSVersion);
		}
		
		return capabilities;
	}


	/**
	 * 
	 * This method sets the desired capabilities based on browser type
	 *
	 * @param browserType
	 *            , Need to pass the browser type
	 */
	public WebDriver setBrowser(String browserType)

	{
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

	public WebDriver RemoteSetBrowser(String browserType) {
		WebDriver driver = null;
		switch (browserType) {
		case "chrome":
			capabilities.setCapability("browserName", browserType);
			break;
		case "firefox":
			capabilities.setCapability("browserName", browserType);
			break;
		case "iexplore":
			capabilities.setCapability("browserName", browserType);
			break;
		case "safari":
			capabilities = DesiredCapabilities.safari();
			break;
		default:
			capabilities = DesiredCapabilities.firefox();
		}
		return driver;
	}

	/**
	 * Extracted method that does common setup of setting OS/Platform where cloud
	 * browser should be launched
	 * 
	 * @return null
	 */
	@SuppressWarnings("deprecation")
	private void setOperatingSystem(String OSName, String OSVersion) {
		switch (OSName.toLowerCase()) {
		case "windows":
			switch (OSVersion.toLowerCase()) {
			case "xp":
				capabilities.setCapability("platform", Platform.XP);
				break;

			case "vista":
				capabilities.setCapability("platform", Platform.VISTA);
				break;

			case "7":
				capabilities.setCapability("platform", Platform.WINDOWS);
				break;

			case "8":
				capabilities.setCapability("platform", Platform.WIN8);
				break;

			case "8_1":
				capabilities.setCapability("platform", Platform.WIN8_1);
				break;

			case "10":
				capabilities.setCapability("platform", Platform.WIN10);
				break;
			}

			break;
		case "mac":
			capabilities.setCapability(CapabilityType.PLATFORM, "Mac");
			break;

		case "linux":
			capabilities.setCapability("platform", Platform.LINUX);
			break;

		case "unix":
			capabilities.setCapability("platform", Platform.UNIX);
			break;

		case "any":
			capabilities.setCapability("platform", Platform.ANY);
			break;

		default:
			capabilities.setCapability("platform", Platform.ANY);
			break;
		}
	}

	private void setCloudCapabulities(String browserVersion, String session, String browserType, String OSName,
			String OSVersion) {
		RemoteSetBrowser(browserType);
		setOperatingSystem(OSName, OSVersion);
		capabilities.setCapability("version", browserVersion);
		capabilities.setCapability("name", session);
	}

}

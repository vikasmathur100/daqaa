package com.google.runner;

import java.io.File;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.cucumber.base.BrowserFactory;
import com.cucumber.base.BaseConfiguration;
import com.cucumber.dataprovider.ConfigManager;
import com.cucumber.listener.ExtentCucumberFormatter;
import com.cucumber.listener.Reporter;
import com.google.pageobjects.BasePage;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;

@CucumberOptions(monochrome = true, features = "src/test/resources/features", glue = {
		"com.google.stepdefinitions" }, tags = { "@SmokeTest" }, plugin = {
				"com.cucumber.listener.ExtentCucumberFormatter:" })

public class TestRunner extends AbstractTestNGCucumberTests {
	private TestNGCucumberRunner testNGCucumberRunner;
	protected static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	protected ConfigManager appData = new ConfigManager("Sys");
	protected BaseConfiguration baseConfiguration = new BaseConfiguration();
	protected BrowserFactory browserFactory = new BrowserFactory();
	protected String browser = "";
	protected static String browserForExtent = "";
	protected ExtentCucumberFormatter report;

	/**
	 * Setter method for WebDriver
	 * 
	 * @param driver
	 */
	public void setDriver(WebDriver webDriver) {
		driver.set(webDriver);
	}


	/**
	 * Getter method for WebDriver
	 * 
	 * @param driver
	 */
	public WebDriver getDriver() {
		return driver.get();
	}
	@Parameters({ "platformName", "platformVersion", "deviceName", "appPath", "URL", "browserType", "browserVersion", "OSName",
			"OSVersion", "session" })
	@BeforeTest(alwaysRun = true)
	public void setUpClass(@Optional String platformName, @Optional String platformVersion, @Optional String deviceName,
			@Optional String appPath, @Optional String URL, @Optional String browserType,@Optional String browserVersion, @Optional String OSName,
			@Optional String OSVersion, @Optional String session) throws Exception {
		browser = browserType;
		browserForExtent = browserForExtent + " " + browserType;
		testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());


		if (appData.getProperty("TypeOfExecution").equalsIgnoreCase("Linear")) {
			setDriver(new BrowserFactory().init(browserType, browserVersion, OSName, OSVersion, session));
		} else if (appData.getProperty("TypeOfExecution").equalsIgnoreCase("Local")) {
			setDriver(baseConfiguration.setBrowser(browserType));
		}

	}

	@Test(groups = "cucumber", description = "Runs Cucumber Feature", dataProvider = "features")
	public void feature(CucumberFeatureWrapper cucumberFeature) {
		testNGCucumberRunner.runCucumber(cucumberFeature.getCucumberFeature());
	}

	@DataProvider
	public Object[][] features() {
		return testNGCucumberRunner.provideFeatures();
	}

	@AfterTest(alwaysRun = true)
	public void tearDownClass(ITestContext context) throws Exception {
		Reporter.loadXMLConfig(new File("src/test/resources/extent-config.xml"));
		Reporter.setSystemInfo("User Name", System.getProperty("user.name"));
		Reporter.setSystemInfo("Time Zone", System.getProperty("user.timezone"));
		Reporter.setSystemInfo("Machine", "Windows 10 " + "64 Bit");
		Reporter.setSystemInfo("Selenium", "3.0");
		Reporter.setSystemInfo("Maven", "3.5.2");
		Reporter.setSystemInfo("Test URL", appData.getProperty("TestUrl"));
		Reporter.setSystemInfo("Browser Type", browserForExtent);
		Reporter.setTestRunnerOutput("Sample test runner output message");
		testNGCucumberRunner.finish();
		getDriver().quit();

	}

	@BeforeSuite
	public void beforeSuite() {
		String screenshot = "Reports/";
		File file = new File(screenshot);
		if (file.exists()) {
			BasePage.deleteDir(file);
		}
		report = new ExtentCucumberFormatter(
					new File("Reports/" + "Extent_report.html"));

	}

	@AfterSuite
	public void afterSuite() {
		
	}
}

package com.cucumber.configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.testng.Assert;

/**
 * Retrieve properties file based to retrieve properties based on key values
 * 
 * @author vikas
 */
public class ConfigManager {

	private Properties properties = new Properties();
	private String configFileName;
	private String fileSeperator = System.getProperty("file.separator");

	/**
	 * Default constructor
	 * 
	 */
	public ConfigManager() {
		configFileName = "Sys";
	}

	/**
	 * Returns the value of given property from either sys.properties or
	 * app.properties file
	 * 
	 * @param key
	 *            - ConfigParamvalue that requires to be returned from
	 *            Config.properties file
	 * @return - return ConfigValue
	 */
	public String getProperty(String key) {
		String value = "";
		if (key != "") {
			loadProperties();
			try {
				if (!properties.getProperty(key).trim().isEmpty())
					value = properties.getProperty(key).trim();
			} catch (NullPointerException e) {
				Assert.fail("Key - '" + key + "' does not exist or not given a value in " + configFileName
						+ ".properties file \n");
			}
		} else {
			Assert.fail("key cannot be null.. ");
		}
		return value;
	}

	/**
	 * 
	 * This method is sued to load properties file that has to be accessed
	 *
	 */
	private void loadProperties() {
		FileInputStream fis;
		try {
			fis = new FileInputStream(getConfigFilePath(configFileName));
			properties.load(fis);
			fis.close();
		} catch (FileNotFoundException e) {
			Assert.fail("Cannot find configuration file - " + configFileName + ".properties" + " at "
					+ getConfigFilePath(configFileName));
		} catch (IOException e) {
			Assert.fail("Cannot read configuration file - " + " at " + getConfigFilePath(configFileName));
		}
	}

	/**
	 * 
	 * This method is used to get the path of specified properties file name
	 *
	 * @param File
	 *            Need to pass the name of properties file
	 * @return 
	 *            returns the file path of the specified properties file
	 */
	public String getConfigFilePath(String File) {
		String configFilePath;
		configFilePath = getConfigFolderPath() + fileSeperator + File.toLowerCase() + ".properties";
		return configFilePath;
	}

	/**
	 * 
	 * This method is used to get the location of 'ConfigFiles' folder
	 *
	 * @return, Returns the path of 'ConfigFiles' folder
	 */
	public String getConfigFolderPath() {
		return System.getProperty("user.dir") + fileSeperator + "ConfigFiles";
	}
}

package com.google.stepdefinitions;

import com.cucumber.configuration.ConfigManager;
import com.google.pageobjects.BasePage;

import cucumber.api.java.After;
import cucumber.api.java.Before;

public class ServiceHooks extends BasePage {

	ConfigManager appData = new ConfigManager();
	
	@Before()
	public void initializeTest() {
	
		
	}

	@After
	public void closeTest() throws Exception {
	
		

	}

}

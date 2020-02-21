package com.google.stepdefinitions;

import com.google.pageobjects.HomePage;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class HomeStep {

	private HomePage homepage = new HomePage();

	@Given("^the user selects to search using Google$")
	public void givenStartOnHomepage() {
		homepage.openAtHomepage();
	}
	
	@When("^the user searches for \"([^\\\"]*)\"$")
	public void whenSearchFor(String search) {
		homepage.enterSearchField(search);
		homepage.clickSearchBtn();
	}
	
	@Then("^search results are displayed using \"([^\\\"]*)\"$")
	public void thenSearchResultsDisp(String search) {
		homepage.resultContains(search);
	}

}

package com.google.stepdefinitions;

import com.google.pageobjects.HomePage;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * This class has step definitions for all the homepage related steps
 * 
 * @author vikas
 *
 */
public class HomeStep {

	private HomePage homepage = new HomePage();

	@Given("^the student selects to search using Google$")
	public void givenStartOnHomepage() {
		homepage.openAtHomepage();
	}
	
	@When("^the student searches for capital of \"([^\\\"]*)\"$")
	public void whenSearchFor(String search) {
		homepage.enterSearchField("Capital of " + search);
		homepage.clickSearchBtn();
	}
	
	@Then("^search results are displayed using \"([^\\\"]*)\"$")
	public void thenSearchResultsDisp(String search) {
		homepage.resultContains(search);
	}
}
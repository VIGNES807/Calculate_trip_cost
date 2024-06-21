package stepDefinitions;

import io.cucumber.java.AfterStep;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pommodels.HomePage;
import pommodels.ResultsPage;
import screenshotmanager.ScreenShot;

import java.io.IOException;
import java.time.Duration;

import org.junit.Assert;
import org.openqa.selenium.*;
import factory.BaseForSteps;

public class HolidayHomesSteps {
	HomePage hp;
	ResultsPage rp;

	
//	executes after every step
	@AfterStep
	public void screenShot(Scenario scenario) throws IOException {
		byte[] screenshot = ((TakesScreenshot)BaseForSteps.getDriver()).getScreenshotAs(OutputType.BYTES);
		scenario.attach(screenshot, "image/png", scenario.getName());
		ScreenShot.fullScreenshot(BaseForSteps.getDriver());
		
	}
	
	
//	given requirements
	@Given("the booking website homepage")
	public void the_website_homepage() {
		BaseForSteps.getDriver().get(BaseForSteps.p.getProperty("url1"));
		BaseForSteps.getLogger().info("Website opened");
		BaseForSteps.getDriver().manage().window().maximize();
		BaseForSteps.getLogger().info("Window maximized");
		BaseForSteps.getDriver().manage().deleteAllCookies();
		BaseForSteps.getLogger().info("Deleted all cookies");
		BaseForSteps.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}

//	when we implement
	@When("the location is given as mentioned")
	public void the_location_is_given_as() {
		hp = new HomePage(BaseForSteps.getDriver());
		hp.giveSearchInput(BaseForSteps.p.getProperty("location"));
		BaseForSteps.getLogger().info("Search input is given");
	}

//	giving the date input
	@When("dates are given five days from tomorrow")
	public void dates_are_given_five_days_from_tomorrow() {
		hp = new HomePage(BaseForSteps.getDriver());
		hp.giveDateInput();
		BaseForSteps.getLogger().info("Date input is given");
	}

//	giving the adult count
	@When("Setting the adult count to four")
	public void setting_the_adult_count_to_four() {
		hp = new HomePage(BaseForSteps.getDriver());
		hp.giveOccupancyDetails();
		BaseForSteps.getLogger().info("Occupancy details are given");
		hp.clickSearchButton();
		
	}

//	verifying the condition if results appear
	@Then("the result with search criteria appears")
	public void the_result_with_search_criteria_appears() {
		rp = new ResultsPage(BaseForSteps.getDriver());
		BaseForSteps.getLogger().info("Verifying if the search appears");
		Assert.assertEquals(rp.isResultPageHere(), true);
	}

//	giving the condition to set up property type
	@When("the vacation homes is given as property type")
	public void the_vacation_homes_is_given_as_property_type() {
		rp = new ResultsPage(BaseForSteps.getDriver());
		rp.setHolidayHomeasPreference();
		BaseForSteps.getLogger().info("Holiday homes are set as preferences");
	}

//	sorting the results based on the ratings
	@When("sort the result with rating in descending order")
	public void sort_the_result_with_rating_in_descending_order() {
		rp = new ResultsPage(BaseForSteps.getDriver());
		rp.setSortToHighestRating();
		BaseForSteps.getLogger().info("Sorted according to the highest priority");
	}

//	getting the property names and price
	@Then("the result is provided with given criteria")
	public void the_result_is_provided_with_given_criteria() throws IOException {
		rp = new ResultsPage(BaseForSteps.getDriver());
		rp.getAllHotelNamesWithPrice();
		BaseForSteps.getLogger().info("Got hotel names with rent fee printed on console and stored on excel sheet");
	}

}

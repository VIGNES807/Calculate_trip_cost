package pommodels;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CruiseHomePage {
	
	WebDriver driver;
	WebDriverWait wait3;
	
	
//	constructor for cruise home page
	public CruiseHomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
//	to navigate to cruise line
	@FindBy(xpath = "//button[@id='hp_searchFilterCruiseline']")
	WebElement cruiseline;
	
//	all the filters available in the cruise line options
	By cruiseline_box = By.id("hp_searchCruiselineFilterItems");
	
	
//	x path for carnival cruise line
	@FindBy(xpath="(//div[@id='hp_searchCruiselineFilterItems']//button)[1]")
	WebElement cruise_line_1;
	
//	close the filter
	@FindBy(id="hp_searchCruiselineFilterClose")
	WebElement close_box;
	
	//Optional
	By c_box_waiter = By.id("hp_searchCruiselineFilterClose");
	
//	view cruise button		
	@FindBy(id="hp_searchContinue")
	WebElement view_cruises;
	
	
	By popup  = By.id("om-oveyuxwr24rpyl42hdfp-optin");
	
	@FindBy(xpath="//button[@class='CloseButton__ButtonElement-sc-79mh24-0 dXqkKJ fallsview-CloseButton fallsview-close fallsview-ClosePosition--top-right']")
	WebElement close_popup;
	
	
	@FindBy(id="ajaxSpinner")
	WebElement loading_spinner;
	
	
	public void setCruiseDetails() {
		try {
//		handle any pop up
			HandlePopup();
		}
		catch(Exception e) {
			System.out.println("There is no popup in cruise homepage too.. Haha !");
		}
		
		finally {
//		cruise line link
		clickCruiseLine();
		waitForElement(cruiseline_box);
//		close icon
		clickCruiseOption();
		waitForElement(c_box_waiter);
		waitForAttrToBe(loading_spinner);
//		wait3.until(ExpectedConditions.elementToBeClickable(close_box));
		clickClose();
//		view cruises
		clickViewCruises();
		}
	}
	
//	performing click action 
	public void clickCruiseLine() {
		cruiseline.click();
	}
	
//	performing click action
	public void clickCruiseOption() {
		cruise_line_1.click();
	}
	
//	performing click action
	public void clickClose() {
		close_box.click();
	}

	
//	performing click action
	public void clickViewCruises() {
		view_cruises.click();
	}
	
//	
	public void waitForElement(By elem) {
		wait3 = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait3.until(ExpectedConditions.presenceOfElementLocated(elem));
	}
	
	
	public void HandlePopup() {
		waitForElement(popup);
		close_popup.click();
		
		
	}
	
	public void waitForAttrToBe(WebElement elem) {
		wait3 = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait3.until(ExpectedConditions.attributeToBeNotEmpty(elem, "style"));
	}

}

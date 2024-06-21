package pommodels;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import excelmanager.ExcelUtils;
import factory.BaseForSteps;

public class ResultsPage{
	WebDriver driver;
	WebDriverWait wait2;
	
//	creating constructor
	public ResultsPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
//	drop down trigger
	@FindBy(xpath="//button[@data-testid='sorters-dropdown-trigger']")
	WebElement sort_dropdown;
	
	By sort_button = By.xpath("//button[@data-testid='sorters-dropdown-trigger']");

//	drop down panel
	By sortListElem = By.xpath("//div[@data-testid='sorters-dropdown']");
	
//	all options in drop down
	@FindBy(xpath="//ul[@class='b122235413']//li")
	List<WebElement> sort_list_elem;
	
//	pop up
	@FindBy(xpath="//div[@class='b43bdecede']//button")
	WebElement close_popup;
	
	By offerDiv = By.className("b43bdecede");
	
//	show all button 
	@FindBy(xpath="(//div[@data-filters-group='ht_id'])[1]//button")
	WebElement showall_button;
	
//	vacation homes
	@FindBy(xpath="(//div[contains(text(),'Holiday homes')  or contains(text(),'Vacation Homes')])[1]")
	WebElement vacation_home;
	
	By vh = By.xpath("(//div[contains(text(),'Holiday homes')  or contains(text(),'Vacation Homes')])[1]");
//	property type show all
	By sb = By.xpath("(//div[@data-filters-group='ht_id'])[1]//button");
	
//	all property card
	By property_cards = By.xpath("//div[@data-testid='property-card']");
//	title of all properties
	By pn = By.xpath("//div[@data-testid='property-card']//div[@data-testid='title']");
//	price and discount of 1st property
	By rp = By.xpath("(//div[@data-testid='property-card']//span[@data-testid='price-and-discounted-price'])[1]");
//	property title
	@FindBy(xpath="//div[@data-testid='property-card']//div[@data-testid='title']")
	List<WebElement> property_names;
	
//	price of properties
	@FindBy(xpath="(//div[@data-testid='property-card']//span[@data-testid='price-and-discounted-price'])")
	List<WebElement> rental_price;
	
//	room accessibility (elevator)
	@FindBy(xpath="(//div[contains(text(),'Upper floors accessible by elevator')])[1]")
	WebElement elevator;
	
//	sorting from high to low rating
	public void setSortToHighestRating() {
			waitForElement(property_cards);
			clickSortDropDown();
			waitForElement(sortListElem);
			for(WebElement a: sort_list_elem) {
				System.out.println(a.getText());
				if(a.getText().equals("Property rating (high to low)")) {
					a.click();
					break;
				}	
		}
		}
	
//	getting property title and price
	public void getAllHotelNamesWithPrice() throws IOException {
		waitForElement(property_cards);
		for(int i =0;i<property_names.size() && i<rental_price.size();i++) {
//			java script executor to scroll
			((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);",property_names.get(i));
			String n = (property_names.get(i)).getText();
			String p = (rental_price.get(i)).getText();
			
//			writing in the excel file
			ExcelUtils.setCellData(BaseForSteps.xlfile2, "Sheet1", i+1, 0, n);
			ExcelUtils.setCellData(BaseForSteps.xlfile2, "Sheet1", i+1, 1, p);
			
//			printing in the console
			System.out.println(i+1 +" -> "+ "Property name : "+n + "Price : "+p);
			
		}
	}
	
//	setting preference
	public void setHolidayHomeasPreference() {
		try {
			handleOfferPopup();
		}
		catch(Exception e) {
			System.out.println("There is no popup here too.. Haha !!");
		}
		finally {
			try {
//				check if vacation homes is visible
				waitForElement(vh);
				((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);",vacation_home);
				vacation_home.click();
			}
			catch(Exception e) {
//				if not clicks show all button
				waitForElement(sb);
				((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);",showall_button);
				showall_button.click();
//				and the clicks vacation homes button
				waitForElement(vh);
				((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);",vacation_home);
				vacation_home.click();		
			}
		}
		
	}
	
//	checks for property card and waits
	public boolean isResultPageHere() {
		waitForElement(property_cards);
		return property_names.get(0).isDisplayed();
	}
	
	
	
//	performing drop down action
	public void clickSortDropDown() {
		sort_dropdown.click();
	}
	
//	performing explicit wait
	public void waitForElement(By elem) {
		wait2 = new WebDriverWait(driver, Duration.ofSeconds(7));
		wait2.until(ExpectedConditions.presenceOfElementLocated(elem));
	}
	
//	handling pop up
	public void handleOfferPopup() {
		waitForElement(offerDiv);
		close_popup.click();
	}

}

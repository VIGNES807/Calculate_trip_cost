package pommodels;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
	WebDriver driver;
	WebDriverWait wait1;
	
	
	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
//	search bar
	@FindBy(xpath="//input[@name='ss']")
	WebElement search_box;
	
	
//	pop up
	@FindBy(xpath="//div[@class='b43bdecede']//button")
	WebElement close_popup;
	
	By offerDiv = By.className("b43bdecede");
	
//	check in/out date
	@FindBy(xpath="//button[@data-testid='date-display-field-start']")
	WebElement date_picker_button;
	
	By calendar_box = By.xpath("//div[@data-testid = 'searchbox-datepicker']");
	
//	to access all the dates
	@FindBy(xpath="//div[@data-testid = 'searchbox-datepicker-calendar']//table//td/span")
	List<WebElement> dates;
	
//	occupancy 
	@FindBy(xpath="//button[@data-testid = 'occupancy-config']")
	WebElement occupancy;
	
	By occupancy_popup = By.xpath("//div[@data-testid='occupancy-popup']");
	
//	adults + sign
	@FindBy(xpath="//div[@data-testid='occupancy-popup']/descendant::button[2]")
	WebElement add_adult_by_one;
//	space to indicate the occupancy of adult
	@FindBy(xpath="//div[@data-testid='occupancy-popup']/descendant::span[3]")
	WebElement adult_count;
//	done button
	@FindBy(xpath="//button[normalize-space() = 'Done']")
	WebElement done_button;
	
//	search button
	@FindBy(xpath="//button[normalize-space() = 'Search']")
	WebElement search_button;
			
	
	
//	handling pop up
	public void giveSearchInput(String loc) {
		try {
		handleOfferPopup();
		}
		catch(Exception e) {
			System.out.println("There is no popup..");
		}
//		must execute
		finally {		
			search_box.click();
			search_box.clear();
			search_box.sendKeys(loc);
		}
		
	}
	
//	give date input
	public void giveDateInput() {
		date_picker_button.click();
		waitForElement(calendar_box);
//		gets tomorrows date
		String tomorrow_date = getTomorrowDate();
//		gets date after 5 days
		String after_fivedays = getDateAfterFiveDays();
//		performs action if condition pass
		for(WebElement a:dates) {
			if(a.getAttribute("data-date").equals(tomorrow_date)) {
				a.click();
			}
			
			if((a.getAttribute("data-date").equals(after_fivedays))) {
				a.click();
				break;
			}
		}
		
	}
	

	public void giveOccupancyDetails() {
		occupancy.click();
		waitForElement(occupancy_popup);
//		clicks until we get 4
		while(!adult_count.getText().equals("4")) {
			add_adult_by_one.click();
		}
		done_button.click();
		
	}
	
//	clicks search button
	public void clickSearchButton() {
		search_button.click();
	}
	
//	waits for element
	public void waitForElement(By elem) {
		wait1 = new WebDriverWait(driver, Duration.ofSeconds(7));
		wait1.until(ExpectedConditions.presenceOfElementLocated(elem));
	}
	
	
//	getting tomorrow date
	public String getTomorrowDate() {
		LocalDate tomorrow = LocalDate.now().plusDays(1);
		DateTimeFormatter pattern =	DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return tomorrow.format(pattern);
	}
	
//	getting date after 5 days
	public String getDateAfterFiveDays() {
		LocalDate d = LocalDate.now().plusDays(6);
		DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return d.format(pattern);
	}
	
//	handling pop up
	public void handleOfferPopup() {
		waitForElement(offerDiv);
		close_popup.click();
	}
}

package pommodels;

import java.io.IOException;
import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import excelmanager.ExcelUtils;
import factory.BaseForSteps;

public class CruiseResultsPage {
	
	WebDriver driver;
	WebDriverWait wait4;
	
//	creating constructor
	public CruiseResultsPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
//	name of cruise
	@FindBy(xpath="//ul[@id='cruiselist']/li//h3[@class='wth2-shipName']")
	List<WebElement> cruises_name;
	
	By c_list_waiter = By.id("cruiselist");
	
//	loading spinner
	@FindBy(id="ajaxSpinnerOuter")
	WebElement loading_spinner;
	
//	getting cruise details
	public void getCruiseDetails() throws IOException {
//		set avoids duplicate values
		Set<String> unique_cruises = new HashSet<String>();
		waitForElement(c_list_waiter);
		System.out.println("Available Cruises : ");
//		extracts the name of each cruise and saves in set
		for(WebElement a: cruises_name) {
			unique_cruises.add(a.getText());
		}
		int i = 0;
		for(String b: unique_cruises) {
//			prints in console
			System.out.println("-> "+b);
//			writes in the excel file
			ExcelUtils.setCellData(BaseForSteps.xlfile1, "Sheet2", ++i, 0,b);
		}
		System.out.println();
	}
	
	public void getFirstCruiseFullDetails() {
//		make sure the page loads fully
		waitForAttrToBe(loading_spinner);
//		clicks the first cruise
		WebElement f_cruise = cruises_name.get(0);
		f_cruise.click();
	}
	
	
//	check if the cruise details is displayed
	public boolean isResultDisplayed() {
		return cruises_name.size()>0;
	}
	public void waitForElement(By elem) {
		wait4 = new WebDriverWait(driver, Duration.ofSeconds(7));
		wait4.until(ExpectedConditions.presenceOfElementLocated(elem));
	}
	
	public void waitForAttrToBe(WebElement elem) {
		wait4 = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait4.until(ExpectedConditions.attributeToBeNotEmpty(elem, "style"));
	}
	 
	

}

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

public class SpecificCruiseDetails {
	WebDriver driver;
	WebDriverWait wait5;
	public SpecificCruiseDetails(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
//	ship details
	@FindBy(xpath="//a[@id='expandCollapse_ship']")
	WebElement ship_details_button;
	
//	expanded ship details
	@FindBy(xpath="//div[@id='expandCollapse_ship_Content']//p")
	List<WebElement> details;
	
//	ship name
	@FindBy(xpath = "(//div[@id='expandCollapse_ship_Content']//p)[1]")
	WebElement first_detail;
	
//	ship statistics
	By details_waiter = By.xpath("//div[@id='expandCollapse_ship_Content']//p");
	
//	getting the complete statistics of the cruise
	public void getCompleteDet() throws IOException {
		clickshipDeatils();
		waitForElement(details_waiter);
//		scroll action
		((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true)", first_detail);
		for(int i =0;i<details.size();i++) {
//			printing in console
			System.out.println(details.get(i).getText());
//			printing in the excel file
			ExcelUtils.setCellData(BaseForSteps.xlfile1, "Sheet1", i+1, 0, details.get(i).getText());
		}
			
	}
	
//	performing the click action for ship details button
	public void clickshipDeatils() {
		ship_details_button.click();
	}
	
//	performing explicit wait
	public void waitForElement(By elem) {
		wait5 = new WebDriverWait(driver, Duration.ofSeconds(7));
		wait5.until(ExpectedConditions.presenceOfElementLocated(elem));
	}

}

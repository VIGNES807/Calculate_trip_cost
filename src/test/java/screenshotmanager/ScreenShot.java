package screenshotmanager;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;


public class ScreenShot {

	public static int screenshotcount = 0;
	
	public static Boolean fullScreenshot(WebDriver driver) throws IOException {
//		source file temporary directory
		File srcfile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
//		to move the screenshot to specified directory
		FileUtils.copyFile(srcfile, new File(System.getProperty("user.dir")+"\\src\\test\\resources\\Screenshots\\image"+ ++screenshotcount+".png"));
		return true;
	}
	
//	public static Boolean elementScreenshot(WebElement element) throws IOException {
//		File srcFile = element.getScreenshotAs(OutputType.FILE);
//		FileUtils.copyFile(srcFile,  new File(System.getProperty("user.dir")+"\\src\\test\\resources\\Screenshots\\image"+ ++screenshotcount+".png"));
//		return true;
//	}
}

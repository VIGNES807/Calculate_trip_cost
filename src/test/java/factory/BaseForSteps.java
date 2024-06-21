package factory;

import java.io.FileReader;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class BaseForSteps {
	public static WebDriver driver;
	public static Logger logger;
//	setting the file path for cruise output
	public static String xlfile1 = System.getProperty("user.dir")+"\\src\\test\\resources\\ExcelFiles\\Cruise Outputs.xlsx";
//	setting the file path for vacation homes output
	public static String xlfile2 = System.getProperty("user.dir")+"\\src\\test\\resources\\ExcelFiles\\VacHomes Outputs.xlsx";
	
	public static Properties p;
	
//	initializing the driver
	public static WebDriver initializeWebDriver() throws Exception {
		//This is used for run local or remote mode
	if(getProperties().getProperty("execution_env").equalsIgnoreCase("remote")){
		
		DesiredCapabilities capabilities = new DesiredCapabilities();
		
		//for run which OS user want
		if (getProperties().getProperty("os").equalsIgnoreCase("windows")) {
		    capabilities.setPlatform(Platform.WIN11);
		} else if (getProperties().getProperty("os").equalsIgnoreCase("mac")) {
		    capabilities.setPlatform(Platform.MAC);
		} else {
		    System.out.println("No matching OS..");
		      }
		//browser
		switch (getProperties().getProperty("browser").toLowerCase()) {
		    case "chrome":
		        capabilities.setBrowserName("chrome");
		        break;
		    case "edge":
		        capabilities.setBrowserName("MicrosoftEdge");
		        break;
		    default:
		        System.out.println("No matching browser");
		     }
       
        driver = new RemoteWebDriver(new URL("http://10.229.50.77:4444/"),capabilities);
		
	}
	else if(getProperties().getProperty("execution_env").equalsIgnoreCase("local"))
		{
			switch(getProperties().getProperty("browser").toLowerCase()) {
				case "chrome":
			        driver=new ChromeDriver();
			        break;
			    case "edge":
			    	driver=new EdgeDriver();
			        break;
			    default:
			        System.out.println("No matching browser");
			        driver=null;
			}
		}
//					 driver.manage().deleteAllCookies(); 
		 driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//					 driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

		 return driver;
	 

	}
	
//	setting up properties for grid
	public static Properties getProperties() throws Exception {
		FileReader file = new FileReader("C:\\Users\\2319974\\Downloads\\Hackathon Project\\CalculateTripCostCucumber\\src\\test\\resources\\config.properties");
		p = new Properties();
		p.load(file);
		return p;
	}
//	retrieving log file 
	public static Logger getLogger() 
	{		 
		logger=LogManager.getLogger(); //Log4j
		return logger;
	}
	
//	to interact with other classes
	public static WebDriver getDriver() {
		return driver;
	}
	

}

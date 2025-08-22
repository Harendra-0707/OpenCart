package testBase;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;		// Log4j
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.Logger;		// Log4j
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
// this is the parent class or common calss of all the test classess...
// this class contains the re-usable methods required for all the testcases...

public class BaseClass {
	public static WebDriver driver;
	public Logger logger;	// Log4j ... to create log, we created Logger class object
	public Properties prop;
	
	@BeforeClass(groups = {"Sanity", "Regression", "Master"})
	@Parameters({"os", "browser"})
	public void setup(String os, String br) throws IOException {
		
		// this code is for local and remote environment...
		// Loading config.properties file...
		FileReader file = new FileReader("./src//test//resources//config.properties");
		prop = new Properties();
		prop.load(file);
		
		logger = LogManager.getLogger(this.getClass());		//log4j2.. we can use it for particular class for which we want to generate logs..
		
		// for remote environment....
		if(prop.getProperty("execution_env").equalsIgnoreCase("remote")) {
			
			// The URL will be IP Address of Hub Machine + Hub Port + /wd/hub
			//http://192.168.12.1:4444/wd/hub or http://localhost:4444/wd/hub
			
			String huburl = "http://localhost:4444";
			// to set the platform like which os or browser, we need to use "DesiredCapabilities" class...
			DesiredCapabilities cap = new DesiredCapabilities();
			//cap.setPlatform(Platform.WIN10);	//cap.setPlatform(Platform.MCA);
			//cap.setBrowserName("chrome");	//cap.setBrowserName("MicrosoftEdge");
			
			//OS...coming from xml file
			if(os.equalsIgnoreCase("windows")) {
				cap.setPlatform(Platform.WIN10);
			}
			else if(os.equalsIgnoreCase("linux")) {
				cap.setPlatform(Platform.LINUX);
			}
			else if(os.equalsIgnoreCase("mac")) {
				cap.setPlatform(Platform.MAC);
			}
			else {
				System.out.println("No matching operation system...");
				return;
			}
			
			//browser....
			switch(br.toLowerCase()) {
			case "chrome": cap.setBrowserName("chrome"); break;
			case "edge": cap.setBrowserName("MicrosoftEdge"); break;
			case "firefox": cap.setBrowserName("Firefox"); break;
			default: System.out.println("No matching browser"); return;
			}
			
			driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap);
		}
		
		// this is for local execution...
		if(prop.getProperty("execution_env").equalsIgnoreCase("local")) {
			
			switch (br.toLowerCase()) {
			case "chrome" : driver = new ChromeDriver(); break;
			case "edge" : driver = new EdgeDriver(); break;
			case "firefox" : driver = new FirefoxDriver(); break;
			default : throw new RuntimeException(br + "Invalid broser has been passed."); 
			//return;
			}
		}
		
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get(prop.getProperty("appURL"));		// Reading URL from properties file...
		driver.manage().window().maximize();
	}
	
	@AfterClass(groups = {"Sanity", "Regression", "Master"})
	public void tearDown() {
		//driver.close();
		driver.quit();
	}
	
	// user defined method to generated random data...
		public String randomString() {
			// "RandomStringUtils" is a pre-defined class in java, will use here..
			String generatedstring = RandomStringUtils.randomAlphabetic(5);
			return generatedstring;
		}
		
		// user defined method to generated random numbers...
		public String randomNumber() {
			// "RandomStringUtils" is a pre-defined class in java, will use here..
			String generatednumber = RandomStringUtils.randomNumeric(10);
			return generatednumber;
		}
		
		// user defined method to generated alphanumeric data...
		public String randomAlphaNumeric() {
			// "RandomStringUtils" is a pre-defined class in java, will use here..
			String generatedstring = RandomStringUtils.randomAlphabetic(4);
			String generatednumber = RandomStringUtils.randomNumeric(3);
			return (generatedstring+"@"+generatednumber);
		}
		
		// screenshot method....
		public String captureScreen(String tname) {
			// here will use time stamp so that it'll take screenshot along with timestamp...
			String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
			
			TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
			File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
		    String targetFilePath =	System.getProperty("user.dir") + "\\screenshots\\" + tname + "_" + timeStamp + ".png";
			File targetFile = new File(targetFilePath);
			sourceFile.renameTo(targetFile);
			return targetFilePath;
		}
}

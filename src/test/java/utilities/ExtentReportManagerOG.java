package utilities;

import java.awt.Desktop;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManagerOG implements ITestListener {
	
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;
	
	String repName;	// variable reportname declared...
	
	public void onStart(ITestContext testContext) {
		
	/*	SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		Date dt = new Date();
		String currentDateTimeStamp = df.format(dt);
		*/
		
		// instead of above 3 lines of code, will write it in a single line to reduce code..
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()); // time stamp
		repName = "Test-Report-" + timeStamp + ".html";	//initialized here...
		sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName);	//Specified the location of the report...
		
		sparkReporter.config().setDocumentTitle("Opencart Automation Report");	// title of the report...
		sparkReporter.config().setReportName("Opencart Functional Testing"); 	// name of the report...
		sparkReporter.config().setTheme(Theme.DARK);
		
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Application", "Opencart");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("Sub Module", "Customers");
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Environment", "QA");
		
		String os = testContext.getCurrentXmlTest().getParameter("os");
		extent.setSystemInfo("Operating System", os);
		
		String browser = testContext.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("Browser", browser);
		
		// check whether the groups info are available in xml file or not...if available then get it...
		List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
		// if included group is not empty and have some values....
		if(!includedGroups.isEmpty()) {
			extent.setSystemInfo("Groups", includedGroups.toString());
		}
	}
	
	public void onTestSuccess(ITestResult result) {
		
		// here getting the name of test class..
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());	// getting the info of tested method along with group info like sanity, regression or master....
		test.log(Status.PASS, result.getName() + " got successfully executed.");
	}
	
	public void onTestFailure(ITestResult result) {
		
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		
		test.log(Status.FAIL, result.getName()+" got failed.");
		test.log(Status.INFO, result.getThrowable().getMessage());
		
		// Here will take the screenshot of the failed test case, call the screenshot method which is there in BaseClass... common method...
		// will put this in try catch block...
		try {
			String imgPath = new BaseClass().captureScreen(result.getName());
			test.addScreenCaptureFromPath(imgPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void onTestSkipped(ITestResult result) {
		
		test = extent.createTest(result.getClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		
		test.log(Status.SKIP, result.getName() + " got skipped.");
		test.log(Status.INFO, result.getThrowable().getMessage());
	}
	
	public void onFinish(ITestContext testContext) {
		
		extent.flush();
		
		// to open extent report automaticall post finishing test....
		String pathOfExtentReport = System.getProperty("user.dir") + "\\reports\\" + repName;
		File extentReport = new File(pathOfExtentReport);
		
		try {
			Desktop.getDesktop().browse(extentReport.toURI());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/* In case if you want to email test report after finishing test automatically...
		 * 
		 *  try {
		  URL url = new  URL("file:///"+System.getProperty("user.dir")+"\\reports\\"+repName);
	  
	  // Create the email message 
	  ImageHtmlEmail email = new ImageHtmlEmail();
	  email.setDataSourceResolver(new DataSourceUrlResolver(url));
	  email.setHostName("smtp.googlemail.com"); 
	  email.setSmtpPort(465);
	  email.setAuthenticator(new DefaultAuthenticator("alex@gmail.com","password")); 
	  email.setSSLOnConnect(true);
	  email.setFrom("alextemp@gmail.com"); //Sender
	  email.setSubject("Test Results");
	  email.setMsg("Please find Attached Report....");
	  email.addTo("john_kv@gmail.com"); //Receiver 
	  email.attach(url, "extent report", "please check report..."); 
	  email.send(); // send the email 
	  }
	  catch(Exception e) 
	  { 
		  e.printStackTrace(); 
		  }
	 */ 
	
	}

}

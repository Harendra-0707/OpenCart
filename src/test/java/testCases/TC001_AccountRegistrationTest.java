package testCases;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass {
	
	// Testing with static test data...
/*	@Test
	public void verify_account_registration() {
		HomePage hp = new HomePage(driver);
		hp.clickOnMyAccount();
		hp.clickOnRegister();
		
		AccountRegistrationPage regPage = new AccountRegistrationPage(driver);
		regPage.setFirstName("Kevin");
		regPage.setLastName("John");
		regPage.setEmail("kevinn_001@gmail.com");
		regPage.setTelephone("7171717120");
		regPage.setPassword("test@123");
		regPage.setConfirmPassword("test@123");
		regPage.setPrivacyPolicy();
		regPage.clickOnContinueBtn();
		String confmsg = regPage.getConfirmationMsg();
		Assert.assertEquals(confmsg, "Your Account Has Been Created!");
	}
	*/
	
	// Testing with dynamic or random test data at runtime....
	@Test(groups = {"Regression", "Master"})	// Master group contains all the test..
	public void verify_account_registration() {
		logger.info("******* Starting TC001_AccountRegistrationTest*******");
		// putting our test steps into try catch so that if any error occur, then it'll handle & with the help of logger, will get proper logs...
		try {
		HomePage hp = new HomePage(driver);
		hp.clickOnMyAccount();
		logger.info("Clicked on MyAccount Link!");
		hp.clickOnRegister();
		logger.info("Clicked on Register Link!");
		
		AccountRegistrationPage regPage = new AccountRegistrationPage(driver);
		logger.info("***** Providing Customer Details *****");
		regPage.setFirstName(randomString().toUpperCase()); // randomly generated first name
		regPage.setLastName(randomString().toUpperCase());	// randomly generated last name
		regPage.setEmail(randomString()+"@gmail.com");	// randomly generated email...
		regPage.setTelephone(randomNumber());
		
		// Capture the generated value or password by "randomAlphaNumeric" method in a variable...
		String password = randomAlphaNumeric();
		regPage.setPassword(password);
		regPage.setConfirmPassword(password);
		regPage.setPrivacyPolicy();
		regPage.clickOnContinueBtn();
		
		logger.info("Validating expected message...!!");
		String confmsg = regPage.getConfirmationMsg();
		if(confmsg.equals("Your Account Has Been Created!")) {
			Assert.assertTrue(true);
		}else {
			logger.error("Test Failed...");
			logger.debug("Debug Logs...");
			Assert.assertTrue(false);
		}
		
		// Assert.assertEquals(confmsg, "Your Account Has Been Created!");
		} catch(Exception e) {
			Assert.fail();
		}
		logger.info("******* Finished TC001_AccountRegistrationTest*******");
	}
	
}

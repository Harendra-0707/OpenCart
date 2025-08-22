package testCases;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass {
	
	// Test with valid credentials or static data...
	@Test(groups = {"Sanity", "Master"})
	public void verify_login() {
		logger.info("***** Starting TC002_LoginTest *****");
		
		try {
		//Homepage...
		HomePage hp = new HomePage(driver);
		hp.clickOnMyAccount();
		hp.clickOnLogin();
		
		//Loginpage...
		LoginPage lp = new LoginPage(driver);
		lp.enterEmail(prop.getProperty("email"));
		lp.enterPassword(prop.getProperty("password"));
		lp.clickOnLoginBtn();
		
		//MyAccountpage...
		MyAccountPage macc = new MyAccountPage(driver);
		boolean targetPage = macc.isMyAccountPageExist();
		//Assert.assertEquals(targetPage, true, "Login Failed!");
		Assert.assertTrue(targetPage);
		
		//Logout...
		macc.clickOnLogoutLink();
		
		} catch (Exception e) {
			Assert.fail();
		}
		
		logger.info("***** Finished TC002_LoginTest *****");
	}

}

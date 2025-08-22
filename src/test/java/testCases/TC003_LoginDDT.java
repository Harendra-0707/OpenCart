package testCases;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

/* Data is valid	-	login success - test pass - logout
 * 						login failed - test fail
 * 
 * Data is invalid	-	login success - test fail - logout
 * 						login failed - test pass
 */

public class TC003_LoginDDT extends BaseClass {

	@Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class, groups = "Datadriven") // getting dataprovider from different
																				// pacakge & class..
	public void verify_loginDDT(String email, String pwd, String expectedValue) throws InterruptedException {

		logger.info("***** Staring TC003_LoginDDT *****");
		try {
			// HomePage..
			HomePage hp = new HomePage(driver);
			hp.clickOnMyAccount();
			hp.clickOnLogin();	// login link under myaccount

			// Loginpage...
			LoginPage lp = new LoginPage(driver);
			lp.enterEmail(email);
			lp.enterPassword(pwd);
			lp.clickOnLoginBtn();

			// MyAccountpage...
			MyAccountPage macc = new MyAccountPage(driver);
			boolean targetPage = macc.isMyAccountPageExist();

			// validations....
			if (expectedValue.equalsIgnoreCase("valid")) {
				if (targetPage==true) {
					macc.clickOnLogoutLink();
					Assert.assertTrue(true);
				} else {
					Assert.assertTrue(false);
				}
			}
			if (expectedValue.equalsIgnoreCase("invalid")) {
				if (targetPage==true) {
					macc.clickOnLogoutLink();
					Assert.assertTrue(false);
				} else {
					Assert.assertTrue(true);
				}
			}

		} 
		catch (Exception e) {
			Assert.fail("An exception occurred: " + e.getMessage());
		}
		
		Thread.sleep(2000);
		logger.info("***** Finished TC003_LoginDDT *****");

	}
}

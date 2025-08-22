package pageObjects;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccountPage extends BasePage {
	
	public MyAccountPage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy (xpath = "//h2[text()='My Account']")		// MyAccount page heading...
	private WebElement msgHeading;
	
	@FindBy (xpath = "//div[@class='list-group']//a[text()='Logout']")
	private WebElement lnkLogout;
	
	public boolean isMyAccountPageExist() {
		try {
			return (msgHeading.isDisplayed());
			
		} catch (Exception e) {
			throw new RuntimeException("MyAccount Page is not displayed...");
		}
	}
	
	public void clickOnLogoutLink() {
		lnkLogout.click();
	}
}

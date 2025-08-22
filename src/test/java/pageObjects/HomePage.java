package pageObjects;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {
	
	public HomePage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy (xpath = "//span[text()='My Account']")
	private WebElement myAccountLink;
	
	@FindBy (xpath = "//a[normalize-space()='Register']")
	private WebElement registerLink;
	
	@FindBy (xpath = "//a[normalize-space()='Login']")
	private WebElement loginLink;
	
	public void clickOnMyAccount() {
		myAccountLink.click();
	}
	
	public void clickOnRegister() {
		registerLink.click();
	}
	
	public void clickOnLogin() {
		loginLink.click();
	}

}

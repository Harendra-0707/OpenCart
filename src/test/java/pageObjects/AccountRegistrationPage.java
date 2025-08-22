package pageObjects;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountRegistrationPage extends BasePage {
	
	public AccountRegistrationPage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy (xpath = "//input[@id='input-firstname']")
	private WebElement txtFirstname;
	
	@FindBy (xpath = "//input[@id='input-lastname']")
	private WebElement txtLastname;
	
	@FindBy (xpath = "//input[@id='input-email']")
	private WebElement txtEmail;
	
	@FindBy (xpath = "//input[@id='input-telephone']")
	private WebElement txtTelephone;
	
	@FindBy (xpath = "//input[@id='input-password']")
	private WebElement txtPassword;
	
	@FindBy (xpath = "//input[@id='input-confirm']")
	private WebElement txtConfirmPassword;
	
	@FindBy (xpath = "//input[@name='agree']")
	private WebElement chkdPolicy;
	
	@FindBy (xpath = "//input[@value='Continue']")
	private WebElement btnContinue;
	
	@FindBy (xpath = "//h1[normalize-space()='Your Account Has Been Created!']")
	private WebElement msgConfirmation;
	
	@FindBy (xpath = "//a[text()='Continue']")
	private WebElement btnContinueOnSuccessRegistration;
	
	public void setFirstName(String fname) {
		txtFirstname.sendKeys(fname);
	}
	
	public void setLastName(String lname) {
		txtLastname.sendKeys(lname);
	}
	
	public void setEmail(String email) {
		txtEmail.sendKeys(email);
	}
	
	public void setTelephone(String tel) {
		txtTelephone.sendKeys(tel);
	}
	
	public void setPassword(String pwd) {
		txtPassword.sendKeys(pwd);
	}
	
	public void setConfirmPassword(String pwd) {
		txtConfirmPassword.sendKeys(pwd);
	}
	
	public void setPrivacyPolicy() {
		chkdPolicy.click();
	}
	
	public void clickOnContinueBtn() {
		
		// solution way number 1
		btnContinue.click();
		
		// sol 2
		//Actions act = new Actions(driver);
		//act.moveToElement(btnContinue).click().perform();
		
		//sol 3
		//JavaScriptExecutor js = (JavaScriptExecutor)driver;
		//js.executeScript("arguments[0].click();", btnContinue);
		
		//sol 4
		// btnContinue.sendKeys(keys.RETURN);
		
		//sol 5
		//WebDriverWait myWait = new WebDriverWait(driver, Duration.ofSeconds(10));
		//myWait.until(ExpectedConditions.elementToBeClickable(btnContinue)).click();
	}
	
	public String getConfirmationMsg() {
		try {
			return (msgConfirmation.getText());
		} catch (Exception e) {
			return (e.getMessage());
		}
	}


}

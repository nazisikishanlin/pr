package info.itest.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
	private String longinURL="http://localhost/wordpress/wp-login.php";
	private WebDriver dr;
	
	@FindBy(id="user_login")
	private WebElement usernameTextField;
	
	@FindBy(id="user_pass")
	private WebElement passwordTextField;
	
	@FindBy(id="wp_submit")
	private WebElement loginBtn;
	
	
	public LoginPage(WebDriver driver){
		this.dr=driver;
		navigate();
	}
	private void navigate(){
		dr.get(longinURL);
	}
	public DashboardPage login(String username,String password){
		usernameTextField.sendKeys(username);
		passwordTextField.sendKeys(password);
		loginBtn.click();
		
		return PageFactory.initElements(dr,DashboardPage.class);
		
	}
	

}

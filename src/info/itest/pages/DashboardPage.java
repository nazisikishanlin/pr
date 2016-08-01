package info.itest.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DashboardPage {
	private WebDriver dr;
	
	@FindBy(css="#wp-admin-bar-my-account .ab-item")
	private WebElement greetingLink;
	
	public DashboardPage(WebDriver driver) {
        dr = driver;
    }
	
	public String getGreetingLinkText(){
		return greetingLink.getText();
	}
}

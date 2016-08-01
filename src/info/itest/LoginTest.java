package info.itest;

import static org.junit.Assert.*;
import info.itest.pages.DashboardPage;
import info.itest.pages.LoginPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;


public class LoginTest {
	private WebDriver dr;
	
	@Before
	public void openBrower(){
		this.dr=new FirefoxDriver();
	}
	@Test
	public void testLoginSuccess(){
		String username="admin";
		String password="admin";
		LoginPage loginPage=new LoginPage(dr);
		PageFactory.initElements(dr, loginPage);
		DashboardPage dashboardPage=loginPage.login(username, password);
	    assertTrue(dr.getCurrentUrl().contains("wp-admin"));
		assertTrue(dashboardPage.getGreetingLinkText().contains(username));
		
	}
	@After
	public void closeBrower(){
		this.dr.close();
	}
	
}

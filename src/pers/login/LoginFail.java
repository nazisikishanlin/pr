package pers.login;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import static org.junit.Assert.*;

public class LoginFail {
	private String loginURL="http://localhost/wordpress/wp-login.php";
	private WebDriver dr;
	
	@Before
	public void openBrower(){
		this.dr=new FirefoxDriver();
	}
	//注释
	

    public void login(String username,String password){
    dr.get(loginURL);	
	dr.findElement(By.id("user_login")).sendKeys(username);
	dr.findElement(By.id("user_pass")).sendKeys(password);
	dr.findElement(By.id("wp-submit")).click();
}
		
	
	//用户名，密码都为空
    @Test
	public void loginFail1(){
		dr.get(loginURL);
		login(null,null);
		assertTrue(dr.getCurrentUrl().equals("http://localhost/wordpress/wp-login.php"));
		
	}
    //用户名错误
    @Test
	public void loginFail2(){
		dr.get(loginURL);
		login("admin1","123456");
		assertTrue(dr.getCurrentUrl().equals("http://localhost/wordpress/wp-login.php"));
		WebElement errorMessage=dr.findElement(By.id("login_error"));
		assertTrue(errorMessage.getText().contains("无效用户名"));
		
	}
    //密码错误
    @Test
	public void loginFail3(){
		dr.get(loginURL);
		login("admin","admin");
		assertTrue(dr.getCurrentUrl().equals("http://localhost/wordpress/wp-login.php"));
		WebElement errorMessage=dr.findElement(By.id("login_error"));
		assertTrue(errorMessage.getText().contains("密码不正确"));
		
	}
    //用户名为空
    @Test
	public void loginFail4(){
		dr.get(loginURL);
		login(null,"admin");
		assertTrue(dr.getCurrentUrl().equals("http://localhost/wordpress/wp-login.php"));
		WebElement errorMessage=dr.findElement(By.id("login_error"));
		assertTrue(errorMessage.getText().contains("用户名一栏为空"));
	
		
	}
	
    //密码为空
    @Test
	public void loginFail5(){
		dr.get(loginURL);
		login("admin",null);
		assertTrue(dr.getCurrentUrl().equals("http://localhost/wordpress/wp-login.php"));
		WebElement errorMessage=dr.findElement(By.id("login_error"));
		assertTrue(errorMessage.getText().contains("密码一栏为空"));
	
		
	}
	@After
	public void closeBrower(){
		this.dr.close();
		
	}

}

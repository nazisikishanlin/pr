package pers.article;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.JavascriptExecutor;

import static org.junit.Assert.*;

public class CreateArticleFromQQTodayTop {
	private String loginURL="http://localhost/wordpress/wp-login.php";
	private String postArticleURL="http://localhost/wordpress/wp-admin/post-new.php";
	private String allArticleListURL="http://localhost/wordpress/wp-admin/edit.php";
	private String qqURL="http://www.qq.com/";
	private WebDriver dr;
	
	
	@Before
	public void openBrower(){
		this.dr=new FirefoxDriver();
	}
	
	@Test
	public void postArticleSuccess(){
		String[] todayTopContent=getTodayTopic();
		loginAdmin();
		dr.get(postArticleURL);
		String title=todayTopContent[0];
		dr.findElement(By.id("title")).sendKeys(title);
		String content=todayTopContent[1];
		setContent(content);
		dr.findElement(By.id("publish")).click();
		dr.get(allArticleListURL);
		String newArticleTitle=dr.findElement(By.className("row-title")).getText();
	    assertEquals(newArticleTitle,title);
	}
	
	public String[] getTodayTopic(){
		dr.get(qqURL);
		WebElement todayTop=dr.findElement(By.cssSelector("h3#todaytop>a"));
		String[] todayTopContent=new String[2];
		todayTopContent[0]=todayTop.getText();
		String todayTopicURL=todayTop.getAttribute("href");
		dr.get(todayTopicURL);
		todayTopContent[1]=dr.findElement(By.id("articleContent")).getAttribute("innerHTML").trim();
		return todayTopContent;
	}
	
	public void loginAdmin(){
		String username="admin";
		String password="123456";
		dr.get(loginURL);
		dr.findElement(By.id("user_login")).sendKeys(username);
		dr.findElement(By.id("user_pass")).sendKeys(password);
		dr.findElement(By.id("wp-submit")).click();
		
	}
	
	private void setContent(String content){
		String js="document.getElementById('content_ifr').contentWindow.document.body.innerHTML='";
		js+=content;
		js+="'";
		((JavascriptExecutor)dr).executeScript(js);
	
	}
	
	@After
	public void closeBrower(){
		this.dr.close();
		
	}

}

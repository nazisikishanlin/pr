package pers.article;

import static org.junit.Assert.assertEquals;

import org.junit.*;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DeleteArticle {
	private String loginURL="http://localhost/wordpress/wp-login.php";
	private String postArticleURL="http://localhost/wordpress/wp-admin/post-new.php";
	private String allArticleListURL="http://localhost/wordpress/wp-admin/edit.php";
	private WebDriver dr;
	
	@Before
	public void openBrower(){
		this.dr=new FirefoxDriver();
	}
	
	@Test
	public void deleteArticle(){
	String id=creatPostAndReturnItsID();
	dr.get(allArticleListURL);
	String postID = '#'+"post-" + id+ " a";
	String oldTitle=dr.findElement(By.cssSelector(postID)).getText();
    dr.findElement(By.cssSelector(postID)).click();
    String addTitle="123";
    dr.findElement(By.cssSelector("#titlewrap input")).sendKeys(addTitle);
    dr.findElement(By.id("publish")).click();
    dr.get(allArticleListURL);
    String editArticleTitle=dr.findElement(By.cssSelector(postID)).getText();
    oldTitle+=addTitle;
    assertEquals(editArticleTitle,oldTitle);    
	}

private String creatPostAndReturnItsID() {
	postArticleSuccess();
    String linkText = dr.findElement(By.id("sample-permalink")).getText();
    String[] chunk = linkText.split("=");
    return chunk[1];
}

public void postArticleSuccess(){
	loginAdmin();
	dr.get(postArticleURL);
	String ts = Long.toString(System.currentTimeMillis());
	String title="this is title"+ts;
	dr.findElement(By.id("title")).sendKeys(title);
	String content="this is content";
	setContent(content);
	dr.findElement(By.id("publish")).click();

	
}

private void setContent(String content){
	String js="document.getElementById('content_ifr').contentWindow.document.body.innerHTML='";
	js+=content;
	js+="'";
	((JavascriptExecutor)dr).executeScript(js);

}

public void loginAdmin(){
	String username="admin";
	String password="123456";
	dr.get(loginURL);
	dr.findElement(By.id("user_login")).sendKeys(username);
	dr.findElement(By.id("user_pass")).sendKeys(password);
	dr.findElement(By.id("wp-submit")).click();
	
}
	
	 @After
	public void closeBrowser(){
	    this.dr.close();
	}
	

}

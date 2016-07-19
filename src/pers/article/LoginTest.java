package info.itest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LoginTest {
    private WebDriver dr;
    private String loginPageURL = "http://localhost/wordpress/wp-login.php";
    private String qqURL = "http://www.qq.com";

    @Before
    public void openBrowser() {
        this.dr = new FirefoxDriver();
    }

    public void testLoginSuccess() {
        String username = "admin";
        String password = "admin";

        login(username, password);

        assertTrue(dr.getCurrentUrl().contains("wp-admin"));
        WebElement greetingLink = dr.findElement(By.cssSelector("#wp-admin-bar-my-account .ab-item"));
        assertTrue(greetingLink.getText().contains(username));
    }

    public void testCreatePostSuccess() {
        loginAsAdmin();
        gotoCreatePostPage();

        String ts = Long.toString(System.currentTimeMillis());
        String title = "this is my post" + ts;
        createPost(title, "this is content");

        gotoPostsListPage();
        String newCreatedPostTitle = dr.findElement(By.className("row-title")).getText();
        assertEquals(title, newCreatedPostTitle);
    }

    public void testCreatePostFromQQDailyHot() {
        String[] titleAndContent = getQQDailyHot();

        loginAsAdmin();
        gotoCreatePostPage();
        createPost(titleAndContent[0], titleAndContent[1]);
    }


    @Test(expected = NoSuchElementException.class)
    public void testDeletePost() {
        loginAsAdmin();

        String ts = Long.toString(System.currentTimeMillis());
        String title = "this is my post" + ts;
        String id = creatPostAndReturnItsID(title, "this is content");
        gotoPostsListPage();

        String postID = "post-" + id;
        WebElement row = dr.findElement(By.id(postID));
        Actions action = new Actions(dr);
        action.moveToElement(row).perform();

        row.findElement(By.className("submitdelete")).click();

        dr.findElement(By.id(postID));
    }

    private void createPost(String title, String content) {
        gotoCreatePostPage();
        dr.findElement(By.id("title")).sendKeys(title);
        setContent(content);
        dr.findElement(By.id("publish")).click();
    }

    private String creatPostAndReturnItsID(String title, String content) {
        createPost(title, content);
        String linkText = dr.findElement(By.id("sample-permalink")).getText();
        String[] chunk = linkText.split("=");
        return chunk[1];
    }

    private String[] getQQDailyHotHrefAndTitle() {
        dr.get(qqURL);
        WebElement titleElement = dr.findElement(By.cssSelector("#todaytop a"));
        String href = titleElement.getAttribute("href");
        String title = titleElement.getText();
        String[] result = new String[2];
        result[0] = href;
        result[1] = title;
        return result;
    }

    private String[] getQQDailyHot() {
        String[] titleAndHref = getQQDailyHotHrefAndTitle();
        dr.get(titleAndHref[0]);
        String content = dr.findElement(By.id("articleContent")).getAttribute("innerHTML").trim();

        String[] result = new String[2];
        result[0] = titleAndHref[1];
        result[1] = content;
        return result;
    }


    private void setContent(String content) {
        String js = "document.getElementById('content_ifr').contentWindow.document.body.innerHTML=\'";
        js += content;
        js += "\';";
        ((JavascriptExecutor)dr).executeScript(js);
    }

    public void loginAsAdmin() {
        String username = "admin";
        String password = "admin";

        login(username, password);
    }

    private void gotoCreatePostPage() {
       this.dr.get("http://localhost/wordpress/wp-admin/post-new.php");
    }

    private void gotoPostsListPage() {
        this.dr.get("http://localhost/wordpress/wp-admin/edit.php");
    }


    public void login(String username, String password) {
        dr.get(loginPageURL);
        dr.findElement(By.id("user_login")).sendKeys(username);
        dr.findElement(By.id("user_pass")).sendKeys(password);
        dr.findElement(By.id("wp-submit")).click();
    }

    @After
    public void closeBrowser() {
        this.dr.close();
    }

}


package com.selenium.course.test;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Set;

public class FirstTest {
    private WebDriver webDrv ;

    @BeforeTest
    public void setUp(){
        WebDriverManager.chromedriver().setup();
        webDrv = new ChromeDriver();
    }

    @AfterTest
    public void tearDown(){
     //   webDrv.close();
        webDrv.quit();
    }

    @Test
    public void logIn(){
        webDrv.get("https://www.saucedemo.com/");
        WebElement userNameInput = webDrv.findElement(By.id ("user-name"));
        userNameInput.sendKeys("standard_user");

        WebElement passwordInput = webDrv.findElement(By.cssSelector("[placeholder=Password]"));
        passwordInput.sendKeys("secret_sauce");

        WebElement logInBtn = webDrv.findElement(By.name("login-button"));
        logInBtn.click();
        WebElement productsLabel = webDrv.findElement(By.xpath("//span[text()='Products']"));
        WebElement shoppingCartLink = webDrv.findElement(By.xpath("//a[@class='shopping_cart_link']"));

        Assert.assertTrue(productsLabel.isDisplayed());
        Assert.assertTrue(shoppingCartLink.isDisplayed());
    }
    @Test
    public void logInInvalidPassword(){
        webDrv.get("https://www.saucedemo.com/");
        WebElement userNameInput = webDrv.findElement(By.id ("user-name"));
        userNameInput.sendKeys("standard_user");

        WebElement passwordInput = webDrv.findElement(By.cssSelector("[placeholder=Password]"));
        passwordInput.sendKeys("secret_sauce1");

        WebElement logInBtn = webDrv.findElement(By.name("login-button"));
        logInBtn.click();

        WebElement errorBtn = webDrv.findElement(By.className("error-button"));
        Assert.assertTrue(errorBtn.isDisplayed());
    }

    @Test
    public void sortPriceDescending(){
        webDrv.get("https://www.saucedemo.com/");
        WebElement userNameInput = webDrv.findElement(By.id ("user-name"));
        userNameInput.sendKeys("standard_user");

        WebElement passwordInput = webDrv.findElement(By.cssSelector("[placeholder=Password]"));
        passwordInput.sendKeys("secret_sauce");

        WebElement logInBtn = webDrv.findElement(By.name("login-button"));
        logInBtn.click();

        Select orderTypesList = new Select( webDrv.findElement(By.className("product_sort_container")));
        orderTypesList.selectByVisibleText("Price (high to low)");

        List<WebElement> itemsList = webDrv.findElements(By.className("inventory_item_price"));
        boolean returnValue=true;
        for (int i=0; i<itemsList.size()-1; i++){
            String price1 = itemsList.get(i).getText().substring(2);
            String price2=itemsList.get(i+1).getText().substring(2);
            if (Double.parseDouble(price2)>Double.parseDouble(price1)) {
                returnValue = false;
                break;
            }

        }
        Assert.assertTrue(returnValue);
    }
}

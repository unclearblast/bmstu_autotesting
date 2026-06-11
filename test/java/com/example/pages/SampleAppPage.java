package com.example.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SampleAppPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(name = "UserName")
    private WebElement usernameInput;

    @FindBy(name = "Password")
    private WebElement passwordInput;

    @FindBy(id = "login")
    private WebElement loginButton;

    @FindBy(id = "loginstatus")
    private WebElement loginStatus;

    public SampleAppPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get("http://uitestingplayground.com/sampleapp");
    }

    public void login(String username, String password) {
        usernameInput.clear();
        usernameInput.sendKeys(username);
        passwordInput.clear();
        passwordInput.sendKeys(password);
        loginButton.click();
        wait.until(ExpectedConditions.textToBePresentInElement(loginStatus, "Welcome, " + username + "!"));
    }

    public String getLoginStatus() {
        return loginStatus.getText();
    }

    public void logout() {
        loginButton.click();
        wait.until(ExpectedConditions.textToBePresentInElement(loginStatus, "User logged out."));
    }
}

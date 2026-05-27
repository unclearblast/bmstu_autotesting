package com.example.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DisabledInputPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(id = "enableButton")
    private WebElement enableButton;

    @FindBy(id = "inputField")
    private WebElement inputField;

    public DisabledInputPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get("http://uitestingplayground.com/disabledinput");
    }

    public void enableAndType(String text) {
        enableButton.click();
        wait.until(ExpectedConditions.elementToBeClickable(inputField));
        inputField.sendKeys(text);
    }

    public String getInputValue() {
        return inputField.getDomProperty("value");
    }
}

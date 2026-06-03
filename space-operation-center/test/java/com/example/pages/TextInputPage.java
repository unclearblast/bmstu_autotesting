package com.example.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TextInputPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(id = "newButtonName")
    private WebElement inputField;

    @FindBy(id = "updatingButton")
    private WebElement updatingButton;

    public TextInputPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get("http://uitestingplayground.com/textinput");
    }

    public void setNewButtonName(String name) {
        inputField.clear();
        inputField.sendKeys(name);
        updatingButton.click();
        wait.until(ExpectedConditions.textToBePresentInElement(updatingButton, name));
    }

    public String getButtonText() {
        return updatingButton.getText();
    }
}

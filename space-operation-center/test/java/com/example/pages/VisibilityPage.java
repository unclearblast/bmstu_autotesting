package com.example.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class VisibilityPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(id = "hideButton")
    private WebElement hideButton;

    @FindBy(id = "removedButton")
    private WebElement removedButton;

    @FindBy(id = "zeroWidthButton")
    private WebElement zeroWidthButton;

    @FindBy(id = "overlappedButton")
    private WebElement overlappedButton;

    @FindBy(id = "transparentButton")
    private WebElement transparentButton;

    @FindBy(id = "invisibleButton")
    private WebElement invisibleButton;

    @FindBy(id = "notdisplayedButton")
    private WebElement notdisplayedButton;

    @FindBy(id = "offscreenButton")
    private WebElement offscreenButton;

    public VisibilityPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get("http://uitestingplayground.com/visibility");
    }

    public void clickHide() {
        hideButton.click();
        // Подождём, пока кнопка "Removed" исчезнет из DOM
        wait.until(ExpectedConditions.invisibilityOf(removedButton));
    }

    public boolean isRemovedButtonPresent() {
        try {
            return driver.findElements(org.openqa.selenium.By.id("removedButton")).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isZeroWidthButtonVisible() {
        return zeroWidthButton.isDisplayed();
    }

    public boolean isOverlappedButtonClickable() {
        try {
            overlappedButton.click();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isTransparentButtonDisplayed() {
        return transparentButton.isDisplayed();
    }

    public boolean isInvisibleButtonDisplayed() {
        return invisibleButton.isDisplayed();
    }

    public boolean isNotdisplayedButtonDisplayed() {
        return notdisplayedButton.isDisplayed();
    }

    public boolean isOffscreenButtonDisplayed() {
        return offscreenButton.isDisplayed();
    }
}

package com.example.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class FramesPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(id = "frame1")
    private WebElement frame1;

    @FindBy(xpath = "//a[contains(text(),'Click me')]")   // внутри фрейма
    private WebElement clickMeLink;

    @FindBy(id = "div1") // элемент внутри фрейма, который меняет текст
    private WebElement resultDiv;

    public FramesPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get("http://uitestingplayground.com/frames");
    }

    public void clickButtonInFrame1() {
        driver.switchTo().frame(frame1);
        clickMeLink.click();
        wait.until(ExpectedConditions.textToBePresentInElement(resultDiv, "This is frame 1"));
        driver.switchTo().defaultContent();
    }

    public String getFrameTextAfterClick() {
        driver.switchTo().frame(frame1);
        String text = resultDiv.getText();
        driver.switchTo().defaultContent();
        return text;
    }
}

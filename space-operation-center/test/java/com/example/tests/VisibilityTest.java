package com.example.tests;

import com.example.base.BaseTest;
import com.example.pages.VisibilityPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Feature("Visibility")
public class VisibilityTest extends BaseTest {

    @Test
    @Description("Скрытие элементов и проверка их видимости после нажатия Hide")
    public void testVisibilityAfterHide() {
        VisibilityPage visibility = new VisibilityPage(driver);
        visibility.open();
        visibility.clickHide();

        // Removed – должен исчезнуть из DOM
        assertFalse(visibility.isRemovedButtonPresent(), "Removed button should not be present");

        // Zero Width – присутствует, но не отображается
        assertFalse(visibility.isZeroWidthButtonVisible(), "Zero Width button should not be displayed");

        // Overlapped – присутствует, но перекрыт и недоступен для клика
        assertFalse(visibility.isOverlappedButtonClickable(), "Overlapped button should not be clickable");

        // Opacity 0 – отображается, но прозрачный (isDisplayed() вернёт true)
        assertTrue(visibility.isTransparentButtonDisplayed(), "Transparent button is displayed but fully transparent");

        // Visibility Hidden – не отображается
        assertFalse(visibility.isInvisibleButtonDisplayed(), "Visibility Hidden button should not be displayed");

        // Display None – не отображается
        assertFalse(visibility.isNotdisplayedButtonDisplayed(), "Display None button should not be displayed");

        // Offscreen – считается отображаемым, но находится за пределами видимой области
        // isDisplayed() возвращает true, проверим косвенно
        assertTrue(visibility.isOffscreenButtonDisplayed(), "Offscreen button is displayed but out of viewport");
    }
}

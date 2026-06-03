package com.example.tests;

import com.example.base.BaseTest;
import com.example.pages.FramesPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Feature("Frames")
public class FramesTest extends BaseTest {

    @Test
    @Description("Переключение в iframe и клик по ссылке с проверкой текста")
    public void testFrameInteraction() {
        FramesPage frames = new FramesPage(driver);
        frames.open();
        frames.clickButtonInFrame1();
        String text = frames.getFrameTextAfterClick();
        assertTrue(text.contains("This is frame 1"));
    }
}

package com.example.tests;

import com.example.base.BaseTest;
import com.example.pages.DisabledInputPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Feature("Disabled Input")
public class DisabledInputTest extends BaseTest {

    @Test
    @Description("Ожидание активации поля ввода и ввод текста после задержки")
    public void testEnableAndInputText() {
        DisabledInputPage disabledInput = new DisabledInputPage(driver);
        disabledInput.open();
        String sampleText = "Hello World";
        disabledInput.enableAndType(sampleText);
        assertEquals(sampleText, disabledInput.getInputValue());
    }
}

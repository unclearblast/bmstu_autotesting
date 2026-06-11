package com.example.tests;

import com.example.base.BaseTest;
import com.example.pages.TextInputPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Feature("Text Input")
public class TextInputTest extends BaseTest {

    @Test
    @Description("Ввод текста и проверка, что имя кнопки изменилось")
    public void testButtonNameChanges() {
        TextInputPage textInput = new TextInputPage(driver);
        textInput.open();
        String newName = "New Button Name";
        textInput.setNewButtonName(newName);
        assertEquals(newName, textInput.getButtonText());
    }
}

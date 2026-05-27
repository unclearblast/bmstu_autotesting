package com.example.tests;

import com.example.base.BaseTest;
import com.example.pages.SampleAppPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Feature("Sample App")
public class SampleAppTest extends BaseTest {

    @Test
    @Description("Проверка успешной авторизации с корректными учётными данными")
    public void testSuccessfulLogin() {
        SampleAppPage sampleApp = new SampleAppPage(driver);
        sampleApp.open();
        sampleApp.login("testuser", "pwd");
        assertEquals("Welcome, testuser!", sampleApp.getLoginStatus());
    }
}

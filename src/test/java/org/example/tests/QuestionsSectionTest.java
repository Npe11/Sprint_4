package org.example.tests;

import org.example.pages.MainPage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class QuestionsSectionTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private MainPage mainPage;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "/opt/homebrew/bin/chromedriver");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        driver.manage().window().maximize();
        driver.get("https://qa-scooter.praktikum-services.ru/");
        mainPage = new MainPage(driver, wait);
    }

    @Test
    public void testAllAccordionQuestions() {
        int count = mainPage.getAccordionCount();
        for (int i = 0; i < count; i++) {
            mainPage.clickAccordionQuestion(i);
            String answerText = mainPage.getAccordionAnswerText(i);
            Assert.assertFalse("Ответ для вопроса " + (i + 1) + " не должен быть пустым", answerText.trim().isEmpty());
        }
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}

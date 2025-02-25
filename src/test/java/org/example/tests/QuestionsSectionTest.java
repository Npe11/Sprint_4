package org.example.tests;

import org.example.pages.MainPage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class QuestionsSectionTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private MainPage mainPage;

    // Индекс вопроса в аккордеоне
    @Parameterized.Parameter(0)
    public int questionIndex;

    // Ожидаемый текст ответа на вопрос
    @Parameterized.Parameter(1)
    public String expectedAnswerText;

    // Параметры теста: для каждого вопроса задаем индекс и ожидаемый текст ответа
    @Parameterized.Parameters(name = "FAQ вопрос {0}")
    public static Collection<Object[]> testData() {
        return Arrays.asList(new Object[][]{
                {0, "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
                {1, "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."},
                {2, "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."},
                {3, "Только начиная с завтрашнего дня. Но скоро станем расторопнее."},
                {4, "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."},
                {5, "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."},
                {6, "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."},
                {7, "Да, обязательно. Всем самокатов! И Москве, и Московской области."}
        });
    }

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "/opt/homebrew/bin/chromedriver");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        driver.manage().window().maximize();
        driver.get(MainPage.BASE_URL);
        mainPage = new MainPage(driver, wait);
    }

    @Test
    public void testAccordionQuestionAnswer() {
        mainPage.clickAccordionQuestion(questionIndex);
        String actualAnswerText = mainPage.getAccordionAnswerText(questionIndex);
        Assert.assertEquals("Некорректный текст ответа для вопроса " + (questionIndex + 1),
                expectedAnswerText, actualAnswerText.trim());
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}

package org.example.tests;

import java.util.Arrays;
import java.util.Collection;

import org.example.pages.MainPage;
import org.example.pages.OrderPage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

@RunWith(Parameterized.class)
public class OrderScooterTest {
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;

    private WebDriver driver;
    private WebDriverWait wait;
    private MainPage mainPage;
    private OrderPage orderPage;

    public OrderScooterTest(String firstName, String lastName, String address, String metroStation, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testData() {
        return Arrays.asList(new Object[][] {
                {"Иван", "Иванов", "ул. Ленина, 1", "Бульвар Рокоссовского", "12345678902"},
                {"Петр", "Петров", "ул. Гагарина, 2", "Черкизовская", "09876543213"}
        });
    }

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "/opt/homebrew/bin/chromedriver");
        driver = new ChromeDriver();
//        System.setProperty("webdriver.gecko.driver", "/opt/homebrew/bin/geckodriver");
//        FirefoxOptions options = new FirefoxOptions();
//        options.setBinary("/Applications/Firefox.app/Contents/MacOS/firefox");
//        driver = new FirefoxDriver(options);

        driver.manage().window().maximize();
        driver.get("https://qa-scooter.praktikum-services.ru/");
        wait = new WebDriverWait(driver, 10);
        mainPage = new MainPage(driver, wait);
        orderPage = new OrderPage(driver);
    }

    @Test
    public void testOrderFlowUsingTopButton() {
        mainPage.clickOrderButtonTop();
        orderPage.fillPersonalData(firstName, lastName, address, metroStation, phone);
        orderPage.clickNextButton();
        orderPage.fillRentalInfo("01.03.2025", "сутки", true, false, "Комментарий для курьера");
        orderPage.submitFinalOrder();
        orderPage.confirmOrderInModal();
        Assert.assertTrue("Сообщение об успешном заказе не отображается", orderPage.isOrderSuccessMessageDisplayed());
    }

    @Test
    public void testOrderFlowUsingBottomButton() {
        mainPage.clickOrderButtonBottom();
        orderPage.fillPersonalData(firstName, lastName, address, metroStation, phone);
        orderPage.clickNextButton();
        orderPage.fillRentalInfo("01.03.2025", "сутки", true, false, "Комментарий для курьера");
        orderPage.submitFinalOrder();
        orderPage.confirmOrderInModal();
        Assert.assertTrue("Сообщение об успешном заказе не отображается", orderPage.isOrderSuccessMessageDisplayed());
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

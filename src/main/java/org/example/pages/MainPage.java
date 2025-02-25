package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MainPage {

    public static final String BASE_URL = "https://qa-scooter.praktikum-services.ru/";

    // Верхняя кнопка "Заказать"
    private final By orderButtonTop = By.cssSelector("button.Button_Button__ra12g");

    // Нижняя кнопка "Заказать"
    private final By orderButtonBottom = By.cssSelector("button.Button_Button__ra12g.Button_Middle__1CSJM");

    // Кнопки раскрывающегося списка вопросов
    private final By accordionButtons = By.cssSelector("div.accordion__button");

    // Ответы на вопросы
    private final By accordionPanels = By.cssSelector("div.accordion__panel");

    private final WebDriver driver;
    private final WebDriverWait wait;

    public MainPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    // Количество вопросов раскрывающегося списка
    public int getAccordionCount() {
        List<WebElement> buttons = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(accordionButtons));
        return buttons.size();
    }

    // Клик по верхней кнопке "Заказать"
    public void clickOrderButtonTop() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(orderButtonTop));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);
        wait.until(ExpectedConditions.elementToBeClickable(button));
        button.click();
    }

    // Клик по нижней кнопке "Заказать"
    public void clickOrderButtonBottom() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(orderButtonBottom));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);
        wait.until(ExpectedConditions.elementToBeClickable(button));
        button.click();
    }

    // Клик по вопросу раскрывающегося списка
    public void clickAccordionQuestion(int index) {
        List<WebElement> buttons = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(accordionButtons));
        if (index >= 0 && index < buttons.size()) {
            WebElement button = buttons.get(index);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);
            wait.until(ExpectedConditions.elementToBeClickable(button));
            button.click();
        }
    }

    // Получение текста ответа на вопросы
    public String getAccordionAnswerText(int index) {
        List<WebElement> panels = driver.findElements(accordionPanels);
        if (index >= 0 && index < panels.size()) {
            WebElement panel = panels.get(index);
            wait.until(ExpectedConditions.visibilityOf(panel));
            return panel.getText();
        }
        return "";
    }
}

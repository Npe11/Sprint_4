package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OrderPage {
    //  Шаг 1: Личные данные
    private final By firstNameField = By.xpath("//input[@placeholder='* Имя']");
    private final By lastNameField = By.xpath("//input[@placeholder='* Фамилия']");
    private final By addressField = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    private final By phoneField = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");
    // Кнопка "Далее"
    private final By nextButton = By.cssSelector("button.Button_Button__ra12g.Button_Middle__1CSJM");

    //  Шаг 2: Данные об аренде
    private final By deliveryDateField = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    private final By rentalPeriodDropdown = By.cssSelector("div.Dropdown-control");
    // Срок аренды
    private final String rentalPeriodOptionXpath = "//div[@role='option' and normalize-space()='%s']";
    private final By blackCheckbox = By.id("black");
    private final By greyCheckbox = By.id("grey");
    private final By courierCommentField = By.xpath("//input[@placeholder='Комментарий для курьера']");
    // кнопка "Заказать"
    private final By finalOrderButton = By.xpath("//div[contains(@class, 'Order_Buttons__1xGrp')]//button[normalize-space()='Заказать']");

    //  Шаг 3: Модальное окно
    // Модальное окно подтверждения заказа, клик на "Да"
    private final By modalYesButton = By.xpath("//div[contains(@class, 'Order_Modal__YZ-d3')]//button[normalize-space()='Да']");
    // Подтверждение заказа
    private final By orderSuccessHeader = By.xpath("//div[contains(@class, 'Order_ModalHeader') and contains(., 'Заказ оформлен')]");

    // Локатор для станции метро в виде шаблона
    private final String metroStationOptionXpathTemplate =
            "//ul[contains(@class, 'select-search__options')]//div[contains(@class, 'Order_Text__2broi') and normalize-space()='%s']";

    private final WebDriver driver;

    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    // Заполнение личных данных: имя, фамилия, адрес, выбор станции метро и телефон
    public void fillPersonalData(String firstName, String lastName, String address, String metroStation, String phone) {
        driver.findElement(firstNameField).sendKeys(firstName);
        driver.findElement(lastNameField).sendKeys(lastName);
        driver.findElement(addressField).sendKeys(address);
        selectMetroStation(metroStation);
        driver.findElement(phoneField).sendKeys(phone);
    }

    // Выбор станции метро
    public void selectMetroStation(String metroStation) {
        WebElement metroInput = driver.findElement(By.xpath("//input[@placeholder='* Станция метро']"));
        metroInput.click();
        By metroStationOptionLocator = By.xpath(String.format(metroStationOptionXpathTemplate, metroStation));
        driver.findElement(metroStationOptionLocator).click();
    }

    public void clickNextButton() {
        driver.findElement(nextButton).click();
    }

    // Заполнение данных об аренде: дата доставки, срок аренды, выбор цвета и комментарий
    public void fillRentalInfo(String deliveryDate, String rentalPeriod, boolean selectBlack, boolean selectGrey, String courierComment) {
        WebElement dateField = driver.findElement(deliveryDateField);
        dateField.sendKeys(deliveryDate);
        dateField.sendKeys(Keys.ENTER);
        WebDriverWait localWait = new WebDriverWait(driver, 5);
        localWait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("react-datepicker__day-names")));

        driver.findElement(rentalPeriodDropdown).click();
        driver.findElement(By.xpath(String.format(rentalPeriodOptionXpath, rentalPeriod))).click();

        if (selectBlack) {
            WebElement black = driver.findElement(blackCheckbox);
            if (!black.isSelected()) {
                black.click();
            }
        }
        if (selectGrey) {
            WebElement grey = driver.findElement(greyCheckbox);
            if (!grey.isSelected()) {
                grey.click();
            }
        }
        driver.findElement(courierCommentField).sendKeys(courierComment);
    }

    public void submitFinalOrder() {
        driver.findElement(finalOrderButton).click();
    }

    public void confirmOrderInModal() {
        driver.findElement(modalYesButton).click();
    }

    public boolean isOrderSuccessMessageDisplayed() {
        return driver.findElement(orderSuccessHeader).isDisplayed();
    }
}

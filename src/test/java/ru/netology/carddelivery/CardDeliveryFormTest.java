package ru.netology.carddelivery;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryFormTest {
    private int daysOffset = 12;

    private String getOrderDate(int daysOffset) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return dateFormat.format(LocalDateTime.now().plusDays(daysOffset));
    }

    @Test
    public void shouldCreditCardDeliveryOrder() {

        open("http://localhost:9999");

        String dateOrder = getOrderDate(daysOffset);
        SelenideElement element = $("form");
        SelenideElement fieldDate = element.$("[data-test-id=date] input");
        element.$("[data-test-id=city] input").setValue("Уфа");
        fieldDate.doubleClick();
        fieldDate.sendKeys(Keys.BACK_SPACE);
        fieldDate.setValue(dateOrder);
        element.$("[data-test-id=name] input").setValue("Кара-Мурза Абдулла Каримович");
        element.$("[data-test-id=phone] input").setValue("+7917357236577");
        element.$("[data-test-id=agreement]").click();
        element.$$("button").find(exactText("Забронировать")).click();

        $(withText("Успешно!")).waitUntil(visible, 15000);
        $(byText("Встреча успешно забронирована на")).shouldBe(visible);
        $(byText(dateOrder)).shouldBe(visible);
    }
}

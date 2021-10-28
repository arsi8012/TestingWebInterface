package ru.netology;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class TestWebInterface {

    @BeforeEach
    void setUp() {
        SelenideElement form = $("[class='form form_size_m form_theme_alfa-on-white']");
    }

    @Test
    void shouldSuccessSubmissionOfForm() {
        open("http://localhost:9999");
        $("[data-test-id=name] input").setValue("Алиев Гейдар");
        $("[data-test-id=phone] input").setValue("+79472512638");
        $("[data-test-id=agreement]").click();
        $("button").click();
        $("[data-test-id='order-success']").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldInputNameOnLatinLetters() {
        open("http://localhost:9999");
        $("[data-test-id=name] input").setValue("Aliev Geidar");
        $("[data-test-id=phone] input").setValue("+79472512638");
        $("[data-test-id=agreement]").click();
        $("button").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldInputWrongNumber() {
        open("http://localhost:9999");
        $("[data-test-id=name] input").setValue("Алиев Гейдар");
        $("[data-test-id=phone] input").setValue("+7947251263");
        $("[data-test-id=agreement]").click();
        $("button").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldWithoutInstallCheckbox() {
        open("http://localhost:9999");
        $("[data-test-id=name] input").setValue("Алиев Гейдар");
        $("[data-test-id=phone] input").setValue("+79472512638");
        $("button").click();
        $("[data-test-id=agreement].input_invalid .checkbox__text").shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй"));
    }

    @Test
    void shouldFormWithoutName() {
        open("http://localhost:9999");
        $("[data-test-id=name] input").setValue("");
        $("[data-test-id=phone] input").setValue("+7947251263");
        $("[data-test-id=agreement]").click();
        $("button").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldBlankFieldsInputPhone() {
        open("http://localhost:9999");
        $("[data-test-id=name] input").setValue("Алиев Гейдар");
        $("[data-test-id=phone] input").setValue("");
        $("[data-test-id=agreement]").click();
        $("button").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }
}
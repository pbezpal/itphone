package Pages;

import HelperClasses.StepReport;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    public LoginPage(){}

    @Step(value = "Проверяем, есть ли форма авторизации")
    public static SelenideElement isLoginForm(){
        return $("div#dialog-authentication-form");
    }

    @Step(value = "Авторизуемся на сервере под пользователем: {login}")
    public static void loginOnServer(String login, String password){
        StepReport.stepTest("Вводим логин - " + login);
        $(byName("name")).clear();
        $(byName("name")).sendKeys(login);
        StepReport.stepTest("Вводим пароль - " + password);
        $(byName("password")).clear();
        $(byName("password")).sendKeys(password);
        StepReport.stepTest("Нажимаем кнопку 'Войти'");
        $("button").click();
    }
}

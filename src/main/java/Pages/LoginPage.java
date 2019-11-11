package Pages;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    private static final SelenideElement elementFormLogin = $(By.xpath("//div[@class='ui-dialog ui-widget ui-widget-content ui-corner-all ui-front ui-dialog-buttons']"));
    private static final SelenideElement elementInputLogin = $(By.xpath("//input[@id='name']"));
    private static final SelenideElement elementInputPassword = $(By.xpath("//input[@id='password']"));
    private static final SelenideElement elementButtonEnter = $(By.xpath("//button"));

    public LoginPage(){}

    @Step(value = "Вводим логин {login}")
    public LoginPage sendLogin(String login){
        elementInputLogin.clear();
        elementInputLogin.sendKeys(login);

        return this;
    }

    @Step(value = "Вводим пароль {password}")
    public LoginPage sendPassword(String password){
        elementInputPassword.clear();
        elementInputPassword.sendKeys(password);

        return this;
    }

    @Step(value = "Нажимаем кнопку 'Войти'")
    public void clickButton(){ elementButtonEnter.click(); }

    public MonitoringPage loginOnServer(String login, String password){
        try{
            elementFormLogin.isEnabled();
            sendLogin(login);
            sendPassword(password);
            clickButton();
            return new MonitoringPage();
        }catch (ElementNotFound element){
            return null;
        }
    }
}

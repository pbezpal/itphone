package Pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ex.ElementNotFound;

import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    public LoginPage(){}

    public static boolean isLoginForm(){
        try {
            $("div#dialog-authentication-form").shouldBe(Condition.visible);
        }catch (ElementNotFound elementNotFound){
            return false;
        }
        return true;
    }

    public static void loginOnServer(String login, String password){
        $(byName("name")).clear();
        $(byName("name")).sendKeys(login);
        $(byName("password")).clear();
        $(byName("password")).sendKeys(password);
        $("button").click();
    }
}

package TestsLoginOnServer;

import AnnotationsTests.LoginTests.EpicLoginTests;
import AnnotationsTests.LoginTests.FeatureLoginTests;
import DataTests.DataLogin;
import Pages.LoginPage;
import Pages.MonitoringPage;
import RecourcesTests.AfterAllTests;
import RecourcesTests.BeforeAllTests;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.qameta.allure.model.Status;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static DataTests.DataLogin.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@EpicLoginTests
@FeatureLoginTests
@ExtendWith(BeforeAllTests.class)
public class Test1LoginAdmin {
    private LoginPage loginPage;

    @Story(value = "Авторизация под Администратором")
    @Description(value = "Тестирование авторизации на сервер, под учётной записью 'Администртаор'")
    @Test
    void test_Login_Admin(){
        if(MonitoringPage.isCheckUser(webLoginAdmin)) Allure.step("Пользователь " + webLoginAdmin + " успешно авторизовался на сервере", Status.PASSED);
        else {
            loginPage = MonitoringPage.clickButtonLogout();
            assertTrue(loginPage.loginOnServer(webLoginAdmin, webPassword).isCheckLogin(), "Не удалось залогиниться под Администратором");
        }
    }
}

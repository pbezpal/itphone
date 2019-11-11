package TestsLoginOnServer;

import AnnotationsTests.LoginTests.EpicLoginTests;
import AnnotationsTests.LoginTests.FeatureLoginTests;
import Pages.LoginPage;
import Pages.MonitoringPage;
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
public class Test2LoginOper {
    private static LoginPage loginPage;
    private static MonitoringPage monitoringPage;

    @Story(value = "Авторизация под Оператором")
    @Description(value = "Тестирование авторизации на сервер, под учётной записью 'Оператор'")
    @Test
    @Disabled
    void test_Login_Oper(){
        if(MonitoringPage.isCheckUser(webLoginOper)) Allure.step("Пользователь " + webLoginOper + " успешно авторизовался на сервере", Status.PASSED);
        else {
            loginPage = MonitoringPage.clickButtonLogout();
            monitoringPage = loginPage.loginOnServer(webLoginOper, webPassword);
            assertTrue(monitoringPage.isCheckLogin(), "Не удалось залогиниться под Оператором");
            assertTrue(monitoringPage.isCheckLinkSettingsSU(), "Ссылка 'Настройки СУ' доступна");
        }
    }
}

package Test_1_Providers;

import AnnotationsTests.ServicesTests.EpicServicesTests;
import AnnotationsTests.ServicesTests.FeatureProviderMX1000;
import HelperClasses.SSHManager;
import HelperClasses.ScreenshotTests;
import Pages.MonitoringPage;
import Pages.Providers.KATSPage;
import Pages.Providers.ProvidersPage;
import Pages.SipServerPage;
import RecourcesTests.BeforeEachTests;
import RecourcesTests.TestRules;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;


import static DataTests.DataLogin.urlServer;
import static DataTests.DataSipServer.*;
import static DataTests.Providers.DataProviderKATS.*;
import static DataTests.Providers.Providers.linkProvidersPage;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@EpicServicesTests
@FeatureProviderMX1000
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
@ExtendWith({TestRules.class, BeforeEachTests.class})
public class Test_2_MX1000 {

    private KATSPage katsPage = null;
    private SipServerPage sipServerPage;

    @Story(value = "Тестирование настройки SIP-сервера")
    @Description(value = "Проверяем, что через СУ настраивается SIP-сервер, настройки сохраняются на сервере и корректно отображается статус SIP сервера")
    @Test
    void test_A_Settings_SIP_Server() {
        sipServerPage = (SipServerPage) MonitoringPage.openSectionWEB(linkSipServerPage);
        assertTrue(sipServerPage.setSettingsSIPServer(urlServer, sipServerPort, turnPortMin, turnPortMax), "Ошибка при настройке SIP сервера");
        assertTrue(sipServerPage.isCheckSettingsSipServer(), "Настройки SIP сервера не сохранились на сервере");
    }

    @Story(value = "Добавление провайдетра MX1000")
    @Description(value = "Проверяем, что добавляется провайдет MX1000 типа КАТС")
    @Test
    void test_B_Add_Provider_MX1000() {
        if( ! ProvidersPage.isCheckProviderPage().isDisplayed()) katsPage = (KATSPage) MonitoringPage.openSectionWEB(linkProvidersPage, KATS);
        if( katsPage == null ) katsPage = KATSPage.getInstance();
        if(SSHManager.isCheckQuerySSH(commandStatusSIPServer)) {
            assertTrue(katsPage.addMX1000(MX1000, IPAddress, usernameMX1000, passwordMX1000, dialplanMX1000, delayRegistration), "Не удалось добавить провайдер MX1000");
            if (!katsPage.isSelectProvider()) failedTestWithScreenshot("Провайдер MX1000 не найден в базе данных MySql", false);
            if (!katsPage.isSelectDialplan()) failedTestWithScreenshot("Шаблон номера для MX1000 не найден в базе данных MySql", false);
        }else failedTestWithScreenshot("SIP сервер не настроен. Не могу добавить провайдер MX1000", false);
        if( ! katsPage.isMX1000()) failedTestWithScreenshot("Сервер MX1000 не установлен на сервер", false);
    }

    void failedTestWithScreenshot(String message, boolean screenshot) {
        fail(message);
        if(screenshot) ScreenshotTests.screenshot();
    }
}

package Test_Providers;

import AnnotationsTests.ServicesTests.EpicServicesTests;
import AnnotationsTests.ServicesTests.FeatureProviderMX1000;
import HelperClasses.SSHManager;
import HelperClasses.ScreenshotTests;
import Pages.MonitoringPage;
import Pages.Providers.KATSPage;
import Pages.Providers.ProvidersPage;
import Pages.SipServerPage;
import RecourcesTests.BeforeAllTests;
import RecourcesTests.TestRules;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.qameta.allure.model.Status;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static DataTests.LOGIN.IP_SERVER;
import static DataTests.OPENSIPS.*;
import static DataTests.Providers.PROVIDER_MX1000.*;
import static DataTests.Providers.PROVIDERS.PROVIDERS_ITEM_MENU;
import static Pages.MonitoringPage.isCheckNotVisibleElement;
import static org.junit.jupiter.api.Assertions.assertTrue;

@EpicServicesTests
@FeatureProviderMX1000
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
@ExtendWith({TestRules.class, BeforeAllTests.class})
public class Test_B_MX1000 {

    private KATSPage katsPage = null;
    private SipServerPage sipServerPage;
    private boolean TEST_STATUS;
    private String TEST_MESSAGE;

    @BeforeEach
    void setUp(){
        TEST_STATUS = true;
        TEST_MESSAGE = "";
    }

    @Story(value = "Тестирование настройки SIP-сервера")
    @Description(value = "Проверяем, что через СУ настраивается SIP-сервер, настройки сохраняются на сервере и корректно отображается статус SIP сервера")
    @Test
    void test_ADD_MX1000_SIP_SERVER() {
        if( isCheckNotVisibleElement() ) sipServerPage = (SipServerPage) MonitoringPage.openSectionWEB(OPENSIPS_ITEM_MENU);
        assertTrue(sipServerPage.setSettingsSIPServer(IP_SERVER, OPENSIPS_SERVER_PORT, OPENSIPS_TURN_PORT_MIN, OPENSIPS_TURN_PORT_MAX), "Ошибка при настройке SIP сервера");
        assertTrue(sipServerPage.isCheckSettingsSipServer(), "Настройки SIP сервера не сохранились на сервере");
    }

    @Story(value = "Добавление провайдетра MX1000")
    @Description(value = "Проверяем, что добавляется провайдет MX1000 типа КАТС")
    @Test
    void test_ADD_PROVIDER_MX1000() {
        if( isCheckNotVisibleElement() && ! ProvidersPage.isCheckProviderPage().isDisplayed()) katsPage = (KATSPage) MonitoringPage.openSectionWEB(PROVIDERS_ITEM_MENU, MX1000_TYPE_PROVIDER);
        if( katsPage == null ) katsPage = KATSPage.getInstance();
        if(SSHManager.isCheckQuerySSH(OPENSIPS_STATUS)) {
            assertTrue(katsPage.addMX1000(MAX1000_NAME, MX1000_HOST, MX1000_USERNAME, MX1000_PASSWORD, MX1000_DIALPLAN, MX1000_DELAY_REGISTRATION), "Не удалось добавить провайдер MX1000");
            if (!katsPage.isSelectProvider()) failedTestWithScreenshot("Провайдер MX1000 не найден в базе данных MySql", false);
            if (!katsPage.isSelectDialplan()) failedTestWithScreenshot("Шаблон номера для MX1000 не найден в базе данных MySql", false);
        }else failedTestWithScreenshot("SIP сервер не настроен. Не могу добавить провайдер MX1000", false);
        if( ! katsPage.isMX1000()) failedTestWithScreenshot("Сервер MX1000 не установлен на сервер", false);
    }

    void failedTestWithScreenshot(String message, boolean screenshot) {
        Allure.step(message, Status.FAILED);
        TEST_STATUS = false;
        TEST_MESSAGE = TEST_MESSAGE + "; " + message;
        if(screenshot) ScreenshotTests.screenshot();
    }

    @AfterEach
    void tearDown(){
        assertTrue(TEST_STATUS, TEST_MESSAGE);
    }
}

package MX1000;

import AnnotationsTests.ServicesTests.EpicServicesTests;
import AnnotationsTests.ServicesTests.FeatureProviderMX1000;
import HelperClasses.SSHManager;
import Pages.MonitoringPage;
import Pages.Providers.DX500Page;
import Pages.Providers.KATSPage;
import Pages.Providers.ProvidersPage;
import Pages.SipServerPage;
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
import static Pages.MonitoringPage.*;
import static RecourcesTests.BeforeSettingsTests.StartTests;
import static com.codeborne.selenide.Selenide.refresh;
import static java.time.Duration.ofSeconds;
import static org.junit.jupiter.api.Assertions.*;

@EpicServicesTests
@FeatureProviderMX1000
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
@ExtendWith(TestRules.class)
public class Test_MX1000 {

    private KATSPage katsPage = null;
    private SipServerPage sipServerPage;
    private boolean TEST_STATUS;
    private String TEST_MESSAGE;
    private static boolean screenshot;

    @Story(value = "Проверяем, настроен ли сервер SIP")
    @Description(value = "Проверяем, настроен ли сервер SIP и если сервер SIP не натсроен, настраиваем его")
    @BeforeEach
    void setUp(){
        StartTests();
        TEST_STATUS = true;
        TEST_MESSAGE = "";
        screenshot = false;
        if ( ! SSHManager.isCheckQuerySSH(OPENSIPS_STATUS)) settings_Opensips();
    }

    @Story(value = "Добавление провайдетра MX1000")
    @Description(value = "Проверяем, что добавляется провайдет MX1000 типа КАТС")
    @Disabled
    @Test
    void test_ADD_PROVIDER_MX1000() {
        assertTimeout(ofSeconds(600), () -> {
            if (isCheckNotVisibleElement() && !ProvidersPage.isCheckProviderPage().isDisplayed())
                katsPage = (KATSPage) MonitoringPage.openSectionWEB(PROVIDERS_ITEM_MENU, MX1000_TYPE_PROVIDER);
            if (katsPage == null) katsPage = KATSPage.getInstance();
            if( ! isCheckNotVisibleDownload()) fail("Невозможно продолжать тестирование, СУ недоступно", new Exception("DOWNLOAD"));
            katsPage.addMX1000(MX1000_NAME, MX1000_HOST, MX1000_USERNAME, MX1000_PASSWORD, MX1000_DIALPLAN, MX1000_DELAY_REGISTRATION);
            if( ! isCheckNotVisibleDownload()) fail("Невозможно продолжать тестирование, СУ недоступно", new Exception("DOWNLOAD"));
            assertTrue(katsPage.isCheckProvider(MX1000_NAME), "Не удалось добавить провайдер " + MX1000_NAME);
            if (!katsPage.isSelectProvider())
                failedTestWithScreenshot("Провайдер MX1000 не найден в базе данных MySql", false);
            if (!katsPage.isSelectDialplan())
                failedTestWithScreenshot("Шаблон номера для MX1000 не найден в базе данных MySql", false);
            if (!katsPage.isMX1000()) failedTestWithScreenshot("Сервер MX1000 не установлен на сервер", false);
        }, () -> "Время теста больше 5 минут");
    }

    void settings_Opensips(){
        sipServerPage = (SipServerPage) MonitoringPage.openSectionWEB(OPENSIPS_ITEM_MENU);
        if( ! isCheckNotVisibleDownload()) fail("Невозможно продолжать тестирование, СУ недоступно", new Exception("DOWNLOAD"));
        assertTrue(sipServerPage.setSettingsSIPServer(IP_SERVER, OPENSIPS_SERVER_PORT, OPENSIPS_TURN_PORT_MIN, OPENSIPS_TURN_PORT_MAX), "Ошибка при настройке SIP сервера");
        assertTrue(sipServerPage.isCheckSettingsSipServer(), "Настройки SIP сервера не сохранились на сервере");
    }

    void failedTestWithScreenshot(String message, boolean screen) {
        Allure.step(message, Status.FAILED);
        TEST_STATUS = false;
        TEST_MESSAGE = TEST_MESSAGE + "\n" + message;
        screenshot = screen;
    }

    @AfterEach
    void tearDown(){
        if( ! TEST_STATUS){
            fail(TEST_MESSAGE, new Exception(String.valueOf(screenshot)));
        }
    }

    @AfterAll
    static void afterAllTests(){
        refresh();
    }
}

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
import static HelperClasses.ScreenshotTests.AScreenshot;
import static Pages.MonitoringPage.*;
import static RecourcesTests.BeforeSettingsTests.StartTests;
import static com.codeborne.selenide.Selenide.refresh;
import static java.time.Duration.ofSeconds;
import static org.junit.jupiter.api.Assertions.*;

@EpicServicesTests
@FeatureProviderMX1000
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class ADD_MX1000_Test {

    private KATSPage katsPage = null;
    private SipServerPage sipServerPage;
    private boolean TEST_STATUS;
    private String TEST_MESSAGE;
    private String filename;

    @Story(value = "Проверяем, настроен ли сервер SIP")
    @Description(value = "Проверяем, настроен ли сервер SIP и если сервер SIP не натсроен, настраиваем его")
    @BeforeEach
    void setUp(){
        StartTests();
        TEST_STATUS = true;
        TEST_MESSAGE = "";
    }

    @Story(value = "Настраиваем SIP сервера")
    @Description(value = "Проверяем настроен ли SIP сервер, если не настроен, настраиваем")
    @Test
    void test_Opensips(){
        filename = new Object(){}.getClass().getEnclosingMethod().getName();
        sipServerPage = (SipServerPage) MonitoringPage.openSectionWEB(OPENSIPS_ITEM_MENU);
        assertTrue(isCheckNotVisibleDownload(),"Невозможно продолжать тестирование, СУ недоступно");
        assertTrue(sipServerPage.setSettingsSIPServer(IP_SERVER, OPENSIPS_SERVER_PORT, OPENSIPS_TURN_PORT_MIN, OPENSIPS_TURN_PORT_MAX), "Ошибка при настройке SIP сервера");
        assertTrue(sipServerPage.isCheckSettingsSipServer(), "Настройки SIP сервера не сохранились на сервере");
    }

    @Story(value = "Добавление провайдетра MX1000")
    @Description(value = "Проверяем, что добавляется провайдет MX1000 типа КАТС")
    @Test
    void test_Provider_MX1000() {
        filename = new Object(){}.getClass().getEnclosingMethod().getName();
        assertTimeout(ofSeconds(600), () -> {
            assertTrue(sipServerPage.isCheckSettingsSipServer(), "Невозмоожно добавить провайдер MX1000. ненастроен SIP сервер");
            if (isCheckNotVisibleElement() && !ProvidersPage.isCheckProviderPage().isDisplayed())
                katsPage = (KATSPage) MonitoringPage.openSectionWEB(PROVIDERS_ITEM_MENU, MX1000_TYPE_PROVIDER);
            if (katsPage == null) katsPage = KATSPage.getInstance();
            assertTrue(isCheckNotVisibleDownload(),"Невозможно продолжать тестирование, СУ недоступно");
            katsPage.addMX1000(MX1000_NAME, MX1000_HOST, MX1000_USERNAME, MX1000_PASSWORD, MX1000_DIALPLAN, MX1000_DELAY_REGISTRATION);
            assertTrue(isCheckNotVisibleDownload(),"Невозможно продолжать тестирование, СУ недоступно");
            assertTrue(katsPage.isCheckProvider(MX1000_NAME), "Не удалось добавить провайдер " + MX1000_NAME);
            if ( ! katsPage.isSelectProvider()) failedTestWithScreenshot("Провайдер MX1000 не найден в базе данных MySql");
            if ( ! katsPage.isSelectDialplan()) failedTestWithScreenshot("Шаблон номера для MX1000 не найден в базе данных MySql");
            if ( ! katsPage.isMX1000()) failedTestWithScreenshot("Сервер MX1000 не установлен на сервер");
            assertTrue(TEST_STATUS, TEST_MESSAGE);
        }, () -> "Время теста больше 5 минут");
    }

    void failedTestWithScreenshot(String message) {
        TEST_STATUS = false;
        TEST_MESSAGE = TEST_MESSAGE + "\n" + message;
    }

    @AfterEach
    void tearDown(){ AScreenshot(filename);}

    @AfterAll
    static void afterAllTests(){
        refresh();
    }
}

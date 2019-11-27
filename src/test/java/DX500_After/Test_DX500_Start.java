package DX500_After;

import AnnotationsTests.ServicesTests.EpicServicesTests;
import AnnotationsTests.ServicesTests.FeatureProviderDX500;
import Pages.MonitoringPage;
import Pages.Providers.DX500Page;
import Pages.Providers.ProvidersPage;
import RecourcesTests.TestRules;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.qameta.allure.model.Status;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static DataTests.Providers.PROVIDERS.PROVIDERS_ITEM_MENU;
import static DataTests.Providers.PROVIDER_DX500.*;
import static DataTests.Providers.PROVIDER_DX500.DX500_BUSY;
import static HelperClasses.ScreenshotTests.AScreenshot;
import static Pages.Providers.DX500Page.dx500Page;
import static RecourcesTests.BeforeSettingsTests.StartTests;
import static com.codeborne.selenide.Selenide.refresh;
import static java.time.Duration.ofSeconds;
import static org.junit.jupiter.api.Assertions.*;


@EpicServicesTests
@FeatureProviderDX500
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class Test_DX500_Start {

    private boolean TEST_STATUS;
    private String TEST_MESSAGE;
    private String filename;

    @BeforeEach
    void setUp(){
        StartTests();
        if( ! ProvidersPage.isCheckProviderPage().isDisplayed()) dx500Page = (DX500Page) MonitoringPage.openSectionWEB(PROVIDERS_ITEM_MENU, DX500_TYPE_PROVIDER);
        if( dx500Page == null ) dx500Page = DX500Page.getInstance();
        assertTrue(dx500Page.isCheckProvider(DX500_TYPE_PROVIDER), "Провайдер DX500 не создан");
        dx500Page.clickSectionDX500();
        TEST_STATUS = true;
        TEST_MESSAGE = "";
    }

    @Story(value = "Старт сервера Ассистентов провайдера DX500")
    @Description(value = "Проверяем, что через СУ успешно стартует сервер Ассистентов провайдера DX500")
    @Test
    void test_DX500_Start_Server_Booster() {
        filename = new Object(){}.getClass().getEnclosingMethod().getName();
        startService(DX500_BOOSTER);
    }

    @Story(value = "Старт сервера Пультов провайдера DX500")
    @Description(value = "Проверяем, что через СУ успешно стартует сервер Пультов провайдера DX500")
    @Test
    void test_DX500_Start_Server_Pult() {
        filename = new Object(){}.getClass().getEnclosingMethod().getName();
        startService(DX500_PULT);
    }

    @Story(value = "Старт сервера SIP(абон)-DX провайдера DX500")
    @Description(value = "Проверяем, что через СУ успешно стартует сервер SIP(абон)-DX провайдера DX500")
    @Test
    void test_DX500_Start_Server_SIP_Abon_DX() {
        filename = new Object(){}.getClass().getEnclosingMethod().getName();
        startService(DX500_SIP_ABON_DX);
    }

    @Story(value = "Старт сервера SIP-Пультов провайдера DX500")
    @Description(value = "Проверяем, что через СУ успешно стартует сервер SIP-Пультов провайдера DX500")
    @Test
    void test_DX500_Start_Server_SIP_Pult() {
        filename = new Object(){}.getClass().getEnclosingMethod().getName();
        startService(DX500_SIP_PULT);
    }

    @Story(value = "Старт сервера Занятости провайдера DX500")
    @Description(value = "Проверяем, что через СУ успешно стартует сервер Занятости провайдера DX500")
    @Test
    void test_DX500_Start_Server_Busy() {
        filename = new Object(){}.getClass().getEnclosingMethod().getName();
        startService(DX500_BUSY);
    }

    @Story(value = "Сохраняем провайдера")
    @Description(value = "Нажимаем кнопку сохранить и проверяем сохранился ли провайдер")
    @Test
    void test_Save_Provider(){
        filename = new Object(){}.getClass().getEnclosingMethod().getName();
        if (!dx500Page.isFormEditProvider().isDisplayed()) dx500Page.clickButtonEditProvider(DX500_TYPE_PROVIDER);
        assertTrue(dx500Page.clickSaveProvider(DX500_TYPE_PROVIDER), "После сохранения провайдера " + DX500_TYPE_PROVIDER + "запись в таблице пропала");
    }

    @AfterEach
    void tearDown(){
        AScreenshot(filename);
    }

    @Story(value = "Сохранение настроек провайдера DX500")
    @Description(value = "Проверяем, сохранились ли настройки провайдера DX500")
    @AfterAll
    static void finalTest() {
        refresh();
    }

    /*void failedTestWithScreenshot(String message) {
        Allure.step(message, Status.FAILED);
        TEST_STATUS = false;
        TEST_MESSAGE = TEST_MESSAGE + "\n" + message;
    }*/

    void startService(String service){
        assertTimeout(ofSeconds(300), () -> {
            assertTrue(dx500Page.startServer(service), "Не удалось запустить сервер " + service + " через СУ");
            assertTrue(dx500Page.isCheckStartServers(service), "На сервере статус сервера " + service + " - не запущен.");
        }, () -> "Время теста больше 5 минут");
    }

}

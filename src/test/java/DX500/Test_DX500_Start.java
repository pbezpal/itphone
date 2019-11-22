package DX500;

import AnnotationsTests.ServicesTests.EpicServicesTests;
import AnnotationsTests.ServicesTests.FeatureProviderDX500;
import HelperClasses.SSHManager;
import Pages.MonitoringPage;
import Pages.Providers.DX500Page;
import Pages.Providers.ProvidersPage;
import RecourcesTests.TestRules;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.qameta.allure.model.Status;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static DataTests.Providers.PROVIDERS.PROVIDERS_ITEM_MENU;
import static DataTests.Providers.PROVIDER_DX500.*;
import static DataTests.Providers.PROVIDER_DX500.DX500_BUSY;
import static Pages.Providers.DX500Page.dx500Page;
import static RecourcesTests.BeforeSettingsTests.StartTests;
import static java.time.Duration.ofSeconds;
import static org.junit.jupiter.api.Assertions.*;


@EpicServicesTests
@FeatureProviderDX500
@ExtendWith(TestRules.class)
public class Test_DX500_Start {

    private boolean TEST_STATUS;
    private String TEST_MESSAGE;
    private boolean screenshot;

    @BeforeEach
    void setUp(){
        StartTests();
        if( ! ProvidersPage.isCheckProviderPage().isDisplayed()) dx500Page = (DX500Page) MonitoringPage.openSectionWEB(PROVIDERS_ITEM_MENU, DX500_TYPE_PROVIDER);
        if( dx500Page == null ) dx500Page = DX500Page.getInstance();
        assertTrue(dx500Page.isCheckProvider(DX500_TYPE_PROVIDER, 60000));
        dx500Page.clickSectionDX500();
        TEST_STATUS = true;
        TEST_MESSAGE = "";
        screenshot = false;
    }

    @Story(value = "Старт сервера Ассистентов провайдера DX500")
    @Description(value = "Проверяем, что через СУ успешно стартует сервер Ассистентов провайдера DX500")
    @Test
    void test_DX500_Start_Server_Booster() {
        assertTimeout(ofSeconds(300), () -> {
            boolean webStatusBooster = dx500Page.startServer(DX500_BOOSTER);
            boolean serverStatusBooster = dx500Page.isCheckStartServers(DX500_BOOSTER);
            if (!webStatusBooster && serverStatusBooster)
                failedTestWithScreenshot("На сервере " + DX500_BOOSTER + " запущен. В WEB некорректный статус сервера. Проверьте соединение со станцией!!!", true);
            else if (webStatusBooster && !serverStatusBooster)
                failedTestWithScreenshot("На сервере " + DX500_BOOSTER + " не запущен. В WEB некорректно статус сервера", true);
            else if (!webStatusBooster && !serverStatusBooster)
                failedTestWithScreenshot("Cервер " + DX500_BOOSTER + " не запущен.", true);
        }, () -> "Время теста больше 5 минут");
    }

    @Story(value = "Старт сервера Пультов провайдера DX500")
    @Description(value = "Проверяем, что через СУ успешно стартует сервер Пультов провайдера DX500")
    @Test
    void test_DX500_Start_Server_Pult() {
        assertTimeout(ofSeconds(300), () -> {
            boolean webStatusPult = dx500Page.startServer(DX500_PULT);
            boolean serverStatusPult = dx500Page.isCheckStartServers(DX500_PULT);
            if (!webStatusPult && serverStatusPult)
                failedTestWithScreenshot("На сервере " + DX500_PULT + " запущен. В WEB некорректный статус сервера. Проверьте соединение со станцией!!!", true);
            else if (webStatusPult && !serverStatusPult)
                failedTestWithScreenshot("На сервере " + DX500_PULT + " не запущен. В WEB некорректно статус сервера", true);
            else if (!webStatusPult && !serverStatusPult)
                failedTestWithScreenshot("Cервер " + DX500_PULT + " не запущен.", true);
        }, () -> "Время теста больше 5 минут");
    }

    @Story(value = "Старт сервера SIP(абон)-DX провайдера DX500")
    @Description(value = "Проверяем, что через СУ успешно стартует сервер SIP(абон)-DX провайдера DX500")
    @Test
    void test_DX500_Start_Server_SIP_Abon_DX() {
        assertTimeout(ofSeconds(300), () -> {
            boolean webStatusSIP = dx500Page.startServer(DX500_SIP_ABON_DX);
            boolean serverStatusSIP = dx500Page.isCheckStartServers(DX500_SIP_ABON_DX);
            if (!webStatusSIP && serverStatusSIP)
                failedTestWithScreenshot("На сервере " + DX500_SIP_ABON_DX + " запущен. В WEB некорректный статус сервера. Проверьте соединение со станцией!!!", true);
            else if (!webStatusSIP && serverStatusSIP)
                failedTestWithScreenshot("На сервере " + DX500_SIP_ABON_DX + " не запущен. В WEB некорректно статус сервера", true);
            else if (!webStatusSIP && !serverStatusSIP)
                failedTestWithScreenshot("Cервер " + DX500_SIP_ABON_DX + " не стартовал.", true);
        }, () -> "Время теста больше 5 минут");
    }

    @Story(value = "Старт сервера SIP-Пультов провайдера DX500")
    @Description(value = "Проверяем, что через СУ успешно стартует сервер SIP-Пультов провайдера DX500")
    @Test
    void test_DX500_Start_Server_SIP_Pult() {
        assertTimeout(ofSeconds(300), () -> {
            boolean webStatusSIPPult = dx500Page.startServer(DX500_SIP_PULT);
            boolean serverStatusSIPPult = dx500Page.isCheckStartServers(DX500_SIP_PULT);
            if (!webStatusSIPPult && serverStatusSIPPult)
                failedTestWithScreenshot("На сервере " + DX500_SIP_PULT + " запущен. В WEB некорректный статус сервера. Проверьте соединение со станцией!!!", true);
            else if (webStatusSIPPult && !serverStatusSIPPult)
                failedTestWithScreenshot("На сервере " + DX500_SIP_PULT + " не запущен. В WEB некорректно статус сервера", true);
            else if (!webStatusSIPPult && !serverStatusSIPPult)
                failedTestWithScreenshot("Cервер " + DX500_SIP_PULT + " не запущен.", true);
        }, () -> "Время теста больше 5 минут");
    }

    @Story(value = "Конфигурацию сервера Занятости")
    @Description(value = "Проверяем, что конфигурация сервера Занятости сохранилась")
    @Test
    void test_DX500_Configuration_Busy(){
        if( ! SSHManager.isCheckQuerySSH(BUSY_CONFIG_ADAPTER_NAME)) failedTestWithScreenshot("Неверное значение в параметре adapter_name", false);
    }

    @Story(value = "Старт сервера Занятости провайдера DX500")
    @Description(value = "Проверяем, что через СУ успешно стартует сервер Занятости провайдера DX500")
    @Test
    void test_DX500_Start_Server_Busy() {
        assertTimeout(ofSeconds(300), () -> {
            boolean webStatusSIPPult = dx500Page.startServer(DX500_BUSY);
            boolean serverStatusSIPPult = dx500Page.isCheckStartServers(DX500_BUSY);
            if (!webStatusSIPPult && serverStatusSIPPult)
                failedTestWithScreenshot("На сервере " + DX500_BUSY + " запущен. В WEB некорректный статус сервера. Проверьте соединение со станцией!!!", true);
            else if (webStatusSIPPult && !serverStatusSIPPult)
                failedTestWithScreenshot("На сервере " + DX500_BUSY + " не запущен. В WEB некорректно статус сервера", true);
            else if (!webStatusSIPPult && !serverStatusSIPPult)
                failedTestWithScreenshot("Cервер " + DX500_BUSY + " не запущен.", true);
        }, () -> "Время теста больше 5 минут");
    }

    @AfterEach
    void tearDown(){
        if( ! TEST_STATUS){
            fail(TEST_MESSAGE, new Exception(String.valueOf(screenshot)));
        }
    }

    @Story(value = "Сохранение настроек провайдера DX500")
    @Description(value = "Проверяем, сохранились ли настройки провайдера DX500")
    @AfterAll
    static void finalTest() {
        if (!dx500Page.isFormEditProvider().isDisplayed()) dx500Page.clickButtonEditProvider(DX500_TYPE_PROVIDER);
        assertTrue(dx500Page.clickSaveProvider(DX500_TYPE_PROVIDER), "После сохранения провайдера " + DX500_TYPE_PROVIDER + "запись в таблице пропала");
    }

    void failedTestWithScreenshot(String message, boolean screen) {
        Allure.step(message, Status.FAILED);
        TEST_STATUS = false;
        TEST_MESSAGE = TEST_MESSAGE + "\n" + message;
        screenshot = screen;
    }

}

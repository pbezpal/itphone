package Test_Providers;

import AnnotationsTests.ServicesTests.EpicServicesTests;
import AnnotationsTests.ServicesTests.FeatureProviderDX500;
import HelperClasses.SSHManager;
import HelperClasses.ScreenshotTests;
import Pages.MonitoringPage;
import Pages.Providers.DX500Page;
import Pages.Providers.ProvidersPage;
import RecourcesTests.BeforeAllTests;
import RecourcesTests.TestRules;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.qameta.allure.model.Status;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static DataTests.Providers.PROVIDERS.PROVIDERS_ITEM_MENU;
import static DataTests.Providers.PROVIDER_DX500.*;
import static Pages.MonitoringPage.isCheckNotVisibleElement;
import static Pages.Providers.DX500Page.dx500Page;
import static org.junit.jupiter.api.Assertions.assertTrue;

@EpicServicesTests
@FeatureProviderDX500
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
@ExtendWith({TestRules.class, BeforeAllTests.class})
public class Test_A_DX500 {

    private boolean TEST_STATUS;
    private String TEST_MESSAGE;

    @BeforeEach
    void setUp(){

        if( isCheckNotVisibleElement() && ! ProvidersPage.isCheckProviderPage().isDisplayed()) dx500Page = (DX500Page) MonitoringPage.openSectionWEB(PROVIDERS_ITEM_MENU, DX500_TYPE_PROVIDER);
        if( dx500Page == null ) dx500Page = DX500Page.getInstance();
        TEST_STATUS = true;
        TEST_MESSAGE = "";
    }

    @Story(value = "Добавление провайдера DX500")
    @Description(value = "Проверяем, что через СУ можно успешно добавить провайдер DX500")
    @Test
    void test_Add_Provider_DX500() {
        if( ! dx500Page.isCheckProvider(DX500_TYPE_PROVIDER)) assertTrue(dx500Page.addDX500Provider(), "Провайдер DX500 не был добавлен");
        if( ! dx500Page.isMySqlDialplan()) { failedTestWithScreenshot("Маршрут для DX500 не был добавлен в БД MySql", false); }
    }

    @Story(value = "Конфигурацию сервера Ассистентов")
    @Description(value = "Проверяем, что конфигурация сервера Ассистентов сохранилась")
    @Test
    void test_DX500_Configuration_Booster(){
        if( ! SSHManager.isCheckQuerySSH(BOOSTER_CONFIG_DB_IP)) failedTestWithScreenshot("Неверное значение в параметре db_ip", false);
        if( ! SSHManager.isCheckQuerySSH(BOOSTER_CONFIG_DB_PORT)) failedTestWithScreenshot("Неверное значение в параметре db_port", false);
        if( ! SSHManager.isCheckQuerySSH(BOOSTER_CONFIG_ADAPTER_NAME)) failedTestWithScreenshot("Неверное значение в параметре adapter_name", false);
        if( ! SSHManager.isCheckQuerySSH(BOOSTER_CONFIG_STATION)) failedTestWithScreenshot("Неверное значение в параметре station", false);
        if( ! SSHManager.isCheckQuerySSH(BOOSTER_CONFIG_IP)) failedTestWithScreenshot("Неверное значение в параметре ip", false);
        if( !SSHManager.isCheckQuerySSH(BOOSTER_CONFIG_PORT)) failedTestWithScreenshot("Неверное значение в параметре port", false);
    }

    @Story(value = "Старт сервера Ассистентов провайдера DX500")
    @Description(value = "Проверяем, что через СУ успешно стартует сервер Ассистентов провайдера DX500")
    @Test
    void test_DX500_Start_Server_Booster() {
        dx500Page.clickSectionDX500();
        boolean webStatusBooster = dx500Page.startServer(DX500_BOOSTER);
        boolean serverStatusBooster = dx500Page.isCheckStartServers(DX500_BOOSTER);
        if (!webStatusBooster && serverStatusBooster) failedTestWithScreenshot("На сервере " + DX500_BOOSTER + " запущен. В WEB некорректный статус сервера. Проверьте соединение со станцией!!!", true);
        else if (webStatusBooster && !serverStatusBooster) failedTestWithScreenshot("На сервере " + DX500_BOOSTER + " не запущен. В WEB некорректно статус сервера", true);
        else if (!webStatusBooster && !serverStatusBooster) failedTestWithScreenshot("Cервер " + DX500_BOOSTER + " не запущен.", true);

    }

    @Story(value = "Конфигурацию сервера Пультов")
    @Description(value = "Проверяем, что конфигурация сервера Пультов сохранилась")
    @Test
    void test_DX500_Configuration_Pult(){
        if( ! SSHManager.isCheckQuerySSH(PULT_CONFIG_CONTACT_IP)) failedTestWithScreenshot("Неверное значение в параметре db_ip", false);
        if( ! SSHManager.isCheckQuerySSH(PULT_CONFIG_CONTACT_PORT)) failedTestWithScreenshot("Неверное значение в параметре db_port", false);
        if( ! SSHManager.isCheckQuerySSH(PULT_CONFIG_SIG_GATE_PORT)) failedTestWithScreenshot("Неверное значение в параметре sig_gate_port", false);
        if( ! SSHManager.isCheckQuerySSH(PULT_CONFIG_IP)) failedTestWithScreenshot("Неверное значение в параметре ip", false);
        if( ! SSHManager.isCheckQuerySSH(PULT_CONFIG_PORT)) failedTestWithScreenshot("Неверное значение в параметре port", false);
        if( !SSHManager.isCheckQuerySSH(PULT_CONFIG_MEDIA_GATE_PORT)) failedTestWithScreenshot("Неверное значение в параметре media_gate_port", false);
        //Проверка настроек SMG
        if( ! SSHManager.isCheckQuerySSH(PULT_SMG_ENABLE)) failedTestWithScreenshot("Неверное значение в параметре SMG1_ENABLE файла /etc/smg.cfg", false);
        if( ! SSHManager.isCheckQuerySSH(PULT_SMG_SGDEV)) failedTestWithScreenshot("Неверное значение в параметре SG1_DEV файла /etc/smg.cfg", false);
        if( !SSHManager.isCheckQuerySSH(PULT_SMG_MGDEV)) failedTestWithScreenshot("Неверное значение в параметре MG1_DEV файла /etc/smg.cfg", false);
    }

    @Story(value = "Старт сервера Пультов провайдера DX500")
    @Description(value = "Проверяем, что через СУ успешно стартует сервер Пультов провайдера DX500")
    @Test
    void test_DX500_Start_Server_Pult() {
        dx500Page.clickSectionDX500();
        boolean webStatusPult = dx500Page.startServer(DX500_PULT);
        boolean serverStatusPult = dx500Page.isCheckStartServers(DX500_PULT);
        if (!webStatusPult && serverStatusPult)
            failedTestWithScreenshot("На сервере " + DX500_PULT + " запущен. В WEB некорректный статус сервера. Проверьте соединение со станцией!!!", true);
        else if (webStatusPult && !serverStatusPult)
            failedTestWithScreenshot("На сервере " + DX500_PULT + " не запущен. В WEB некорректно статус сервера", true);
        else if (!webStatusPult && !serverStatusPult)
            failedTestWithScreenshot("Cервер " + DX500_PULT + " не запущен.", true);
    }

    @Story(value = "Конфигурацию сервера Пультов")
    @Description(value = "Проверяем, что конфигурация сервера Пультов сохранилась")
    @Test
    void test_DX500_Configuration_SIP_Abon_DX(){
        if( ! SSHManager.isCheckQuerySSH(SIP_ABON_DX_CONFIG_DB_IP)) failedTestWithScreenshot("Неверное значение в параметре db_ip", false);
        if( ! SSHManager.isCheckQuerySSH(SIP_ABON_DX_CONFIG_DB_PORT)) failedTestWithScreenshot("Неверное значение в параметре db_port", false);
        if( ! SSHManager.isCheckQuerySSH(SIP_ABON_DX_CONFIG_SIG_GATE_IP)) failedTestWithScreenshot("Неверное значение в параметре sig_gate_ip", false);
        if( ! SSHManager.isCheckQuerySSH(SIP_ABON_DX_CONFIG_SIG_GATE_PORT)) failedTestWithScreenshot("Неверное значение в параметре sig_gate_port", false);
        if( ! SSHManager.isCheckQuerySSH(SIP_ABON_DX_CONFIG_IP)) failedTestWithScreenshot("Неверное значение в параметре ip", false);
        if( ! SSHManager.isCheckQuerySSH(SIP_ABON_DX_CONFIG_PORT)) failedTestWithScreenshot("Неверное значение в параметре port", false);
        if( !SSHManager.isCheckQuerySSH(SIP_ABON_DX_CONFIG_MEDIA_GATE_IP)) failedTestWithScreenshot("Неверное значение в параметре media_gate_ip", false);
        if( !SSHManager.isCheckQuerySSH(SIP_ABON_DX_CONFIG_MEDIA_GATE_PORT)) failedTestWithScreenshot("Неверное значение в параметре media_gate_port", false);
        //Проверка настроек SMG
        if( ! SSHManager.isCheckQuerySSH(SIP_ABON_DX_CONFIG_SMG_ENABLE)) failedTestWithScreenshot("Неверное значение в параметре SMG0_ENABLE файла /etc/smg.cfg", false);
        if( ! SSHManager.isCheckQuerySSH(SIP_ABON_DX_CONFIG_SGDEV)) failedTestWithScreenshot("Неверное значение в параметре SG0_DEV файла /etc/smg.cfg", false);
        if( !SSHManager.isCheckQuerySSH(SIP_ABON_DX_CONFIG_MGDEV)) failedTestWithScreenshot("Неверное значение в параметре MG0_DEV файла /etc/smg.cfg", false);
    }

    @Story(value = "Старт сервера SIP(абон)-DX провайдера DX500")
    @Description(value = "Проверяем, что через СУ успешно стартует сервер SIP(абон)-DX провайдера DX500")
    @Test
    void test_DX500_Start_Server_SIP_Abon_DX() {
        dx500Page.clickSectionDX500();
        boolean webStatusSIP = dx500Page.startServer(DX500_SIP_ABON_DX);
        boolean serverStatusSIP = dx500Page.isCheckStartServers(DX500_SIP_ABON_DX);
        if (!webStatusSIP && serverStatusSIP) failedTestWithScreenshot("На сервере " + DX500_SIP_ABON_DX + " запущен. В WEB некорректный статус сервера. Проверьте соединение со станцией!!!", true);
        else if (!webStatusSIP && serverStatusSIP) failedTestWithScreenshot("На сервере " + DX500_SIP_ABON_DX + " не запущен. В WEB некорректно статус сервера", true);
        else if (!webStatusSIP && !serverStatusSIP) failedTestWithScreenshot("Cервер " + DX500_SIP_ABON_DX + " не стартовал.", true);
    }

    @Story(value = "Конфигурацию сервера Пультов")
    @Description(value = "Проверяем, что конфигурация сервера Пультов сохранилась")
    @Test
    void test_DX500_Configuration_SIP_Pult(){
        if( ! SSHManager.isCheckQuerySSH(SIP_PULT_CONFIG_DB_IP)) failedTestWithScreenshot("Неверное значение в параметре db_ip", false);
        if( ! SSHManager.isCheckQuerySSH(SIP_PULT_CONFIG_DB_PORT)) failedTestWithScreenshot("Неверное значение в параметре db_port", false);
        if( ! SSHManager.isCheckQuerySSH(SIP_PULT_CONFIG_SG_PORT)) failedTestWithScreenshot("Неверное значение в параметре sg_port", false);
        if( ! SSHManager.isCheckQuerySSH(SIP_PULT_CONFIG_IP)) failedTestWithScreenshot("Неверное значение в параметре ip", false);
        if( ! SSHManager.isCheckQuerySSH(SIP_PULT_CONFIG_PORT)) failedTestWithScreenshot("Неверное значение в параметре port", false);
        if( !SSHManager.isCheckQuerySSH(SIP_PULT_CONFIG_MG_PORT)) failedTestWithScreenshot("Неверное значение в параметре mg_port", false);
        //Проверка настроек SMG
        if( ! SSHManager.isCheckQuerySSH(SIP_PULT_CONFIG_SMG_ENSBLE)) failedTestWithScreenshot("Неверное значение в параметре SMG3_ENABLE файла /etc/smg.cfg", false);
        if( ! SSHManager.isCheckQuerySSH(SIP_PULT_CONFIG_SGDEV)) failedTestWithScreenshot("Неверное значение в параметре SG3_DEV файла /etc/smg.cfg", false);
        if( !SSHManager.isCheckQuerySSH(SIP_PULT_CONFIG_MGDEV)) failedTestWithScreenshot("Неверное значение в параметре MG3_DEV файла /etc/smg.cfg", false);
    }

    @Story(value = "Старт сервера SIP-Пультов провайдера DX500")
    @Description(value = "Проверяем, что через СУ успешно стартует сервер SIP-Пультов провайдера DX500")
    @Test
    void test_DX500_Start_Server_SIP_Pult() {
        dx500Page.clickSectionDX500();
        boolean webStatusSIPPult = dx500Page.startServer(DX500_SIP_PULT);
        boolean serverStatusSIPPult = dx500Page.isCheckStartServers(DX500_SIP_PULT);
        if (!webStatusSIPPult && serverStatusSIPPult)
            failedTestWithScreenshot("На сервере " + DX500_SIP_PULT + " запущен. В WEB некорректный статус сервера. Проверьте соединение со станцией!!!", true);
        else if (webStatusSIPPult && !serverStatusSIPPult)
            failedTestWithScreenshot("На сервере " + DX500_SIP_PULT + " не запущен. В WEB некорректно статус сервера", true);
        else if (!webStatusSIPPult && !serverStatusSIPPult)
            failedTestWithScreenshot("Cервер " + DX500_SIP_PULT + " не запущен.", true);
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
        dx500Page.clickSectionDX500();
        boolean webStatusSIPPult = dx500Page.startServer(DX500_BUSY);
        boolean serverStatusSIPPult = dx500Page.isCheckStartServers(DX500_BUSY);
        if (!webStatusSIPPult && serverStatusSIPPult)
            failedTestWithScreenshot("На сервере " + DX500_BUSY + " запущен. В WEB некорректный статус сервера. Проверьте соединение со станцией!!!", true);
        else if (webStatusSIPPult && !serverStatusSIPPult)
            failedTestWithScreenshot("На сервере " + DX500_BUSY + " не запущен. В WEB некорректно статус сервера", true);
        else if (!webStatusSIPPult && !serverStatusSIPPult)
            failedTestWithScreenshot("Cервер " + DX500_BUSY + " не запущен.", true);
    }

    @AfterEach
    void etarDown(){
        assertTrue(TEST_STATUS, TEST_MESSAGE);
    }

    @Story(value = "Сохранение настроек провайдера DX500")
    @Description(value = "Проверяем, сохранились ли настройки провайдера DX500")
    @AfterAll
    static void finalTest() {
        if (!dx500Page.isFormEditProvider().isDisplayed()) dx500Page.clickButtonEditProvider(DX500_TYPE_PROVIDER);
        assertTrue(dx500Page.clickSaveProvider(DX500_TYPE_PROVIDER), "После сохранения провайдера " + DX500_TYPE_PROVIDER + "запись в таблице пропала");
    }

    void failedTestWithScreenshot(String message, boolean screenshot) {
        Allure.step(message, Status.FAILED);
        TEST_STATUS = false;
        TEST_MESSAGE = TEST_MESSAGE + "\n" + message;
        if(screenshot) ScreenshotTests.screenshot();
    }



}

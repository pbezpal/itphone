package Test_1_Providers;

import AnnotationsTests.ServicesTests.EpicServicesTests;
import AnnotationsTests.ServicesTests.FeatureProviderDX500;
import HelperClasses.SSHManager;
import HelperClasses.ScreenshotTests;
import RecourcesTests.BeforeEachTests;
import RecourcesTests.TestRules;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static DataTests.Providers.DataProviderDX500.*;
import static Pages.Providers.DX500Page.dx500Page;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@EpicServicesTests
@FeatureProviderDX500
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
@ExtendWith({TestRules.class, BeforeEachTests.class})
public class Test_1_DX500 {

    @Story(value = "Добавление провайдера DX500")
    @Description(value = "Проверяем, что через СУ можно успешно добавить провайдер DX500")
    @Test
    void test_Add_Provider_DX500() {
        if( ! dx500Page.isCheckProvider(DX500)) assertTrue(dx500Page.addDX500Provider(), "Провайдер DX500 не был добавлен");
        if( ! dx500Page.isMySqlDialplan()) failedTestWithScreenshot("Маршрут для DX500 не был добавлен в БД MySql", false);
    }

    @Story(value = "Конфигурацию сервера Ассистентов")
    @Description(value = "Проверяем, что конфигурация сервера Ассистентов сохранилась")
    @Test
    void test_DX500_Configuration_Booster(){
        if( ! SSHManager.isCheckQuerySSH(boosterDBIP)) failedTestWithScreenshot("Неверное значение в параметре db_ip", false);
        if( ! SSHManager.isCheckQuerySSH(boosterDBPort)) failedTestWithScreenshot("Неверное значение в параметре db_port", false);
        if( ! SSHManager.isCheckQuerySSH(boosterAdapterName)) failedTestWithScreenshot("Неверное значение в параметре adapter_name", false);
        if( ! SSHManager.isCheckQuerySSH(boosterStation)) failedTestWithScreenshot("Неверное значение в параметре station", false);
        if( ! SSHManager.isCheckQuerySSH(boosterIP)) failedTestWithScreenshot("Неверное значение в параметре ip", false);
        if( !SSHManager.isCheckQuerySSH(boosterPort)) failedTestWithScreenshot("Неверное значение в параметре port", false);
    }

    @Story(value = "Старт сервера Ассистентов провайдера DX500")
    @Description(value = "Проверяем, что через СУ успешно стартует сервер Ассистентов провайдера DX500")
    @Test
    void test_DX500_Start_Server_Booster() {
        dx500Page.clickSectionDX500();
        boolean webStatusBooster = dx500Page.startServer(serverBooster);
        boolean serverStatusBooster = dx500Page.isCheckStartServers(serverBooster);
        if (!webStatusBooster && serverStatusBooster) failedTestWithScreenshot("На сервере " + serverBooster + " запущен. В WEB некорректный статус сервера. Проверьте соединение со станцией!!!", true);
        else if (webStatusBooster && !serverStatusBooster) failedTestWithScreenshot("На сервере " + serverBooster + " не запущен. В WEB некорректно статус сервера", true);
        else if (!webStatusBooster && !serverStatusBooster) failedTestWithScreenshot("Cервер " + serverBooster + " не запущен.", true);
    }

    @Story(value = "Конфигурацию сервера Пультов")
    @Description(value = "Проверяем, что конфигурация сервера Пультов сохранилась")
    @Test
    void test_DX500_Configuration_Pult(){
        if( ! SSHManager.isCheckQuerySSH(pultContactIP)) failedTestWithScreenshot("Неверное значение в параметре db_ip", false);
        if( ! SSHManager.isCheckQuerySSH(pultContactPort)) failedTestWithScreenshot("Неверное значение в параметре db_port", false);
        if( ! SSHManager.isCheckQuerySSH(pultSGP)) failedTestWithScreenshot("Неверное значение в параметре sig_gate_port", false);
        if( ! SSHManager.isCheckQuerySSH(pultIP)) failedTestWithScreenshot("Неверное значение в параметре ip", false);
        if( ! SSHManager.isCheckQuerySSH(pultPort)) failedTestWithScreenshot("Неверное значение в параметре port", false);
        if( !SSHManager.isCheckQuerySSH(pultMGP)) failedTestWithScreenshot("Неверное значение в параметре media_gate_port", false);
        //Проверка настроек SMG
        if( ! SSHManager.isCheckQuerySSH(SMGPult_Enable)) failedTestWithScreenshot("Неверное значение в параметре SMG1_ENABLE файла /etc/smg.cfg", false);
        if( ! SSHManager.isCheckQuerySSH(SMGPult_SGDEV)) failedTestWithScreenshot("Неверное значение в параметре SG1_DEV файла /etc/smg.cfg", false);
        if( !SSHManager.isCheckQuerySSH(SMGPult_MGDEV)) failedTestWithScreenshot("Неверное значение в параметре MG1_DEV файла /etc/smg.cfg", false);
    }

    @Story(value = "Старт сервера Пультов провайдера DX500")
    @Description(value = "Проверяем, что через СУ успешно стартует сервер Пультов провайдера DX500")
    @Test
    void test_DX500_Start_Server_Pult() {
        dx500Page.clickSectionDX500();
        boolean webStatusPult = dx500Page.startServer(serverPult);
        boolean serverStatusPult = dx500Page.isCheckStartServers(serverPult);
        if (!webStatusPult && serverStatusPult)
            failedTestWithScreenshot("На сервере " + serverPult + " запущен. В WEB некорректный статус сервера. Проверьте соединение со станцией!!!", true);
        else if (webStatusPult && !serverStatusPult)
            failedTestWithScreenshot("На сервере " + serverPult + " не запущен. В WEB некорректно статус сервера", true);
        else if (!webStatusPult && !serverStatusPult)
            failedTestWithScreenshot("Cервер " + serverPult + " не запущен.", true);
    }

    @Story(value = "Конфигурацию сервера Пультов")
    @Description(value = "Проверяем, что конфигурация сервера Пультов сохранилась")
    @Test
    void test_DX500_Configuration_SIP_Abon_DX(){
        if( ! SSHManager.isCheckQuerySSH(sipDBIP)) failedTestWithScreenshot("Неверное значение в параметре db_ip", false);
        if( ! SSHManager.isCheckQuerySSH(sipDPort)) failedTestWithScreenshot("Неверное значение в параметре db_port", false);
        if( ! SSHManager.isCheckQuerySSH(sipSGI)) failedTestWithScreenshot("Неверное значение в параметре sig_gate_ip", false);
        if( ! SSHManager.isCheckQuerySSH(sipSGP)) failedTestWithScreenshot("Неверное значение в параметре sig_gate_port", false);
        if( ! SSHManager.isCheckQuerySSH(sipIP)) failedTestWithScreenshot("Неверное значение в параметре ip", false);
        if( ! SSHManager.isCheckQuerySSH(sipPort)) failedTestWithScreenshot("Неверное значение в параметре port", false);
        if( !SSHManager.isCheckQuerySSH(sipMGI)) failedTestWithScreenshot("Неверное значение в параметре media_gate_ip", false);
        if( !SSHManager.isCheckQuerySSH(sipMGP)) failedTestWithScreenshot("Неверное значение в параметре media_gate_port", false);
        //Проверка настроек SMG
        if( ! SSHManager.isCheckQuerySSH(SMGSIP_Enable)) failedTestWithScreenshot("Неверное значение в параметре SMG0_ENABLE файла /etc/smg.cfg", false);
        if( ! SSHManager.isCheckQuerySSH(SMGSIP_SGDEV)) failedTestWithScreenshot("Неверное значение в параметре SG0_DEV файла /etc/smg.cfg", false);
        if( !SSHManager.isCheckQuerySSH(SMGSIP_MGDEV)) failedTestWithScreenshot("Неверное значение в параметре MG0_DEV файла /etc/smg.cfg", false);
    }

    @Story(value = "Старт сервера SIP(абон)-DX провайдера DX500")
    @Description(value = "Проверяем, что через СУ успешно стартует сервер SIP(абон)-DX провайдера DX500")
    @Test
    void test_DX500_Start_Server_SIP_Abon_DX() {
        dx500Page.clickSectionDX500();
        boolean webStatusSIP = dx500Page.startServer(serverSIP);
        boolean serverStatusSIP = dx500Page.isCheckStartServers(serverSIPModule);
        if (!webStatusSIP && serverStatusSIP) failedTestWithScreenshot("На сервере " + serverSIPModule + " запущен. В WEB некорректный статус сервера. Проверьте соединение со станцией!!!", true);
        else if (!webStatusSIP && serverStatusSIP) failedTestWithScreenshot("На сервере " + serverSIPModule + " не запущен. В WEB некорректно статус сервера", true);
        else if (!webStatusSIP && !serverStatusSIP) failedTestWithScreenshot("Cервер " + serverSIPModule + " не стартовал.", true);
    }

    @Story(value = "Конфигурацию сервера Пультов")
    @Description(value = "Проверяем, что конфигурация сервера Пультов сохранилась")
    @Test
    void test_DX500_Configuration_SIP_Pult(){
        if( ! SSHManager.isCheckQuerySSH(sipPultDBIP)) failedTestWithScreenshot("Неверное значение в параметре db_ip", false);
        if( ! SSHManager.isCheckQuerySSH(sipPultDBPort)) failedTestWithScreenshot("Неверное значение в параметре db_port", false);
        if( ! SSHManager.isCheckQuerySSH(sipPultSGPort)) failedTestWithScreenshot("Неверное значение в параметре sg_port", false);
        if( ! SSHManager.isCheckQuerySSH(sipPultIP)) failedTestWithScreenshot("Неверное значение в параметре ip", false);
        if( ! SSHManager.isCheckQuerySSH(sipPultPort)) failedTestWithScreenshot("Неверное значение в параметре port", false);
        if( !SSHManager.isCheckQuerySSH(sipPultMGPort)) failedTestWithScreenshot("Неверное значение в параметре mg_port", false);
        //Проверка настроек SMG
        if( ! SSHManager.isCheckQuerySSH(SMGSIPPult_Enable)) failedTestWithScreenshot("Неверное значение в параметре SMG3_ENABLE файла /etc/smg.cfg", false);
        if( ! SSHManager.isCheckQuerySSH(SMGSIPPult_SGDEV)) failedTestWithScreenshot("Неверное значение в параметре SG3_DEV файла /etc/smg.cfg", false);
        if( !SSHManager.isCheckQuerySSH(SMGSIPPult_MGDEV)) failedTestWithScreenshot("Неверное значение в параметре MG3_DEV файла /etc/smg.cfg", false);
    }

    @Story(value = "Старт сервера SIP-Пультов провайдера DX500")
    @Description(value = "Проверяем, что через СУ успешно стартует сервер SIP-Пультов провайдера DX500")
    @Test
    void test_DX500_Start_Server_SIP_Pult() {
        dx500Page.clickSectionDX500();
        boolean webStatusSIPPult = dx500Page.startServer(serverSIPPult);
        boolean serverStatusSIPPult = dx500Page.isCheckStartServers(serverSIPPult);
        if (!webStatusSIPPult && serverStatusSIPPult)
            failedTestWithScreenshot("На сервере " + serverSIPPult + " запущен. В WEB некорректный статус сервера. Проверьте соединение со станцией!!!", true);
        else if (webStatusSIPPult && !serverStatusSIPPult)
            failedTestWithScreenshot("На сервере " + serverSIPPult + " не запущен. В WEB некорректно статус сервера", true);
        else if (!webStatusSIPPult && !serverStatusSIPPult)
            failedTestWithScreenshot("Cервер " + serverSIPPult + " не запущен.", true);
    }

    @Story(value = "Конфигурацию сервера Занятости")
    @Description(value = "Проверяем, что конфигурация сервера Занятости сохранилась")
    @Test
    void test_DX500_Configuration_Busy(){
        if( ! SSHManager.isCheckQuerySSH(busyAdapterName)) failedTestWithScreenshot("Неверное значение в параметре adapter_name", false);
    }

    @Story(value = "Старт сервера Занятости провайдера DX500")
    @Description(value = "Проверяем, что через СУ успешно стартует сервер Занятости провайдера DX500")
    @Test
    void test_DX500_Start_Server_Busy() {
        dx500Page.clickSectionDX500();
        boolean webStatusSIPPult = dx500Page.startServer(serverBusy);
        boolean serverStatusSIPPult = dx500Page.isCheckStartServers(serverBusy);
        if (!webStatusSIPPult && serverStatusSIPPult)
            failedTestWithScreenshot("На сервере " + serverBusy + " запущен. В WEB некорректный статус сервера. Проверьте соединение со станцией!!!", true);
        else if (webStatusSIPPult && !serverStatusSIPPult)
            failedTestWithScreenshot("На сервере " + serverBusy + " не запущен. В WEB некорректно статус сервера", true);
        else if (!webStatusSIPPult && !serverStatusSIPPult)
            failedTestWithScreenshot("Cервер " + serverBusy + " не запущен.", true);
    }

    @Story(value = "Сохранение настроек провайдера DX500")
    @Description(value = "Проверяем, сохранились ли настройки провайдера DX500")
    @AfterAll
    static void finalTest() {
        if (!dx500Page.isFormEditProvider().isDisplayed()) dx500Page.clickButtonEditProvider(DX500);
        assertTrue(dx500Page.clickSaveProvider(DX500), "После сохранения провайдера " + DX500 + "запись в таблице пропала");
    }

    void failedTestWithScreenshot(String message, boolean screenshot) {
        fail(message);
        if(screenshot) ScreenshotTests.screenshot();
    }
}

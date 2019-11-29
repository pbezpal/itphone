package DX500_After;

import AnnotationsTests.ServicesTests.EpicServicesTests;
import AnnotationsTests.ServicesTests.FeatureProviderDX500;
import HelperClasses.SSHManager;
import Pages.MonitoringPage;
import Pages.Providers.DX500Page;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.model.Status;
import org.junit.jupiter.api.*;

import static DataTests.Providers.PROVIDERS.PROVIDERS_ITEM_MENU;
import static DataTests.Providers.PROVIDER_DX500.*;
import static Pages.MonitoringPage.isCheckNotVisibleDownload;
import static Pages.Providers.DX500Page.dx500Page;
import static Pages.Providers.ProvidersPage.isCheckProviderPage;
import static org.junit.jupiter.api.Assertions.*;

@EpicServicesTests
@FeatureProviderDX500
public class CONFIG_DX500_Test {

    private boolean TEST_STATUS;
    private String TEST_MESSAGE;
    private boolean screenshot;

    @BeforeEach
    void setUp(){
        if( ! isCheckProviderPage().isDisplayed()) dx500Page = (DX500Page) MonitoringPage.openSectionWEB(PROVIDERS_ITEM_MENU, DX500_TYPE_PROVIDER);
        if( dx500Page == null ) dx500Page = DX500Page.getInstance();
        assertTrue(isCheckNotVisibleDownload(), "Невозможно продолжать тестирование, СУ недоступно");
        assertTrue(isCheckProviderPage().isDisplayed(), "Ошибка при переходе на страницу провайдеров");
        assertTrue(dx500Page.isCheckProvider(DX500_TYPE_PROVIDER, 30000), "Провайдер DX500 не создан");
        TEST_STATUS = true;
        TEST_MESSAGE = "";
        screenshot = false;
    }

    @Story(value = "Конфигурацию сервера Ассистентов")
    @Description(value = "Проверяем, что конфигурация сервера Ассистентов сохранилась")
    @Disabled
    @Test
    public void test_DX500_Configuration_Booster(){
        if( ! SSHManager.isCheckQuerySSH(BOOSTER_CONFIG_DB_IP)) failedTestWithScreenshot("Неверное значение в параметре db_ip");
        if( ! SSHManager.isCheckQuerySSH(BOOSTER_CONFIG_DB_PORT)) failedTestWithScreenshot("Неверное значение в параметре db_port");
        if( ! SSHManager.isCheckQuerySSH(BOOSTER_CONFIG_ADAPTER_NAME)) failedTestWithScreenshot("Неверное значение в параметре adapter_name");
        if( ! SSHManager.isCheckQuerySSH(BOOSTER_CONFIG_STATION)) failedTestWithScreenshot("Неверное значение в параметре station");
        if( ! SSHManager.isCheckQuerySSH(BOOSTER_CONFIG_IP)) failedTestWithScreenshot("Неверное значение в параметре ip");
        if( !SSHManager.isCheckQuerySSH(BOOSTER_CONFIG_PORT)) failedTestWithScreenshot("Неверное значение в параметре port");
    }

    @Story(value = "Конфигурацию сервера Пультов")
    @Description(value = "Проверяем, что конфигурация сервера Пультов сохранилась")
    @Test
    public void test_DX500_Configuration_Pult(){
        if( ! SSHManager.isCheckQuerySSH(PULT_CONFIG_CONTACT_IP)) failedTestWithScreenshot("Неверное значение в параметре db_ip");
        if( ! SSHManager.isCheckQuerySSH(PULT_CONFIG_CONTACT_PORT)) failedTestWithScreenshot("Неверное значение в параметре db_port");
        if( ! SSHManager.isCheckQuerySSH(PULT_CONFIG_SIG_GATE_PORT)) failedTestWithScreenshot("Неверное значение в параметре sig_gate_port");
        if( ! SSHManager.isCheckQuerySSH(PULT_CONFIG_IP)) failedTestWithScreenshot("Неверное значение в параметре ip");
        if( ! SSHManager.isCheckQuerySSH(PULT_CONFIG_PORT)) failedTestWithScreenshot("Неверное значение в параметре port");
        if( !SSHManager.isCheckQuerySSH(PULT_CONFIG_MEDIA_GATE_PORT)) failedTestWithScreenshot("Неверное значение в параметре media_gate_port");
        //Проверка настроек SMG
        if( ! SSHManager.isCheckQuerySSH(PULT_SMG_ENABLE)) failedTestWithScreenshot("Неверное значение в параметре SMG1_ENABLE файла /etc/smg.cfg");
        if( ! SSHManager.isCheckQuerySSH(PULT_SMG_SGDEV)) failedTestWithScreenshot("Неверное значение в параметре SG1_DEV файла /etc/smg.cfg");
        if( !SSHManager.isCheckQuerySSH(PULT_SMG_MGDEV)) failedTestWithScreenshot("Неверное значение в параметре MG1_DEV файла /etc/smg.cfg");
    }

    @Story(value = "Конфигурацию сервера SIP(абон)-DX")
    @Description(value = "Проверяем, что конфигурация сервера SIP(абон)-DX сохранилась")
    @Test
    public void test_DX500_Configuration_SIP_Abon_DX(){
        if( ! SSHManager.isCheckQuerySSH(SIP_ABON_DX_CONFIG_DB_IP)) failedTestWithScreenshot("Неверное значение в параметре db_ip");
        if( ! SSHManager.isCheckQuerySSH(SIP_ABON_DX_CONFIG_DB_PORT)) failedTestWithScreenshot("Неверное значение в параметре db_port");
        if( ! SSHManager.isCheckQuerySSH(SIP_ABON_DX_CONFIG_SIG_GATE_IP)) failedTestWithScreenshot("Неверное значение в параметре sig_gate_ip");
        if( ! SSHManager.isCheckQuerySSH(SIP_ABON_DX_CONFIG_SIG_GATE_PORT)) failedTestWithScreenshot("Неверное значение в параметре sig_gate_port");
        if( ! SSHManager.isCheckQuerySSH(SIP_ABON_DX_CONFIG_IP)) failedTestWithScreenshot("Неверное значение в параметре ip");
        if( ! SSHManager.isCheckQuerySSH(SIP_ABON_DX_CONFIG_PORT)) failedTestWithScreenshot("Неверное значение в параметре port");
        if( !SSHManager.isCheckQuerySSH(SIP_ABON_DX_CONFIG_MEDIA_GATE_IP)) failedTestWithScreenshot("Неверное значение в параметре media_gate_ip");
        if( !SSHManager.isCheckQuerySSH(SIP_ABON_DX_CONFIG_MEDIA_GATE_PORT)) failedTestWithScreenshot("Неверное значение в параметре media_gate_port");
        //Проверка настроек SMG
        if( ! SSHManager.isCheckQuerySSH(SIP_ABON_DX_CONFIG_SMG_ENABLE)) failedTestWithScreenshot("Неверное значение в параметре SMG0_ENABLE файла /etc/smg.cfg");
        if( ! SSHManager.isCheckQuerySSH(SIP_ABON_DX_CONFIG_SGDEV)) failedTestWithScreenshot("Неверное значение в параметре SG0_DEV файла /etc/smg.cfg");
        if( !SSHManager.isCheckQuerySSH(SIP_ABON_DX_CONFIG_MGDEV)) failedTestWithScreenshot("Неверное значение в параметре MG0_DEV файла /etc/smg.cfg");
    }

    @Story(value = "Конфигурацию сервера SIP Пультов")
    @Description(value = "Проверяем, что конфигурация сервера SIP Пультов сохранилась")
    @Test
    public void test_DX500_Configuration_SIP_Pult(){
        if( ! SSHManager.isCheckQuerySSH(SIP_PULT_CONFIG_DB_IP)) failedTestWithScreenshot("Неверное значение в параметре db_ip");
        if( ! SSHManager.isCheckQuerySSH(SIP_PULT_CONFIG_DB_PORT)) failedTestWithScreenshot("Неверное значение в параметре db_port");
        if( ! SSHManager.isCheckQuerySSH(SIP_PULT_CONFIG_SG_PORT)) failedTestWithScreenshot("Неверное значение в параметре sg_port");
        if( ! SSHManager.isCheckQuerySSH(SIP_PULT_CONFIG_IP)) failedTestWithScreenshot("Неверное значение в параметре ip");
        if( ! SSHManager.isCheckQuerySSH(SIP_PULT_CONFIG_PORT)) failedTestWithScreenshot("Неверное значение в параметре port");
        if( !SSHManager.isCheckQuerySSH(SIP_PULT_CONFIG_MG_PORT)) failedTestWithScreenshot("Неверное значение в параметре mg_port");
        //Проверка настроек SMG
        if( ! SSHManager.isCheckQuerySSH(SIP_PULT_CONFIG_SMG_ENSBLE)) failedTestWithScreenshot("Неверное значение в параметре SMG3_ENABLE файла /etc/smg.cfg");
        if( ! SSHManager.isCheckQuerySSH(SIP_PULT_CONFIG_SGDEV)) failedTestWithScreenshot("Неверное значение в параметре SG3_DEV файла /etc/smg.cfg");
        if( !SSHManager.isCheckQuerySSH(SIP_PULT_CONFIG_MGDEV)) failedTestWithScreenshot("Неверное значение в параметре MG3_DEV файла /etc/smg.cfg");
    }

    @Step(value = "Маршрутизация вызовов")
    @Description(value = "Проверяе, есть ли маршрутизация вызовов для провайдера")
    @Disabled
    @Test
    void test_DX500_Route_Calls(){
        if (!dx500Page.isMySqlDialplan()) {
            failedTestWithScreenshot("Маршрут для DX500 не был добавлен в БД MySql");
        }
    }

    @Story(value = "Конфигурацию сервера Занятости")
    @Description(value = "Проверяем, что конфигурация сервера Занятости сохранилась")
    @Test
    void test_DX500_Configuration_Busy(){
        if( ! SSHManager.isCheckQuerySSH(BUSY_CONFIG_ADAPTER_NAME)) failedTestWithScreenshot("Неверное значение в параметре adapter_name");
    }

    @AfterEach
    void tearDown(){
        assertTrue(TEST_STATUS, TEST_MESSAGE);
    }

    void failedTestWithScreenshot(String message) {
        Allure.step(message, Status.FAILED);
        TEST_STATUS = false;
        TEST_MESSAGE = TEST_MESSAGE + "\n" + message;
    }

}

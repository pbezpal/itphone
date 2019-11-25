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
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static DataTests.Providers.PROVIDERS.PROVIDERS_ITEM_MENU;
import static DataTests.Providers.PROVIDER_DX500.*;
import static Pages.Providers.DX500Page.dx500Page;
import static RecourcesTests.BeforeSettingsTests.StartTests;
import static java.time.Duration.ofSeconds;
import static org.junit.jupiter.api.Assertions.*;

@EpicServicesTests
@FeatureProviderDX500
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
@ExtendWith(TestRules.class)
public class Test_DX500 {

    private boolean TEST_STATUS;
    private String TEST_MESSAGE;
    private boolean screenshot;

    public Test_DX500() {}

    @BeforeEach
    void setUp(){
        StartTests();
        if( ! ProvidersPage.isCheckProviderPage().isDisplayed()) dx500Page = (DX500Page) MonitoringPage.openSectionWEB(PROVIDERS_ITEM_MENU, DX500_TYPE_PROVIDER);
        if( dx500Page == null ) dx500Page = DX500Page.getInstance();
        TEST_STATUS = true;
        TEST_MESSAGE = "";
        screenshot = false;
    }

    @Story(value = "Добавление провайдера DX500")
    @Description(value = "Проверяем, что через СУ можно успешно добавить провайдер DX500")
    @Test
    public void test_Add_Provider_DX500() {
        assertTimeout(ofSeconds(600), () -> {
            if (!dx500Page.isCheckProvider(DX500_TYPE_PROVIDER))
                assertTrue(dx500Page.addDX500Provider(), "Провайдер DX500 не был добавлен");
            if (!dx500Page.isMySqlDialplan()) {
                failedTestWithScreenshot("Маршрут для DX500 не был добавлен в БД MySql", false);
            }
        }, () -> "Время теста больше 10 минут");
    }

    @Story(value = "Конфигурацию сервера Ассистентов")
    @Description(value = "Проверяем, что конфигурация сервера Ассистентов сохранилась")
    @Test
    public void test_DX500_Configuration_Booster(){
        if( ! SSHManager.isCheckQuerySSH(BOOSTER_CONFIG_DB_IP)) failedTestWithScreenshot("Неверное значение в параметре db_ip", false);
        if( ! SSHManager.isCheckQuerySSH(BOOSTER_CONFIG_DB_PORT)) failedTestWithScreenshot("Неверное значение в параметре db_port", false);
        if( ! SSHManager.isCheckQuerySSH(BOOSTER_CONFIG_ADAPTER_NAME)) failedTestWithScreenshot("Неверное значение в параметре adapter_name", false);
        if( ! SSHManager.isCheckQuerySSH(BOOSTER_CONFIG_STATION)) failedTestWithScreenshot("Неверное значение в параметре station", false);
        if( ! SSHManager.isCheckQuerySSH(BOOSTER_CONFIG_IP)) failedTestWithScreenshot("Неверное значение в параметре ip", false);
        if( !SSHManager.isCheckQuerySSH(BOOSTER_CONFIG_PORT)) failedTestWithScreenshot("Неверное значение в параметре port", false);
    }

    @Story(value = "Конфигурацию сервера Пультов")
    @Description(value = "Проверяем, что конфигурация сервера Пультов сохранилась")
    @Test
    public void test_DX500_Configuration_Pult(){
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

    @Story(value = "Конфигурацию сервера SIP(абон)-DX")
    @Description(value = "Проверяем, что конфигурация сервера SIP(абон)-DX сохранилась")
    @Test
    public void test_DX500_Configuration_SIP_Abon_DX(){
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

    @Story(value = "Конфигурацию сервера SIP Пультов")
    @Description(value = "Проверяем, что конфигурация сервера SIP Пультов сохранилась")
    @Test
    public void test_DX500_Configuration_SIP_Pult(){
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

    @AfterEach
    void tearDown(){
        if( ! TEST_STATUS){
            fail(TEST_MESSAGE, new Exception(String.valueOf(screenshot)));
        }
    }

    void failedTestWithScreenshot(String message, boolean screen) {
        Allure.step(message, Status.FAILED);
        TEST_STATUS = false;
        TEST_MESSAGE = TEST_MESSAGE + "\n" + message;
        screenshot = screen;
    }
}

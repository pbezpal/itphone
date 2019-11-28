package Providers_After;

import AnnotationsTests.Monitoring.EpicMonitoringTests;
import AnnotationsTests.Monitoring.FeatureStatusServers;
import HelperClasses.SSHManager;
import HelperClasses.ScreenshotTests;
import Pages.MonitoringPage;
import RecourcesTests.TestRules;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static DataTests.OPENSIPS.*;
import static DataTests.Providers.PROVIDER_DX500.*;
import static Pages.MonitoringPage.*;
import static Pages.Providers.DX500Page.isCheckStartServers;
import static RecourcesTests.BeforeSettingsTests.StartTests;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.refresh;
import static org.junit.jupiter.api.Assertions.*;

@EpicMonitoringTests
@FeatureStatusServers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Test_SectionMonitoring {

    private static boolean TEST_STATUS;
    private static String TEST_MESSAGE;
    private String filename;
    private static final String labelContacts = "сервер контактов:";
    private static final String labelStation = "соединение со станцией:";
    private static final String labelLec = "линейный эхо-компенсатор:";
    private static final String labelBusy = "матрица занятости:";

    @BeforeEach
    void setUp(){
        StartTests();
        if( ! isSectionMonitoring()) MonitoringPage.clickButtonMonitoringPage();
        assertTrue(isCheckNotVisibleDownload(),"Невозможно продолжать тестирование, СУ недоступно");
        assertTrue(isCheckNotVisibleElement(),"Невозможно продолжать тестирование, СУ недоступно");
        assertTrue(isSectionMonitoring(), "Не удалось перейти в раздел Мониторинг");
        TEST_STATUS = true;
    }

    @Story(value = "Статус SIP сервера")
    @Description(value = "Проверяем корректное отображение статуса SIP сервера")
    @Order(1)
    @Test
    void test_Status_SIP_Server(){
        getStatusServer(OPENSIPS_MODULE_ID, OPENSIPS_ITEM_MENU);
    }

    /*@Story(value = "Статус сервера Ассистентов")
    @Description(value = "Проверяем корректное отображение статуса сервера Ассистентов")
    @Order(2)
    @Test
    void test_Booster_Status_Server(){
        filename = new Object(){}.getClass().getEnclosingMethod().getName();
        getStatusServer(DX500_BOOSTER, DX500_BOOSTER_HEAD_MODULE);
    }

    @Story(value = "Статус сервера контактов на сервере Ассистентов")
    @Description(value = "Проверяем корректное отображение статус сервера контактов на сервере Ассистентов")
    @Order(3)
    @Test
    void test_Booster_Status_Server_Contacts(){
        filename = new Object(){}.getClass().getEnclosingMethod().getName();
        getStatusArticleModule(DX500_BOOSTER, DX500_BOOSTER_HEAD_MODULE, BOOSTER_CONNECT_CONTACTS, labelContacts);
    }

    @Story(value = "Статус станции на сервере Ассистентов")
    @Description(value = "Проверяем корректное отображение статус станции на сервере Ассистентов")
    @Order(4)
    @Test
    void test_Booster_Status_Connect_Station(){
        filename = new Object(){}.getClass().getEnclosingMethod().getName();
        getStatusArticleModule(DX500_BOOSTER, DX500_BOOSTER_HEAD_MODULE, BOOSTER_CONNECT_STATION, labelStation);
    }


    @Story(value = "Статус сервера Пультов")
    @Description(value = "Проверяем корректное отображение статуса сервера Пультов")
    @Order(5)
    @Test
    void test_Pult_Status_Server(){
        filename = new Object(){}.getClass().getEnclosingMethod().getName();
        getStatusServer(DX500_PULT, DX500_PULT_HEAD_MODULE);
    }

    @Story(value = "Статус сервера контактов на сервере Пультов")
    @Description(value = "Проверяем корректное отображение статус сервера контактов на сервере Пультов")
    @Order(6)
    @Test
    void test_Pult_Status_Server_Contacts(){
        filename = new Object(){}.getClass().getEnclosingMethod().getName();
        getStatusArticleModule(DX500_PULT, DX500_PULT_HEAD_MODULE, PULT_CONNECT_CONTACTS, labelContacts);
    }

    @Story(value = "Статус станции на сервере Пультов")
    @Description(value = "Проверяем корректное отображение статус станции на сервере Пультов")
    @Order(7)
    @Test
    void test_Pult_Status_Connect_Station(){
        filename = new Object(){}.getClass().getEnclosingMethod().getName();
        getStatusArticleModule(DX500_PULT, DX500_PULT_HEAD_MODULE, PULT_CONNECT_STATION, labelStation);
    }

    @Story(value = "Статус конвертера Lan/E1 сервере Пультов")
    @Description(value = "Проверяем корректное отображение статуса конвертера Lan/E1 сервера Пультов")
    @Order(8)
    @Test
    void test_Pult_Status_Converter(){
        filename = new Object(){}.getClass().getEnclosingMethod().getName();
        getStatusConverter(DX500_PULT, DX500_PULT_SMG);
    }

    @Story(value = "Статус сервера SIP(абон)-DX")
    @Description(value = "Проверяем корректное отображение статуса сервера SIP(абон)-DX")
    @Order(9)
    @Test
    void test_SIP_Abon_DX_Status_Server(){
        filename = new Object(){}.getClass().getEnclosingMethod().getName();
        getStatusServer(DX500_SIP_ABON_DX, DX500_SIP_ABON_DX_HEADER_MODULE);
    }

    @Story(value = "Статус конвертера Lan/E1 на сервере SIP(абон)-DX")
    @Description(value = "Проверяем корректное отображение статуса конвертера Lan/E1 сервера SIP(абон)-DX")
    @Order(10)
    @Test
    void test_SIP_Abon_DX_Status_Converter(){
        filename = new Object(){}.getClass().getEnclosingMethod().getName();
        getStatusConverter(DX500_SIP_ABON_DX, DX500_SIP_ABON_DX_SMG);
    }

    @Story(value = "Статус сервера SIP Пульт")
    @Description(value = "Проверяем корректное отображение статуса сервера SIP Пульт")
    @Order(11)
    @Test
    void test_SIP_Pult_Status_Server(){
        filename = new Object(){}.getClass().getEnclosingMethod().getName();
        getStatusServer(DX500_SIP_PULT, DX500_SIP_PULT_HEAD_MODULE);
    }

    @Story(value = "Статус сервера контактов на сервере SIP Пульт")
    @Description(value = "Проверяем корректное отображение статус сервера контактов на сервере SIP Пульт")
    @Order(12)
    @Test
    void test_SIP_Pult_Status_Server_Contacts(){
        filename = new Object(){}.getClass().getEnclosingMethod().getName();
        getStatusArticleModule(DX500_SIP_PULT, DX500_SIP_PULT_HEAD_MODULE, SIP_PULT_CONNECT_CONTACTS, labelContacts);
    }

    @Story(value = "Статус станции на сервере SIP Пульт")
    @Description(value = "Проверяем корректное отображение статус станции на сервере SIP Пульт")
    @Order(13)
    @Test
    void test_SIP_Pult_Status_Connect_Station(){
        filename = new Object(){}.getClass().getEnclosingMethod().getName();
        getStatusArticleModule(DX500_SIP_PULT, DX500_SIP_PULT_HEAD_MODULE, SIP_PULT_CONNECT_STATION, labelStation);
    }

    @Story(value = "Статус линейного эхо-компенсатора на сервере SIP Пульт")
    @Description(value = "Проверяем корректное отображение статуса линейного эхо-компенсатора на сервере SIP Пульт")
    @Order(14)
    @Test
    void test_SIP_Pult_Status_Lec(){
        filename = new Object(){}.getClass().getEnclosingMethod().getName();
        getStatusArticleModule(DX500_SIP_PULT, DX500_SIP_PULT_HEAD_MODULE, SIP_PULT_STATUS_LEC, labelLec);
    }

    @Story(value = "Статус матрицы занятости на сервере SIP Пульт")
    @Description(value = "Проверяем корректное отображение статуса матрицы занятости на сервере SIP Пульт")
    @Order(15)
    @Test
    void test_SIP_Pult_Connect_BUSY(){
        filename = new Object(){}.getClass().getEnclosingMethod().getName();
        getStatusArticleModule(DX500_SIP_PULT, DX500_SIP_PULT_HEAD_MODULE, SIP_PULT_CONNECT_BUSY, labelBusy);
    }

    @Story(value = "Статус конвертера Lan/E1 на сервере SIP Пульт")
    @Description(value = "Проверяем корректное отображение статуса конвертера Lan/E1 сервера Пульт")
    @Order(16)
    @Test
    void test_SIP_Pult_Status_Converter(){
        filename = new Object(){}.getClass().getEnclosingMethod().getName();
        getStatusConverter(DX500_SIP_PULT, DX500_SIP_PULT_SMG);
    }

    @Story(value = "Статус сервера Занятости")
    @Description(value = "Проверяем корректное отображение статуса сервера Занятости")
    @Order(17)
    @Test
    void test_Busy_Status_Server(){
        filename = new Object(){}.getClass().getEnclosingMethod().getName();
        getStatusServer(DX500_BUSY, DX500_BUSY_HEAD_MODULE);
    }

    @Story(value = "Статус сетевого интерфейса на сервере Занятости")
    @Description(value = "Проверяем корректное отображение статус сетевого интерфейса на сервере Занятости")
    @Order(18)
    @Test
    void test_Busy_Status_Network(){
        filename = new Object(){}.getClass().getEnclosingMethod().getName();
        assertTrue(isCheckStartServers(DX500_BUSY), "Сервер не запущен!!!");
        assertTrue(isCheckNotVisibleElement(),"Невозможно получить статус интервейса сервера занятости");
        this.TEST_MESSAGE = MonitoringPage.isAdapterName(DX500_BUSY, DX500_BUSY_HEAD_MODULE);
        reportArticleModule();
    }

    @Story(value = "Статус станции на сервере Занятости")
    @Description(value = "Проверяем корректное отображение статус станции на сервере Занятости")
    @Order(19)
    @Test
    void test_Busy_Status_Connect_Station(){
        filename = new Object(){}.getClass().getEnclosingMethod().getName();
        getStatusArticleModule(DX500_BUSY, DX500_BUSY_HEAD_MODULE, BUSY_CONNECT_STATION, labelStation);
    }

    @Story(value = "Переход на страницу 'Управление кластерами'")
    @Description(value = "Проверяем, что на странице Мониторинг корректно работает переход на страницу 'Управление кластерами'")
    @Order(20)
    @Test
    void test_Management_Booster(){
        filename = new Object(){}.getClass().getEnclosingMethod().getName();
        assertTrue(isCheckStartServers(DX500_BOOSTER), "Сервер не запущен!!!");
        assertTrue(clickLinkSimple().isManagementBoosterPage(), "Ошибка при переходе на страницу 'Управление кластерами'");
    }*/

    public void reportArticleModule(){
        ScreenshotTests.AScreenshot(filename, getArticleModule());
        assertEquals(this.TEST_MESSAGE, null, this.TEST_MESSAGE);
    }

    public void reportModule(){
        ScreenshotTests.AScreenshot(filename);
        assertTrue(this.TEST_STATUS, this.TEST_MESSAGE);
    }

    public void getStatusServer(String server, String headModule){
        assertTrue(isCheckNotVisibleElement(),"Невозможно получить статус пераметра " + server);
        assertTrue(isCheckStartServers(server), "Сервер не запущен!!!");
        if(isCheckStartServers(server) && ! MonitoringPage.isStatusService(server, headModule) || ! MonitoringPage.isCheckModuleStatusServer(server)){
            clickUpdateMonitoring();
            assertTrue(isCheckNotVisibleElement(),"Невозможно получить статус пераметра " + server);
        }
        boolean serverStatus = isCheckStartServers(server);
        boolean tableStatusServer = MonitoringPage.isStatusService(server, headModule);
        boolean moduleStatusServer = MonitoringPage.isCheckModuleStatusServer(server);
        if(serverStatus && moduleStatusServer && tableStatusServer) TEST_STATUS = true;
        else if( ! serverStatus && (moduleStatusServer || tableStatusServer)){
            this.TEST_STATUS = false;
            this.TEST_MESSAGE = "На сервере не запущена служба " + server + ", однако в СУ отображется статус - ОК";
        }else if(serverStatus && ( ! moduleStatusServer || ! tableStatusServer)) {
            this.TEST_STATUS = false;
            this.TEST_MESSAGE = "На сервере запущена служба " + server + ", однако в СУ отображается статус - NOK";
        }else{
            this.TEST_STATUS = false;
            this.TEST_MESSAGE = "На сервере не запущена служба " + server + " и в СУ отображается статус сервера - NOK";
        }
        reportModule();
    }

    public void getStatusConverter(String server, String SMG){
        assertTrue(isCheckNotVisibleElement(),"Невозможно получить статус SMG");
        assertTrue(isCheckStartServers(server), "Сервер не запущен!!!");
        boolean converterStatus = SSHManager.isCheckQuerySSH("smg.status s | grep 231" + SMG + " -A 7 | grep 'lapd_ready: establish'");
        boolean moduleStatusConverter = MonitoringPage.isCheckModuleConverterLanE1(server);
        boolean tableStatusConverter = MonitoringPage.isCheckTableConverterLanE1(DX500_SIP_PULT);
        if(converterStatus && moduleStatusConverter && tableStatusConverter) TEST_STATUS = true;
        else if( ! converterStatus && (moduleStatusConverter || tableStatusConverter)){
            this.TEST_STATUS = false;
            this.TEST_MESSAGE = "На серере статус SMG: " + SMG + " - disconnected, однако в СУ отображется статус SMG - establish";
        }else if(converterStatus && ( ! moduleStatusConverter || ! tableStatusConverter)) {
            this.TEST_STATUS = false;
            this.TEST_MESSAGE = "На сервере статус SMG: " + SMG + " - establish, однако в СУ отображается статус SMG - disconnected";
        }else{
            this.TEST_STATUS = false;
            this.TEST_MESSAGE = "На сервере статус SMG: " + SMG + " - disconnected и в СУ отображается статус SMG - disconnected. Проверьте соединение со станцией!!!";
        }
        reportModule();
    }

    void getStatusArticleModule(String service, String headModule, String command, String label){
        assertTrue(isCheckStartServers(service), "Сервер не запущен!!!");
        assertTrue(isCheckNotVisibleElement(), "Не удалось получить статус для " + label + " для сервера " + service);
        this.TEST_MESSAGE = MonitoringPage.isConnectService(service, headModule, command, label);
        reportArticleModule();
    }

    @AfterAll
    static void afterAllTests(){
        refresh();
    }
}

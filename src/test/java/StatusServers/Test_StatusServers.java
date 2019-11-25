package StatusServers;

import AnnotationsTests.ServicesTests.EpicServicesTests;
import AnnotationsTests.ServicesTests.FeatureStatusServers;
import HelperClasses.SSHManager;
import HelperClasses.ScreenshotTests;
import Pages.MonitoringPage;
import Pages.Providers.DX500Page;
import RecourcesTests.TestRules;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static DataTests.Providers.PROVIDER_DX500.*;
import static Pages.MonitoringPage.*;
import static RecourcesTests.BeforeSettingsTests.StartTests;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@EpicServicesTests
@FeatureStatusServers
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
@ExtendWith(TestRules.class)
public class Test_StatusServers {

    private static boolean TEST_STATUS;
    private static String TEST_MESSAGE;
    private static String filename;
    private static final String labelContacts = "сервер контактов:";
    private static final String labelStation = "соединение со станцией:";
    private static final String labelLec = "линейный эхо-компенсатор:";
    private static final String labelBusy = "матрица занятости:";

    @BeforeEach
    void setUp(){
        StartTests();
        if( ! isSectionMonitoring()) MonitoringPage.clickButtonMonitoringPage();
        if( ! isCheckNotVisibleDownload()) fail("Невозможно продолжать тестирование, СУ недоступно", new Exception("DOWNLOAD"));
        if( ! isCheckNotVisibleElement()) fail("Невозможно продолжать тестирование, СУ недоступно", new Exception("DOWNLOAD"));
        assertTrue(isSectionMonitoring(), "Не удалось перейти в раздел Мониторинг");
        TEST_STATUS = true;
    }

    @Story(value = "Статус сервера Ассистентов")
    @Description(value = "Проверяем корректное отображение статуса сервера Ассистентов")
    @Test
    void test_Booster_Status_Server(){
        getStatusServer(DX500_BOOSTER, DX500_BOOSTER_HEAD_MODULE);
    }

    @Story(value = "Статус сервера контактов на сервере Ассистентов")
    @Description(value = "Проверяем корректное отображение статус сервера контактов на сервере Ассистентов")
    @Test
    void test_Booster_Status_Server_Contacts(){
        getStatusArticleModule(DX500_BOOSTER, DX500_BOOSTER_HEAD_MODULE, BOOSTER_CONNECT_CONTACTS, labelContacts);
    }

    @Story(value = "Статус станции на сервере Ассистентов")
    @Description(value = "Проверяем корректное отображение статус станции на сервере Ассистентов")
    @Test
    void test_Booster_Status_Connect_Station(){
        getStatusArticleModule(DX500_BOOSTER, DX500_BOOSTER_HEAD_MODULE, BOOSTER_CONNECT_STATION, labelStation);
    }


    @Story(value = "Статус сервера Пультов")
    @Description(value = "Проверяем корректное отображение статуса сервера Пультов")
    @Test
    void test_Pult_Status_Server(){
        getStatusServer(DX500_PULT, DX500_PULT_HEAD_MODULE);
    }

    @Story(value = "Статус сервера контактов на сервере Пультов")
    @Description(value = "Проверяем корректное отображение статус сервера контактов на сервере Пультов")
    @Test
    void test_Pult_Status_Server_Contacts(){
        getStatusArticleModule(DX500_PULT, DX500_PULT_HEAD_MODULE, PULT_CONNECT_CONTACTS, labelContacts);
    }

    @Story(value = "Статус станции на сервере Пультов")
    @Description(value = "Проверяем корректное отображение статус станции на сервере Пультов")
    @Test
    void test_Pult_Status_Connect_Station(){
        getStatusArticleModule(DX500_PULT, DX500_PULT_HEAD_MODULE, PULT_CONNECT_STATION, labelStation);
    }

    @Story(value = "Статус конвертера Lan/E1 сервере Пультов")
    @Description(value = "Проверяем корректное отображение статуса конвертера Lan/E1 сервера Пультов")
    @Test
    void test_Pult_Status_Converter(){
        getStatusConverter(DX500_PULT, DX500_PULT_SMG);
    }

    @Story(value = "Статус сервера SIP(абон)-DX")
    @Description(value = "Проверяем корректное отображение статуса сервера SIP(абон)-DX")
    @Test
    void test_SIP_Abon_DX_Status_Server(){
        getStatusServer(DX500_SIP_ABON_DX, DX500_SIP_ABON_DX_HEADER_MODULE);
    }

    @Story(value = "Статус конвертера Lan/E1 сервере SIP(абон)-DX")
    @Description(value = "Проверяем корректное отображение статуса конвертера Lan/E1 сервера SIP(абон)-DX")
    @Test
    void test_SIP_Abon_DX_Status_Converter(){
        getStatusConverter(DX500_SIP_ABON_DX, DX500_SIP_ABON_DX_SMG);
    }

    @Story(value = "Статус сервера SIP Пульт")
    @Description(value = "Проверяем корректное отображение статуса сервера SIP Пульт")
    @Test
    void test_SIP_Pult_Status_Server(){
        getStatusServer(DX500_SIP_PULT, DX500_SIP_PULT_HEAD_MODULE);
    }

    @Story(value = "Статус сервера контактов на сервере SIP Пульт")
    @Description(value = "Проверяем корректное отображение статус сервера контактов на сервере SIP ekmn")
    @Test
    void test_SIP_Pult_Status_Server_Contacts(){
        getStatusArticleModule(DX500_SIP_PULT, DX500_SIP_PULT_HEAD_MODULE, SIP_PULT_CONNECT_CONTACTS, labelContacts);
    }

    @Story(value = "Статус станции на сервере SIP Пульт")
    @Description(value = "Проверяем корректное отображение статус станции на сервере SIP Пульт")
    @Test
    void test_SIP_Pult_Status_Connect_Station(){
        this.TEST_MESSAGE = MonitoringPage.isConnectService(DX500_SIP_PULT, DX500_SIP_PULT_HEAD_MODULE, SIP_PULT_CONNECT_STATION, labelStation);
        reportArticleModule();
    }

    @Story(value = "Статус линейного эхо-компенсатора на сервере SIP Пульт")
    @Description(value = "Проверяем корректное отображение статуса линейного эхо-компенсатора на сервере SIP Пульт")
    @Test
    void test_SIP_Pult_Status_Lec(){
        getStatusArticleModule(DX500_SIP_PULT, DX500_SIP_PULT_HEAD_MODULE, SIP_PULT_STATUS_LEC, labelLec);
    }

    @Story(value = "Статус матрицы занятости на сервере SIP Пульт")
    @Description(value = "Проверяем корректное отображение статуса матрицы занятости на сервере SIP Пульт")
    @Test
    void test_SIP_Pult_Connect_BUSY(){
        getStatusArticleModule(DX500_SIP_PULT, DX500_SIP_PULT_HEAD_MODULE, SIP_PULT_CONNECT_BUSY, labelBusy);
    }

    @Story(value = "Статус конвертера Lan/E1 сервере SIP Пульт")
    @Description(value = "Проверяем корректное отображение статуса конвертера Lan/E1 сервера Пульт")
    @Test
    void test_SIP_Pult_Status_Converter(){
        getStatusConverter(DX500_SIP_PULT, DX500_SIP_PULT_SMG);
    }

    @Story(value = "Статус сервера Занятости")
    @Description(value = "Проверяем корректное отображение статуса сервера Занятости")
    @Test
    void test_Busy_Status_Server(){
        getStatusServer(DX500_BUSY, DX500_BUSY_HEAD_MODULE);
    }

    @Story(value = "Статус сетевого интерфейса на сервере Занятости")
    @Description(value = "Проверяем корректное отображение статус сетевого интерфейса на сервере Занятости")
    @Test
    void test_Busy_Status_Network(){
        assertTrue(isCheckNotVisibleElement(),"Невозможно получить статус SMG");
        this.TEST_MESSAGE = MonitoringPage.isAdapterName(DX500_BUSY, DX500_BUSY_HEAD_MODULE);
        reportArticleModule();
    }

    @Story(value = "Статус станции на сервере Занятости")
    @Description(value = "Проверяем корректное отображение статус станции на сервере Занятости")
    @Test
    void test_Busy_Status_Connect_Station(){
        getStatusArticleModule(DX500_BUSY, DX500_BUSY_HEAD_MODULE, BUSY_CONNECT_STATION, labelStation);
    }

    public void reportArticleModule(){
        if(this.TEST_MESSAGE != null) {
            filename = new Object(){}.getClass().getEnclosingMethod().getName();
            ScreenshotTests.AScreenshot(filename, getArticleModule());
            fail(this.TEST_MESSAGE, new Exception("article module"));
        }
    }

    public void reportModule(){
        if( ! this.TEST_STATUS){
            filename = new Object(){}.getClass().getEnclosingMethod().getName();
            ScreenshotTests.AScreenshot(filename);
            fail(this.TEST_MESSAGE);
        }
    }

    public void getStatusServer(String server, String headModule){
        assertTrue(isCheckNotVisibleElement(),"Невозможно получить статус пераметра " + server);
        boolean serverStatus = DX500Page.isCheckStartServers(server);
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
        assertTrue(isCheckNotVisibleElement(), "Не удалось получить статус для " + label + " для сервера " + DX500_BOOSTER);
        this.TEST_MESSAGE = MonitoringPage.isConnectService(service, headModule, command, label);
        reportArticleModule();
    }
}

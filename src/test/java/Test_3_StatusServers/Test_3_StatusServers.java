package Test_3_StatusServers;

import AnnotationsTests.ServicesTests.EpicServicesTests;
import AnnotationsTests.ServicesTests.FeatureStatusServers;
import HelperClasses.SSHManager;
import HelperClasses.ScreenshotTests;
import Pages.MonitoringPage;
import Pages.Providers.DX500Page;
import RecourcesTests.BeforeEachTests;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import static DataTests.DataSipServer.commandStatusSIPServer;
import static DataTests.DataSipServer.serverNameModule;
import static DataTests.Providers.DataProviderDX500.*;
import static com.codeborne.selenide.Selenide.refresh;
import static org.junit.jupiter.api.Assertions.fail;

@EpicServicesTests
@FeatureStatusServers
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
@ExtendWith(BeforeEachTests.class)
public class Test_3_StatusServers {

    private boolean serverStatus;
    private String messageStatus;

    @Story(value = "Статус SIP сервера")
    @Description(value = "Проверяем корректное отображение статуса SIP сервера")
    @Test
    void test_Status_SIP_Server(){
        getStatusServer(
                SSHManager.isCheckQuerySSH(commandStatusSIPServer),
                MonitoringPage.isCheckModuleStatusServer(serverNameModule),
                MonitoringPage.isCheckTableStatusServer(),
                serverNameModule);
        if( ! MonitoringPage.isCheckModuleStatusServer(serverNameModule) || ! MonitoringPage.isCheckTableStatusServer()){
            refresh();
            getStatusServer(
                    SSHManager.isCheckQuerySSH(commandStatusSIPServer),
                    MonitoringPage.isCheckModuleStatusServer(serverNameModule),
                    MonitoringPage.isCheckTableStatusServer(),
                    serverNameModule);
        }
    }

    @Story(value = "Статус сервера Ассистентов")
    @Description(value = "Проверяем корректное отображение статуса сервера Ассистентов")
    @Test
    void test_Booster_Status_Server(){
        getStatusServer(
                DX500Page.isCheckStartServers(serverBooster),
                MonitoringPage.isCheckModuleStatusServer(serverBooster),
                MonitoringPage.isStatusService(serverBooster),
                serverBooster);
        if( ! MonitoringPage.isCheckModuleStatusServer(serverBooster) || ! MonitoringPage.isStatusService(serverBooster)){
            refresh();
            getStatusServer(
                    DX500Page.isCheckStartServers(serverBooster),
                    MonitoringPage.isCheckModuleStatusServer(serverBooster),
                    MonitoringPage.isStatusService(serverBooster),
                    serverBooster);
        }
    }

    @Story(value = "Статус сервера контактов на сервере Ассистентов")
    @Description(value = "Проверяем корректное отображение статус сервера контактов на сервере Ассистентов")
    @Test
    void test_Booster_Status_Server_Contacts(){
        this.messageStatus = MonitoringPage.isConnectServerContacts(serverBooster, controlPortBooster);
        if(this.messageStatus != null) this.serverStatus = false;
    }

    @Story(value = "Статус станции на сервере Ассистентов")
    @Description(value = "Проверяем корректное отображение статус станции на сервере Ассистентов")
    @Test
    void test_Booster_Status_Connect_Station(){
        this.messageStatus = MonitoringPage.isConnectStation(serverBooster, boosterConnectStation);
        if(this.messageStatus != null) this.serverStatus = false;
    }


    @Story(value = "Статус сервера Пультов")
    @Description(value = "Проверяем корректное отображение статуса сервера Пультов")
    @Test
    void test_Pult_Status_Server(){
        getStatusServer(
                DX500Page.isCheckStartServers(serverPult),
                MonitoringPage.isCheckModuleStatusServer(serverPult),
                MonitoringPage.isStatusService(serverPult),
                serverPult);
        if( ! MonitoringPage.isCheckModuleStatusServer(serverPult) || ! MonitoringPage.isStatusService(serverPult)){
            refresh();
            getStatusServer(
                    DX500Page.isCheckStartServers(serverPult),
                    MonitoringPage.isCheckModuleStatusServer(serverPult),
                    MonitoringPage.isStatusService(serverPult),
                    serverPult);
        }
    }

    @Story(value = "Статус сервера контактов на сервере Пультов")
    @Description(value = "Проверяем корректное отображение статус сервера контактов на сервере Пультов")
    @Test
    void test_Pult_Status_Server_Contacts(){
        this.messageStatus = MonitoringPage.isConnectServerContacts(serverPult, controlPortPult);
        if(this.messageStatus != null) this.serverStatus = false;
    }

    @Story(value = "Статус станции на сервере Пульто")
    @Description(value = "Проверяем корректное отображение статус станции на сервере Пультов")
    @Test
    void test_Pult_Status_Connect_Station(){
        this.messageStatus = MonitoringPage.isConnectStation(serverPult, pultConnectStation);
        if(this.messageStatus != null) this.serverStatus = false;
    }

    @Story(value = "Статус конвертера Lan/E1 сервере Пультов")
    @Description(value = "Проверяем корректное отображение статуса конвертера Lan/E1 сервера Пультов")
    @Test
    void test_Pult_Status_Converter(){
        getStatusConverter(
                SSHManager.isCheckQuerySSH("smg.status s | grep 231" + SMGPult + " -A 7 | grep 'lapd_ready: establish'"),
                MonitoringPage.isCheckModuleConverterLanE1(serverPult),
                MonitoringPage.isCheckTableConverterLanE1(serverPult),
                SMGPult);
        if( ! MonitoringPage.isCheckModuleConverterLanE1(serverPult) || ! MonitoringPage.isCheckTableConverterLanE1(serverPult)){
            refresh();
            getStatusConverter(
                    SSHManager.isCheckQuerySSH("smg.status s | grep 231" + SMGPult + " -A 7 | grep 'lapd_ready: establish'"),
                    MonitoringPage.isCheckModuleConverterLanE1(serverPult),
                    MonitoringPage.isCheckTableConverterLanE1(serverPult),
                    SMGPult);
        }
    }

    @Story(value = "Статус сервера SIP(абон)-DX")
    @Description(value = "Проверяем корректное отображение статуса сервера SIP(абон)-DX")
    @Test
    void test_SIP_Abon_DX_Status_Server(){
        getStatusServer(
                DX500Page.isCheckStartServers(serverSIPModule),
                MonitoringPage.isCheckModuleStatusServer(serverSIPModule),
                MonitoringPage.isStatusService(serverSIPModule),
                serverSIPModule);
        if( ! MonitoringPage.isCheckModuleStatusServer(serverSIPModule) || ! MonitoringPage.isStatusService(serverSIPModule)){
            refresh();
            getStatusServer(
                    DX500Page.isCheckStartServers(serverSIPModule),
                    MonitoringPage.isCheckModuleStatusServer(serverSIPModule),
                    MonitoringPage.isStatusService(serverSIPModule),
                    serverSIPModule);
        }
    }

    @Story(value = "Статус конвертера Lan/E1 сервере SIP(абон)-DX")
    @Description(value = "Проверяем корректное отображение статуса конвертера Lan/E1 сервера SIP(абон)-DX")
    @Test
    void test_SIP_Abon_DX_Status_Converter(){
        getStatusConverter(
                SSHManager.isCheckQuerySSH("smg.status s | grep 231" + SMGSIP + " -A 7 | grep 'lapd_ready: establish'"),
                MonitoringPage.isCheckModuleConverterLanE1(serverSIPModule),
                MonitoringPage.isCheckTableConverterLanE1(serverSIPModule),
                SMGPult);
        if( ! MonitoringPage.isCheckModuleConverterLanE1(serverSIPModule) || ! MonitoringPage.isCheckTableConverterLanE1(serverSIPModule)){
            refresh();
            getStatusConverter(
                    SSHManager.isCheckQuerySSH("smg.status s | grep 231" + SMGSIP + " -A 7 | grep 'lapd_ready: establish'"),
                    MonitoringPage.isCheckModuleConverterLanE1(serverSIPModule),
                    MonitoringPage.isCheckTableConverterLanE1(serverSIPModule),
                    SMGPult);
        }
    }

    @Story(value = "Статус сервера SIP Пульт")
    @Description(value = "Проверяем корректное отображение статуса сервера SIP Пульт")
    @Test
    void test_SIP_Pult_Status_Server(){
        getStatusServer(
                DX500Page.isCheckStartServers(serverSIPPult),
                MonitoringPage.isCheckModuleStatusServer(serverSIPPult),
                MonitoringPage.isStatusService(serverSIPPult),
                serverSIPPult);
        if( ! MonitoringPage.isCheckModuleStatusServer(serverSIPPult) || ! MonitoringPage.isStatusService(serverSIPPult)){
            refresh();
            getStatusServer(
                    DX500Page.isCheckStartServers(serverSIPPult),
                    MonitoringPage.isCheckModuleStatusServer(serverSIPPult),
                    MonitoringPage.isStatusService(serverSIPPult),
                    serverSIPPult);
        }
    }

    @Story(value = "Статус сервера контактов на сервере SIP Пульт")
    @Description(value = "Проверяем корректное отображение статус сервера контактов на сервере SIP ekmn")
    @Test
    void test_SIP_Pult_Status_Server_Contacts(){
        this.messageStatus = MonitoringPage.isConnectServerContacts(serverSIPPult, controlPortSIPPult);
        if(this.messageStatus != null) this.serverStatus = false;
    }

    @Story(value = "Статус станции на сервере SIP Пульт")
    @Description(value = "Проверяем корректное отображение статус станции на сервере SIP Пульт")
    @Test
    void test_SIP_Pult_Status_Connect_Station(){
        this.messageStatus = MonitoringPage.isConnectStation(serverSIPPult, SIPPultConnectStation);
        if(this.messageStatus != null) this.serverStatus = false;
    }

    @Story(value = "Статус конвертера Lan/E1 сервере SIP Пульт")
    @Description(value = "Проверяем корректное отображение статуса конвертера Lan/E1 сервера Пульт")
    @Test
    void test_SIP_Pult_Status_Converter(){
        getStatusConverter(
                SSHManager.isCheckQuerySSH("smg.status s | grep 231" + SMGSIPPult + " -A 7 | grep 'lapd_ready: establish'"),
                MonitoringPage.isCheckModuleConverterLanE1(serverSIPPult),
                MonitoringPage.isCheckTableConverterLanE1(serverSIPPult),
                SMGSIPPult);
        if( ! MonitoringPage.isCheckModuleConverterLanE1(serverSIPPult) || ! MonitoringPage.isCheckTableConverterLanE1(serverSIPPult)){
            refresh();
            getStatusConverter(
                    SSHManager.isCheckQuerySSH("smg.status s | grep 231" + SMGSIPPult + " -A 7 | grep 'lapd_ready: establish'"),
                    MonitoringPage.isCheckModuleConverterLanE1(serverSIPPult),
                    MonitoringPage.isCheckTableConverterLanE1(serverSIPPult),
                    SMGSIPPult);
        }
    }

    @Story(value = "Статус сервера Занятости")
    @Description(value = "Проверяем корректное отображение статуса сервера Занятости")
    @Test
    void test_Busy_Status_Server(){
        getStatusServer(
                DX500Page.isCheckStartServers(serverBusy),
                MonitoringPage.isCheckModuleStatusServer(serverBusy),
                MonitoringPage.isStatusService(serverBusy),
                serverBusy);
        if( ! MonitoringPage.isCheckModuleStatusServer(serverBusy) || ! MonitoringPage.isStatusService(serverBusy)){
            refresh();
            getStatusServer(
                    DX500Page.isCheckStartServers(serverBusy),
                    MonitoringPage.isCheckModuleStatusServer(serverBusy),
                    MonitoringPage.isStatusService(serverBusy),
                    serverBusy);
        }
    }

    @Story(value = "Статус сетевого интерфейса на сервере Занятости")
    @Description(value = "Проверяем корректное отображение статус сетевого интерфейса на сервере Занятости")
    @Test
    void test_Busy_Status_Network(){
        this.messageStatus = MonitoringPage.isAdapterName(serverBusy);
        if(this.messageStatus != null) this.serverStatus = false;
    }

    @Story(value = "Статус станции на сервере Занятости")
    @Description(value = "Проверяем корректное отображение статус станции на сервере Занятости")
    @Test
    void test_Busy_Status_Connect_Station(){
        this.messageStatus = MonitoringPage.isConnectStation(serverBusy, busyConnectStation);
        if(this.messageStatus != null) this.serverStatus = false;
    }

    @AfterEach
    void tearDown(){
        if( ! serverStatus) {
            ScreenshotTests.screenshot();
            fail(messageStatus);
        }
    }

    public void getStatusServer(boolean serverStatus, boolean moduleStatusServer, boolean tableStatusServer, String server){
        if(serverStatus && moduleStatusServer && tableStatusServer) serverStatus = true;
        else if( ! serverStatus && (moduleStatusServer || tableStatusServer)){
            this.serverStatus = false;
            this.messageStatus = "На сервере не запущена служба " + server + ", однако в СУ отображется статус - ОК";
        }else if(serverStatus && ( ! moduleStatusServer || ! tableStatusServer)) {
            this.serverStatus = false;
            this.messageStatus = "На сервере запущена служба " + server + ", однако в СУ отображается некорреткный статус";
        }else{
            this.serverStatus = false;
            this.messageStatus = "На сервере не запущен служба " + server + " и в СУ отображается статус сервера - NOK";
        }
    }

    public void getStatusConverter(boolean serverStatus, boolean moduleStatusConverter, boolean tableStatusConverter, String SMG){
        if(serverStatus && moduleStatusConverter && tableStatusConverter) serverStatus = true;
        else if( ! serverStatus && (moduleStatusConverter || tableStatusConverter)){
            this.serverStatus = false;
            this.messageStatus = "На серере статус SMG: " + SMG + " - disconnected, однако в СУ отображется статус SMG - establish";
        }else if(serverStatus && ( ! moduleStatusConverter || ! tableStatusConverter)) {
            this.serverStatus = false;
            this.messageStatus = "На сервере статус SMG: " + SMG + " - establish, однако в СУ отображается статус SMG - disconnected";
        }else{
            this.serverStatus = false;
            this.messageStatus = "На сервере статус SMG: " + SMG + " - disconnected и в СУ отображается статус SMG - disconnected";
        }
    }
}

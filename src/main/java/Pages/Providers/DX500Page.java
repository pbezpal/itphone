package Pages.Providers;

import HelperClasses.SSHManager;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ex.ElementNotFound;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static DataTests.LOGIN.IP_SERVER;
import static DataTests.Providers.PROVIDER_DX500.*;
import static com.codeborne.selenide.Selenide.$;

public class DX500Page extends ProvidersPage{

    public static DX500Page dx500Page = new DX500Page();
    public static DX500Page getInstance() { return dx500Page; }

    public boolean addDX500Provider(){
        isCheckProviderPage();
        $("#add_provider").click();
        isFormEditProvider();
        clickSelectTypeProvider(DX500_TYPE_PROVIDER);
        addServers();
        setNameProvider(DX500_TYPE_PROVIDER);
        setRouteCalls(DX500_DIALPLAN);
        clickButtonAddProviders();

        return isCheckProvider(DX500_TYPE_PROVIDER, 60000);
    }

    @Step(value = "Создаем сервер {serverName}")
    public DX500Page addServer(String serverName){
        if (serverName.equals(DX500_SIP_ABON_DX)) serverName = DX500_SIP_ABON_DX_OPTION;
        $("#sel_add_new_server_to_prov").click();
        $("#sel_add_new_server_to_prov").selectOptionByValue(serverName);
        $("#btn_add_new_server_to_prov").click();

        return this;
    }

    @Step(value = "Переходим в раздел сервера {serverName}")
    public DX500Page clickSectionServer(String serverName){
        if($("li[aria-controls*=" + serverName + "]").attr("aria-selected").equals("false")) {
            $("li[aria-controls*=" + serverName + "]").click();
        }
        return this;
    }

    @Step(value = "Нажимаем кнопку 'Добавить SMG' на сервере {serverName}")
    public DX500Page clickButtonAddSMG(String serverName){
        $("form[id*='" + serverName + "']").find("button.btn-dx500-add-smg").click();
        return this;
    }

    @Step(value = "Вводим номер SMG - {numberSMG} для сервера {serverName}")
    public DX500Page sendInputSMG(String serverName, String numberSMG){
        if( ! $("form[id*='" + serverName + "']").find(By.name("smg")).val().equals(numberSMG)) {
            $("form[id*='" + serverName + "']").find(By.name("smg")).clear();
            $("form[id*='" + serverName + "']").find(By.name("smg")).sendKeys(numberSMG);
        }
        return this;
    }

    @Step(value = "Настраиваем тип конвертера для сервера {serverName}")
    public DX500Page selectTypeConverter(String serverName){
        $("form[id*='" + serverName + "']").find(By.name("smg_converter")).selectOptionByValue("Конвертер");
        return this;
    }

    @Step(value = "Вводим IP адрес конвертера - {converterIP} для сервера {serverName}")
    public DX500Page sendInputConverterIP(String serverName, String converterIP){
        if( ! $("form[id*='" + serverName + "']").find(By.name("conv_ip")).val().equals(converterIP)) {
            $("form[id*='" + serverName + "']").find(By.name("conv_ip")).clear();
            $("form[id*='" + serverName + "']").find(By.name("conv_ip")).setValue(converterIP);
        }
        return this;
    }

    @Step(value = "Выбираем IP адрес шлюза для сервера {serverName}")
    public DX500Page selectGateIP(String serverName){
        $("form[id*='" + serverName + "']").find(By.name("gate_ip")).selectOptionByValue(IP_SERVER);
        return this;
    }

    public DX500Page setServer(String serverName, String numberSMG, String converterIP){
        if( ! $("#provider_dx500_tab_elem").find("a[id*='" + serverName + "']").isDisplayed()) addServer(serverName);
        if(serverName.equals(DX500_SIP_ABON_DX_OPTION)) serverName = DX500_SIP_ABON_DX;
        clickSectionServer(serverName);
        clickButtonAddSMG(serverName);
        sendInputSMG(serverName, numberSMG);
        selectTypeConverter(serverName);
        sendInputConverterIP(serverName, converterIP);
        selectGateIP(serverName);
        return this;
    }

    public DX500Page setServer(String server){
        if( ! $("#provider_dx500_tab_elem").find("a[id*='" + server + "']").isDisplayed()) addServer(server);
        clickSectionServer(server);
        if(server.equals(DX500_BOOSTER)){
            $("form[id*='" + server + "']").find(By.name("ip")).clear();
            $("form[id*='" + server + "']").find(By.name("ip")).sendKeys(DX500_BOOSTER_IP_DB);
            $("form[id*='" + server + "']").find(By.name("station")).sendKeys(DX500_BOOSTER_STATION);
            $("form[id*='" + server + "']").find(By.name("gate_ip")).sendKeys(DX500_BOOSTER_GATE_IP);
            $("form[id*='" + server + "']").find(By.name("gate_port")).sendKeys(DX500_BOOSTER_GATE_PORT);
        }
        $("form[id*='" + server + "']").find(By.name("adapter_name")).selectOptionByValue(DX500_BOOSTER_ADAPTER_NAME);
        return this;
    }

    public DX500Page addServers(){
        setServer(DX500_SIP_ABON_DX, DX500_SIP_ABON_DX_SMG, DX500_SIP_ABON_DX_IP_CONVERTER);
        setServer(DX500_BOOSTER);
        setServer(DX500_BUSY);
        setServer(DX500_PULT, DX500_PULT_SMG, DX500_PULT_CONVERTER_IP);
        setServer(DX500_SIP_PULT, DX500_SIP_PULT_SMG, DX500_SIP_PULT_CONVERTER_IP);
        return this;
    }

    @Step(value = "Переходим на вкладку DX500")
    public DX500Page clickSectionDX500(){
        if( ! dx500Page.isFormEditProvider().isDisplayed()) dx500Page.clickButtonEditProvider(DX500_TYPE_PROVIDER);
        $("#provider_dialog_params").waitUntil(Condition.visible, 60000);
        $("li[aria-controls='dx500_settings']").click();
        return this;
    }

    @Step(value = "Запускаем сервер {serverName}")
    public boolean startServer(String serverName){
        clickSectionServer(serverName);
        if($("form[id*='" + serverName + "']").find("i.button-control-status").text().equals("Отключен")) {
            $("form[id*='" + serverName + "']").find("i.button-control-start").click();
        }

        try{
            $("form[id*='" + serverName + "']").find("i.button-control-status-start").waitUntil(Condition.visible, 60000);
        }
        catch (ElementNotFound elementNotFound) {
            return false;
        }
        return true;
    }

    @Step(value = "Проверяем, запущен ли сервер {serverName}")
    public static boolean isCheckStartServers(String serverName){
        return SSHManager.isCheckQuerySSH("systemctl status " + serverName + " | awk '/active/ && !/inactive/'");
    }

    @Step(value = "Проверяем, что в БД записался диалплан для DX500")
    public boolean isMySqlDialplan(){ return SSHManager.isCheckQuerySSH(MYSQL_DX500_DIALPLAN); }
}
